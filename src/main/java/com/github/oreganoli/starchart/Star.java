package com.github.oreganoli.starchart;
import static com.github.oreganoli.starchart.Constants.*;

/** The central data type of starchart. */
public class Star {
    /** The primary key in the corresponding DB table - left null when inserting a new star. */
    public Integer id;
    /** The astronomical name of the star. */
    public String name;
    /** What constellation the star belongs to. It is recommended to use the genitive case of the constellation's Latin name. */
    public String constellation;
    /** Calculated based on the apparent magnitude and distance. */
    public double absolute_magnitude;
    /** The north-south coordinate on the celestial sphere, measured in degrees, ranging from -90° to 90° */
    public Declination declination;
    /** The east-west coordinate, measured in hours, ranging from 0 to 24 hours. */
    public RightAscension right_ascension;
    /** Calculated based on the declination. */
    public Hemisphere hemisphere;
    /** How bright the star appears to be from Earth. */
    public double apparent_magnitude;
    /** The Bayer designation of the star within its constellation using a Greek letter, for example "Alpha Ceti" for the brightest star in the Cetus constellation.
    * This is ignored on inserts and updates, like "id". */
    public String catalog_name;
    /** Temperature in degrees centigrade. */
    public double temperature;
    /** Distance from the Solar System, measured in light years. */
    public double distance;
    /** Mass relative to the Sun. */
    public double mass;

    public Star(Integer id, String name, String constellation, String catalog_name, double temperature, double distance, double mass, Declination declination, RightAscension ascension, double apparent_magnitude) {
        this.id = id;
        set_name(name);
        if (constellation == null || constellation.isEmpty()) {
            throw new IllegalArgumentException("Stars must belong to a constellation.");
        }
        this.constellation = constellation;
        this.catalog_name = catalog_name;
        set_temperature(temperature);
        set_distance(distance);
        set_mass(mass);
        set_declination(declination);
        this.right_ascension = ascension;
        set_apparent_magnitude(apparent_magnitude);
    }

    @Override
    public String toString() {
        return "Star{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", constellation='" + constellation + '\'' +
               ", absolute_magnitude=" + absolute_magnitude +
               ", declination=" + declination +
               ", right_ascension=" + right_ascension +
               ", hemisphere=" + hemisphere +
               ", apparent_magnitude=" + apparent_magnitude +
               ", catalog_name='" + catalog_name + '\'' +
               ", temperature=" + temperature +
               ", distance=" + distance +
               ", mass=" + mass +
               '}';
    }

    /**
     * Helper method for converting parsecs to light years.
     * @param pc Parsecs
     * @return Light years
     */
    public static double pc_to_ly(double pc) {
        return pc * LY_TO_PC;
    }

    /**
     * Helper method for converting light years to parsecs.
     * @param ly Light years
     * @return Parsecs
     */
    public static double ly_to_pc(double ly) {
        return ly / LY_TO_PC;
    }

    private void calculate_absolute_magnitude() {
        absolute_magnitude = apparent_magnitude - 5 * Math.log10(ly_to_pc(distance)) + 5;
    }

    /**
     * Sets the apparent magnitude and recalculates the absolute magnitude.
     * @param magnitude New apparent magnitude.
     * @throws IllegalArgumentException If magnitude does not fit within the allowable range of <-26,74; 15>.
     */
    void set_apparent_magnitude(double magnitude) {
        if (magnitude < MIN_APPARENT_MAGNITUDE || magnitude > MAX_APPARENT_MAGNITUDE) {
            throw new IllegalArgumentException("Stars are assumed to have an apparent magnitude between -26.74 and 15.");
        } else {
            apparent_magnitude = magnitude;
            calculate_absolute_magnitude();
        }
    }
    /**
     * Sets the distance and recalculates the absolute magnitude.
     * @param distance New distance.
     * @throws IllegalArgumentException If the distance is lesser than or equal to 0.
     */
    void set_distance(double distance) {
        if (distance <= 0) {
            throw new IllegalArgumentException("A star must be at least some distance away from the observer.");
        } else {
            this.distance = distance;
            calculate_absolute_magnitude();
        }
    }
    /**
     * Sets the mass.
     * @param mass The new mass.
     * @throws IllegalArgumentException If the mass isn't between 0.1 and 50 solar masses.
     */
    void set_mass(double mass) {
        if (mass < MIN_MASS || mass > MAX_MASS) {
            throw new IllegalArgumentException("Stars are assumed to have a mass between one-tenth that of the Sun to fifty times that of the Sun.");
        } else {
            this.mass = mass;
        }
    }

    void set_declination(Declination declination) {
        this.declination = declination;
        this.hemisphere = this.declination.hemisphere();
    }

    public void set_temperature(double temperature) {
        if (temperature < MIN_TEMPERATURE) {
            throw new IllegalArgumentException("Stars are assumed to be at a temperature of at least 2000°C");
        } else {
            this.temperature = temperature;
        }
    }
    /**
     * Validates and sets the name.
     * @param name New name.
     * @throws IllegalArgumentException If the name is not 3 Latin letters followed by 4 digits.
     */
    public void set_name(String name) {
        var new_name = name.toUpperCase();
        if (new_name.matches("[A-Z]{3}[0-9]{4}")) {
            this.name = new_name;
        } else {
            throw new IllegalArgumentException("Star names must be 3 Latin letters followed by 4 digits.");
        }
    }

    /** Which hemisphere a star can be found on. */
    public enum Hemisphere {
        Northern,
        Southern,
        /** Not really a hemisphere, but handles the edge case of a theoretical 0°0'0" declination. */
        Equatorial
    }
}
