package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class DeckBuilder {
    public static List<Card> buildDeck(String csvFilePath, List<Player> players) {
        List<Card> deck = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",", 4); // name, description, quantity, action
                String name = values[0];
                String description = values[1];
                int quantity = Integer.parseInt(values[2]);
                String actionName = values[3];

                Runnable action = CardFactory.getAction(actionName, players);

                for (int i = 0; i < quantity; i++) {
                    deck.add(new Card(name, description, action));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return deck;
    }
}

