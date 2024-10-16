package dk.marquardt.model;

import dk.marquardt.jpa.DateStateDB;

import java.util.NoSuchElementException;

public enum DateState {
    SUCCESS,
    FAIL;

    public static DateState mapToDateState(DateStateDB state) {
        switch (state) {
            case SUCCESS -> {
                return SUCCESS;
            }
            case FAIL -> {
                return FAIL;
            }
            default -> throw new NoSuchElementException("Could not find any mapping state in DateState for " + state);
        }
    }
}
