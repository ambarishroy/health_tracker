package ie.setu.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ie.setu.domain.Step
import ie.setu.domain.UserBloodPressure
import ie.setu.domain.repository.BloodPressureDAO
import ie.setu.domain.repository.StepsDAO
import ie.setu.domain.repository.UserDAO
import io.javalin.http.Context

object BloodPressureController{

    private val userDao = UserDAO()
    private val bpDAO= BloodPressureDAO()
    fun getAllBP(ctx: Context) {
        ctx.json(bpDAO.getAll())
    }
    fun addBP(ctx: Context) {
        val mapper = jacksonObjectMapper()
        val bp = mapper.readValue<UserBloodPressure>(ctx.body())
        bpDAO.save(bp)
        ctx.json(bp)
    }
    fun getBPByUserId(ctx: Context) {
        if (userDao.findById(ctx.pathParam("user-id").toInt()) != null) {
            val bp = bpDAO.findByUserId(ctx.pathParam("user-id").toInt())
            if (bp.isNotEmpty()) {
                ctx.json(bp)
            }
        }
    }
}