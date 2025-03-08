package org.subho.design;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final char[][] board;
    private final List<Player> observers = new ArrayList<>();

    public Board(int size) {
        board = new char[size][size];
    }

    public void addObserver(Player player) {
        observers.add(player);
    }

    public void notifyObserver() {
        observers.forEach(player -> player.update(this));
    }

    public void updateBoard(int x, int y, char symbol) {
        board[x][y] = symbol;
        notifyObserver();
    }

    public char getCell(int x, int y) {
        return board[x][y];
    }

    public int getSize() {
        return board.length;
    }
}
