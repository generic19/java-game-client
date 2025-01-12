/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.javagameclient;

import java.net.URL;
import java.util.ResourceBundle;

import com.mycompany.game.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
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
    @FXML private Line winningLine;
    @FXML private GridPane cellGrid;
    
    private Game game;
    private String firstPlayerName;
    private String secondPlayerName;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
    }
    
    public void initializeGame(GameMode mode) {

    }

    @FXML
    private void onLeave(ActionEvent event) {
    }

    @FXML
    private void onCellClicked(MouseEvent event) {
    
    }
    
    public enum GameMode {
        localWithComputer, localWithFriend;
    }
}
