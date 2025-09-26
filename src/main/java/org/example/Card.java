package org.example;

public class Card {
    private final String name;
    private final String description;
    private final CardAction action; // functional interface

    public Card(String name, String description, CardAction action) {
        this.name = name;
        this.description = description;
        this.action = action;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }

    public void performAction(PigeonGameGUI gui) {
        action.execute(gui);
    }

}
