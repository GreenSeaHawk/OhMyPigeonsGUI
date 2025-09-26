package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActionsTest {

    private final InputStream originalIn = System.in;
    private Player p1;
    private Player p2;
    private Player p3;
    private Player[] all;

    @AfterEach
    void restoreSystemIn() {
        System.setIn(originalIn); // reset after each test
    }

    @BeforeEach
    void setUp() {
        p1 = new Player(1);
        p2 = new Player(2);
        p3 = new Player(3);
        all = new Player[]{p1, p2, p3};
    }
    @Test
    void testRemoveOnePigeonFromEveryoneElse() {
        Card c1 = new Card(
                "AHHHH!!",
                "All other players return 1 pigeon to the flock",
                () -> Actions.removeOnePigeonFromEveryoneElse(all)
        );
        Datastore.saveValue("currentPlayer", p1);
        c1.performAction();

        assertEquals(3, p1.getBench().getNumPigeons());
        assertEquals(2, p2.getBench().getNumPigeons());
        assertEquals(2, p3.getBench().getNumPigeons());
    }

    @Test
    void testTakeOneFromTarget() {
        // Simulate user typing "2" + Enter
        String simulatedInput = "2\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Card c1 = new Card(
                "I'll take that",
                "Take 1 pigeon from another player",
                () -> Actions.takeOnePigeonFromAnotherPlayer(all)
        );
        Datastore.saveValue("currentPlayer", p1);
        c1.performAction();

        assertEquals(4, p1.getBench().getNumPigeons());
        assertEquals(2, p2.getBench().getNumPigeons());
        assertEquals(3, p3.getBench().getNumPigeons());
    }

    @Test
    void testTakeThreePigeonsFromFlock() {
        Card c1 = new Card(
                "Let's get this bread",
                "Take 3 pigeons from the flock",
                () -> Actions.takeThreePigeonsFromFlock(all)
        );
        Datastore.saveValue("currentPlayer", p1);
        c1.performAction();

        assertEquals(6, p1.getBench().getNumPigeons());
        assertEquals(3, p2.getBench().getNumPigeons());
        assertEquals(3, p3.getBench().getNumPigeons());
    }

    @Test
    void testSlideLeft() {
        Card c1 = new Card(
                "Sliiiide to the left",
                "All players pass their bench to the left",
                () -> Actions.slideLeft(all)
        );
        p1.getBench().setNumberPigeons(5);
        p2.getBench().setNumberPigeons(2);
        p3.getBench().setNumberPigeons(3);

        c1.performAction();

        assertEquals(2, p1.getBench().getNumPigeons());
        assertEquals(3, p2.getBench().getNumPigeons());
        assertEquals(5, p3.getBench().getNumPigeons());

        System.out.println("P1: " + p1.getBench().getNumPigeons());
        System.out.println("P2: " + p2.getBench().getNumPigeons());
        System.out.println("P3: " + p3.getBench().getNumPigeons());
    }

    @Test
    void testSlideLeftTwoPlayers() {
        Player px1 = new Player(1);
        Player px2 = new Player(2);
        Player[] players = new Player[]{px1,px2};

        Card c1 = new Card(
                "Sliiiide to the left",
                "All players pass their bench to the left",
                () -> Actions.slideLeft(players)
        );
        px1.getBench().setNumberPigeons(5);
        px2.getBench().setNumberPigeons(2);

        c1.performAction();

        assertEquals(2, px1.getBench().getNumPigeons());
        assertEquals(5, px2.getBench().getNumPigeons());

        System.out.println("P1: " + px1.getBench().getNumPigeons());
        System.out.println("P2: " + px2.getBench().getNumPigeons());
    }

    @Test
    void testSlideRight() {
        Card c1 = new Card(
                "Sliiiide to the right",
                "All players pass their bench to the right",
                () -> Actions.slideRight(all)
        );
        p1.getBench().setNumberPigeons(5);
        p2.getBench().setNumberPigeons(2);
        p3.getBench().setNumberPigeons(3);

        c1.performAction();

        assertEquals(3, p1.getBench().getNumPigeons());
        assertEquals(5, p2.getBench().getNumPigeons());
        assertEquals(2, p3.getBench().getNumPigeons());

        System.out.println("P1: " + p1.getBench().getNumPigeons());
        System.out.println("P2: " + p2.getBench().getNumPigeons());
        System.out.println("P3: " + p3.getBench().getNumPigeons());
    }

    @Test
    void testSlideRightTwoPlayers() {
        Player px1 = new Player(1);
        Player px2 = new Player(2);
        Player[] players = new Player[]{px1,px2};

        Card c1 = new Card(
                "Sliiiide to the right",
                "All players pass their bench to the right",
                () -> Actions.slideRight(players)
        );
        px1.getBench().setNumberPigeons(5);
        px2.getBench().setNumberPigeons(2);

        c1.performAction();

        assertEquals(2, px1.getBench().getNumPigeons());
        assertEquals(5, px2.getBench().getNumPigeons());

        System.out.println("P1: " + px1.getBench().getNumPigeons());
        System.out.println("P2: " + px2.getBench().getNumPigeons());
    }

    @Test
    void testTwoGiveOnePigeon() {
        // Simulate user typing "2" + Enter
        String simulatedInput = "2\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Card c1 = new Card(
                "Be not afraid my child",
                "Take 2 pigeons from the flock and give 1 pigeon from the flock to another player",
                () -> Actions.takeTwoGiveOnePigeon(all)
        );
        Datastore.saveValue("currentPlayer", p1);
        c1.performAction();

        assertEquals(5, p1.getBench().getNumPigeons());
        assertEquals(4, p2.getBench().getNumPigeons());
        assertEquals(3, p3.getBench().getNumPigeons());
    }

    @Test
    void testBelowFourTakeTwo() {
        Card c1 = new Card(
                "Pigeon party",
                "All players with 3 or fewer pigeons take 2 from the flock",
                () -> Actions.belowFourTakeTwo(all)
        );
        p1.getBench().setNumberPigeons(2);
        p2.getBench().setNumberPigeons(4);

        c1.performAction();

        assertEquals(4, p1.getBench().getNumPigeons());
        assertEquals(4, p2.getBench().getNumPigeons());
        assertEquals(5, p3.getBench().getNumPigeons());
    }

    @Test
    void testAllTakeOne() {
        Card c1 = new Card(
                "Is this a pigeon?",
                "All players take 1 pigeon from the flock",
                () -> Actions.allTakeOne(all)
        );
        p1.getBench().setNumberPigeons(2);
        p2.getBench().setNumberPigeons(4);

        c1.performAction();

        assertEquals(3, p1.getBench().getNumPigeons());
        assertEquals(5, p2.getBench().getNumPigeons());
        assertEquals(4, p3.getBench().getNumPigeons());
    }

    @Test
    void testSwapBench() {
        // Simulate user typing "2" + Enter
        String simulatedInput = "3\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        Card c1 = new Card(
                "Swapsies",
                "Trade benches with another player",
                () -> Actions.swapBench(all)
        );
        p1.getBench().setNumberPigeons(5);
        p2.getBench().setNumberPigeons(2);
        p3.getBench().setNumberPigeons(3);
        Datastore.saveValue("currentPlayer", p1);

        c1.performAction();

        assertEquals(3, p1.getBench().getNumPigeons());
        assertEquals(2, p2.getBench().getNumPigeons());
        assertEquals(5, p3.getBench().getNumPigeons());

        System.out.println("P1: " + p1.getBench().getNumPigeons());
        System.out.println("P2: " + p2.getBench().getNumPigeons());
        System.out.println("P3: " + p3.getBench().getNumPigeons());
    }

    @Test
    void testTakeOneFromAll() {
        Card c1 = new Card(
                "Attack",
                "Take 1 pigeon from each other player",
                () -> Actions.takeOneFromAll(all)
        );
        p1.getBench().setNumberPigeons(2);
        p2.getBench().setNumberPigeons(4);
        p3.getBench().setNumberPigeons(0);
        Datastore.saveValue("currentPlayer", p1);

        c1.performAction();

        assertEquals(3, p1.getBench().getNumPigeons());
        assertEquals(3, p2.getBench().getNumPigeons());
        assertEquals(0, p3.getBench().getNumPigeons());
    }

    @Test
    void testTakeThreeFromMostOneMax() {
        Card c1 = new Card(
                "Why",
                "The player with the most pigeons returns 3 to the flock. (If there's a tie you choose who must return their pigeons to the flock)",
                () -> Actions.takeThreeFromMost(all)
        );
        p1.getBench().setNumberPigeons(5);
        p2.getBench().setNumberPigeons(2);
        p3.getBench().setNumberPigeons(3);

        c1.performAction();

        assertEquals(2, p1.getBench().getNumPigeons());
        assertEquals(2, p2.getBench().getNumPigeons());
        assertEquals(3, p3.getBench().getNumPigeons());

        System.out.println("P1: " + p1.getBench().getNumPigeons());
        System.out.println("P2: " + p2.getBench().getNumPigeons());
        System.out.println("P3: " + p3.getBench().getNumPigeons());
    }

    @Test
    void testTakeThreeFromMostTwoMax() {
        String simulatedInput = "3\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        Card c1 = new Card(
                "Why",
                "The player with the most pigeons returns 3 to the flock. (If there's a tie you choose who must return their pigeons to the flock)",
                () -> Actions.takeThreeFromMost(all)
        );
        p1.getBench().setNumberPigeons(5);
        p2.getBench().setNumberPigeons(6);
        p3.getBench().setNumberPigeons(6);

        c1.performAction();

        assertEquals(5, p1.getBench().getNumPigeons());
        assertEquals(6, p2.getBench().getNumPigeons());
        assertEquals(3, p3.getBench().getNumPigeons());

        System.out.println("P1: " + p1.getBench().getNumPigeons());
        System.out.println("P2: " + p2.getBench().getNumPigeons());
        System.out.println("P3: " + p3.getBench().getNumPigeons());
    }

    @Test
    void testOnePlayerLostTwo() {
        // Simulate user typing "2" + Enter
        String simulatedInput = "2\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Card c1 = new Card(
                "Byee",
                "Choose another player to return 2 pigeons to the flock",
                () -> Actions.onePlayerLoseTwo(all)
        );

        c1.performAction();

        assertEquals(3, p1.getBench().getNumPigeons());
        assertEquals(1, p2.getBench().getNumPigeons());
        assertEquals(3, p3.getBench().getNumPigeons());
    }
}

