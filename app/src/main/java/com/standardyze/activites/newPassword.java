package com.standardyze.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.standardyze.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class newPassword extends AppCompatActivity {
  @BindView(R.id.newPass_edtxt)
  EditText newPass_edtxt;
  @BindView(R.id.confrm_edtxt)
  EditText confrm_edtxt;

  @BindView(R.id.update_btn)
  Button update_btn;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_new_password);
    ButterKnife.bind(this);

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
          v.setBackground(getResources().getDrawable(R.drawable.edttxt_line_bg));

        }
      }
    });

  }
}

