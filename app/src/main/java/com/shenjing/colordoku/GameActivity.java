package com.shenjing.colordoku;

import android.content.SharedPreferences;
import android.os.SystemClock;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.Chronometer;

import androidx.appcompat.app.AppCompatActivity;

import com.shenjing.colordoku.util.SpHelper;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    public static int currentTheme = 0;
    public static long leavedTime;
    public static long baseTime;
    public int currentSelectedColor = -1;
    public Block lastSelectedBlock;
    public Block currentSelectedBlock;
    private boolean firstRun = true;
    private final Block[] blocks = new Block[10];
    private final int[] blockId = {
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
    public Chronometer chronometer;


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

        lastSelectedBlock = findViewById(R.id.block_last);
        currentSelectedBlock = findViewById(R.id.block_current);
        gameView = findViewById(R.id.view_game);
        gameView.setDifficulty(getIntent().getIntExtra("difficulty", 0));
        for (int i = 0; i < 10; i++) {
            blocks[i] = findViewById(blockId[i]);
            blocks[i].setColor(i);
            blocks[i].setOnClickListener(this);
        }
        chronometer = findViewById(R.id.chronometer);
        Log.i("chronometer", "onCreate: chronometer start");
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveGame();
        leavedTime = SystemClock.elapsedRealtime();
        chronometer.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (firstRun) {
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            firstRun = false;
        } else {
            chronometer.setBase(SystemClock.elapsedRealtime() - leavedTime + baseTime);
            chronometer.start();
        }
        Log.i("TAG", "onResume: ");
    }

    @Override
    public void onClick(View view) {
        Log.i("block", "Click!");

        final Block b = (Block) view;
        int selectedColor = b.getColor();

        if (gameView.lastPoint != null) {
            int row = gameView.lastPoint.x;
            int col = gameView.lastPoint.y;
            if (GameView.blocks[row][col].getColor() != selectedColor) {
                isExistError(row, col, GameView.blocks[row][col].getColor(), selectedColor);
                GameView.blocks[row][col].setColor(selectedColor);
                GameView.blocks[row][col].selected = false;
                gameView.lastPoint = null;

                gameView.generateAnimation(currentSelectedBlock, false);
                ScaleAnimation animation = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,
                        ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
                animation.setDuration(100);
                b.startAnimation(animation);

                if (selectedColor == 0) {
                    gameView.remainCount++;
                } else {
                    gameView.remainCount--;
                }
                Log.i("TAG", "onClick: " + gameView.remainCount);
                currentSelectedBlock.setColor(selectedColor);
                gameView.isGameOver();
            }
        }
    }

    public void isExistError(int row, int col, int preColor, int selectedColor) {
        GameView.blocks[row][col].errorImage.setVisibility(View.INVISIBLE);
        boolean flag = false;
        for (int i = 0; i < 9; i++) {
            //检查行是否满足要求
            if (selectedColor == GameView.blocks[i][col].getColor() && i != row && selectedColor != 0) {
                GameView.blocks[i][col].errorImage.setVisibility(View.VISIBLE);
                flag = true;
            }
            if (preColor == GameView.blocks[i][col].getColor() && i != row) {
                GameView.blocks[i][col].errorImage.setVisibility(View.INVISIBLE);
            }

            //检查列是否满足要求
            if (selectedColor == GameView.blocks[row][i].getColor() && i != col && selectedColor != 0) {
                GameView.blocks[row][i].errorImage.setVisibility(View.VISIBLE);
                flag = true;
            }
            if (preColor == GameView.blocks[row][i].getColor() && i != col) {
                GameView.blocks[row][i].errorImage.setVisibility(View.INVISIBLE);
            }


            //检查九宫格是否满足要求
            if (selectedColor == GameView.blocks[row / 3 * 3 + i / 3][col / 3 * 3 + i % 3].getColor() && ((row / 3 * 3 + i / 3) != row || (col / 3 * 3 + i % 3) != col) &&
                    selectedColor != 0) {
                GameView.blocks[row / 3 * 3 + i / 3][col / 3 * 3 + i % 3].errorImage.setVisibility(View.VISIBLE);
                flag = true;
            }
            if (preColor == GameView.blocks[row / 3 * 3 + i / 3][col / 3 * 3 + i % 3].getColor() && ((row / 3 * 3 + i / 3) != row || (col / 3 * 3 + i % 3) != col)) {
                GameView.blocks[row / 3 * 3 + i / 3][col / 3 * 3 + i % 3].errorImage.setVisibility(View.INVISIBLE);
            }
        }
        if (flag) {
            GameView.blocks[row][col].errorImage.setVisibility(View.VISIBLE);
        }
    }

    public void saveGame() {
        SharedPreferences sharedPreferences = SpHelper.INSTANCE.getInstance();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        gameView.saveGameView();
        leavedTime = SystemClock.elapsedRealtime();
        baseTime = chronometer.getBase();
        editor.putLong("leavedTime", leavedTime);
        editor.putLong("baseTime", chronometer.getBase());
        editor.apply();
    }

    public void loadGame() {
        SharedPreferences sharedPreferences = SpHelper.INSTANCE.getInstance();
        gameView.loadGameView();
        leavedTime = sharedPreferences.getLong("leavedTime", SystemClock.elapsedRealtime());
        baseTime = sharedPreferences.getLong("baseTime", SystemClock.elapsedRealtime());
        firstRun = false;

        Log.i("Tag", "loadGame: ");
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            saveGame();
            finish();
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
