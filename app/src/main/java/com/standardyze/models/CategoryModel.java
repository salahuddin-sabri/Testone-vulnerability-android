package com.standardyze.models;

import androidx.annotation.Nullable;

public class CategoryModel {
  private String key;
  private String value;
  private int yesCount;
  private int noCount;
  private double totalScore;
  private String id;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public double getTotalScore() {
    return totalScore;
  }

  public void setTotalScore(double totalScore) {
    this.totalScore = totalScore;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public int getYesCount() {
    return yesCount;
  }

  public void setYesCount(int yesCount) {
    this.yesCount = yesCount;
  }




  @Override
  public boolean equals(@Nullable Object obj) {
    if (obj instanceof CategoryModel) {
      CategoryModel model = (CategoryModel) obj;
      if (model.getKey().equalsIgnoreCase(this.getKey())) {
        return true;
      }
    }

    return false;
  }

  @Override
  public String toString() {
    return "{" +
            "key='" + key + '\'' +
            ", value='" + value + '\'' +
            ", yesCount=" + yesCount +
            ", noCount=" + noCount +
            '}';
  }

  public int getNoCount() {
    return noCount;
  }

  public void setNoCount(int noCount) {
    this.noCount = noCount;
  }
}
