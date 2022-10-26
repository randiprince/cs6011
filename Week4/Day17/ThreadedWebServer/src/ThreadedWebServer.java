import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class ThreadedWebServer {
//    final ServerSocket serverSocket;

    public class MyRunnable implements Runnable {
        ServerSocket serverSocket = null;
        MyRunnable(ServerSocket ss) {
            serverSocket = ss;
        }

        @Override
        public void run() {
            Socket client = null;
            InputStream inputStream = null;
            OutputStream outputStream = null;
            Scanner scanner = null;
            Scanner fileReader = null;
            FileInputStream inputStream1 = null;
            PrintWriter printWriter = null;
            try {
                client = serverSocket.accept();
                inputStream = client.getInputStream();
                scanner = new Scanner(inputStream);
                scanner.next();
                String fileName = scanner.next();

                if (fileName.equals("/")) {
                    fileName = "index.html";
                } else {
                    fileName = fileName.substring(1);
                }
                System.out.println(fileName);

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
                
                outputStream = client.getOutputStream();
                printWriter = new PrintWriter(outputStream);

                printWriter.println("http/1.x" + result);
                printWriter.println("Content-Type: text/css");
                printWriter.println("Content-Type: text/html");
                printWriter.println(); // blank line ends request header

                inputStream1 = new FileInputStream(file);
                fileReader = new Scanner(inputStream1);
                while (fileReader.hasNextLine()) {
                    printWriter.println(fileReader.nextLine());
                    printWriter.flush();
                    Thread.sleep(150);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    if (printWriter != null) {
                        printWriter.close();
                    }
                    if (client != null) {
                        client.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ThreadedWebServer() throws InterruptedException, IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8080);
            serverSocket.setReuseAddress(true);

            while (true) {
                // creates Thread
                Thread myThreads[] = new Thread[4];
                for(int i = 0; i < myThreads.length; i++) {
                    myThreads[i] = new Thread(new MyRunnable(serverSocket));
                    myThreads[i].start();
                }

                //for loop to join
                for(int j = 0; j < myThreads.length; j++) {
                    myThreads[j].join();
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public static void main(String[] args) throws IOException, InterruptedException {
        new ThreadedWebServer();
    }
}