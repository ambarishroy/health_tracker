package ie.setu.domain.repository

import ie.setu.domain.UserBloodPressure
import ie.setu.domain.db.BloodPressure
import ie.setu.utils.mapToBP
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class BloodPressureDAO{
    //Get the blood pressures of users in the database regardless of user id
    fun getAll(): ArrayList<UserBloodPressure> {
        val BPList: ArrayList<UserBloodPressure> = arrayListOf()
        transaction {
            BloodPressure.selectAll().map {
                BPList.add(mapToBP(it)) }
        }
        return BPList
    }
    //Find the blood pressure for a specific user id
    fun findByUserId(userId: Int): List<UserBloodPressure>{
        return transaction {
            BloodPressure
                .selectAll().where { BloodPressure.userId eq userId}
                .map { mapToBP(it) }
        }
    }
    //Save the bloodpressure to the database
    fun save(bp: UserBloodPressure){
        transaction {
            BloodPressure.insert {
                it[lower] = bp.lowerval
                it[upper] = bp.upperval
                if(bp.upperval<120 && bp.lowerval<80){
                    bp.category= "Blood pressure is normal."
                }
                else if((bp.upperval in 121..128) && bp.lowerval<80){
                    bp.category= "Blood pressure is elevated."
                }
                else if((bp.upperval in 131..138) || (bp.lowerval in 81..88)){
                    bp.category= "Blood pressure is high. Hypertension stage 1."
                }
                else if((bp.upperval in 140..180) || (bp.lowerval in 90..120)){
                    bp.category= "Blood pressure is high. Hypertension stage 2."
                }
                else{
                    bp.category="Hypertensive crisis! Consult your doctor immediately."
                }
                it[statusresponse] = bp.category
                it[userId] = bp.userId
            }
        }
    }
    fun delete(id: Int):Int{
        return transaction{
            BloodPressure.deleteWhere{ userId eq id }
        }
    }

    fun update(id: Int, bp: UserBloodPressure){
        transaction {
            BloodPressure.update ({
                BloodPressure.userId eq id}) {
                it[lower] = bp.lowerval
                it[upper] = bp.upperval
            }
        }
    }
}