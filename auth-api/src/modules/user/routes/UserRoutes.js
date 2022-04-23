import { Router } from "express";

import UserController from "../controller/UserController.js";
import checkToken from "../../../config/auth/checkToken.js";

const router = new Router()

router.post("/api/user/auth", UserController.getAccessToken)

router.use(checkToken) // endpoints a partir daqui são afetados pelo middleware

router.get("/api/user/email/:email", UserController.findByEmail)

export default router;