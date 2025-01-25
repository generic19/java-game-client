package com.mycompany.javagameclient;

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
    private final RecordingManager<?> recordManager = new RecordingManagerImpl();

    @FXML
    void onDeleteClicked(ActionEvent event) {
        UIHelper.showQuestion(
            "Warning",
            "Are you sure you want to delete this file?",
            Map.of(
                "Yes",
                () -> {
                    boolean deleted = recordManager.deldeteFile(recordFile);
                    if (deleted) {
                        ((VBox) recordGame.getParent().getParent()).getChildren().remove(recordGame.getParent());
                       // UIHelper.showMessage("Success", "File deleted successfully!");
                    } else {
                      //  UIHelper.showMessage("Error", "Failed to delete the file.");
                    }
                },
                "No",
                () -> {} // Do nothing
            )
        );
    }

    @FXML
    void onPlayClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = App.getFXMLLoader("xoGame");
        Parent root = loader.load();

        XoGameController controller = loader.getController();
       // controller.initializeReplayGame(recordManager.loadRecording(recordFile));

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