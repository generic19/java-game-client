package com.mycompany.networking;

import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author ayasa
 */
public class CommunicatorImpl implements Communicator {
    private static volatile CommunicatorImpl instance;
    
    private final Map<Class<? extends Message>, Listener> listeners = new ConcurrentHashMap<>();
    private ErrorListener errorListener;
    private DisconnectedListener disconnectedListener;
    
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private boolean isClosing;
    
    private Thread thread;
    
    private Optional<String> serverAddress = Optional.empty();
    
    public static CommunicatorImpl getInstance() {
        if (instance == null) {
            synchronized (CommunicatorImpl.class) {
                if (instance == null) {
                    instance = new CommunicatorImpl();
                }
            }
        }
        
        return instance;
    }
    
    public CommunicatorImpl() {}
    
    @Override
    public <M extends Message> void setMessageListener(Class<M> messageClass, Listener<M> listener) {
        listeners.put(messageClass, listener);
    }
    
    @Override
    public void unsetMessageListener(Class<? extends Message> messageClass) {
        listeners.remove(messageClass);
    }
    
    @Override
    public void sendMessage(Message message) {
        try {
            if (isConnected()) {
                System.out.println("sending " + message + "..");
                
                outputStream.writeObject(message);
                
                System.out.println("sent.");
            } else {
                broadcastError("Not connected to server.");
            }
        } catch (IOException ex) {
            broadcastError("Connection lost with server.");
            closeConnection();
        }
    }
    
    public void setServerAddress(String ip) {
        if (socket != null) {
            throw new IllegalStateException("Cannot set server address while connected.");
        }
        serverAddress =  Optional.ofNullable(ip);
    }
    
    @Override
    public void openConnection() {
        isClosing = false;
        
        if (socket == null) {
            synchronized (this) {
                if (socket == null) {
                    try {
                        socket = new Socket(serverAddress.orElse(DEFAULT_SERVER_ADDRESS), SERVER_PORT);
                        
                        try {
                            outputStream = new ObjectOutputStream(socket.getOutputStream());
                            inputStream = new ObjectInputStream(socket.getInputStream());
                            
                            startListenerThread();
                        } catch (IOException ex) {
                            socket.close();
                            
                            socket = null;
                            inputStream = null;
                            outputStream = null;
                            
                            broadcastError("Error opening I/O streams with server.");
                        }
                    } catch (IOException ex) {
                        socket = null;
                        inputStream = null;
                        outputStream = null;
                        
                        broadcastError("Could not open connection to server.");
                    }
                }
            }
        }
    }
    
    @Override
    public void closeConnection() {
        isClosing = true;
        
        for (Listener listener : listeners.values()) {
            listener.onMessage(null);
        }
        
        listeners.clear();
        
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException ex) {
            System.err.println("Could not close socket.");
        }
        
        socket = null;
        inputStream = null;
        outputStream = null;
        
        try {
            thread.stop();
        } catch (UnsupportedOperationException ex) {
            thread.interrupt();
        }
        
        thread = null;
        
        disconnectedListener.onCommunicatorDisconnected();
    }
    
    private void broadcastError(String errorMessage) {
        System.out.println("Communicator error: " + errorMessage);
        errorListener.onCommunicatorError(errorMessage);
    }
    
    private void startListenerThread() {
        if (thread != null && thread.isAlive()) {
            return;
        }
        
        thread = new Thread(() -> {
            isClosing = false;
            
            try {
                while (true) {
                    try {
                        Object object = inputStream.readObject();
                        
                        System.out.println("received " + object + ".");
                        
                        if (object instanceof Message) {
                            Message message = (Message) object;
                            Class<? extends Message> messageClass = message.getClass();
                            
                            Listener listener = listeners.getOrDefault(messageClass, null);
                            
                            if (listener != null) {
                                listener.onMessage(messageClass.cast(message));
                            }
                        }
                    } catch (ClassNotFoundException
                        | InvalidClassException
                        | StreamCorruptedException
                        | OptionalDataException ex) {
                        
                        broadcastError("Could not interpret an incoming message from the server.");
                        
                        ex.printStackTrace();
                    }
                }
            }
            catch (IOException ex) {
                if (!isClosing) {
                    closeConnection();
                    broadcastError("Lost connection to server.");
                }
            }
        });
        
        thread.start();
    }
    
    @Override
    public void setErrorListener(ErrorListener listener) {
        errorListener = listener;
    }
    
    @Override
    public void unsetErrorListener() {
        errorListener = null;
    }
    
    @Override
    public boolean isConnected() {
        return socket != null && socket.isConnected() && thread != null && thread.isAlive();
    }
    
    @Override
    public void setDisconnectedListener(DisconnectedListener listener) {
        disconnectedListener = listener;
    }
    
    @Override
    public void unsetDisconnectedListener() {
        disconnectedListener = null;
    }
}
