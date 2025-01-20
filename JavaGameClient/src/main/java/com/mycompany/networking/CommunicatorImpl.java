/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

/**
 *
 * @author ayasa
 */
public class CommunicatorImpl implements Communicator {

    HashMap<Class, Listener> listeners = new HashMap<>();
    Socket socket;
    ObjectInputStream inputStream;
    ObjectOutputStream outputStream;
    Thread thread;

    public CommunicatorImpl() {
        try {
            initConnection();
            listenToServer();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void setListener(Class type, Listener listener) {
        listeners.put(type, listener);

    }

    @Override
    public void unsetListener(Class type, Listener listener) {
        listeners.remove(type);
    }

    @Override
    public void sendMessage(Message message) {
        try {
            outputStream.writeObject(message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void close() {
        for (Listener listener : listeners.values()) {
            listener.onMessage(null, true);

        }
        listeners.clear();
        try {
            inputStream.close();
        } catch (IOException ex1) {
            System.out.println("already closed");
        }
        thread.stop();
    }

    private void initConnection() throws IOException {
        socket = new Socket("127.0.0.1", 5555);
        inputStream = new ObjectInputStream(socket.getInputStream());
        outputStream = new ObjectOutputStream(socket.getOutputStream());
    }

    private void listenToServer() {
        thread = new Thread(() -> {
            while (true) {
                try {
                    Object obj = inputStream.readObject();
                    if (listeners.containsKey(obj.getClass())) {
                        listeners.get(obj.getClass()).onMessage((Message) (obj), false);
                    }
                } catch (IOException ex) {
                    close();
                    break;
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
        thread.start();
    }

}
