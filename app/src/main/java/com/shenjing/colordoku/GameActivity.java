package com.shenjing.colordoku;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private final float scaleFrom =1.0f;
    private final float scaleTo =1.3f;
    public int currentSelected = -1;
    private Block[] blocks = new Block[10];
    private int[] blockId = {
            R.id.block_grey,
            R.id.block_red,
            R.id.block_orange,
            R.id.block_yellow,
            R.id.block_lime,
            R.id.block_green,
            R.id.block_teal,
            R.id.block_blue,
            R.id.block_purple,
            R.id.block_pink
    };
    private GameView gameView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameView = (GameView) findViewById(R.id.view_game);
        for (int i = 0; i < 10; i++) {
            blocks[i] = (Block) findViewById(blockId[i]);
            blocks[i].setColor(i);
            blocks[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        Log.i("GameView","Click!");

        final Block b = (Block)view;
        int currentSelected=b.getColor();

//        for (int i = 0; i < 10; i++) {
//            if(blockId[i] == view.getId()){
//                currentSelected = i;
//                break;
//            }
//        }
        if(this.currentSelected == -1){
            AnimatorSet animatorSet = new AnimatorSet();
            ObjectAnimator zoomOutX = ObjectAnimator.ofFloat(b,"scaleX",1.0f,1.3f).setDuration(50);
            ObjectAnimator zoomOutY = ObjectAnimator.ofFloat(b,"scaleY",1.0f,1.3f).setDuration(50);
            animatorSet.play(zoomOutX).with(zoomOutY);
            animatorSet.start();
            this.currentSelected = currentSelected;
        } else {
            AnimatorSet animatorSet = new AnimatorSet();
            ObjectAnimator zoomInX = ObjectAnimator.ofFloat(blocks[this.currentSelected],"scaleX",1.3f,1.0f).setDuration(50);
            ObjectAnimator zoomInY = ObjectAnimator.ofFloat(blocks[this.currentSelected],"scaleY",1.3f,1.0f).setDuration(50);
            animatorSet.play(zoomInX).with(zoomInY);
            animatorSet.start();
            if(this.currentSelected != currentSelected){
                AnimatorSet animatorSetCurrent = new AnimatorSet();
                ObjectAnimator zoomOutX = ObjectAnimator.ofFloat(b,"scaleX",1.0f,1.3f).setDuration(50);
                ObjectAnimator zoomOutY = ObjectAnimator.ofFloat(b,"scaleY",1.0f,1.3f).setDuration(50);
                animatorSetCurrent.play(zoomOutX).with(zoomOutY);
                animatorSetCurrent.start();
                this.currentSelected = currentSelected;
            } else {
                this.currentSelected = -1;
            }
        }

    }
}
