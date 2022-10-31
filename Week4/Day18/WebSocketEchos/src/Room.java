import netscape.javascript.JSException;
import netscape.javascript.JSObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Room {
    private ArrayList<ClientHandler> roomClients_ = new ArrayList<>();
    private ArrayList<String> allMessages_ = new ArrayList<>();
    public String roomName_ = null;
    static HashMap<String, Room> roomInfo_ = new HashMap();

    Room(String name) {
        this.roomName_ = name;
    }
    public synchronized static Room getRoom(String name) {
        synchronized(roomInfo_) {
            Room getRoom = (Room) roomInfo_.get(name);
//        System.out.println("ROOM NAME: "+getRoom.roomName_);
            if (getRoom == null) {
                getRoom = new Room(name);
                roomInfo_.put(name, getRoom);
            }
            return getRoom;
        }
    }

//    public String getRoomName() {
//        return this.roomName_;
//    }

    public synchronized void addClient(ClientHandler client) throws IOException {
        Iterator iterator = this.roomClients_.iterator();

        while(iterator.hasNext()) {
            ClientHandler existingClient = (ClientHandler)iterator.next();
            String jsObject = "{ \"type\" : \"join\","
                    + " \"room\" : " + "\"" + this.roomName_ + "\"" + ", "
                    + "\"user\" : " + "\"" + existingClient.userName_ + "\"" + " }";
            System.out.println("In while loop ROOM NAME: " + this.roomName_);
            System.out.println("In while loop USER NAME: " + existingClient.userName_);
            client.sendWebSocketMsg(jsObject);
        }

        iterator = this.allMessages_.iterator();
        while(iterator.hasNext()) {
            String messages = (String)iterator.next();
            client.sendWebSocketMsg(messages);
        }

        this.roomClients_.add(client);
        System.out.println("CLIENT USER NAME: " + client.userName_);
//        client.sendWebSocketMsg("hi!"); // need to send this in json so client gets type, user, and room
        String jsObject = "{ \"type\" : \"join\","
                + " \"room\" : " + "\"" + this.roomName_ + "\"" + ", "
                + "\"user\" : " + "\"" + client.userName_ + "\"" + " }";
        System.out.println("ROOM NAME: " + this.roomName_);
        System.out.println("CLIENT NAME NOT IN WHILE: " + client.userName_);
        this.sendClientsMessage(jsObject, false);

    }

    public synchronized void removeClient(ClientHandler client) throws IOException {
//        Iterator clientIterator = this.roomClients_.iterator();
//
//        while(clientIterator.hasNext()) {
//            ClientHandler existingClient = (ClientHandler)clientIterator.next();
//            String jsObject = "{ \"type\" : \"leave\","
//                    + " \"room\" : " + "\"" + this.roomName_ + "\"" + ", "
//                    + "\"user\" : " + "\"" + existingClient.userName_ + "\"" + " }";
//            client.sendWebSocketMsg(jsObject);
//        }
        this.roomClients_.remove(client);
        String jsObject = "{ \"type\" : \"leave\","
                + " \"room\" : " + "\"" + roomName_ + "\"" + ", "
                + "\"user\" : " + "\"" + client.userName_ + "\"" + " }";

        this.sendClientsMessage(jsObject, false);

    }

    public synchronized void sendClientsMessage(String message, boolean addMsg) throws IOException {
        Iterator clientIterator = this.roomClients_.iterator();

        while(clientIterator.hasNext()) {
            ClientHandler existingClient = (ClientHandler)clientIterator.next();
            System.out.println("EXISTING CLIENT: " + existingClient.userName_);
            System.out.println("MESSAGE: " + message);
            existingClient.sendWebSocketMsg(message);
        }

        if (addMsg) {
            this.allMessages_.add(message);
        }
    }
}
