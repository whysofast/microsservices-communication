import bcrypt from "bcrypt"
import jwt from "jsonwebtoken"

import UserRepository from "../repository/UserRepository.js";
import UserException from "../exception/UserException.js";
import * as httpStatus from "../../../config/constants/httpStatus.js"
import * as secrets from "../../../config/constants/secrets.js"

class UserService {

    async findByEmail(req) {
        try {
            const email = req.params.email;

            this.validateRequestEmail(email);
            let user = await UserRepository.findByEmail(email);
            this.validateUserNotFound(user);

            return {
                status: httpStatus.OK,
                user: {
                    id: user.id,
                    name: user.name,
                    email: user.email
                }
            }
        } catch (error) {
            console.info(error)
            return {
                status: error.status ? error.status : httpStatus.INTERNAL_SERVER_ERROR,
                message: error.status
            }
        }
    }

    validateRequestEmail(email) {
        if (!email) {
            throw new UserException(httpStatus.BAD_REQUEST, "User email must be informed.")
        }
    }

    validateUserNotFound(user) {
        if (!user) {
            throw new UserException(httpStatus.NOT_FOUND, "User was not found.")
        }
    }

    async getAccessToken(req) {
        const {
            email,
            password
        } = req.body;

        this.validateAccessTokenData(email, password)
        let user = await UserRepository.findByEmail(this.findByEmail)
        this.validateUserNotFound(user);

        await this.validatePassword(password, user.password)
        const authUser = {
            id: user.id,
            name: user.name,
            email: user.email
        }
        const accessToken = jwt.sign({
            authUser
        }, secrets.API_SECRET, {
            expiresIn: "1d"
        })

        return {
            status: httpStatus.OK,
            accessToken
        }
    }

    validateAccessTokenData(email, password) {
        if (!email || !password) {
            throw new UserException(httpStatus.UNAUTHORIZED, "Credentials must be informed correctly.")
        }
    }

    async validatePassword(password, hashPassword) {
        if (!await bcrypt.compare(password, hashPassword)) {
            throw new UserException(httpStatus.UNAUTHORIZED, "Password doesnt match.")
        }
    }

}

export default new UserService;