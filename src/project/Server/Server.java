package project.Server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private static ServerSocket serverSocket = null;
    private static int count = 0;

    public static void main(String[] args) throws IOException {

        try {
            serverSocket = new ServerSocket(1024);
            System.out.println("Server is waiting...");
            while(true) {
                new Thread(new ServerThread(serverSocket.accept(), ++count)).start();
                System.out.println("Client " + count + " connected to server.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            serverSocket.close();
        }
    }
}

