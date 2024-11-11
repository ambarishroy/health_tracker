package ie.setu.domain

data class Calorie (var id: Int,
                    var weight: Float,
                    var height: Float,
                    var velocity: Float,
                    var calculatedCalorie: Float,
                    var userId: Int)