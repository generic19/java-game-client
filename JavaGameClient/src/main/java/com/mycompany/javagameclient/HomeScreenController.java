/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javagameclient;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class HomeScreenController implements Initializable {

    @FXML public StackPane root;
    @FXML public VBox innerBox;
    
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
        App.openWindow("Login");
    }

    @FXML
    void onPlayWithFriend(ActionEvent event) throws IOException {
        App.switchToFXML("xoGameNameInput");
    }

    @FXML
    void onplayWithComputer(ActionEvent event) throws IOException {
        App.switchToFXML("LevelScreen");
    }
}

