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
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class RecordsScreenController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;
    @FXML // fx:id="recordesList"
    private VBox recordesList; // Value injected by FXMLLoader
    RecordingManager<GameMove> recordManager = new RecordingManagerImpl();
    List<File> recordesFilles = recordManager.getRecordings();

    @FXML
    void onBackClicked(ActionEvent event) throws IOException {
        App.switchToFXML("HomeScreen");
    }

    @FXML 
    void initialize() {

        List<File> recordsFiles = recordManager.getRecordings();

        for (int i = 0; i < recordsFiles.size(); i++) {
            File file = recordsFiles.get(i);
            createNode(file, i);

        }
                            recordesList.prefHeightProperty().bind(recordesList.heightProperty());

    }

    void createNode(File recordFile, int index) {
        try {
            FXMLLoader loader = App.getFXMLLoader("RecordItem");
            Node element = loader.load();
            RecordItemController controller = loader.getController();
            controller.setLabel(recordFile.getName());
            // controller.setLabel(recordFile);
            controller.setIndex(index);
            controller.setRecordFile(recordFile);
            recordesList.getChildren().add(element);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}