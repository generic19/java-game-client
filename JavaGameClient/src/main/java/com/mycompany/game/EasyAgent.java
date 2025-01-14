package com.mycompany.game;

import java.util.*;

public class EasyAgent implements GameAgent<GameMove, GameState<GameMove>> {

    @Override
    public GameMove getNextMove(GameState<GameMove> state) {
        List<GameMove> availableMoves = new ArrayList<>();
        Iterator<GameMove> it = state.getValidMoves();
        
        while (it.hasNext()) {
            availableMoves.add(it.next());
        }
        
        return availableMoves.get(new Random().nextInt(availableMoves.size()));
    }
}
