/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.game;

import java.io.Serializable;
import java.util.Iterator;
import java.util.StringJoiner;
import java.util.Vector;

/**
 *
 * @author ArwaKhaled
 */
public class XOGameState implements GameState<XOGameMove>, Serializable {

    private char[] board;

    private Player currentPlayer;
    private XOGameMove lastMove;
    private Vector<Integer> validBoard;

    //int index ;
    public XOGameState() {
        validBoard = new Vector<>();
        for (int i = 0; i < 9; i++) {
            validBoard.add(i);
        }
        board = new char[9];
        currentPlayer = Player.one;
        lastMove = null;
    }

    public XOGameState(XOGameState oldGame, XOGameMove lastMove) {

        validBoard = (Vector<Integer>) oldGame.validBoard.clone();
        board = oldGame.board.clone();
        currentPlayer = oldGame.currentPlayer == Player.one ? Player.two : Player.one;
        this.lastMove = lastMove;
        validBoard.removeElement(lastMove.getIndex());
        board[lastMove.getIndex()] = oldGame.currentPlayer == Player.one ? 'X' : 'O';
    }

    public char[] getBoard() {
        return board;
    }

    public Player getCell(int row, int col) {
        char play = board[row * 3 + col];

        switch (play) {
            case 'X':
                return Player.one;
            case 'O':
                return Player.two;
            default:
                return null;
        }
    }

    @Override
    public Player getNextTurnPlayer() {
        return currentPlayer;
    }

    @Override
    public boolean isValidMove(XOGameMove move) {
        return move.getPlayer() == currentPlayer && board[move.getIndex()] == '\0';
    }

    @Override
    public Iterator getValidMoves() {
        Iterator<Integer> it = validBoard.iterator();
        return new Iterator<XOGameMove>() {
            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override

            public XOGameMove next() {

                int index = it.next();
                return new XOGameMove(index, currentPlayer);

            }
        };

    }

    @Override
    public XOGameState play(XOGameMove move) throws IllegalStateException {
        if (!isValidMove(move)) {
            throw new IllegalStateException("invalid move ");
        }
        return new XOGameState(this, move);

    }
//board[0] and board[4] and board[8]== X 
// board[2]and board [4] and board[6] == X bardo 
// 0  1  2
// 3  4  5
// 6  7  8 

//board[i] and board[i+3] aand board [i+6] == 
// board [i] and board[i+1] and board [i+2] ==
    @Override
    public boolean isEndState() {
        if (validBoard.isEmpty()) {
            return true;
        }
        if (isXWinner()) {
            return true;
        } else if (isOWinner()) {
            return true;
        }
        return false;

    }

    boolean isXWinner() {
        if (board[0] == 'X' && board[4] == 'X' && board[8] == 'X') {
            return true;
        } else if (board[2] == 'X' && board[4] == 'X' && board[6] == 'X') {
            return true;
        }
        for (int i = 0; i <= 2; i++) {
            if (board[i] == 'X' && board[i + 3] == 'X' && board[i + 6] == 'X') {
                return true;
            }
        }

        for (int i = 0; i < 9; i += 3) {
            if (board[i] == 'X' && board[i + 1] == 'X' && board[i + 2] == 'X') {
                return true;
            }
        }
        return false;

    }

    boolean isOWinner() {
        if (board[0] == 'O' && board[4] == 'O' && board[8] == 'O') {
            return true;
        } else if (board[2] == 'O' && board[4] == 'O' && board[6] == 'O') {
            return true;
        }
        for (int i = 0; i <= 2; i++) {
            if (board[i] == 'O' && board[i + 3] == 'O' && board[i + 6] == 'O') {
                return true;
            }
        }

        for (int i = 0; i < 9; i += 3) {
            if (board[i] == 'O' && board[i + 1] == 'O' && board[i + 2] == 'O') {
                return true;
            }
        }
        return false;

    }

    @Override
    public Player getWinner() throws IllegalStateException {
        if (isEndState()) {
            if (isXWinner()) {
                return Player.one;
            } else if (isOWinner()) {
                return Player.two;
            }
            return null;
        } else {
            throw new IllegalStateException("its not an end state");
        }

    }

    @Override
    public XOGameMove getLastMove() {
        return lastMove;
    }

    public void resetBoard() {
        validBoard = new Vector<>();
        for (int i = 0; i < 9; i++) {
            validBoard.add(i);
        }
        board = new char[9];
        currentPlayer = Player.one;
        lastMove = null;
    }

    public int[] getWinningLineLineIndices() {
        // 0 1 2
        // 3 4 5
        // 6 7 8
        if (!isEndState() || getWinner() == null) {
            return null;
        }

        XOGameMove lastMove = getLastMove();
        Player lastMovePlayer = lastMove.getPlayer();

        int row = lastMove.getRow();
        boolean isRow = true;

        for (int i = 0; i < 3; i++) {
            if (getCell(row, i) != lastMovePlayer) {
                isRow = false;
                break;
            }
        }

        if (isRow) {
            int startIndex = row * 3;
            int endIndex = row * 3 + 2;

            return new int[]{startIndex, endIndex};
        }

        int col = lastMove.getCol();
        boolean isCol = true;

        for (int i = 0; i < 3; i++) {
            if (getCell(i, col) != lastMovePlayer) {
                isCol = false;
                break;
            }
        }

        if (isCol) {
            int startIndex = col;
            int endIndex = 2 * 3 + col;

            return new int[]{startIndex, endIndex};
        }

        // (0) 1  2
        //  3  4  5
        //  6  7 (8)
        /*
        x o x
        o x o
        o x x
         */
        if (lastMovePlayer == getCell(0, 0) && lastMovePlayer == getCell(2, 2)) {
            return new int[]{0, 8};
        } else {
            return new int[]{2, 6};
        }
    }

    @Override
    public String toString() {
        String boardString = "";

        if (board != null) {
            StringJoiner rowJoiner = new StringJoiner(" ");
            StringJoiner colJoiner = new StringJoiner("; ");

            for (int i = 0; i < 8; i++) {
                char c = board[i] == 0 ? ' ' : board[i];
                rowJoiner.add("" + c);

                if (i % 3 == 2) {
                    colJoiner.add(rowJoiner.toString());
                    rowJoiner = new StringJoiner(" ");
                }
            }
        }
        
        return "XOGameState{" + "board=(" + boardString + "), currentPlayer=" + currentPlayer + '}';
    }
}
