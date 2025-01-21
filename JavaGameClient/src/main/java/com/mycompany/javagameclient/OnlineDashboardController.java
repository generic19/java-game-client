/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.javagameclient;

import com.mycompany.networking.authentication.AuthManager;
import com.mycompany.networking.Communicator;
import com.mycompany.networking.CommunicatorImpl;
import com.mycompany.networking.OnlinePlayer;
import com.mycompany.networking.matching.MatchingManager;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.Node;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
/**
 * FXML Controller class
 *
 * @author AhmedAli
 */    
public class OnlineDashboardController implements Initializable,MatchingManager.Listener, AuthManager.Listener  {
    MatchingManager matchingManager;
    Communicator communicator=Communicator.getInstance();
    List<OnlinePlayer>onlinePlayers,availables;
     

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
       
        matchingManager=new MatchingManager(communicator);
        onlinePlayers=new ArrayList<> (matchingManager.getAvailable());
        availables= new ArrayList<>(matchingManager.getInGame());
        matchingManager.addListener(this);
       for (OnlinePlayer player : onlinePlayers) { 
           FXMLLoader loader =App.getFXMLLoader("itemAvailablePlayers");
            try {
                Node element =loader.load();
                ItemAvailablePlayersController controller=loader.getController() ;
                controller.setPlayer(player);
                
                availablePlayersList.getChildren().add(element);
                
                
            } catch (IOException ex) {
                ex.printStackTrace();
            }
           
        }
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

    public void onMatchingUpdate() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void onIncomingInvite(String userName) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void onIncomingResponse(boolean accept, boolean timeOut) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void onErrorMessage(String errorMsg) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void onError(String errorMsg) {
        UIHelper.showAlert("Error", errorMsg, Alert.AlertType.ERROR);
    }
}
