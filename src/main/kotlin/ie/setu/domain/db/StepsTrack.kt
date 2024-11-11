package ie.setu.domain.db

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object StepsTrack : Table("steps") {
    val id = integer("id").autoIncrement()
    val steps = integer("steps")
    val target= integer("target")
    val statusresponse = varchar("status", 100).nullable()
    val userId = integer("user_id").references(Users.id, onDelete = ReferenceOption.CASCADE)
}