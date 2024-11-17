package ie.setu.repository

import ie.setu.domain.User
import ie.setu.domain.UserBMI
import ie.setu.domain.db.BMI
import ie.setu.domain.db.Users
import ie.setu.domain.repository.BMIDAO
import ie.setu.domain.repository.UserDAO
import ie.setu.helpers.bmi
import ie.setu.helpers.users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

//retrieving some test data from BMIhelper
val userBMIS1 = users[0]
val userBMIS2 = users[1]
val userBMIS3 = users[2]

//retrieving some test data from BMIhelper
val bmi1 = bmi[0]
val bmi2 = bmi[1]
val bmi3 = bmi[2]

class BMITest {
    companion object {

        //Make a connection to a local, in memory H2 database.
        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }

    internal fun populateUserBMITable(): BMIDAO {
        SchemaUtils.create(BMI)
        val bmiDAO = BMIDAO()
        bmiDAO.save(bmi1)
        bmiDAO.save(bmi2)
        bmiDAO.save(bmi3)
        return bmiDAO
    }
    internal fun populateUserTable(): UserDAO {
        SchemaUtils.create(Users)
        val userDAO = UserDAO()
        userDAO.save(userBMIS1)
        userDAO.save(userBMIS2)
        userDAO.save(userBMIS3)
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
                val bmiDAO = populateUserBMITable()

                //Act & Assert
                assertEquals(3, bmiDAO.getAll().size)
            }
        }
        @Test
        fun `get user by id that doesn't exist, results in no user returned`() {
            transaction {
                //Populate user table before
                val userDAO = populateUserTable()
                //Arrange - create and populate table with three users
                val bmiDAO = populateUserBMITable()

                //Act & Assert
                assertEquals(null, bmiDAO.findByUserId(4))
            }
        }
        @Test
        fun `get user by id that exists, results in a correct user returned`() {
            transaction {
                //Populate user table before
                val userDAO = populateUserTable()
                //Arrange - create and populate table with three users
                val bmiDAO = populateUserBMITable()

                //Act & Assert
                assertEquals(bmi3, bmiDAO.findByUserId(bmi3.userId))
            }
        }
        @Test
        fun `get all users over empty table returns none`() {
            transaction {
                //Arrange - create and setup userDAO object
                SchemaUtils.create(BMI)
                val bmiDAO = BMIDAO()

                //Act & Assert
                assertEquals(0, bmiDAO.getAll().size)
            }
        }
        @Nested
        inner class CreateUsers {
            @Test
            fun `multiple users added to table can be retrieved successfully`() {
                transaction {

                    //Arrange - create and populate table with three users
                    val userDAO = populateUserTable()
                    val bmiDAO = populateUserBMITable()
                    //Act & Assert
                    assertEquals(3, bmiDAO.getAll().size)
                    assertEquals(bmi1, bmiDAO.findByUserId(bmi1.userId))
                    assertEquals(bmi2, bmiDAO.findByUserId(bmi2.userId))
                    assertEquals(bmi3, bmiDAO.findByUserId(bmi3.userId))
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
                    val bmiDAO = populateUserBMITable()
                    //Act & Assert
                    assertEquals(3, bmiDAO.getAll().size)
                    bmiDAO.delete(4)
                    assertEquals(3, bmiDAO.getAll().size)
                }
            }

            @Test
            fun `deleting an existing user in table results in record being deleted`() {
                transaction {

                    //Arrange - create and populate table with three users
                    val userDAO = populateUserTable()
                    val bmiDAO = populateUserBMITable()
                    //Act & Assert
                    assertEquals(3, bmiDAO.getAll().size)
                    bmiDAO.delete(bmi3.userId)
                    assertEquals(2, bmiDAO.getAll().size)
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
                    val bmiDAO = populateUserBMITable()

                    //Act & Assert
                    val user3Updated = UserBMI(3, 76F, 1.9F,21.052631F, userId = 3)
                    bmiDAO.update(bmi3.userId, user3Updated)
                    assertEquals(user3Updated, bmiDAO.findByUserId(3))
                }
            }

            @Test
            fun `updating non-existant user in table results in no updates`() {
                transaction {

                    //Arrange - create and populate table with three users
                    val userDAO = populateUserTable()
                    val bmiDAO = populateUserBMITable()

                    //Act & Assert
                    val user4Updated = UserBMI(4, 76F, 1.9F,21.052631F, userId = 4)
                    bmiDAO.update(4, user4Updated)
                    assertEquals(null, bmiDAO.findByUserId(4))
                    assertEquals(3, bmiDAO.getAll().size)
                }
            }
        }


    }
}