package com.standardyze.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.standardyze.App.App;

import java.util.Collection;

public class SharedPrefManager {
    private SharedPreferences sharedpreferences;
    private Context mContext;

    private final String sharedPrefKey = "Project Standardyze";


    public SharedPrefManager(Context context) {
        mContext = context;
        sharedpreferences = mContext.getSharedPreferences(sharedPrefKey, Context.MODE_PRIVATE);
    }

 public void clearkey(){
     SharedPreferences.Editor editor = sharedpreferences.edit();

     editor.remove("selectedMeal");
     editor.remove("room");
     editor.remove("rolesArrayList");

     editor.apply();
 }
 public void clearKey(String key){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove(key);
        editor.apply();
 }
    //Todo: Remove Prefences when Log off
    public void clearPreferences() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();
    }
    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            Toast.makeText(App.getContext(), "Not logged In", Toast.LENGTH_SHORT).show();
        }

    }
    public boolean isLoggedIn() {
        return sharedpreferences.getBoolean(AppConstants.IS_LOGIN, false);
    }

    public void setBooleanForKey(Boolean val, String key) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(key, val);
        editor.apply();
    }

    public void setStringForKey(String val, String key) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, val);
        editor.apply();
    }

    public void setIntegerForKey(int val, String key) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(key, val);
        editor.apply();
    }


    public Boolean getBooleanByKey(String key) {
        if (sharedpreferences.contains(key)) {
            return sharedpreferences.getBoolean(key, false);
        }
        return false;
    }

    public String getStringByKey(String key){
        if (sharedpreferences.contains(key)) {
            return sharedpreferences.getString(key, "");
        }
        return null;
    }

    public int getIntegerByKey(String key){
        if (sharedpreferences.contains(key)) {
            return sharedpreferences.getInt(key, 0);
        }
        return 0;
    }

    public void saveCollectionTOSharedPref(Object collection, String key) {
        String value = new Gson().toJson(collection);
        setStringForKey(value, key);
    }


    public Collection getCollectionFromSharedPref(String key){
        String value = getStringByKey(key);
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        Collection collection  = gson.fromJson(value, Collection.class);
        return collection;
    }

    public void saveObjectInSharedPref(Object obj, String key) {
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        setStringForKey(json, key);
    }

    public Object getObjectFromSharedPref(String key,Class toCast) {
        Gson gson = new Gson();
        String json = getStringByKey(key);
        if (json!=null&&!json.equals("")){
            return gson.fromJson(json,toCast);
        }else{
            return null;
        }
    }

    public boolean isContains(String key){
        return sharedpreferences.contains(key);
    }

}