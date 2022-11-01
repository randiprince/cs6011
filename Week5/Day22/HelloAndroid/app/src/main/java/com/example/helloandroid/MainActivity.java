package com.example.helloandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void sendMessage(View view){
        // do something
        Log.i("randi: ", "sendMessage: hello!");
        ConstraintLayout constraintLayout;
        constraintLayout = findViewById(R.id.layoutsos);
        constraintLayout.setBackgroundResource(R.color.teal_200);
        TextView textView = findViewById(R.id.helloWorld);
        textView.setText(R.string.text_view);
    }
}