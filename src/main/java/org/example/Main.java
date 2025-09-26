package org.example;

import java.util.*;

/*
Game Flow:
Initialise game:
1. Ask how many players
2. Create that many players and take 3 random cards from the deck to be in their starting hand.
3. Player 1's turn, are you ready to see your cards? (Y/n)
4. Player 1 then sees the cards and chooses one to use.
5. If the card requires a target player the player is asked which player to target.
6. The card action happens.
7. Repeat the process until a player has 9 pigeons.
 */


public class Main {
    private static final String csvPath= "src/main/resources/data/card-data.csv";


    public static void main(String[] args) {
        String pigeon = """
       __
     >(o )__
       (  ._\\
        `---'
""";

        System.out.println(pigeon);
        Scanner sc = new Scanner(System.in);
        int numPlayers = 0;
        while (true) {
            System.out.println("How many players are playing? (Enter 2-5)");
            try {
                numPlayers = sc.nextInt();
                if (numPlayers < 2 || numPlayers > 5) {
                    System.out.println("Please enter a number between 2 and 5.");
                    continue;
                }
                break; // valid input
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                sc.next(); // consume invalid token
            }
        }
        System.out.printf("You have chosen " + numPlayers + " players\n");

        // Create the players
        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= numPlayers; i++) {
            players.add(new Player(i)); // assumes your Player has a constructor Player(int playerNumber)
        }

        // Verify they were created
        for (Player p : players) {
            System.out.println("Created Player " + p.getPlayerNumber());
        }

        // Create the deck
        List<Card> startingDeck = DeckBuilder.buildDeck(csvPath, players);
        Deck deck = new Deck(startingDeck);
        deck.shuffle();
        System.out.println("New deck created with " + deck.getDeck().size() + " cards");

        // Deal 3 cards to each player
        for (Player p : players) {
            p.addCardToHand(deck.draw());
            p.addCardToHand(deck.draw());
            p.addCardToHand(deck.draw());
        }

        // Verify each player has 3 cards
        for (Player p : players) {
            System.out.println("Player " + p.getPlayerNumber() + " has " + p.getHand().size() + " cards");
        }

        int counter = 0;
        while (Utils.allBenchesBelowNine(players)) {
            // Set player
            int playerTurn = counter % numPlayers;
            Datastore.saveValue("currentPlayer", players.get(playerTurn));
            Player currentPlayer = Datastore.retrieveValue("currentPlayer", Player.class);
            System.out.println("");
            System.out.println("Current player: Player " + currentPlayer.getPlayerNumber());
            // --- Choose a card ---
            int chosenCard = -1;
            while (true) {
                System.out.println("Choose a card to play: ");
                currentPlayer.printHand();
                try {
                    chosenCard = sc.nextInt();
                    if (chosenCard < 0 || chosenCard >= currentPlayer.getHand().size()) {
                        System.out.println("Invalid choice. Please enter a number between 0 and " + (currentPlayer.getHand().size() - 1));
                        continue;
                    }
                    break; // valid choice
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter an integer.");
                    sc.next(); // consume invalid token
                }
            }
            currentPlayer.playCard(chosenCard);
            System.out.println("Card played");
            currentPlayer.addCardToHand(deck.draw());
            System.out.println("Card drawn");
            for (Player p : players) {
                System.out.println("Player " + p.getPlayerNumber() + " has " + p.getBench().getNumPigeons() + " pigeons");
            }
            counter += 1;
        }
        for (Player p : players) {
            if (p.getBench().getNumPigeons() >= 9)
                System.out.println("Player " + p.getPlayerNumber() + " has won the game!");
        }
        System.out.println(pigeon);
        sc.close();

    }
}
