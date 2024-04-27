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

    public static RockPaperScissiors getWinner(RockPaperScissiors player1, RockPaperScissiors player2) {
        if (player1 == player2) {
            return null;
        }
        if (player1 == ROCK && player2 == SCISSORS) {
            return ROCK;
        }
        if (player1 == SCISSORS && player2 == PAPER) {
            return SCISSORS;
        }
        if (player1 == PAPER && player2 == ROCK) {
            return PAPER;
        }
        return null;
    }

    public static RockPaperScissiors getLoser(RockPaperScissiors player1, RockPaperScissiors player2) {
        RockPaperScissiors winner = getWinner(player1, player2);
        if (winner == null) {
            return null;
        }
        if (winner == player1) {
            return player2;
        }
        return player1;
    }

    public static boolean isWinner(RockPaperScissiors player, RockPaperScissiors opponent) {
        return getWinner(player, opponent) == player;
    }

    public static boolean isLoser(RockPaperScissiors player, RockPaperScissiors opponent) {
        return getLoser(player, opponent) == player;
    }
    
}
