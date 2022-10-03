import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);

        while (true) {
            Socket client = serverSocket.accept();
            InputStream inputStream = client.getInputStream();
            Scanner scanner = new Scanner(inputStream);

            scanner.next();
            String fileName = scanner.next();

            if (fileName.equals("/")) {
                fileName = "index.html";
            } else {
                fileName = fileName.substring(1);
            }

            String line = scanner.nextLine();

            while (!line.isEmpty()) {
                line = scanner.nextLine();
            }
            File file = new File(fileName);

            String result;
            if (file.isFile() && file.canRead()) {
                result = "200 Success";
            } else {
                result = "Error 404";
            }


            OutputStream outputStream = client.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);

            printWriter.println("http/1.x" + result);
            printWriter.println("Content-Type: text/css");
            printWriter.println("Content-Type: text/html");
            printWriter.println();

            Scanner fileReader = new Scanner(new FileInputStream(file));
            while (fileReader.hasNext()) {
                printWriter.println(fileReader.nextLine());
            }
            printWriter.close();
            client.close();
        }
    }
}