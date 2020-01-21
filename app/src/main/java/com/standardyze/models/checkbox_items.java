package com.standardyze.models;

public class checkbox_items {

  String role1;
  String role2;
  String role3;
  boolean isSelected;

  public boolean isSelected() {
    return isSelected;
  }

  public void setSelected(boolean selected) {
    isSelected = selected;
  }

  public checkbox_items() {
  }

  public checkbox_items(String role1) {
    this.role1 = role1;
  }

  public String getRole1() {
    return role1;
  }

  public void setRole1(String role1) {
    this.role1 = role1;
  }

  public String getRole2() {
    return role2;
  }

  public void setRole2(String role2) {
    this.role2 = role2;
  }

  public String getRole3() {
    return role3;
  }

  public void setRole3(String role3) {
    this.role3 = role3;
  }
}
