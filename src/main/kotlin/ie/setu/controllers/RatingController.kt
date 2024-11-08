package ie.setu.controllers

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ie.setu.domain.UserRating
import ie.setu.domain.repository.ActivityDAO
import ie.setu.domain.repository.RatingDAO
import ie.setu.domain.repository.UserDAO
import io.javalin.http.Context

object RatingController{
    private val ratingDAO= RatingDAO()
    private val userDao= UserDAO()
    private val activityDAO= ActivityDAO()

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
            if (ratings.isNotEmpty()) {
                ctx.json(ratings)
            }
        }
    }
}