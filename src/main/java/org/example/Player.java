package org.example;

import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final int playerNumber;
    private final Bench bench;
    private final List<Card> hand;

    public Player(int playerNumber) {
        this.playerNumber = playerNumber;
        this.bench = new Bench();
        this.hand = new ArrayList<>(3); // empty, but capacity set to 3
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public Bench getBench() {
        return bench;
    }

    public List<Card> getHand() {
        return new ArrayList<>(hand); // safe copy
    }

    public void addCardToHand(Card c) {
        if (hand.size() < 3) {
            hand.add(c);
        } else {
            throw new IllegalStateException("Hand already has 3 cards!");
        }
    }

    public void printHand() {
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);
            System.out.println(i + ": " + card.getName() + " - " + card.getDescription());
        }
    }

    public void playCard(int index) {
        Card card = hand.get(index);
        card.performAction();
        hand.remove(index);
    }
}
