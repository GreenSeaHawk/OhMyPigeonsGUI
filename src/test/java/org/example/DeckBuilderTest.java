package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeckBuilderTest {

    private final String csvPath= "src/main/resources/data/card-data.csv";
    private final int numPlayers = 3;

    @Test
    void testBuildDeck () {
        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= numPlayers; i++) {
            players.add(new Player(i));
        }
        List<Card> startingDeck = DeckBuilder.buildDeck(csvPath, players);
        Deck deck = new Deck(startingDeck);
        assertEquals(29,deck.getDeck().size());

    }
}
