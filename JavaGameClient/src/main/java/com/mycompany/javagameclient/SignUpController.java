/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.javagameclient;

import java.io.IOException;
import com.mycompany.networking.authentication.AuthManager;
import com.mycompany.networking.authentication.AuthManagerImpl;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
public class SignUpController implements Initializable, AuthManager.Listener {

    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField confirmPassword;
    @FXML
    private Button signUp;
    @FXML
    private Label loginPage;
    
    AuthManager authManager;
    boolean isListenerAdded = false;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        authManager = new AuthManagerImpl();
        validateInputFields();
    }    
    
    @FXML
    private void handleSignUp(ActionEvent event) {
        if(!password.getText().equals(confirmPassword.getText())){
            UIHelper.showAlert("Warning", "password and confirm password is not tha same", Alert.AlertType.WARNING);
        } else {
            if(!isListenerAdded){
                authManager.addListener(this);
                isListenerAdded = true;
            }
            authManager.register(username.getText().trim(), password.getText().trim());
        }
    }

    @FXML
    private void goToLoginPage(MouseEvent event) throws IOException {
       Stage stagee=(Stage) loginPage.getScene().getWindow();
        stagee.close();
        App.openModal("Login");
    }

    @Override
    public void onAuthStateChange(boolean signedIn) {
        if(signedIn){
            authManager.removeListener(this);
            App.getFXMLLoader("onlineDashboard");
        }
    }

    @Override
    public void onError(String errorMsg) { 
        UIHelper.showAlert("Error", errorMsg, Alert.AlertType.ERROR);
    }

    private void validateInputFields() {
        username.textProperty().addListener((observable, oldValue, newValue)->{
            if(observable.getValue().trim().isEmpty() 
                    || password.getText().trim().isEmpty()
                    || confirmPassword.getText().trim().isEmpty()){
                signUp.setDisable(true);
            }else{
                signUp.setDisable(false);
            }
        });
        password.textProperty().addListener((observable, oldValue, newValue)->{
            if(observable.getValue().trim().isEmpty() 
                    || username.getText().trim().isEmpty()
                    || confirmPassword.getText().trim().isEmpty()){
                signUp.setDisable(true);
            }else{
                signUp.setDisable(false);
            }
        });
        confirmPassword.textProperty().addListener((observable, oldValue, newValue)->{
            if(observable.getValue().trim().isEmpty() 
                    || password.getText().trim().isEmpty()
                    || username.getText().trim().isEmpty()){
                signUp.setDisable(true);
            }else{
                signUp.setDisable(false);
            }
        });
    }
}
