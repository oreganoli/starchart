package com.github.oreganoli.starchart;
import static com.github.oreganoli.starchart.Constants.*;

public class Star {
    // The primary key in the corresponding DB table - left null when inserting a new star.
    Integer id;
    // The astronomical name of the star.
    String name;
    // What constellation the star belongs to. It is recommended to use the genitive case of the constellation's Latin name.
    String constellation;
    // The Bayer designation of the star within its constellation using a Greek letter, for example "Alpha Ceti" for the brightest star in the Cetus constellation.
    String catalog_name;
    // The north-south coordinate on the celestial sphere, measured in degrees, ranging from -90° to 90°
    Declination declination;
    // The east-west coordinate, measured in hours, ranging from 0 to 24 hours.
    RightAscension right_ascension;
    // Calculated based on the declination.
    Hemisphere hemisphere;
    // How bright the star appears to be from Earth.
    double apparent_magnitude;
    // Calculated based on the apparent magnitude and distance.
    double absolute_magnitude;
    // Temperature in degrees centigrade.
    double temperature;
    // Distance from the Solar System, measured in light years.
    private double distance;
    // Mass relative to the Sun.
    private double mass;

    Star(Integer id, String name, String constellation, String catalog_name, double temperature, double distance, double mass, Declination declination, RightAscension ascension, double apparent_magnitude) {
        this.id = id;
        set_name(name);
        this.constellation = constellation;
        this.catalog_name = catalog_name;
        set_temperature(temperature);
        set_distance(distance);
        set_mass(mass);
        set_declination(declination);
        this.right_ascension = ascension;
        set_apparent_magnitude(apparent_magnitude);
    }

    // Helper method for converting light years to parsecs.
    public static double ly_to_pc(double ly) {
        return ly / LY_TO_PC;
    }

    private void calculate_absolute_magnitude() {
        absolute_magnitude = apparent_magnitude - 5 * Math.log10(ly_to_pc(distance)) + 5;
    }

    void set_apparent_magnitude(double magnitude) {
        if (magnitude < MIN_APPARENT_MAGNITUDE || magnitude > MAX_APPARENT_MAGNITUDE) {
            throw new IllegalArgumentException("Stars are assumed to have an apparent magnitude between -26.74 and 15.");
        } else {
            apparent_magnitude = magnitude;
            calculate_absolute_magnitude();
        }
    }
    void set_distance(double distance) {
        if (distance <= 0) {
            throw new IllegalArgumentException("A star must be at least some distance away from the observer.");
        } else {
            this.distance = distance;
            calculate_absolute_magnitude();
        }
    }
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

    public void set_name(String name) {
        var new_name = name.toUpperCase();
        if (new_name.matches("[A-Z]{3}[0-9]{4}")) {
            this.name = new_name;
        } else {
            throw new IllegalArgumentException("Star names must be 3 Latin letters followed by 4 digits.");
        }
    }

    public enum Hemisphere {
        Northern,
        Southern,
        Equatorial
    }
}
