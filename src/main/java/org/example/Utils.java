package org.example;

import java.util.List;

public class Utils {
    public static boolean allBenchesBelowNine(List<Player> players) {
        for (Player player : players) {
            if (player.getBench().getNumPigeons() >= 9) return false;
        }
        return true;
    }
}
