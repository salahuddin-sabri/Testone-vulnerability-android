package com.standardyze.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.standardyze.R;
import com.standardyze.customViews.CustomTextViewBook;
import com.standardyze.customViews.CustomTextViewLight;
import com.standardyze.customViews.CustomTextViewMedium;
import com.standardyze.wrapper.QuestionsResult;
import com.standardyze.wrapper.getAssessmentResponseObj;

import java.util.ArrayList;

public class newResponseAdapter extends RecyclerView.Adapter<newResponseAdapter.ViewHolder> {

    Context context;
    private ArrayList<QuestionsResult> questionsResultArrayList;

    public newResponseAdapter(Context context, ArrayList<QuestionsResult> questionsResultArrayList) {
        this.context = context;
        this.questionsResultArrayList = questionsResultArrayList;
    }

    @NonNull
    @Override
    public newResponseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_responses, parent, false); //Inflating the layout

        return new newResponseAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull newResponseAdapter.ViewHolder holder, int position) {
        QuestionsResult questionsResult = questionsResultArrayList.get(position);

        if (questionsResult.getSelection().equalsIgnoreCase(" ")) {
            questionsResult.setSelection("N/A");
            holder.selection_txt.setText(questionsResult.getSelection());
        }
        if (questionsResult.getImgId().isEmpty()) {
            holder.imageView.setVisibility(View.GONE);
        }
        if (questionsResult.getComment().isEmpty()) {
            holder.comment.setVisibility(View.GONE);
        }
        holder.question_txt.setText(questionsResult.getQuestion());
        holder.selection_txt.setText(questionsResult.getSelection());
        holder.getComment.setText(questionsResult.getComment());
        holder.imageView.setImageResource(R.drawable.ic_launcher_background);
    }

    @Override
    public int getItemCount() {
        return questionsResultArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CustomTextViewBook question_txt;
        CustomTextViewBook selection_txt;
        CustomTextViewLight comment;
        CustomTextViewMedium getComment;
        ImageView imageView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            question_txt = (CustomTextViewBook) itemView.findViewById(R.id.question_txt);
            selection_txt = (CustomTextViewBook) itemView.findViewById(R.id.selection_txt);
            comment = (CustomTextViewLight) itemView.findViewById(R.id.comment_txt);
            getComment = (CustomTextViewMedium) itemView.findViewById(R.id.comment_edtxt);
            imageView = (ImageView) itemView.findViewById(R.id.response_img);


        }
    }
}
