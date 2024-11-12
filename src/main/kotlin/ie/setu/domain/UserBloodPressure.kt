package ie.setu.domain

data class UserBloodPressure (var id: Int,
                 var lowerval: Int,
                 var upperval: Int,
                 var category: String?,
                 var userId: Int)