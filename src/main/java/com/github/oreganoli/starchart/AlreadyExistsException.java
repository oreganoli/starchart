package com.github.oreganoli.starchart;

/** Thrown when the user tries to create a star with a naming conflict. */
public class AlreadyExistsException extends Exception {
    public AlreadyExistsException(String msg) {
        super(msg);
    }
}
