package ie.setu.controllers

import ie.setu.config.DbConfig
import ie.setu.domain.UserRating
import ie.setu.helpers.*
import ie.setu.utils.jsonToObject
import kong.unirest.core.HttpResponse
import kong.unirest.core.JsonNode
import kong.unirest.core.Unirest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

//Integration testing for ratings posted by user
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RatingControllerTest {
    private val db = DbConfig().getDbConnection()
    private val app = ServerContainer.instance
    private val origin = "http://localhost:" + app.port()

    //helper function to add a test user to the database
    private fun addUser ( userId: Int,rating: Int,): HttpResponse<JsonNode> {
        return Unirest.post(origin + "/api/ratings")
            .body("{\"userId\":\"$userId\", \"rating\":\"$rating\"}")
            .asJson()
    }
    //helper function to delete a test user from the database
    private fun deleteUser (userId: Int): HttpResponse<String> {
        return Unirest.delete(origin + "/api/users/$userId/ratings").asString()
    }
    //helper function to retrieve a test user from the database by id
    private fun retrieveUserById(userId: Int) : HttpResponse<String> {
        return Unirest.get(origin + "/api/users/${userId}/ratings").asString()
    }
    //helper function to add a test user to the database
    private fun updateUser (id: Int, rating: Int): HttpResponse<JsonNode> {
        return Unirest.patch(origin + "/api/users/$id/ratings")
            .body("{\"rating\":\"$rating\"}")
            .asJson()
    }
    @Nested
    inner class ReadUsersRatings{
        @Test
        fun `get all users from the database returns 200 or 404 response`() {
            val response = Unirest.get(origin + "/api/ratings/").asString()
            if (response.status == 200) {
                val retrievedUsers: ArrayList<UserRating> = jsonToObject(response.body.toString())
                assertNotEquals(0, retrievedUsers.size)
            }
            else {
                assertEquals(404, response.status)
            }
        }
        @Test
        fun `get user by id when user does not exist returns 404 response`() {
            //Arrange - test data for user id
            val id = Integer.MIN_VALUE
            // Act - attempt to retrieve the non-existent user from the database
            val retrieveResponse = Unirest.get(origin + "/api/users/${id}/ratings/").asString()
            // Assert -  verify return code
            assertEquals(404, retrieveResponse.status)
        }
        @Test
        fun `getting a user by id when id exists, returns a 200 response`() {

            //Arrange - add the user
            val addResponse = addUser(validUserId , validRating)
            val addedUser : UserRating = jsonToObject(addResponse.body.toString())

            //Assert - retrieve the added user from the database and verify return code
            val retrieveResponse = retrieveUserById(addedUser.userId)
            assertEquals(200, retrieveResponse.status)

            //After - restore the db to previous state by deleting the added user
            deleteUser(addedUser.userId)
        }
    }

    @Nested
    inner class CreateUserRatings{
        @Test
        fun `add a user with correct details returns a 201 response`() {

            //Arrange & Act & Assert
            //    add the user and verify return code (using Ratinghelper data)
            val addResponse = addUser(validUserId, validRating)
            assertEquals(201, addResponse.status)

            //Assert - verify the contents of the retrieved user
            val retrievedUser : UserRating = jsonToObject(addResponse.body.toString())
            assertEquals(validUserId, retrievedUser.userId)
            assertEquals(validRating, retrievedUser.rating)

            //After - restore the db to previous state by deleting the added user
            val deleteResponse = deleteUser(retrievedUser.userId)
            assertEquals(204, deleteResponse.status)
        }
    }
    @Nested
    inner class DeleteUserRatings{
        @Test
        fun `deleting a user when it doesn't exist, returns a 404 response`() {
            //Act & Assert - attempt to delete a user that doesn't exist
            assertEquals(404, deleteUser(-1).status)
        }

        @Test
        fun `deleting a user when it exists, returns a 204 response`() {

            //Arrange - add the user that we plan to do a delete on
            val addedResponse = addUser(validUserId, validRating)
            val addedUser : UserRating = jsonToObject(addedResponse.body.toString())

            //Act & Assert - delete the added user and assert a 204 is returned
            assertEquals(204, deleteUser(addedUser.userId).status)

            //Act & Assert - attempt to retrieve the deleted user --> 404 response
            assertEquals(404, retrieveUserById(addedUser.userId).status)
        }
    }

    @Nested
    inner class UpdateUserRatings{
        @Test
        fun `updating a user when it exists, returns a 204 response`() {

            //Arrange - add the user that we plan to do an update on
            val updatedRating = 4
            val addedResponse = addUser(validUserId, validRating)
            val addedUser : UserRating = jsonToObject(addedResponse.body.toString())

            //Act & Assert - update the rating of the retrieved user and assert 204 is returned
            assertEquals(204, updateUser(addedUser.userId, updatedRating).status)

            //Act & Assert - retrieve updated user and assert details are correct
            val updatedUserResponse = retrieveUserById(addedUser.userId)
            val updatedUser : UserRating = jsonToObject(updatedUserResponse.body.toString())
            assertEquals(updatedRating, updatedUser.rating)

            //After - restore the db to previous state by deleting the added user
            deleteUser(addedUser.userId)
        }

        @Test
        fun `updating a user when it doesn't exist, returns a 404 response`() {

            //Arrange - creating some text fixture data
            val updatedRating = 4

            //Act & Assert - attempt to update the email and name of user that doesn't exist
            assertEquals(404, updateUser(-1, updatedRating).status)
        }
    }

}