package org.subho.design;

import java.util.Random;

public class AIPlayer extends Player{

    public AIPlayer(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public void makeMove(Board board) {
        Random random = new Random();
        int size = board.getSize();
        int x, y = 0;
        do {
            x = random.nextInt(size);
            y = random.nextInt(size);
        } while (board.getCell(x, y) != '\0');
        board.updateBoard(x, y, symbol);
    }

    @Override
    public void update(Board board) {
        System.out.println("For Player:" + symbol + " Board is updated");
    }
}
