package com.github.oreganoli.starchart;

/** Astronomic constants used in starchart. */
public class Constants {
    /** The ratio of light years to one parsec, used for conversion. */
    static final double LY_TO_PC = 3.26;
    /** The minimum allowable apparent magnitude - that of the Sun. */
    static final double MIN_APPARENT_MAGNITUDE = -26.74;
    /** The minimum allowable temperature for stars. */
    static final double MIN_TEMPERATURE = 2000.0;
    /** The minimum allowable mass for stars - 1/10th that of the Sun. */
    static final double MIN_MASS = 0.1;
    /** The maximum allowable mass for stars - 50 times that of the Sun. */
    static final double MAX_MASS = 50.0;
    /**The maximum apparent magnitude for stars. */
    static final double MAX_APPARENT_MAGNITUDE = 15.00;
    /** A magic number used for checking if a star is a potential supernova - its mass must be at least this big. */
    static final double CHANDRASEKHAR_LIMIT = 1.44;
}
