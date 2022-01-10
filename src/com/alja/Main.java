package com.alja;

import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        boolean chooseOrder = false;
        boolean gameContinues = true;
        String gameLogo = "\t\t    ______       ___         \n" +
                "\t\t   / ____/  ____/ (_)_______ \n" +
                "\t\t  /___ \\   / __  / / ___/ _ \\\n" +
                "\t\t ____/ /  / /_/ / / /__/  __/\n" +
                "\t\t/_____/   \\__,_/_/\\___/\\___/ \n";

        System.out.println(gameLogo);
        System.out.println("\n\t\t== Welcome to the Five Dice Game ==\n");

        while (gameContinues) {

            // == Input names for players ==
            System.out.println("Player 1 name:");
            Player player1 = new Player(scanner.nextLine());
            System.out.println("Player 2 name:");
            Player player2 = new Player(scanner.nextLine());
            GameLoop gameLoop1 = new GameLoop(player1);
            GameLoop gameLoop2 = new GameLoop(player2);

            // == Initial stage of game - rolling dice order draw ==
            while (!chooseOrder) {
                // == loop continues as long as both players have same number of points
                while ((player1.getRoundPts() == player2.getRoundPts())) {
                    System.out.println("\nPlayer " + player1.getName() + " rolls:");
                    scanner.nextLine();
                    player1.rollDice(player1.getDicesToRoll());

                    System.out.println("\nPlayer " + player2.getName() + " rolls:");
                    scanner.nextLine();
                    player2.rollDice(player2.getDicesToRoll());
                }
                // == setting the order of dice rolling ==
                if (player1.getRoundPts() > player2.getRoundPts()) {
                    System.out.println("\n\n\t\t\t* " + player1.getName() + " STARTS *\n\n");
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

            // == Loop that continues game until any player wins or want's to quit ==
            while (!(player1.hasWon() || player2.hasWon()) && !(player1.wantsQuit() || player2.wantsQuit())) {

                // = score board message =
                String totalPointsTable = "=================================================\n" +
                        "\t * player:\t" + player1.getName() + ":\t\t" + player1.getTotalPoints() +
                        "\n\t * player:\t" + player2.getName() + ":\t\t" + player2.getTotalPoints() +
                        "\n=================================================";
                System.out.println(totalPointsTable);

                // = switching players =
                if (player1.isCurrent()) {
                    gameLoop1.gameLoop();
                    player2.setCurrent(true);
                } else {
                    gameLoop2.gameLoop();
                    player1.setCurrent(true);
                }
            }
            // = exiting process =
            System.out.println("Do you want to play again? y/n");
            String yesOrNo = scanner.nextLine();
            while (!(yesOrNo.equals("n") || yesOrNo.equals("y"))) {
                System.out.println("Select: y/n");
                yesOrNo = scanner.nextLine();
            }
            if (yesOrNo.equals("n")) {
                System.out.println("quitting...");
                System.out.println(gameLogo);
                gameContinues = false;
            }
            chooseOrder = false;
            System.out.println(gameLogo);
        }
    }
}