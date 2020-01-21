package com.standardyze.activites;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.standardyze.App.App;
import com.standardyze.App.SessionClass;
import com.standardyze.MainActivity;
import com.standardyze.R;
import com.standardyze.baseActivity;
import com.standardyze.customViews.CustomTextViewBook;
import com.standardyze.customViews.CustomTextViewMedium;
import com.standardyze.networking.WebServiceFactory;
import com.standardyze.utils.AppConstants;
import com.standardyze.utils.SharedPrefManager;
import com.standardyze.wrapper.updatePasswordResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class account_setting extends baseActivity {

    @BindView(R.id.client_logo)
    ImageView client_logo;
    @BindView(R.id.client_name)
    CustomTextViewMedium tv_clientName;
    @BindView(R.id.username_txt)
    CustomTextViewBook tv_username;
    @BindView(R.id.useremail_txt)
    CustomTextViewBook tv_useremail;
    @BindView(R.id.currentpass_edtxt)
    EditText currentpass_edtxt;
    @BindView(R.id.newPass_edtxt)
    EditText newPass_edtxt;
    @BindView(R.id.confrm_edtxt)
    EditText confrm_edtxt;
    @BindView(R.id.update_btn)
    Button updatePass_btn;
    String email, newPassword, confirmPassword, currentPassword;
    int companyId;
    String userName, userEmail;
    SharedPrefManager sharedPrefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);
        ButterKnife.bind(this);
        sharedPrefManager = new SharedPrefManager(this);
        showActionBar("Account Settings", new Intent(this, MainActivity.class), false, false, false, false);
        init();
        Typeface copperplateGothicLight = Typeface.createFromAsset(this.getAssets(), "font/GothamBold.ttf");
        updatePass_btn.setTypeface(copperplateGothicLight);
    }

    public void init() {
        userEmail = SessionClass.getInstance().getUser(this).getEmail();
        userName = SessionClass.getInstance().getUser(this).getFname()+" "+ SessionClass.getInstance().getUser(this).getLname();
        tv_useremail.setText(userEmail);
        tv_username.setText(userName);
        currentpass_edtxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        newPass_edtxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        confrm_edtxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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


    }

    public void setUpdatePass(int companyId, String currentPassword, String newPassword, String repeatOPassword, String email) {
        WebServiceFactory.getInstance().getUpdatePassword(companyId, currentPassword, newPassword, repeatOPassword, email).enqueue(new Callback<updatePasswordResponse>() {
            @Override
            public void onResponse(Call<updatePasswordResponse> call, Response<updatePasswordResponse> response) {
                try {
                    JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                    updatePasswordResponse updatePasswordResponse = new Gson().fromJson(jsonObject.toString(), updatePasswordResponse.class);
                    if (checkConnection()){


                    if (updatePasswordResponse.getStatusCode() == 1) {
                        alertPasswordSucceeded(R.string.password_change_success);
                        //showDialogAlert(R.string.password_change_success);
                        confrm_edtxt.setText("");
                        newPass_edtxt.setText("");
                        currentpass_edtxt.setText("");
                        sharedPrefManager.clearPreferences();
                        sharedPrefManager.setBooleanForKey(false, AppConstants.IS_LOGIN);

                        Log(updatePasswordResponse.getMessage());
                    }
                    else {
                        showDialogAlert(R.string.valid_password);
                    }
                    }
                    else {
                        showDialogAlert(R.string.checkNetwork);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Call<updatePasswordResponse> call, Throwable t) {
                showDialogAlert(R.string.password_length_validation_failed);
              //  showDialogAlert(R.string.checkNetwork);
            }
        });
    }
    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
          // final String PASSWORD_PATTERN = "(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[^\\\\w\\\\s]).{8,}";
           final String PASSWORD_PATTERN = "^(?=[^0-9]*[0-9])(?=(?:[^A-Za-z]*[A-Za-z]){2})(?=[^@#$%^&+=]*[@#$%^&+=])\\S{8,20}$";
          // final String PASSWORD_PATTERN = "(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z]){8,}";
          // ^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"
       // final String PASSWORD_PATTERN = "(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z]).{8,}";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
    @OnClick(R.id.update_btn)
    public void setUpdatePass_btn() {
        companyId = SessionClass.getInstance().getUser(this).getCompanyId();
        email = SessionClass.getInstance().getUser(this).getEmail();

        currentPassword = currentpass_edtxt.getText().toString();
        newPassword = newPass_edtxt.getText().toString();
        confirmPassword = confrm_edtxt.getText().toString();
        if (!currentPassword.isEmpty()&& !newPassword.isEmpty() && !confirmPassword.isEmpty()){
            if (newPassword.equalsIgnoreCase(confirmPassword)) {


                if (isValidPassword(newPassword) ||isValidPassword(confirmPassword)) {
                    setUpdatePass(companyId, currentPassword, newPassword, confirmPassword, email);
   System.out.println("Valid");

                } else {
                    System.out.println("Not Valid");
                    showDialogAlert(R.string.password_length_validation_failed);
                }
            }
            else {
                showDialogAlert(R.string.alert_password_match);
            }
        }
        else {
            showDialogAlert(R.string.emptyFields);
        }



    }

}
