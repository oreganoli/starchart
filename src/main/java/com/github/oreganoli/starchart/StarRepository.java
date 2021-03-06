package com.github.oreganoli.starchart;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


@SuppressWarnings("DuplicatedCode")
/** This is the data access object of the application. */
public class StarRepository {
    /** Hikari connection pool. */
    HikariDataSource source;
    public StarRepository() throws Exception {
        var dbUrl = System.getenv("STARCHART_DB_URL");
        if (dbUrl == null) {
            throw new Exception("The STARCHART_DB_URL environment variable must be set.");
        }
        System.out.println("Acquired database URL...");
        var config = new HikariConfig();
        config.setJdbcUrl(dbUrl);
        config.setMaximumPoolSize(20);
        config.setMinimumIdle(10);
        System.out.println("Created a Hikari config.");
        source = new HikariDataSource(config);
        initialize();
    }

    private Connection conn() throws SQLException {
        return source.getConnection();
    }

    /** Sets up the DB table. */
    public void initialize() throws Exception {
        var conn = conn();
        var initStatement = conn.prepareStatement("    CREATE TABLE IF NOT EXISTS stars (\n" +
                                                  "        id SERIAL PRIMARY KEY,\n" +
                                                  "        name VARCHAR(7) NOT NULL UNIQUE CHECK (name ~ '[A-Z]{3}[0-9]{4}'),\n" +
                                                  "        catalog_name VARCHAR DEFAULT NULL,\n" +
                                                  "        constellation VARCHAR NOT NULL,\n" +
                                                  "        decl_degs INTEGER NOT NULL,\n" +
                                                  "        decl_mins INTEGER NOT NULL,\n" +
                                                  "        decl_secs INTEGER NOT NULL,\n" +
                                                  "        ra_hrs INTEGER NOT NULL,\n" +
                                                  "        ra_mins INTEGER NOT NULL,\n" +
                                                  "        ra_secs INTEGER NOT NULL,\n" +
                                                  "        distance_ly DOUBLE PRECISION NOT NULL CHECK(distance_ly > 0),\n" +
                                                  "        apparent_magnitude DOUBLE PRECISION NOT NULL CHECK (apparent_magnitude BETWEEN -26.74 AND 15.00),\n" +
                                                  "        temperature_c DOUBLE PRECISION NOT NULL CHECK (temperature_c >= 2000),\n" +
                                                  "        mass DOUBLE PRECISION NOT NULL CHECK (mass BETWEEN 0.1 AND 50)\n" +
                                                  "    );\n");
        initStatement.execute();
        conn.close();
    }
    
    private int create(Star star) throws Exception {
        var conn = conn();
        var unq = conn.prepareStatement("SELECT EXISTS (SELECT id FROM stars WHERE name = ?);");
        unq.setString(1, star.name);
        var urs = unq.executeQuery();
        if (urs.next() && urs.getBoolean(1)) {
            throw new AlreadyExistsException("This name is already taken by another star.");
        }
        var ins = conn.prepareStatement("INSERT INTO stars\n" +
                                        "(name, constellation, decl_degs, decl_mins, decl_secs, ra_hrs, ra_mins, ra_secs, distance_ly, apparent_magnitude, temperature_c, mass)\n" +
                                        "VALUES\n" +
                                        "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)\n" +
                                        "RETURNING id;\n");
        ins.setString(1, star.name);
        ins.setString(2, star.constellation);
        ins.setInt(3, star.declination.degrees);
        ins.setInt(4, star.declination.minutes);
        ins.setInt(5, star.declination.seconds);
        ins.setInt(6, star.right_ascension.hours);
        ins.setInt(7, star.right_ascension.minutes);
        ins.setInt(8, star.right_ascension.seconds);
        ins.setDouble(9, star.distance);
        ins.setDouble(10, star.apparent_magnitude);
        ins.setDouble(11, star.temperature);
        ins.setDouble(12, star.mass);
        var idr = ins.executeQuery();
        int id = 0;
        while (idr.next()) {
            id = idr.getInt(1);
        }
        conn.close();
        return id;
    }

    /**
     * Reads a particular star.
     * @param id ID of the star to get.
     * @return Star data; null if not found
     * @throws Exception on DB errors.
     */
    public Star read(int id) throws Exception {
        var conn = conn();
        var stmt = conn.prepareStatement("SELECT\n" +
                                         "id, name, constellation, catalog_name,\n" +
                                         "decl_degs, decl_mins, decl_secs,\n" +
                                         "ra_hrs, ra_mins, ra_secs,\n" +
                                         "distance_ly, apparent_magnitude, temperature_c, mass\n" +
                                         "FROM stars\n" +
                                         "WHERE id = ?;");
        stmt.setInt(1, id);
        var rs = stmt.executeQuery();
        if (rs.next()) {
            var ret = new Star(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getDouble(13),
                    rs.getDouble(11),
                    rs.getDouble(14),
                    new Declination(rs.getInt(5), rs.getInt(6), rs.getInt(7)),
                    new RightAscension(rs.getInt(8), rs.getInt(9), rs.getInt(10)),
                    rs.getDouble(12));
            conn.close();
            return ret;
        } else {
            conn.close();
            return null;
        }
    }
    /**
     * Reads all the stars.
     * @return List of stars
     * @throws Exception on DB errors.
     */
    public ArrayList<Star> read_all() throws Exception {
        var conn = conn();
        var read_stmt = conn.prepareStatement("SELECT\n" +
                                              "id, name, constellation, catalog_name,\n" +
                                              "decl_degs, decl_mins, decl_secs,\n" +
                                              "ra_hrs, ra_mins, ra_secs,\n" +
                                              "distance_ly, apparent_magnitude, temperature_c, mass\n" +
                                              "FROM stars\n" +
                                              "ORDER BY constellation;\n");
        var list = new ArrayList<Star>();
        var rs = read_stmt.executeQuery();
        while (rs.next()) {
            list.add(new Star(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getDouble(13),
                    rs.getDouble(11),
                    rs.getDouble(14),
                    new Declination(rs.getInt(5), rs.getInt(6), rs.getInt(7)),
                    new RightAscension(rs.getInt(8), rs.getInt(9), rs.getInt(10)),
                    rs.getDouble(12)
            ));
        }
        conn.close();
        return list;
    }

    private int update(Star star) throws Exception {
        var conn = conn();
        var ins = conn.prepareStatement("UPDATE stars SET\n" +
                                        "name = ?,\n" +
                                        "constellation = ?,\n" +
                                        "decl_degs = ?,\n" +
                                        "decl_mins = ?,\n" +
                                        "decl_secs = ?,\n" +
                                        "ra_hrs = ?,\n" +
                                        "ra_mins = ?,\n" +
                                        "ra_secs = ?,\n" +
                                        "distance_ly = ?,\n" +
                                        "apparent_magnitude = ?,\n" +
                                        "temperature_c = ?,\n" +
                                        "mass = ?\n" +
                                        "WHERE id = ?;\n");
        ins.setString(1, star.name);
        ins.setString(2, star.constellation);
        ins.setInt(3, star.declination.degrees);
        ins.setInt(4, star.declination.minutes);
        ins.setInt(5, star.declination.seconds);
        ins.setInt(6, star.right_ascension.hours);
        ins.setInt(7, star.right_ascension.minutes);
        ins.setInt(8, star.right_ascension.seconds);
        ins.setDouble(9, star.distance);
        ins.setDouble(10, star.apparent_magnitude);
        ins.setDouble(11, star.temperature);
        ins.setDouble(12, star.mass);
        ins.setInt(13, star.id);
        ins.execute();
        conn.close();
        return star.id;
    }

    /**
     * This is where new stars are saved or existing ones are modified.
     * Internally, this method decides between actual update and insert methods depending on the star's ID field.
     * A null ID results in an insert, a non-null one in an update.
     * @param star Star to insert or update.
     * @return The id of the newly created or modified star.
     * @throws Exception on DB errors.
     */
    public int upsert(Star star) throws Exception {
        StarExceptionThrower.validate(star);
        int id;
        if (star.id == null) {
            id = create(star);
        } else {
            id = update(star);
        }
        rename_stars();
        return id;
    }
    /**
     * Deletes a star. Idempotent - will not throw on an already-nonexistent star.
     * @param id ID of the star to delete.
     * @throws Exception on DB errors.
     */
    public void delete(int id) throws Exception {
        var conn = conn();
        var stmt = conn.prepareStatement("DELETE FROM stars WHERE id = ?;\n");
        stmt.setInt(1, id);
        stmt.execute();
        rename_stars();
        conn.close();
    }
    /**
     * Called internally after updates and inserts. Sorts stars by apparent magnitude and hands out new Bayer designations appropriately.
     * @throws Exception on DB errors.
     */
    public void rename_stars() throws Exception {
        var map = new HashMap<String, ArrayList<Integer>>();
        var conn = conn();
        var consts_rs = conn.prepareStatement("SELECT DISTINCT constellation FROM stars;").executeQuery();
        while (consts_rs.next()) {
            map.put(consts_rs.getString(1), new ArrayList<>());
        }
        var read_rs = conn.prepareStatement("SELECT id, constellation\n" +
                                            "FROM stars\n" +
                                            "ORDER BY apparent_magnitude;\n").executeQuery();
        while (read_rs.next()) {
            map.get(read_rs.getString(2)).add(read_rs.getInt(1));
        }
        var set_stmt = conn.prepareStatement("UPDATE stars SET catalog_name = ? WHERE id = ?;");
        for (String x : map.keySet()) {
            var len = map.get(x).size();
            for (int i = 0; i < len; i++) {
                set_stmt.setString(1, Bayer.designation(i, x));
                set_stmt.setInt(2, map.get(x).get(i));
                set_stmt.execute();
            }
        }
        conn.close();
    }

    /**
     * Searches for stars matching the given criteria.
     * @param criteria The search criteria, see the Criteria type.
     * @return List of stars matching the given criteria.
     * @throws Exception on DB errors.
     */
    public ArrayList<Star> search(Criteria criteria) throws Exception {
        if (criteria == null) {
            return read_all();
        }
        var conn = conn();
        var query = conn.prepareStatement("SELECT\n" +
                                          "id, name, constellation, catalog_name,\n" +
                                          "decl_degs, decl_mins, decl_secs,\n" +
                                          "ra_hrs, ra_mins, ra_secs,\n" +
                                          "distance_ly, apparent_magnitude, temperature_c, mass\n" +
                                          "FROM stars\n" +
                                          "WHERE\n" +
                                          "constellation LIKE ? AND\n" +
                                          "distance_ly BETWEEN ? AND ? AND\n" +
                                          "temperature_c BETWEEN ? AND ? AND\n" +
                                          "apparent_magnitude BETWEEN ? AND ? AND\n" +
                                          "mass BETWEEN ? AND ?\n" +
                                          "ORDER BY constellation;\n");
        // If a particular criterion is null and thus irrelevant, then the sides of the BETWEEN clause are filled with the minimum and maximum legal values, making it meaningless.
        // This lets us avoid dynamically composing the query.
        // Unfortunately, filtering by hemisphere has to be done after retrieving the data, as it isn't an atomic scalar value we could do this with.
        query.setString(1, Objects.requireNonNullElse(criteria.constellation, "%"));
        if (criteria.distance_parsecs != null) {
            query.setDouble(2, Star.pc_to_ly(criteria.distance_parsecs.min));
            query.setDouble(3, Star.pc_to_ly(criteria.distance_parsecs.max));
        } else {
            query.setDouble(2, 0);
            query.setDouble(3, Double.MAX_VALUE);
        }
        if (criteria.temperature != null) {
            query.setDouble(4, criteria.temperature.min);
            query.setDouble(5, criteria.temperature.max);
        } else {
            query.setDouble(4, Constants.MIN_TEMPERATURE);
            query.setDouble(5, Double.MAX_VALUE);
        }
        if (criteria.apparent_magnitude != null) {
            query.setDouble(6, criteria.apparent_magnitude.min);
            query.setDouble(7, criteria.apparent_magnitude.max);
        } else {
            query.setDouble(6, Constants.MIN_APPARENT_MAGNITUDE);
            query.setDouble(7, Constants.MAX_APPARENT_MAGNITUDE);
        }
        if (criteria.potential_supernovae != null) {
            if (criteria.potential_supernovae) {
                query.setDouble(8, Constants.CHANDRASEKHAR_LIMIT);
                query.setDouble(9, Constants.MAX_MASS);
            } else {
                query.setDouble(8, Constants.MIN_MASS);
                query.setDouble(9, Constants.CHANDRASEKHAR_LIMIT);
                // We conveniently assume here that stars right on the Chandrasekhar limit could be potential supernovae or not. This lets us do our "clever" BETWEEN trick in the SQL query.
            }
        } else {
            query.setDouble(8, Constants.MIN_MASS);
            query.setDouble(9, Constants.MAX_MASS);
        }
        var list = new ArrayList<Star>();
        var rs = query.executeQuery();
        while (rs.next()) {
            list.add(new Star(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getDouble(13),
                    rs.getDouble(11),
                    rs.getDouble(14),
                    new Declination(rs.getInt(5), rs.getInt(6), rs.getInt(7)),
                    new RightAscension(rs.getInt(8), rs.getInt(9), rs.getInt(10)),
                    rs.getDouble(12)
            ));
        }
        if (criteria.hemisphere != null) {
            list.removeIf((x) -> x.declination.hemisphere() != criteria.hemisphere);
        }
        conn.close();
        return list;
    }
}
