package com.alja;

public class Player {

    public String name;
    int moveCount;
    int totalPoints;

    public int roundPts;
    public int currentRollPts;

    public int dicesToRoll;
    boolean canRoll;

    boolean hasReachedInitSixty;


    public DiceRoll dice = new DiceRoll();


    public Player(String name) {
        this.name = name;

        this.totalPoints = 0;
        this.moveCount = 0;

        this.roundPts =0;
        this.currentRollPts=0;

        this.dicesToRoll =5;
        this.canRoll = true;

        hasReachedInitSixty = false;
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
        totalPoints += roundPts;
        moveCount ++;
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
}