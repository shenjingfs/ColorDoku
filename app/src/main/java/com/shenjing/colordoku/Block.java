package com.shenjing.colordoku;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by shenjing on 2016/11/14.
 */

public class Block extends FrameLayout {
    GradientDrawable g;
    public int row;
    public int col;
//    public int uncertainty = 1;
    public int degree = -1;
    public boolean selected = false;
    public boolean changeable = false;
    private static final int GREY   = 0;
    private static final int RED    = 1;
    private static final int ORANGE = 2;
    private static final int YELLOW = 3;
    private static final int LIME   = 4;
    private static final int GREEN  = 5;
    private static final int TEAL   = 6;
    private static final int BLUE   = 7;
    private static final int PURPLE = 8;
    private static final int PINK   = 9;
    private int color = GREY ;

    public Block(Context context) {
        super(context);
        init();
    }

    public Block(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        setBackgroundResource(R.drawable.shape_block);
        g=(GradientDrawable) this.getBackground();
    }

    public void setColor(int color){
        this.color=color;
        switch (color){
            case GREY   :
                g.setColor(ContextCompat.getColor(getContext(),R.color.colorWhite));
                break;
            case RED    :
                g.setColor(ContextCompat.getColor(getContext(),R.color.colorRed));
                break;
            case ORANGE :
                g.setColor(ContextCompat.getColor(getContext(),R.color.colorOrange));
                break;
            case YELLOW :
                g.setColor(ContextCompat.getColor(getContext(),R.color.colorYellow));
                break;
            case LIME   :
                g.setColor(ContextCompat.getColor(getContext(),R.color.colorLime));
                break;
            case GREEN  :
                g.setColor(ContextCompat.getColor(getContext(),R.color.colorGreen));
                break;
            case TEAL   :
                g.setColor(ContextCompat.getColor(getContext(),R.color.colorTeal));
                break;
            case BLUE   :
                g.setColor(ContextCompat.getColor(getContext(),R.color.colorBlue));
                break;
            case PURPLE :
                g.setColor(ContextCompat.getColor(getContext(),R.color.colorPurple));
                break;
            case PINK   :
                g.setColor(ContextCompat.getColor(getContext(),R.color.colorPink));
                break;
        }
    }

    public int getColor(){
        return color;
    }

}
