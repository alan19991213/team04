package com.example.project20210608_4roup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button but,but2;
        but = findViewById(R.id.button);
        but.setOnClickListener(butOnClick);
        but2 = findViewById(R.id.button2);
        but2.setOnClickListener(but2OnClick);

    }

    private View.OnClickListener butOnClick = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            Intent it = new Intent();
            it.setClass(MainActivity.this, gameActivity_1.class);
            startActivity(it);
        }
    };
    private View.OnClickListener but2OnClick = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            Intent it = new Intent();
            it.setClass(MainActivity.this, gameActivity_2.class);
            startActivity(it);
        }
    };
}