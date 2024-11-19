package ie.setu.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ie.setu.domain.UserRating
import ie.setu.domain.repository.RatingDAO
import ie.setu.domain.repository.UserDAO
import ie.setu.utils.jsonToObject
import io.javalin.http.Context

object RatingController{
    private val ratingDAO= RatingDAO()
    private val userDao= UserDAO()

    fun getAllRatings(ctx: Context) {
        val usersRatings = ratingDAO.getAll()
        if (usersRatings.size != 0) {
            ctx.status(200)
        }
        else{
            ctx.status(404)
        }
        ctx.json(usersRatings)
    }
    fun addRating(ctx: Context) {
        val rating : UserRating = jsonToObject(ctx.body())
        val userId = ratingDAO.save(rating)
        if (userId != null) {
            //rating.userId = userId
            ctx.json(rating)
            ctx.status(201)
        }
    }
    fun getRatingsByUserId(ctx: Context) {
        val ratings = ratingDAO.findByUserId(ctx.pathParam("user-id").toInt())
            if (ratings!=null) {
                ctx.json(ratings)
                ctx.status(200)
            }
            else{
                ctx.status(404)
            }

    }
    fun deleteUserRating(ctx: Context){
        if(ratingDAO.delete(ctx.pathParam("user-id").toInt())!=0)
            ctx.status(204)
        else
            ctx.status(404)
    }

    fun updateUserRating(ctx: Context){
//        val mapper = jacksonObjectMapper()
//        val userUpdates = mapper.readValue<UserRating>(ctx.body())
        val foundUser : UserRating = jsonToObject(ctx.body())
        if ((ratingDAO.update(id = ctx.pathParam("user-id").toInt(), rate = foundUser)) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }
}