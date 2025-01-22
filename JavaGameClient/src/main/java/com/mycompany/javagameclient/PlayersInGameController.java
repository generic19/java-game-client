/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.javagameclient;

import com.mycompany.networking.OnlinePlayer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Label;
/**
 * FXML Controller class
 *
 * @author AhmedAli
 */
public class PlayersInGameController implements Initializable {


    @FXML
    private Label inGamePlayerName;
    @FXML
    private Label inGamePlayerScore;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    void setPlayer(OnlinePlayer player){
        inGamePlayerName.setText(player.getUsername());
        inGamePlayerScore.setText(String.valueOf(player.getScore()));
    }
    
}
