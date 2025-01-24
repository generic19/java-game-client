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
import java.io.FilenameFilter;
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
    
    public RecordItemController(){
        recordManager = new RecordingManagerImpl();
    }

    @FXML
    void onDeleteClicked(ActionEvent event) {
        /*
        // getting current directory of the project
        File directory = new File(System.getProperty("user.dir"));
        // filtering txt files
        FilenameFilter filenameFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().contains(recordGame.getText());
            }
        };
        
        File[] fileArray = directory.listFiles(filenameFilter);
        
        recordManager.deldeteFile(fileArray[0]);
        */
        
        recordManager.deldeteFile(recordFile);
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
