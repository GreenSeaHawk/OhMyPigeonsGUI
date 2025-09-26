package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> deck;
    private List<Card> discardPile;

    public Deck(List<Card> startingCards) {
        this.deck = new ArrayList<>(startingCards); // copy starting deck
        this.discardPile = new ArrayList<>();
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public Card draw() {
        if (deck.isEmpty()) {
            reshuffleFromDiscard();
        }
        return deck.remove(0); // take from top
    }

    public void discard(Card card) {
        discardPile.add(card);
    }

    public void reshuffleFromDiscard() {
        deck.addAll(discardPile);
        discardPile.clear();
        shuffle();
    }

    public List<Card> getDeck() {
        return deck;
    }

    public List<Card> getDiscard() {
        return discardPile;
    }

    public int deckSize() { return deck.size(); }
    public int discardSize() { return discardPile.size(); }
}
