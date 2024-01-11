package com.shenjing.colordoku;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Build;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.GridLayout;

import androidx.core.view.ViewCompat;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by shenjing on 2016/11/14.
 */

public class GameView extends GridLayout implements View.OnClickListener {
    public final DisplayMetrics dm = getResources().getDisplayMetrics();
    public int densityDpi = dm.densityDpi;
    public final int WIDTH = dm.widthPixels / 11;
    public final int HEIGHT = WIDTH;
    public int offest = densityDpi / 160 * 10;
    private int difficulty = 1;
    public int remainCount = 0;
    public Point lastPoint = null;
    public ArrayList<Point> points = new ArrayList<>();
    public static Block[][] blocks = new Block[9][9];
    public int[][] dokuMatrix = {
            {9, 7, 8, 3, 1, 2, 6, 4, 5},
            {3, 1, 2, 6, 4, 5, 9, 7, 8},
            {6, 4, 5, 9, 7, 8, 3, 1, 2},
            {7, 8, 9, 1, 2, 3, 4, 5, 6},
            {1, 2, 3, 4, 5, 6, 7, 8, 9},
            {4, 5, 6, 7, 8, 9, 1, 2, 3},
            {8, 9, 7, 2, 3, 1, 5, 6, 4},
            {2, 3, 1, 5, 6, 4, 8, 9, 7},
            {5, 6, 4, 8, 9, 7, 2, 3, 1}
    };
    public int[][] uncertainty = new int[9][9];
    public int degree = -1;
    private int marginEdge, marginNormal;

    public GameView(Context context) {
        super(context);
        init();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundResource(R.drawable.shape_game_view);
        if (Build.VERSION.SDK_INT < 21) {
            marginEdge = 8;
            marginNormal = 2;
        } else {
            marginEdge = 12;
            marginNormal = 3;
        }
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                blocks[row][col] = new Block(getContext());
                blocks[row][col].row = row;
                blocks[row][col].col = col;
                ViewCompat.setElevation(blocks[row][col], 4f);
                //uncertainty[row][col] = 1;
                addView(blocks[row][col], WIDTH, HEIGHT);
                LayoutParams lp = new LayoutParams();
                setMargin(lp, row, col);
                blocks[row][col].setLayoutParams(lp);
                //blocks[row][col].setColor(dokuMatrix[row][col]);
                blocks[row][col].setOnClickListener(this);
                //points.add(new Point(row, col));
            }
        }
    }

    private void setMargin(LayoutParams lp, int row, int col) {
        lp.width = WIDTH;
        lp.height = HEIGHT;
        if (row == 2 || row == 5) {
            lp.bottomMargin = marginEdge;
        } else {
            lp.bottomMargin = marginNormal;
        }
        if (row == 3 || row == 6) {
            lp.topMargin = marginEdge;
        } else {
            lp.topMargin = marginNormal;
        }
        if (col == 2 || col == 5) {
            lp.rightMargin = marginEdge;
        } else {
            lp.rightMargin = marginNormal;
        }
        if (col == 3 || col == 6) {
            lp.leftMargin = marginEdge;
        } else {
            lp.leftMargin = marginNormal;
        }
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
        generatePuzzle(difficulty);
    }

//    private void generatePuzzle() {
//        Log.i("GameView", "start");
//        int num = 50;
//        Point p;
//        switch (difficulty) {
//            case 1:
//                num = (int) (Math.random() * 5 + 33);
//                break;
//            case 2:
//                num = (int) (Math.random() * 5 + 28);
//                break;
//            case 3:
//                num = (int) (Math.random() * 5 + 23);
//                break;
//        }
//        for (int i = 0; i < num; i++) {
//            boolean flag = true;
//
//            while (flag && points.size() > 0) {
//                Log.i("GameView", "" + points.size());
//                p = points.remove((int) (Math.random() * points.size()));
//                if (uncertainty[p.x][p.y] == 1 && blocks[p.x][p.y].getColor() != 0) {
//                    blocks[p.x][p.y].setColor(0);
//                    int x = p.x / 3 * 3;
//                    int y = p.y / 3 * 3;
//                    for (int j = x; j < x + 3; j++) {
//                        for (int k = y; k < y + 3; k++) {
//                            if (blocks[j][k].degree < i) {
//                                blocks[j][k].degree = i;
//                                uncertainty[j][k] += 1;
//                                if (points.contains(new Point(j, k))) {
//                                    points.remove(new Point(j, k));
//                                }
//                            }
//                        }
//                    }
//                    for (int j = 0; j < 9; j++) {
//                        if (blocks[j][y].degree < i) {
//                            blocks[j][y].degree = i;
//                            uncertainty[j][y] += 1;
//                            if (points.contains(new Point(j, y))) {
//                                points.remove(new Point(j, y));
//                            }
//                        }
//                    }
//                    for (int k = 0; k < 9; k++) {
//                        if (blocks[x][k].degree < i) {
//                            blocks[x][k].degree = i;
//                            uncertainty[x][k] += 1;
//                            if (points.contains(new Point(x, k))) {
//                                points.remove(new Point(x, k));
//                            }
//                        }
//                    }
//                    flag = false;
//                } else {
//                    points.add(p);
//                }
//            }
//        }
//        Log.i("GameView", "end");
//    }

//    private void generatePuzzle(int difficulty) {
//        Log.i("GameView", "start");
//        int num = 50;
//        Point p;
//        switch (difficulty) {
//            case 0:
//                num = 79;
//                break;
//            case 1:
//                num = (int) (Math.random() * 5 + 40);
//                break;
//            case 2:
//                num = (int) (Math.random() * 5 + 35);
//                break;
//            case 3:
//                num = (int) (Math.random() * 5 + 30);
//                break;
//        }
//        for (int i = 81; i > num; i--) {
//            p = points.remove((int) (Math.random() * points.size()));
//            blocks[p.x][p.y].setColor(0);
//            blocks[p.x][p.y].changeable = true;
//            remainCount++;
//        }
//        Log.i("GameView", "end");
//    }

    private void generatePuzzle(int difficulty) {
        String[] strings;
        if (difficulty == 0) {
            ((GameActivity) getContext()).loadGame();
        } else {
            switch (difficulty) {
                case 1:
                    strings = getResources().getStringArray(R.array.easy);
                    break;
                case 2:
                    strings = getResources().getStringArray(R.array.normal);
                    break;
                case 3:
                    strings = getResources().getStringArray(R.array.hard);
                    break;
                case 4:
                    strings = getResources().getStringArray(R.array.fiendish);
                    break;
                default:
                    strings = getResources().getStringArray(R.array.easy);
                    break;
            }
            String puzzleString = strings[(int) (Math.random() * strings.length)];
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    dokuMatrix[i][j] = puzzleString.charAt(i * 9 + j) - 48;
                    if (dokuMatrix[i][j] == 0) {
                        blocks[i][j].setChangeable(true);
                        remainCount++;
                    } else {
                        blocks[i][j].setChangeable(false);
                    }
                    blocks[i][j].setColor(dokuMatrix[i][j]);
                }
            }

        }

    }

    @Override
    public void onClick(View v) {
        final Block block = (Block) v;
        int selectedColor = ((GameActivity) getContext()).currentSelectedColor;
        final int blockColor = block.getColor();
        if (block.changeable) {
            final Block lastSelectedBlock = ((GameActivity) getContext()).lastSelectedBlock;
            final Block currentSelectedBlock = ((GameActivity) getContext()).currentSelectedBlock;

            FrameLayout.LayoutParams lpCurrent = new FrameLayout.LayoutParams(WIDTH, HEIGHT);
            lpCurrent.setMargins(block.getLeft() + offest, block.getTop() + offest, 0, 0);
            currentSelectedBlock.setLayoutParams(lpCurrent);
            currentSelectedBlock.setColor(blockColor);
            if (!block.selected) {

                if (lastPoint != null) {
                    FrameLayout.LayoutParams lpLast = new FrameLayout.LayoutParams(WIDTH, HEIGHT);
                    lpLast.setMargins(currentSelectedBlock.getLeft(), currentSelectedBlock.getTop(), 0, 0);
                    lastSelectedBlock.setLayoutParams(lpLast);
                    int lastColor = blocks[lastPoint.x][lastPoint.y].getColor();
                    lastSelectedBlock.setColor(lastColor);

                    generateAnimation(lastSelectedBlock, false);
                    blocks[lastPoint.x][lastPoint.y].selected = false;
                }
                generateAnimation(currentSelectedBlock, true);
                lastPoint = new Point(block.row, block.col);
                block.selected = true;

            } else {
                generateAnimation(currentSelectedBlock, false);
                lastPoint = null;
                block.selected = false;
            }

        }
    }

    public void generateAnimation(final Block block, final boolean zoomType) {
        float scaleFrom;
        float scaleTo;
        if (zoomType) {
            scaleFrom = 1.0f;
            scaleTo = 1.5f;
        } else {
            scaleFrom = 1.5f;
            scaleTo = 1.0f;
        }
        ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(block, "scaleX", scaleFrom, scaleTo).setDuration(100);
        ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(block, "scaleY", scaleFrom, scaleTo).setDuration(100);
        AnimatorSet animatorSetScale = new AnimatorSet();
        animatorSetScale.play(animatorScaleX).with(animatorScaleY);
        animatorSetScale.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                block.setVisibility(VISIBLE);
                ViewCompat.setElevation(block, 8);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (!zoomType) {
                    block.setVisibility(INVISIBLE);
                    ViewCompat.setElevation(block, 4);
                }
            }
        });
        animatorSetScale.start();
    }


    public void isGameOver() {
        if (remainCount <= 0) {
            for (int i = 0; i <= 8; i++) {
                for (int j = 0; j <= 7; j++) {
                    for (int k = 1; k <= 8 - j; k++) {

                        //检查行是否满足要求
                        if (blocks[i][j].getColor() == blocks[i][j + k].getColor()) {
                            Log.i(TAG, "row: " + i + ":" + j + "--" + k);
                            return;
                        }

                        //检查列是否满足要求
                        if (blocks[j][i].getColor() == blocks[j + k][i].getColor()) {
                            Log.i(TAG, "col " + i + ":" + j + "--" + k);
                            return;
                        }

                        //检查九宫格是否满足要求
                        if (blocks[i / 3 * 3 + j / 3][i % 3 * 3 + j % 3].getColor() == blocks[i / 3 * 3 + (j + k) / 3][i % 3 * 3 + (j + k) % 3].getColor()) {
                            Log.i(TAG, "gong: " + i + ":" + j + "--" + k);
                            return;
                        }
                    }
                }
            }

            updateLeaderboard();
            showWinDialog();
        }
    }

    private void updateLeaderboard() {
        Chronometer chronometer = ((GameActivity) getContext()).chronometer;
        chronometer.stop();
        long usedTime = SystemClock.elapsedRealtime() - chronometer.getBase();
        SharedPreferences sp = ((GameActivity) getContext()).getSharedPreferences("leader board", Context.MODE_PRIVATE);
        String bestTimeKey, winsKey;
        switch (difficulty) {
            case 1:
                bestTimeKey = "best time easy";
                winsKey = "wins easy";
                break;
            case 2:
                bestTimeKey = "best time normal";
                winsKey = "wins normal";
                break;
            case 3:
                bestTimeKey = "best time hard";
                winsKey = "wins hard";
                break;
            case 4:
                bestTimeKey = "best time fiendish";
                winsKey = "wins fiendish";
                break;
            default:
                bestTimeKey = "best time easy";
                winsKey = "wins easy";
                break;
        }
        long bestTime = sp.getLong(bestTimeKey, (59 * 60 + 59) * 1000);
        int wins = sp.getInt(winsKey, 0);
        if (usedTime < bestTime) {
            SharedPreferences.Editor ed = sp.edit();
            ed.putLong(bestTimeKey, usedTime);
            ed.putInt(winsKey, wins + 1);
            ed.apply();
        }
    }

    private void showWinDialog() {
        new AlertDialog.Builder(getContext())
                .setMessage("恭喜你完成难度为" + difficulty + "的数独！你的成绩为" + ((GameActivity) getContext()).chronometer.getText())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((GameActivity) getContext()).finish();
                    }
                })
                .show();
    }

    public void saveGameView() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                editor.putInt("color" + (i * 9 + j), blocks[i][j].getColor());
                editor.putBoolean("changeable" + (i * 9 + j), blocks[i][j].changeable);
                editor.putInt("Visibility" + (i * 9 + j), blocks[i][j].errorImage.getVisibility());
            }
        }
        editor.putInt("difficulty", difficulty);
        editor.putInt("remainCount", remainCount);
        editor.apply();
    }

    public void loadGameView() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                blocks[i][j].setColor(sharedPreferences.getInt("color" + (i * 9 + j), 0));
                blocks[i][j].setChangeable(sharedPreferences.getBoolean("changeable" + (i * 9 + j), false));
                if (sharedPreferences.getInt("Visibility" + (i * 9 + j), VISIBLE) == VISIBLE) {
                    blocks[i][j].errorImage.setVisibility(VISIBLE);
                }
            }
        }
        difficulty = sharedPreferences.getInt("difficulty", 0);
        remainCount = sharedPreferences.getInt("remainCount", 0);
    }
}
