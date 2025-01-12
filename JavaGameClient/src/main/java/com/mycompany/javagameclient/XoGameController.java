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
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;

/**
 * FXML Controller class
 *
 * @author basel
 */
public class XoGameController implements Initializable {

    @FXML private Label lblHeader;
    @FXML private Label lblLeftPlayer;
    @FXML private Label lblLeftPlayerScore;
    @FXML private Label lblRightPlayer;
    @FXML private Label lblRightPlayerScore;
    @FXML private ImageView imageRow0Col0;
    @FXML private ImageView imageRow0Col1;
    @FXML private ImageView imageRow0Col2;
    @FXML private ImageView imageRow1Col0;
    @FXML private ImageView imageRow1Col1;
    @FXML private ImageView imageRow1Col2;
    @FXML private ImageView imageRow2Col0;
    @FXML private ImageView imageRow2Col1;
    @FXML private ImageView imageRow2Col2;
    @FXML private Line winningLine;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onLeave(ActionEvent event) {
    }
    
}
