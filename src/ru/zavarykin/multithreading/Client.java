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
        while (true) {
                System.out.println(connection.readMessage());
        }
    });

    // формирует сообщение от пользователя из консоли и отправляет его на сервер
    public void sendMessageToServer() throws Exception {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.println("Введите сообщение");
                String text = reader.readLine();
                connection.sendMessage(new Message(text));
            }
        }
    }

    public static void main(String[] args) {
        int port = 8099;
        String ip = "127.0.0.1";
        try {
            new Client(ip, port).sendMessageToServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

