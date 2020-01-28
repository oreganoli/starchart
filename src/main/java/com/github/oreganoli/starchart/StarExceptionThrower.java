package com.github.oreganoli.starchart;

/** This is an ugly, inefficient hack.
 * It exists purely because the JSON serialization library uses the default constructor and "manually" fills in the values, thus skipping the validation steps already in the constructor.
 * Writing a custom deserializer would have been too much of a hassle.
 * Do not do this in production.
 * */
public class StarExceptionThrower {
    /**
     * Checks if the given star data makes sense. This is done by calling the constructor for Star, Declination and RightAscension. Do not do this in production.
     * @param star Star to validate
     * @throws IllegalArgumentException if the star does not pass muster.
     */
    public static void validate(Star star) throws IllegalArgumentException {
        var decl = new Declination(star.declination.degrees, star.declination.minutes, star.declination.seconds);
        var asc = new RightAscension(star.right_ascension.hours, star.right_ascension.minutes, star.right_ascension.hours);
        var thrower = new Star(star.id, star.name, star.constellation, star.catalog_name, star.temperature, star.distance, star.mass, star.declination, star.right_ascension, star.apparent_magnitude);
    }
}
