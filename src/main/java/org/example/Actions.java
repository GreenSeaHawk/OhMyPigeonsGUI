package org.example;

import javax.swing.*;
import java.util.List;
import java.util.Random;

public class Actions {

    public static void removeOnePigeonFromEveryoneElse(List<Player> allPlayers, PigeonGameGUI gui) {
        Player currentPlayer = Datastore.retrieveValue("currentPlayer", Player.class);
        for (Player p : allPlayers) {
            if (p != currentPlayer && p.getBench().getNumPigeons() > 0) {
                p.getBench().subtractPigeons(1);
                gui.appendLog("Player " + p.getPlayerNumber() + " loses 1 pigeon.");
            }
        }
        gui.updateTurn();
    }

    public static void takeOnePigeonFromAnotherPlayer(List<Player> allPlayers, PigeonGameGUI gui) {
        Player currentPlayer = Datastore.retrieveValue("currentPlayer", Player.class);
        Player target = gui.promptChoosePlayer("Take 1 pigeon from which player?", currentPlayer);
        if (target != null) {
            if (target.getBench().getNumPigeons() > 0) {
                target.getBench().subtractPigeons(1);
                currentPlayer.getBench().addPigeons(1);
                gui.appendLog("Player " + currentPlayer.getPlayerNumber() + " took 1 pigeon from Player " + target.getPlayerNumber());
            } else {
                gui.appendLog("Player " + target.getPlayerNumber() + " has no pigeons to take.");
            }
        }
        gui.updateTurn();
    }

    public static void takeThreePigeonsFromFlock(List<Player> allPlayers, PigeonGameGUI gui) {
        Player currentPlayer = Datastore.retrieveValue("currentPlayer", Player.class);
        currentPlayer.getBench().addPigeons(3);
        gui.appendLog("Player " + currentPlayer.getPlayerNumber() + " takes 3 pigeons from the flock.");
        gui.updateTurn();
    }

    public static void slideLeft(List<Player> allPlayers, PigeonGameGUI gui) {
        int firstBench = allPlayers.get(0).getBench().getNumPigeons();
        for (int i = 0; i < allPlayers.size() - 1; i++) {
            allPlayers.get(i).getBench().setNumberPigeons(allPlayers.get(i + 1).getBench().getNumPigeons());
        }
        allPlayers.get(allPlayers.size() - 1).getBench().setNumberPigeons(firstBench);
        gui.appendLog("All benches slid left.");
        gui.updateTurn();
    }

    public static void slideRight(List<Player> allPlayers, PigeonGameGUI gui) {
        int lastBench = allPlayers.get(allPlayers.size() - 1).getBench().getNumPigeons();
        for (int i = allPlayers.size() - 1; i > 0; i--) {
            allPlayers.get(i).getBench().setNumberPigeons(allPlayers.get(i - 1).getBench().getNumPigeons());
        }
        allPlayers.get(0).getBench().setNumberPigeons(lastBench);
        gui.appendLog("All benches slid right.");
        gui.updateTurn();
    }

    public static void takeTwoGiveOnePigeon(List<Player> allPlayers, PigeonGameGUI gui) {
        Player currentPlayer = Datastore.retrieveValue("currentPlayer", Player.class);
        Player target = gui.promptChoosePlayer("Give 1 pigeon to which player?", currentPlayer);
        if (target != null) {
            target.getBench().addPigeons(1);
            currentPlayer.getBench().addPigeons(2);
            gui.appendLog("Player " + currentPlayer.getPlayerNumber() + " gave 1 pigeon to Player " + target.getPlayerNumber() + " and took 2 pigeons from the flock.");
        }
        gui.updateTurn();
    }

    public static void belowFourTakeTwo(List<Player> allPlayers, PigeonGameGUI gui) {
        for (Player p : allPlayers) {
            if (p.getBench().getNumPigeons() <= 3) {
                p.getBench().addPigeons(2);
                gui.appendLog("Player " + p.getPlayerNumber() + " had ≤3 pigeons and gains 2 pigeons.");
            }
        }
        gui.updateTurn();
    }

    public static void allTakeOne(List<Player> allPlayers, PigeonGameGUI gui) {
        for (Player p : allPlayers) {
            p.getBench().addPigeons(1);
            gui.appendLog("Player " + p.getPlayerNumber() + " takes 1 pigeon from the flock.");
        }
        gui.updateTurn();
    }

    public static void swapBench(List<Player> allPlayers, PigeonGameGUI gui) {
        Player currentPlayer = Datastore.retrieveValue("currentPlayer", Player.class);
        Player target = gui.promptChoosePlayer("Swap benches with which player?", currentPlayer);
        if (target != null) {
            int temp = currentPlayer.getBench().getNumPigeons();
            currentPlayer.getBench().setNumberPigeons(target.getBench().getNumPigeons());
            target.getBench().setNumberPigeons(temp);
            gui.appendLog("Player " + currentPlayer.getPlayerNumber() + " swapped benches with Player " + target.getPlayerNumber());
        }
        gui.updateTurn();
    }

    public static void takeOneFromAll(List<Player> allPlayers, PigeonGameGUI gui) {
        Player currentPlayer = Datastore.retrieveValue("currentPlayer", Player.class);
        for (Player p : allPlayers) {
            if (p != currentPlayer && p.getBench().getNumPigeons() > 0) {
                p.getBench().subtractPigeons(1);
                currentPlayer.getBench().addPigeons(1);
                gui.appendLog("Player " + currentPlayer.getPlayerNumber() + " took 1 pigeon from Player " + p.getPlayerNumber());
            }
        }
        gui.updateTurn();
    }

    public static void takeThreeFromMost(List<Player> allPlayers, PigeonGameGUI gui) {
        int max = allPlayers.stream().mapToInt(p -> p.getBench().getNumPigeons()).max().orElse(0);
        List<Player> maxPlayers = allPlayers.stream().filter(p -> p.getBench().getNumPigeons() == max).toList();

        Player target;
        if (maxPlayers.size() > 1) {
            target = gui.promptChoosePlayer("Multiple players have most pigeons. Choose one to lose 3 pigeons:", null);
        } else {
            target = maxPlayers.get(0);
        }

        if (target != null) {
            target.getBench().subtractPigeons(3);
            gui.appendLog("Player " + target.getPlayerNumber() + " loses 3 pigeons.");
        }
        gui.updateTurn();
    }

    public static void onePlayerLoseTwo(List<Player> allPlayers, PigeonGameGUI gui) {
        Player currentPlayer = Datastore.retrieveValue("currentPlayer", Player.class);
        Player target = gui.promptChoosePlayer("Choose a player to lose 2 pigeons:", currentPlayer);
        if (target != null) {
            target.getBench().subtractPigeons(2);
            gui.appendLog("Player " + target.getPlayerNumber() + " loses 2 pigeons.");
        }
        gui.updateTurn();
    }

    public static void ohMyPigeons(List<Player> allPlayers, PigeonGameGUI gui) {
        Player currentPlayer = Datastore.retrieveValue("currentPlayer", Player.class);
        int dice = new Random().nextInt(6) + 1;
        switch (dice) {
            case 1, 6 -> {
                gui.appendLog("Dice rolled: " + dice + " → Flick poo!");
                flickPoo(allPlayers, gui);
            }
            case 2 -> {
                gui.appendLog("Dice rolled: " + dice + " → Take 2 pigeons from other players");
                takeOnePigeonFromAnotherPlayer(allPlayers, gui);
                takeOnePigeonFromAnotherPlayer(allPlayers, gui);
            }
            case 3 -> {
                gui.appendLog("Dice rolled: " + dice + " → Take 3 pigeons from the flock");
                currentPlayer.getBench().addPigeons(3);
            }
            case 4 -> {
                gui.appendLog("Dice rolled: " + dice + " → Take 4 pigeons from other players");
                for (int i = 0; i < 4; i++) takeOnePigeonFromAnotherPlayer(allPlayers, gui);
            }
            case 5 -> {
                gui.appendLog("Dice rolled: " + dice + " → Take 5 pigeons from the flock");
                currentPlayer.getBench().addPigeons(5);
            }
        }
        gui.updateTurn();
    }

    public static void flickPoo(List<Player> allPlayers, PigeonGameGUI gui) {
        Player currentPlayer = Datastore.retrieveValue("currentPlayer", Player.class);
        Player target = gui.promptChoosePlayer("Flick poo at which player?", currentPlayer);
        if (target != null) {
            int roll = new Random().nextInt(14);
            int pigeonsLost;
            if (roll <= 3) pigeonsLost = 0;
            else if (roll <= 6) pigeonsLost = 1;
            else if (roll <= 9) pigeonsLost = 2;
            else if (roll <= 11) pigeonsLost = 3;
            else if (roll == 12) pigeonsLost = 4;
            else pigeonsLost = 5;

            target.getBench().subtractPigeons(pigeonsLost);
            gui.appendLog("Player " + target.getPlayerNumber() + " lost " + pigeonsLost + " pigeons from poo flick!");
        }
        gui.updateTurn();
    }
}
