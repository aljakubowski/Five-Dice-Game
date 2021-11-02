package com.alja;

public class Player {

    public final String name;

    private int totalPoints;
    private int roundPts;
    private int currentRollPts;

    private int dicesToRoll;

    private boolean isCurrent;
    private boolean canRoll;
    private boolean hasReachedEntryLevel;
    private boolean hasWon;
    private boolean wantsQuit;

    private final DiceRoll dice = new DiceRoll();

    public Player(String name) {
        this.name = name;
        this.totalPoints = 0;
        this.roundPts = 0;
        this.currentRollPts = 0;
        this.dicesToRoll = 5;
        this.canRoll = true;
        this.hasReachedEntryLevel = false;
        this.hasWon = false;
        this.wantsQuit = false;
    }

    public void rollDice(int numOfDices) {
        String message;

        this.currentRollPts = dice.roll(numOfDices);
        this.dicesToRoll = dice.getAvailableDicesAfterRoll();

        if (this.currentRollPts > 0) {
            this.roundPts += this.currentRollPts;

            if (dicesToRoll > 0) {
                dicesToRoll = dice.getAvailableDicesAfterRoll();
            }
            if (dicesToRoll == 0) {
                dicesToRoll = 5;
            }
            this.canRoll = true;
            message = "dices left: " + this.dicesToRoll;
        } else {
            message = "dices left: 0";
            this.canRoll = false;
        }
        System.out.println("\t| Current roll points = " + currentRollPts + " | " + message + " | ");
        System.out.println("\t| Player: " + name);
        this.currentRollPts = 0;
    }

    public void resetCurrentRound() {
        this.canRoll = true;
        this.roundPts = 0;
        this.currentRollPts = 0;
        this.dicesToRoll = 5;
        dice.resetDice();
    }

    public String getName() {
        return name;
    }

    public int getTotalPoints() {
        return this.totalPoints;
    }

    public int getRoundPts() {
        return roundPts;
    }

    public int getDicesToRoll() {
        return dicesToRoll;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public boolean canRoll() {
        return canRoll;
    }

    public void setCanRoll(boolean canRoll) {
        this.canRoll = canRoll;
    }

    public boolean hasReachedEntryLevel() {
        return hasReachedEntryLevel;
    }

    public void setHasReachedEntryLevel(boolean hasReachedEntryLevel) {
        this.hasReachedEntryLevel = hasReachedEntryLevel;
    }

    public boolean hasWon() {
        return hasWon;
    }

    public void setHasWon(boolean hasWon) {
        this.hasWon = hasWon;
    }

    public boolean wantsQuit() {
        return wantsQuit;
    }

    public void setWantsQuit(boolean wantsQuit) {
        this.wantsQuit = wantsQuit;
    }

    public void savePoints() {
        this.totalPoints += roundPts;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }
}