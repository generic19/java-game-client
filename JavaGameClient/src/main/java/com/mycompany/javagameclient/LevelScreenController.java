 ///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */


package com.mycompany.javagameclient;

import com.mycompany.game.GameAgent;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import com.mycompany.game.*;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;


public class LevelScreenController {

    @FXML
    private StackPane levelStack;

    @FXML
    private VBox innerBox;

    @FXML
    void onBackClicked(ActionEvent event) {
            App.switchToFXML("HomeScreen");  
    }

    @FXML
    void onEasyClicked(ActionEvent event) throws IOException {
        startGameWithAgent(new EasyAgent());
    }

    @FXML
    void onHardClicked(ActionEvent event) throws IOException {
        startGameWithAgent(new HardAgent());
    }

    @FXML
    void onMediumClicked(ActionEvent event) throws IOException {
        startGameWithAgent(new MeduimAgent());
    }

    @FXML
    void initialize() {
        levelStack.setBackground(new Background(
                new BackgroundImage(
                        new Image(App.class.getResource("images/GameBackGround.png").toExternalForm()),
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
                        new BackgroundSize(1, 1, true, true, false, true)
                )
        ));

        DoubleBinding scaleFactor = levelStack.heightProperty().divide(1000).add(0.65);

        innerBox.scaleXProperty().bind(scaleFactor);
        innerBox.scaleYProperty().bind(scaleFactor);

    }

    private void startGameWithAgent(GameAgent agent) throws IOException {
        FXMLLoader loader = App.getFXMLLoader("xoGame");
        Parent root = loader.load();

        XoGameController controller = loader.getController();
        controller.initializeGameForLocalWithComputer(agent);
        

        App.setRoot(root);
    }
}
