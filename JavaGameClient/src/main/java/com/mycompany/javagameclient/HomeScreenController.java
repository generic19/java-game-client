/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javagameclient;

import com.mycompany.networking.Communicator;
import com.mycompany.networking.authentication.AuthManager;
import com.mycompany.networking.authentication.AuthManagerImpl;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.DoubleBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class HomeScreenController implements Initializable, AuthManager.Listener {

    @FXML public StackPane root;
    @FXML public VBox innerBox;
    boolean isListenerAdded = false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        root.setBackground(new Background(
            new BackgroundImage(
                new Image(App.class.getResource("images/GameBackGround.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1, 1, true, true, false, true)
            )
        ));
        
        DoubleBinding scaleFactor = root.heightProperty().divide(1000).add(0.65);
        
        innerBox.scaleXProperty().bind(scaleFactor);
        innerBox.scaleYProperty().bind(scaleFactor);
    }
    
    @FXML
    void onPlayOnlineClicked(ActionEvent event) throws IOException {
        Communicator.getInstance().openConnection();
        
        if (Communicator.getInstance().isConnected()) {
            // check if we have a stored token into file
            if (UIHelper.getToken() == null) {
                App.openModal("Login");
            } else {
                if (!isListenerAdded) {
                    AuthManager.getInstance().addListener(this);
                    isListenerAdded = true;
                }
                AuthManager.getInstance().signInWithToken();
            }
        } else {
            UIHelper.showAlert("Error", "Error connecting to server.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onPlayWithFriend(ActionEvent event) throws IOException {
        App.switchToFXML("xoGameNameInput");
    }

    @FXML
    void onplayWithComputer(ActionEvent event) throws IOException {
        App.switchToFXML("LevelScreen");
    }

    @Override
    public void onAuthStateChange(boolean signedIn) {
        if(signedIn){
            App.getFXMLLoader("onlineDashboard");
        } else{
            onError("Inavalid Token");
        }
    }

    @Override
    public void onError(String errorMsg) {
        try {
            App.switchToFXML("Login");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

