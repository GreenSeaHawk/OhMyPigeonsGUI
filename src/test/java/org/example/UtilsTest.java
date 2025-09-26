package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UtilsTest {
    @Test
    void testAllBenchesBelowNine() {
        int numPlayers = 3;
        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= numPlayers; i++) {
            players.add(new Player(i)); // assumes your Player has a constructor Player(int playerNumber)
        }
        assertTrue(Utils.allBenchesBelowNine(players));
        players.get(0).getBench().setNumberPigeons(9);
        assertFalse(Utils.allBenchesBelowNine(players));

    }
}
