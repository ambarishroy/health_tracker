package ie.setu.utils

import ie.setu.domain.*
import org.jetbrains.exposed.sql.ResultRow
import ie.setu.domain.db.*

fun mapToUser(it: ResultRow) = User(
    id = it[Users.id],
    name = it[Users.name],
    email = it[Users.email]
)
fun mapToActivity(it: ResultRow) = Activity(
    id = it[Activities.id],
    description = it[Activities.description],
    duration = it[Activities.duration],
    started = it[Activities.started],
    calories = it[Activities.calories],
    userId = it[Activities.userId]
)
fun mapToRatings(it: ResultRow) = UserRating(
    id = it[Ratings.id],
    rating = it[Ratings.rating],
    userId = it[Ratings.userId]
)
fun mapToBMI(it: ResultRow) = UserBMI(
    id = it[BMI.id],
    weight = it[BMI.weight].toFloat(),
    height = it[BMI.height].toFloat(),
    userId = it[BMI.userId],
    calculatedBMI = it[BMI.calculatedBMI].toFloat()
)
fun mapToCalorie(it: ResultRow) = Calorie(
    id = it[Calories.id],
    weight = it[Calories.weight].toFloat(),
    height = it[Calories.height].toFloat(),
    userId = it[Calories.userId],
    calculatedCalorie = it[Calories.calculatedcalorie].toFloat(),
    velocity = it[Calories.velocity].toFloat(),
)