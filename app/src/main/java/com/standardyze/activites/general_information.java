package com.standardyze.activites;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.gson.Gson;
import com.standardyze.R;
import com.standardyze.adapters.spinnersAdapter.general_info_adapter;
import com.standardyze.baseActivity;
import com.standardyze.utils.SharedPrefManager;
import com.standardyze.wrapper.General_infromations;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class general_information extends baseActivity {

    @BindView(R.id.room_num_edtxt)
    EditText room_edtxt;

    @BindView(R.id.spinner)
    Spinner meal_spinner;
    @BindView(R.id.save_btn)
    Button save_btn;
    general_info_adapter general_info_adapter;
    String[] meal_period = {"Select Meal Period", "Breakfast", "Lunch", "Dinner"};
    int room_num;
    ArrayList<General_infromations> general_infromationsArrayList;
    ArrayList<General_infromations> list;
    String selectedMeal, submitted_selectedMeal,saved_selectedMeal;
    SharedPrefManager sharedPrefManager;

    General_infromations general_infromations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_information);
        ButterKnife.bind(this);
        meal_spinner.setFocusable(false);
        meal_spinner.setFocusableInTouchMode(false);
        meal_spinner.requestFocus();

        general_infromationsArrayList = new ArrayList<>();
        showActionBar("General Information", new Intent(this, arrival_service.class), false, false, false, false);
        sharedPrefManager = new SharedPrefManager(this);
        Typeface copperplateGothicLight = Typeface.createFromAsset(this.getAssets(), "font/GothamBold.ttf");
        save_btn.setTypeface(copperplateGothicLight);


        //Submitted Data
        if (sharedPrefManager.getStringByKey("SubmittedGI")!=null){
            sharedPrefManager.clearKey("room");
            sharedPrefManager.clearKey("selectedMeal");
            room_edtxt.setEnabled(false);
            meal_spinner.setEnabled(false);
            save_btn.setEnabled(false);
            meal_spinner.setAlpha(0.2f);
            save_btn.setAlpha(0.2f);
            if (sharedPrefManager.getStringByKey("SubmittedGI").equalsIgnoreCase("N/A")){
                room_edtxt.setText("");
                meal_spinner.setSelection(0);
            }else{
                general_infromations = new Gson().fromJson(sharedPrefManager.getStringByKey("SubmittedGI"), General_infromations.class);
                if (general_infromations.getRoom()!=0 || general_infromations.getRoom().equals("N/A")){
                    room_edtxt.setText(general_infromations.getRoom().toString());
                }

                submitted_selectedMeal = general_infromations.getMealPeriod() ;

            }
        }
       /* else {
            room_edtxt.setEnabled(false);
            meal_spinner.setEnabled(false);
            save_btn.setEnabled(false);
            meal_spinner.setAlpha(0.2f);
            save_btn.setAlpha(0.2f);
        }*/
       else   if (sharedPrefManager.getStringByKey("SavedGI")!=null){
            sharedPrefManager.clearKey("room");
            sharedPrefManager.clearKey("selectedMeal");
            if (sharedPrefManager.getStringByKey("SavedGI").equalsIgnoreCase("N/A")){
                room_edtxt.setText("");
                meal_spinner.setSelection(0);
            }
            else{
                general_infromations = new Gson().fromJson(sharedPrefManager.getStringByKey("SavedGI"), General_infromations.class);

                if (general_infromations.getRoom()!=0 &&general_infromations.getRoom()!=null
                ){
                    room_edtxt.setText(general_infromations.getRoom().toString());
                }
                else {
                    room_edtxt.setText("");
                }

                saved_selectedMeal = general_infromations.getMealPeriod() ;

            }
        }
        else  if (sharedPrefManager.getIntegerByKey("selectedMeal")!=0){
            saved_selectedMeal =null;

           // selectedMeal = String.valueOf(sharedPrefManager.getIntegerByKey("selectedMeal"));
          // meal_spinner.setSelection(sharedPrefManager.getIntegerByKey("selectedMeal"));
           selectedMeal =meal_period[sharedPrefManager.getIntegerByKey("selectedMeal")];
       }if (sharedPrefManager.getIntegerByKey("room") != 0) {
            room_edtxt.setText(String.valueOf(sharedPrefManager.getIntegerByKey("room")));
        //    selectedMeal = String.valueOf(sharedPrefManager.getIntegerByKey("selectedMeal"));
        } else {
          //  room_edtxt.setText("");
          //  meal_spinner.setSelection(0);

        }



        meal_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                room_edtxt.setElevation(0);
                //saved_selectedMeal=null;

                room_edtxt.setBackground(getResources().getDrawable(R.drawable.edttxt_line_bg));
                if ( (submitted_selectedMeal != null)) {
                    switch (submitted_selectedMeal) {
                        case "Breakfast":
                            submitted_selectedMeal = meal_period[1];
                            meal_spinner.setSelection(1);
                            meal_spinner.setEnabled(false);
                            meal_spinner.setAlpha(0.2f);
                            save_btn.setAlpha(0.2f);


                            break;
                        case "Lunch":
                            submitted_selectedMeal = meal_period[2];
                            meal_spinner.setSelection(2);
                            meal_spinner.setEnabled(false);
                            meal_spinner.setAlpha(0.2f);
                            save_btn.setAlpha(0.2f);


                            break;
                        case "Dinner":
                            submitted_selectedMeal = meal_period[3];
                            meal_spinner.setSelection(3);
                            meal_spinner.setEnabled(false);
                            meal_spinner.setAlpha(0.2f);
                            save_btn.setAlpha(0.2f);
                            break;
                    }
                    meal_spinner.setAlpha(0.9f);

                }

                if (saved_selectedMeal!=null){
                    switch (saved_selectedMeal) {
                        case "Breakfast":
                            saved_selectedMeal = meal_period[1];
                            meal_spinner.setSelection(1);
                           // selectedMeal =saved_selectedMeal;
                           // saved_selectedMeal=meal_period[position];
                            break;
                        case "Lunch":
                            saved_selectedMeal = meal_period[2];
                            meal_spinner.setSelection(2);
                          //  selectedMeal =saved_selectedMeal;

                            break;
                        case "Dinner":
                            saved_selectedMeal = meal_period[3];
                            meal_spinner.setSelection(3);
                           // selectedMeal =saved_selectedMeal;
                            break;

                    }

                  //  selectedMeal =null;
                    //saved_selectedMeal =selectedMeal;

                }if (selectedMeal!=null){
                 //   sharedPrefManager.clearKey("selectedMeal");
                   //  selectedMeal = meal_period[position];
                     switch (selectedMeal) {
                         case"Breakfast":
                              selectedMeal =  meal_period[1];
                             meal_spinner.setSelection(1);
                             meal_spinner.setAlpha(0.9f);
                             save_btn.setEnabled(true);
                             save_btn.setAlpha(0.9f);
                             break;
                         case "Lunch":
                             selectedMeal =  meal_period[2];
                             meal_spinner.setSelection(2);
                             meal_spinner.setAlpha(0.9f);


                             save_btn.setEnabled(true);
                             save_btn.setAlpha(0.9f);

                             break;
                         case "Dinner":

                             selectedMeal =  meal_period[3];
                             meal_spinner.setSelection(3);
                             meal_spinner.setAlpha(0.9f);
                             save_btn.setEnabled(true);
                             save_btn.setAlpha(0.9f);

                             break;

                     }

                    if (position>0){
                        selectedMeal =  meal_period[position];
                        meal_spinner.setSelection(position);
                        meal_spinner.setAlpha(0.9f);
                        save_btn.setEnabled(true);
                        save_btn.setAlpha(0.9f);
                    }



                 /*   if (position > 0)
                   selectedMeal = meal_period[position];
                    meal_spinner.setSelection(position);*/
                /*    room_edtxt.setElevation(0);
                    room_edtxt.setBackground(getResources().getDrawable(R.drawable.edttxt_line_bg));
                    // If user change the default selection
                    // First item is disable and it is used for hint
                    if (position > 0) {
                        meal_spinner.setAlpha(0.9f);

                        //  save_btn.setBackground(getResources().getDrawable(R.drawable.button_layout));
                        save_btn.setEnabled(true);
                        save_btn.setAlpha(0.9f);


                    } else {

                        meal_spinner.setSelection(0);
                        meal_spinner.setAlpha(0.2f);
                        save_btn.setAlpha(0.2f);

                    }*/
                }

                else {
                    sharedPrefManager.clearKey("selectedMeal");
                    saved_selectedMeal=null;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        room_edtxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    v.setBackground(getResources().getDrawable(R.drawable.editbox_drawable));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        v.setElevation(10f);
                    }
                } else {
                    v.setElevation(0);
                    v.setBackground(getResources().getDrawable(R.drawable.edttxt_line_bg));

                }
            }
        });

        general_info_adapter = new general_info_adapter(general_information.this, meal_period);
        meal_spinner.setAdapter(general_info_adapter);
        room_edtxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                enableSubmitIfReady();


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    public void enableSubmitIfReady() {

        boolean isReady = room_edtxt.getText().toString().length()!=-1;
        if (isReady ){
            save_btn.setEnabled(isReady);
           // save_btn.setBackground(getResources().getDrawable(R.drawable.button_layout));
            save_btn.setAlpha(0.9f);
        }
        else {
            save_btn.setEnabled(isReady);
          //  save_btn.setBackground(getResources().getDrawable(R.drawable.button_selector));
            save_btn.setAlpha(0.2f);
        }


    }
    @OnClick(R.id.save_btn)
    public void setSave_btn() {
        sharedPrefManager.clearKey("GIData");
        sharedPrefManager.clearKey("selectedMeal");
        sharedPrefManager.clearKey("room");
     /*   if (room_edtxt.getText().toString().isEmpty()) {
            save_btn.setBackground(getResources().getDrawable(R.drawable.button_selector));
           // Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
            //   showtoastError();


        } else {*/
     //   sharedPrefManager.clearKey("GIData");
     if (room_edtxt.getText().toString().equalsIgnoreCase("")){
         sharedPrefManager.setIntegerForKey(room_num, "room");
         room_edtxt.setText("");
     }else {
         room_num = Integer.                                                                                                                                                                            parseInt(room_edtxt.getText().toString());
         sharedPrefManager.setIntegerForKey(room_num, "room");
         room_edtxt.setText(String.valueOf(room_num));
     }
     if (meal_spinner.getSelectedItemPosition()!=0){
         selectedMeal= meal_period[meal_spinner.getSelectedItemPosition()];
         sharedPrefManager.setIntegerForKey(meal_spinner.getSelectedItemPosition(), "selectedMeal");

     }/*else {
         meal_spinner.setSelection(-1);
     }*/



            Gson gson = new Gson();
           if (meal_spinner.getSelectedItemPosition()==0 ){
               general_infromationsArrayList.add(new General_infromations(room_num, "N/A"));
           }
           else {
               general_infromationsArrayList.add(new General_infromations(room_num, selectedMeal));
           }

            showDialogAlert(R.string.generalInfo);
            String list = gson.toJson(general_infromationsArrayList);
            sharedPrefManager.setStringForKey(list     ,"GIData");
            sharedPrefManager.clearKey("SavedGI");
            Intent intent = new Intent("com.tutorialspoint.CUSTOM_INTENT").putExtra("general_info_list", general_infromationsArrayList);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
           // finish();


        }
   // }

    public void showtoastError() {
        // Get the custom layout view.
        View toastView = getLayoutInflater().inflate(R.layout.cutom_toast_view, null);

        // Initiate the Toast instance.
        Toast toast = new Toast(getApplicationContext());
        // Set custom view in toast.
        toast.setView(toastView);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0, 350);
        toast.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
