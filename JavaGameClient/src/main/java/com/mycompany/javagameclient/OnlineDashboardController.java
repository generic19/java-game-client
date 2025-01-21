/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.javagameclient;

import com.mycompany.networking.authentication.AuthManager;
import com.mycompany.networking.matching.MatchingManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
/**
 * FXML Controller class
 *
 * @author AhmedAli
 */
public class OnlineDashboardController implements Initializable, AuthManager.Listener  {
    
    @FXML
    private Label labelPlayerName;
    @FXML
    private TextField searchField;
    @FXML
    private Label labelScore;
    @FXML
    private VBox availablePlayersList;
    @FXML
    private VBox inGamePlayersList;
    
    AuthManager authManager;
    boolean isListenerAdded = false;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void onBackClicked(ActionEvent event) {
        
    }

    @FXML
    private void onLogoutClicked(ActionEvent event) {
        if(!isListenerAdded){
            authManager.addListener(this);
            isListenerAdded = true;
        }
        authManager.signOut(labelPlayerName.getText());
    }

    @Override
    public void onAuthStateChange(boolean signedIn) {
        if(signedIn){
            App.getFXMLLoader("HomeScreen");
        } 
    }

    @Override
    public void onError(String errorMsg) {
        UIHelper.showAlert("Error", errorMsg, Alert.AlertType.ERROR);
    }
}
