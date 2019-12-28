package com.github.oreganoli.starchart;

// Is an ugly, inefficient hack.
// Exists to get around the fact that Gson uses the default constructor.
public class StarExceptionThrower {
    public static void validate(Star star) throws IllegalArgumentException {
        var decl = new Declination(star.declination.degrees, star.declination.minutes, star.declination.seconds);
        var asc = new RightAscension(star.right_ascension.hours, star.right_ascension.minutes, star.right_ascension.hours);
        var thrower = new Star(star.id, star.name, star.constellation, star.catalog_name, star.temperature, star.distance, star.mass, star.declination, star.right_ascension, star.apparent_magnitude);
    }
}
