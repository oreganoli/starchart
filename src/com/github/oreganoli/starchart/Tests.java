package com.github.oreganoli.starchart;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Tests {
    private static Constellation constellation = new Constellation("Probātiōnis");
    private static double valid_temp = 5672.9132;
    private static double valid_dist = 4.22;
    private static double valid_mass = 40.73;
    private static double valid_magnitude = 4.53;
    private static Declination valid_declination = new Declination(56, 3, 21);
    private static RightAscension valid_ascension = new RightAscension(23, 59, 32);


    @Test
    void test_right_ascension() {
        assertThrows(IllegalArgumentException.class, () -> {
            var asc = new RightAscension(25, 0, 32);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            var asc = new RightAscension(12, 64, 32);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            var asc = new RightAscension(0, 0, -8);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            var asc = new RightAscension(24, 1, 2);
        });
        assertEquals(valid_ascension.toString(), "23 h 59 m 32 s");
    }

    @Test
    void test_declination() {
        assertThrows(IllegalArgumentException.class, () -> new Declination(-90, 0, 2));
        assertThrows(IllegalArgumentException.class, () -> new Declination(-20, 60, 2));
        assertThrows(IllegalArgumentException.class, () -> new Declination(34, -2, 2));
        var valid1 = new Declination(-89, 23, 12);
        var valid2 = new Declination(0, 0, 0);
        var valid3 = new Declination(0, 0, 43);
        assertEquals(valid1.hemisphere(), Star.Hemisphere.Southern);
        assertEquals(valid1.toString(), "-89°23\'12\"");
        assertEquals(valid2.hemisphere(), Star.Hemisphere.Equatorial);
        assertEquals(valid3.hemisphere(), Star.Hemisphere.Northern);
    }


    @Test
    void test_apparent_magnitude() {
        assertThrows(IllegalArgumentException.class, () -> constellation.add_star("MAG0001", valid_temp, valid_dist, valid_mass, valid_declination, valid_ascension, -424.2));
        assertThrows(IllegalArgumentException.class, () -> constellation.add_star("MAG0002", valid_temp, valid_dist, valid_mass, valid_declination, valid_ascension, 24));
        constellation.add_star("MAG0003", valid_temp, valid_dist, valid_mass, valid_declination, valid_ascension, valid_magnitude);
    }
    @Test
    void test_star_distance() {
        assertThrows(IllegalArgumentException.class, () -> constellation.add_star("DST0001", valid_temp, 0.0, valid_mass, valid_declination, valid_ascension, valid_magnitude));
        assertThrows(IllegalArgumentException.class, () -> constellation.add_star("DST0002", valid_temp, -90.0, valid_mass, valid_declination, valid_ascension, valid_magnitude));
        constellation.add_star("DST0003", valid_temp, valid_dist, valid_mass, valid_declination, valid_ascension, valid_magnitude);
    }
    @Test
    void test_star_mass() {
        assertThrows(IllegalArgumentException.class, () -> constellation.add_star("MSS0001", valid_temp, valid_dist, 0.05, valid_declination, valid_ascension, valid_magnitude));
        assertThrows(IllegalArgumentException.class, () -> constellation.add_star("MSS0002", valid_temp, valid_dist, 90.23, valid_declination, valid_ascension, valid_magnitude));
        constellation.add_star("MSS0003", valid_temp, valid_dist, valid_mass, valid_declination, valid_ascension, valid_magnitude);
    }

    @Test
    void test_star_names() {
        assertThrows(IllegalArgumentException.class, () -> constellation.add_star("INVALID", valid_temp, valid_dist, valid_mass, valid_declination, valid_ascension, valid_magnitude));
        constellation.add_star("NAM0001", valid_temp, valid_dist, valid_mass, valid_declination, valid_ascension, valid_magnitude);
    }

    @Test
    void test_star_temperature() {
        assertThrows(IllegalArgumentException.class, () -> constellation.add_star("TMP0001", 80.0, valid_dist, valid_mass, valid_declination, valid_ascension, valid_magnitude));
        constellation.add_star("TMP0002", valid_temp, valid_dist, valid_mass, valid_declination, valid_ascension, valid_magnitude);
    }

    @Test
    void test_catalog_naming() {
        constellation.add_star("CAT0001", valid_temp, valid_dist, valid_mass, valid_declination, valid_ascension, valid_magnitude);
        constellation.add_star("CAT0002", valid_temp, valid_dist, valid_mass, valid_declination, valid_ascension, valid_magnitude);
        assertEquals(constellation.stars()[1].catalog_name(), "Beta Probātiōnis");
        assertEquals(constellation.stars()[1].constellation(), "Probātiōnis");
    }
}
