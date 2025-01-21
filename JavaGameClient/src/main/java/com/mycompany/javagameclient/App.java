package com.mycompany.javagameclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
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
//        scene = new Scene(loadFXML("HomeScreen"));
         scene = new Scene(loadFXML("onlineDashboard"));
        stage.setScene(scene);
        stage.show();
    }
    
    static void openModal(String fxml) throws IOException{
        Stage stage = new Stage(StageStyle.UTILITY);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        stage.setScene(new Scene(loadFXML(fxml)));
        stage.show();
    }

    static void switchToFXML(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
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
            "Aldhabi-Regular.ttf",
        };
        
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