package com.standardyze.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.standardyze.R;
import com.standardyze.customViews.CustomTextViewMedium;
import com.standardyze.models.CategoryModel;
import com.standardyze.models.summary_score;
import com.standardyze.utils.DatabaseHelper;
import com.standardyze.wrapper.AssesmentResponse_Categories;
import com.standardyze.wrapper.Category;
import com.standardyze.wrapper.CategoryResults;
import com.standardyze.wrapper.categories_score;

import java.util.ArrayList;

public class summary_catScore_adapter extends RecyclerView.Adapter<summary_catScore_adapter.viewHolder> {
    Context context;
    ArrayList<categories_score> arrayList;


    public summary_catScore_adapter(Context context, ArrayList<categories_score> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.summary_items, parent, false); //Inflating the layout

        return new summary_catScore_adapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        categories_score category = arrayList.get(position);

        /*   String name= category.getCategoryname().substring(0, 1).toUpperCase() + category.getCategoryname().substring(1).toLowerCase();
           if (name!=null){
               holder.category_txt.setText(name);
           }
           else {*/
        String name= category.getCategoryname().substring(0, 1).toUpperCase() + category.getCategoryname().substring(1).toUpperCase();
               holder.category_txt.setText(name);
         //  }

        holder.category_score.setText(String.valueOf(category.getCategoryScore())+"%");

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        CustomTextViewMedium category_score, category_txt;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            category_score = (CustomTextViewMedium) itemView.findViewById(R.id.scores_text);
            category_txt = (CustomTextViewMedium) itemView.findViewById(R.id.assessment_cat_text);
        }
    }
}
