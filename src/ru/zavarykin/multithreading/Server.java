package ru.zavarykin.multithreading;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
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
    public static CopyOnWriteArrayList<SomeServer> connections; // набор подключений
    //public static ArrayBlockingQueue<Message> messages; // очередь сообщений

    public Server(int port) {
        this.port = port;
        connections = new CopyOnWriteArrayList<>();
        //messages = new ArrayBlockingQueue<Message>(10);
    }
    // запускаем сервер и считываем сообщения клиентов
    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Сервер начал работу..." + Thread.currentThread().getName());
        while (true) {
            Socket clientSocket = serverSocket.accept();   // ждем соединение с клиентом
            //System.out.println("ждем подключения " + Thread.currentThread().getName());
            connections.add(new SomeServer(clientSocket)); // добавляем новое соединение в коллекцию (создается параллельный поток который слушает каждое соединение)
            //System.out.println("подключился - " + connections.get(keyMap));

        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server(8099);
        server.start();
    }
}
// принимает сообщение от клиента добавляет в блокирующую очередь
class SomeServer extends Thread {
    private Socket clientSocket;
    private Connection connection;
    private Message message;

    public Connection getConnection() {
        return connection;
    }

    public SomeServer(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        connection = new Connection(clientSocket);
        start();
    }

    @Override
    public void run() {
        while (true){
            try {
                message = connection.readMessage();  // читаем сообщение от клиента
                System.out.println(message + Thread.currentThread().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            for(SomeServer ss : Server.connections){
                try {
                    if (!ss.equals(this)) {
                        ss.getConnection().sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
