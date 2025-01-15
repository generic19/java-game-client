/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javagameclient;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;

public class HomeScreenController {


    @FXML
    void onLoginClicked(MouseEvent event) {

    }

    @FXML
    void onPlayOnlineClicked(ActionEvent event) {

    }

    @FXML
    void onPlayWithFriend(ActionEvent event) throws IOException {
        App.switchToFXML("xoGameNameInput");
    }

    @FXML
    void onSignUpClicked(MouseEvent event) {

    }

    @FXML
    void onplayWithComputer(ActionEvent event) throws IOException {
        App.switchToFXML("LevelScreen");
    }

    void initialize() {

    }
}

