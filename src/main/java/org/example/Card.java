package org.example;

public class Card {
    private final String name;
    private final String description;
    private final Runnable action; // the method to run when this card is played

    public Card(String name, String description, Runnable action) {
        this.name = name;
        this.description = description;
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void performAction() {
        action.run(); // execute the method passed in
    }
}
