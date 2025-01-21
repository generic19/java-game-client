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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ayasa
 */
public class LoginController implements Initializable {


    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Button button;
    @FXML
    private Label SignUpPage;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        
    }

    @FXML
    private void goToSignup(MouseEvent event) throws IOException  {
        Stage stage=(Stage) SignUpPage.getScene().getWindow();
        stage.close();
        App.openWindow("SignUp");
    }
}
