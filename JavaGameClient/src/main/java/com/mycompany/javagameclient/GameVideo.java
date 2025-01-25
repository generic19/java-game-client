/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package com.mycompany.javagameclient;

import javafx.scene.media.Media;

/**
 *
 * @author basel
 */
public enum GameVideo {
    winner("winner.mp4"),
    loser("loser.mp4"),
    draw("draw.mp4");
    
    private final Media media;
    
    private GameVideo(String videoFileName) {
        this.media = new Media(getClass().getResource("/com/mycompany/videos/" + videoFileName).toExternalForm());
    }

    public Media getMedia() {
        return media;
    }
}
