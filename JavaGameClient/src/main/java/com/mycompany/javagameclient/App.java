package com.mycompany.javagameclient;

import com.mycompany.networking.Communicator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void init() throws Exception {
        loadFonts();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Communicator.getInstance().setErrorListener(((errorMessage) -> {
            UIHelper.showAlert("Connection Error", errorMessage, Alert.AlertType.ERROR);
        }));

        Communicator.getInstance().setDisconnectedListener(() -> {
            Platform.runLater(() -> {
                App.switchToFXML("HomeScreen");
            });
        });
        
        scene = new Scene(loadFXML("HomeScreen"));

        Communicator.getInstance().setServerAddress(showServerIpDialog());
        
        stage.setScene(scene);
        stage.show();
    }
    
    private String showServerIpDialog() {
        TextInputDialog dialog = new TextInputDialog("127.0.0.1");
        
        dialog.setHeaderText("Server IP Address");
        dialog.setContentText("Enter the IP address of the server you want to connect to.");
        
        Optional<String> result = dialog.showAndWait();
        
        return result.orElse(null);
    }

    static Stage openModal(String fxml) {
        try {
            Stage stage = new Stage(StageStyle.UTILITY);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);

            stage.setScene(new Scene(loadFXML(fxml)));
            stage.show();
            return stage;
        } catch (IOException ex) {
            throw new IllegalArgumentException("FXML file" + fxml + "not found.");
        }

    }

    static void switchModal(String fxml, Stage stage) {
        try {
            stage.setScene(new Scene(loadFXML(fxml)));
        } catch (IOException ex) {
            throw new IllegalArgumentException("FXML file" + fxml + "not found.");
        }

    }

    static void switchToFXML(String fxml) {
        try {
            scene.setRoot(loadFXML(fxml));
        } catch (IOException ex) {
            throw new IllegalArgumentException("FXML file " + fxml + " not found.");
        }
    }

    static void setRoot(Parent root) throws IOException {
        scene.setRoot(root);
    }

    static FXMLLoader getFXMLLoader(String fxml) {
        return new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    }

    static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = getFXMLLoader(fxml);
        return fxmlLoader.load();
    }

    private static void loadFonts() {
        String[] fontFileNames = new String[]{
            "Borel-Regular.ttf",
            "Brawler-Regular.ttf",
            "Brawler-Bold.ttf",
            "Fredoka_Expanded-Regular.ttf",
            "Fredoka_Expanded-Bold.ttf",
            "Fredoka-Bold.ttf",
            "OpenSans-Regular.ttf",
            "Aldhabi-Regular.ttf",};

        for (String fontFileName : fontFileNames) {
            if (Font.loadFont(App.class.getResource("fonts/" + fontFileName).toExternalForm(), 0) == null) {
                throw new RuntimeException("Could not load font " + fontFileName + ".");
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
