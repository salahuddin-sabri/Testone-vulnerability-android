package com.standardyze.activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.orm.SugarContext;
import com.standardyze.App.SessionClass;
import com.standardyze.MainActivity;
import com.standardyze.R;
import com.standardyze.baseActivity;
import com.standardyze.customViews.CustomTextViewBook;
import com.standardyze.networking.WebServiceFactory;
import com.standardyze.utils.AppConstants;
import com.standardyze.utils.DatabaseHelper;
import com.standardyze.utils.SharedPrefManager;
import com.standardyze.wrapper.loginResponse;
import com.standardyze.wrapper.newAssessmentResponse;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends baseActivity {

    @BindView(R.id.email_edtxt)
    EditText email_edtxt;
    @BindView(R.id.pass_edtxt)
    EditText pass_edtxt;
    @BindView(R.id.signin_btn)
    Button loginBtn;

    @BindView(R.id.forgotpassword)
    CustomTextViewBook forgotpassword_txt;
    @BindView(R.id.checked)
    CheckBox rememberCheck;

    String userEmail, userPass;
    SharedPrefManager sharedPrefManager;

    private FirebaseAuth mAuth;
    DatabaseHelper databaseHelper;
    @BindView(R.id.rotateloadingLogin)
    RotateLoading rotateLoading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        SugarContext.init(this);
        init();
        getSupportActionBar().hide();
        Typeface copperplateGothicLight = Typeface.createFromAsset(this.getAssets(), "font/GothamBold.ttf");
        loginBtn.setTypeface(copperplateGothicLight);

        Typeface copperplateGothic = Typeface.createFromAsset(this.getAssets(), "font/GothamBook.ttf");
        email_edtxt.setTypeface(copperplateGothic);

    }

    public void init() {
        databaseHelper = new DatabaseHelper(this);
// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        sharedPrefManager = new SharedPrefManager(this);

if (sharedPrefManager.getStringByKey(AppConstants.USER_EMAIL) != null){
    setRememberCheck();
}
else {
    rememberCheck.setChecked(false);
}



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
        pass_edtxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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

    public void setRememberCheck() {

        if (getIntent().getStringExtra(AppConstants.USER_EMAIL) != null) {
            email_edtxt.setText(getIntent().getStringExtra(AppConstants.USER_EMAIL));
        } else if (sharedPrefManager.getStringByKey(AppConstants.USER_EMAIL) != null
                && !sharedPrefManager.getStringByKey(AppConstants.USER_EMAIL).equals("")
                    || !sharedPrefManager.getStringByKey(AppConstants.USER_EMAIL).isEmpty()
    ) {
                email_edtxt.setText(sharedPrefManager.getStringByKey(AppConstants.USER_EMAIL));
               // pass_edtxt.setText(sharedPrefManager.getStringByKey(AppConstants.USER_PASSWORD));
                rememberCheck.setChecked(true);
            } else {
                rememberCheck.setChecked(false);
            }


    }

    public void LoginApi(String email, String password) {
        rotateLoading.start();

        WebServiceFactory.getInstance().callLoginService(email, password).enqueue(new Callback<loginResponse>() {
            @Override
            public void onResponse(Call<loginResponse> call, Response<loginResponse> response) {
                try {
                    JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                    loginResponse loginResponse = (loginResponse) new Gson().fromJson(jsonObject.toString(), loginResponse.class);

                    if (loginResponse.getStatusCode() == 1) {
                        rotateLoading.stop();
                        ProgressDialog.show(login.this, "Loading", "Wait while loading...").dismiss();
                        getNewAssesmentDropDownData(loginResponse.getResults().get(0).getCompanyId(), AppConstants.lastUpdateDate);
                        if (rememberCheck.isChecked()) {
                            sharedPrefManager.setStringForKey(email, AppConstants.USER_EMAIL);
                            sharedPrefManager.setStringForKey(password, AppConstants.USER_PASSWORD);
                            sharedPrefManager.setBooleanForKey(true, AppConstants.IS_LOGIN);
                        } else {
                            sharedPrefManager.setStringForKey("", AppConstants.USER_EMAIL);
                            sharedPrefManager.setStringForKey("", AppConstants.USER_PASSWORD);
                            rememberCheck.setChecked(false);
                        }

                        SessionClass.getInstance().saveUserInfo(login.this, loginResponse.getResults().get(0));


                        Intent intent = new Intent(login.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();


                    } else {
                        rotateLoading.stop();
                        // ProgressDialog.show(login.this, "Loading", "Wait while loading...").dismiss();
                        showDialogAlert(R.string.alert_msg_loginCredentials);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<loginResponse> call, Throwable t) {
                showDialogAlert(R.string.network);
            }
        });


    }


    public void getNewAssesmentDropDownData(int companyId, int lastUpdatedDate) {
        WebServiceFactory.getInstance().newAssessmentService(companyId, lastUpdatedDate).enqueue(new Callback<newAssessmentResponse>() {
            @Override
            public void onResponse(Call<newAssessmentResponse> call, Response<newAssessmentResponse> response) {


                try {
                    JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                    newAssessmentResponse AssessmentResponse = new Gson().fromJson(jsonObject.toString(), newAssessmentResponse.class);

/*
                    Type listType = new TypeToken<List<String>>() {
                    }.getType();
                    List<propertyModel> target = new LinkedList<propertyModel>();
                    target.add(new propertyModel());

                    Gson gson = new Gson();
                    String json = gson.toJson(target, listType);
                    List<String> target2 = gson.fromJson(json, listType);*/


                    if (AssessmentResponse.getStatuCode() == 1) {
                        int updatedDate = AssessmentResponse.getLastUpdateDate();
                        sharedPrefManager.setIntegerForKey(updatedDate, AppConstants.getDate);


                        //users
                      /*  for (int i = 0; i < AssessmentResponse.getUsers().size(); i++) {


                        }*/
                        //Properties

                        for (int i = 0; i < AssessmentResponse.getProperties().size(); i++) {
                            databaseHelper.insertPropertyModel(AssessmentResponse.getProperties().get(i));
                            if (AssessmentResponse.getProperties().get(i).getAction().equalsIgnoreCase("add") || AssessmentResponse.getProperties().get(i).getAction().equalsIgnoreCase("edit")) {

                                Log(AssessmentResponse.getProperties().get(i) + "", "");
                            }
                          /*  if (AssessmentResponse.getProperties().get(i).getAction().equalsIgnoreCase("edit")){
                                databaseHelper.updateProperties(AssessmentResponse.getProperties().get(i).getId().toString(),AssessmentResponse.getProperties().get(i).getName(),AssessmentResponse.getProperties().get(i).getCompanyId());
                            }*/

                        }
                        //Departments
                        for (int i = 0; i < AssessmentResponse.getDepartments().size(); i++) {
                            if (AssessmentResponse.getDepartments().get(i).getAction().equalsIgnoreCase("add")) {
                                databaseHelper.insertDepartmentModel(AssessmentResponse.getDepartments().get(i));
                            }

                        }

                        //Roles
                        for (int i = 0; i < AssessmentResponse.getRoles().size(); i++) {
                            if (AssessmentResponse.getRoles().get(i).getAction().equalsIgnoreCase("add")) {
                                databaseHelper.insertRoles(AssessmentResponse.getRoles().get(i));
                            }
                        }

                        //Employee
                        for (int i = 0; i < AssessmentResponse.getEmployees().size(); i++) {
                            if (AssessmentResponse.getEmployees().get(i).getAction().equalsIgnoreCase("add")) {
                                databaseHelper.insertEmployess(AssessmentResponse.getEmployees().get(i),0);
                            }

                        }

                        //Assessment Titles
                        for (int i = 0; i < AssessmentResponse.getAssmessments().size(); i++) {
                            if (AssessmentResponse.getAssmessments().get(i).getAction().equalsIgnoreCase("add")) {
                                databaseHelper.insertAssessmentTitles(AssessmentResponse.getAssmessments().get(i));
                            }
                        }

                        //Property-Department Mapping
                        for (int i = 0; i < AssessmentResponse.getPropertyDepartmentMapping().size(); i++) {
                            databaseHelper.insertPropertyDepartmentMapping(AssessmentResponse.getPropertyDepartmentMapping().get(i));
                        }

                        //Department-Assessment Mapping
                        for (int i = 0; i < AssessmentResponse.getDepartmentAssessmentMapping().size(); i++) {
                            databaseHelper.insertDepartmentAndAssessmentMapping(AssessmentResponse.getDepartmentAssessmentMapping().get(i));
                        }

                        //Assessment-Role Mapping
                        for (int i = 0; i < AssessmentResponse.getAssessmentRoleMapping().size(); i++) {
                            databaseHelper.insertAssessmentRoleMapping(AssessmentResponse.getAssessmentRoleMapping().get(i));
                        }

                        //Property-Trainer Mapping
                        for (int i = 0; i < AssessmentResponse.getPropertyTrainerMapping().size(); i++) {
                            databaseHelper.insertPropertyTrainerMapping(AssessmentResponse.getPropertyTrainerMapping().get(i));
                        }

                        //Standard Questions
                        for (int i = 0; i < AssessmentResponse.getStandardQuestions().size(); i++) {
                            if (AssessmentResponse.getStandardQuestions().get(i).getAction().equalsIgnoreCase("add")) {
                                databaseHelper.insertStandardQuestions(AssessmentResponse.getStandardQuestions().get(i));
                            }

                        }
                        //Categories
                        for (int i = 0; i < AssessmentResponse.getCategories().size(); i++) {
                            if (AssessmentResponse.getCategories().get(i).getAction().equalsIgnoreCase("add")) {
                                databaseHelper.insertCategories(AssessmentResponse.getCategories().get(i));
                            }

                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(Call<newAssessmentResponse> call, Throwable t) {

            }
        });
    }

    public void signInMethod(String email, String password) {
        // if (checkConnection()){


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("SignIn Success", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.e("id", user.getUid());

                            Intent intent = new Intent(login.this, verificationActivity.class);
                            startActivity(intent);
                            // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("SignIn Fails", "signInWithEmail:failure", task.getException());
                            Toast.makeText(login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            // updateUI(null);
                        }

                        // ...
                    }
                });
        /*}
        else {
            Toast.makeText(this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        }*/
    }

    /**
     * Listeners
     */

    @OnClick(R.id.signin_btn)
    public void setLoginBtn() {
        userEmail = email_edtxt.getText().toString();
        userPass = pass_edtxt.getText().toString();
        if (!userEmail.isEmpty() && !userPass.isEmpty()) {
            if (isValidEmail(userEmail)) {
                if (checkConnection()) {
                    LoginApi(userEmail, userPass);
                } else {
                    showDialogAlert(R.string.checkNetwork);
                }

                //   signInMethod(userEmail, userPass);

            } else {
                showDialogAlert(R.string.alert_msg_valid_email);
            }
        } else {
            showDialogAlert(R.string.emptyFields);
        }

    }

    @OnClick(R.id.forgotpassword)
    public void setForgotpassword() {
        Intent intent
                = new Intent(this, forgotPassword.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // updateUI(currentUser);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean checkConnection() {
        return super.checkConnection();

    }


}