package com.github.oreganoli.starchart;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


@SuppressWarnings("DuplicatedCode")
public class StarRepository {
    HikariDataSource source;
    public StarRepository() throws Exception {
        var dbUrl = System.getenv("STARCHART_DB_URL");
        if (dbUrl == null) {
            throw new Exception("The STARCHART_DB_URL environment variable must be set.");
        }
        System.out.println("Acquired database URL...");
        var config = new HikariConfig();
        config.setJdbcUrl(dbUrl);
        System.out.println("Created a Hikari config.");
        source = new HikariDataSource(config);
        initialize();
    }

    private Connection conn() throws SQLException {
        return source.getConnection();
    }

    public void initialize() throws Exception {
        var initStatement = conn().prepareStatement("""
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
                        distance_ly DOUBLE PRECISION NOT NULL CHECK(distance_ly > 0),
                        apparent_magnitude DOUBLE PRECISION NOT NULL CHECK (apparent_magnitude BETWEEN -26.74 AND 15.00),
                        temperature_c DOUBLE PRECISION NOT NULL CHECK (temperature_c >= 2000),
                        mass DOUBLE PRECISION NOT NULL CHECK (mass BETWEEN 0.1 AND 50)
                    );
                """);
        initStatement.execute();
    }

    private void create(Star star) throws Exception {
        var unq = conn().prepareStatement("SELECT EXISTS (SELECT id FROM stars WHERE name = ?);");
        unq.setString(1, star.name);
        var urs = unq.executeQuery();
        if (urs.next() && urs.getBoolean(1)) {
            throw new IllegalArgumentException("This name is already taken by another star.");
        }
        var ins = conn().prepareStatement("""
                INSERT INTO stars
                (name, constellation, decl_degs, decl_mins, decl_secs, ra_hrs, ra_mins, ra_secs, distance_ly, apparent_magnitude, temperature_c, mass)
                VALUES
                (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
                """);
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
        ins.execute();
    }

    public Star read(int id) throws Exception {
        var stmt = conn().prepareStatement("""
                SELECT
                id, name, constellation, catalog_name,
                decl_degs, decl_mins, decl_secs,
                ra_hrs, ra_mins, ra_secs,
                distance_ly, apparent_magnitude, temperature_c, mass
                FROM stars
                WHERE id = ?;""");
        stmt.setInt(1, id);
        var rs = stmt.executeQuery();
        if (rs.next()) {
            return new Star(
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
        } else {
            return null;
        }
    }

    public ArrayList<Star> read_all() throws Exception {
        var read_stmt = conn().prepareStatement("""
                SELECT
                id, name, constellation, catalog_name,
                decl_degs, decl_mins, decl_secs,
                ra_hrs, ra_mins, ra_secs,
                distance_ly, apparent_magnitude, temperature_c, mass
                FROM stars
                ORDER BY id;
                """);
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
        return list;
    }

    private void update(Star star) throws Exception {
        var ins = conn().prepareStatement("""
                UPDATE stars SET
                name = ?,
                constellation = ?,
                decl_degs = ?,
                decl_mins = ?,
                decl_secs = ?,
                ra_hrs = ?,
                ra_mins = ?,
                ra_secs = ?,
                distance_ly = ?,
                apparent_magnitude = ?,
                temperature_c = ?,
                mass = ?
                WHERE id = ?;
                """);
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
    }

    public void upsert(Star star) throws Exception {
        if (star.id == null) {
            create(star);
        } else {
            update(star);
        }
        rename_stars();
    }

    public void delete(int id) throws Exception {
        var stmt = conn().prepareStatement("""
                DELETE FROM stars WHERE id = ?;
                """);
        stmt.setInt(1, id);
        stmt.execute();
        rename_stars();
    }

    public void rename_stars() throws Exception {
        var map = new HashMap<String, ArrayList<Integer>>();
        var consts_rs = conn().prepareStatement("SELECT DISTINCT constellation FROM stars;").executeQuery();
        while (consts_rs.next()) {
            map.put(consts_rs.getString(1), new ArrayList<>());
        }
        var read_rs = conn().prepareStatement("""
                SELECT id, constellation
                FROM stars
                ORDER BY apparent_magnitude;
                """).executeQuery();
        while (read_rs.next()) {
            map.get(read_rs.getString(2)).add(read_rs.getInt(1));
        }
        var set_stmt = conn().prepareStatement("UPDATE stars SET catalog_name = ? WHERE id = ?;");
        for (String x : map.keySet()) {
            var len = map.get(x).size();
            for (int i = 0; i < len; i++) {
                set_stmt.setString(1, Bayer.designation(i, x));
                set_stmt.setInt(2, map.get(x).get(i));
                set_stmt.execute();
            }
        }
    }

    public ArrayList<Star> search(Criteria criteria) throws Exception {
        if (criteria == null) {
            return read_all();
        }
        var query = conn().prepareStatement("""
                SELECT
                id, name, constellation, catalog_name,
                decl_degs, decl_mins, decl_secs,
                ra_hrs, ra_mins, ra_secs,
                distance_ly, apparent_magnitude, temperature_c, mass
                FROM stars
                WHERE
                constellation LIKE ? AND
                distance_ly BETWEEN ? AND ? AND
                temperature_c BETWEEN ? AND ? AND
                apparent_magnitude BETWEEN ? AND ? AND
                mass BETWEEN ? AND ?
                ORDER BY id;
                """);
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
        return list;
    }
}
