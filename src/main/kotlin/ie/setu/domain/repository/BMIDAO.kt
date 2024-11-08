package ie.setu.domain.repository

import ie.setu.domain.UserBMI
import ie.setu.domain.UserRating
import ie.setu.domain.db.BMI
import ie.setu.domain.db.Ratings
import ie.setu.utils.mapToBMI
import ie.setu.utils.mapToRatings
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class BMIDAO{
    //Get all the BMI in the database regardless of user id
    fun getAll(): ArrayList<UserBMI> {
        val BMIList: ArrayList<UserBMI> = arrayListOf()
        transaction {
            BMI.selectAll().map {
                BMIList.add(mapToBMI(it)) }
        }
        return BMIList
    }
    //Find BMI for a specific user id
    fun findByUserId(userId: Int): List<UserBMI>{
        return transaction {
            BMI
                .selectAll().where { BMI.userId eq userId}
                .map { mapToBMI(it) }
        }
    }
    //Save the BMI to the database
    fun save(bmi: UserBMI){
    val calBMI= bmi.weight/(bmi.height * bmi.height)
        transaction {
            BMI.insert {
                it[weight] = bmi.weight
                it[height] = bmi.height
                it[userId] = bmi.userId
                it[calculatedBMI] = calBMI
            }
        }
    }
}