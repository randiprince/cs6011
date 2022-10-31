import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import static java.lang.Thread.sleep;

public class ThreadedWebServer {

    public class MyRunnable implements Runnable {
        ServerSocket serverSocket = null;
        MyRunnable(ServerSocket ss) {
            serverSocket = ss;
        }

        @Override
        public void run() {
            Socket client = null;
            OutputStream outputStream = null;
            PrintWriter printWriter = null;
            try {
                client = serverSocket.accept();
                String fileName = getFileName(client);
                System.out.println(fileName);

                if (fileName.equals("/")) {
                    fileName = "/index.html";
                }
                fileName = "resources" + fileName;
                System.out.println(fileName);

                File file = new File(fileName);

                String result;
                if (file.isFile() && file.canRead()) {
                    result = "200 Success";
                } else {
                    result = "Error 404";
                }

                outputStream = client.getOutputStream();
                printWriter = new PrintWriter(outputStream);

                makeHeader(result, printWriter);
                readHTMLFile(file, printWriter);

            } catch (IOException e) {
                e.printStackTrace();
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

    public String getFileName(Socket client) {
        InputStream inputStream = null;
        Scanner scanner = null;
        String fileName = null;
        try {
            inputStream = client.getInputStream();
            scanner = new Scanner(inputStream);
            scanner.next();
            fileName = scanner.next();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileName;
    }

    public void makeHeader(String result, PrintWriter printWriter) {
        printWriter.println("HTTP/1.1 " + result);
        printWriter.println("Content-type: " + "text/html");
        printWriter.println("");

    }

    public void readHTMLFile(File file, PrintWriter printWriter) throws FileNotFoundException {
        Scanner fileReader = null;
        FileInputStream inputStream1 = null;
        try {
            inputStream1 = new FileInputStream(file);
            fileReader = new Scanner(inputStream1);
            while (fileReader.hasNextLine()) {
                printWriter.println(fileReader.nextLine());
                printWriter.flush();
                Thread.sleep(15);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new ThreadedWebServer();
    }
}
