/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javagameclient;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author AhmedAli
 */
public class UIHelper {

    public static void showAlert(String header, String message, Alert.AlertType type) {
        Platform.runLater(() -> {
            Alert alert = new Alert(type);
            alert.setHeaderText(header);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    public static void showQuestion(String header, String message, Map<String, Runnable> options) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.NONE);

            alert.setHeaderText(header);
            alert.setContentText(message);

            alert.getButtonTypes().clear();

            options.keySet().forEach(title -> {
                alert.getButtonTypes().add(new ButtonType(title));
            });

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent()) {
                String choice = result.get().getText();
                options.get(choice).run();
            }
        });
    }

    public static void showAlertWithButton(String header, String message, Runnable onAccept, Runnable onReject) {

        Platform.runLater(() -> {
            ButtonType accept = new ButtonType("Accept");
            ButtonType reject = new ButtonType("reject");
            Alert alert = new Alert(Alert.AlertType.INFORMATION, message + " ", accept, reject);
            alert.setHeaderText(header);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == accept) {
                onAccept.run();
            } else if (result.get() == reject) {

                onReject.run();
            }
        });
    }

    public static boolean saveTokenIntoFile(String username, String token) {
        boolean result = true;
        String data = username + ";" + token;
        String fileName = "token.txt";
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            // Convert string to byte array
            byte[] bytes = data.getBytes();

            // Write the bytes to the file
            fos.write(bytes);

            System.out.println("UserName and Token have been written to the file successfully.");
        } catch (IOException e) {
            result = false;
            e.printStackTrace();
        }

        return result;
    }

    public static boolean deleteTokenFile() {
        File file = new File("token.txt");

        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Token file deleted successfully.");
                return true;
            } else {
                System.out.println("Failed to delete token file.");
                return false;
            }
        } else {
            System.out.println("Token file does not exist.");
            return false;
        }
    }

    public static TokenData getToken() {
        String username;
        String token;

        String data = readDataFromFile();
        if (data == null) {
            token = null;
            username = null;
        } else {
            StringTokenizer tokenizer = new StringTokenizer(data, ";");

            // Extract username and token
            if (tokenizer.countTokens() == 2) {
                username = tokenizer.nextToken();
                token = tokenizer.nextToken();

                // Output the extracted credentials
                System.out.println("Username: " + username);
                System.out.println("Token: " + token);
            } else {
                username = null;
                token = null;

                System.out.println("Invalid format in the file.");
            }
        }

        return new TokenData(username, token);
    }

    public static String getUserName() {
        String userName;

        String data = readDataFromFile();
        if (data == null) {
            userName = null;
        } else {
            StringTokenizer tokenizer = new StringTokenizer(data, ";");

            // Extract username and token
            if (tokenizer.countTokens() == 2) {
                userName = tokenizer.nextToken();

                System.out.println("Username: " + userName);
            } else {
                userName = null;
                System.out.println("Invalid format in the file.");
            }
        }

        return userName;
    }

    public static String readDataFromFile() {
        String fileName = "token.txt";
        String result = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            // Read each line from the file
            if ((line = reader.readLine()) != null) {
                result = line;
            }
        } catch (IOException e) {
            return null;
        }

        return result;
    }

    public static class TokenData {

        private final String username;
        private final String token;

        public TokenData(String username, String token) {
            this.username = username;
            this.token = token;
        }

        public String getUsername() {
            return username;
        }

        public String getToken() {
            return token;
        }
    }
}
