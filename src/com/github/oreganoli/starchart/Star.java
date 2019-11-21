package com.github.oreganoli.starchart;

public class Star {
    private Declination declination;
    private static final double MIN_TEMPERATURE = 2000.0;
    private String name;
    private String catalog_name;
    private double temperature;
    private Hemisphere hemisphere;

    Star(String name, double temperature, Declination declination, Constellation.ConstellationHandle handle) {
        set_name(name);
        set_temperature(temperature);
        set_declination(declination);
    }
    private Constellation constellation;

    public Declination declination() {
        return declination;
    }

    public void set_declination(Declination declination) {
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
