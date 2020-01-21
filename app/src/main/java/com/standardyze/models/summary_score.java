package com.standardyze.models;

public class summary_score {
  String assesment_cat;
  String scores;


  public summary_score() {
  }

  public summary_score(String assesment_cat, String scores) {
    this.assesment_cat = assesment_cat;
    this.scores = scores;
  }

  public String getAssesment_cat() {
    return assesment_cat;
  }

  public void setAssesment_cat(String assesment_cat) {
    this.assesment_cat = assesment_cat;
  }

  public String getScores() {
    return scores;
  }

  public void setScores(String scores) {
    this.scores = scores;
  }
}
