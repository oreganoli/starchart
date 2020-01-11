package com.github.oreganoli.starchart;

public class RightAscension {
    private static final int MAX_HOURS = 24;
    private static final int MAX_MINSEC = 59;

    public int hours;
    public int minutes;
    public int seconds;

    public RightAscension(int hours, int minutes, int seconds) {
        if ((hours == MAX_HOURS && (minutes != 0 || seconds != 0)) || (hours < 0 || hours > MAX_HOURS || minutes < 0 || minutes > MAX_MINSEC || seconds < 0 || seconds > MAX_MINSEC)) {
            throw new IllegalArgumentException("Attempted to create an invalid right ascension");
        }
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public String toString() {
        return String.format("%02d h %02d m %02d s", hours, minutes, seconds);
    }


}
