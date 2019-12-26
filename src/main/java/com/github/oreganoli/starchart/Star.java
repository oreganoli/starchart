package com.github.oreganoli.starchart;
import static com.github.oreganoli.starchart.Constants.*;

public class Star {
    public RightAscension right_ascension;
    private double apparent_magnitude;
    private double absolute_magnitude;
    private Declination declination;
    private String name;
    private String catalog_name;
    private Constellation constellation;
    private double temperature;
    // Distance from the Earth, measured in light years.
    private double distance;
    private Hemisphere hemisphere;
    private double mass;

    Star(Constellation constellation, String name, double temperature, double distance, double mass, Declination declination, RightAscension ascension, double apparent_magnitude, Constellation.ConstellationHandle handle) {
        this.constellation = constellation;
        set_name(name);
        set_temperature(temperature);
        set_distance(distance);
        set_apparent_magnitude(apparent_magnitude);
        set_mass(mass);
        set_declination(declination);
        this.right_ascension = ascension;
    }

    public static double ly_to_pc(double ly) {
        return ly / LY_TO_PC;
    }

    private void calculate_absolute_magnitude() {
        absolute_magnitude = apparent_magnitude - 5 * Math.log10(ly_to_pc(distance)) + 5;
    }

    public double absolute_magnitude() {
        return absolute_magnitude;
    }

    double apparent_magnitude() {
        return apparent_magnitude;
    }

    void set_apparent_magnitude(double magnitude) {
        if (magnitude < MIN_APPARENT_MAGNITUDE || magnitude > MAX_APPARENT_MAGNITUDE) {
            throw new IllegalArgumentException("Stars are assumed to have an apparent magnitude between -26.74 and 15.");
        } else {
            apparent_magnitude = magnitude;
            calculate_absolute_magnitude();
        }
    }

    public String constellation() {
        return constellation.getName();
    }

    public double distance() {
        return distance;
    }

    void set_distance(double distance) {
        if (distance <= 0) {
            throw new IllegalArgumentException("A star must be at least some distance away from the observer.");
        } else {
            this.distance = distance;
            calculate_absolute_magnitude();
        }
    }

    public double mass() {
        return mass;
    }

    void set_mass(double mass) {
        if (mass < MIN_MASS || mass > MAX_MASS) {
            throw new IllegalArgumentException("Stars are assumed to have a mass between one-tenth that of the Sun to fifty times that of the Sun.");
        } else {
            this.mass = mass;
        }
    }

    public Declination declination() {
        return declination;
    }

    void set_declination(Declination declination) {
        this.declination = declination;
        this.hemisphere = this.declination.hemisphere();
    }

    public Hemisphere hemisphere() {
        return hemisphere;
    }

    public String name() {
        return name;
    }

    public String catalog_name() {
        return catalog_name;
    }

    public double temperature() {
        return temperature;
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

    void set_catalog_name(String name, Constellation.ConstellationHandle handle) {
        this.catalog_name = name;
    }

    public enum Hemisphere {
        Northern,
        Southern,
        Equatorial
    }
}
