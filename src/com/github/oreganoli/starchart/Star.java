package com.github.oreganoli.starchart;

public class Star {
    private static final double MIN_TEMPERATURE = 2000.0;
    private static final double MIN_MASS = 0.1;
    private static final double MAX_MASS = 50.0;
    private Declination declination;
    public RightAscension right_ascension;
    private String name;
    private String catalog_name;
    private Constellation constellation;
    private double temperature;
    // Distance from the Earth, measured in light years.
    private double distance;
    private Hemisphere hemisphere;
    private double mass;

    Star(Constellation constellation, String name, double temperature, double distance, double mass, Declination declination, RightAscension ascension, Constellation.ConstellationHandle handle) {
        this.constellation = constellation;
        set_name(name);
        set_temperature(temperature);
        set_distance(distance);
        set_mass(mass);
        set_declination(declination);
        this.right_ascension = ascension;
    }

    String constellation() {
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

    String catalog_name() {
        return catalog_name;
    }

    public enum Hemisphere {
        Northern,
        Southern,
        Equatorial
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
}
