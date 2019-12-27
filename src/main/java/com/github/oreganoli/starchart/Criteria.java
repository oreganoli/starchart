package com.github.oreganoli.starchart;

// A set of criteria to search by. Note that reference types are used in all fields to make them nullable. If a field is null, that criterion is ignored in the search.
public class Criteria {
    public String constellation;
    public Range<Double> distance_parsecs;
    public Range<Double> temperature;
    public Range<Double> apparent_magnitude;
    public Star.Hemisphere hemisphere;
    public Boolean potential_supernovae;

    public Criteria(String constellation, Range<Double> distance_parsecs, Range<Double> temperature, Range<Double> apparent_magnitude, Star.Hemisphere hemisphere, Boolean potential_supernovae) {
        this.constellation = constellation;
        this.distance_parsecs = distance_parsecs;
        this.temperature = temperature;
        this.apparent_magnitude = apparent_magnitude;
        this.hemisphere = hemisphere;
        this.potential_supernovae = potential_supernovae;
    }
}

