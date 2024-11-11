package ie.setu.domain

data class Step (var id: Int,
                    var steps: Int,
                    var target: Int,
                    var status: String?,
                    var userId: Int)