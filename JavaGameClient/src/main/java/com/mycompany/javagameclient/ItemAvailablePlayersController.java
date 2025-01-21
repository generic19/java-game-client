/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.javagameclient;

import com.mycompany.networking.OnlinePlayer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Label;
/**
 * FXML Controller class
 *
 * @author AhmedAli
 */
public class ItemAvailablePlayersController implements Initializable {


    @FXML
    private Label playerName;
    @FXML
    private Label playerScore;
    /**
     * Initializes the controller class.
     */
    public void setPlayer(OnlinePlayer onlinePlayer){
        playerName.setText(onlinePlayer.getUsername());
        playerScore.setText(String.valueOf(onlinePlayer.getScore()));
       
    }
    @Override
    
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void onInviteClicked(ActionEvent event) {
    }

}
