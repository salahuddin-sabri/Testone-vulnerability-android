package com.standardyze.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.bumptech.glide.Glide;
import com.standardyze.R;
import com.standardyze.customViews.CustomTextViewBook;
import com.standardyze.customViews.CustomTextViewLight;
import com.standardyze.customViews.CustomTextViewMedium;
import com.standardyze.utils.AppConstants;
import com.standardyze.wrapper.QuestionsResult;
import com.standardyze.wrapper.getAssessmentResponseObj;

import java.net.URL;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class pdf_newQuestions_adapter extends RecyclerView.Adapter<pdf_newQuestions_adapter.ViewHolder> {

    Context context;
    ArrayList<QuestionsResult> questionsResultArrayList;
    URL presignedUrl;
    AmazonS3Client s3Client;
    public pdf_newQuestions_adapter(Context context, ArrayList<QuestionsResult> questionsResultArrayList) {
        this.context = context;
        this.questionsResultArrayList = questionsResultArrayList;
        AWSMobileClient.getInstance().initialize(context).execute();
        BasicAWSCredentials credentials = new BasicAWSCredentials(AppConstants.KEY, AppConstants.SECRET);
        s3Client = new AmazonS3Client(credentials);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_responses, parent, false); //Inflating the layout

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuestionsResult questionsResult = questionsResultArrayList.get(position);

        if (questionsResult.getSelection().equalsIgnoreCase(" ")) {
            questionsResult.setSelection("N/A");
            holder.selection_txt.setText(questionsResult.getSelection());
        }
        if (questionsResult.getImgId() == null) {
            holder.imageView.setVisibility(View.GONE);
        }
        if (questionsResult.getComment() == null) {
            holder.comment.setVisibility(View.GONE);
        }
        if (questionsResult.getSelection().equalsIgnoreCase("YES") || questionsResult.getSelection().equalsIgnoreCase("NO")
                || questionsResult.getSelection().equalsIgnoreCase("NONE")) {
            holder.selection_txt.setText(questionsResult.getSelection());
        } else if (questionsResult.getSelection().equalsIgnoreCase("N/A")) {
            holder.cardView.setVisibility(View.GONE);
        }
        holder.question_txt.setText(questionsResult.getQuestion());
        Glide.with(context).load(getImageUrl(questionsResult.getImgId())).into(holder.imageView);

        holder.getComment.setText(questionsResult.getComment());
        //holder.imageView.setImageResource(R.drawable.ic_launcher_background);
    }
    public String getImageUrl(String ImgKey) {

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(AppConstants.BUCKET_NAME, ImgKey)
                        .withMethod(HttpMethod.GET);
        Log.e("expire",""+generatePresignedUrlRequest.getExpiration());

        presignedUrl = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
        // questionsResultArrayList.get(pos).setImageURL(String.valueOf(presignedUrl));
        System.out.println("Pre-Signed URL =" + presignedUrl);
        return presignedUrl.toString();
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
        CardView cardView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            question_txt = (CustomTextViewBook) itemView.findViewById(R.id.question_txt);
            selection_txt = (CustomTextViewBook) itemView.findViewById(R.id.selection_txt);
            comment = (CustomTextViewLight) itemView.findViewById(R.id.comment_txt);
            getComment = (CustomTextViewMedium) itemView.findViewById(R.id.comment_edtxt);
            imageView = (ImageView) itemView.findViewById(R.id.response_img);
            cardView = (CardView) itemView.findViewById(R.id.view);


        }
    }
}


