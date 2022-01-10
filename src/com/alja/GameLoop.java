package com.alja;

import java.util.Scanner;

public class GameLoop {

    private final Scanner scanner = new Scanner(System.in);

    private final Player player;

    private boolean entryLevelMessageFlag = true;
    private int select = 0;
    private final int entryPtsLevel = 60;
    private final int pointsToWin = 1000;

    public GameLoop(Player player) {
        this.player = player;
    }

    public void gameLoop() {

        String turnMessage = "\n\t\t\t\t       *\n\t\t\t\t    " + player.getName() + " - turn\n\t\t\t\t       *";
        String wonMessage = "++++++++++++++++++++++++++++++++++++++++++++++++\n" +
                "\t\t\t\t\t! ! ! !\n\t\t\t\tPlayer: " + player.getName() +
                "\n\t\t\t\t\t  WON\n\t\t\t\t\t! ! ! !\n\n\t\t\t\t GAME OVER\t:D\n" +
                "++++++++++++++++++++++++++++++++++++++++++++++++";

        //  == GAME LOOP - before gaining 'entryPtsLevel' number of points ==
        if (!player.hasReachedEntryLevel() && player.isCurrent()) {
            System.out.println(turnMessage);
            System.out.println("-initial roll: you must get " + entryPtsLevel +
                    " points in one round\n\t\t\t - press 'Enter' to roll");
            scanner.nextLine();
            player.rollDice(player.getDicesToRoll());

            // = checks if player points are below entry pts level =
            while (player.getRoundPts() < entryPtsLevel) {
                // = allows to roll or ends loop =
                if (player.canRoll()) {
                    System.out.println("\t| " + player.getRoundPts() + "/" + entryPtsLevel + " points gained |" +
                            "\n\t\t\t - press 'Enter' to roll");
                    scanner.nextLine();
                    player.rollDice(player.getDicesToRoll());
                } else {
                    System.out.println("\t___________________________________________" +
                            "\n\t- FAILED - your points are cleared\n\t- Try again");
                    player.resetCurrentRound();
                    player.setCurrent(false);
                    break;
                }
            }
        }

        // == sets that player has reached 'entry level'
        //    and message after gaining 'entryPtsLevel' number of points ==
        if (player.isCurrent() && entryLevelMessageFlag) {
            player.setHasReachedEntryLevel(true);
            System.out.println("\n\t* " + player.getName() + " - reached " + entryPtsLevel + " points level *\n");
            entryLevelMessageFlag = false;
        }
        // = mechanism that checks if game is already won =
        if (player.getRoundPts() == pointsToWin) {
            System.out.println(wonMessage);
            player.savePoints();
            player.setHasWon(true);
            player.setWantsQuit(true);
            return;
        }

        // == GAME LOOP after gaining 'entryPtsLevel' number of points ==
        while (player.hasReachedEntryLevel() && player.isCurrent() && !player.wantsQuit()) {
            System.out.println(turnMessage);
            System.out.println("_________________________________________________" +
                    "\nThis round points: " + player.getRoundPts() +
                    "|\t\t\t Total points: " + player.getTotalPoints() +
                    "\nPlayer: " + player.getName() + " |\t\t\t\t choose:" +
                    "\n(Enter)-roll dice\t (1)-save points\t (9)-quit" +
                    "\n_________________________________________________");
            // = choosing mechanism to save points or continue rolling dice =
            String choose = scanner.nextLine();
            try {
                // = checks input - (enter) or selection number =
                if (!choose.isEmpty()) {
                    select = Integer.parseInt(choose);
                } else {
                    player.rollDice(player.getDicesToRoll());
                    // = mechanism that checks if game is won =
                    if (player.getTotalPoints() + player.getRoundPts() == pointsToWin) {
                        System.out.println(wonMessage);
                        player.savePoints();
                        player.setHasWon(true);
                        this.select = 9;
                        break;
                        // = checks if gained points exceed pointsToWin level =
                    } else if (player.getTotalPoints() + player.getRoundPts() > pointsToWin) {
                        System.out.println("\t * Too many points, current round points are cleared");
                        player.setCanRoll(false);
                    }
                    // = mechanism that checks if player can roll further =
                    if (!player.canRoll()) {
                        player.setCurrent(false);
                        player.resetCurrentRound();
                        // = checks if player saved points after reaching 'entry level'
                        //   if not he has to reach it again =
                        if (player.getTotalPoints() <= 0) {
                            player.setHasReachedEntryLevel(false);
                            entryLevelMessageFlag = true;
                        }
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Select: (Enter), (1) or (9)");
            }
            switch (select) {
                case 1:
                    // = checks if player have any points to save =
                    if (player.getRoundPts() == 0) {
                        System.out.println("\t" + player.getName() + " - Failed - You have not any points!");
                        break;
                    }
                    // = saves points and ends round =
                    player.setCurrent(false);
                    System.out.println("Player " + player.getName() + " Saved: " + player.getRoundPts() + " points");
                    player.savePoints();
                    player.resetCurrentRound();
                    select = 0;
                    break;

                case 9:
                    // = quits game if desired =
                    System.out.println("Are you sure you want to abandon current game? y/n");
                    String yesOrNo = scanner.nextLine();
                    while (!(yesOrNo.equals("n") || yesOrNo.equals("y"))) {
                        System.out.println("Select: y/n");
                        yesOrNo = scanner.nextLine();
                    }
                    if (yesOrNo.equals("n")) {
                        select = 0;
                        break;
                    }
                    System.out.println("Game interrupted");
                    player.setWantsQuit(true);
                    break;
            }
        }
    }
}