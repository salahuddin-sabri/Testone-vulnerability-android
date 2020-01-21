package com.standardyze.activites;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.standardyze.R;
import com.standardyze.baseActivity;
import com.standardyze.customViews.CustomTextViewMedium;
import com.standardyze.networking.WebServiceFactory;
import com.standardyze.wrapper.forgotPasswordResponse;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class forgotPassword extends baseActivity {
    @BindView(R.id.edtxt_forgot)
    EditText email_edtxt;
    @BindView(R.id.recover_btn)
    Button recover_btn;
    @BindView(R.id.reg_txt)
    CustomTextViewMedium reg_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().hide();
        ButterKnife.bind(this);


        Typeface copperplateGothicLight = Typeface.createFromAsset(this.getAssets(), "font/GothamMedium.ttf");
        recover_btn.setTypeface(copperplateGothicLight);

        email_edtxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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


        reg_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(forgotPassword.this,login.class);
                startActivity(in);
                finish();
            }
        });
    }

    public void  forgotPasswordAPI(String email){
        WebServiceFactory.getInstance().setPassword(email).enqueue(new Callback<forgotPasswordResponse>() {
            @Override
            public void onResponse(Call<forgotPasswordResponse> call, Response<forgotPasswordResponse> response) {

                try {
                    JSONObject jsonObject  = new JSONObject(new Gson().toJson(response.body()));
                    forgotPasswordResponse json = new Gson().fromJson(jsonObject.toString(), forgotPasswordResponse.class);
                    if (json.getStatusCode()==1){
                        Toast.makeText(forgotPassword.this, ""+json.getMessage(), Toast.LENGTH_SHORT).show();
                        email_edtxt.setText("");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    showDialogAlert(R.string.email_not_exist);
                }

            }

            @Override
            public void onFailure(Call<forgotPasswordResponse> call, Throwable t) {

            }
        });

    }
    @OnClick(R.id.recover_btn)
    public void setRecover_btn() {
        String email = email_edtxt.getText().toString();



        if (!email.isEmpty()) {
            if (isValidEmail(email)) {
                if (checkConnection()){
                forgotPasswordAPI(email);
            } else {
                    showDialogAlert(R.string.checkNetwork);
                }

        }else {
                showDialogAlert(R.string.alert_msg_valid_email);

            }


        }
        else {
            showDialogAlert(R.string.enteremail);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}