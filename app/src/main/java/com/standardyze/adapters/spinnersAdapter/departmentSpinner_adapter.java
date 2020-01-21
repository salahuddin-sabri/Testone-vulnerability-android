package com.standardyze.adapters.spinnersAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.standardyze.R;
import com.standardyze.customViews.CustomTextViewBook;
import com.standardyze.wrapper.departmentModel;
import com.standardyze.wrapper.propertyModel;

import java.util.ArrayList;

public class departmentSpinner_adapter extends BaseAdapter {
  Context context;
  private ArrayList<departmentModel> departmentModelArrayList;

  public departmentSpinner_adapter(Context context, ArrayList<departmentModel> spinner_list) {
    this.context = context;
    this.departmentModelArrayList = spinner_list;
  }

  @Override
  public int getCount() {
    return departmentModelArrayList.size();
  }

  @Override
  public Object getItem(int position) {
    return null;
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  @SuppressLint("ViewHolder")
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    convertView = LayoutInflater.from(context).inflate(R.layout.spinner_layout, null);
    departmentModel departmentModel = departmentModelArrayList.get(position);
    CustomTextViewBook names = (CustomTextViewBook) convertView.findViewById(R.id.spinner_txt);

    names.setText(departmentModel.getName());


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
