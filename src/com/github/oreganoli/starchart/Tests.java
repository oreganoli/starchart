package com.github.oreganoli.starchart;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Tests {
    @Test
    void test_right_ascension() {
        var invalid = "Attempted to create an invalid right ascension";
        var too_big = "Attempted to create a right ascension of more than 24 hours";
        assertThrows(IllegalArgumentException.class, () -> {
            var asc = new RightAscension(25, 0, 32);
        }, invalid);
        assertThrows(IllegalArgumentException.class, () -> {
            var asc = new RightAscension(12, 64, 32);
        }, invalid);
        assertThrows(IllegalArgumentException.class, () -> {
            var asc = new RightAscension(0, 0, -8);
        }, invalid);
        assertThrows(IllegalArgumentException.class, () -> {
            var asc = new RightAscension(24, 1, 2);
        }, too_big);
        var asc1 = new RightAscension(24, 0, 0);
        var asc2 = new RightAscension(23, 59, 60);
        assertEquals(asc1.toString(), asc2.toString());
    }

    @Test
    void test_star_names() {
        var constellation = new Constellation("Test");
        assertThrows(IllegalArgumentException.class, () -> constellation.add_star("INVALID"));
        constellation.add_star("VAL1300");
    }

    @Test
    void test_catalog_naming() {
        var constellation = new Constellation("Test");
        constellation.add_star("ADX0817");
        constellation.add_star("TST1337");
        assertEquals(constellation.stars()[1].catalog_name(), "Beta Test");
    }
}
