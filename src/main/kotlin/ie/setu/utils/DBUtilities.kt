package ie.setu.utils

import ie.setu.domain.User
import ie.setu.domain.db.Users
import org.jetbrains.exposed.sql.ResultRow
import ie.setu.domain.Activity
import ie.setu.domain.db.Activities
import ie.setu.domain.UserRating
import ie.setu.domain.db.Ratings

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