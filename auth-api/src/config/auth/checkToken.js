import jwt from "jsonwebtoken"
import { promisify } from "util"

import * as secrets from "../constants/secrets.js"

import AuthException from "./AuthException.js";
import * as httpStatus from "../constants/httpStatus.js"

export default async (req, res, next) => {

    try {

        const { authorization } = req.headers;
        if (!authorization) throw new AuthException(httpStatus.UNAUTHORIZED, "Access token is missing.")

        const accessToken = validateBearer(authorization);

        const decoded = await promisify(jwt.verify)(accessToken, secrets.API_SECRET);

        req.authUser = decoded.authUser;
        return next();
    } catch (err) {
        const status = err.status ? err.status : httpStatus.INTERNAL_SERVER_ERROR
        return res.status(status).json({ status, message: err.message })
    }

}

function validateBearer(authorization) {
    if (authorization.includes(" ")) return authorization.split(" ")[1];
}
