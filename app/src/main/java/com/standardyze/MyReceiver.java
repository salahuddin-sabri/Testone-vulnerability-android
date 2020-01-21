package com.standardyze;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.standardyze.wrapper.General_infromations;
import com.standardyze.wrapper.QuestionsResult;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ArrayList<General_infromations> general_infromations = new ArrayList<>();
        Gson gson = new Gson();
        Type type = new TypeToken<List<General_infromations>>() {
        }.getType();
        general_infromations = gson.fromJson(intent.getStringExtra("general_info_list"), type);
        Toast.makeText(context, "" + general_infromations, Toast.LENGTH_SHORT).show();
    }
}