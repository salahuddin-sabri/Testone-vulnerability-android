package com.standardyze;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.standardyze.App.App;
import com.standardyze.activites.account_setting;
import com.standardyze.activites.addEmployees;
import com.standardyze.activites.general_information;
import com.standardyze.activites.login;

import com.standardyze.activites.pdfActivity;
import com.standardyze.customViews.CustomTextViewBold;
import com.standardyze.customViews.CustomTextViewBook;
import com.standardyze.customViews.CustomTextViewMedium;
import com.standardyze.utils.AppConstants;
import com.standardyze.utils.ConnectionDetector;
import com.standardyze.utils.SharedPrefManager;

import static com.standardyze.App.App.getContext;

public abstract class baseActivity extends AppCompatActivity {
    ConnectionDetector connectionDetector;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectionDetector = new ConnectionDetector(getContext());
        sharedPrefManager = new SharedPrefManager(this);
        adjustFontScale(getResources().getConfiguration());

    }

    public void makeToast(String message) {
        Toast.makeText(App.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void makeToastLong(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    public void adjustFontScale(Configuration configuration) {
        if (configuration.fontScale > 1.30) {
            configuration.fontScale = (float) 1.00;
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(metrics);
            metrics.scaledDensity = configuration.fontScale * metrics.density;
            getBaseContext().getResources().updateConfiguration(configuration, metrics);
        }
    }


    public boolean checkLoginState() {
        return sharedPrefManager.isLoggedIn();
    }

    //User userInfo
/*
    public void createLoginSession(User userInfo) {
        sessionManager.createLoginSession(userInfo);
    }
*/

    public void Log(String tag, String value) {
        if (AppConstants.onTest) Log.e(tag, value);
    }

    public void Log(String value) {
        if (AppConstants.onTest) Log.e(AppConstants.LOG_TAG, value);
    }

    public boolean checkConnection() {
        boolean flag = false;
        if (connectionDetector.isNetworkAvailable(this)) {
            flag = true;
        } else {
            flag = false;
        }
        return flag;



    }
/*
    public abstract void showLoader();

    public abstract void hideLoader();*/


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public void showAlert(int error, DialogInterface.OnClickListener listener) {
        LayoutInflater inflater = (this).getLayoutInflater();
        new AlertDialog.Builder(this)
                .setTitle(R.string.alert)
                .setView(inflater.inflate(R.layout.custom_alert_dialog, null))
                .setMessage(error)
                .setPositiveButton(R.string.dg_button_positive_ok, listener)
                .show();


    }

    public boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public void showDialogAlert(int error) {
        Dialog dialog = new Dialog(this);
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

    public void showDialogAlertLogout(int error) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_alert_dialog);
        CustomTextViewBook errormsg = (CustomTextViewBook) dialog.findViewById(R.id.alertText);
        LinearLayout linearLayout = (LinearLayout) dialog.findViewById(R.id.yesNoLayout);
        errormsg.setText(error);
        dialog.setCancelable(false);
        Button button = (Button) dialog.findViewById(R.id.ok_btn);
        Button yes = (Button) dialog.findViewById(R.id.yes_btn);
        Button no_btn = (Button) dialog.findViewById(R.id.no_btn);
        linearLayout.setVisibility(View.VISIBLE);
        button.setVisibility(View.GONE);
        yes.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {

               // sharedPrefManager.clearPreferences();
                Intent i = new Intent(baseActivity.this, login.class);
                sharedPrefManager.setBooleanForKey(false, AppConstants.IS_LOGIN);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
                dialog.dismiss();
            }

        });
        no_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    public void alertPasswordSucceeded(int error) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_alert_dialog);
        CustomTextViewBook errormsg = (CustomTextViewBook) dialog.findViewById(R.id.alertText);
        errormsg.setText(error);
        dialog.setCancelable(false);
        Button button = (Button) dialog.findViewById(R.id.ok_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  i =new Intent(baseActivity.this,login.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void showActionBar(String Title, Intent intent, boolean logout_isvisible, boolean res, boolean actionBar_res, boolean summary_email) {

        LayoutInflater inflator = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_ab, null);
        LinearLayout arrival_bakLayout = (LinearLayout) v.findViewById(R.id.arrival_back);
        ImageView addEmployee = (ImageView) v.findViewById(R.id.add_employee_btn);
        ImageView generalInfo = (ImageView) v.findViewById(R.id.general_btn);
        ImageView email_ic = (ImageView) v.findViewById(R.id.email_ic);


        if (summary_email) {
            email_ic.setVisibility(View.VISIBLE);
            email_ic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // requestPermission();
                    Intent in = new Intent(baseActivity.this, pdfActivity.class);
                    startActivity(in);
                }
            });
        } else {
            email_ic.setVisibility(View.GONE);
        }
        if (actionBar_res) {
            arrival_bakLayout.setVisibility(View.VISIBLE);
        } else {
            arrival_bakLayout.setVisibility(View.GONE);
        }
        addEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(baseActivity.this, addEmployees.class));
            }
        });
        generalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent   intent1 =new Intent(baseActivity.this, general_information.class);

                startActivity(intent1);

            }
        });

        CustomTextViewBold textView = (CustomTextViewBold) v.findViewById(R.id.title);
        textView.setText(Title);
        ImageView setting = (ImageView) v.findViewById(R.id.setting_btn);
        if (res) {
            setting.setImageResource(R.drawable.setting_btn);
            setting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Intent();
                    startActivity(intent);


                }
            });
        } else {
            setting.setImageResource(R.drawable.back_btn);
            setting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();

                }
            });
        }

        ImageView logoutBtn = (ImageView) v.findViewById(R.id.logout_btn);
        if (logout_isvisible) {
            logoutBtn.setVisibility(View.VISIBLE);
        } else {
            logoutBtn.setVisibility(View.GONE);

        }
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //logout
                showDialogAlertLogout(R.string.alert_msg_logout);

            }
        });


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setCustomView(v);

    }
    public void showSummaryActionbar(String Title,boolean summary_email,boolean res){
        LayoutInflater inflator = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_ab, null);
        ImageView email_ic = (ImageView) v.findViewById(R.id.email_ic);
        CustomTextViewBold textView = (CustomTextViewBold) v.findViewById(R.id.title);
        textView.setText(Title);
        if (summary_email) {
            email_ic.setVisibility(View.VISIBLE);
            email_ic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // requestPermission();
               /*    Intent in = new Intent(baseActivity.this, pdfActivity.class);
                   startActivity(in);*/
                }
            });
        } else {
            email_ic.setVisibility(View.GONE);
        }
        ImageView setting = (ImageView) v.findViewById(R.id.setting_btn);
        if (res) {
            setting.setImageResource(R.drawable.setting_btn);
            setting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setting.setVisibility(View.GONE);
                }
            });
        } else {
            setting.setImageResource(R.drawable.back_btn);
            setting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();

                }
            });
        }

    }
    public void requestPermission() {

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE,
                            android.Manifest.permission.CAMERA},
                    100);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v("permission", "Permission: " + permissions[0] + " was " + grantResults[0]);

        } else {
            requestPermission();

        }
    }


/* @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2){
            if (resultCode == RESULT_OK){
                Bundle MBuddle = data.getExtras();
                String MMessage = MBuddle .getString("general_info_list");
               Log.e("DataFrom general Info",""+data.getStringExtra("general_info_list"));
               Log.e("DataFrom general Info",""+MMessage);
            }
        }
    }*/
}
