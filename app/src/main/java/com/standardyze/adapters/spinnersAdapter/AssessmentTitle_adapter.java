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
import com.standardyze.wrapper.departmentModel;

import java.util.ArrayList;

public class AssessmentTitle_adapter extends BaseAdapter {
  Context context;
  private ArrayList<AssmessmentModel> assmessmentModelArrayList;

  public AssessmentTitle_adapter(Context context, ArrayList<AssmessmentModel> spinner_list) {
    this.context = context;
    this.assmessmentModelArrayList = spinner_list;
  }

  @Override
  public int getCount() {
    return assmessmentModelArrayList.size();
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
    AssmessmentModel assmessmentModel = assmessmentModelArrayList.get(position);
    CustomTextViewBook names = (CustomTextViewBook) convertView.findViewById(R.id.spinner_txt);

    names.setText(assmessmentModel.getTitle());


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
