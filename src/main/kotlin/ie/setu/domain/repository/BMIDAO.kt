package ie.setu.domain.repository

import ie.setu.domain.UserBMI
import ie.setu.domain.db.BMI
import ie.setu.utils.mapToBMI
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

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
    fun findByUserId(userId: Int): UserBMI?{
        return transaction {
            BMI
                .selectAll().where { BMI.userId eq userId}
                .map { mapToBMI(it) }
                .singleOrNull()
        }
    }
    //Save the BMI to the database
    fun save(bmi: UserBMI){
        transaction {
            BMI.insert {
                it[weight] = bmi.weight
                it[height] = bmi.height
                it[userId] = bmi.userId
                it[calculatedBMI] = CalculateBMI(bmi.weight, bmi.height)
            }
        }
    }
    fun delete(id: Int):Int{
        return transaction{
            BMI.deleteWhere{ userId eq id }
        }
    }

    fun update(id: Int, bmi: UserBMI){
        transaction {
            BMI.update ({
                BMI.userId eq id}) {
                it[weight] = bmi.weight
                it[height] = bmi.height
                it[calculatedBMI]=CalculateBMI(bmi.weight, bmi.height)
            }
        }
    }
    fun CalculateBMI(weight:Float, height:Float):Float{
        val calBMI=weight/(height*height)
        return calBMI
    }
}