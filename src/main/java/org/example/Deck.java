package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final List<Card> deck;
    private final List<Card> discardPile;

    public Deck(List<Card> startingCards) {
        this.deck = new ArrayList<>(startingCards);
        this.discardPile = new ArrayList<>();
        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public Card draw() {
        if (deck.isEmpty()) {
            reshuffleFromDiscard();
            if (deck.isEmpty()) {
                // No cards left at all
                throw new IllegalStateException("No cards left in deck or discard pile!");
            }
        }
        return deck.remove(0);
    }

    public void discard(Card card) {
        discardPile.add(card);
    }

    public void reshuffleFromDiscard() {
        if (!discardPile.isEmpty()) {
            deck.addAll(discardPile);
            discardPile.clear();
            shuffle();
        }
    }

    public int deckSize() {
        return deck.size();
    }

    public int discardSize() {
        return discardPile.size();
    }

    public List<Card> getDeck() {
        return new ArrayList<>(deck);
    }

    public List<Card> getDiscard() {
        return new ArrayList<>(discardPile);
    }
}
