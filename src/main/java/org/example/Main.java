package org.example;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String csvPath = "src/main/resources/data/card-data.csv";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::startGame);
    }

    private static void startGame() {
        int numPlayers = promptNumberOfPlayers();
        if (numPlayers == -1) return; // user cancelled

        // Create the players
        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= numPlayers; i++) {
            players.add(new Player(i));
        }

        // 🔑 Create a placeholder GUI first
        PigeonGameGUI gui = new PigeonGameGUI(players, null);

        // Build the deck (now GUI is available for actions)
        List<Card> startingDeck = DeckBuilder.buildDeck(csvPath, players, gui);
        Deck deck = new Deck(startingDeck);
        deck.shuffle();

        // Deal 3 cards to each player
        for (Player p : players) {
            for (int i = 0; i < 3; i++) {
                p.addCardToHand(deck.draw());
            }
        }

        // 🔑 Now link the deck properly to GUI
        gui.setDeck(deck);
        gui.updateTurn();
    }

    private static int promptNumberOfPlayers() {
        Integer[] options = {2, 3, 4, 5};
        Integer choice = (Integer) JOptionPane.showInputDialog(
                null,
                "How many players are playing?",
                "Number of Players",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
        if (choice == null) return -1; // user cancelled
        return choice;
    }
}
