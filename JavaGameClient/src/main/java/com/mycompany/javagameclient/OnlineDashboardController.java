/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.javagameclient;

import com.mycompany.game.Player;
import com.mycompany.game.XOGameMove;
import com.mycompany.game.XOGameState;
import com.mycompany.networking.Communicator;
import com.mycompany.networking.authentication.AuthManager;
import com.mycompany.networking.OnlinePlayer;
import com.mycompany.networking.game.GameManager;
import com.mycompany.networking.game.XOGameManager;
import com.mycompany.networking.matching.IncomingInviteRespose;
import com.mycompany.networking.matching.MatchingManager;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author AhmedAli
 */
public class OnlineDashboardController implements
    Initializable,
    AuthManager.Listener,
    MatchingManager.Listener,
    GameManager.Listener<XOGameState> {

    @FXML
    private Label labelPlayerName;
    @FXML
    private TextField searchField;
    @FXML
    private Label labelScore;
    @FXML
    private VBox availablePlayersList;
    @FXML
    private VBox inGamePlayersList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AuthManager.getInstance().setListener(this);
        MatchingManager.getInstance().setListener(this);
        XOGameManager.getInstance().setListener(this);

        labelPlayerName.setText(AuthManager.getInstance().getUsername());
    }

    private void addAvailablePlayerItem(OnlinePlayer player) {
        if (player.getUsername().equals(AuthManager.getInstance().getUsername())) {
            labelScore.setText("Score: " + player.getScore());
        } else {
            FXMLLoader loader = App.getFXMLLoader("itemAvailablePlayers");

            try {
                Node item = loader.load();
                ItemAvailablePlayersController controller = loader.getController();

                controller.setPlayer(player);

                availablePlayersList.getChildren().add(item);
            } catch (IOException ex) {
                throw new RuntimeException("Incorrect FXML file name.", ex);
            }
        }
    }

    private void addInGamePlayerItem(OnlinePlayer player) {
        if (player.getUsername().equals(AuthManager.getInstance().getUsername())) {
            labelScore.setText("Score: " + player.getScore());
        } else {
            FXMLLoader loader = App.getFXMLLoader("playersInGame");

            try {
                Node item = loader.load();
                ItemAvailablePlayersController controller = loader.getController();

                controller.setPlayer(player);

                inGamePlayersList.getChildren().add(item);
            } catch (IOException ex) {
                throw new RuntimeException("Incorrect FXML file name.", ex);
            }
        }
    }

    @FXML
    private void onBackClicked(ActionEvent event) throws IOException {
        AuthManager.getInstance().unsetListener();
        MatchingManager.getInstance().unsetListener();

        App.switchToFXML("HomeScreen");
    }

    @FXML
    private void onLogoutClicked(ActionEvent event) {
        AuthManager.getInstance().signOut();
    }

    @Override
    public void onAuthStateChange(boolean signedIn) {
        if (!signedIn) {
            AuthManager.getInstance().unsetListener();
            MatchingManager.getInstance().unsetListener();

            Platform.runLater(() -> {
                App.switchToFXML("HomeScreen");
            });
        }
    }

    @Override
    public void onPlayersCollectionsUpdated() {
        Platform.runLater(() -> {
            availablePlayersList.getChildren().clear();
            inGamePlayersList.getChildren().clear();
            
            MatchingManager.getInstance().getAvailable().forEach(player -> addAvailablePlayerItem(player));
            MatchingManager.getInstance().getInGame().forEach(player -> addInGamePlayerItem(player));
        });
    }
    
    @Override
    public void onIncomingInviteRequest(String userName) {
        UIHelper.showQuestion(
            "Incoming Invitation",
            "User " + userName + " wants to start a game with you.",
            Map.of(
                "Accept",
                () -> Communicator.getInstance().sendMessage(
                    new IncomingInviteRespose(IncomingInviteRespose.Response.ACCEPTED)),
                "Decline",
                () -> Communicator.getInstance().sendMessage(
                    new IncomingInviteRespose(IncomingInviteRespose.Response.REJECTED))
            )
        );
    }

    @Override
    public void onInviteResponse(boolean accept, boolean timeOut) {
        if (!accept) {
            AuthManager.getInstance().setListener(this);
            MatchingManager.getInstance().setListener(this);
            XOGameManager.getInstance().setListener(this);
        } else {
            if (timeOut) {
                UIHelper.showAlert(
                    "Invitation",
                    "Player did not respond to your invitation in time.",
                    Alert.AlertType.INFORMATION
                );
            } else {
                UIHelper.showAlert(
                    "Invitation Declined",
                    "Player did not want to play with you at this moment. Try again another time.",
                    Alert.AlertType.INFORMATION
                );
            }
        }
    }

    @Override
    public void onMatchingError(String errorMsg) {
        UIHelper.showAlert("Matching Error", errorMsg, Alert.AlertType.ERROR);
    }

    @Override
    public void onAuthError(String errorMsg) {
    }

    @Override
    public void onGameStart(Player player, OnlinePlayer opponent) {

    }

    @Override
    public void onGameState(XOGameState newState) {
    }

    @Override
    public void onGameEnd(boolean isWinner, boolean isLoser, int score) {
    }

    @Override
    public void onGameError(String errorMessage) {
    }

    private static <T> Set<T> intersection(Set<T> lhs, Set<T> rhs) {
        Set<T> result = new HashSet<T>(lhs);
        result.retainAll(rhs);
        return result;
    }

    private static <T> Set<T> difference(Set<T> lhs, Set<T> rhs) {
        Set<T> result = new HashSet<T>(lhs);
        result.removeAll(rhs);
        return result;
    }

}
