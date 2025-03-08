package org.subho.design;

public class DefaultMoveStrategy implements MoveStrategy{
    @Override
    public boolean isValidMove(Board board, int x, int y) {
        return (x >= 0 && y >= 0 && x < board.getSize() && y < board.getSize() && board.getCell(x, y) == '\0');
    }
}
