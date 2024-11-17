package ie.setu.repository

import ie.setu.domain.db.StepsTrack
import ie.setu.domain.db.Users
import ie.setu.domain.repository.StepsDAO
import ie.setu.domain.repository.UserDAO
import ie.setu.helpers.step
import ie.setu.helpers.users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

//retrieving some test data from Stepshelper
val userSteps1 = users[0]
val userSteps2 = users[1]
val userSteps3 = users[2]

//retrieving some test data from Stepshelper
val stp1 = step[0]
val stp2 = step[1]
val stp3 = step[2]

class StepsTest {
    companion object {

        //Make a connection to a local, in memory H2 database.
        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }

    internal fun populateUserStepsTable(): StepsDAO {
        SchemaUtils.create(StepsTrack)
        val stepDAO = StepsDAO()
        stepDAO.save(stp1)
        stepDAO.save(stp2)
        stepDAO.save(stp3)
        return stepDAO
    }
    internal fun populateUserTable(): UserDAO {
        SchemaUtils.create(Users)
        val userDAO = UserDAO()
        userDAO.save(userSteps1)
        userDAO.save(userSteps2)
        userDAO.save(userSteps3)
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
                val stepDAO = populateUserStepsTable()

                //Act & Assert
                assertEquals(3, stepDAO.getAll().size)
            }
        }

        @Test
        fun `get user by id that doesn't exist, results in no user returned`() {
            transaction {
                //Populate user table before
                val userDAO = populateUserTable()
                //Arrange - create and populate table with three users
                val stepDAO = populateUserStepsTable()

                //Act & Assert
                assertEquals(null, stepDAO.findByUserId(4))
            }
        }

        @Test
        fun `get user by id that exists, results in a correct user returned`() {
            transaction {
                //Populate user table before
                val userDAO = populateUserTable()
                //Arrange - create and populate table with three users
                val stepDAO = populateUserStepsTable()

                //Act & Assert
                assertEquals(stp3, stepDAO.findByUserId(stp3.userId))
            }
        }

        @Test
        fun `get all users over empty table returns none`() {
            transaction {
                //Arrange - create and setup userDAO object
                SchemaUtils.create(StepsTrack)
                val stepsDAO = StepsDAO()

                //Act & Assert
                assertEquals(0, stepsDAO.getAll().size)
            }
        }
    }
}