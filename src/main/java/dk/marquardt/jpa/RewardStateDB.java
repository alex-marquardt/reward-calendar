package dk.marquardt.jpa;

import dk.marquardt.model.RewardState;

import java.util.NoSuchElementException;

public enum RewardStateDB {
    USABLE,
    SPEND;

    public static RewardStateDB mapToRewardStateDB(RewardState state) {
        switch (state) {
            case USABLE -> {
                return USABLE;
            }
            case SPEND -> {
                return SPEND;
            }
            default -> throw new NoSuchElementException("Could not find any mapping state in RewardStateDB for " + state);
        }
    }
}
