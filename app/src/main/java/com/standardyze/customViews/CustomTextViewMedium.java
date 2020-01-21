package com.standardyze.customViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.standardyze.App.App;

public class CustomTextViewMedium extends TextView {

    public CustomTextViewMedium(Context context) {
        super(context);
        init();
    }

    public CustomTextViewMedium(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomTextViewMedium(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public void init() {
        Typeface tf = Typeface.createFromAsset(this.getContext().getAssets(), "font/GothamMedium.ttf");
        setTypeface(tf);
    }
}