package ie.setu.helpers

import ie.setu.domain.User
import ie.setu.domain.UserRating

val rate= arrayListOf<UserRating>(
    UserRating(rating = 5, id = 1, userId = 1),
    UserRating(rating = 3, id = 2, userId = 2),
    UserRating(rating = 4, id = 3, userId = 3),
    UserRating(rating = 1, id = 4, userId = 4),
)
val userRatings = arrayListOf<User>(
    User(name = "Alice Wonderland", email = "alice@wonderland.com", age=24, id = 1),
    User(name = "Bob Cat", email = "bob@cat.ie", age=35, id = 2),
    User(name = "Mary Contrary", email = "mary@contrary.com", age=27,id = 3),
    User(name = "Carol Singer", email = "carol@singer.com", age=31, id = 4)
)