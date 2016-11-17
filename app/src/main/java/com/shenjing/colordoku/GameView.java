package com.shenjing.colordoku;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by shenjing on 2016/11/14.
 */

public class GameView extends GridLayout implements View.OnClickListener{
    public final DisplayMetrics dm = getResources().getDisplayMetrics();
    public final int WIDTH = dm.widthPixels / 11;
    public final int HEIGHT = WIDTH;
    private int difficulty = 1;
    private int remainCount = 81;
    public ArrayList<Point> points = new ArrayList<>();
    public Block[][] blocks = new Block[9][9];
    public int[][] colorDoku = {
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
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                blocks[row][col] = new Block(getContext());
                blocks[row][col].row = row;
                blocks[row][col].col = col;
                uncertainty[row][col] = 1;
                addView(blocks[row][col], WIDTH, HEIGHT);
                LayoutParams lp = new LayoutParams();
                setMargin(lp, row, col);
                blocks[row][col].setLayoutParams(lp);
                blocks[row][col].setColor(colorDoku[row][col]);
                blocks[row][col].setOnClickListener(this);
                points.add(new Point(row, col));
            }
        }
        generatePuzzleEasy();
    }

    private void setMargin(LayoutParams lp, int row, int col) {
        lp.width = WIDTH;
        lp.height = HEIGHT;
        if (row == 2 || row == 5) {
            lp.bottomMargin = 10;
        } else {
            lp.bottomMargin = 5;
        }
        if (row == 3 || row == 6) {
            lp.topMargin = 10;
        } else {
            lp.topMargin = 5;
        }
        if (col == 2 || col == 5) {
            lp.rightMargin = 10;
        } else {
            lp.rightMargin = 5;
        }
        if (col == 3 || col == 6) {
            lp.leftMargin = 10;
        } else {
            lp.leftMargin = 5;
        }
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
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

    private void generatePuzzleEasy() {
        Log.i("GameView", "start");
        int num = 50;
        Point p;
        switch (difficulty) {
            case 1:
                num = (int) (Math.random() * 5 + 33);
                break;
            case 2:
                num = (int) (Math.random() * 5 + 28);
                break;
            case 3:
                num = (int) (Math.random() * 5 + 23);
                break;
        }
        for (; remainCount > num; remainCount--) {
            Log.i("GameView", "" + points.size());
            p = points.remove((int) (Math.random() * points.size()));
            blocks[p.x][p.y].setColor(0);
            blocks[p.x][p.y].changeable = true;
        }
        Log.i("GameView", "end");
    }

    @Override
    public void onClick(View v) {
        Block block = (Block) v;
        int color = ((GameActivity) getContext()).currentSelected;
        int currentColor = block.getColor();
        if (color != -1 && block.changeable) {
            if(currentColor == 0 && color != 0){
                remainCount--;
            }
            if(currentColor != 0 && color == 0){
                remainCount++;
            }
            block.setColor(color);
            isGameOver();
        }
    }

    private void isGameOver() {
        if(remainCount<=0){
            for (int i = 0; i <= 8; i++) {
                for (int j = 0; j <= 7; j++) {
                    for (int k = 1; k <= 8-j; k++) {

                        //检查行是否满足要求
                        if(blocks[i][j].getColor() == blocks[i][j+k].getColor()){
                            return;
                        }

                        //检查列是否满足要求
                        if(blocks[j][i].getColor() == blocks[j+k][i].getColor()){
                            return;
                        }

                        //检查九宫格是否满足要求
                        if(blocks[i/3*3+j/3][i/3+j%3].getColor() == blocks[i/3*3+(j+k)/3][i/3+(j+k)%3].getColor()){
                            return;
                        }
                    }
                }
            }

            new AlertDialog.Builder(getContext())
                    .setMessage("23333")
                    .show();

        }
    }
}
