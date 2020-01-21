package com.standardyze.models;

public class dashboard_items {
  private String AssessmentTitle;
  private String AssessmentProperty;
  private String AssessmentTrainer;
  private String AssessmentScore;
  private String AssessmentStatus;


  public dashboard_items() {
  }

  public dashboard_items(String assessmentTitle, String assessmentProperty, String assessmentTrainer, String assessmentScore, String assessmentStatus) {
    AssessmentTitle = assessmentTitle;
    AssessmentProperty = assessmentProperty;
    AssessmentTrainer = assessmentTrainer;
    AssessmentScore = assessmentScore;
    AssessmentStatus = assessmentStatus;
  }

  public String getAssessmentTitle() {
    return AssessmentTitle;
  }

  public void setAssessmentTitle(String assessmentTitle) {
    AssessmentTitle = assessmentTitle;
  }

  public String getAssessmentProperty() {
    return AssessmentProperty;
  }

  public void setAssessmentProperty(String assessmentProperty) {
    AssessmentProperty = assessmentProperty;
  }

  public String getAssessmentTrainer() {
    return AssessmentTrainer;
  }

  public void setAssessmentTrainer(String assessmentTrainer) {
    AssessmentTrainer = assessmentTrainer;
  }

  public String getAssessmentScore() {
    return AssessmentScore;
  }

  public void setAssessmentScore(String assessmentScore) {
    AssessmentScore = assessmentScore;
  }

  public String getAssessmentStatus() {
    return AssessmentStatus;
  }

  public void setAssessmentStatus(String assessmentStatus) {
    AssessmentStatus = assessmentStatus;
  }
}
