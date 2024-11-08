package ie.setu.config

import ie.setu.controllers.HealthTrackerController
import ie.setu.controllers.RatingController
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
        app.get("/api/ratings/{user-id}", RatingController::getRatingsByUserId)

    }

}