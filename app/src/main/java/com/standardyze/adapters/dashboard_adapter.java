package com.standardyze.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.standardyze.R;
import com.standardyze.activites.savedData;
import com.standardyze.activites.submitted_data;
import com.standardyze.customViews.CustomTextViewBold;
import com.standardyze.customViews.CustomTextViewBook;
import com.standardyze.customViews.CustomTextViewMedium;
import com.standardyze.utils.AppConstants;
import com.standardyze.utils.ConnectionDetector;
import com.standardyze.utils.DatabaseHelper;
import com.standardyze.utils.SharedPrefManager;
import com.standardyze.wrapper.dashboardAssessmentData;
import com.victor.loading.rotate.RotateLoading;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class dashboard_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<dashboardAssessmentData> assessmentList;
    DatabaseHelper databaseHelper;
    SharedPrefManager sharedPrefManager;
    ConnectionDetector connectionDetector;
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;
    public dashboard_adapter(Context context, ArrayList<dashboardAssessmentData> assessmentList) {
        this.context = context;
        this.assessmentList = assessmentList;
        databaseHelper = new DatabaseHelper(context);
        sharedPrefManager = new SharedPrefManager(context);
        connectionDetector = new ConnectionDetector(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;


        //View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_items_layout, parent, false); //Inflating the layout
        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.dashboard_items_layout, parent, false);
                viewHolder = new ViewHolder(viewItem);

                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);

                break;
        }
        return viewHolder;

    }



    public static String parseDate(String inputDateString, SimpleDateFormat inputDateFormat, SimpleDateFormat outputDateFormat) {
        Date date = null;
        String outputDateString = null;
        try {
            date = inputDateFormat.parse(inputDateString);
            outputDateString = outputDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDateString;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        dashboardAssessmentData dashboard_items = assessmentList.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                ViewHolder holder = (ViewHolder) viewHolder;
                if (assessmentList.get(position)!=null){


                    holder.assessment_title.setText(dashboard_items.getAssessmentTitle());
                    holder.property_txt.setText(dashboard_items.getPropertyName());
                    holder.trainer_txt.setText("Trainer - " + dashboard_items.getTrainerName());
                    if (dashboard_items.getScore()!=null){
                        holder.score_txt.setText(dashboard_items.getScore().toString()+"%");
                    }

                    SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US);

                    SimpleDateFormat EEEddMMMyyyy = new SimpleDateFormat("d MMM yyyy", Locale.US);
                    String date = new SimpleDateFormat("d MMM yyyy  hh:mm:ss a", Locale.getDefault()).format(new Date());

                    holder.status_txt.setText(dashboard_items.getStatus());
                    if (dashboard_items.getDatetime()!=null){
                        holder.date_txt.setText(parseDate(dashboard_items.getDatetime(), ymdFormat, EEEddMMMyyyy));

                    }


                    holder.cardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (checkConnection()) {

                                /**
                                 * Saved TO SERVER
                                 */
                                if (dashboard_items.getStatus().equalsIgnoreCase(AppConstants.SAVED_TO_SERVER)) {

                                    Intent in = new Intent(context, savedData.class);
                                    sharedPrefManager.clearKey("new");
                                    sharedPrefManager.clearKey("submittedEmployees");
                                    in.putExtra("id", dashboard_items.getId());
                                    in.putExtra("status", dashboard_items.getStatus());
                                    in.putExtra("dateTime", parseDate(dashboard_items.getDatetime(), ymdFormat, EEEddMMMyyyy));
                                    in.putExtra("savedEmployees", dashboard_items.getEmployees());
                                    in.putExtra("generalInfo",dashboard_items.getGeneralInfromations());
                                    Log.e("GI",""+dashboard_items.getGeneralInfromations());
                                    sharedPrefManager.setStringForKey(dashboard_items.getTrainerName(),"savedTrainerName");
                                    sharedPrefManager.setStringForKey(dashboard_items.getEmployees(), "savedEmployees");
                                    sharedPrefManager.setIntegerForKey(dashboard_items.getAssessmentUniqueId(), "assessmentID");
                                    sharedPrefManager.setStringForKey(dashboard_items.getStatus(), "statusPending");
                                    sharedPrefManager.setStringForKey(dashboard_items.getDepartmentName(),"savedDepartmentName");


                                    for (int i =0;i<databaseHelper.selectEmployees().size();i++){
                                        if (databaseHelper.selectEmployees().get(i).isEmployeeAdded()!=0 &&
                                                databaseHelper.selectEmployees().get(i).isEmployeeAdded()!= dashboard_items.getAssessmentUniqueId()
                                        ){
                                            databaseHelper.updateCustomEmployee(databaseHelper.selectEmployees().get(i).isEmployeeAdded());
                                        }
                                    }
                                    context.startActivity(in);
                                    //  ((Activity)context).finish();

                                } else {
                                    sharedPrefManager.clearKey("savedEmployees");
                                    sharedPrefManager.clearKey("savedTrainerName");
                                    sharedPrefManager.clearKey("savedDepartmentName");
                                    Intent in = new Intent(context, submitted_data.class);
                                    sharedPrefManager.clearKey("new");
                                    in.putExtra("id", dashboard_items.getId());
                                    in.putExtra("status", dashboard_items.getStatus());
                                    in.putExtra("dateTime",parseDate(dashboard_items.getDatetime(), ymdFormat, EEEddMMMyyyy));
                                    in.putExtra("submitted", dashboard_items.getEmployees());
                                    in.putExtra("generalInfo",dashboard_items.getGeneralInfromations());
                                    sharedPrefManager.setStringForKey(dashboard_items.getEmployees(), "submittedEmployees");
                                    sharedPrefManager.setIntegerForKey(dashboard_items.getAssessmentUniqueId(), "assessmentID");
                                    sharedPrefManager.setStringForKey(dashboard_items.getStatus(), "statusSubmitted");
                                    sharedPrefManager.setStringForKey(dashboard_items.getTrainerName(),"submittedTrainerName");
                                    sharedPrefManager.setStringForKey(dashboard_items.getDepartmentName(),"submittedDepartmentName");

                                    context.startActivity(in);
                                    // ((Activity)context).finish();
                                }
                            }

                /*else { showDialogAlert(R.string.checkNetwork);
                    if (dashboard_items.getStatus().equalsIgnoreCase("DBSaved")) {
                        Intent in = new Intent(context, savedData.class);
                        in.putExtra("id", dashboard_items.getId());
                        in.putExtra("status", dashboard_items.getStatus());
                        in.putExtra("dateTime", dashboard_items.getDatetime());
                        in.putExtra("savedEmployees", dashboard_items.getEmployees());
                        sharedPrefManager.setIntegerForKey(dashboard_items.getAssessmentUniqueId(),"assessmentID");
                        sharedPrefManager.setStringForKey(dashboard_items.getEmployees(), "savedEmployees");
                        sharedPrefManager.setStringForKey(dashboard_items.getStatus(),"statusPending");
                        context.startActivity(in);
                        //  ((Activity)context).finish();

                    }

                }*/
                        }
                    });
                }

                break;
            case LOADING:
//                Do nothing
                LoadingVH loadingViewHolder = (LoadingVH) viewHolder;
                loadingViewHolder.progressBar.setVisibility(View.VISIBLE);
                loadingViewHolder.progressBar.start();
                break;

        }

    }
 /*   @NonNull
    private RecyclerView.ViewHolder  getViewHolder(ViewGroup parent, LayoutInflater inflater) {

        View v1 = inflater.inflate(R.layout.dashboard_items_layout, parent, false);

        return  new ViewHolder(v1);
    }*/



    public boolean checkConnection() {
        boolean flag = false;
        if (connectionDetector.isNetworkAvailable(context)) {
            flag = true;
        } else {
            flag = false;
        }
        return flag;

    }

    public void showDialogAlert(int error) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_alert_dialog);
        CustomTextViewBook errormsg = (CustomTextViewBook) dialog.findViewById(R.id.alertText);
        errormsg.setText(error);
        dialog.setCancelable(false);
        Button button = (Button) dialog.findViewById(R.id.ok_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        CustomTextViewMedium assessment_title;
        CustomTextViewBook property_txt;
        CustomTextViewBook trainer_txt;
        CustomTextViewBook status_txt;
        CustomTextViewBook date_txt;
        CustomTextViewBold score_txt;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardview);
            assessment_title = (CustomTextViewMedium) itemView.findViewById(R.id.assessment_title);
            property_txt = (CustomTextViewBook) itemView.findViewById(R.id.property_txt);
            trainer_txt = (CustomTextViewBook) itemView.findViewById(R.id.trainer_txt);
            status_txt = (CustomTextViewBook) itemView.findViewById(R.id.status_txt);
            date_txt = (CustomTextViewBook) itemView.findViewById(R.id.date_txt);
            score_txt = (CustomTextViewBold) itemView.findViewById(R.id.score_txt);

        }
    }
    public class LoadingVH extends RecyclerView.ViewHolder {
        private RotateLoading progressBar;
        public LoadingVH(View itemView) {
            super(itemView);
            progressBar = (RotateLoading) itemView.findViewById(R.id.loadmore_progress);
        }
    }




    @Override
    public int getItemCount() {
        return assessmentList == null ? 0 : assessmentList.size();
    }
    @Override
    public int getItemViewType(int position) {
        return (position == assessmentList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }
    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new dashboardAssessmentData());
    }
    public void removeLoadingFooter() {
        isLoadingAdded = false;
        int position = assessmentList.size() - 1;
        dashboardAssessmentData result = getItem(position);
        if (result != null) {
            assessmentList.remove(position);
            notifyItemRemoved(position);
        }
    }
    public void add(dashboardAssessmentData movie) {
        assessmentList.add(movie);
        notifyItemInserted(assessmentList.size() - 1);
    }
    public void addAll(List<dashboardAssessmentData> moveResults) {
        for (dashboardAssessmentData result : moveResults) {
            add(result);
        }
    }
    public dashboardAssessmentData getItem(int position) {
        return assessmentList.get(position);
    }



}
