package com.ddabadi.exception;

/**
 * Created by deddy on 5/10/16.
 */
public class BagianNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -2859292084648724403L;
    private final int bagianId;

    public BagianNotFoundException(int id) {
        bagianId = id;
    }

    public int getBagianId() {
        return bagianId;
    }

}
