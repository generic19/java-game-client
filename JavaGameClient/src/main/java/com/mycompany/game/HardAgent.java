/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.game;

import java.util.Iterator;

public class HardAgent implements GameAgent<GameMove, GameState<GameMove>> {

    @Override
    public GameMove getNextMove(GameState<GameMove> state) {
        Player currentPlayer = state.getNextTurnPlayer();
        return minimax(state, currentPlayer, true).move;
    }

    private ScoreMove minimax(GameState<GameMove> state, Player player, boolean isMaximizing) {

        if (state.isEndState()) {
            if (state.getWinner() == player) {
                return new ScoreMove(null, 10);
            } else if (state.getWinner() == null) {
                return new ScoreMove(null, 0);
            } else {
                return new ScoreMove(null, -10);
            }
        }

        Iterator<GameMove> availableMoves = state.getValidMoves();
        ScoreMove bestMove = isMaximizing ? new ScoreMove(null, Integer.MIN_VALUE) : new ScoreMove(null,
            Integer.MAX_VALUE);

        for (Iterator<GameMove> iterator = availableMoves; iterator.hasNext();) {
            GameMove move = iterator.next();
            GameState<GameMove> simulatedState = simulateMove(state, move);
            ScoreMove currentMove = minimax(simulatedState, player, !isMaximizing);

            if (isMaximizing) {
                if (currentMove.score > bestMove.score) {
                    bestMove = new ScoreMove(move, currentMove.score);
                }
            } else {
                if (currentMove.score < bestMove.score) {
                    bestMove = new ScoreMove(move, currentMove.score);
                }
            }
        }

        return bestMove;
    }

    private GameState<GameMove> simulateMove(GameState<GameMove> state, GameMove move) {
        GameState<GameMove> simulatedState = state.play(move);
        return simulatedState;
    }

    public static class ScoreMove {

        GameMove move;
        int score;

        public ScoreMove(GameMove move, int score) {
            this.move = move;
            this.score = score;
        }
    }
}
