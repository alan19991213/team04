package com.example.project20210608_4roup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class gameActivity_1 extends AppCompatActivity {
    private LinearLayout scoreLayout;
    private LinearLayout highScoreLayout;
    private TextView scoreTextView;
    private TextView highScoreTextView;
    private long score;
    private long highScore;
    private TextView[][] cellTextViewMatrix;
    private int[][] cellValueMatrix;
    public float upX,upY,downX,downY;//滑動判定變數
    ArrayList<Pair<Integer,Integer>> blankPairs;
    HashMap<Integer,Integer> correspondingColor;
    Button playAgainButton;
    boolean isGame;
    SharedPreferences sharedPreferences;

    public void reset(View v) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                cellValueMatrix[i][j] = 0;
                fillCellTextView(cellTextViewMatrix[i][j], cellValueMatrix[i][j]);
                blankPairs.add(new Pair<Integer, Integer>(i, j));
            }
        }
        isGame=true;
        score = 0;
        scoreTextView.setText(""+score);
        highScore = sharedPreferences.getLong("highScore",0);
        highScoreTextView.setText(""+highScore);
        fillRandomNo();
        fillRandomNo();
    }

    boolean isGameOver(){
        if(cellValueMatrix[0][0]==0)
            return false;
        for(int i=0;i<4;i++) {
            for(int j=1;j<4;j++){
                if(cellValueMatrix[i][j]==0 || cellValueMatrix[i][j]==cellValueMatrix[i][j-1])
                    return false;
            }
        }
        for(int j=0;j<4;j++) {
            for(int i=1;i<4;i++){
                if(cellValueMatrix[i][j]==0 || cellValueMatrix[i][j]==cellValueMatrix[i-1][j])
                    return false;
            }
        }
        return true;
    }

    void fillRandomNo() {
        if(blankPairs.isEmpty())
            return;
        Random random = new Random();
        int randomIndex = random.nextInt(blankPairs.size());
        Pair<Integer,Integer> randomBlankCell = blankPairs.get(randomIndex);
        int x = randomBlankCell.first;
        int y = randomBlankCell.second;
        blankPairs.remove(randomIndex);
        int fillValue = random.nextInt(2);
        if(fillValue==0)
            cellValueMatrix[x][y]=2;
        else
            cellValueMatrix[x][y]=4;
        fillCellTextView(cellTextViewMatrix[x][y],cellValueMatrix[x][y]);
    }

    @SuppressLint("SetTextI18n")
    void fillCellTextView(TextView textView, int num) {
        if(num==0)
            textView.setText("");
        else
            textView.setText(""+num);
        switch(num)
        {
            case 0: textView.setBackgroundColor(Color.LTGRAY);
                textView.setTextColor(Color.BLACK);
                break;
            case 2: textView.setBackgroundColor(Color.rgb(240,240,240));
                textView.setTextColor(Color.BLACK);
                break;
            case 4: textView.setBackgroundColor(Color.rgb(255,255,224));
                textView.setTextColor(Color.BLACK);
                break;
            case 8: textView.setBackgroundColor(Color.rgb(255,200,100));
                textView.setTextColor(Color.WHITE);
                break;
            case 16: textView.setBackgroundColor(Color.rgb(255,140,30));
                textView.setTextColor(Color.WHITE);
                break;
            case 32: textView.setBackgroundColor(Color.rgb(255,100,65));
                textView.setTextColor(Color.WHITE);
                break;
            case 64: textView.setBackgroundColor(Color.rgb(250,80,100));
                textView.setTextColor(Color.WHITE);
                break;
            case 128: textView.setBackgroundColor(Color.rgb(255,220,0));
                textView.setTextColor(Color.WHITE);
                break;
            case 256: textView.setBackgroundColor(Color.rgb(240,240,0));
                textView.setTextColor(Color.BLACK);
                break;
            case 512: textView.setBackgroundColor(Color.rgb(245,245,0));
                textView.setTextColor(Color.BLACK);
                break;
            case 1024: textView.setBackgroundColor(Color.rgb(250,250,0));
                textView.setTextColor(Color.BLACK);
                break;
            default: textView.setBackgroundColor(Color.rgb(255,255,0));
                textView.setTextColor(Color.BLACK);
        }
    }
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_1);
        scoreLayout = findViewById(R.id.scoreLayout);

        highScoreLayout = findViewById(R.id.highScoreLayout);
        scoreTextView = findViewById(R.id.scoreTextView);
        highScoreTextView = findViewById(R.id.highScoreTextView);
        score = 0;
        //scoreTextView.setText(""+score);
        cellTextViewMatrix = new TextView[][]{
                {(TextView)findViewById(R.id.cell1),(TextView)findViewById(R.id.cell2),(TextView)findViewById(R.id.cell3),(TextView)findViewById(R.id.cell4)},
                {(TextView)findViewById(R.id.cell5),(TextView)findViewById(R.id.cell6),(TextView)findViewById(R.id.cell7),(TextView)findViewById(R.id.cell8)},
                {(TextView)findViewById(R.id.cell9),(TextView)findViewById(R.id.cell10),(TextView)findViewById(R.id.cell11),(TextView)findViewById(R.id.cell12)},
                {(TextView)findViewById(R.id.cell13),(TextView)findViewById(R.id.cell14),(TextView)findViewById(R.id.cell15),(TextView)findViewById(R.id.cell16)}
        };
        cellValueMatrix = new int[][]{{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
        blankPairs = new ArrayList<Pair<Integer,Integer> >();
        for(int i=0;i<4;i++) {
            for (int j = 0; j < 4; j++)
                blankPairs.add(new Pair<Integer, Integer>(i, j));
        }
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<4;j++)
                fillCellTextView(cellTextViewMatrix[i][j],cellValueMatrix[i][j]);
        }
        fillRandomNo();
        fillRandomNo();
        playAgainButton = (Button)findViewById(R.id.playAgainButton);
        isGame=true;
        sharedPreferences = this.getSharedPreferences("com.example.android.a2048",MODE_PRIVATE);
        highScore = sharedPreferences.getLong("highScore",0);
        highScoreTextView.setText(""+highScore);
        correspondingColor = new HashMap<Integer, Integer>();
        correspondingColor.put(0,Color.argb(1,220,0,0));
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float X = event.getX(); // 觸控的 X 軸位置
        float Y = event.getY(); // 觸控的 Y 軸位置



        switch (event.getAction()) { // 判斷觸控的動作

            case MotionEvent.ACTION_DOWN: // 按下
                downX = event.getX();
                downY = event.getY();

                return true;
            case MotionEvent.ACTION_MOVE: // 拖曳

                return true;
            case MotionEvent.ACTION_UP: // 放開
                Log.d("onTouchEvent-ACTION_UP","UP");
                upX = event.getX();
                upY = event.getY();
                float x=Math.abs(upX-downX);
                float y=Math.abs(upY-downY);
                double z=Math.sqrt(x*x+y*y);
                int jiaodu=Math.round((float)(Math.asin(y/z)/Math.PI*180));//角度

                if (upY < downY && jiaodu>45) {//上
                    Log.d("onTouchEvent-ACTION_UP","角度:"+jiaodu+", 動作:上");
                    onSwipeUp();
                }else if(upY > downY && jiaodu>45) {//下
                    Log.d("onTouchEvent-ACTION_UP","角度:"+jiaodu+", 動作:下");
                    onSwipeDown();
                }else if(upX < downX && jiaodu<=45) {//左
                    Log.d("onTouchEvent-ACTION_UP","角度:"+jiaodu+", 動作:左");
                    onSwipeLeft();
                }else if(upX > downX && jiaodu<=45) {//右
                    Log.d("onTouchEvent-ACTION_UP","角度:"+jiaodu+", 動作:右");
                    onSwipeRight();
                }
                return true;
        }
        return super.onTouchEvent(event);
    }

    private void onSwipeUp() {
        if(!isGame)
            return;
        boolean onSwipeUpChange=false;
        for(int j=0;j<4;j++)
        {
            for(int i=1;i<4;i++)
            {
                if(cellValueMatrix[i][j]==0)
                    continue;
                int k=i-1;
                while(k>=0 && cellValueMatrix[k][j]==0)
                    k--;
                if( k==-1 || (cellValueMatrix[k][j]!=cellValueMatrix[i][j] && (k+1)!=i) ) {
                    cellValueMatrix[k + 1][j] = cellValueMatrix[i][j];
                    onSwipeUpChange=true;
                    blankPairs.remove(new Pair<Integer, Integer>(k+1,j) );
                    cellValueMatrix[i][j]=0;
                } else if(cellValueMatrix[k][j]==cellValueMatrix[i][j]){
                    cellValueMatrix[k][j] += cellValueMatrix[i][j];
                    onSwipeUpChange=true;
                    score+=2*cellValueMatrix[i][j];
                    scoreTextView.setText(""+score);
                    cellValueMatrix[i][j]=0;
                }
            }
            for(int i=0;i<4;i++) {
                fillCellTextView(cellTextViewMatrix[i][j],cellValueMatrix[i][j]);
                if(cellValueMatrix[i][j]==0 && !blankPairs.contains(new Pair<Integer,Integer>(i,j)))
                    blankPairs.add(new Pair<Integer,Integer>(i,j));
            }
        }
        if(onSwipeUpChange)
            fillRandomNo();
        if(isGameOver()) {
            highScore = sharedPreferences.getLong("highScore",0);
            if(score>highScore)
                sharedPreferences.edit().putLong("highScore",score).apply();
            Toast.makeText(getApplicationContext(), "Game Over", Toast.LENGTH_SHORT).show();
            isGame=false;
            playAgainButton.setVisibility(View.VISIBLE);
        }
    }

    private  void onSwipeDown() {
        if(!isGame)
            return;
        boolean onSwipeDownChange = false;
        for(int j=0;j<4;j++)
        {
            for(int i=2;i>=0;i--)
            {
                if(cellValueMatrix[i][j]==0)
                    continue;
                int k=i+1;
                while(k<=3 && cellValueMatrix[k][j]==0)
                    k++;
                if( k==4 || (cellValueMatrix[k][j]!=cellValueMatrix[i][j] && (k-1)!=i) ) {
                    onSwipeDownChange = true;
                    cellValueMatrix[k-1][j] = cellValueMatrix[i][j];
                    blankPairs.remove(new Pair<Integer, Integer>(k-1,j) );
                    cellValueMatrix[i][j]=0;
                } else if(cellValueMatrix[k][j]==cellValueMatrix[i][j]){
                    onSwipeDownChange = true;
                    cellValueMatrix[k][j] += cellValueMatrix[i][j];
                    score+=2*cellValueMatrix[i][j];
                    scoreTextView.setText(""+score);
                    cellValueMatrix[i][j]=0;
                }
            }
            for(int i=0;i<4;i++) {
                fillCellTextView(cellTextViewMatrix[i][j],cellValueMatrix[i][j]);
                if(cellValueMatrix[i][j]==0 && !blankPairs.contains(new Pair<Integer,Integer>(i,j)))
                    blankPairs.add(new Pair<Integer,Integer>(i,j));
            }
        }
        if(onSwipeDownChange)
            fillRandomNo();
        if(isGameOver()) {
            highScore = sharedPreferences.getLong("highScore",0);
            if(score>highScore)
                sharedPreferences.edit().putLong("highScore",score).apply();
            Toast.makeText(getApplicationContext(), "Game Over", Toast.LENGTH_SHORT).show();
            isGame=false;
            playAgainButton.setVisibility(View.VISIBLE);
        }
    }

    private  void onSwipeLeft() {
        if(!isGame)
            return;
        boolean onSwipeLeftChange = false;
        for(int i=0;i<4;i++)
        {
            for(int j=1;j<4;j++)
            {
                if(cellValueMatrix[i][j]==0)
                    continue;
                int k=j-1;
                while(k>=0 && cellValueMatrix[i][k]==0)
                    k--;
                if( k==-1 || (cellValueMatrix[i][k]!=cellValueMatrix[i][j] && (k+1)!=j) ) {
                    onSwipeLeftChange = true;
                    cellValueMatrix[i][k+1] = cellValueMatrix[i][j];
                    blankPairs.remove(new Pair<Integer, Integer>(i,k+1) );
                    cellValueMatrix[i][j]=0;
                } else if(cellValueMatrix[i][k]==cellValueMatrix[i][j]){
                    onSwipeLeftChange = true;
                    cellValueMatrix[i][k] += cellValueMatrix[i][j];
                    score+=2*cellValueMatrix[i][j];
                    scoreTextView.setText(""+score);
                    cellValueMatrix[i][j]=0;
                }
            }
            for(int j=0;j<4;j++) {
                fillCellTextView(cellTextViewMatrix[i][j],cellValueMatrix[i][j]);
                if(cellValueMatrix[i][j]==0 && !blankPairs.contains(new Pair<Integer,Integer>(i,j)))
                    blankPairs.add(new Pair<Integer,Integer>(i,j));
            }
        }
        if(onSwipeLeftChange)
            fillRandomNo();
        if(isGameOver()) {
            highScore = sharedPreferences.getLong("highScore",0);
            if(score>highScore)
                sharedPreferences.edit().putLong("highScore",score).apply();
            Toast.makeText(getApplicationContext(), "Game Over", Toast.LENGTH_SHORT).show();
            isGame=false;
            playAgainButton.setVisibility(View.VISIBLE);
        }
    }

    private  void onSwipeRight() {
        if(!isGame)
            return;
        boolean onSwipeRightChange = false;
        for(int i=0;i<4;i++)
        {
            for(int j=2;j>=0;j--)
            {
                if(cellValueMatrix[i][j]==0)
                    continue;
                int k=j+1;
                while(k<=3 && cellValueMatrix[i][k]==0)
                    k++;
                if( k==4 || (cellValueMatrix[i][k]!=cellValueMatrix[i][j] && (k-1)!=j) ) {
                    onSwipeRightChange = true;
                    cellValueMatrix[i][k-1] = cellValueMatrix[i][j];
                    blankPairs.remove(new Pair<Integer, Integer>(i,k-1) );
                    cellValueMatrix[i][j]=0;
                } else if(cellValueMatrix[i][k]==cellValueMatrix[i][j]){
                    onSwipeRightChange = true;
                    cellValueMatrix[i][k] += cellValueMatrix[i][j];
                    score+=2*cellValueMatrix[i][j];
                    scoreTextView.setText(""+score);
                    cellValueMatrix[i][j]=0;
                }
            }
            for(int j=0;j<4;j++) {
                fillCellTextView(cellTextViewMatrix[i][j],cellValueMatrix[i][j]);
                if(cellValueMatrix[i][j]==0 && !blankPairs.contains(new Pair<Integer,Integer>(i,j)))
                    blankPairs.add(new Pair<Integer,Integer>(i,j));
            }
        }
        if(onSwipeRightChange)
            fillRandomNo();
        if(isGameOver()) {
            highScore = sharedPreferences.getLong("highScore",0);
            if(score>highScore)
                sharedPreferences.edit().putLong("highScore",score).apply();
            Toast.makeText(getApplicationContext(), "Game Over", Toast.LENGTH_SHORT).show();
            isGame=false;
            playAgainButton.setVisibility(View.VISIBLE);
        }
    }

}