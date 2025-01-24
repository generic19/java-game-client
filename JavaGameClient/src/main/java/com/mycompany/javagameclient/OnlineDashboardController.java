/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.javagameclient;

import static com.mycompany.javagameclient.App.switchToFXML;
import com.mycompany.networking.authentication.AuthManager;
import com.mycompany.networking.Communicator;
import com.mycompany.networking.Message;
import com.mycompany.networking.OnlinePlayer;
import com.mycompany.networking.matching.IncomingInviteRespose;
import com.mycompany.networking.matching.MatchingManager;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
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
public class OnlineDashboardController implements Initializable, MatchingManager.Listener, AuthManager.Listener {

    MatchingManager matchingManager;
    Communicator communicator = Communicator.getInstance();
    Set<OnlinePlayer> availablePlayers, inGamePlayers;
    Map<OnlinePlayer, Node> availablePlayersNodes = new HashMap<>();
    Map<OnlinePlayer, Node> inGamePlayersNodes = new HashMap<>();

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

        matchingManager = new MatchingManager(communicator);

        matchingManager.addListener(this);
        availablePlayers = (matchingManager.getAvailable());
        inGamePlayers = (matchingManager.getAvailable());

        availablePlayers.add(new OnlinePlayer("basel", 0));

        for (OnlinePlayer player : availablePlayers) {
            addAvailablePlayerItem(player);
        }
        for (OnlinePlayer player : inGamePlayers) {
            addInGamePlayerItem(player);
        }
    }

    private void addAvailablePlayerItem(OnlinePlayer player) {
        FXMLLoader loader = App.getFXMLLoader("itemAvailablePlayers");
        try {
            Node element = loader.load();
            ItemAvailablePlayersController controller = loader.getController();

            controller.setPlayer(player);

            availablePlayersList.getChildren().add(element);
            availablePlayersNodes.put(player, element);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void addInGamePlayerItem(OnlinePlayer player) {
        FXMLLoader loader = App.getFXMLLoader("playersInGame");
        try {
            Node element = loader.load();
            PlayersInGameController controller = loader.getController();
            controller.setPlayer(player);
            inGamePlayersList.getChildren().add(element);
            inGamePlayersNodes.put(player, element);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void removeInGamePlayerItem(OnlinePlayer player) {
        Node node = inGamePlayersNodes.remove(player);
        inGamePlayersList.getChildren().remove(node);
    }

    private void removeAvailablePlayerItem(OnlinePlayer player) {
        Node node = availablePlayersNodes.remove(player);
        availablePlayersList.getChildren().remove(node);
    }

    @FXML
    private void onBackClicked(ActionEvent event) throws IOException {
        switchToFXML("HomeScreen");
    }

    @FXML
    private void onLogoutClicked(ActionEvent event) {
          //////////// just bu screen , the token is not deleted yet
        AuthManager.getInstance().setListener(this);
        AuthManager.getInstance().signOut();
        App.switchToFXML("HomeScreen");
        
    }

    @Override
    public void onAuthStateChange(boolean signedIn) {
        if (!signedIn) {
            App.switchToFXML("HomeScreen");
        }
    }

    @Override
    public void onMatchingUpdate() {
        Set<OnlinePlayer> newAvailablePlayers = matchingManager.getAvailable();
        Set<OnlinePlayer> newInGamePlayers = matchingManager.getInGame();
        for (OnlinePlayer player : inGamePlayersNodes.keySet()) {
            boolean inOld = inGamePlayers.contains(player);
            boolean inNew = newInGamePlayers.contains(player);

            if (inOld && !inNew) {
                addInGamePlayerItem(player);
            } else if (!inOld && inNew) {
                removeInGamePlayerItem(player);
            }
        }
        for (OnlinePlayer player : availablePlayersNodes.keySet()) {
            boolean inOld = availablePlayers.contains(player);
            boolean inNew = newAvailablePlayers.contains(player);

            if (inOld && !inNew) {
                addAvailablePlayerItem(player);
            } else if (!inOld && inNew) {
                removeAvailablePlayerItem(player);
            }
        }
    }

    @Override
    public void onIncomingInviteRequest(String userName) {
        UIHelper.showAlertWithButton(
            "Invitation",
            userName + " wants to play with you",
            () -> {
                Message message = new IncomingInviteRespose(IncomingInviteRespose.Response.ACCEPTED);
                communicator.sendMessage(message);
            },
            () -> {
                Message message = new IncomingInviteRespose(IncomingInviteRespose.Response.REJECTED);
                communicator.sendMessage(message);
            }
        );
    }

    @Override
    public void onInviteResponse(boolean accept, boolean timeOut) {
        if (accept) {
            //start the game 
        } else if (!accept || timeOut) {
            UIHelper.showAlert("Rejected", " the Invitation Has been rejected", Alert.AlertType.NONE);

        }
    }

    @Override
    public void onErrorMessage(String errorMsg) {

        UIHelper.showAlert("error", errorMsg, Alert.AlertType.NONE);
        // throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void onError(String errorMsg) {
        UIHelper.showAlert("Error", errorMsg, Alert.AlertType.ERROR);
    }
}
