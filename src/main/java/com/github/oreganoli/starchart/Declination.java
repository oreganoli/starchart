package com.github.oreganoli.starchart;

public class Declination {
    private static int MAX_DEGREES = 90;
    private static int MAX_MINSEC = 59;
    private int degrees;
    private int minutes;
    private int seconds;

    public Declination(int degrees, int minutes, int seconds) {
        if (Math.abs(degrees) > MAX_DEGREES || (Math.abs(degrees) == MAX_DEGREES && (minutes > 0 || seconds > 0))) {
            throw new IllegalArgumentException("A star's declination can be at most 90 degrees either north or south.");
        } else if (minutes < 0 || minutes > MAX_MINSEC || seconds < 0 || seconds > MAX_MINSEC) {
            throw new IllegalArgumentException("There are 60 minutes in an hour and 60 seconds in a minute.");
        } else {
            this.degrees = degrees;
            this.minutes = minutes;
            this.seconds = seconds;
        }
    }

    public String toString() {
        return String.format("%02d°%02d\'%02d\"", degrees, minutes, seconds);
    }

    public Star.Hemisphere hemisphere() {
        if (degrees == 0) {
            if (minutes > 0 || seconds > 0) {
                return Star.Hemisphere.Northern;
            } else {
                return Star.Hemisphere.Equatorial;
            }
        } else {
            return degrees > 0 ? Star.Hemisphere.Northern : Star.Hemisphere.Southern;
        }
    }
}
