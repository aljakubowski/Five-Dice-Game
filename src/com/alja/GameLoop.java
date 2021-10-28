package com.alja;

import java.util.Scanner;

public class GameLoop {

    private final Scanner scanner = new Scanner(System.in);

    private final Player player;

    private boolean reached60message = true;
    private int select = 0;
    private int pointsToWin = 200;

    private boolean gameIsContinued = true; // IS NEEDED ?


    public GameLoop(Player player) {
        this.player = player;
    }


    public void gameLoop() {

        boolean quit = false;

        while (!quit) {

            String turnMessage = "\n\t\t\t\t       *\n\t\t\t\t    " + player.getName() + " - turn\n\t\t\t\t       *";


            //  == GAME LOOP (before 60)
            while (!player.hasReachedInitSixty && player.isCurrent) {
                System.out.println(turnMessage);

                // == initial message
                System.out.println(" - initial roll: you must get 60 points in this round -\n\t\t\t press 'Enter' to roll");
                scanner.nextLine();
                player.rollDice(player.dicesToRoll);
                // == 60 points level
                while (player.roundPts < 60) {
                    if (player.canRoll) {
                        System.out.println("\t| " + player.roundPts + "/60 points gained|");
                        System.out.println("\t\t\tpress 'Enter' to roll");
                        scanner.nextLine();
                        player.rollDice(player.dicesToRoll);
                    } else {
                        System.out.println("\t___________________________________________");
                        System.out.println("\t* FAILED - your points are cleared\n\t* Try again");
                        player.resetCurrentRound();
                        player.setCurrent(false);
                        break;
                    }
                }
                break;
            }

            if (player.isCurrent) {
                player.hasReachedInitSixty = true;
                if (reached60message) {
                    System.out.println("");
                    System.out.println("\t* " + player.getName() + " - reached 60 point level *");
                    System.out.println();
                    reached60message = false;
                }
            } else {
                break;
            }


            // == GAME LOOP (after 60)
            while (gameIsContinued && player.hasReachedInitSixty && player.isCurrent) {

                System.out.println(turnMessage);

                System.out.println("________________________________________________");
                System.out.println("This round points: " + player.roundPts + "|\t\t\t Total points: " + player.getTotalPoints());
                System.out.println("Player: " + player.getName() + " |\t\t\t\t choose:");

                System.out.println("\n'Enter' to roll\t(1) save points\t\t(9) quit");
                System.out.println("________________________________________________");


                String choose = scanner.nextLine();
                try {
                    if (!choose.isEmpty()) {
                        select = Integer.parseInt(choose);
                    } else {
                        player.rollDice(player.dicesToRoll);

                        // == mechanism that allows to win
                        if (player.getTotalPoints() + player.roundPts  == pointsToWin){

                            System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
                            System.out.println("\n\n\n");
                            System.out.println("\t\t*!*!*!*! Player: "  + " WON !*!*!*!\n\n\t\t\t\t GAME OVER\t:D");
                            System.out.println("\n\n\n");
                            System.out.println("+++++++++++++++++++++++++++++++++++++++++++");

                            player.savePoints();
                            player.hasWon = true;
                            this.select = 9;
                            break;
                        } else if (player.getTotalPoints() + player.roundPts  > pointsToWin){
                            System.out.println("\t * To many points, current round is cleared");
                            player.canRoll = false;
                        }


                        if (!player.canRoll) {

                            player.setCurrent(false);
                            player.resetCurrentRound();
                            if (player.getTotalPoints() <= 0) {
                                player.hasReachedInitSixty = false;
                            }
                            break;
                        }


                    }
                } catch (Exception e) {
                    System.out.println("Failed, select: 'Enter', 1 or 9");
                }


                switch (select) {
                    case 1:
                        player.setCurrent(false);
                        System.out.println("Player " + player.getName() + " Saved: " + player.roundPts + " points");
                        player.savePoints();
                        player.resetCurrentRound();
                        select = 0;
                        break;

                    case 9:
                        System.out.println("quitting...");
                        player.canRoll = false;
                        gameIsContinued = false;
                        quit = true;
                        break;
                }
//                break;
            }
            quit = true;
        }
    }


}
