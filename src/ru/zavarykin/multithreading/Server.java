package ru.zavarykin.multithreading;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/*Сервер
Все соединеия необходимо хранить в мапе или сете (потокобезопасных) В ситуации, когда клиент отсоединяется от сервера,
его соединение необходимо удалять из мапы / сета.

На сервере на каждое подключение должен создаваться отдельный поток - поток reader - получает
сообщания от клиента и добавляет в блокирующую очередь)

Поток writer (один поток) берет сообщение из блокирующей очереди и рассылает по всем
соединениям (из мапы / сета), кроме отправителя

 */

public class Server {
    private int port;
    private Connection connection;

    public Server(int port) {
        this.port = port;
    }

    public void start() throws IOException, ClassNotFoundException {
        try (ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Сервер запущен...");
            while (true){

                Socket clientSocket = serverSocket.accept();
                connection = new Connection(clientSocket);
                System.out.println(connection.readMessage());
                connection.sendMessage(Message
                        .getInstance("server", "hello"));
            }
        }
    }

    public static void main(String[] args) {
        int port = 8099;
        Server server = new Server(port);
        try {
            server.start();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
