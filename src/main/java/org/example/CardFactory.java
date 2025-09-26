package org.example;

import java.util.List;

public class CardFactory {
    public static Runnable getAction(String actionName, List<Player> players) {
        switch (actionName) {
            case "removeOnePigeonFromEveryoneElse":
                return () -> Actions.removeOnePigeonFromEveryoneElse(players.toArray(new Player[0]));
            case "takeThreePigeonsFromFlock":
                return () -> Actions.takeThreePigeonsFromFlock(players.toArray(new Player[0]));
            case "takeTwoGiveOnePigeon":
                return () -> Actions.takeTwoGiveOnePigeon(players.toArray(new Player[0]));
            case "slideRight":
                return () -> Actions.slideRight(players.toArray(new Player[0]));
            case "slideLeft":
                return () -> Actions.slideLeft(players.toArray(new Player[0]));
            case "belowFourTakeTwo":
                return () -> Actions.belowFourTakeTwo(players.toArray(new Player[0]));
            case "allTakeOne":
                return () -> Actions.allTakeOne(players.toArray(new Player[0]));
            case "takeOnePigeonFromAnotherPlayer":
                return () -> Actions.takeOnePigeonFromAnotherPlayer(players.toArray(new Player[0]));
            case "swapBench":
                return () -> Actions.swapBench(players.toArray(new Player[0]));
            case "takeOneFromAll":
                return () -> Actions.takeOneFromAll(players.toArray(new Player[0]));
            case "takeThreeFromMost":
                return () -> Actions.takeThreeFromMost(players.toArray(new Player[0]));
            case "onePlayerLoseTwo":
                return () -> Actions.onePlayerLoseTwo(players.toArray(new Player[0]));
            case "ohMyPigeons":
                return () -> Actions.ohMyPigeons(players.toArray(new Player[0]));
            default:
                throw new IllegalArgumentException("Unknown action: " + actionName);
        }
    }
}

