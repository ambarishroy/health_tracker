package ie.setu.domain.db

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object Ratings : Table("ratings") {
    val id = integer("id").autoIncrement()
    val rating = integer("rating")
    val userId = integer("user_id").references(Users.id, onDelete = ReferenceOption.CASCADE)
}