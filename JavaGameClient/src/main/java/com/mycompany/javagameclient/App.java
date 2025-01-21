package com.mycompany.javagameclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.text.Font;
import javafx.stage.Modality;

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
        scene = new Scene(loadFXML("HomeScreen"));
        stage.setScene(scene);
        stage.show();
    }
    
    static void openWindow(String fxml) throws IOException{

    FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    Parent root = loader.load();

    Stage stage = new Stage();
    stage.setScene(new Scene(root));
    stage.setResizable(false); 
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