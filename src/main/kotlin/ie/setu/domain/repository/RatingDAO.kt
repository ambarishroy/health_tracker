package ie.setu.domain.repository

import ie.setu.domain.UserRating
import ie.setu.domain.db.Ratings
import ie.setu.utils.mapToRatings
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

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
    fun delete(id: Int):Int{
        return transaction{
            Ratings.deleteWhere{ userId eq id }
        }
    }

    fun update(id: Int, rate: UserRating){
        transaction {
            Ratings.update ({
                Ratings.userId eq id}) {
                it[rating] = rate.rating
            }
        }
    }
}