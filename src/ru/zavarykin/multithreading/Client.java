package ru.zavarykin.multithreading;

import java.io.*;
import java.net.Socket;


public class Client {
    private String ip;
    private int port;
    private Connection connection;
    private Socket socket;

    public Client(String ip, int port) throws IOException {
        this.ip = ip;
        this.port = port;
        socket = new Socket(ip, port);
        connection = new Connection(socket);
        readMessageFromServer.start();
    }

    // принимает сообщения от сервера и выводит его в консоль
    Thread readMessageFromServer = new Thread(()-> {
        Message fromServer = null;
        while (true) {
            try {
                fromServer = connection.readMessage();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("сообщение от сервера " + fromServer);
        }
    });


    // формирует сообщение от пользователя из консоли и отправляет его на сервер
    public void sendMessageOnServer() throws Exception {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Ведите имя");
            String name = reader.readLine();
            String text;
            while (true) {
                System.out.println("Введите сообщение");
                text = reader.readLine();
                connection.sendMessage(Message.getInstance(name, text));
            }
        }
    }

    public static void main(String[] args) {
        int port = 8099;
        String ip = "127.0.0.1";
        try {
            new Client(ip, port).sendMessageOnServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

