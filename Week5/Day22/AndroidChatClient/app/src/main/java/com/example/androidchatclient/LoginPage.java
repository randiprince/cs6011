package com.example.androidchatclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketFactory;

import org.json.JSONObject;

import java.io.IOException;

public class LoginPage extends AppCompatActivity {

    public static WebSocket webSocket_;
    public static JSONObject jsonObject_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
//        setContentView(R.layout.chat_messages);
        try {
            webSocket_ = new WebSocketFactory().createSocket("ws://10.0.2.2:8080/something");
        } catch (IOException e) {
            e.printStackTrace();
        }
        webSocket_.addListener(new WebSocketHandler());
        webSocket_.connectAsynchronously();
    }

    public void enterRoomHandler(View view) {
        Intent intent = new Intent(this, ChatViewActivity.class);

        TextInputEditText room = findViewById(R.id.roomInput);
        String roomname = room.getText().toString();
        TextInputEditText name = findViewById(R.id.userInput);
        String username = name.getText().toString();
        intent.putExtra("roomname", roomname);
        intent.putExtra("username", username);

        startActivity(intent);
        webSocket_.sendText("join " + username + " " + roomname);
    }
}