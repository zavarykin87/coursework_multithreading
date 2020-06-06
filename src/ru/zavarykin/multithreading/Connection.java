package ru.zavarykin.multithreading;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection {

    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public Connection(Socket socket) {
        this.socket = socket;
        try {
            output = new ObjectOutputStream(this.socket.getOutputStream());
            input = new ObjectInputStream(this.socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            close();
        }
    }

    public void sendMessage(Message message) {
        message.setDateTime();
        try {
            output.writeObject(message);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
            close();
        }
    }

    public Message readMessage() {
        Message message = null;
        try {
            message = (Message) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            close();
        }
        return message;
    }

    public void close() {
        try {
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
