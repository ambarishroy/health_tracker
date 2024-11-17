package ie.setu.helpers

import ie.setu.domain.User
import ie.setu.domain.UserBloodPressure

val bp= arrayListOf<UserBloodPressure>(
    UserBloodPressure(lowerval = 70, upperval = 112, category = "Blood pressure is normal.", id = 1, userId = 1),
    UserBloodPressure(lowerval = 78, upperval = 134, category = "Blood pressure is normal.", id = 2, userId = 2),
    UserBloodPressure(lowerval = 121, upperval = 196, category = "Blood pressure is high. Hypertension stage 1.", id = 3, userId = 3),
    UserBloodPressure(lowerval = 80, upperval = 116, category = "Blood pressure is normal.", id = 4, userId = 4),
)
val userBP = arrayListOf<User>(
    User(name = "Alice Wonderland", email = "alice@wonderland.com", age=24, id = 1),
    User(name = "Bob Cat", email = "bob@cat.ie", age=35, id = 2),
    User(name = "Mary Contrary", email = "mary@contrary.com", age=27,id = 3),
    User(name = "Carol Singer", email = "carol@singer.com", age=31, id = 4)
)