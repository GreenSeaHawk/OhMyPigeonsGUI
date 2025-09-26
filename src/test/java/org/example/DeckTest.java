package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {
// Need to test the methods in Deck.java
    private Card c1,c2,c3,c4,c5;
    private List<Card> cardsList;
    private Deck deck;
    private Player p1,p2;


    @BeforeEach
    void setUp() {
        p1 = new Player(1);
        p2 = new Player(2);
        Player[] all = new Player[]{p1, p2};
        c1 = new Card(
                "AHHHH!!",
                "All other players return 1 pigeon to the flock",
                () -> Actions.removeOnePigeonFromEveryoneElse(all)
        );

        c2 = new Card(
                "I'll take that",
                "Take 1 pigeon from another player",
                () -> Actions.takeOnePigeonFromAnotherPlayer(all)
        );

        c3 = new Card(
                "Let's get this bread",
                "Take 3 pigeons from the flock",
                () -> Actions.takeThreePigeonsFromFlock(all)
        );
        c4 = new Card(
                "Sliiiide to the left",
                "All players pass their bench to the left",
                () -> Actions.slideLeft(all)
        );
        c5 = new Card(
                "Sliiiide to the left",
                "All players pass their bench to the left",
                () -> Actions.slideLeft(all)
        );
        cardsList = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));
        deck = new Deck(cardsList);
    }

    @Test
    void testDeskDiscardSize() {
        assertEquals(5,deck.deckSize());
        assertEquals(0,deck.discardSize());
    }

    @Test
    void testGetDeck() {
        List<Card> expectedDeck = new ArrayList<>(Arrays.asList(c1, c2, c3, c4, c5));
        System.out.println(expectedDeck);
        assertEquals(expectedDeck, deck.getDeck());
    }

    @Test
    void testDiscard() {
        deck.discard(c1);
        deck.discard(c2);
        assertEquals(2,deck.discardSize());

        List<Card> expectedDeck = new ArrayList<>(Arrays.asList(c1,c2));
        assertEquals(expectedDeck, deck.getDiscard());
    }

    @Test
    void testShuffle() {
        List<Card> original = new ArrayList<>(deck.getDeck());
        deck.shuffle();
        List<Card> shuffled = deck.getDeck();

        assertTrue(shuffled.containsAll(original));
        assertEquals(original.size(), shuffled.size());

        boolean orderChanged = false;
        for (int i=0; i < 10; i++) {
            deck.shuffle();
            if (!deck.getDeck().equals(original)) {
                orderChanged = true;
                break;
            }
        }
        assertTrue(orderChanged);
    }

    @Test
    void testReshuffleFromDiscard() {
        List<Card> noCards = new ArrayList<>();
        Deck testDeck = new Deck(noCards);
        assertEquals(0, testDeck.deckSize());
        testDeck.discard(c1);
        testDeck.discard(c2);
        testDeck.discard(c3);
        testDeck.discard(c4);
        testDeck.reshuffleFromDiscard();
        List<Card> expected = new ArrayList<>(Arrays.asList(c1, c2, c3, c4));
        assertEquals(expected.size(), testDeck.getDeck().size());
        assertTrue(testDeck.getDeck().containsAll(expected));
    }

    @Test
    void testDraw() {
        assertEquals(deck.draw(), c1);
        assertEquals(4, deck.getDeck().size());
    }

    @Test
    void testDrawWhenEmpty() {
        List<Card> noCards = new ArrayList<>();
        Deck testDeck = new Deck(noCards);
        testDeck.discard(c1);
        testDeck.discard(c2);
        testDeck.discard(c3);
        testDeck.discard(c4);
        testDeck.draw();
        assertEquals(3, testDeck.getDeck().size());
    }
}
