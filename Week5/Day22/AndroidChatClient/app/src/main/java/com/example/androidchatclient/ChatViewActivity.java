package com.example.androidchatclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatViewActivity extends AppCompatActivity {
    public static ArrayList<String> messages_ = new ArrayList<>();
    public static ListView lv_ = null;
    public static ArrayAdapter adapter_ = null;
    String room_;
    String user_;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.login_page);
        setContentView(R.layout.activity_chat_view);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            room_ = extras.getString("roomname");
            user_ = extras.getString("username");
        }
        TextView header = findViewById(R.id.messageHeader);
        header.setText(room_);
        lv_ = findViewById(R.id.messageView);
        adapter_ = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, messages_);
        lv_.setAdapter(adapter_);
    }

    public void goBack(View view) {
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
        LoginPage.webSocket_.sendClose();
        LoginPage.webSocket_.disconnect();
    }

    public void sendMessages(View view) {
       TextView typedMsg = findViewById(R.id.messageText);
       String msgString = typedMsg.getText().toString();
       LoginPage.webSocket_.sendText(user_ + " " + msgString);
//       typedMsg.clearComposingText();
    }
}