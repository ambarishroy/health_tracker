package ie.setu.domain.repository

import ie.setu.domain.Calorie
import ie.setu.domain.UserBMI
import ie.setu.domain.db.BMI
import ie.setu.domain.db.Calories
import ie.setu.utils.mapToBMI
import ie.setu.utils.mapToCalorie
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class CalorieDAO {
    //Get all the calorie in the database regardless of user id
    fun getAll(): ArrayList<Calorie> {
        val calorieList: ArrayList<Calorie> = arrayListOf()
        transaction {
            Calories.selectAll().map {
                calorieList.add(mapToCalorie(it)) }
        }
        return calorieList
    }
    //Find calorie for a specific user id
    fun findByUserId(userId: Int): List<Calorie>{
        return transaction {
            Calories
                .selectAll().where { Calories.userId eq userId}
                .map { mapToCalorie(it) }
        }
    }
    //Save the Calorie to the database
    fun save(cal: Calorie){
        val calCalorie= (cal.weight*0.035)+(((cal.velocity*cal.velocity)/cal.height))*0.029*cal.weight
        transaction {
            Calories.insert {
                it[weight] = cal.weight
                it[height] = cal.height
                it[velocity] = cal.velocity
                it[userId] = cal.userId
                it[calculatedcalorie] = calCalorie.toFloat()
            }
        }
    }
}