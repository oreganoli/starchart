package com.github.oreganoli.starchart;

/** Helper class for generating Bayer designations for stars. */
public class Bayer {
    static final String[] GREEK = {
            "Alpha",
            "Beta",
            "Gamma",
            "Delta",
            "Epsilon",
            "Zeta",
            "Eta",
            "Theta",
            "Iota",
            "Kappa",
            "Lambda",
            "Mu",
            "Nu",
            "Xi",
            "Omicron",
            "Pi",
            "Rho",
            "Sigma",
            "Tau",
            "Upsilon",
            "Phi",
            "Chi",
            "Psi",
            "Omega"
    };
    static final String LATIN = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * 
     * @param i The rank of the star, by apparent magnitude, in ascending order.
     * @param constellation The constellation's name.
     * @return A full Bayer designation for the given index and constellation name.
     */
    public static String designation(int i, String constellation) {
        if (i < 0) {
            throw new IllegalArgumentException("Invalid index");
        } else if (i <= 23) {
            return String.format("%s %s", GREEK[i], constellation);
        } else if (i <= 75) {
            return String.format("%s %s", LATIN.charAt(i - GREEK.length), constellation);
        } else {
            throw new UnsupportedOperationException("This program does not handle constellations of more than 76 stars; we've run out of alphabets");
        }
    }
}
