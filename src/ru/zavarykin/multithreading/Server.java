package ru.zavarykin.multithreading;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private int port;
    public static CopyOnWriteArrayList<Reader> connections;


    public Server(int port) {
        this.port = port;
        connections = new CopyOnWriteArrayList<>();
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Сервер начал работу..." + Thread.currentThread().getName());
        while (true) {
            Socket clientSocket = serverSocket.accept();
            connections.add(new Reader(clientSocket));
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server(8099);
        server.start();
    }
}

class Reader extends Thread {
    private Socket clientSocket;
    private Connection connection;
    private Message message;

    public Connection getConnection() {
        return connection;
    }

    public Reader(Socket clientSocket) {
        this.clientSocket = clientSocket;
        connection = new Connection(clientSocket);
        start();
    }
    @Override
    public void run() {
        while (true) {
            try {
                message = connection.readMessage();  // читаем сообщение от клиента
                System.out.println(message + Thread.currentThread().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (Reader ss : Server.connections) {
                if (!ss.equals(this)) {
                    ss.getConnection().sendMessage(message);
                }
            }
        }
    }
}
