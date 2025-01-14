/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.javagameclient;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author basel
 */
public class XoGameNameInputController implements Initializable {

    @FXML private TextField txtPlayer1Name;
    @FXML private TextField txtPlayer2Name;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void onBack(ActionEvent event) throws IOException {
        App.switchToFXML("HomeScreen");
    }

    @FXML
    private void onStartGame(ActionEvent event) throws IOException {
        String player1 = txtPlayer1Name.getText();
        String player2 = txtPlayer2Name.getText();
        
        if (player1.isBlank()) {
            player1 = "Player 1";
        }
        if (player2.isBlank()) {
            player2 = "Player 2";
        }
        
        FXMLLoader loader = App.getFXMLLoader("xoGame");
        Parent root = loader.load();
        
        XoGameController controller = loader.getController();
        controller.initializeGameForLocalwithFriend(player1, player2);
        
        App.setRoot(root);
    }
    
}
