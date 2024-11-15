package ie.setu.config

import org.jetbrains.exposed.sql.Database
import org.postgresql.util.PSQLException

class DbConfig {

    private lateinit var dbConfig: Database

    fun getDbConnection(): Database {

        val PGHOST = "dpg-csrmqtpu0jms73932pm0-a.frankfurt-postgres.render.com"
        val PGPORT = "5432"
        val PGUSER = "healthtracker_oquv_user"
        val PGPASSWORD = "wuOyDFqka1ZOQYBkNmMtfzOBbzbo4AHn"
        val PGDATABASE = "healthtracker_oquv"

        //url format should be jdbc:postgresql://host:port/database
        val dbUrl = "jdbc:postgresql://$PGHOST:$PGPORT/$PGDATABASE"

        try {

            dbConfig = Database.connect(
                url = dbUrl, driver = "org.postgresql.Driver",
                user = PGUSER, password = PGPASSWORD
            )

        } catch (e: PSQLException) {

        }
        return dbConfig

    }
}