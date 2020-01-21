package com.standardyze.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.standardyze.R;
import com.standardyze.customViews.CustomTextViewBook;
import com.standardyze.customViews.CustomTextViewMedium;
import com.standardyze.utils.AppConstants;
import com.standardyze.utils.SharedPrefManager;
import com.standardyze.wrapper.getAssessmentResponseObj;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.victor.loading.rotate.RotateLoading;

import java.net.URL;
import java.util.ArrayList;

public class submitted_adapter extends RecyclerView.Adapter<submitted_adapter.ViewHolder> {

    Context context;
    ArrayList<getAssessmentResponseObj> arrayList;
    private submitted_adapter.OnItemClickListener onItemClickListener;
    SharedPrefManager sharedPrefManager;
    AmazonS3Client s3Client;
    URL presignedUrl;
    public submitted_adapter(Context context, ArrayList<getAssessmentResponseObj> arrayList, submitted_adapter.OnItemClickListener onItemClickListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.onItemClickListener = onItemClickListener;
        notifyDataSetChanged();
        sharedPrefManager = new SharedPrefManager(context);
    }


    @NonNull
    @Override
    public submitted_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.arrival_service_items, parent, false); //Inflating the layout

        return new submitted_adapter.ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        getAssessmentResponseObj questionsResult = arrayList.get(holder.getAdapterPosition());
        holder.setIsRecyclable(false);
        holder.questions.setText(questionsResult.getQuestionText());
        int s_no = position + 1;
        switch (questionsResult.getSelection()){
            case "YES":
                holder.yes_img.setImageResource(R.drawable.yes_enabled);
                holder.yes_img.setAlpha(0.5f);
                break;
            case "NO":
                holder.no_img.setImageResource(R.drawable.no_enabled);
                holder.no_img.setAlpha(0.5f);
                break;
            case "NONE":
                holder.none_img.setImageResource(R.drawable.none_enabled);
                holder.none_img.setAlpha(0.5f);
                break;
        }
        if (!questionsResult.getComment().isEmpty()){
            holder.comment_img.setImageResource(R.drawable.comment_enabled);
            holder.comment_box.setText(questionsResult.getComment());
        }
        else if (questionsResult.getComment().equalsIgnoreCase(" ")){
            holder.comment_img.setImageResource(R.drawable.comment_ic);
            holder.comment_box.setClickable(false);
        }
        if (!questionsResult.getImgId().isEmpty()){
            holder.image.setImageResource(R.drawable.image_enabled);

        }
        else {
            holder.image.setImageResource(R.drawable.img_ic);
        }

        if (questionsResult.getSelection().equalsIgnoreCase(" ")){
            holder.none_img.setImageResource(R.drawable.none_enabled);
            holder.none_img.setAlpha(0.5f);

        }





        holder.serial_num.setText(String.valueOf(s_no));

     /*   holder.yes_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!v.isSelected()) {
                    holder.yes_img.setImageResource(R.drawable.yes_enabled);
                    questionsResult.setSelection("YES");
                    questionsResult.setCount(1);
                    holder.no_img.setImageResource(R.drawable.no_ic);
                    holder.none_img.setImageResource(R.drawable.none_ic);
                   // onItemClickListener.onItemClicked(position,questionsResult.getResponseTblId(), questionsResult.getQuestionText(), "YES", questionsResult.getComment(),questionsResult.getImgId(),questionsResult.getCount());

                }

            }
        });

        holder.no_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!v.isSelected()) {

                    holder.yes_img.setImageResource(R.drawable.yes_ic);
                    holder.no_img.setImageResource(R.drawable.no_enabled);
                    questionsResult.setSelection("NO");
                    questionsResult.setCount(1);
                    holder.none_img.setImageResource(R.drawable.none_ic);

                   // onItemClickListener.onItemClicked(holder.getAdapterPosition(),questionsResult.getResponseTblId(), questionsResult.getQuestionText(), "NO", questionsResult.getComment(), questionsResult.getImgId(),questionsResult.getCount());



                }


            }
        });


        holder.none_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!v.isSelected()) {

                    holder.none_img.setImageResource(R.drawable.none_enabled);
                    questionsResult.setSelection("NONE");
                    questionsResult.setCount(1);
                    holder.yes_img.setImageResource(R.drawable.yes_ic);
                    holder.no_img.setImageResource(R.drawable.no_ic);
                  //  onItemClickListener.onItemClicked(holder.getAdapterPosition(),questionsResult.getResponseTblId(), questionsResult.getQuestionText(), "NONE", questionsResult.getComment(), questionsResult.getImgId(),questionsResult.getCount());


                }



            }
        });*/
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!v.isSelected()){
                    if (!arrayList.get(position).getImgId().equalsIgnoreCase("")){
                        onItemClickListener.onItemClicked(holder.getAdapterPosition(),
                                questionsResult.getResponseTblId(),questionsResult.getQuestionText(),
                                questionsResult.getSelection(),questionsResult.getComment(),questionsResult.getImgId(),questionsResult.getImageURL());
                        showDialogAlert(questionsResult.getImageURL());

                    }
                    else {
                        holder.image.setImageResource(R.drawable.img_ic);
                        holder.image.setEnabled(false);
                    }

                  //  showDialogAlert(getImageUrl(questionsResult.getImgId()));

                  /*  onItemClickListener.onItemClicked(holder.getAdapterPosition(),questionsResult.getResponseTblId(), questionsResult.getQuestionText(), "NONE", questionsResult.getComment(),questionsResult.getImgId(),
                            questionsResult.getCount(),questionsResult.getImageURL());*/
           /*         CropImage.activity()
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .start((Activity) context);
                    holder.image.setImageResource(R.drawable.image_enabled);*/
                   // onItemClickListener.onItemClicked(holder.getAdapterPosition(),questionsResult.getResponseTblId(), questionsResult.getQuestionText(), "NONE", questionsResult.getComment(),questionsResult.getImgId(),questionsResult.getCount());

                }
                /*else {
                    holder.image.setImageResource(R.drawable.img_ic);
                }*/
            }
        });


if (questionsResult.getComment().equalsIgnoreCase("")){
    holder.comment_box.setClickable(false);
}else
        holder.comment_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.setSelected(!v.isSelected());
                if (v.isSelected()) {
                    holder.comment_img.setImageResource(R.drawable.comment_enabled);
                    holder.hiddenView.setVisibility(View.VISIBLE);
                    holder.comment_box.setText(questionsResult.getComment());
                    holder.comment_box.setEnabled(false);
                   /* holder.comment_box.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            Log.e("after", "" + s);
                            questionsResult.setComment(String.valueOf(s));
                        }
                    });*/


                } else {
                    holder.hiddenView.setVisibility(View.GONE);
                  //  holder.comment_img.setImageResource(R.drawable.comment_ic);
                }
            }
        });


    }


  /*  public String getImageUrl(String ImgKey) {
        AWSMobileClient.getInstance().initialize(context).execute();
        // KEY and SECRET are gotten when we create an IAM user above
        BasicAWSCredentials credentials = new BasicAWSCredentials(AppConstants.KEY, AppConstants.SECRET);
        s3Client = new AmazonS3Client(credentials);
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(AppConstants.BUCKET_NAME, ImgKey)
                        .withMethod(HttpMethod.GET);
        presignedUrl = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
        //  questionsResultArrayList.get(pos).setImageURL(String.valueOf(presignedUrl));
        System.out.println("Pre-Signed URL =" + presignedUrl);

        return presignedUrl.toString();
    }*/
    public void showDialogAlert(String img) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_submitted_layout);
        ImageView imageView = (ImageView) dialog.findViewById(R.id.imageView);
        dialog.setCancelable(false);
        RotateLoading rotateLoading1 = (RotateLoading) dialog.findViewById(R.id.rotateloadingLogin);
        //imageView.setImageResource(R.drawable.image_enabled);
      //
        rotateLoading1.start();
         Glide.with(context).load(img).listener(new RequestListener<Drawable>() {
             @Override
             public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                 rotateLoading1.stop();
                 return false;
             }

             @Override
             public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                 rotateLoading1.stop();
                 return false;
             }
         }).into(imageView);
        Button button = (Button) dialog.findViewById(R.id.ok_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    @Override
    public int getItemCount() {
        return arrayList.size();

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        CustomTextViewMedium serial_num;
        CustomTextViewBook questions;
        EditText comment_box;
        ImageView yes_img, no_img, comment_img, none_img, image;
        RelativeLayout hiddenView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            questions = (CustomTextViewBook) itemView.findViewById(R.id.questions);
            serial_num = (CustomTextViewMedium) itemView.findViewById(R.id.serial_number);
            yes_img = (ImageView) itemView.findViewById(R.id.yes_img);
            no_img = (ImageView) itemView.findViewById(R.id.no_img);
            comment_img = (ImageView) itemView.findViewById(R.id.comment_img);
            none_img = (ImageView) itemView.findViewById(R.id.none_img);
            image = (ImageView) itemView.findViewById(R.id.img);
            hiddenView = (RelativeLayout) itemView.findViewById(R.id.hidden_view);
            comment_box = (EditText) itemView.findViewById(R.id.commement_edtxt);

        }


    }

    public interface OnItemClickListener {
        void onItemClicked(int position, int responseTblId, String questionText, String selection, String comment, String imgId ,String ImgURl);
    }


}
