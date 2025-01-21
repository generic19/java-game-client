/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javagameclient;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import javafx.application.Platform;
import javafx.scene.control.Alert;

/**
 *
 * @author AhmedAli
 */
public class UIHelper {
    
    public static void showAlert(String header, String message, Alert.AlertType type){
        
        Platform.runLater(() -> {
            Alert alert = new Alert(type);
            alert.setHeaderText(header);
            alert.setContentText(message);
            alert.show();
        });
    }
    
    public static boolean saveTokenIntoFile(String username, String token){
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
    
    public static String getToken(){
        String token;
        
        String data = readDataFromFile();
        if(data == null){
            token = null;
        } else{
            StringTokenizer tokenizer = new StringTokenizer(data, ";");

            // Extract username and token
            if (tokenizer.countTokens() == 2) {
                String username = tokenizer.nextToken();
                token = tokenizer.nextToken();

                // Output the extracted credentials
                System.out.println("Username: " + username);
                System.out.println("Token: " + token);
            } else {
                token = null;
                System.out.println("Invalid format in the file.");
            }
        }
        
        return token;
    }
    
    public static String getUserName(){
        String userName;
        
        String data = readDataFromFile();
        if(data == null){
            userName = null;
        } else{
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
    
    
    public static String readDataFromFile(){
        String fileName = "token.txt";
        String result = null;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            
            // Read each line from the file
            if((line = reader.readLine()) != null){
                result = line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return result;
    }
}
