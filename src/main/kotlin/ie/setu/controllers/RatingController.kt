package ie.setu.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ie.setu.domain.UserRating
import ie.setu.domain.repository.RatingDAO
import ie.setu.domain.repository.UserDAO
import io.javalin.http.Context

object RatingController{
    private val ratingDAO= RatingDAO()
    private val userDao= UserDAO()

    fun getAllRatings(ctx: Context) {
        ctx.json(ratingDAO.getAll())
    }
    fun addRating(ctx: Context) {
        val mapper = jacksonObjectMapper()
        val rating = mapper.readValue<UserRating>(ctx.body())
        ratingDAO.save(rating)
        ctx.json(rating)
    }
    fun getRatingsByUserId(ctx: Context) {
        if (userDao.findById(ctx.pathParam("user-id").toInt()) != null) {
            val ratings = ratingDAO.findByUserId(ctx.pathParam("user-id").toInt())
            if (ratings!=null) {
                ctx.json(ratings)
            }
        }
    }
    fun deleteUserRating(ctx: Context){
        ratingDAO.delete(ctx.pathParam("user-id").toInt())
    }

    fun updateUserRating(ctx: Context){
        val mapper = jacksonObjectMapper()
        val userUpdates = mapper.readValue<UserRating>(ctx.body())
        ratingDAO.update(
            id = ctx.pathParam("user-id").toInt(),
            rate = userUpdates)
    }
}