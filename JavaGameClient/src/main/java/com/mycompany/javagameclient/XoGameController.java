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
import javafx.beans.binding.DoubleBinding;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * FXML Controller class
 *
 * @author basel
 */
public class XoGameController implements Initializable, XOGame.Listener {
    @FXML private VBox root;
    @FXML private GridPane horizontalGrid;
    @FXML private Label lblHeader;
    @FXML private Label lblLeftPlayer;
    @FXML private Label lblLeftPlayerScore;
    @FXML private Label lblRightPlayer;
    @FXML private Label lblRightPlayerScore;
    @FXML private Line winningLine;
    @FXML private GridPane cellGrid;
    @FXML private Button btnLeave;
    
    private GameMode gameMode;
    private XOGame game;
    private String firstPlayerName;
    private String secondPlayerName;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DoubleBinding scaleFactor = root.widthProperty().divide(1500).add(0.8);
        
        lblHeader.scaleXProperty().bind(scaleFactor);
        lblHeader.scaleYProperty().bind(scaleFactor);
        
        btnLeave.scaleXProperty().bind(scaleFactor);
        btnLeave.scaleYProperty().bind(scaleFactor);
        btnLeave.translateXProperty().bind(scaleFactor.multiply(24));
        btnLeave.translateYProperty().bind(scaleFactor.multiply(12).negate());
        
        for (Node child : horizontalGrid.getChildren()) {
            child.scaleXProperty().bind(scaleFactor);
            child.scaleYProperty().bind(scaleFactor);
        }
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
    
    private void updateWinningLine() {
        int[] indices = getWinningLineCellIndices();
        
        if (indices == null) {
            winningLine.setVisible(false);
        } else {
            int startRow = indices[0] / 3;
            int startCol = indices[0] % 3;
            int endRow = indices[1] / 3;
            int endCol = indices[1] % 3;
            
            Bounds startBounds = cellGrid.getCellBounds(startCol, startRow);
            Bounds endBounds = cellGrid.getCellBounds(endCol, endRow);
            
            final int edgeOffset = 10;
            
            if (startRow == endRow) {
                setWinningLinePosition(
                    (int) startBounds.getMinX() + edgeOffset,
                    (int) startBounds.getCenterY(),
                    (int) endBounds.getMaxX() - edgeOffset,
                    (int) endBounds.getCenterY()
                );
            } else if (startCol == endCol) {
                setWinningLinePosition(
                    (int) startBounds.getCenterX(),
                    (int) startBounds.getMinY() + edgeOffset,
                    (int) endBounds.getCenterX(),
                    (int) endBounds.getMaxY() - edgeOffset
                );
            } else if (startRow == 0 && startCol == 0) {
                setWinningLinePosition(
                    (int) startBounds.getMinX() + edgeOffset,
                    (int) startBounds.getMinY() + edgeOffset,
                    (int) endBounds.getMaxX() - edgeOffset,
                    (int) endBounds.getMaxY() - edgeOffset
                );
            } else {
                setWinningLinePosition(
                    (int) startBounds.getMaxX() - edgeOffset,
                    (int) startBounds.getMinY() + edgeOffset,
                    (int) endBounds.getMinX() + edgeOffset,
                    (int) endBounds.getMaxY() - edgeOffset
                );
            }
            
            winningLine.setVisible(true);
        }
    }
    
    private void setWinningLinePosition(int x1, int y1, int x2, int y2) {
        winningLine.setStartX(x1);
        winningLine.setEndX(x2);
        winningLine.setStartY(y1);
        winningLine.setEndY(y2);
        
        StackPane.setMargin(winningLine, new Insets(Math.min(y1, y2), 0, 0, Math.min(x1, x2)));
    }
    
    private int[] getWinningLineCellIndices() {
        XOGameState state = game.getState();
        
        // 0 1 2
        // 3 4 5
        // 6 7 8
        
        if (!state.isEndState() || state.getWinner() == null) {
            return null;
        }
        
        XOGameMove lastMove = state.getLastMove();
        Player lastMovePlayer = lastMove.getPlayer();
        
        int row = lastMove.getRow();
        boolean isRow = true;
        
        for (int i = 0; i < 3; i++) {
            if (state.getCell(row, i) != lastMovePlayer) {
                isRow = false;
                break;
            }
        }
        
        if (isRow) {
            int startIndex = row * 3;
            int endIndex = row * 3 + 2;
            
            return new int[] {startIndex, endIndex};
        }
        
        int col = lastMove.getCol();
        boolean isCol = true;
        
        for (int i = 0; i < 3; i++) {
            if (state.getCell(i, col) != lastMovePlayer) {
                isCol = false;
                break;
            }
        }
        
        if (isCol) {
            int startIndex = col;
            int endIndex = 2 * 3 + col;
            
            return new int[]{startIndex, endIndex};
        }
        
        // (0) 1  2
        //  3  4  5
        //  6  7 (8)
        
        /*
        x o x
        o x o
        o x x
        */
        
        if (lastMovePlayer == state.getCell(0, 0) && lastMovePlayer == state.getCell(2, 2)) {
            return new int[]{0, 8};
        } else {
            return new int[]{2, 6};
        }
    }
    
    @Override
    public void onStateChange(GameState state) {
        XOGameState gameState = (XOGameState) state;
        char[] board = gameState.getBoard();
        
        Image xImage = new Image(App.class.getResource("images/Neon X.png").toExternalForm());
        Image oImage = new Image(App.class.getResource("images/Neon O.png").toExternalForm());
        
        for (int index = 0; index < 9; index++) {
            ImageView cell = (ImageView) cellGrid.getChildren().get(index);
            
            cell.setEffect(null);
            
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
        
        updateWinningLine();
        
        String alertMsg = null;
        
        if (gameState.isEndState()) {
            cellGrid.setDisable(true);
            
            Player winner = gameState.getWinner();
            
            if (winner != null) {
                XOGameMove lastMove = gameState.getLastMove();
                
                cellGrid.getChildren()
                    .get(lastMove.getIndex())
                    .setEffect(new DropShadow(
                        20,
                        lastMove.getPlayer() == Player.one
                            ? Color.MAGENTA
                            : Color.CYAN
                    ));
                
                String winnerName = (winner == Player.one) ? firstPlayerName : secondPlayerName;
                lblHeader.setText(winnerName + " wins!");
                alertMsg=winnerName+" is the WINNER. What would you like to do?";
                
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
