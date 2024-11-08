package ie.setu.domain

data class UserBMI (var id: Int,
                       var weight: Float,
                       var height: Float,
                       var calculatedBMI: Float,
                       var userId: Int)