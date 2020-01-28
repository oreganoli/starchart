package com.github.oreganoli.starchart;

/** A set of criteria to search by. Note that reference types are used in all fields to make them nullable. If a field is null, that criterion is ignored in the search. */
public class Criteria {
    /** What constellation to look in. */
    public String constellation;
    /** An inclusive range of distances in parsecs. */
    public Range<Double> distance_parsecs;
    /** An inclusive range of temperatures. */
    public Range<Double> temperature;
    /** An inclusive range of apparent magnitudes. */
    public Range<Double> apparent_magnitude;
    /** Which hemisphere the stars can be on. */
    public Star.Hemisphere hemisphere;
    /** Whether or not the stars are potential supernovae; ignored if null */
    public Boolean potential_supernovae;

    @Override
    public String toString() {
        return "Criteria{" +
               "constellation='" + constellation + '\'' +
               ", distance_parsecs=" + distance_parsecs +
               ", temperature=" + temperature +
               ", apparent_magnitude=" + apparent_magnitude +
               ", hemisphere=" + hemisphere +
               ", potential_supernovae=" + potential_supernovae +
               '}';
    }

    public Criteria(String constellation, Range<Double> distance_parsecs, Range<Double> temperature, Range<Double> apparent_magnitude, Star.Hemisphere hemisphere, Boolean potential_supernovae) {
        this.constellation = constellation;
        this.distance_parsecs = distance_parsecs;
        this.temperature = temperature;
        this.apparent_magnitude = apparent_magnitude;
        this.hemisphere = hemisphere;
        this.potential_supernovae = potential_supernovae;
    }
}

