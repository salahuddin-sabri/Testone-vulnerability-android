package com.standardyze.App;

import android.app.Application;
import android.content.Context;

import androidx.appcompat.content.res.AppCompatResources;

import com.orm.SugarContext;
import com.standardyze.R;
import com.standardyze.utils.DatabaseHelper;

import butterknife.ButterKnife;
import sakout.mehdi.StateViews.StateViewsBuilder;

public class App extends Application {

  private static Context mContext;


  @Override
  public void onCreate() {//
    super.onCreate();
    // FacebookSdk.sdkInitialize(getApplicationContext());
    SugarContext.init(this);
    DatabaseHelper  databaseHelper = new DatabaseHelper(mContext);

    mContext = this;

  }

  public static Context getContext(){
    return mContext;
  }
}