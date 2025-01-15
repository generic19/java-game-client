/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.javagameclient;

import com.mycompany.game.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;

/**
 * FXML Controller class
 *
 * @author basel
 */
public class XoGameController implements Initializable, XOGame.Listener {
    @FXML private Label lblHeader;
    @FXML private Label lblLeftPlayer;
    @FXML private Label lblLeftPlayerScore;
    @FXML private Label lblRightPlayer;
    @FXML private Label lblRightPlayerScore;
    @FXML private Line winningLine;
    @FXML private GridPane cellGrid;
    
    private GameMode gameMode;
    private XOGame game;
    private String firstPlayerName;
    private String secondPlayerName;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
    }
    
    public void initializeGameForLocalwithFriend(String player1, String player2) {
        gameMode = GameMode.localWithFriend;
        
        firstPlayerName = player1;
        secondPlayerName = player2;
        
        game = new XOGame();
        
        initializeGame();
        
    }
    
    
    public void initializeGameForLocalWithComputer(GameAgent agent) {
        gameMode = GameMode.localWithComputer;
        
        firstPlayerName = "Player";
        secondPlayerName = "Computer";
        
        game = new XOGame();
        game.attachAgent(Player.two, agent);
        
        initializeGame();
    }

    private void initializeGame() {
        lblHeader.setText(firstPlayerName + "'s turn...");
        
        lblLeftPlayer.setText(firstPlayerName);
        lblRightPlayer.setText(secondPlayerName);
        
        lblLeftPlayerScore.setText("0");
        lblRightPlayerScore.setText("0");
        
        game.addListener(this);
    }
    
    @FXML
    private void onLeave(ActionEvent event) throws IOException {
        game.removeListener(this);
        App.switchToFXML("HomeScreen");
    }

    @FXML
    private void onCellClicked(MouseEvent event) {
        boolean found = false;
        int index;
        
        for (index = 0; index < cellGrid.getChildren().size(); index++) {
            Node node = cellGrid.getChildren().get(index);
            
            if (node == event.getSource()) {
                found = true;
                break;
            }
        }
        
        if (found) { 
            XOGameState state = game.getState();
            XOGameMove move = new XOGameMove(index, state.getNextTurnPlayer());
            
            if (state.isValidMove(move)) {
                cellGrid.setDisable(true);
                game.play(move);
            }
        }
    }
    
    @Override
    public void onStateChange(GameState state) {
        XOGameState gameState = (XOGameState) state;
       XOGameState newGameState;
        char[] board = gameState.getBoard();
        
        Image xImage = new Image(App.class.getResource("images/Neon X.png").toExternalForm());
        Image oImage = new Image(App.class.getResource("images/Neon O.png").toExternalForm());
        
        for (int index = 0; index < 9; index++) {
            ImageView cell = (ImageView) cellGrid.getChildren().get(index);

            switch (board[index]) {
                case 'X':
                    cell.setImage(xImage);
                    break;
                   
                case 'O':
                    cell.setImage(oImage);
                    break;
                    
                default:
                    cell.setImage(null);
                    break;
            }
        }
        String alertMsg = null;
        if (gameState.isEndState()) {
            cellGrid.setDisable(true); 
            
            Player winner = gameState.getWinner();
            
            if (winner != null) {
                
                String winnerName = (winner == Player.one) ? firstPlayerName : secondPlayerName;
                lblHeader.setText(winnerName + " wins!");
                 alertMsg=winnerName+"is the WINNER. What would you like to do?";
                
                if (winner == Player.one) {
                    int score = Integer.parseInt(lblLeftPlayerScore.getText());
                    lblLeftPlayerScore.setText(String.valueOf(score + 1));
                  
                } else {
                    int score = Integer.parseInt(lblRightPlayerScore.getText());
                    lblRightPlayerScore.setText(String.valueOf(score + 1));
                }
                
                
            } else {
                alertMsg="It's a draw!. What would you like to do?";
                lblHeader.setText("It's a draw!");
            }
             ButtonType goHomeButton = new ButtonType("Go Home");
               ButtonType keepPlayingButton = new ButtonType("Keep Playing");

                Alert alert = new Alert(Alert.AlertType.INFORMATION, alertMsg + " ", goHomeButton, keepPlayingButton);
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get()==goHomeButton){
                    try {
                        App.switchToFXML("HomeScreen");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }else if(result.get()==keepPlayingButton){
                    game.resetGame();
                   
                }
        } else {
           
            String nextPlayerName = gameState.getNextTurnPlayer() == Player.one
                ? firstPlayerName
                : secondPlayerName;
            lblHeader.setText(nextPlayerName + "'s turn...");

            cellGrid.setDisable(false);
        }
    }    
    
    private enum GameMode {
        localWithComputer, localWithFriend;
    }
}
