package com.standardyze.adapters.spinnersAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.standardyze.R;
import com.standardyze.customViews.CustomTextViewBook;
import com.standardyze.utils.AppConstants;
import com.standardyze.wrapper.propertyModel;

import java.util.ArrayList;
import java.util.List;

public class propertySpinner_adapter extends BaseAdapter {

    Context context;
    private ArrayList<propertyModel> propertyModelArrayList;
    CustomTextViewBook names;
    public propertySpinner_adapter(Context context, ArrayList<propertyModel> spinner_list) {
        this.context = context;
        this.propertyModelArrayList = spinner_list;
    }

    @Override
    public int getCount() {
        return propertyModelArrayList.size() ;
    }

    @Override
    public Object getItem(int position) {
        return propertyModelArrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.spinner_layout, null);

        propertyModel propertyModel = propertyModelArrayList.get(position );

        names  = (CustomTextViewBook) convertView.findViewById(R.id.spinner_txt);

        names.setText(propertyModelArrayList.get(position).getName());

        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        if(position == 0)
        {
            // Disable the second item from Spinner
            return false;
        }
        else
        {
            return true;
        }
    }
}
