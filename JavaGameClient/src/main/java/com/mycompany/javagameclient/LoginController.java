/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.javagameclient;

import com.mycompany.networking.authentication.AuthManager;
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
    private Label signUpPage;
    @FXML
    private Button btnLogin;
    
    boolean isListenerAdded = false;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validateInputFields();
    }    
    
    @FXML   
    private void handleLogin(ActionEvent event) {
        if(!isListenerAdded){
            isListenerAdded = true;
        }
        AuthManager.getInstance().signIn(username.getText().trim(), password.getText().trim());
        
        
    }

    @FXML
    private void goToSignup(MouseEvent event) throws IOException {
        Stage stage=(Stage) signUpPage.getScene().getWindow();
        App.switchModal("SignUp", stage);
    }
    
    private void validateInputFields() {
        username.textProperty().addListener((observable, oldValue, newValue)->{
            if(observable.getValue().trim().isEmpty() 
                    || password.getText().trim().isEmpty()){
                btnLogin.setDisable(true);
            }else{
                btnLogin.setDisable(false);
            }
        });
        password.textProperty().addListener((observable, oldValue, newValue)->{
            if(observable.getValue().trim().isEmpty() 
                    || username.getText().trim().isEmpty()){
                btnLogin.setDisable(true);
            }else{
                btnLogin.setDisable(false);
            }
        });
        
    }
}
