/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javagameclient;

import com.mycompany.networking.Communicator;
import com.mycompany.networking.authentication.AuthManager;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class HomeScreenController implements Initializable, AuthManager.Listener {

    @FXML
    public StackPane root;
    @FXML
    public VBox innerBox;

    private boolean tryingSignInWithToken;
    private Stage openModal;

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
        AuthManager.getInstance().setListener(this);

        if (Communicator.getInstance().isConnected()) {
            // check if we have a stored token into file
            if (AuthManager.getInstance().getUsername() != null) {
                App.switchToFXML("onlineDashboard");
            } else if (UIHelper.getToken().getToken() == null) {
                tryingSignInWithToken = false;
                App.openModal("Login");
            } else {
                tryingSignInWithToken = true;
                AuthManager.getInstance().signInWithToken();
            }
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

        // to be !tryingSignInWithToken  later but only for the debugging i guess
        // when its tryingSignInWithToken the app will navigate to onlineDashboard succ
        // when its !tryingSignInWithToken the will not navigate to anything
        
        if (signedIn) {
            AuthManager.getInstance().unsetListener();
            
            Platform.runLater(() -> {
                if (openModal != null) {
                    openModal.close();
                    openModal = null;
                }
                
                App.switchToFXML("onlineDashboard");
            });
        } else {
            if (tryingSignInWithToken) {
                UIHelper.deleteTokenFile();
                
                Platform.runLater(() -> {
                    openModal = App.openModal("Login");
                });
            }
        }
    }

    @FXML
    void OnGameRecordClicked(ActionEvent event) throws IOException {
        App.switchToFXML("RecordesScreen");
    }

    @Override
    public void onAuthError(String errorMsg) {
        UIHelper.showAlert("Authentication Error", errorMsg, Alert.AlertType.ERROR);
    }
}
