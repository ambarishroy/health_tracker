package ie.setu.repository

import ie.setu.domain.UserRating
import ie.setu.domain.db.Ratings
import ie.setu.domain.db.Users
import ie.setu.domain.repository.RatingDAO
import ie.setu.domain.repository.UserDAO
import ie.setu.helpers.rate
import ie.setu.helpers.users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

//retrieving some test data from Ratinghelper
val userRating1 = users[0]
val userRating2 = users[1]
val userRating3 = users[2]

//retrieving some test data from Ratinghelper
val rate1 = rate[0]
val rate2 = rate[1]
val rate3 = rate[2]

class RatingTest {
    companion object {

        //Make a connection to a local, in memory H2 database.
        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }

    internal fun populateUserRatingTable(): RatingDAO {
        SchemaUtils.create(Ratings)
        val ratingDAO = RatingDAO()
        ratingDAO.save(rate1)
        ratingDAO.save(rate2)
        ratingDAO.save(rate3)
        return ratingDAO
    }
    internal fun populateUserTable(): UserDAO {
        SchemaUtils.create(Users)
        val userDAO = UserDAO()
        userDAO.save(userRating1)
        userDAO.save(userRating2)
        userDAO.save(userRating3)
        return userDAO
    }
    @Nested
    inner class ReadUsers {
        @Test
        fun `getting all users from a populated table returns all rows`() {
            transaction {
                //Populate user table before
                val userDAO = populateUserTable()
                //Arrange - create and populate table with three users
                val ratingDAO = populateUserRatingTable()

                //Act & Assert
                assertEquals(3, ratingDAO.getAll().size)
            }
        }

        @Test
        fun `get user by id that doesn't exist, results in no user returned`() {
            transaction {
                //Populate user table before
                val userDAO = populateUserTable()
                //Arrange - create and populate table with three users
                val ratingDAO = populateUserRatingTable()

                //Act & Assert
                assertEquals(null, ratingDAO.findByUserId(4))
            }
        }

        @Test
        fun `get user by id that exists, results in a correct user returned`() {
            transaction {
                //Populate user table before
                val userDAO = populateUserTable()
                //Arrange - create and populate table with three users
                val ratingDAO = populateUserRatingTable()

                //Act & Assert
                assertEquals(rate1, ratingDAO.findByUserId(rate1.userId))
            }
        }

        @Test
        fun `get all users over empty table returns none`() {
            transaction {
                //Arrange - create and setup userDAO object
                SchemaUtils.create(Ratings)
                val ratingDAO = RatingDAO()

                //Act & Assert
                assertEquals(0, ratingDAO.getAll().size)
            }
        }
    }
    @Nested
    inner class CreateUsers {
        @Test
        fun `multiple users added to table can be retrieved successfully`() {
            transaction {

                //Arrange - create and populate table with three users
                val userDAO = populateUserTable()
                val ratingDAO = populateUserRatingTable()
                //Act & Assert
                assertEquals(3, ratingDAO.getAll().size)
                assertEquals(rate1, ratingDAO.findByUserId(rate1.userId))
                assertEquals(rate2, ratingDAO.findByUserId(rate2.userId))
                assertEquals(rate3, ratingDAO.findByUserId(rate3.userId))
            }
        }
    }
    @Nested
    inner class DeleteUsers {
        @Test
        fun `deleting a non-existant user in table results in no deletion`() {
            transaction {

                //Arrange - create and populate table with three users
                val userDAO = populateUserTable()
                val ratingDAO = populateUserRatingTable()
                //Act & Assert
                assertEquals(3, ratingDAO.getAll().size)
                ratingDAO.delete(4)
                assertEquals(3, ratingDAO.getAll().size)
            }
        }

        @Test
        fun `deleting an existing user in table results in record being deleted`() {
            transaction {

                //Arrange - create and populate table with three users
                val userDAO = populateUserTable()
                val ratingDAO = populateUserRatingTable()
                //Act & Assert
                assertEquals(3, ratingDAO.getAll().size)
                ratingDAO.delete(rate3.userId)
                assertEquals(2, ratingDAO.getAll().size)
            }
        }
    }
    @Nested
    inner class UpdateUsers {

        @Test
        fun `updating existing user in table results in successful update`() {
            transaction {

                //Arrange - create and populate table with three users
                val userDAO = populateUserTable()
                val ratingDAO = populateUserRatingTable()

                //Act & Assert
                val user3Updated = UserRating(3, 5,  3)
                ratingDAO.update(rate3.userId, user3Updated)
                assertEquals(user3Updated, ratingDAO.findByUserId(3))
            }
        }

        @Test
        fun `updating non-existant user in table results in no updates`() {
            transaction {

                //Arrange - create and populate table with three users
                val userDAO = populateUserTable()
                val ratingDAO = populateUserRatingTable()

                //Act & Assert
                val user4Updated = UserRating(4, 3,   4)
                ratingDAO.update(4, user4Updated)
                assertEquals(null, ratingDAO.findByUserId(4))
                assertEquals(3, ratingDAO.getAll().size)
            }
        }
    }
}