package ie.setu.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ie.setu.domain.Calorie
import ie.setu.domain.repository.CalorieDAO
import ie.setu.domain.repository.UserDAO
import io.javalin.http.Context

object CalorieController{
    private val calorieDAO = CalorieDAO()
    private val userDao = UserDAO()

    fun getAllCalorie(ctx: Context) {
        ctx.json(calorieDAO.getAll())
    }
    fun addCalorie(ctx: Context) {
        val mapper = jacksonObjectMapper()
        val calorie = mapper.readValue<Calorie>(ctx.body())
        calorieDAO.save(calorie)
        ctx.json(calorie)
    }
    fun getCalorieByUserId(ctx: Context) {
        if (userDao.findById(ctx.pathParam("user-id").toInt()) != null) {
            val calorie = calorieDAO.findByUserId(ctx.pathParam("user-id").toInt())
            if (calorie.isNotEmpty()) {
                ctx.json(calorie)
            }
        }
    }
    fun deleteUserCalorie(ctx: Context){
        calorieDAO.delete(ctx.pathParam("user-id").toInt())
    }

    fun updateUserCalorie(ctx: Context){
        val mapper = jacksonObjectMapper()
        val userUpdates = mapper.readValue<Calorie>(ctx.body())
        calorieDAO.update(
            id = ctx.pathParam("user-id").toInt(),
            cal = userUpdates)
    }
}