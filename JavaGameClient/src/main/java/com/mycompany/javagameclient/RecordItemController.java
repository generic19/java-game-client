/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javagameclient;

/**
 *
 * @author ArwaKhaled
 */
import com.mycompany.game.GameMove;
import com.mycompany.game.recording.RecordingManager;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class RecordItemController {

    @FXML
    private Label recordGame;
    private File recordFile;
    private int index;
    RecordingManager<GameMove> recordManager;

    @FXML
    void onDeleteClicked(ActionEvent event) {

        ((VBox) recordGame.getParent().getParent()).getChildren().remove(recordGame.getParent());
    }

    @FXML
    void onPlayClicked(ActionEvent event) {
        //that will play the record ?
        recordManager.loadRecording(recordFile);
    }

    void setLabel(String recordName) {
        this.recordGame.setText(recordName);
    }

    void setIndex(int index) {
        this.index = index;
    }

    void setRecordFile(File file) {
        this.recordFile = file;
    }

}
