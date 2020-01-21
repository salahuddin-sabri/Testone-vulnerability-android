package com.standardyze.App;

import android.content.Context;

import com.standardyze.utils.AppConstants;
import com.standardyze.utils.SharedPrefManager;
import com.standardyze.wrapper.loginResult;

public class SessionClass {

  private static SessionClass mInstance;
  private String userPassword;
  private String userEmail;
  private int userId;
  private String firstName;
  private boolean isLogin;

  private SessionClass() {
    //no instance
  }

  public static SessionClass getInstance() {
    if (mInstance == null)
      mInstance = new SessionClass();
    return mInstance;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public boolean isLogin() {
    return isLogin;
  }

  public void setLogin(boolean login) {
    isLogin = login;
  }

  public String getUserPassword() {
    return userPassword;
  }

  public void setUserPassword(String userPassword) {
    this.userPassword = userPassword;
  }


  public String getFirstName() {
    return firstName;
  }

  public void setFirstname(String firstname) {
    this.firstName = firstname;
  }

  public loginResult getUser(Context context) {
    return ((loginResult) new SharedPrefManager(context).
            getObjectFromSharedPref(AppConstants.KEY_USER, loginResult.class));
  }

  public void saveUserInfo(Context context, loginResult user) {
    new SharedPrefManager(context).
            saveObjectInSharedPref(user, AppConstants.KEY_USER);
  }
}
