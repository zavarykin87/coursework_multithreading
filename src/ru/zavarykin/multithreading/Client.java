package ru.zavarykin.multithreading;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket clientSocket;
    private BufferedReader reader;
    private ObjectInputStream input;
    private ObjectOutputStream output;


    // принимаю сообщение от пользователя
    public Message writeMessageFromClient() throws IOException {
        reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите ваше имя...");
        String name = reader.readLine();
        System.out.println("Введите ваше сообщение...");
        String msg = reader.readLine();
        Message message = new Message(name, msg);
        return message;
    }
    // отправить сообщение на сервер
    public void sendMessageOnServer(Message message) {
        try {
            clientSocket = new Socket("127.0.0.1", 8099);
            output = new ObjectOutputStream(clientSocket.getOutputStream());
            output.writeObject(message);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // получить и вывести в консоль сообщение от сервера (отдельный поток)
    Thread readMessageFromServer = new Thread(()-> {
        try {
            clientSocket = new Socket("127.0.0.1", 8099);
            input = new ObjectInputStream(clientSocket.getInputStream());
            Message message = new Message();
            message = (Message) input.readObject();
            System.out.println(message);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    });


    public static void main(String[] args) {


    }


}

