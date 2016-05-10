package com.ddabadi.exception;

/**
 * Created by deddy on 5/10/16.
 */
public class InvalidKarakterException extends RuntimeException {
    private static final long serialVersionUID = -2859292084648724403L;
    private String karakter;

    public String getKarakter() {
        return karakter;
    }

    public InvalidKarakterException(String karakterErr) {
        karakter = karakterErr;
    }

}
