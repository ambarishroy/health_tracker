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
        val StepList: ArrayList<Step> = arrayListOf()
        transaction {
            StepsTrack.selectAll().map {
                StepList.add(mapToSteps(it)) }
        }
        return StepList
    }
    //Find steps completed for a specific user id
    fun findByUserId(userId: Int): List<Step>{
        return transaction {
            StepsTrack
                .selectAll().where { StepsTrack.userId eq userId}
                .map { mapToSteps(it) }
        }
    }
    //Save the steps to the database
    fun save(stp: Step){
        transaction {
            StepsTrack.insert {
                it[steps] = stp.steps
                it[target] = stp.target
                if(stp.steps<stp.target){
                    stp.status= "${stp.target-stp.steps} more steps to go!"
                }
                else{
                    stp.status="Congratulations!"
                }
                it[statusresponse] = stp.status
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
            }
        }
    }
}