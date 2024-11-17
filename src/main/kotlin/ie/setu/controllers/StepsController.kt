package ie.setu.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ie.setu.domain.Step
import ie.setu.domain.repository.StepsDAO
import ie.setu.domain.repository.UserDAO
import io.javalin.http.Context

object StepsController {
    private val userDao = UserDAO()
    private val stepsDAO= StepsDAO()
    fun getAllSteps(ctx: Context) {
        ctx.json(stepsDAO.getAll())
    }
    fun addStepTracking(ctx: Context) {
        val mapper = jacksonObjectMapper()
        val steps = mapper.readValue<Step>(ctx.body())
        stepsDAO.save(steps)
        ctx.json(steps)
    }
    fun getStepsByUserId(ctx: Context) {
        if (userDao.findById(ctx.pathParam("user-id").toInt()) != null) {
            val steps = stepsDAO.findByUserId(ctx.pathParam("user-id").toInt())
            if (steps != null) {
                ctx.json(steps)
            }
        }
    }
    fun deleteUserSteps(ctx: Context){
        stepsDAO.delete(ctx.pathParam("user-id").toInt())
    }

    fun updateUserSteps(ctx: Context){
        val mapper = jacksonObjectMapper()
        val userUpdates = mapper.readValue<Step>(ctx.body())
        stepsDAO.update(
            id = ctx.pathParam("user-id").toInt(),
            stp = userUpdates)
    }
}