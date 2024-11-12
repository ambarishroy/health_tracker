package ie.setu.domain.db

import ie.setu.domain.db.StepsTrack.autoIncrement
import ie.setu.domain.db.StepsTrack.nullable
import ie.setu.domain.db.StepsTrack.references
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object BloodPressure : Table("bloodpressure") {
    val id = integer("id").autoIncrement()
    val lower = integer("lowerval")
    val upper= integer("upperval")
    val statusresponse = varchar("category", 100).nullable()
    val userId = integer("user_id").references(Users.id, onDelete = ReferenceOption.CASCADE)
}