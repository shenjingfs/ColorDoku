package com.shenjing.colordoku;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.Chronometer;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    public static int currentTheme = 0;
    public static long leavedTime;
    public static long elapsedGameTime;
    public int currentSelectedColor = -1;
    public Block lastSelectedBlock;
    public Block currentSelectedBlock;
    public int difficulty ;
    private Block[] blocks = new Block[10];
    private int[] blockId = {
            R.id.block_0,
            R.id.block_1,
            R.id.block_2,
            R.id.block_3,
            R.id.block_4,
            R.id.block_5,
            R.id.block_6,
            R.id.block_7,
            R.id.block_8,
            R.id.block_9
    };
    private GameView gameView;
    public static Chronometer chronometer;
    private int time = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (currentTheme == 0){
//            setTheme(R.style.DarkColor);
//            currentTheme = 1;
//        }else {
//            setTheme(R.style.LightColor);
//            currentTheme = 0;
//        }
        setContentView(R.layout.activity_game);

        lastSelectedBlock = (Block) findViewById(R.id.block_last);
        currentSelectedBlock = (Block) findViewById(R.id.block_current);
        gameView = (GameView) findViewById(R.id.view_game);
        gameView.setDifficulty(getIntent().getIntExtra("difficulty",0));
        for (int i = 0; i < 10; i++) {
            blocks[i] = (Block) findViewById(blockId[i]);
            blocks[i].setColor(i);
            blocks[i].setOnClickListener(this);
        }
        chronometer = (Chronometer) findViewById(R.id.chronometer);
//        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
//            @Override
//            public void onChronometerTick(Chronometer chronometer) {
////                int minute = time/60;
////                int second = time%60;
////                chronometer.setText(String.format("%02d:%02d",minute,second));
//            }
//        });
        chronometer.setBase(SystemClock.elapsedRealtime());
        leavedTime = chronometer.getBase();
        chronometer.start();
        Log.i("chronometer", "onCreate: chronometer start");
    }

    @Override
    protected void onPause() {
        super.onPause();
        leavedTime = SystemClock.elapsedRealtime();
        chronometer.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        chronometer.setBase(SystemClock.elapsedRealtime()-leavedTime + chronometer.getBase());
        chronometer.start();
    }

    @Override
    public void onClick(View view) {
        Log.i("block","Click!");

        final Block b = (Block)view;
        int selectedColor=b.getColor();

        if(gameView.lastPoint!=null)
        {
            int row = gameView.lastPoint.x;
            int col = gameView.lastPoint.y;
            if(gameView.blocks[row][col].getColor() != selectedColor){
                gameView.blocks[row][col].setColor(selectedColor);
                gameView.blocks[row][col].selected = false;
                gameView.lastPoint = null;
                currentSelectedBlock.setColor(selectedColor);

                gameView.generateAnimation(currentSelectedBlock,false);
                ScaleAnimation animation = new ScaleAnimation(1.0f,1.2f,1.0f,1.2f,
                        ScaleAnimation.RELATIVE_TO_SELF,0.5f,ScaleAnimation.RELATIVE_TO_SELF,0.5f);
                animation.setDuration(100);
                b.startAnimation(animation);

                if(selectedColor == 0){
                    gameView.remainCount++;
                }else{
                    gameView.remainCount--;
                }
                gameView.isGameOver();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
