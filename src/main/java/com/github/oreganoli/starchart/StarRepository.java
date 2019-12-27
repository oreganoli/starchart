package com.github.oreganoli.starchart;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class StarRepository {
    Connection conn;
    public StarRepository() throws Exception {
        var dbUrl = System.getenv("STARCHART_DB_URL");
        if (dbUrl == null) {
            throw new Exception("The STARCHART_DB_URL environment variable must be set.");
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbUrl);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        this.conn = conn;
    }

    public void initialize() throws Exception {
        var initStatement = conn.prepareStatement("""
                    CREATE TABLE IF NOT EXISTS stars (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(7) NOT NULL UNIQUE CHECK (name ~ '[A-Z]{3}[0-9]{4}'),
                        catalog_name VARCHAR DEFAULT NULL,
                        constellation VARCHAR NOT NULL,
                        decl_degs INTEGER NOT NULL CHECK(decl_degs BETWEEN -90 AND 90),
                        decl_mins INTEGER NOT NULL CHECK(decl_mins BETWEEN -59 AND 59),
                        decl_secs INTEGER NOT NULL CHECK(decl_secs BETWEEN -59 AND 59),
                        ra_hrs INTEGER NOT NULL CHECK(ra_hrs BETWEEN 0 AND 24),
                        ra_mins INTEGER NOT NULL CHECK(ra_mins BETWEEN 0 AND 59),
                        ra_secs INTEGER NOT NULL CHECK(decl_mins BETWEEN 0 AND 59),
                        distance_ly REAL NOT NULL CHECK(distance_ly > 0),
                        apparent_magnitude REAL NOT NULL CHECK (apparent_magnitude BETWEEN -26.74 AND 15.00),
                        temperature_c REAL NOT NULL CHECK (temperature_c >= 2000),
                        mass REAL CHECK (mass BETWEEN 0.1 AND 50)
                    );
                """);
        initStatement.execute();
    }
}
