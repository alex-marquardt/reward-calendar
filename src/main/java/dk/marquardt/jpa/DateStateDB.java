package dk.marquardt.jpa;

import dk.marquardt.model.DateState;

import java.util.NoSuchElementException;

public enum DateStateDB {
    SUCCESS,
    FAIL;

    public static DateStateDB mapToDateStateDB(DateState state) {
        switch (state) {
            case SUCCESS -> {
                return SUCCESS;
            }
            case FAIL -> {
                return FAIL;
            }
            default -> throw new NoSuchElementException("Could not find any mapping state in DateStateDB for " + state);
        }
    }
}
