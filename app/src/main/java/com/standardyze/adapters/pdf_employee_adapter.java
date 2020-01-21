package com.standardyze.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.standardyze.R;
import com.standardyze.customViews.CustomTextViewBook;
import com.standardyze.wrapper.Employee;

import java.util.ArrayList;

public class pdf_employee_adapter extends RecyclerView.Adapter<pdf_employee_adapter.viewHolder> {
    private ArrayList<Employee> employeeArrayList;
    Context context;

    public pdf_employee_adapter(ArrayList<Employee> employeeArrayList, Context context) {
        this.employeeArrayList = employeeArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_layout, parent, false); //Inflating the layout

        return new pdf_employee_adapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
      Employee  employee = employeeArrayList.get(position);
        String eName = employee.getFname()+" "+employee.getLname();

      if (employee.getFname() ==null && employee.getLname()== null){

          holder.customTextViewBook.setText("N/A");

      }
      else {
          holder.customTextViewBook.setText(eName);


      }


    }

    @Override
    public int getItemCount() {
        return employeeArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        CustomTextViewBook customTextViewBook;
        CardView  emp_cardview;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            customTextViewBook = (CustomTextViewBook) itemView.findViewById(R.id.tv_employee);
            emp_cardview = (CardView) itemView.findViewById(R.id.emp_cardview);

        }
    }
}
