package com.standardyze.customViews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;

import android.util.AttributeSet;
import android.widget.TextView;

import com.standardyze.App.App;


public class CustomTextViewLight extends TextView {
    public CustomTextViewLight(Context context) {
        super(context);
        init();
    }

    public CustomTextViewLight(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomTextViewLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }



    public void init() {
        Typeface tf = Typeface.createFromAsset(this.getContext().getAssets(), "font/GothamLight.ttf");
        setTypeface(tf);
    }
}
