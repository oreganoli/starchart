package com.github.oreganoli.starchart;

public class Star {
    private static final double SOUTH_DECLINATION = -90.0;
    private static final double NORTH_DECLINATION = +90.0;
    private static final double MIN_TEMPERATURE = 2000.0;
    private String name;
    private String catalog_name;
    private Constellation constellation;

    public String name() {
        return name;
    }

    Star(String name, Constellation.ConstellationHandle handle) {
        set_name(name);
    }

    public String catalog_name() {
        return catalog_name;
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
