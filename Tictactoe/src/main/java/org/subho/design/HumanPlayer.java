package org.subho.design;

public class HumanPlayer extends Player{

    public HumanPlayer(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public void makeMove(Board board) {
        System.out.println("Makes move");
    }

    @Override
    public void update(Board board) {
        System.out.println("For Player:" + symbol + " Board is updated");
    }
}
