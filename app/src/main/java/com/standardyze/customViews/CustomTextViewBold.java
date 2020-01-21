package com.standardyze.customViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.standardyze.App.App;


public class CustomTextViewBold extends TextView {
    public CustomTextViewBold(Context context) {
        super(context);
        init();
    }

    public CustomTextViewBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomTextViewBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }



    public void init() {
        Typeface tf = Typeface.createFromAsset(this.getContext().getAssets(), "font/GothamBold.ttf");
        setTypeface(tf);
    }
}
