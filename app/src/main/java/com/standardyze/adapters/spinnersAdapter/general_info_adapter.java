package com.standardyze.adapters.spinnersAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.standardyze.R;
import com.standardyze.customViews.CustomTextViewBook;
import com.standardyze.wrapper.AssmessmentModel;
import com.standardyze.wrapper.General_infromations;

import java.util.ArrayList;

public class general_info_adapter extends BaseAdapter {
    Context context;
    String [] mealPeriod;
    ArrayList<General_infromations>  general_infromationsArrayList;
    // private ArrayList<AssmessmentModel> assmessmentModelArrayList;

    public general_info_adapter(Context context, String[] spinner_list) {
        this.context = context;
        this.mealPeriod = spinner_list;
    }

    @Override
    public int getCount() {
        return mealPeriod.length;
    }

    @Override
    public Object getItem(int position) {
        return mealPeriod.length;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.spinner_layout, null);

        CustomTextViewBook names = (CustomTextViewBook) convertView.findViewById(R.id.spinner_txt);

        names.setText(mealPeriod[position]);


        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        if(position == 0)
        {
            // Disable the first item from Spinner
            // First item will be use for hint
            return false;
        }
        else
        {
            return true;
        }
    }
}
