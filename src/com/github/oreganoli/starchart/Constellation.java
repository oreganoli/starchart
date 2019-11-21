package com.github.oreganoli.starchart;

import java.util.ArrayList;

class Constellation {
    private static final ConstellationHandle handle = new ConstellationHandle();
    private String name;
    private ArrayList<Star> stars;

    Constellation(String name) {
        this.name = name;
        stars = new ArrayList<>();
    }

    String getName() {
        return name;
    }

    void add_star(String name, double temperature, double distance, double mass, Declination declination, RightAscension ascension) {
        stars.add(new Star(this, name, temperature, distance, mass, declination, ascension, handle));
        rename_stars();
    }

    Star[] stars() {
        var arr = new Star[stars.size()];
        return stars.toArray(arr);
    }

    private void rename_stars() {
        for (int i = 0; i < stars.size(); i++) {
            stars.get(i).set_catalog_name(
                    String.format("%s %s", Greek.ALPHABET[i], this.name),
                    handle
            );
        }
    }


    // The following is a dirty hack to make sure only Constellations get to do certain things.
    static final class ConstellationHandle {
        private ConstellationHandle() {
        }
    }
}
