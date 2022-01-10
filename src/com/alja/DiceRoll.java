package com.alja;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

// == Class that handles all actions of Dice ==
public class DiceRoll {

    private int availableDicesAfterRoll;

    public DiceRoll() {
    }

    public int getAvailableDicesAfterRoll() {
        return availableDicesAfterRoll;
    }

    public void resetDice() {
        this.availableDicesAfterRoll = 0;
    }

    // == method that performs Dice roll and returns its points ==
    public int roll(int numOfDices) {

        // = 'rolling' Dice by getting as many random numbers (from 1-6) as dices available =
        ArrayList<Integer> diceRoll = getRandomNumbers(numOfDices);
        System.out.println("\t\t\t\t[*  *  *  *  *]\nDices on board: " + diceRoll);

        // = creates a Roll Set from rolled numbers =
        Map<Integer, Integer> rollSet = createRollSet(diceRoll);
        // = counts points =
        int points = countPoints(rollSet);
        // = set number of dices that left after roll =
        setDicesLeft(rollSet, numOfDices);
        return points;
    }

    // == method counts how many dices left after roll
    //    - based on a current roll set ==
    private void setDicesLeft(Map<Integer, Integer> rollSet, int numOfDices) {
        int availableDices = numOfDices;

        if (rollSet.containsKey(1)) {
            availableDices = availableDices - rollSet.get(1);
        }
        if (rollSet.containsKey(5)) {
            availableDices = availableDices - rollSet.get(5);
        }
        for (Map.Entry<Integer, Integer> e : rollSet.entrySet()) {
            if (!(e.getKey() == 1 || e.getKey() == 5)) {
                if (e.getValue() >= 3) {
                    availableDices -= e.getValue();
                }
            }
        }
        this.availableDicesAfterRoll = availableDices;
    }

    // == method counts points depending on Roll Set it gets
    //    - game counting points logic ==
    private int countPoints(Map<Integer, Integer> rollSet) {
        int points = 0;

        // = every 'ONE' is counted as 'TEN' points =
        if (rollSet.containsKey(1)) {
            rollSet.put(10, rollSet.get(1));
            rollSet.remove(1);
        }
        // = count points from any rolled 'ONE' and 'FIVE'
        //    switch 'ONE' to 'TEN' =
        for (Map.Entry<Integer, Integer> e : rollSet.entrySet()) {

            if (e.getKey() == 10 || e.getKey() == 5) {

                if (e.getValue() == 1) {
                    points += e.getKey();
                } else if (e.getValue() == 2) {
                    points += e.getKey() * 2;
                } else if (e.getValue() == 3) {
                    points += e.getKey() * 10;
                } else if (e.getValue() == 4) {
                    points += e.getKey() * 20;
                } else if (e.getValue() == 5) {
                    points += e.getKey() * 40;
                }
            } else {
                if (e.getValue() == 3) {
                    points += e.getKey() * 10;
                } else if (e.getValue() == 4) {
                    points += e.getKey() * 20;
                } else if (e.getValue() == 5) {
                    points += e.getKey() * 40;
                }
            }
        }
        // = put back appropriate key to the map
        //    switch 'TEN' to 'ONE' =
        if (rollSet.containsKey(10)) {
            rollSet.put(1, rollSet.get(10));
            rollSet.remove(10);
        }

        return points;
    }

    // == gets random number for each Dice ==
    private ArrayList<Integer> getRandomNumbers(int numOfDices) {
        ArrayList<Integer> listOfRandoms = new ArrayList<>();

        for (int i = 0; i <= numOfDices - 1; i++) {
            listOfRandoms.add(new Random().ints(1, 7)
                    .findFirst()
                    .getAsInt());
        }
        return listOfRandoms;
    }

    // == creates map of Roll Set <K = number on dice, V = times that number occurs>
    //    from array of rolled numbers ==
    private Map<Integer, Integer> createRollSet(ArrayList<Integer> rollResult) {
        Map<Integer, Integer> rollSet = new HashMap<>();

        int countONEs = 0;
        int countTWOs = 0;
        int countTHREEs = 0;
        int countFOURs = 0;
        int countFIVEs = 0;
        int countSIXs = 0;

        for (int i = 0; i <= rollResult.size() - 1; i++) {
            switch (rollResult.get(i)) {
                case 1:
                    countONEs++;
                    rollSet.put(1, countONEs);
                    break;
                case 2:
                    countTWOs++;
                    rollSet.put(2, countTWOs);
                    break;
                case 3:
                    countTHREEs++;
                    rollSet.put(3, countTHREEs);
                    break;
                case 4:
                    countFOURs++;
                    rollSet.put(4, countFOURs);
                    break;
                case 5:
                    countFIVEs++;
                    rollSet.put(5, countFIVEs);
                    break;
                case 6:
                    countSIXs++;
                    rollSet.put(6, countSIXs);
                    break;
            }
        }
        for (Map.Entry<Integer, Integer> e : rollSet.entrySet()) {
            System.out.println("\t\t\t\t[" + e.getKey() + "] times: " + e.getValue());
        }
        return rollSet;
    }
}