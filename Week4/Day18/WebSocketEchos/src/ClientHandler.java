import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;

public class ClientHandler implements Runnable {
    Socket socket_ = null;
    String fileName_ = null;
    HashMap<String, String> headerInfo_ = new HashMap<>();
    Room room_ = null;

    String userName_ = null;
    public ClientHandler(Socket clientSocket) {
        socket_ = clientSocket;
    }

    @Override
    public void run() {
        BufferedReader bufferedReader = null;
//        PrintWriter printWriter = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(this.socket_.getInputStream()));
            this.getHeader(bufferedReader);
            System.out.println(headerInfo_.containsKey("sec-websocket-key"));

            if (this.headerInfo_.containsKey("sec-websocket-key")) { // if in header, we know it's a web socket request
                this.handleWebSocketRequest();
                return; // if websocket, do not call file request...we only do that once
            }
            this.handleHTMLFileRequest(this.socket_.getOutputStream());
            this.socket_.close();

        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void handleWebSocketRequest() throws NoSuchAlgorithmException, IOException {
        OutputStream out = this.socket_.getOutputStream();
        String key = this.headerInfo_.get("sec-websocket-key");
        String encodedKey = Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA-1").digest((key + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11").getBytes(StandardCharsets.UTF_8)));
        out.write(("HTTP/1.1 101 Switching Protocols\r\nUpgrade: websocket\r\nConnection: Upgrade\r\nSec-WebSocket-Accept: " + encodedKey + "\r\n\r\n").getBytes());
        out.flush();
        System.out.print("done with handshake. web socket handled");
        // now we need to handle if client tries to do ws.send()...ask sending a message. need to read it
        // read with DataInputStream
        try {
            while(true) {
                this.readWebSocketMsg();
            }

        } catch (Exception e) {
            if (this.room_ != null) {
                this.room_.removeClient(this);
//                String jsObject = "{ \"type\" : \"leave\","
//                        + " \"room\" : " + "\"" + this.room_.roomName_ + "\"" + ", "
//                        + "\"user\" : " + "\"" + this.userName_ + "\"" + " }";
//                this.room_.sendClientMessage(jsObject, false);
            }
            e.printStackTrace();
        }
    }

    public void readWebSocketMsg() throws IOException {
        DataInputStream inputStream = new DataInputStream(this.socket_.getInputStream());
        byte[] data = inputStream.readNBytes(2); // read first two bytes
//        boolean mask3 = (data[0] & 128) > 0;
//        int var4 = var2[0] & 15;
//        if (var4 == 8) {
//            throw new Exception("Connection Closed");
//        }
        boolean isMasked = (data[1] & 0x80) > 0; // 1000 0000
        int length = (data[1] & 0x7F); // 0111 1111
//        if (length == 126) {
//            length = inputStream.readUnsignedShort();
//        } else if (length == 127) {
//            length = (int)inputStream.readLong();
//        }
        byte[] mask = inputStream.readNBytes(4);

        // now read in payload
        byte[] payload = inputStream.readNBytes(length);
        // must fix payload by unmasking it
        byte[] decoded = payload;
        for (int i = 0; i < payload.length; i++) {
            decoded[i] = (byte) (payload[i] ^ mask[i%4]);
        }
        String[] message = new String(decoded).split(" ", 2);
        System.out.println(message);
        String commmand = message[0];
        System.out.println(commmand);

        if (commmand.equals("join")) {
            // add user to list of clients
            // user name is messages.split(" ")[1]
            // add user to room - client handler store what room in, room just handles clients and messages
            // room name is  messages.split(" ")[2]
            //so...we need room class that contains the name, list of clients, list of messages
            // need to be able to add or remove clients from the room
            // add user with Room.addClient, then have addClient send join message to websocket

            String[] info = message[1].split(" ", 2);
            String userName = info[0];
            this.userName_ = userName;
            String roomName = info[1];
            // need to get room in case it is already being used
            Room roomGet = Room.getRoom(roomName);
            this.room_ = roomGet;
            // add whole user to room
            roomGet.addClient(this);
        } else if (commmand.equals("leave")) {
            // remove user from list of clients
            // set client room to null
            // remove user with Room.removeClient, then have removeClient send leave message to websocket
            this.room_.removeClient(this);
        } else {
            // add message to log of room messages
            // client send message to room with Room.sendMessage and then room sends message to websocket
            System.out.println("line 120: " + message);
            String messageToSend = message[1];
            System.out.println("line 122: " + messageToSend);
            String jsonObj = "{ \"type\": \"message\","
                    + " \"user\" : " + "\"" + this.userName_ + "\"" + ", "
                    + "\"message\" : " + "\"" + messageToSend + " \"}";
            this.room_.sendClientsMessage(jsonObj, true);
        }
    }

    public synchronized void sendWebSocketMsg(String message) throws IOException {
        System.out.println("send message: " + message);
        DataOutputStream outputStream = new DataOutputStream(this.socket_.getOutputStream());
        // send the ascii message as a websocket message - in first byte : final, reserve x3, opcode (5 total)
        // 1000 0001
        outputStream.writeByte(129);
        // 2nd byte is: mask bit length of msg...when server back to client we dont mask
//        outputStream.writeByte(message.length());

        if (message.length() < 126) {
            outputStream.write(message.length());
        } else if (message.length() < 65535) {
            outputStream.writeByte(126);
            outputStream.writeShort(message.length());
        } else {
            outputStream.writeByte(127);
            outputStream.writeLong((long) message.length());
        }

        outputStream.write(message.getBytes());
        outputStream.flush();
    }

    private void getHeader(BufferedReader reader) throws IOException {
        String headerValues = reader.readLine(); // first header line to get file name
        this.fileName_ = headerValues.split(" ")[1];

        if (this.fileName_.equals("/")) {
            this.fileName_ = "/index.html";
        }
        this.fileName_ = "resources" + this.fileName_;
//        System.out.println(fileName_);

        boolean haveAllPairs = false;
        while (!haveAllPairs) { // get key value pairs of header
            headerValues = reader.readLine();
            if (headerValues.equals("")) { // blank line means end of header, and we have all the header values
                haveAllPairs = true;
            } else {
                String[] values = headerValues.split(": ");
//                System.out.println(values[0]);
//                System.out.println(values[1]);
                this.headerInfo_.put(values[0].toLowerCase(), values[1]);

            }

        }
    }

    public void handleHTMLFileRequest(OutputStream outputStream) throws IOException {

        PrintWriter printWriter = new PrintWriter(outputStream, true);
        File file_ = new File(this.fileName_);
        String result = "200 Success";
        String body = null;
        long length;
        String fileType;
        FileInputStream fileInputStream = null;
//        boolean isFile = false;
        if (!file_.exists()) {
            result = "404 Not Found";
            body = "<html><head></head><body>404: File Not Found</body></html>";
            length = body.length();
            fileType = "html";
        } else {
            length = file_.length();
            fileInputStream = new FileInputStream(file_);
            int getFileType = this.fileName_.lastIndexOf(".");
            fileType = this.fileName_.substring(getFileType + 1);
            if (fileType.equals("js")) {
                fileType = "javascript";
//                isFile = true;
            }
//            isFile = true;
//            if (!isFile) {
//                result = "400 Bad Request";
//                body = "<html><head></head><body>400: Bad Request for file " + this.fileName_ + "</body></html>";
//                length = body.length();
//                fileType = "html";
//            }

        }

        printWriter.println("HTTP/1.1 " + result);
        printWriter.println("Content-type: " + "text/" + fileType);
        printWriter.println("Content-length: " + length);
        printWriter.println("");
        if (body == null) {
            fileInputStream.transferTo(outputStream);
        } else {
            printWriter.println(body);
        }

        printWriter.flush();
    }

}
