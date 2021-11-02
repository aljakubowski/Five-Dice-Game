package com.alja;

import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        boolean chooseOrder = false;

        System.out.println("\n\t\t== Welcome to the Five Dice Game ==\n");

        System.out.println("Player 1 name:");
        Player player1 = new Player(scanner.nextLine());
        System.out.println("Player 2 name:");
        Player player2 = new Player(scanner.nextLine());

        GameLoop gameLoop1 = new GameLoop(player1);
        GameLoop gameLoop2 = new GameLoop(player2);

        // == order drawing ==
        while (!chooseOrder) {
            while ((player1.getRoundPts() == player2.getRoundPts())) {
                System.out.println("\nPlayer " + player1.getName() + " rolls:");
                scanner.nextLine();
                player1.rollDice(player1.getDicesToRoll());

                System.out.println("\nPlayer " + player2.getName() + " rolls:");
                scanner.nextLine();
                player2.rollDice(player2.getDicesToRoll());
            }

            if (player1.getRoundPts() > player2.getRoundPts()) {
                System.out.println("\n\n\t\t\t* " + player2.getName() + " STARTS *\n\n");
                player1.setCurrent(true);
                player2.setCurrent(false);
            } else {
                System.out.println("\n\n\t\t\t* " + player2.getName() + " STARTS *\n\n");
                player1.setCurrent(false);
                player2.setCurrent(true);
            }
            player1.resetCurrentRound();
            player2.resetCurrentRound();
            chooseOrder = true;
        }
        
            // == Loop that continues game until player wins or want's to quit ==
            while (!(player1.hasWon() || player2.hasWon()) && !(player1.wantsQuit() || player2.wantsQuit()) ) {

            // == score board message ==
            String totalPointsTable = "================================================\n" +
                    "\t * player:\t" + player1.getName() + ":\t\t" + player1.getTotalPoints() +
                    "\n\t * player:\t" + player2.getName() + ":\t\t" + player2.getTotalPoints() +
                    "\n================================================";
            System.out.println(totalPointsTable);

            // == switching players ==
            if (player1.isCurrent()) {
                gameLoop1.gameLoop();
                player2.setCurrent(true);
            } else if (player2.isCurrent()) {
                gameLoop2.gameLoop();
                player1.setCurrent(true);
            }
        }

    }
}