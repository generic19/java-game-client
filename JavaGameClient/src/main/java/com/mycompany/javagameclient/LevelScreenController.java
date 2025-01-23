/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javagameclient;
import com.mycompany.game.*;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class LevelScreenController {

    @FXML
    void onEasyClicked(ActionEvent event) throws IOException {
        startGameWithAgent(new EasyAgent());
    }

    @FXML
    void onHardClicked(ActionEvent event) throws IOException {
        startGameWithAgent(new HardAgent());
    }

    @FXML
    void onMediumClicked(ActionEvent event) throws IOException {
        startGameWithAgent(new MeduimAgent());
    }
    
    private void startGameWithAgent(GameAgent agent) throws IOException {
        FXMLLoader loader = App.getFXMLLoader("xoGame");
        Parent root = loader.load();
        
        XoGameController controller = loader.getController();
        controller.initializeGameForLocalWithComputer(agent);
        
        App.setRoot(root);
    }

    @FXML
    private void onBackClicked(ActionEvent event) {
        App.switchToFXML("HomeScreen");
    }
}
