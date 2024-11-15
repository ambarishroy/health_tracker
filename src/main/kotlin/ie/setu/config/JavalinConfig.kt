package ie.setu.config

import ie.setu.controllers.*
import ie.setu.utils.jsonObjectMapper
import io.javalin.Javalin
import io.javalin.json.JavalinJackson
import org.jetbrains.exposed.sql.selectAll

class JavalinConfig {

    fun startJavalinService(): Javalin {
        val app = Javalin.create {
            //add this jsonMapper to serialise objects to json
            it.jsonMapper(JavalinJackson(jsonObjectMapper()))
        }
            .apply{
                exception(Exception::class.java) { e, ctx -> e.printStackTrace() }
                error(404) { ctx -> ctx.json("404 - Not Found") }
            }
            .start(7001)

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

    }
}