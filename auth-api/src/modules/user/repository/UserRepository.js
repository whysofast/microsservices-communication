import User from "../model/User.js"

class UserRepository {

    async findByEmail(email) {
        return await User.findOne({
            where: email
        })
    }

    async findById(id) {
        return await User.findOne({
            where: id
        })
    }
}

export default new UserRepository();