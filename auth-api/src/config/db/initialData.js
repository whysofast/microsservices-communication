import bcrypt from "bcrypt"
import User from "../../modules/user/model/User.js"

export async function createInitialData() {
    await User.sync({
        force: true
    });

    const password = await bcrypt.hash("123456", 10)

    let firstUser = await User.create({
        name: "User Test",
        email: "asdfasdf@aasdfa.com",
        password: password
    })
}