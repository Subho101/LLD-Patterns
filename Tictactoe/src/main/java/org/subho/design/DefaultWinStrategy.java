package org.subho.design;

public class DefaultWinStrategy implements WinStrategy{
    @Override
    public boolean isWin(Board board, char symbol) {
        return checkRow(board, symbol) ||
                checkCol(board, symbol) ||
                checkDiagonal(board, symbol) ||
                checkAntiDiagonal(board, symbol);
    }

    private boolean checkRow(Board board, char symbol) {
        int size = board.getSize();

        for(int row=0; row<size; row++) {
            for(int col=0; col<size; col++) {
                if(board.getCell(row, col) != symbol) return false;
            }
        }

        return true;
    }

    private boolean checkCol(Board board, char symbol) {
        int size = board.getSize();

        for(int col=0; col<size; col++) {
            for(int row=0; row<size; row++) {
                if(board.getCell(row, col) != symbol) return false;
            }
        }

        return true;
    }

    private boolean checkDiagonal(Board board, char symbol) {
        int size = board.getSize();

        for(int index=0; index<size; index++) {
            if(board.getCell(index, index) != symbol) return false;
        }

        return true;
    }

    private boolean checkAntiDiagonal(Board board, char symbol) {
        int size = board.getSize();

        for(int index=0; index<size; index++) {
            if(board.getCell(index, size-1-index) != symbol) return false;
        }

        return true;
    }
}
