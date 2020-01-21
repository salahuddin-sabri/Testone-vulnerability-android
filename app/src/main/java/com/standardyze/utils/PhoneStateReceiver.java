package com.standardyze.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

public class PhoneStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            System.out.println("Receiver start");


                Intent local = new Intent();
                local.setAction("service.to.activity.transfer");
                local.putExtra("number", "aaaa");
                context.sendBroadcast(local);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}