package com.mycompany.networking;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author ayasa
 */
public class CommunicatorImpl implements Communicator {
    
    private static volatile CommunicatorImpl instance;
    
    private final Map<Class<? extends Message>, Listener> listeners = new ConcurrentHashMap<>();
    private final List<ErrorListener> errorListeners = new CopyOnWriteArrayList<>();
    
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    
    private Thread thread;
    
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
            if(isConnected()){
            outputStream.writeObject(message);
            
            
            }
        } catch (IOException ex) {
            broadcastError("Connection lost with server.");
            closeConnection();
        }
    }
    
    @Override
    public void openConnection() {
        try {
            socket = new Socket("127.0.0.1", 5555);
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            
            startListenerThread();
        } catch (IOException ex) {
            broadcastError("Could not open connection to server.");
        }
    }
    
    @Override
    public void closeConnection() {
        for (Listener listener : listeners.values()) {
            listener.onMessage(null);
        }
        
        listeners.clear();
        
        try {
            socket.close();
        } catch (IOException ex) {
            System.out.println("Could not close stream.");
        }
        
        thread.stop();
        thread = null;
    }
    
    private void broadcastError(String errorMessage) {
        errorListeners.forEach(l -> l.onCommunicatorError(errorMessage));
    }
    
    private void startListenerThread() {
        thread = new Thread(() -> {
            try {
                while (true) {
                    try {
                        Object object = inputStream.readObject();
                        
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
                    }
                }
            }
            catch (IOException ex) {
                closeConnection();
                broadcastError("Lost connection to server.");
            }
        });
        
        thread.start();
    }
    
    @Override
    public void addErrorListener(ErrorListener listener) {
        errorListeners.add(listener);
    }
    
    @Override
    public void removeErrorListener(ErrorListener listener) {
        errorListeners.remove(listener);
    }
    
    @Override
    public boolean isConnected() {
        return socket != null && socket.isConnected();
    }
}
