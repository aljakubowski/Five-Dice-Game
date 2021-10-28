package com.alja;

import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in);
    public static Player player1 = new Player("Tenet");

    public static void main(String[] args) {

        boolean gameIsContinued = true;
        boolean quit = false;
        int select = 0;

        System.out.println("== Welcome to the Dice Game ==");
        System.out.println("");


        while (!quit) {

            //  == GAME LOOP (before 60)
            while (!player1.hasReachedInitSixty) {
                // == initial message
                System.out.println("initial roll: you must get 60 points at once\n press 'Enter' to roll");
                scanner.nextLine();
                player1.rollDice(player1.dicesToRoll);
                // == 60 points level
                while (player1.roundPts < 60) {
                    if (player1.canRoll) {
                        System.out.println("'Enter' to roll until you get 60 pts (" + player1.roundPts + "/60 gained)");
                        scanner.nextLine();
                        player1.rollDice(player1.dicesToRoll);
                    } else {
                        System.out.println("\t______________________________________________");
                        System.out.println("\t* can't roll anymore - your points are cleared\n\t* Try again");
                        player1.resetCurrentRound();
                    }
                }
                player1.hasReachedInitSixty = true;

                System.out.println();
                System.out.println("\t* You've reached 60 point level *");
                System.out.println();

            // == GAME LOOP (after 60)
            while (gameIsContinued && player1.hasReachedInitSixty) {

                System.out.println("________________________________________________");
                System.out.println("Total points: " + player1.getTotalPoints());
                System.out.println("This round points: " + player1.roundPts + " | " + player1.dicesToRoll + " dices left  choose:");

                System.out.println(" (enter) roll\t(1) save points \t\t(9) quit");
                System.out.println("________________________________________________");


                String choose = scanner.nextLine();
                if (!choose.isEmpty()) {
                    select = Integer.parseInt(choose);
                } else {
                    player1.rollDice(player1.dicesToRoll);
                    if (!player1.canRoll) {
                        player1.resetCurrentRound();
                        if (player1.getTotalPoints() <= 0) {
                            player1.hasReachedInitSixty = false;
                        }
                    }
                }


                switch (select) {
                    case 1:
                        System.out.println("Saved: " + player1.roundPts + " points to Total");
                        player1.savePoints();
                        player1.resetCurrentRound();
                        select = 0;
                        break;
                    case 9:
                        System.out.println("quitting...");
                        player1.canRoll = false;
                        gameIsContinued = false;
                        quit = true;
                        break;
                }
            }

        }
            quit = true;
        }


//            System.out.println("What is your name?");
//
//            Player player2 = new Player(scanner.nextLine());
//
//            System.out.println("Hello " + player2.getName() +

    }
}
