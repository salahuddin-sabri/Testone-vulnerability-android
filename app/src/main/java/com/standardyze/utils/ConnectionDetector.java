package com.standardyze.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.standardyze.App.App;

public class ConnectionDetector {

  private Context _context;

  public ConnectionDetector(Context context) {
    this._context = context;
  }

  public boolean isConnectingToInternet() {
    ConnectivityManager connectivity = (ConnectivityManager) App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    if (connectivity != null) {
      NetworkInfo[] info = connectivity.getAllNetworkInfo();
      if (info != null)
        for (int i = 0; i < info.length; i++)
          if (info[i].getState() == NetworkInfo.State.CONNECTED) {
            return true;
          }


    }
    return false;
  }
  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public  void checkInternet(){
    NetworkRequest.Builder builder = new NetworkRequest.Builder();
    ConnectivityManager connectivity = (ConnectivityManager) App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    connectivity.registerNetworkCallback(
            builder.build(),
            new ConnectivityManager.NetworkCallback() {
              /**
               * @param network
               */
              @Override
              public void onAvailable(Network network) {

                LocalBroadcastManager.getInstance(App.getContext()).sendBroadcast(
                        getConnectivityIntent(false)
                );

              }

              /**
               * @param network
               */
              @Override
              public void onLost(Network network) {

                LocalBroadcastManager.getInstance(App.getContext()).sendBroadcast(
                        getConnectivityIntent(true)
                );

              }
            }

    );

  }
  public  boolean isNetworkAvailable(Context context) {
    ConnectivityManager connectivityManager
            = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    assert connectivityManager != null;
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
  }


  /**
   * @param noConnection
   * @return
   */
  private Intent getConnectivityIntent(boolean noConnection) {

    Intent intent = new Intent();

    intent.setAction("mypackage.CONNECTIVITY_CHANGE");
    intent.putExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, noConnection);

    return intent;



  }

}