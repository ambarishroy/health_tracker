package ie.setu.config

import ie.setu.controllers.*
import ie.setu.utils.jsonObjectMapper
import io.javalin.Javalin
import io.javalin.http.Header
import io.javalin.json.JavalinJackson
import io.javalin.vue.VueComponent

class JavalinConfig {

    fun startJavalinService(): Javalin {
        val app = Javalin.create{
            //added this jsonMapper for our integration tests - serialise objects to json
            it.jsonMapper(JavalinJackson(jsonObjectMapper()))
            it.staticFiles.enableWebjars()
            it.vue.vueInstanceNameInJs = "app" // only required for Vue 3, is defined in layout.html
        }.apply {
            exception(Exception::class.java) { e, _ -> e.printStackTrace() }
            error(404) { ctx -> ctx.json("404 : Not Found") }
        }.before { ctx ->
            ctx.header(Header.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
            ctx.header(Header.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PATCH, DELETE, OPTIONS")
            ctx.header(Header.ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type, Authorization")
        }.start(7001)

        registerRoutes(app)
        return app
    }

    private fun registerRoutes(app: Javalin) {
        app.get("/api/users", HealthTrackerController::getAllUsers)
        app.get("/api/users/{user-id}", HealthTrackerController::getUserByUserId)
        app.post("/api/users", HealthTrackerController::addUser)
        app.delete("/api/users/{user-id}", HealthTrackerController::deleteUser)
        app.patch("/api/users/{user-id}", HealthTrackerController::updateUser)
        app.get("/api/users/email/{email}", HealthTrackerController::getUserByEmail)
        //activity route
        app.get("/api/activities", HealthTrackerController::getAllActivities)
        app.post("/api/activities", HealthTrackerController::addActivity)
        app.get("/api/users/{user-id}/activities", HealthTrackerController::getActivitiesByUserId)
        //rating route
        app.get("/api/ratings", RatingController::getAllRatings)
        app.post("/api/ratings", RatingController::addRating)
        app.get("/api/users/{user-id}/ratings", RatingController::getRatingsByUserId)
        app.delete("/api/users/{user-id}/ratings", RatingController::deleteUserRating)
        app.patch("/api/users/{user-id}/ratings", RatingController::updateUserRating)
        //BMI routes
        app.get("/api/bmi", BMIController::getAllBMI)
        app.post("/api/bmi", BMIController::addBMI)
        app.get("/api/users/{user-id}/bmi", BMIController::getBMIByUserId)
        app.delete("/api/users/{user-id}/bmi", BMIController::deleteUserBMI)
        app.patch("/api/users/{user-id}/bmi", BMIController::updateUserBMI)
        //calorie routes
        app.get("/api/calorie", CalorieController::getAllCalorie)
        app.post("/api/calorie", CalorieController::addCalorie)
        app.get("/api/users/{user-id}/calorie", CalorieController::getCalorieByUserId)
        app.delete("/api/users/{user-id}/calorie", CalorieController::deleteUserCalorie)
        app.patch("/api/users/{user-id}/calorie", CalorieController::updateUserCalorie)
        //stepstracking routes
        app.get("/api/steps", StepsController::getAllSteps)
        app.post("/api/steps", StepsController::addStepTracking)
        app.get("/api/users/{user-id}/steps", StepsController::getStepsByUserId)
        app.delete("/api/users/{user-id}/steps", StepsController::deleteUserSteps)
        app.patch("/api/users/{user-id}/steps", StepsController::updateUserSteps)
        //bloodpressuretracking routes
        app.get("/api/bloodpressure", BloodPressureController::getAllBP)
        app.post("/api/bloodpressure", BloodPressureController::addBP)
        app.get("/api/users/{user-id}/bloodpressure", BloodPressureController::getBPByUserId)
        app.delete("/api/users/{user-id}/bloodpressure", BloodPressureController::deleteUserBP)
        app.patch("/api/users/{user-id}/bloodpressure", BloodPressureController::updateUserBP)

        app.get("/", VueComponent("<home-page></home-page>"))
        app.get("/users", VueComponent("<user-overview></user-overview>"))
        app.get("/bloodpressure", VueComponent("<user-bloodpressure></user-bloodpressure>"))
        app.get("/ratings", VueComponent("<user-ratings></user-ratings>"))
        app.get("/bmi", VueComponent("<user-bmi></user-bmi>"))
        app.get("/calorie", VueComponent("<user-calorie></user-calorie>"))
        app.get("/steps", VueComponent("<user-steps></user-steps>"))
        app.get("/users/{user-id}", VueComponent("<user-profile></user-profile>"))
        app.get("/users/{user-id}/activities", VueComponent("<user-activity-overview></user-activity-overview>"))
        app.get("/users/{user-id}/bloodpressure", VueComponent("<user-bloodpressure-overview></user-bloodpressure-overview>"))


    }
}