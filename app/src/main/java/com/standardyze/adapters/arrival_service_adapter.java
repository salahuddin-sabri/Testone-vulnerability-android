package com.standardyze.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.standardyze.R;
import com.standardyze.customViews.CustomTextViewBook;
import com.standardyze.customViews.CustomTextViewMedium;
import com.standardyze.utils.SharedPrefManager;
import com.standardyze.wrapper.QuestionsResult;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.victor.loading.rotate.RotateLoading;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class arrival_service_adapter extends RecyclerView.Adapter<arrival_service_adapter.ViewHolder> {

    Context context;
    ArrayList<QuestionsResult> arrayList;
    private OnItemClickListener onItemClickListener;
    int count;
    boolean isUploaded;
    SharedPrefManager sharedPrefManager;
    //Added here temporary ArrayList
    private ArrayList<String> mSelectedPosition;
    public arrival_service_adapter(Context context, ArrayList<QuestionsResult> arrayList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.onItemClickListener = onItemClickListener;
        sharedPrefManager = new SharedPrefManager(context);
        mSelectedPosition = new ArrayList<>();
        setHasStableIds(true);
        notifyDataSetChanged();
       // setHasStableIds(true);
    }


    @NonNull
    @Override
    public arrival_service_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.arrival_service_items, parent, false); //Inflating the layout

        return new arrival_service_adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull arrival_service_adapter.ViewHolder holder, int position) {
        QuestionsResult questionsResult = arrayList.get(position);
        holder.setIsRecyclable(true);
        holder.yes_layout.setTag(position);

        holder.questions.setText(questionsResult.getQuestion());
        int s_no = position + 1;


        holder.serial_num.setText(String.valueOf(s_no));

        holder.yes_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  v.setSelected(!v.isSelected());
                if (!v.isSelected()) {

                    holder.yes_img.setImageResource(R.drawable.yes_enabled);
                    questionsResult.setSelection("YES");
                    questionsResult.setCount(1);
                    questionsResult.setSelected(true);
                    holder.no_img.setImageResource(R.drawable.no_ic);
                    holder.none_img.setImageResource(R.drawable.none_ic);
                    onItemClickListener.onItemClicked(position, questionsResult.getId(), questionsResult.getQuestion(), "YES", questionsResult.getComment(), questionsResult.getImgId(), questionsResult.getCount(), questionsResult.getImgURL(),questionsResult.isSelected());

                } else {
                    questionsResult.setSelection(" ");
                    questionsResult.setSelected(false);
                    holder.yes_img.setImageResource(R.drawable.yes_ic);
                    onItemClickListener.onItemClicked(position, questionsResult.getId(), questionsResult.getQuestion(), " ", questionsResult.getComment(), questionsResult.getImgId(), questionsResult.getCount(), questionsResult.getImgURL(),questionsResult.isSelected());

                }

            }
        });

        holder.no_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  v.setSelected(!v.isSelected());
                if (!v.isSelected()) {

                    holder.yes_img.setImageResource(R.drawable.yes_ic);
                    holder.no_img.setImageResource(R.drawable.no_enabled);
                    questionsResult.setSelection("NO");
                    questionsResult.setCount(1);
                    questionsResult.setSelected(true);
                    holder.none_img.setImageResource(R.drawable.none_ic);

                    onItemClickListener.onItemClicked(holder.getAdapterPosition(), questionsResult.getId(), questionsResult.getQuestion(), "NO", questionsResult.getComment(), questionsResult.getImgId(), questionsResult.getCount(), questionsResult.getImgURL(),questionsResult.isSelected());


                } else if (!v.isSelected()) {
                    questionsResult.setSelection(" ");
                    questionsResult.setSelected(false);
                    holder.no_img.setImageResource(R.drawable.no_ic);
                    onItemClickListener.onItemClicked(position, questionsResult.getId(), questionsResult.getQuestion(), " ", questionsResult.getComment(), questionsResult.getImgId(), questionsResult.getCount(), questionsResult.getImgURL(),questionsResult.isSelected());

                }


            }
        });


        holder.none_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // v.setSelected(!v.isSelected());
                if (!v.isSelected()) {

                    holder.none_img.setImageResource(R.drawable.none_enabled);
                    questionsResult.setSelection("NONE");
                    questionsResult.setCount(1);
                    questionsResult.setSelected(true);
                    holder.yes_img.setImageResource(R.drawable.yes_ic);
                    holder.no_img.setImageResource(R.drawable.no_ic);
                    onItemClickListener.onItemClicked(holder.getAdapterPosition(), questionsResult.getId(), questionsResult.getQuestion(), "NONE", questionsResult.getComment(), questionsResult.getImgId(), questionsResult.getCount(), questionsResult.getImgURL(),questionsResult.isSelected());


                } else {
                    questionsResult.setSelection("");
                    questionsResult.setSelected(false);
                    holder.none_img.setImageResource(R.drawable.none_ic);
                    onItemClickListener.onItemClicked(position, questionsResult.getId(), questionsResult.getQuestion(), "", questionsResult.getComment(), questionsResult.getImgId(), questionsResult.getCount(), questionsResult.getImgURL(),questionsResult.isSelected());
                }
            }
        });


        holder.image_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  v.setSelected(!v.isSelected());
                if (v.isSelected()) {*/

                    if (questionsResult.getImgId() == null) {
                        sharedPrefManager.setBooleanForKey(false, "isUploaded");

                        CropImage.activity()
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .start((Activity) context);


                        onItemClickListener.onItemClicked(holder.getAdapterPosition(), questionsResult.getId(),
                                questionsResult.getQuestion(), "NONE", questionsResult.getComment(),
                                questionsResult.getImgId(), questionsResult.getCount(), questionsResult.getImgURL(),questionsResult.isSelected());

                            holder.image.setImageResource(R.drawable.image_enabled);



                    }
                /*    if (questionsResult.getImgURL()==null){
                        sharedPrefManager.setBooleanForKey(false, "isUploaded");

                        CropImage.activity()
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .start((Activity) context);
                        onItemClickListener.onItemClicked(holder.getAdapterPosition(), questionsResult.getId(),
                                questionsResult.getQuestion(), "NONE", questionsResult.getComment(),
                                questionsResult.getImgId(), questionsResult.getCount(), questionsResult.getImgURL());
                    }*/
                  else  if (questionsResult.getImgId() != null) {
                        if (!sharedPrefManager.getBooleanByKey("isUploaded")) {
                            //toast
                            Toast.makeText(context, "Image upload is in progress", Toast.LENGTH_SHORT).show();
                            holder.image.setImageResource(R.drawable.image_enabled);
                        } else {
                            holder.image.setImageResource(R.drawable.image_enabled);
                            showDialogAlert(questionsResult.getImgURL());
                        }

                    } else {
                        Log.e("eer", "eeee");
                    }

                } /*else {
                    holder.image.setImageResource(R.drawable.img_ic);
                    questionsResult.setImgId("");
                    onItemClickListener.onItemClicked(position, questionsResult.getId(), questionsResult.getQuestion(), "", questionsResult.getComment(), questionsResult.getImgId(), questionsResult.getCount(), questionsResult.getImgURL());


                }*/
           // }
        });
        holder.comment_layout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                if (v.isSelected()) {
                    InputMethodManager inputMethodManager =
                            (InputMethodManager) context.getSystemService(
                                    Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInput(InputMethodManager.RESULT_HIDDEN, 0);
                    //    holder.comment_img.setImageResource(R.drawable.comment_enabled);
                    holder.hiddenView.setVisibility(View.VISIBLE);

                    holder.comment_img.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (hasFocus) {
                                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                            }
                            else {
                                InputMethodManager inputMethodManager =
                                        (InputMethodManager) context.getSystemService(
                                                Activity.INPUT_METHOD_SERVICE);
                                inputMethodManager.toggleSoftInput(InputMethodManager.RESULT_HIDDEN, 0);

                            }

                        }
                    });
                    holder.comment_box.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            Log.e("before", "" + s);
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            Log.e("onTextChanged", "" + s);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            Log.e("after", "" + s);
                            //  if (s.toString().length()>0){
                            questionsResult.setComment(String.valueOf(s));
                            holder.comment_img.setImageResource(R.drawable.comment_enabled);


                            // }
                          /* else {
                               holder.comment_img.setImageResource(R.drawable.comment_ic);
                           }*/

                        }
                    });


                } else {

                    holder.hiddenView.setVisibility(View.GONE);
                    if ( holder.comment_box.getText().toString().isEmpty()){
                        holder.comment_img.setImageResource(R.drawable.comment_ic);

                    }
                    else {
                        holder.comment_img.setImageResource(R.drawable.comment_enabled);
                    }
                }
                // }


            }
        });
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
    public void showDialogAlert(String img) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_image_view_alert);
        ImageView imageView = (ImageView) dialog.findViewById(R.id.imageView);
        RotateLoading rotateLoading1 = (RotateLoading) dialog.findViewById(R.id.rotateloadingLogin);
        CustomTextViewMedium customTextViewMedium = (CustomTextViewMedium) dialog.findViewById(R.id.dummytxt);
        rotateLoading1.start();
        dialog.setCancelable(false);
        Glide.with(context).load(img).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                rotateLoading1.stop();
                customTextViewMedium.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                rotateLoading1.stop();
                customTextViewMedium.setVisibility(View.GONE);
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
        Button button1 = (Button) dialog.findViewById(R.id.change_btn);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start((Activity) context);
                dialog.dismiss();
                sharedPrefManager.setBooleanForKey(false, "isUploaded");

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CustomTextViewMedium serial_num;
        CustomTextViewBook questions;
        EditText comment_box;
        LinearLayout none_layout, yes_layout,no_layout,comment_layout,image_layout;
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
            none_layout = (LinearLayout) itemView.findViewById(R.id.none_layout);
            yes_layout = (LinearLayout) itemView.findViewById(R.id.yes_layout);
            no_layout = (LinearLayout) itemView.findViewById(R.id.no_layout);
            comment_layout = (LinearLayout) itemView.findViewById(R.id.comment_layout);
            image_layout = (LinearLayout) itemView.findViewById(R.id.image_layout);



        }


    }

    public interface OnItemClickListener {
        void onItemClicked(int position, int responseTblId, String questionText, String selection, String comment, String imgId, int totalSelection, String url,boolean isSelected);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}


