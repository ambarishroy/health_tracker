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
    fun findByUserId(userId: Int): UserBloodPressure ?{
        return transaction {
            BloodPressure
                .selectAll().where { BloodPressure.userId eq userId}
                .map { mapToBP(it) }
                .singleOrNull()
        }
    }
    //Save the bloodpressure to the database
    fun save(bp: UserBloodPressure){
        transaction {
            BloodPressure.insert {
                it[lower] = bp.lowerval
                it[upper] = bp.upperval
                bp.category = categorizeBloodPressure(bp.upperval, bp.lowerval)
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
                bp.category = categorizeBloodPressure(bp.upperval, bp.lowerval)
                it[statusresponse] = bp.category
            }
        }
    }
    fun categorizeBloodPressure(upper: Int, lower: Int): String {
        if (upper < 120 && lower < 80) {
            return "Blood pressure is normal."
        } else if (upper in 121..128 && lower < 80) {
            return "Blood pressure is elevated."
        } else if (upper in 131..138 || lower in 81..88) {
            return "Blood pressure is high. Hypertension stage 1."
        } else if (upper in 140..180 || lower in 90..120) {
            return "Blood pressure is high. Hypertension stage 2."
        } else {
            return "Hypertensive crisis! Consult your doctor immediately."
        }
    }
}