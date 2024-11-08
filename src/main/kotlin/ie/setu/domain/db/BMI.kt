package ie.setu.domain.db

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object BMI : Table("bmi") {
    val id = integer("id").autoIncrement()
    val weight = float("weight")
    val height = float("height")
    val calculatedBMI = float("bmi_value")
    val userId = integer("user_id").references(Users.id, onDelete = ReferenceOption.CASCADE)
}