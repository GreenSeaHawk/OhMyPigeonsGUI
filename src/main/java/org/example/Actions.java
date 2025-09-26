package org.example;

import java.util.*;

public class Actions {

    public static void removeOnePigeonFromEveryoneElse(Player[] allPlayers) {
        Player currentPlayer = Datastore.retrieveValue("currentPlayer", Player.class);
        for (Player p : allPlayers) {
            if (p.getPlayerNumber() != currentPlayer.getPlayerNumber()
                    && p.getBench().getNumPigeons() > 0) {
                p.getBench().subtractPigeons(1);
            }
        }
    }

    public static void takeOnePigeonFromAnotherPlayer(Player[] allPlayers) {
        Player currentPlayer = Datastore.retrieveValue("currentPlayer", Player.class);
        Scanner sc = new Scanner(System.in);
        Player targetPlayer = null;

        while (true) {
            System.out.println("Who would you like to take a pigeon from?");
            // List possible targets
            for (Player p : allPlayers) {
                if (!p.equals(currentPlayer)) {
                    System.out.println("Player " + p.getPlayerNumber());
                }
            }

            try {
                int targetNum = sc.nextInt();
                targetPlayer = Arrays.stream(allPlayers)
                        .filter(p -> p.getPlayerNumber() == targetNum && !p.equals(currentPlayer))
                        .findFirst()
                        .orElse(null);

                if (targetPlayer == null) {
                    System.out.println("Invalid choice. You cannot target yourself or a non-existent player. Try again.");
                    continue;
                }
                break; // valid target
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                sc.next(); // consume invalid input
            }
        }

        // Execute the action
        if (targetPlayer.getBench().getNumPigeons() > 0) {
            targetPlayer.getBench().subtractPigeons(1);
            currentPlayer.getBench().addPigeons(1);
            System.out.println("One pigeon taken from Player " + targetPlayer.getPlayerNumber());
        } else {
            System.out.println("That player has no pigeons to take.");
        }
    }

    public static void takeThreePigeonsFromFlock(Player[] allPlayers) {
        Player currentPlayer = Datastore.retrieveValue("currentPlayer", Player.class);
        for (Player p : allPlayers) {
            if (p.getPlayerNumber() == currentPlayer.getPlayerNumber()) {
                p.getBench().addPigeons(3);
            }
        }
    }

    public static void slideLeft(Player[] allPlayers) {
        int numFirstBench = allPlayers[0].getBench().getNumPigeons();
        for (int i=0; i < allPlayers.length - 1; i++) {
                allPlayers[i].getBench().setNumberPigeons(allPlayers[i+1].getBench().getNumPigeons());
            }
        allPlayers[allPlayers.length - 1].getBench().setNumberPigeons(numFirstBench);
    }

    public static void slideRight(Player[] allPlayers) {
        int numLastBench = allPlayers[allPlayers.length - 1].getBench().getNumPigeons();
        for (int i=allPlayers.length -1 ; i > 0; i--) {
            allPlayers[i].getBench().setNumberPigeons(allPlayers[i-1].getBench().getNumPigeons());
        }
        allPlayers[0].getBench().setNumberPigeons(numLastBench);
    }

    public static void takeTwoGiveOnePigeon(Player[] allPlayers) {
        Player currentPlayer = Datastore.retrieveValue("currentPlayer", Player.class);
        Scanner sc = new Scanner(System.in);
        Player targetPlayer = null;

        while (true) {
            System.out.println("Who would you like to give a pigeon to? (from the flock)");

            // List possible target players (excluding current player if needed)
            for (Player p : allPlayers) {
                if (!p.equals(currentPlayer)) {
                    System.out.println("Player " + p.getPlayerNumber());
                }
            }

            try {
                int targetNum = sc.nextInt();
                targetPlayer = Arrays.stream(allPlayers)
                        .filter(p -> p.getPlayerNumber() == targetNum && !p.equals(currentPlayer))
                        .findFirst()
                        .orElse(null);

                if (targetPlayer == null) {
                    System.out.println("Invalid choice. You cannot target yourself or a non-existent player. Try again.");
                    continue;
                }
                break; // valid target
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                sc.next(); // consume invalid token
            }
        }

        // Execute the action
        targetPlayer.getBench().addPigeons(1);
        currentPlayer.getBench().addPigeons(2);
        System.out.println("Gave 1 pigeon to Player " + targetPlayer.getPlayerNumber() + " and took 2 pigeons from the flock.");
    }

    public static void belowFourTakeTwo(Player[] allPlayers) {
        for (Player p : allPlayers) {
            if (p.getBench().getNumPigeons() <= 3) {
                p.getBench().addPigeons(2);
            }
        }
    }

    public static void allTakeOne(Player[] allPlayers) {
        for (Player p : allPlayers) {
            p.getBench().addPigeons(1);
        }
    }

    public static void swapBench(Player[] allPlayers) {
        Player currentPlayer = Datastore.retrieveValue("currentPlayer", Player.class);
        Scanner sc = new Scanner(System.in);
        Player targetPlayer = null;

        while (true) {
            System.out.println("Who would you like to swap benches with?");
            // List valid target players (excluding current player)
            for (Player p : allPlayers) {
                if (!p.equals(currentPlayer)) {
                    System.out.println("Player " + p.getPlayerNumber());
                }
            }

            try {
                int targetNum = sc.nextInt();
                targetPlayer = Arrays.stream(allPlayers)
                        .filter(p -> p.getPlayerNumber() == targetNum && !p.equals(currentPlayer))
                        .findFirst()
                        .orElse(null);

                if (targetPlayer == null) {
                    System.out.println("Invalid choice. You cannot swap with yourself or a non-existent player. Try again.");
                    continue;
                }
                break; // valid target
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                sc.next(); // consume invalid token
            }
        }

        // Swap benches
        int currentPlayerBench = currentPlayer.getBench().getNumPigeons();
        int targetPlayerBench = targetPlayer.getBench().getNumPigeons();
        currentPlayer.getBench().setNumberPigeons(targetPlayerBench);
        targetPlayer.getBench().setNumberPigeons(currentPlayerBench);

        System.out.println("Swapped benches with Player " + targetPlayer.getPlayerNumber());
    }

    public static void takeOneFromAll(Player[] allPlayers) {
        Player currentPlayer = Datastore.retrieveValue("currentPlayer", Player.class);
        for (Player p : allPlayers) {
            if (p.getPlayerNumber() != currentPlayer.getPlayerNumber()
                    && p.getBench().getNumPigeons() > 0) {
                p.getBench().subtractPigeons(1);
                currentPlayer.getBench().addPigeons(1);
            }
        }
    }

    public static void takeThreeFromMost(Player[] allPlayers) {
        // Find the maximum number of pigeons
        int max = Arrays.stream(allPlayers)
                .mapToInt(p -> p.getBench().getNumPigeons())
                .max()
                .orElse(0);

        // Find all players with max pigeons
        List<Player> maxPlayers = Arrays.stream(allPlayers)
                .filter(p -> p.getBench().getNumPigeons() == max)
                .toList();

        Player targetPlayer = null;
        Scanner sc = new Scanner(System.in);

        if (maxPlayers.size() > 1) {
            // Multiple players have max pigeons, ask which one to target
            while (true) {
                System.out.println("Multiple players have the most pigeons. " +
                        "Choose a player to lose 3 pigeons:");
                for (Player p : maxPlayers) {
                    System.out.println("Player " + p.getPlayerNumber());
                }

                try {
                    int choice = sc.nextInt();
                    targetPlayer = maxPlayers.stream()
                            .filter(p -> p.getPlayerNumber() == choice)
                            .findFirst()
                            .orElse(null);

                    if (targetPlayer == null) {
                        System.out.println("Invalid choice. Try again.");
                        continue;
                    }
                    break; // valid choice
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter an integer.");
                    sc.next(); // consume invalid token
                }
            }
        } else {
            // Only one player has the max pigeons
            targetPlayer = maxPlayers.get(0);
        }

        // Apply action
        targetPlayer.getBench().subtractPigeons(3);
        System.out.println("Player " + targetPlayer.getPlayerNumber() + " loses 3 pigeons.");
    }

    public static void onePlayerLoseTwo(Player[] allPlayers) {
        Scanner sc = new Scanner(System.in);
        Player targetPlayer = null;
        Player currentPlayer = Datastore.retrieveValue("currentPlayer", Player.class);

        while (true) {
            System.out.println("Who would you like to lose 2 pigeons?");
            // List all valid players
            for (Player p : allPlayers) {
                if (!p.equals(currentPlayer)) {
                    System.out.println("Player " + p.getPlayerNumber());
                }
            }

            try {
                int targetNum = sc.nextInt();
                targetPlayer = Arrays.stream(allPlayers)
                        .filter(p -> p.getPlayerNumber() == targetNum && !p.equals(currentPlayer)) // exclude self
                        .findFirst()
                        .orElse(null);

                if (targetPlayer == null) {
                    System.out.println("Invalid choice. You can’t pick yourself. Try again.");
                    continue;
                }
                break; // valid target
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                sc.next(); // consume invalid token
            }
        }

        // Execute action
        targetPlayer.getBench().subtractPigeons(2);
        System.out.println("Player " + targetPlayer.getPlayerNumber() + " loses 2 pigeons.");
    }

    public static void ohMyPigeons(Player[] allPlayers) {
        Random rand = new Random();
        Player currentPlayer = Datastore.retrieveValue("currentPlayer", Player.class);
        int diceRoll = rand.nextInt(6) + 1;
        switch (diceRoll) {
            case 1,6:
                System.out.println("Flick poo");
                flickPoo(allPlayers);
                break;
            case 2:
                System.out.println("Take 2 pigeons from other players");
                takeOnePigeonFromAnotherPlayer(allPlayers);
                takeOnePigeonFromAnotherPlayer(allPlayers);
                break;
            case 3:
                System.out.println("Take 3 pigeons from the flock");
                currentPlayer.getBench().addPigeons(3);
                break;
            case 4:
                System.out.println("Take 4 pigeons from other players");
                takeOnePigeonFromAnotherPlayer(allPlayers);
                takeOnePigeonFromAnotherPlayer(allPlayers);
                takeOnePigeonFromAnotherPlayer(allPlayers);
                takeOnePigeonFromAnotherPlayer(allPlayers);
                break;
            case 5:
                System.out.println("Take 5 pigeons from the flock");
                currentPlayer.getBench().addPigeons(5);
                break;
        }
    }

    public static void flickPoo(Player[] allPlayers) {
        Player currentPlayer = Datastore.retrieveValue("currentPlayer", Player.class);
        Scanner sc = new Scanner(System.in);
        Player targetPlayer = null;

        while (true) {
            System.out.println("Who would you like to flick poo at?");
            // List valid target players (excluding current player)
            for (Player p : allPlayers) {
                if (!p.equals(currentPlayer)) {
                    System.out.println("Player " + p.getPlayerNumber());
                }
            }

            try {
                int targetNum = sc.nextInt();
                targetPlayer = Arrays.stream(allPlayers)
                        .filter(p -> p.getPlayerNumber() == targetNum && !p.equals(currentPlayer))
                        .findFirst()
                        .orElse(null);

                if (targetPlayer == null) {
                    System.out.println("Invalid choice. You can't flick poo at yourself!");
                    continue;
                }
                break; // valid target
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                sc.next(); // consume invalid token
            }
        }
        Random rand = new Random();
        int random = rand.nextInt(14);
        switch (random) {
            case 0,1,2,3:
                System.out.println("You missed! No pigeons hit.");
                break;
            case 4,5,6:
                System.out.println("You took out 1 pigeon!");
                targetPlayer.getBench().subtractPigeons(1);
                break;
            case 7,8,9:
                System.out.println("You took out 2 pigeons!");
                targetPlayer.getBench().subtractPigeons(2);
                break;
            case 10,11:
                System.out.println("You took out 3 pigeons!");
                targetPlayer.getBench().subtractPigeons(3);
                break;
            case 12:
                System.out.println("You took out 4 pigeons!");
                targetPlayer.getBench().subtractPigeons(4);
                break;
            case 13:
                System.out.println("You took out 5 pigeons!");
                targetPlayer.getBench().subtractPigeons(5);
                break;

        }
    }
}
