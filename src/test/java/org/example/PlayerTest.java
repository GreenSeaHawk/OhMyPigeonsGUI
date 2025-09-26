package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {

    private Player p1;
    private Player p2;
    private Card c1;
    private Card c2;
    private Card c3;
    private Card c4;
    private Card c5;
    private Card c6;

    @BeforeEach
    void setUp() {
        p1 = new Player(1);
        p2 = new Player(2);
        Player[] all = new Player[]{p1, p2};
        c1 = new Card(
                "AHHHH!!",
                "All other players return 1 pigeon to the flock",
                () -> Actions.removeOnePigeonFromEveryoneElse(all) // <- wrapped in lambda
        );
        c2 = new Card(
                "AHHHH!!",
                "All other players return 1 pigeon to the flock",
                () -> Actions.removeOnePigeonFromEveryoneElse(all) // <- wrapped in lambda
        );
        c3 = new Card(
                "I'll take that",
                "Take 1 pigeon from another player",
                () -> Actions.takeOnePigeonFromAnotherPlayer(all) // <- wrapped in lambda
        );
        c4 = new Card(
                "AHHHH!!",
                "All other players return 1 pigeon to the flock",
                () -> Actions.removeOnePigeonFromEveryoneElse(all) // <- wrapped in lambda
        );
        c5 = new Card(
                "AHHHH!!",
                "All other players return 1 pigeon to the flock",
                () -> Actions.removeOnePigeonFromEveryoneElse(all) // <- wrapped in lambda
        );
        c6 = new Card(
                "I'll take that",
                "Take 1 pigeon from another player",
                () -> Actions.takeOnePigeonFromAnotherPlayer(all) // <- wrapped in lambda
        );
    }

    @Test
    void testGetPlayerNumber() {
        assertEquals(1, p1.getPlayerNumber());
    }

    @Test
    void testGetBench() {
        assertEquals(3, p1.getBench().getNumPigeons());
    }

    @Test
    void testAddCardToHand() {
        p1.addCardToHand(c1);
        assertEquals(1,p1.getHand().size());
        p1.addCardToHand(c2);
        assertEquals(2,p1.getHand().size());
    }

    @Test
    void testPrintHand() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        p1.addCardToHand(c1);
        p1.addCardToHand(c2);

        p1.printHand();

        System.setOut(System.out);

        String expected =
                "0: AHHHH!! - All other players return 1 pigeon to the flock\n" +
                "1: AHHHH!! - All other players return 1 pigeon to the flock\n";

        assertEquals(expected, outContent.toString());
    }

    @Test
    void testPlayCard() {
        p1.addCardToHand(c1);
        p1.addCardToHand(c2);

        Datastore.saveValue("currentPlayer", p1);

        p1.playCard(1);

        assertEquals(3,p1.getBench().getNumPigeons());
        assertEquals(2,p2.getBench().getNumPigeons());
    }
}
