package com.alja;

import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

//        boolean gameIsContinued = true;
//        boolean quit = false;
//        int select = 0;

        boolean chooseOrder = false;

        System.out.println("== Welcome to the Five Dice Game ==");
        System.out.println("");

        System.out.println("Player 1 name:");
        Player player1 = new Player(scanner.nextLine());
        System.out.println("Player 2 name:");
        Player player2 = new Player(scanner.nextLine());

        GameLoop gameLoop1 = new GameLoop(player1);
        GameLoop gameLoop2 = new GameLoop(player2);


        // order rolls
        while (!chooseOrder) {
            while ((player1.roundPts == player2.roundPts)) {
                System.out.println("Player " + player1.getName() + " rolls:");
                scanner.nextLine();
                player1.rollDice(player1.dicesToRoll);

                System.out.println("Player " + player2.getName() + " rolls:");
                scanner.nextLine();
                player2.rollDice(player2.dicesToRoll);
            }

            if (player1.roundPts > player2.roundPts) {
                System.out.println("\t\t\t* Player: " + player1.getName() + " starts *");
                player1.setCurrent(true);
                player2.setCurrent(false);
            } else {
                System.out.println("*" + player2.getName() + " start *");
                player1.setCurrent(false);
                player2.setCurrent(true);
            }
            player1.resetCurrentRound();
            player2.resetCurrentRound();
            chooseOrder = true;
        }


        // == WORKING ==
//            System.out.println("Player 1 name:");
//            Player player1 = new Player(scanner.nextLine());
//            System.out.println("Player 2 name:");
//            Player player2 = new Player(scanner.nextLine());

//            GameLoop gameLoop1 = new GameLoop(player1);
//            GameLoop gameLoop2 = new GameLoop(player2);


//            player1.setCurrent(true);
//            player2.setCurrent(false);


        // == loop that changes players until gameLoop is broken
        while (player1.isCurrent || player2.isCurrent && !player1.hasWon && !player2.hasWon) {

            String totalPointsTable = "================================================\n" +
                    "\t * player:\t" + player1.getName() + ":\t\t" + player1.getTotalPoints() +
                    "\n\t * player:\t" + player2.getName() + ":\t\t" + player2.getTotalPoints() +
                    "\n================================================";
            System.out.println(totalPointsTable);



            if (player1.isCurrent) {

                gameLoop1.gameLoop();
                // == switch players ==
                player2.setCurrent(true);

            } else if (player2.isCurrent) {

                gameLoop2.gameLoop();
                // == switch players ==
                player1.setCurrent(true);
            }
        }

    }
}
