package ie.setu.domain.repository

import ie.setu.domain.Activity
import ie.setu.domain.UserRating
import ie.setu.domain.db.Activities
import ie.setu.domain.db.Ratings
import ie.setu.utils.mapToActivity
import ie.setu.utils.mapToRatings
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class RatingDAO{
    //Get all the ratings in the database regardless of user id
    fun getAll(): ArrayList<UserRating> {
        val RatingList: ArrayList<UserRating> = arrayListOf()
        transaction {
            Ratings.selectAll().map {
                RatingList.add(mapToRatings(it)) }
        }
        return RatingList
    }
    //Find rating for a specific user id
    fun findByUserId(userId: Int): List<UserRating>{
        return transaction {
            Ratings
                .selectAll().where {Ratings.userId eq userId}
                .map { mapToRatings(it) }
        }
    }
    //Save the rating to the database
    fun save(rate: UserRating){
        transaction {
            Ratings.insert {
                it[rating] = rate.rating
                it[userId] = rate.userId
            }
        }
    }
}