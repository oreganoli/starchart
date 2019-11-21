package com.github.oreganoli.starchart;

public class RightAscension {
    private static final int MIN_HOURS = 0;
    private static final int MAX_HOURS = 24;
    private static final int MIN_MINSEC = 0;
    private static final int MAX_MINSEC = 60;

    private int hours;
    private int minutes;
    private int seconds;

    public RightAscension(int hours, int minutes, int seconds) {
        if (
                hours < MIN_HOURS || hours > MAX_HOURS ||
                minutes < MIN_MINSEC || minutes > MAX_MINSEC ||
                seconds < MIN_MINSEC || seconds > MAX_MINSEC
        ) {
            throw new IllegalArgumentException("Attempted to create an invalid right ascension");
        }
        if (seconds == 60) {
            seconds = 0;
            minutes += 1;
        }
        if (minutes == 60) {
            minutes = 0;
            hours += 1;
        }
        if (hours > MAX_HOURS || (hours == MAX_HOURS && (minutes > 0 || seconds > 0))) {
            throw new IllegalArgumentException("Attempted to create a right ascension of more than 24 hours");
        }
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public String toString() {
        return String.format("%02d h %02d m %02d s", hours, minutes, seconds);
    }


}
