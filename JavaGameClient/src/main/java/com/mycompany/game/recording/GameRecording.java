/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.game.recording;

import com.mycompany.game.GameMove;
import com.mycompany.networking.OnlinePlayer;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author basel
 */
public interface GameRecording<M extends GameMove> extends Serializable {
    List<M> getMoves();
    
    String getFirstPlayerName();
    String getSecondPlayerName();
    
    int getFirstPlayerScore();
    int getSecondPlayerScore();
}
