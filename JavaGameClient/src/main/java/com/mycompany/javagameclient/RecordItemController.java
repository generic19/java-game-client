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
import com.mycompany.game.recording.RecordingManagerImpl;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class RecordItemController {

    @FXML
    private Label recordGame;
    private File recordFile;
    private int index;
    RecordingManager<GameMove> recordManager;
    
    public RecordItemController(){
        recordManager = new RecordingManagerImpl();
    }

    @FXML
    void onDeleteClicked(ActionEvent event) {
        
        UIHelper.showQuestion(
            "Warning",
            "Are you sure you want to delete this file?",
            Map.of(
                "Yes",
                () -> {
                    recordManager.deldeteFile(recordFile);
                    ((VBox) recordGame.getParent().getParent()).getChildren().remove(recordGame.getParent());
                },
                "No",
                () -> {
                    
                }
            )
        );
        
    }

    @FXML
    void onPlayClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = App.getFXMLLoader("xoGame");
        Parent root = loader.load();
        
        XoGameController controller = loader.getController();
        controller.initializeReplayGame(recordManager.loadRecording(recordFile));
        
        App.setRoot(root);
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
