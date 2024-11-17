package ie.setu.helpers

import ie.setu.domain.Step
import ie.setu.domain.User

val step= arrayListOf<Step>(
    Step(steps = 8995, target = 8000, status = "Congratulations!", id = 1, userId = 1),
    Step(steps = 10995, target = 9000, status = "Congratulations!", id = 2, userId = 2),
    Step(steps = 95, target = 8000, status = "7905 more steps to go!", id = 3, userId = 3),
    Step(steps = 8945, target = 6000, status = "Congratulations!", id = 4, userId = 4),
)
val userSteps = arrayListOf<User>(
    User(name = "Alice Wonderland", email = "alice@wonderland.com", age=24, id = 1),
    User(name = "Bob Cat", email = "bob@cat.ie", age=35, id = 2),
    User(name = "Mary Contrary", email = "mary@contrary.com", age=27,id = 3),
    User(name = "Carol Singer", email = "carol@singer.com", age=31, id = 4)
)