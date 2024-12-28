package ie.setu.config

import org.jetbrains.exposed.sql.Database
import org.postgresql.util.PSQLException

class DbConfig {

    private lateinit var dbConfig: Database

    fun getDbConnection(): Database {

        val PGHOST = "dpg-ctnq2ndsvqrc73b36nc0-a.frankfurt-postgres.render.com"
        val PGPORT = "5432"
        val PGUSER = "health_tracker_database_user"
        val PGPASSWORD = "5SfmSOhYuQPeXMEJ1D2YAcTySyM5WLj4"
        val PGDATABASE = "health_tracker_database"

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