package org.subho.design;

import java.util.Scanner;

public class Tictactoe {
    private Board board;
    private Player currentPlayer;
    private Player player1;
    private Player player2;
    private MoveStrategy moveStrategy;
    private WinStrategy winStrategy;

    public Tictactoe() {
        GameController gameController = GameController.getInstance();
        board = new Board(3);
        player1 = PlayerFactory.createPlayer('X', false);
        player2 = PlayerFactory.createPlayer('O', true);
        currentPlayer = player1;
        moveStrategy = new DefaultMoveStrategy();
        winStrategy = new DefaultWinStrategy();
        board.addObserver(player1);
        board.addObserver(player2);
    }

    public void playGame() {
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.println("Player :: " + currentPlayer.getSymbol() + " 's turn");
            System.out.println("Enter row:: between 0 to " + (board.getSize()-1));
            int x = sc.nextInt();
            System.out.println("Enter col:: between 0 to " + (board.getSize()-1));
            int y = sc.nextInt();

            if(moveStrategy.isValidMove(board, x, y)) {
                board.updateBoard(x, y, currentPlayer.getSymbol());

                if(winStrategy.isWin(board, currentPlayer.getSymbol())) {
                    System.out.println("Player :: " + currentPlayer.getSymbol() + " wins!!");
                    break;
                }

                if(isDraw()) {
                    System.out.println("Game is draw!!");
                    break;
                }
                switchPlayer();

            } else {
                System.out.println("Invalid Move try again!!");
            }
        }

        sc.close();
    }

    private boolean isDraw() {
        for(int row=0; row<board.getSize(); row++) {
            for(int col=0; col< board.getSize(); col++) {
                if(board.getCell(row,col) != '\0') return false;
            }
        }

        return true;
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }
}
