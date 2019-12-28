package com.github.oreganoli.starchart;

// This is a helper type for reporting errors via JSON.
public class ErrWrapper {
    public String type;
    public String reason;

    public ErrWrapper(Exception ex) {
        type = ex.getClass().getName();
        reason = ex.getMessage();
    }
}
