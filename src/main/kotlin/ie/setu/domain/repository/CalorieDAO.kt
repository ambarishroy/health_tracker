package ie.setu.domain.repository

import ie.setu.domain.Calorie
import ie.setu.domain.db.Calories
import ie.setu.utils.mapToCalorie
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

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
    fun findByUserId(userId: Int): Calorie?{
        return transaction {
            Calories
                .selectAll().where { Calories.userId eq userId}
                .map { mapToCalorie(it) }
                .singleOrNull()
        }
    }
    //Save the Calorie to the database
    fun save(cal: Calorie){
        transaction {
            Calories.insert {
                it[weight] = cal.weight
                it[height] = cal.height
                it[velocity] = cal.velocity
                it[calculatedcalorie] = CalculateCalorie(cal.weight, cal.height, cal.velocity)
                it[userId] = cal.userId
            }
        }
    }
    fun delete(id: Int):Int{
        return transaction{
            Calories.deleteWhere{ userId eq id }
        }
    }

    fun update(id: Int, cal: Calorie){
        transaction {
            Calories.update ({
                Calories.userId eq id}) {
                it[weight] = cal.weight
                it[height] = cal.height
                it[velocity] = cal.velocity
                it[calculatedcalorie] = CalculateCalorie(cal.weight, cal.height, cal.velocity)
            }
        }
    }
    fun CalculateCalorie(weight: Float, height: Float, velocity:Float): Float {
        val calCalorie= (weight*0.035)+(((velocity*velocity)/height))*0.029*weight
        return calCalorie.toFloat()
    }
}