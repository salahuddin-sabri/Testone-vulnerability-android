package com.standardyze.adapters.spinnersAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.standardyze.R;
import com.standardyze.customViews.CustomTextViewBook;
import com.standardyze.wrapper.Employee;
import com.standardyze.wrapper.departmentModel;

import java.util.ArrayList;

public class trainers_adapter extends BaseAdapter {
  Context context;
  private ArrayList<Employee> employeeArrayList;

  public trainers_adapter(Context context, ArrayList<Employee> spinner_list) {
    this.context = context;
    this.employeeArrayList = spinner_list;
  }

  @Override
  public int getCount() {
    return employeeArrayList.size();
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
    Employee employee = employeeArrayList.get(position);
    CustomTextViewBook names = (CustomTextViewBook) convertView.findViewById(R.id.spinner_txt);

    names.setText(employee.getFname() + " " + employee.getLname());


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
