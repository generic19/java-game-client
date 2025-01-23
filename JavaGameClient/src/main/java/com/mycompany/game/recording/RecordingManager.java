/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.game.recording;

import com.mycompany.game.GameMove;
import java.io.File;
import java.util.List;

/**
 *
 * @author basel
 */
public interface RecordingManager<M extends GameMove> {
    List<File> getRecordings();
    void saveRecording(GameRecording recording);
    GameRecording<M> loadRecording(File file);
}
