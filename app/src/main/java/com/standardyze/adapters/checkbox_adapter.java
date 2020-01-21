package com.standardyze.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.standardyze.R;
import com.standardyze.models.checkbox_items;
import com.standardyze.utils.DatabaseHelper;
import com.standardyze.wrapper.roles;

import java.util.ArrayList;

public class checkbox_adapter extends RecyclerView.Adapter<checkbox_adapter.viewHolder> {
    Context context;
    ArrayList<roles> rolesArrayList;
    DatabaseHelper databaseHelper;
    public checkbox_adapter(Context context, ArrayList<roles> arrayList) {
        this.context = context;
        this.rolesArrayList = arrayList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkbox_items, parent, false); //Inflating the layout

        return new checkbox_adapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        roles checkbox_items = rolesArrayList.get(position);
        holder.item.setText(checkbox_items.getName());
        databaseHelper  = new DatabaseHelper(context);
        if (checkbox_items.getIsChecked()==0){
            holder.item.setChecked(false);
            databaseHelper.updateRoles(0,checkbox_items.getId());
        }
        holder.item.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    checkbox_items.setIsChecked(1);


                    databaseHelper.updateRoles(1,checkbox_items.getId());
                }
                else {
                    databaseHelper.updateRoles(0,checkbox_items.getId());
                    checkbox_items.setIsChecked(0);

                }
            }
        });
       /* if (checkbox_items.isChecked()){


        }
        else {
            databaseHelper.updateRoles(0,checkbox_items.getId());
            checkbox_items.setChecked(false);

        }*/



    }

    @Override
    public int getItemCount() {
        return rolesArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        CheckBox item;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            item = (CheckBox) itemView.findViewById(R.id.checkbox1);

        }
    }
}
