package ie.setu.repository

import ie.setu.domain.UserBloodPressure
import ie.setu.domain.db.BloodPressure
import ie.setu.domain.db.Users
import ie.setu.domain.repository.BloodPressureDAO
import ie.setu.domain.repository.UserDAO
import ie.setu.helpers.bp
import ie.setu.helpers.users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

//retrieving some test data from BPhelper
val userBP1 = users[0]
val userBP2 = users[1]
val userBP3 = users[2]

//retrieving some test data from BPhelper
val bp1 = bp[0]
val bp2 = bp[1]
val bp3 = bp[2]

class BPTest {
    companion object {

        //Make a connection to a local, in memory H2 database.
        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }

    internal fun populateUserBPTable(): BloodPressureDAO {
        SchemaUtils.create(BloodPressure)
        val BPDAO = BloodPressureDAO()
        BPDAO.save(bp1)
        BPDAO.save(bp2)
        BPDAO.save(bp3)
        return BPDAO
    }
    internal fun populateUserTable(): UserDAO {
        SchemaUtils.create(Users)
        val userDAO = UserDAO()
        userDAO.save(userBP1)
        userDAO.save(userBP2)
        userDAO.save(userBP3)
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
                val BPDAO = populateUserBPTable()

                //Act & Assert
                assertEquals(3, BPDAO.getAll().size)
            }
        }

        @Test
        fun `get user by id that doesn't exist, results in no user returned`() {
            transaction {
                //Populate user table before
                val userDAO = populateUserTable()
                //Arrange - create and populate table with three users
                val BPDAO = populateUserBPTable()

                //Act & Assert
                assertEquals(null, BPDAO.findByUserId(4))
            }
        }

        @Test
        fun `get user by id that exists, results in a correct user returned`() {
            transaction {
                //Populate user table before
                val userDAO = populateUserTable()
                //Arrange - create and populate table with three users
                val BPDAO = populateUserBPTable()

                //Act & Assert
                assertEquals(bp3, BPDAO.findByUserId(bp3.userId))
            }
        }

        @Test
        fun `get all users over empty table returns none`() {
            transaction {
                //Arrange - create and setup userDAO object
                SchemaUtils.create(BloodPressure)
                val BPDAO = BloodPressureDAO()

                //Act & Assert
                assertEquals(0, BPDAO.getAll().size)
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
                val BPDAO = populateUserBPTable()
                //Act & Assert
                assertEquals(3, BPDAO.getAll().size)
                assertEquals(bp1, BPDAO.findByUserId(bp1.userId))
                assertEquals(bp2, BPDAO.findByUserId(bp2.userId))
                assertEquals(bp3, BPDAO.findByUserId(bp3.userId))
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
                val BPDAO = populateUserBPTable()
                //Act & Assert
                assertEquals(3, BPDAO.getAll().size)
                BPDAO.delete(4)
                assertEquals(3, BPDAO.getAll().size)
            }
        }

        @Test
        fun `deleting an existing user in table results in record being deleted`() {
            transaction {

                //Arrange - create and populate table with three users
                val userDAO = populateUserTable()
                val BPDAO = populateUserBPTable()
                //Act & Assert
                assertEquals(3, BPDAO.getAll().size)
                BPDAO.delete(bp3.userId)
                assertEquals(2, BPDAO.getAll().size)
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
                val BPDAO = populateUserBPTable()

                //Act & Assert
                val user3Updated = UserBloodPressure(3, 90, 111,"Blood pressure is normal.", userId = 3)
                BPDAO.update(bp3.userId, user3Updated)
                assertEquals(user3Updated, BPDAO.findByUserId(3))
            }
        }

        @Test
        fun `updating non-existant user in table results in no updates`() {
            transaction {

                //Arrange - create and populate table with three users
                val userDAO = populateUserTable()
                val BPDAO = populateUserBPTable()

                //Act & Assert
                val user4Updated = UserBloodPressure(4, 90, 111,"Blood pressure is normal.", userId = 4)
                BPDAO.update(4, user4Updated)
                assertEquals(null, BPDAO.findByUserId(4))
                assertEquals(3, BPDAO.getAll().size)
            }
        }
    }
}