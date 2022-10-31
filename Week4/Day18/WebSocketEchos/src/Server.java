import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public Server() {
    }

    public static void main(String[] args) {

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(8080);
//            serverSocket.setReuseAddress(true);
        } catch (IOException e) {
            System.out.println("Failed to open server socket: " + e.getMessage());
            System.exit(-1);
        }

        while (true) {

//            Socket clientSocket = null;

            try {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                Thread thread = new Thread(clientHandler);
                thread.start();
//                (new Thread(clientHandler)).start();
            } catch (IOException e) {
                System.out.println("Server socket failed to accept: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
