package com.example.androidchatclient;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;

import org.json.JSONException;
import org.json.JSONObject;

public class WebSocketHandler extends WebSocketAdapter {
    String message = "help";
    @Override
    public void onTextMessage(WebSocket ws, String payload) throws JSONException {
        JSONObject jsonObject = new JSONObject(payload);
        String type = (String) jsonObject.get("type");
        String room = (String) jsonObject.get("room");
        String user = (String) jsonObject.get("user");

        if (type.equals("join")) {
            message = user + " joined the chat";
        } else if (type.equals("message")) {
            String messageJson = (String) jsonObject.get("message");
            message = user + ": " + messageJson;
        } else {
            message = user + " left the chat";
        }

        ChatViewActivity.messages_.add(message);
        ChatViewActivity.lv_.post(new Runnable() {
            @Override
            public void run() {
                ChatViewActivity.adapter_.notifyDataSetChanged();
                ChatViewActivity.lv_.smoothScrollToPosition(ChatViewActivity.adapter_.getCount());
            }
        });
    }
}
