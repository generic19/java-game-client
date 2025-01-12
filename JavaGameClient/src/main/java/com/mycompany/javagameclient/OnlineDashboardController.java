/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.javagameclient;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
/**
 * FXML Controller class
 *
 * @author AhmedAli
 */
public class OnlineDashboardController implements Initializable {


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
    }

}
