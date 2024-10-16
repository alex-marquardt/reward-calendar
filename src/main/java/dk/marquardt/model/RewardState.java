package dk.marquardt.model;

import dk.marquardt.jpa.RewardStateDB;

import java.util.NoSuchElementException;

public enum RewardState {
    USABLE,
    SPEND;

    public static RewardState mapToRewardState(RewardStateDB state) {
        switch (state) {
            case SPEND -> {
                return RewardState.SPEND;
            }
            case USABLE -> {
                return RewardState.USABLE;
            }
            default -> throw new NoSuchElementException("Could not find any mapping state in RewardState for " + state);
        }
    }
}
