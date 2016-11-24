package com.shenjing.colordoku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.ScaleAnimation;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        difficulty = getIntent().getIntExtra("difficulty",0);
        setContentView(R.layout.activity_game);

        lastSelectedBlock = (Block) findViewById(R.id.block_last);
        currentSelectedBlock = (Block) findViewById(R.id.block_current);
        gameView = (GameView) findViewById(R.id.view_game);
        for (int i = 0; i < 10; i++) {
            blocks[i] = (Block) findViewById(blockId[i]);
            blocks[i].setColor(i);
            blocks[i].setOnClickListener(this);
        }
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
}
