package com.shenjing.colordoku;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by shenjing on 2016/11/14.
 */

public class Block extends FrameLayout {
    public final DisplayMetrics dm = getResources().getDisplayMetrics();
    public int densityDpi = dm.densityDpi;
    public final int WIDTH = dm.widthPixels / 11;
    public final int HEIGHT = WIDTH;
    private int color = GREY;

    private GradientDrawable gradientDrawable;
    private Background stroke;
    public Background errorImage;
    public int row;
    public int col;
    //    public int uncertainty = 1;
    public int degree = -1;
    public boolean selected = false;
    public boolean changeable = false;
    private static final int GREY = 0;
    private static final int RED = 1;
    private static final int ORANGE = 2;
    private static final int YELLOW = 3;
    private static final int LIME = 4;
    private static final int GREEN = 5;
    private static final int TEAL = 6;
    private static final int BLUE = 7;
    private static final int PURPLE = 8;
    private static final int PINK = 9;

    public Block(Context context) {
        super(context);
        init();
    }

    public Block(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        stroke = new Background(getContext());
        addView(stroke, WIDTH, HEIGHT);
        errorImage = new Background(getContext());
        errorImage.setBackgroundResource(R.drawable.error);
        errorImage.setVisibility(INVISIBLE);
        addView(errorImage, WIDTH, HEIGHT);

        setBackgroundResource(R.drawable.shape_block);
        gradientDrawable = (GradientDrawable) this.getBackground();
    }


    public void setColor(int color) {
        this.color = color;
        TypedValue typedValue = new TypedValue();
        switch (color) {
            case GREY:
                getContext().getTheme().resolveAttribute(R.attr.colorBlock0, typedValue, true);
//                this.setBackgroundColor(typedValue.data);
                gradientDrawable.setColor(typedValue.data);
//                g.setColor(ContextCompat.getColor(getContext(),R.color.colorWhite));
//                g.setColor(ContextCompat.getColor(getContext(),R.attr.colorBlock0));
                break;
            case RED:
                getContext().getTheme().resolveAttribute(R.attr.colorBlock1, typedValue, true);
//                this.setBackgroundColor(typedValue.data);
                gradientDrawable.setColor(typedValue.data);
//                g.setColor(ContextCompat.getColor(getContext(),R.color.colorRed));
                break;
            case ORANGE:
//                g.setColor(ContextCompat.getColor(getContext(),R.color.colorOrange));
                getContext().getTheme().resolveAttribute(R.attr.colorBlock2, typedValue, true);
//                this.setBackgroundColor(typedValue.data);
                gradientDrawable.setColor(typedValue.data);
                break;
            case YELLOW:
//                g.setColor(ContextCompat.getColor(getContext(),R.color.colorYellow));
                getContext().getTheme().resolveAttribute(R.attr.colorBlock3, typedValue, true);
//                this.setBackgroundColor(typedValue.data);
                gradientDrawable.setColor(typedValue.data);
                break;
            case LIME:
//                g.setColor(ContextCompat.getColor(getContext(),R.color.colorLime));
                getContext().getTheme().resolveAttribute(R.attr.colorBlock4, typedValue, true);
//                this.setBackgroundColor(typedValue.data);
                gradientDrawable.setColor(typedValue.data);
                break;
            case GREEN:
//                g.setColor(ContextCompat.getColor(getContext(),R.color.colorGreen));
                getContext().getTheme().resolveAttribute(R.attr.colorBlock5, typedValue, true);
//                this.setBackgroundColor(typedValue.data);
                gradientDrawable.setColor(typedValue.data);
                break;
            case TEAL:
//                g.setColor(ContextCompat.getColor(getContext(),R.color.colorTeal));
                getContext().getTheme().resolveAttribute(R.attr.colorBlock6, typedValue, true);
//                this.setBackgroundColor(typedValue.data);
                gradientDrawable.setColor(typedValue.data);
                break;
            case BLUE:
                getContext().getTheme().resolveAttribute(R.attr.colorBlock7, typedValue, true);
//                this.setBackgroundColor(typedValue.data);
                gradientDrawable.setColor(typedValue.data);
//                g.setColor(ContextCompat.getColor(getContext(),R.attr.colorBlock7));
                break;
            case PURPLE:
//                g.setColor(ContextCompat.getColor(getContext(),R.color.colorPurple));
                getContext().getTheme().resolveAttribute(R.attr.colorBlock8, typedValue, true);
//                this.setBackgroundColor(typedValue.data);
                gradientDrawable.setColor(typedValue.data);
                break;
            case PINK:
//                g.setColor(ContextCompat.getColor(getContext(),R.color.colorPink));
                getContext().getTheme().resolveAttribute(R.attr.colorBlock9, typedValue, true);
//                this.setBackgroundColor(typedValue.data);
                gradientDrawable.setColor(typedValue.data);
                break;
        }


    }


    public int getColor() {
        return color;
    }

    public void setChangeable(Boolean changeable) {
        this.changeable = changeable;
        if(!changeable){
            stroke.setStroke();
        }
    }


    class Background extends View {

        public Background(Context context) {
            super(context);
        }

        public Background(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public void setStroke() {
            this.setBackgroundResource(R.drawable.shapa_block_unchangeable);
        }
    }

}
