package com.standardyze.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.standardyze.MainActivity;
import com.standardyze.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class verificationActivity extends AppCompatActivity {


    @BindView(R.id.code_edtxt)
    EditText code_edtxt;
    @BindView(R.id.verify_btn)
    Button verify_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        code_edtxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    v.setBackground(getResources().getDrawable(R.drawable.editbox_drawable));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        v.setElevation(10f);
                    }

                }
                else {
                    v.setElevation(0);
                    v.setBackground(getResources().getDrawable(R.drawable.edttxt_line_bg));
                }
            }
        });


    }
    @OnClick(R.id.verify_btn)
    public void setVerify_btn(){
        startActivity(new Intent(this, MainActivity.class));

    }
}
