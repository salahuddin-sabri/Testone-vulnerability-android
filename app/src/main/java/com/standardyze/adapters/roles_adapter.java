package com.standardyze.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.standardyze.R;
import com.standardyze.customViews.CustomTextViewMedium;
import com.standardyze.wrapper.roles;

import java.util.ArrayList;

public class roles_adapter extends RecyclerView.Adapter<roles_adapter.viewHolder> {
    ArrayList<roles> items;
    Context context;

    public roles_adapter(ArrayList<roles> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_of_roles, parent, false); //Inflating the layout

        return new roles_adapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        roles checkbox_items = items.get(position);

        holder.role_name.setText(checkbox_items.getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        CustomTextViewMedium role_name;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            role_name = (CustomTextViewMedium) itemView.findViewById(R.id.role_name);
        }
    }
}
