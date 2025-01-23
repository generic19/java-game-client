/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.game.recording;

import com.mycompany.game.GameMove;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author AhmedAli
 */
public class GameRecordingImpl implements GameRecording<GameMove> {
    
    private List<GameMove> gameMoves;
    private String firstPlayerName;
    private String secondPlayerName;
    private int firstPlayerScore;
    private int secondPlayerScore;

    public GameRecordingImpl(List<GameMove> gameMoves, String firstPlayerName, String secondPlayerName, int firstPlayerScore, int secondPlayerScore) {
        this.gameMoves = gameMoves;
        this.firstPlayerName = firstPlayerName;
        this.secondPlayerName = secondPlayerName;
        this.firstPlayerScore = firstPlayerScore;
        this.secondPlayerScore = secondPlayerScore;
    }

    @Override
    public List<GameMove> getMoves() {
        return gameMoves;
    }

    @Override
    public String getFirstPlayerName() {
        return firstPlayerName;
    }

    @Override
    public String getSecondPlayerName() {
        return secondPlayerName;
    }

    @Override
    public int getFirstPlayerScore() {
        return firstPlayerScore;
    }

    @Override
    public int getSecondPlayerScore() {
        return secondPlayerScore;
    }
    
}
