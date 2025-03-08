package org.subho.design;

public class PlayerFactory {
    public static Player createPlayer(char symbol, boolean isAI) {
        if(isAI) {
            return new AIPlayer(symbol);
        }
        return new HumanPlayer(symbol);
    }
}
