package ie.setu.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ie.setu.domain.UserBMI
import ie.setu.domain.repository.BMIDAO
import ie.setu.domain.repository.UserDAO
import io.javalin.http.Context

object BMIController{
    private val bmiDAO = BMIDAO()
    private val userDao = UserDAO()

    fun getAllBMI(ctx: Context) {
        ctx.json(bmiDAO.getAll())
    }
    fun addBMI(ctx: Context) {
        val mapper = jacksonObjectMapper()
        val bmi = mapper.readValue<UserBMI>(ctx.body())
        bmiDAO.save(bmi)
        ctx.json(bmi)
    }
    fun getBMIByUserId(ctx: Context) {
        if (userDao.findById(ctx.pathParam("user-id").toInt()) != null) {
            val bmi = bmiDAO.findByUserId(ctx.pathParam("user-id").toInt())
            if (bmi.isNotEmpty()) {
                ctx.json(bmi)
            }
        }
    }
    fun deleteUserBMI(ctx: Context){
        bmiDAO.delete(ctx.pathParam("user-id").toInt())
    }

    fun updateUserBMI(ctx: Context){
        val mapper = jacksonObjectMapper()
        val userUpdates = mapper.readValue<UserBMI>(ctx.body())
        bmiDAO.update(
            id = ctx.pathParam("user-id").toInt(),
            bmi = userUpdates)
    }
}