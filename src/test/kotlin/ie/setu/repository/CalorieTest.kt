package ie.setu.repository

import ie.setu.domain.Calorie
import ie.setu.domain.UserBMI
import ie.setu.domain.db.BMI
import ie.setu.domain.db.Calories
import ie.setu.domain.db.Users
import ie.setu.domain.repository.BMIDAO
import ie.setu.domain.repository.CalorieDAO
import ie.setu.domain.repository.UserDAO
import ie.setu.helpers.cal
import ie.setu.helpers.users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

//retrieving some test data from Caloriehelper
val userCal1 = users[0]
val userCal2 = users[1]
val userCal3 = users[2]

//retrieving some test data from Caloriehelper
val cal1 = cal[0]
val cal2 = cal[1]
val cal3 = cal[2]

class CalorieTest {
    companion object {

        //Make a connection to a local, in memory H2 database.
        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }

    internal fun populateUserCalorieTable(): CalorieDAO {
        SchemaUtils.create(Calories)
        val calDAO = CalorieDAO()
        calDAO.save(cal1)
        calDAO.save(cal2)
        calDAO.save(cal3)
        return calDAO
    }
    internal fun populateUserTable(): UserDAO {
        SchemaUtils.create(Users)
        val userDAO = UserDAO()
        userDAO.save(userCal1)
        userDAO.save(userCal2)
        userDAO.save(userCal3)
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
                val calDAO = populateUserCalorieTable()

                //Act & Assert
                assertEquals(3, calDAO.getAll().size)
            }
        }

        @Test
        fun `get user by id that doesn't exist, results in no user returned`() {
            transaction {
                //Populate user table before
                val userDAO = populateUserTable()
                //Arrange - create and populate table with three users
                val calDAO = populateUserCalorieTable()

                //Act & Assert
                assertEquals(null, calDAO.findByUserId(4))
            }
        }

        @Test
        fun `get user by id that exists, results in a correct user returned`() {
            transaction {
                //Populate user table before
                val userDAO = populateUserTable()
                //Arrange - create and populate table with three users
                val calDAO = populateUserCalorieTable()

                //Act & Assert
                assertEquals(cal3, calDAO.findByUserId(cal3.userId))
            }
        }

        @Test
        fun `get all users over empty table returns none`() {
            transaction {
                //Arrange - create and setup userDAO object
                SchemaUtils.create(Calories)
                val calDAO = CalorieDAO()

                //Act & Assert
                assertEquals(0, calDAO.getAll().size)
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
                val calDAO = populateUserCalorieTable()
                //Act & Assert
                assertEquals(3, calDAO.getAll().size)
                assertEquals(cal1, calDAO.findByUserId(cal1.userId))
                assertEquals(cal2, calDAO.findByUserId(cal2.userId))
                assertEquals(cal3, calDAO.findByUserId(cal3.userId))
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
                val calDAO = populateUserCalorieTable()
                //Act & Assert
                assertEquals(3, calDAO.getAll().size)
                calDAO.delete(4)
                assertEquals(3, calDAO.getAll().size)
            }
        }

        @Test
        fun `deleting an existing user in table results in record being deleted`() {
            transaction {

                //Arrange - create and populate table with three users
                val userDAO = populateUserTable()
                val calDAO = populateUserCalorieTable()
                //Act & Assert
                assertEquals(3, calDAO.getAll().size)
                calDAO.delete(cal3.userId)
                assertEquals(2, calDAO.getAll().size)
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
                val calDAO = populateUserCalorieTable()

                //Act & Assert
                val user3Updated = Calorie(3, 76F, 1.9F,1.6F, 5.6296F  ,3)
                calDAO.update(cal3.userId, user3Updated)
                assertEquals(user3Updated, calDAO.findByUserId(3))
            }
        }

        @Test
        fun `updating non-existant user in table results in no updates`() {
            transaction {

                //Arrange - create and populate table with three users
                val userDAO = populateUserTable()
                val calDAO = populateUserCalorieTable()

                //Act & Assert
                val user4Updated = Calorie(3, 76F, 1.9F,1.6F, 5.6296F  ,4)
                calDAO.update(4, user4Updated)
                assertEquals(null, calDAO.findByUserId(4))
                assertEquals(3, calDAO.getAll().size)
            }
        }
    }
}