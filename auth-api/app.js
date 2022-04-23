import express from "express";
import * as db from "./src/config/db/initialData.js";
import userRoutes from "./src/modules/user/routes/UserRoutes.js"

const app = express();
const env = process.env;
const PORT = env.PORT || 8080;

db.createInitialData();

app.get("/health", (req,res) => {
    return res.status(200).json({status : "up"})
})

app.use(userRoutes)
app.use(express.json())

app.listen(PORT, () => {
    console.info(`Server started on ${PORT}`);
});