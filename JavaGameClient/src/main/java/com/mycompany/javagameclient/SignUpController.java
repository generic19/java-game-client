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
public class SignUpController implements Initializable {


    @FXML
    private TextField password;
    @FXML
    private TextField confirmpassword;
    @FXML
    private Button SignUp;
    @FXML
    private Label LoginPage;
   
    
        /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void handleSignUp(ActionEvent event) {
    }

    @FXML
    private void goToLoginPage(MouseEvent event) throws IOException {
       Stage stagee=(Stage) LoginPage.getScene().getWindow();
        stagee.close();
        App.openWindow("Login");
    }
    
}
