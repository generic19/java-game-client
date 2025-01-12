package com.mycompany.game;

import java.util.List;
import java.util.Random;

public class EasyAgent implements GameAgent<GameMove, GameState<GameMove>> {

    @Override
    public GameMove getNextMove(GameState<GameMove> state) {
        List<GameState> availableMoves = state.getVaildMoves();
        return availableMoves.get(new Random().nextInt(availableMoves.size()));
    }
}/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
