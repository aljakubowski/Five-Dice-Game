package com.alja;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DiceRoll {

    int availableDicesAfterRoll;

    public DiceRoll() {
    }

    public void resetDice(){
        this.availableDicesAfterRoll=0;
    }

    public int roll(int numOfDices){

        ArrayList<Integer> diceRoll = getRandomNumbers(numOfDices);
        System.out.println("\t\t\t\t[*  *  *  *  *]\nDices on board: " + diceRoll);

        HashMap<Integer, Integer> mapOfRoll = createMap(diceRoll);
        int points = countPoints(mapOfRoll);
        setDicesLeft(mapOfRoll, numOfDices);
        return points;
    }

    private int setDicesLeft(HashMap<Integer, Integer> rollSet, int numOfDices){
        int availableDices = numOfDices;

        if (rollSet.containsKey(1)){
            availableDices = availableDices - rollSet.get(1);
        }
        if (rollSet.containsKey(5)){
            availableDices = availableDices - rollSet.get(5);
        }
        for (Map.Entry<Integer, Integer> e : rollSet.entrySet()){
            if (!(e.getKey() == 1 || e.getKey() == 5)) {
                if (e.getValue() >= 3) {
                    availableDices -= e.getValue();
                }
            }
        }
        this.availableDicesAfterRoll = availableDices;
        return availableDices;
    }

    private int countPoints(HashMap<Integer, Integer> rollSet) {

        int points =0;

        if (rollSet.containsKey(1)) {
            if (rollSet.get(1) == 1) {
                points += 10;
            } else if (rollSet.get(1) == 2) {
                points += 20;
            } else if (rollSet.get(1) == 3) {
                points += 100;
            } else if (rollSet.get(1) == 4) {
                points += 200;
            } else {
                points += 400;
            }
        }

        if (rollSet.containsKey(5)) {
            if (rollSet.get(5) == 1) {
                points += 5;
            } else if (rollSet.get(5) == 2) {
                points += 10;
            } else if (rollSet.get(5) == 3) {
                points += 50;
            } else if (rollSet.get(5) == 4) {
                points += 100;
            } else {
                points += 200;
            }
        }

        for (Map.Entry<Integer, Integer> e : rollSet.entrySet()) {
            if (!(e.getKey() == 1 || e.getKey() == 5)) {
                if (e.getValue() == 3) {
                    points += e.getKey() * 10;
                } else if (e.getValue() == 4) {
                    points += e.getKey() * 20;
                } else if (e.getValue() == 5) {
                    points += e.getKey() * 40;
                }
            }
        }
        return points;
    }

    private ArrayList<Integer> getRandomNumbers(int num){
        ArrayList<Integer> fiveNumbers = new ArrayList<>();

        Random randomFive = new Random();
        for (int i =0; i<=num-1; i++){
            fiveNumbers.add(randomFive.ints(1,7)
                    .findFirst()
                    .getAsInt());
        }
        return fiveNumbers;
    }

    private HashMap<Integer, Integer> createMap(ArrayList<Integer> rollResult){ // add dices count

        HashMap<Integer, Integer> rollSet = new HashMap<>();

        int countONEs = 0;
        int countTWOs = 0;
        int countTHREEs = 0;
        int countFOURs = 0;
        int countFIVEs = 0;
        int countSIXs = 0;

            for (int i = 0; i <= rollResult.size()-1; i++) {
                switch (rollResult.get(i)){
                    case 1:
                        countONEs ++;
                        rollSet.put(1,countONEs);
                        break;
                    case 2:
                        countTWOs ++;
                        rollSet.put(2,countTWOs);
                        break;
                    case 3:
                        countTHREEs ++;
                        rollSet.put(3,countTHREEs);
                        break;
                    case 4:
                        countFOURs ++;
                        rollSet.put(4,countFOURs);
                        break;
                    case 5:
                        countFIVEs ++;
                        rollSet.put(5,countFIVEs);
                        break;
                    case 6:
                        countSIXs ++;
                        rollSet.put(6,countSIXs);
                        break;
                }
            }
        for (Map.Entry<Integer, Integer> e : rollSet.entrySet()) {
            System.out.println("\t\t\t\t[" + e.getKey() + "] times: " + e.getValue());
        }
        return rollSet;
    }
}