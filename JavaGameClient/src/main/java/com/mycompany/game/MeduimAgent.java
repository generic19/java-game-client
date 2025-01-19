package com.mycompany.game;

import java.util.*;

public class MeduimAgent implements GameAgent<GameMove, GameState<GameMove>> {

    @Override
    public GameMove getNextMove(GameState<GameMove> state) {
        List<GameMove> availableMoves = new ArrayList<>();
        Iterator<GameMove> it = state.getValidMoves();

        while (it.hasNext()) {
            availableMoves.add(it.next());
        }

        if (availableMoves.isEmpty()) {
            return null;
        }

        GameMove bestMove = null;
        int bestScore = Integer.MIN_VALUE;

        for (GameMove move : availableMoves) {
            GameState<GameMove> simulatedState = state.play(move);

           
            int score = evaluateState(simulatedState) + evaluateFutureOptions(simulatedState);

            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }

        return bestMove;
    }

 
    private int evaluateState(GameState<GameMove> state) {
        if (state.isEndState()) {
            if (state.getWinner() == null) {
                return 0; // Draw
            } else {
                return state.getWinner().equals(state.getLastMove().getPlayer()) ? 1 : -1; // Win or Loss
            }
        }
        return 0; 
    }

    
    private int evaluateFutureOptions(GameState<GameMove> state) {
        int futureMoves = 0;
        Iterator<GameMove> it = state.getValidMoves();
        while (it.hasNext()) {
            futureMoves++;
            it.next();
        }
        return futureMoves; 
    }

    public void attachToGame(Game<GameMove, GameState<GameMove>> game, Player player) {
        game.attachAgent(player, this);
    }

    public void detachFromGame(Game<GameMove, GameState<GameMove>> game, Player player) {
        game.detachAgent(player);
    }
}

