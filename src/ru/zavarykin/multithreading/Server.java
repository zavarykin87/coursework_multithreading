package ru.zavarykin.multithreading;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class Server {
    private int port;
    public static CopyOnWriteArraySet<Connection> connections;
    public static ArrayBlockingQueue<Message> messages;

    public Server(int port) {
        this.port = port;
        connections = new CopyOnWriteArraySet<>();
        messages = new ArrayBlockingQueue<>(10);
    }
    public void startServer(){
        System.out.println("Сервер начал работу...");
        new ServerWriter().start();  // запускаем поток writer
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true){
                Socket clientSocket = serverSocket.accept();           // ожидаем новое подключение
                Connection connection = new Connection(clientSocket); // на каждое новое подключение запускаем новый поток который прослушивает клиента
                connections.add(connection);                         // добавляем новое подключение в список
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
        Server server = new Server(8099);
        server.startServer();
    }
}
// отдельный поток перебирает список соединений и рассылает по ним сообщение из очереди
class ServerWriter extends Thread {
    public void run(){

        while (true) {
            if(!Server.connections.isEmpty()) {
                Message message = null;
                try {
                    message = Server.messages.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (Connection connection: Server.connections) {
                    if(connection.getMarkerMessage()!=message) {
                        connection.sendMessage(message);
                    }
                }

            }
        }
    }

}



