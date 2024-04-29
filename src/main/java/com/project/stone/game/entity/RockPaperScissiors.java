package com.project.stone.game.entity;

public enum RockPaperScissiors {

    ROCK,
    PAPER,
    SCISSORS;

    public static RockPaperScissiors fromString(String value) {
        if (value != null) {
            for (RockPaperScissiors rockPaperScissiors : RockPaperScissiors.values()) {
                if (value.equalsIgnoreCase(rockPaperScissiors.name())) {
                    return rockPaperScissiors;
                }
            }
        }
        return null;
    }

    public static RockPaperScissiors fromInt(int value) {
        if (value >= 0 && value < RockPaperScissiors.values().length) {
            return RockPaperScissiors.values()[value];
        }
        return null;
    }

    public static int toInt(RockPaperScissiors value) {
        if (value != null) {
            return value.ordinal();
        }
        return -1;
    }

    public static String toString(RockPaperScissiors value) {
        if (value != null) {
            return value.name();
        }
        return null;
    }

    public static RockPaperScissiors getRandom() {
        return RockPaperScissiors.values()[(int) (Math.random() * RockPaperScissiors.values().length)];
    }

}
