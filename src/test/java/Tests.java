import com.github.oreganoli.starchart.Bayer;
import com.github.oreganoli.starchart.Declination;
import com.github.oreganoli.starchart.RightAscension;
import com.github.oreganoli.starchart.Star;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Tests {
    private static double valid_temp = 5672.9132;
    private static double valid_dist = 4.22;
    private static double valid_mass = 40.73;
    private static double valid_magnitude = 4.53;
    private static Declination valid_declination = new Declination(56, 3, 21);
    private static RightAscension valid_ascension = new RightAscension(23, 59, 32);

    @Test
    void test_right_ascension() {
        assertThrows(IllegalArgumentException.class, () -> new RightAscension(25, 0, 32));
        assertThrows(IllegalArgumentException.class, () -> new RightAscension(12, 64, 32));
        assertThrows(IllegalArgumentException.class, () -> new RightAscension(0, 0, -8));
        assertThrows(IllegalArgumentException.class, () -> new RightAscension(24, 1, 2));
        assertEquals("23 h 59 m 32 s", valid_ascension.toString());
    }

    @Test
    void test_declination() {
        assertThrows(IllegalArgumentException.class, () -> new Declination(-90, 0, 2));
        assertThrows(IllegalArgumentException.class, () -> new Declination(-20, 60, 2));
        assertThrows(IllegalArgumentException.class, () -> new Declination(34, -2, 2));
        var valid1 = new Declination(-89, 23, 12);
        var valid2 = new Declination(0, 0, 0);
        var valid3 = new Declination(0, 0, 43);
        assertEquals(valid1.toString(), "-89°23'12\"");
        assertEquals(Star.Hemisphere.Southern, valid1.hemisphere());
        assertEquals(Star.Hemisphere.Equatorial, valid2.hemisphere());
        assertEquals(Star.Hemisphere.Northern, valid3.hemisphere());
    }

    @Test
    void test_apparent_magnitude() {
        assertThrows(IllegalArgumentException.class, () -> new Star(null, "MAG0001", "of the Test", null, valid_temp, valid_dist, valid_mass, valid_declination, valid_ascension, -424.2));
        assertThrows(IllegalArgumentException.class, () -> new Star(null, "MAG0002", "of the Test", null, valid_temp, valid_dist, valid_mass, valid_declination, valid_ascension, 24));
    }

    @Test
    void test_absolute_magnitude() {
        var rigel = new Star(null, "ADS3823", "Orionis", "Beta Orionis", 11826.85, 860.0, 21, new Declination(-8, 12, 14), new RightAscension(5, 14, 32), 0.12);
        assertEquals(-7.0, Math.round(rigel.absolute_magnitude));
    }

    @Test
    void test_star_distance() {
        assertThrows(IllegalArgumentException.class, () -> new Star(null, "DST0001", null, null, valid_temp, 0.0, valid_mass, valid_declination, valid_ascension, valid_magnitude));
        assertThrows(IllegalArgumentException.class, () -> new Star(null, "DST0002", null, null, valid_temp, -90.0, valid_mass, valid_declination, valid_ascension, valid_magnitude));
    }

    @Test
    void test_star_mass() {
        assertThrows(IllegalArgumentException.class, () -> new Star(null, "MSS0001", null, null, valid_temp, valid_dist, 0.05, valid_declination, valid_ascension, valid_magnitude));
        assertThrows(IllegalArgumentException.class, () -> new Star(null, "MSS0002", null, null, valid_temp, valid_dist, 90.23, valid_declination, valid_ascension, valid_magnitude));
    }

    @Test
    void test_star_names() {
        assertThrows(IllegalArgumentException.class, () -> new Star(null, "INVALID", null, null, valid_temp, valid_dist, valid_mass, valid_declination, valid_ascension, valid_magnitude));
    }

    @Test
    void test_star_temperature() {
        assertThrows(IllegalArgumentException.class, () -> new Star(null, "TMP0001", null, null, 80.0, valid_dist, valid_mass, valid_declination, valid_ascension, valid_magnitude));
    }

    @Test
    void test_bayer_desigs() {
        assertThrows(IllegalArgumentException.class, () -> Bayer.designation(-1, "Eridani"));
        assertThrows(UnsupportedOperationException.class, () -> Bayer.designation(76, "Eridani"));
        assertEquals("Alpha Centauri", Bayer.designation(0, "Centauri"));
        assertEquals("Beta Orionis", Bayer.designation(1, "Orionis"));
        assertEquals("Z Eridani", Bayer.designation(75, "Eridani"));
    }
}
