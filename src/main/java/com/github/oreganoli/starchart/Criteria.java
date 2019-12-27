package com.github.oreganoli.starchart;

public class Criteria {
    public String constellation;
    public Range<Double> distance;
    public Range<Double> temperature;
    public Range<Double> magnitude;
    public Star.Hemisphere hemisphere;
    public Boolean supernovae;

    public Criteria(String constellation, Range<Double> distance, Range<Double> temperature, Range<Double> magnitude, Star.Hemisphere hemisphere, Boolean supernovae) {
        this.constellation = constellation;
        this.distance = distance;
        this.temperature = temperature;
        this.magnitude = magnitude;
        this.hemisphere = hemisphere;
        this.supernovae = supernovae;
    }
}

