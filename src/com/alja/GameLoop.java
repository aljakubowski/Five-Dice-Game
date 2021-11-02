package com.alja;

import java.util.Scanner;

public class GameLoop {

    private final Scanner scanner = new Scanner(System.in);

    private final Player player;

    private boolean reachedEntryLevel = true;
    private int select = 0;
    private final int entryPtsLevel = 60;
    private final int pointsToWin = 500;

    public GameLoop(Player player) {
        this.player = player;
    }
    // == method that handles rolling dice ==
    public void gameLoop() {

        boolean quit = false;

        while (!quit) {

            String turnMessage = "\n\t\t\t\t       *\n\t\t\t\t    " + player.getName() + " - turn\n\t\t\t\t       *";

            //  == GAME LOOP - before -entryPtsLevel- pts ==
            if (!player.hasReachedEntryLevel() && player.isCurrent()) {
                System.out.println(turnMessage);

                System.out.println(" - initial roll: you must get " + entryPtsLevel +
                        " points in this round -\n\t\t\t press 'Enter' to roll");
                scanner.nextLine();
                player.rollDice(player.getDicesToRoll());

                while (player.getRoundPts() < entryPtsLevel) {
                    if (player.canRoll()) {
                        System.out.println("\t| " + player.getRoundPts() + "/" + entryPtsLevel + " points gained|");
                        System.out.println("\t\t\tpress 'Enter' to roll");
                        scanner.nextLine();
                        player.rollDice(player.getDicesToRoll());
                    } else {
                        System.out.println("\t___________________________________________");
                        System.out.println("\t- FAILED - your points are cleared\n\t- Try again");
                        player.resetCurrentRound();
                        player.setCurrent(false);
                        break;
                    }
                }
            }
            
            // == message after achieving entry level ==
            if (player.isCurrent() && reachedEntryLevel) {
                player.setHasReachedEntryLevel(true);
                System.out.println("\n\t* " + player.getName() + " - reached " + entryPtsLevel + " points level *\n");
                reachedEntryLevel = false;
            }

            // == GAME LOOP - after -entryPtsLevel- pts ==
            while (player.hasReachedEntryLevel() && player.isCurrent() && !player.wantsQuit()) {

                System.out.println(turnMessage);

                System.out.println("________________________________________________");
                System.out.println("This round points: " + player.getRoundPts() +
                        "|\t\t\t Total points: " + player.getTotalPoints());
                System.out.println("Player: " + player.getName() + " |\t\t\t\t choose:");

                System.out.println("\n'Enter' to roll\t(1) save points\t\t(9) quit");
                System.out.println("________________________________________________");

                // == choosing mechanism ==
                String choose = scanner.nextLine();
                try {
                    if (!choose.isEmpty()) {
                        select = Integer.parseInt(choose);
                    } else {
                        player.rollDice(player.getDicesToRoll());

                        // == mechanism that checks if game is won ==
                        if (player.getTotalPoints() + player.getRoundPts() == pointsToWin) {

                            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++\n");
                            System.out.println("\t\t\t\t\t! ! ! !\n\t\t\t\tPlayer: " + player.getName() +
                                    "\n\t\t\t\t\t  WON\n\t\t\t\t\t! ! ! !\n\n\t\t\t\t GAME OVER\t:D\n");
                            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
                            player.savePoints();
                            player.setHasWon(true);
                            this.select = 9;
                            break;
                        } else if (player.getTotalPoints() + player.getRoundPts() > pointsToWin) {
                            System.out.println("\t * Too many points, current round points are cleared");
                            player.setCanRoll(false);
                        }
                        // == mechanism that checks if player can roll ==
                        if (!player.canRoll()) {
                            player.setCurrent(false);
                            player.resetCurrentRound();
                            if (player.getTotalPoints() <= 0) {
                                player.setHasReachedEntryLevel(true);
                            }
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Failed, select: 'Enter', 1 or 9");
                }
                switch (select) {
                    case 1:
                        if (player.getRoundPts()==0){
                            System.out.println("\t" + player.getName() + " - Failed - You have not any points!");
                            break;
                        }
                        player.setCurrent(false);
                        System.out.println("Player " + player.getName() + " Saved: " + player.getRoundPts() + " points");
                        player.savePoints();
                        player.resetCurrentRound();
                        select = 0;
                        break;

                    case 9:
                        System.out.println("quitting...");
                        player.setWantsQuit(true);
                        break;
                }
            }
            quit = true;
        }
    }

}