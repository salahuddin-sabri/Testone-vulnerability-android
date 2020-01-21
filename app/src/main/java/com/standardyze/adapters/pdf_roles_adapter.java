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
import com.standardyze.customViews.CustomTextViewMedium;
import com.standardyze.wrapper.Employee;
import com.standardyze.wrapper.roles;

import java.util.ArrayList;

public class pdf_roles_adapter extends RecyclerView.Adapter<pdf_roles_adapter.viewHolder> {
    private ArrayList<roles> employeeArrayList;
    Context context;

    public pdf_roles_adapter(ArrayList<roles> employeeArrayList, Context context) {
        this.employeeArrayList = employeeArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public pdf_roles_adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_of_roles, parent, false); //Inflating the layout

        return new pdf_roles_adapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull pdf_roles_adapter.viewHolder holder, int position) {
        roles   pdf_roles = employeeArrayList.get(position);
        holder.customTextViewBook.setText(pdf_roles.getName());

    }

    @Override
    public int getItemCount() {
        return employeeArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        CustomTextViewMedium customTextViewBook;
        CardView emp_cardview;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            customTextViewBook = (CustomTextViewMedium) itemView.findViewById(R.id.role_name);


        }
    }
}

