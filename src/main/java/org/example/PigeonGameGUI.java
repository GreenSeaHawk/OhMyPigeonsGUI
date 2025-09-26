package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class PigeonGameGUI extends JFrame {
    private final List<Player> players;
//    private final Deck deck;
    private int currentPlayerIndex = 0;
    private Deck deck;

    private JLabel pigeonsLabel;
    private JPanel handPanel;
    private JTextArea gameLog;

    public PigeonGameGUI(List<Player> players, Deck deck) {
        this.players = players;
        this.deck = deck;

        setTitle("Oh My Pigeons!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(900, 600);

        // Top: current player and pigeon counts
        pigeonsLabel = new JLabel();
        add(pigeonsLabel, BorderLayout.NORTH);

        // Center: hand panel
        handPanel = new JPanel();
        add(handPanel, BorderLayout.CENTER);

        // Bottom: game log
        gameLog = new JTextArea(10, 50);
        gameLog.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(gameLog);
        add(scrollPane, BorderLayout.SOUTH);

        updateTurn();
        setVisible(true);
    }

    public void appendLog(String message) {
        gameLog.append(message + "\n");
    }

    public Player promptChoosePlayer(String message, Player exclude) {
        Object[] options = players.stream()
                .filter(p -> p != exclude)
                .map(p -> "Player " + p.getPlayerNumber())
                .toArray();
        int choice = JOptionPane.showOptionDialog(this, message, "Choose Player",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                options, options[0]);
        if (choice < 0) return null;
        return players.stream().filter(p -> p != exclude).toList().get(choice);
    }

    private void updatePigeonCounts() {
        StringBuilder sb = new StringBuilder("<html>");
        for (Player p : players) {
            sb.append("Player ").append(p.getPlayerNumber())
                    .append(": ").append(p.getBench().getNumPigeons()).append(" pigeons<br>");
        }
        sb.append("</html>");
        pigeonsLabel.setText(sb.toString());
    }

    public void updateTurn() {
        Player currentPlayer = players.get(currentPlayerIndex);
        Datastore.saveValue("currentPlayer", currentPlayer);

        updatePigeonCounts();

        // Update hand buttons
        handPanel.removeAll();
        List<Card> hand = currentPlayer.getHand();
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);
            JButton cardButton = new JButton("<html>" + card.getName() + "<br>" + card.getDescription() + "</html>");
            int cardIndex = i;
            cardButton.addActionListener((ActionEvent e) -> playCard(currentPlayer, cardIndex));
            handPanel.add(cardButton);
        }

        handPanel.revalidate();
        handPanel.repaint();

        appendLog("Current player: Player " + currentPlayer.getPlayerNumber());
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    private void playCard(Player currentPlayer, int cardIndex) {
        Card playedCard = currentPlayer.playCard(cardIndex, this); // ✅ now matches
        appendLog("Player " + currentPlayer.getPlayerNumber() + " played " + playedCard.getName());

        // Draw new card
        if (deck.deckSize() > 0) {
            currentPlayer.addCardToHand(deck.draw());
        }

        // Check win condition
        for (Player p : players) {
            if (p.getBench().getNumPigeons() >= 9) {
                JOptionPane.showMessageDialog(this, "Player " + p.getPlayerNumber() + " has won the game!");
                System.exit(0);
            }
        }

        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        updateTurn();
    }
}
