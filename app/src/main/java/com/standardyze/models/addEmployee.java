package com.standardyze.models;

import java.util.Objects;

public class addEmployee {
  String employeeName;
  int isChecked;


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    addEmployee that = (addEmployee) o;
    return isChecked == that.isChecked &&
            employeeName.equals(that.employeeName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(employeeName, isChecked);
  }

  public String getEmployeeName() {
    return employeeName;
  }

  public void setEmployeeName(String employeeName) {
    this.employeeName = employeeName;
  }

  public int isChecked() {
    return isChecked;
  }

  public void setChecked(int checked) {
    isChecked = checked;
  }
}
