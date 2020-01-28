package com.github.oreganoli.starchart;

/** This is a helper type for reporting errors via JSON. */
public class ErrWrapper {
    /**
     * 
     * @param type Type of the error.
     * @param reason Detailed information about the error.
     */
    public ErrWrapper(String type, String reason) {
        this.type = type;
        this.reason = reason;
    }

    public String type;
    public String reason;

    /**
     * 
     * @param ex Exception to generate an ErrWrapper from.
     */
    public ErrWrapper(Exception ex) {
        type = ex.getClass().getName();
        reason = ex.getMessage();
    }
}
