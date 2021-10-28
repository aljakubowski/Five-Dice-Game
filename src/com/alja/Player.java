package com.alja;

public class Player {

    public String name;
    public int moveCount;
    public int totalPoints;

    public int roundPts;
    public int currentRollPts;

    public int dicesToRoll;
    public boolean canRoll;

    public boolean hasReachedInitSixty;
    public boolean isCurrent;

    public boolean hasWon;


    public DiceRoll dice = new DiceRoll();


    public Player(String name) {
        this.name = name;

        this.totalPoints = 0;
        this.moveCount = 0;

        this.roundPts =0;
        this.currentRollPts=0;

        this.dicesToRoll =5;
        this.canRoll = true;

        this.hasReachedInitSixty = false;
        this.hasWon = false;

    }


    public int rollDice(int numOfDices){

        String message;

        this.currentRollPts = dice.roll(numOfDices);
        this.dicesToRoll = dice.availableDicesAfterRoll;

        if (this.currentRollPts >0){
            this.roundPts += this.currentRollPts;

            if (dicesToRoll >0){
                dicesToRoll= dice.availableDicesAfterRoll;
            }
            if (dicesToRoll ==0){
                dicesToRoll= 5;
            }
            this.canRoll = true;
            message = "dices left: " + this.dicesToRoll;
        } else {

            message = "dices left: 0";
            this.canRoll = false;
        }
        System.out.println("\t| Current roll points = " + currentRollPts + " | " + message + " | ");
        System.out.println("\t| Player: " + name);
        this.currentRollPts =0;
        return currentRollPts;
    }

    public void resetCurrentRound(){
        this.canRoll = true;
        this.roundPts = 0;
        this.currentRollPts = 0;
        this.dicesToRoll = 5;
        dice.resetDice();
    }

    public void savePoints(){
        this.totalPoints += roundPts;
    }

    public void subtractTotalPts(){
        totalPoints -=100;
    }

    public String getName() {
        return name;
    }

    public int getTotalPoints() {
        return this.totalPoints;
    }

    public void getCurrentRollPoints() {
        System.out.println("Player: current points: " + this.currentRollPts);
    }


    public int getMoveCount() {
        return moveCount;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }
}