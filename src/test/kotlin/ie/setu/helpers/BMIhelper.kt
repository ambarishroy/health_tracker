package ie.setu.helpers

import ie.setu.domain.User
import ie.setu.domain.UserBMI

val bmi= arrayListOf<UserBMI>(
    UserBMI(weight = 65F, height = 1.7F, calculatedBMI = 22.491348F, id = 1, userId = 1),
     UserBMI(weight = 66F, height = 1.8F, calculatedBMI = 20.37037F, id = 2, userId = 2),
     UserBMI(weight = 76F, height = 1.9F, calculatedBMI = 21.052631F, id = 3, userId = 3),
     UserBMI(weight = 98F, height = 2F, calculatedBMI = 24F, id = 4, userId = 4)
)
val userBMIS = arrayListOf<User>(
    User(name = "Alice Wonderland", email = "alice@wonderland.com", age=24, id = 1),
    User(name = "Bob Cat", email = "bob@cat.ie", age=35, id = 2),
    User(name = "Mary Contrary", email = "mary@contrary.com", age=27,id = 3),
    User(name = "Carol Singer", email = "carol@singer.com", age=31, id = 4)
)