package ie.setu.helpers

import ie.setu.domain.User
import ie.setu.domain.Calorie

val cal= arrayListOf<Calorie>(
    Calorie(weight = 65F, height = 1.7F, calculatedCalorie = 4.448294F, velocity = 1.4F, id = 1, userId = 1),
    Calorie(weight = 66F, height = 1.8F, calculatedCalorie = 4.7025F, velocity = 1.5F, id = 2, userId = 2),
    Calorie(weight = 76F, height = 1.9F, calculatedCalorie = 5.6296F, velocity = 1.6F, id = 3, userId = 3),
    Calorie(weight = 98F, height = 2F, calculatedCalorie = 24F, id = 4, velocity = 1F ,userId = 4)
)
val userCalorie = arrayListOf<User>(
    User(name = "Alice Wonderland", email = "alice@wonderland.com", age=24, id = 1),
    User(name = "Bob Cat", email = "bob@cat.ie", age=35, id = 2),
    User(name = "Mary Contrary", email = "mary@contrary.com", age=27,id = 3),
    User(name = "Carol Singer", email = "carol@singer.com", age=31, id = 4)
)