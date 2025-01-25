/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.javagameclient;

import com.mycompany.game.*;
import com.mycompany.game.recording.GameRecording;
import com.mycompany.game.recording.GameRecordingImpl;
import com.mycompany.game.recording.RecordingManager;
import com.mycompany.game.recording.RecordingManagerImpl;
import com.mycompany.networking.OnlinePlayer;
import com.mycompany.networking.authentication.AuthManager;
import com.mycompany.networking.game.GameManager;
import com.mycompany.networking.game.XOGameManager;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
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
import java.util.ArrayList;
import java.util.List;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.input.MouseButton;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author basel
 */
public class XoGameController implements Initializable, XOGame.Listener, GameManager.Listener<XOGameState> {

    @FXML
    private VBox root;
    @FXML
    private GridPane horizontalGrid;
    @FXML
    private Label lblHeader;
    @FXML
    private Label lblLeftPlayer;
    @FXML
    private Label lblLeftPlayerScore;
    @FXML
    private Label lblRightPlayer;
    @FXML
    private Label lblRightPlayerScore;
    @FXML
    private Line winningLine;
    @FXML
    private GridPane cellGrid;
    @FXML
    private Button btnLeave;

    private GameMode gameMode;
    private XOGame game;

    private String firstPlayerName;
    private String secondPlayerName;
    private int firstPlayerScore;
    private int secondPlayerScore;

    private Player onlinePlayerTurn;
    private OnlinePlayer onlinePlayer;
    private OnlinePlayer opponentOnlinePlayer;

    @FXML
    private MediaView mediaView;
    String msg;

    List<GameMove> moves = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DoubleBinding scaleFactor = Bindings.createDoubleBinding(
            () -> {
                double side = Math.min(root.getWidth(), root.getHeight());
                return side / 500 + 0.33;
            },
            root.widthProperty(),
            root.heightProperty()
        );

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

    public void initializeGameForOnline(OnlinePlayer onlinePlayer, OnlinePlayer opponent, Player playerTurn) {
        gameMode = GameMode.online;

        this.onlinePlayerTurn = playerTurn;
        this.onlinePlayer = onlinePlayer;
        this.opponentOnlinePlayer = opponent;

        firstPlayerName = onlinePlayer.getUsername();
        secondPlayerName = opponent.getUsername();

        switch (playerTurn) {
            case one:
                lblLeftPlayer.setText(onlinePlayer.getUsername());
                firstPlayerScore = onlinePlayer.getScore();
                lblLeftPlayerScore.setText("" + firstPlayerScore);
                
                lblRightPlayer.setText(opponent.getUsername());
                secondPlayerScore = opponentOnlinePlayer.getScore();
                lblRightPlayerScore.setText("" + secondPlayerScore);
                break;

            case two:
                lblLeftPlayer.setText(opponentOnlinePlayer.getUsername());
                firstPlayerScore = opponentOnlinePlayer.getScore();
                lblLeftPlayerScore.setText("" + firstPlayerScore);
                
                lblRightPlayer.setText(onlinePlayer.getUsername());
                secondPlayerScore = onlinePlayer.getScore();
                lblRightPlayerScore.setText("" + secondPlayerScore);
                break;
        }
        
        if (onlinePlayerTurn == Player.one) {
            lblHeader.setText("Your turn..");
        } else {
            lblHeader.setText(opponentOnlinePlayer.getUsername() + "'s turn..");
        }

        XOGameManager.getInstance().setListener(this);
    }

    public void initializeGameForLocalwithFriend(String player1, String player2) {
        gameMode = GameMode.localWithFriend;

        firstPlayerName = player1;
        secondPlayerName = player2;

        game = new XOGame();

        initializeLocalGame();
    }

    public void initializeGameForLocalWithComputer(GameAgent agent) {
        gameMode = GameMode.localWithComputer;

        firstPlayerName = "Player";
        secondPlayerName = "Computer";

        game = new XOGame();
        game.attachAgent(Player.two, agent);

        initializeLocalGame();
    }

    private void initializeLocalGame() {
        lblHeader.setText(firstPlayerName + "'s turn...");

        lblLeftPlayer.setText(firstPlayerName);
        lblRightPlayer.setText(secondPlayerName);

        lblLeftPlayerScore.setText("0");
        lblRightPlayerScore.setText("0");

        game.addListener(this);
    }

    public void initializeReplayGame(GameRecording<GameMove> gameRecording) {
        gameMode = GameMode.replay;

        firstPlayerName = gameRecording.getFirstPlayerName();
        secondPlayerName = gameRecording.getSecondPlayerName();

        lblLeftPlayer.setText(firstPlayerName);
        lblRightPlayer.setText(secondPlayerName);

        lblLeftPlayerScore.setText(String.valueOf(gameRecording.getFirstPlayerScore()));
        lblRightPlayerScore.setText(String.valueOf(gameRecording.getSecondPlayerScore()));

        game = new XOGame();
        game.addListener(this);

        List<GameMove> gameMoves = gameRecording.getMoves();

        cellGrid.setDisable(true);

        new Thread(() -> {
            for (GameMove gameMove : gameMoves) {
                try {
                    // Cast the GameMove to XOGameMove
                    XOGameMove move = (XOGameMove) gameMove;

                    // Calculate the index from the row and column
                    /*
                    0    1   2
                    0   0    1   2
                    1   3    4   5
                    2   6    7   8
                     */
                    int index = move.getRow() * 3 + move.getCol(); // For a 3x3 grid

                    // Simulate a click event on the cell
                    Platform.runLater(() -> {
                        Node cell = cellGrid.getChildren().get(index);

                        MouseEvent mouseEvent = new MouseEvent(
                            MouseEvent.MOUSE_CLICKED, // Event type
                            0, 0, // X and Y coordinates (not used in this case)
                            0, 0, // Screen coordinates (not used in this case)
                            MouseButton.PRIMARY, // Mouse button (left click)
                            1, // Click count
                            false, false, false, false, // Modifier keys (shift, ctrl, etc.)
                            true, // Popup trigger (not used in this case)
                            false, // Still since press (not used in this case)
                            false, // Primary button down (not used in this case)
                            false, // Middle button down (not used in this case)
                            false, // Secondary button down (not used in this case)
                            true, // Synthesized (indicates this is a simulated event)
                            false, // Drag event (not used in this case)
                            false, // Inertia (not used in this case)
                            null // Pick result (not used in this case)
                        );
                        cell.fireEvent(mouseEvent); // Fire the event on the cell
                    });

                    // Add a 1-second delay between moves
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    // TODO: Show lose message here.
    @FXML
    private void onLeave(ActionEvent event) throws IOException {
        if (gameMode == GameMode.online) {
            XOGameManager.getInstance().leave();
        } else {
            game.removeListener(this);
            App.switchToFXML("HomeScreen");
        }
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
            if (gameMode == GameMode.online) {
                XOGameManager.getInstance().move(new XOGameMove(index, onlinePlayerTurn));
            } else {
                XOGameState state = game.getState();
                XOGameMove move = new XOGameMove(index, state.getNextTurnPlayer());

                if (state.isValidMove(move)) {
                    cellGrid.setDisable(true);
                    game.play(move);
                    moves.add(move);
                }
            }
        }
    }

    private void updateWinningLine(XOGameState state) {
        int[] indices = state.getWinningLineLineIndices();

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

    @Override
    public void onStateChange(GameState state) {
        XOGameState gameState = (XOGameState) state;
        updateBoard(gameState);

        String alertMsg = null;

        if (gameState.isEndState()) {
            cellGrid.setDisable(true);

            Player winner = gameState.getWinner();

            if (winner != null) {
                String winnerName = (winner == Player.one) ? firstPlayerName : secondPlayerName;
                lblHeader.setText(winnerName + " wins!");
                alertMsg = winnerName + " is the WINNER. What would you like to do?";
                String videoPath;

                if (winner == Player.one) {
                    videoPath = "/com/mycompany/videos/winner.mp4";
                    msg = "You won! What would you like to do?";

                } else {
                    videoPath = "/com/mycompany/videos/loser.mp4";
                    msg = "You Lose! What would you like to do?";
                }

                Media media = new Media(getClass().getResource(videoPath).toExternalForm());
                MediaPlayer mediaPlayer = new MediaPlayer(media);

                mediaView.setMediaPlayer(mediaPlayer);

                mediaView.setVisible(true);
                Platform.runLater(() -> {
                    mediaPlayer.play();
                });

                PauseTransition pause = new PauseTransition(Duration.seconds(7));

                pause.setOnFinished((actionEvent) -> {
                    mediaPlayer.stop();
                    mediaView.setVisible(false);
                    openSaveDialogue();

                });

                pause.play();

                if (winner == Player.one) {
                    firstPlayerScore++;
                    lblLeftPlayerScore.setText("" + firstPlayerScore);

                } else {
                    secondPlayerScore++;
                    lblRightPlayerScore.setText("" + secondPlayerScore);
                }

            } else {
                lblHeader.setText("It's a draw!");

                msg = "It's a draw! What would you like to do?";

                Media media = new Media(getClass().getResource("/com/mycompany/videos/draw.mp4").toExternalForm());
                MediaPlayer mediaPlayer = new MediaPlayer(media);

                mediaView.setMediaPlayer(mediaPlayer);

                mediaView.setVisible(true);
                Platform.runLater(() -> {
                    mediaPlayer.play();
                });

                PauseTransition pause = new PauseTransition(Duration.seconds(7));
                pause.setOnFinished((actionEvent) -> {
                    mediaPlayer.stop();
                    mediaView.setVisible(false);

                    openSaveDialogue();
                });

                pause.play();
            }
        } else {

            String nextPlayerName = gameState.getNextTurnPlayer() == Player.one
                ? firstPlayerName
                : secondPlayerName;
            lblHeader.setText(nextPlayerName + "'s turn...");

            cellGrid.setDisable(false);
        }
    }

    private void updateBoard(XOGameState gameState) {
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

        updateWinningLine(gameState);

        if (gameState.isEndState() && gameState.getWinner() != null) {
            XOGameMove lastMove = gameState.getLastMove();

            cellGrid.getChildren()
                .get(lastMove.getIndex())
                .setEffect(new DropShadow(
                    20,
                    lastMove.getPlayer() == Player.one
                    ? Color.MAGENTA
                    : Color.CYAN
                ));
        }
    }

    @Override
    public void onGameStart(OnlinePlayer player, OnlinePlayer opponent, Player playerTurn) {
    }

    @Override
    public void onGameState(XOGameState newState) {
        Platform.runLater(() -> {
            if (newState.getNextTurnPlayer() == onlinePlayerTurn) {
                lblHeader.setText("Your turn..");
            } else {
                lblHeader.setText(opponentOnlinePlayer.getUsername() + "'s turn..");
            }

            updateBoard(newState);
        });
    }

    @Override
    public void onGameEnd(boolean isWinner, boolean isLoser, int score) {
        Platform.runLater(() -> {
            if (isWinner) {
                lblHeader.setText("You win!");
                
                if (this.onlinePlayerTurn == Player.one){
                    lblLeftPlayerScore.setText("" + score);
                } else {
                    lblRightPlayerScore.setText("" + score);
                }
            } else {
                if (isLoser) {
                    lblHeader.setText("You lose!");
                } else {
                    lblHeader.setText("It's a draw!");
                }
                
                if (this.onlinePlayerTurn == Player.two){
                    lblLeftPlayerScore.setText("" + score);
                } else {
                    lblRightPlayerScore.setText("" + score);
                }
            }
        });
    }

    @Override
    public void onGameError(String errorMessage) {
        UIHelper.showAlert("Game Error", errorMessage, Alert.AlertType.ERROR);
        App.switchToFXML("HomeScreen");
    }

    private void openSaveDialogue() {
        if (gameMode.equals(GameMode.replay)) {
            return;
        }
        Platform.runLater(() -> {

            ButtonType btnSave = new ButtonType("Save");
            ButtonType btnCancel = new ButtonType("Cancel");

            String alertMsg2 = msg;
            Alert alert = new Alert(Alert.AlertType.INFORMATION, alertMsg2 + " ", btnSave, btnCancel);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == btnSave) {
                GameRecording gameRecordingImpl = new GameRecordingImpl(moves, lblLeftPlayer.getText(), lblRightPlayer.
                    getText(), Integer.parseInt(lblLeftPlayerScore.getText()), Integer.parseInt(lblRightPlayerScore.
                    getText()));
                RecordingManager recordingManagerImpl = new RecordingManagerImpl();
                recordingManagerImpl.saveRecording(gameRecordingImpl);
                openSecondDialogue();
            } else if (result.get() == btnCancel) {
                openSecondDialogue();
            }

        });
    }

    private void openSecondDialogue() {
        if (gameMode.equals(GameMode.replay)) {
            return;
        }
        Platform.runLater(() -> {
            ButtonType goHomeButton = new ButtonType("Go Home");
            ButtonType keepPlayingButton = new ButtonType("Keep Playing");

            String alertMsg2 = msg;
            Alert alert = new Alert(Alert.AlertType.INFORMATION, alertMsg2 + " ", goHomeButton, keepPlayingButton);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == goHomeButton) {
                App.switchToFXML("HomeScreen");
            } else if (result.get() == keepPlayingButton) {
                game.resetGame();
            }
        });
    }

    private enum GameMode {
        localWithComputer, localWithFriend, online, replay;
    }
}
