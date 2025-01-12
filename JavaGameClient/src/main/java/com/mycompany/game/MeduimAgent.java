/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.game;

import java.util.Iterator;

public class MeduimAgent implements GameAgent<GameMove, GameState<GameMove>> {

    @Override
    public GameMove getNextMove(GameState<GameMove> state) {
        Player currentPlayer = state.getNextTurnPlayer();
        Player opponent = (currentPlayer == Player.one) ? Player.two : Player.one;
        Iterator<GameMove> availableMoves = state.getValidMoves();

        // Check for a winning move
        while (availableMoves.hasNext()) {
            GameMove move = availableMoves.next();
            GameState<GameMove> simulatedState = simulateMove(state, move);
            if (simulatedState.getWinner() == currentPlayer) {
                return move;
            }
        }

        // Check for a blocking move
        availableMoves = state.getValidMoves(); // Reset iterator
        while (availableMoves.hasNext()) {
            GameMove move = availableMoves.next();
            GameState<GameMove> simulatedState = simulateMove(state, move);
            if (simulatedState.getWinner() == opponent) {
                return move;
            }
        }

        // Fallback to the first move
        availableMoves = state.getValidMoves(); // Reset iterator
        return availableMoves.next(); // Get the first available move
    }

    private GameState<GameMove> simulateMove(GameState<GameMove> state, GameMove move) {
        GameState<GameMove> simulatedState = state.play(move); // Use the play method to simulate the move
        return simulatedState;
    }
}
