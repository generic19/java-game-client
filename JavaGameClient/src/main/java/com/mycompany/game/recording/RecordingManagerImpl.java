/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.game.recording;

import com.mycompany.game.GameMove;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 * @author AhmedAli
 */
public class RecordingManagerImpl implements RecordingManager<GameMove>{
    @Override
    public List<File> getRecordings() {
        // getting current directory of the project
        File directory = new File(System.getProperty("user.dir"));
        // filtering txt files
        FilenameFilter filenameFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().contains(".xo");
            }
        };
        
        File[] fileArray = directory.listFiles(filenameFilter);
        List<File> fileList = new ArrayList<>();
        
        fileList.addAll(Arrays.asList(fileArray));
        return fileList;
         
    }

    @Override
    public void saveRecording(GameRecording recording) {
        Date date = new Date();
        String fileName = recording.getFirstPlayerName() + " VS " + recording.getSecondPlayerName() + date.toString() + ".xo";     
        try {
            ObjectOutputStream fos = new ObjectOutputStream(new FileOutputStream(fileName));
            fos.writeObject(recording);
            System.out.println("recording Saved successfully");
        } catch (IOException ex) {
            System.out.println("can not save the recordin");
            ex.printStackTrace();
        }
    }

    @Override
    public GameRecording<GameMove> loadRecording(File file) {
        GameRecording<GameMove> gameRecording = null;
        ObjectInputStream ois = null;
        
        try {
            FileInputStream fis = new FileInputStream(file);

            if(fis != null){
                ois = new ObjectInputStream(fis);
            }
            
            gameRecording = (GameRecording<GameMove>) ois.readObject();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return gameRecording;
    }

    @Override
    public boolean deldeteFile(File file) {
        boolean deleted = false;
        if(file.exists() && file.isFile() && file.getName().endsWith(".xo")){
            deleted = file.delete();
            if(deleted){
                System.out.println("The file deleted" + file.getName());
            } else {
                System.out.println("can not delete the file" + file.getName());
            }
        } else{
            System.out.println("Invalid recording file" + file.getName());
            
        }
        
        return deleted;
    }
}
