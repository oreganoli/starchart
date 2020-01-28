package com.github.oreganoli.starchart;

/** A star's north-south coordinate on the celestial sphere, measured in degrees, ranging from -90° to 90° */
public class Declination {
    private static int MAX_DEGREES = 90;
    private static int MAX_MINSEC = 59;
    public int degrees;
    public int minutes;
    public int seconds;

    public Declination(int degrees, int minutes, int seconds) {
        if (!(
                (Math.abs(degrees) == 90 && minutes == 0 && seconds == 0) ||
                        (Math.abs(degrees) < 90 && degrees != 0 && minutes >= 0 && minutes <= MAX_MINSEC && seconds >= 0 && seconds <= MAX_MINSEC) ||
                        (degrees == 0 && Math.abs(minutes) <= MAX_MINSEC && seconds >= 0 && seconds <= MAX_MINSEC) ||
                        (degrees == 0 && minutes == 0 && Math.abs(seconds) <= MAX_MINSEC) ||
                        (degrees == 0 && minutes == 0 && seconds == 0)
        )) {
            throw new IllegalArgumentException("Declinations may be at most 90 degrees either north or south.");
        } else {
            this.degrees = degrees;
            this.minutes = minutes;
            this.seconds = seconds;
        }
    }

    public String toString() {
        return String.format("%02d°%02d'%02d\"", degrees, minutes, seconds);
    }

    public Star.Hemisphere hemisphere() {
        if (degrees == 0) {
            if (minutes == 0) {
                if (seconds == 0) {
                    return Star.Hemisphere.Equatorial;
                } else if (seconds < 0) {
                    return Star.Hemisphere.Southern;
                } else {
                    return Star.Hemisphere.Northern;
                }
            } else if (minutes < 0) {
                return Star.Hemisphere.Southern;
            } else {
                return Star.Hemisphere.Northern;
            }
        } else if (degrees < 0) {
            return Star.Hemisphere.Southern;
        } else {
            return Star.Hemisphere.Northern;
        }
    }
}
