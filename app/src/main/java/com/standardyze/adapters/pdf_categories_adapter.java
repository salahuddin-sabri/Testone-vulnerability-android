package com.standardyze.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.standardyze.R;
import com.standardyze.customViews.CustomTextViewMedium;
import com.standardyze.models.CategoryModel;
import com.standardyze.models.pdfCategoryScores;
import com.standardyze.wrapper.categories_score;

import java.util.ArrayList;

public class pdf_categories_adapter extends RecyclerView.Adapter<pdf_categories_adapter.ViewHolder> {

  Context context;
  ArrayList<categories_score> categoryModelArrayList;

  public pdf_categories_adapter(Context context, ArrayList<categories_score> categoryModelArrayList) {
    this.context = context;
    this.categoryModelArrayList = categoryModelArrayList;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pdf_item_categories, parent, false); //Inflating the layout

    return new ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    categories_score pdfCategoryScores = categoryModelArrayList.get(position);
    holder.category_name.setText(pdfCategoryScores.getCategoryname());
    holder.category_score.setText(String.valueOf(pdfCategoryScores.getCategoryScore())+"%");
  }

  @Override
  public int getItemCount() {
    return categoryModelArrayList.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {


    CustomTextViewMedium category_name;
    CustomTextViewMedium category_score;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      category_name = (CustomTextViewMedium) itemView.findViewById(R.id.category_name);
      category_score = (CustomTextViewMedium) itemView.findViewById(R.id.category_score);
    }
  }
}