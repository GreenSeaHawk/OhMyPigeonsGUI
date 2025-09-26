package org.example;

import java.util.List;

public class CardFactory {

    public static CardAction getAction(String actionName, List<Player> players) {
        return (gui) -> {
            switch (actionName) {
                case "removeOnePigeonFromEveryoneElse":
                    Actions.removeOnePigeonFromEveryoneElse(players, gui);
                    break;
                case "takeThreePigeonsFromFlock":
                    Actions.takeThreePigeonsFromFlock(players, gui);
                    break;
                case "takeTwoGiveOnePigeon":
                    Actions.takeTwoGiveOnePigeon(players, gui);
                    break;
                case "slideRight":
                    Actions.slideRight(players, gui);
                    break;
                case "slideLeft":
                    Actions.slideLeft(players, gui);
                    break;
                case "belowFourTakeTwo":
                    Actions.belowFourTakeTwo(players, gui);
                    break;
                case "allTakeOne":
                    Actions.allTakeOne(players, gui);
                    break;
                case "takeOnePigeonFromAnotherPlayer":
                    Actions.takeOnePigeonFromAnotherPlayer(players, gui);
                    break;
                case "swapBench":
                    Actions.swapBench(players, gui);
                    break;
                case "takeOneFromAll":
                    Actions.takeOneFromAll(players, gui);
                    break;
                case "takeThreeFromMost":
                    Actions.takeThreeFromMost(players, gui);
                    break;
                case "onePlayerLoseTwo":
                    Actions.onePlayerLoseTwo(players, gui);
                    break;
                case "ohMyPigeons":
                    Actions.ohMyPigeons(players, gui);
                    break;
                case "flickPoo":
                    Actions.flickPoo(players, gui);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown action: " + actionName);
            }
        };
    }
}
