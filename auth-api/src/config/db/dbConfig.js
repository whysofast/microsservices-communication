import Sequelize from "sequelize";

const sequelize = new Sequelize('auth-db-course-msc',"admin", "123456", {
    host: "localhost",
    dialect: "postgres",
    quoteIdentifier: false,
    define: {
        syncOnAssociation: true,
        timestamps: false,
        underscored: true,
        underscoredAll: true,
        freezeTableName: true,
    },
});

sequelize
    .authenticate()
    .then(() => {
        console.info("Connection has been estabelecida, brow")
    })
    .catch((err)=> {
        console.error("Unable to connect to database")
        console.error(err.message)
    })


export default sequelize;