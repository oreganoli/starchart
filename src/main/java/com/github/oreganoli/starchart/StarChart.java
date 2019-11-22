package com.github.oreganoli.starchart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;

public class StarChart {
    HashMap<String, Constellation> constellations;

    void add_constellation(Constellation constellation) {
        constellations.put(constellation.getName(), constellation);
    }

    Star[] all_stars() {
        throw new UnsupportedOperationException();
    }

    private Star[] find_stars(Predicate<Star> criterion) {
        var stars = new ArrayList<Star>();
        for (var each : constellations.values()) {
            for (var star : each.stars()) {
                if (criterion.test(star)) {
                    stars.add(star);
                }
            }
        }
        var output = new Star[stars.size()];
        return stars.toArray(output);
    }

    Star[] find_stars_in_constellation(String constellation_name) {
        throw new UnsupportedOperationException();
    }

    Star[] find_stars_by_distance(double parsecs) {
        throw new UnsupportedOperationException();
    }

    Star[] find_stars_by_temperature(double min, double max) {
        throw new UnsupportedOperationException();
    }

    Star[] find_stars_by_abs_magnitude(double min, double max) {
        throw new UnsupportedOperationException();
    }

    Star[] find_stars_by_app_magnitude(double min, double max) {
        throw new UnsupportedOperationException();
    }

    Star[] find_northern_stars() {
        throw new UnsupportedOperationException();
    }

    Star[] find_southern_stars() {
        throw new UnsupportedOperationException();
    }

    Star[] find_potential_supernovae() {
        throw new UnsupportedOperationException();
    }
}
