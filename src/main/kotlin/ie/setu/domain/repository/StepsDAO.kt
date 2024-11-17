package ie.setu.domain.repository

import ie.setu.domain.Step
import ie.setu.domain.db.StepsTrack
import ie.setu.utils.mapToSteps
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class StepsDAO {
    //Get the steps of users in the database regardless of user id
    fun getAll(): ArrayList<Step> {
        val stepList: ArrayList<Step> = arrayListOf()
        transaction {
            StepsTrack.selectAll().map {
                stepList.add(mapToSteps(it)) }
        }
        return stepList
    }
    //Find steps completed for a specific user id
    fun findByUserId(userId: Int): Step?{
        return transaction {
            StepsTrack
                .selectAll().where { StepsTrack.userId eq userId}
                .map { mapToSteps(it) }
                .singleOrNull()
        }
    }
    //Save the steps to the database
    fun save(stp: Step){
        transaction {
            StepsTrack.insert {
                it[steps] = stp.steps
                it[target] = stp.target
                it[statusresponse] = categorizeSteps(stp.steps, stp.target)
                it[userId] = stp.userId
            }
        }
    }
    fun delete(id: Int):Int{
        return transaction{
            StepsTrack.deleteWhere{ userId eq id }
        }
    }

    fun update(id: Int, stp: Step){
        transaction {
            StepsTrack.update ({
                StepsTrack.userId eq id}) {
                it[steps] = stp.steps
                it[target] = stp.target
                it[statusresponse] = categorizeSteps(stp.steps, stp.target)
            }
        }
    }
    fun categorizeSteps(steps: Int, target: Int):String{
        val status: String?
        if(steps<target){
             status= "${target-steps} more steps to go!"
        }
        else{
             status="Congratulations!"
        }
        return status
    }
}