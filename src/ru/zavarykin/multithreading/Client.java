package ru.zavarykin.multithreading;

import java.io.*;

public class Client {
    private String ip;
    private int port;
    private Connection connection;

    public Client(String ip, int port) throws IOException {
        this.ip = ip;
        this.port = port;
        connection = new Connection(this.ip, this.port);
    }

    // формирует сообщение от пользователя из консоли и отправляет его на сервер
    public void sendMessageToServer() throws Exception {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                Message message = new Message(reader.readLine());
                connection.sendMessage(message);
                if(message.getText().equalsIgnoreCase("exit")){
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Добро пожаловать в чат! Введите ваше сообщение...\nДля выхода из чата введите exit");
        int port = 8099;
        String ip = "127.0.0.1";
        try {
            new Client(ip, port).sendMessageToServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

