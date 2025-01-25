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
import javafx.application.Platform;
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
public class SignUpController implements Initializable {

    @FXML private TextField username;
    @FXML private TextField password;
    @FXML private TextField confirmPassword;
    @FXML private Button signUp;
    @FXML private Label loginPage;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validateInputFields();
    }    
    
    @FXML
    private void handleSignUp(ActionEvent event) {
        if(!password.getText().equals(confirmPassword.getText())){
            UIHelper.showAlert(
                "Validation Error",
                "password and confirm password is not the same",
                Alert.AlertType.WARNING
            );
        } else {
            AuthManager.getInstance().register(
                username.getText().trim(),
                password.getText().trim()
            );
        }
         Stage stage = (Stage) username.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void goToLoginPage(MouseEvent event) throws IOException {
       Stage stage = (Stage) loginPage.getScene().getWindow();
        App.switchModal("Login", stage);
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
