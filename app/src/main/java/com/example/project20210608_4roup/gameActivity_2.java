package com.example.project20210608_4roup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class gameActivity_2 extends AppCompatActivity {
    private int i=0;
    private TextView score_show;
    private ImageView mouse;
    private TextView info1;
    private Handler handler;
    public int[][] position=new int[][]{
            {120, 400}, {290, 380}, {450, 400}, {610,380}, {770, 380},
            {150, 740}, {365, 740}, {580,740}, {785, 740},
            {190, 1170},{460, 1170},{730,1170}
    };

    @SuppressLint({"ClickableViewAccessibility", "HandlerLeak"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_2);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        mouse = (ImageView) findViewById(R.id.imageView1);
        score_show= findViewById(R.id.score_show);
        info1 = findViewById(R.id.info);

        info1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        float x = event.getRawX();
                        float y = event.getRawY();
                        Log.i("x:" + x, "y:" + y);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {

                int index;
                if (msg.what == 0x101) {
                    index = msg.arg1;
                    mouse.setX(position[index][0]);
                    mouse.setY(position[index][1]);
                    mouse.setVisibility(View.VISIBLE);
                }
                super.handleMessage(msg);
            }
        };
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                int index = 0;
                while (!Thread.currentThread().isInterrupted()) {
                    index = new Random().nextInt(position.length);
                    Message m = handler.obtainMessage();
                    m.what = 0x101;
                    m.arg1 = index;
                    handler.sendMessage(m);
                    try {
                        Thread.sleep(new Random().nextInt(500) + 500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
        mouse.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setVisibility(View.INVISIBLE);
                i++;
                score_show.setText("打到 [ " + i + " ] 隻地鼠！");
                //Toast.makeText(gameActivity_2.this, "打到 [ " + i + " ] 隻地鼠！",
                //       Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }
}