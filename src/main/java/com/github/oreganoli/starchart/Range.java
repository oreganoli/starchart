package com.github.oreganoli.starchart;

// A helper type that lets us wrap two numbers behind a reference and thus make them nullable. See the Criteria class for the rationale behind this.
// Represents an inclusive range of numbers between "min" and "max" that can be used as a search criterion.
public class Range<T> {
    public T min;
    public T max;

    public Range(T min, T max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public String toString() {
        return String.format("[%s; %s]", min, max);
    }
}
