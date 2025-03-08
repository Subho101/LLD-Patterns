package org.subho.design;

public interface MoveStrategy {
    boolean isValidMove(Board board, int x, int y);
}
