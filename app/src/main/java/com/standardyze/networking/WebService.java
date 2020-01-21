package com.standardyze.networking;

import com.standardyze.wrapper.Category;
import com.standardyze.wrapper.DashboardResponseModel;

import com.standardyze.wrapper.FilterQuestionsResponse;
import com.standardyze.wrapper.SaveResponse;
import com.standardyze.wrapper.forgotPasswordResponse;
import com.standardyze.wrapper.getAssessmentQuestionsResponse;
import com.standardyze.wrapper.loginResponse;
import com.standardyze.wrapper.loginResult;
import com.standardyze.wrapper.newAssessmentResponse;
import com.standardyze.wrapper.roles;
import com.standardyze.wrapper.updatePasswordResponse;

import org.json.JSONObject;

import java.security.Policy;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface WebService {

    @FormUrlEncoded
    @POST("api/login")
    Call<loginResponse> callLoginService(
            @Field("email") String email,
            @Field("password") String password);


    @FormUrlEncoded
    @POST("api/get/data")
    Call<newAssessmentResponse> newAssessmentService(
            @Field("companyId") int companyId,
            @Field("lastUpdateDate") int lastUpdateDate);

 /*   @FormUrlEncoded
    @POST("api/get/data")
    Call<Example> getDropDowndata(
            @Field("companyId") int companyId,
            @Field("lastUpdateDate") int lastUpdateDate);*/

    @FormUrlEncoded
    @POST("api/filter/question")
    Call<FilterQuestionsResponse> filterQuestionsService(
            @Field("companyId") int companyId,
            @Field("assessmentKey") String assessmentKey,
            @Field("roleKeys") String roleKeys
    );

    @FormUrlEncoded
    @POST("api/save/assessment")
    Call<Object> saveResponse(@Body String saveResponse);

    @FormUrlEncoded
    @POST("api/get/assessments")
    Call<Object> dashBoardData(
            @Field("npp")int noPerPage,
            @Field("page") int pageNo,
            @Field("userId") int userId,
            @Field("companyId") int companyId);

    @FormUrlEncoded
    @POST("api/update/assessment")
    Call<Object> updateData(@Body String Data);
    @FormUrlEncoded
    @POST("api/forgot/password")
    Call<forgotPasswordResponse> setPassword(@Field( "email") String email);


    @FormUrlEncoded
    @POST("api/get/assessment/questions")
    Call <getAssessmentQuestionsResponse> getSubmittedData(@Field("userId") int userId,
                                                           @Field("companyId") int companyId,
                                                           @Field("id") int assessmentID);

    @FormUrlEncoded
    @POST("api/update/password")
    Call<updatePasswordResponse> getUpdatePassword(@Field("companyId") int  companyId,
                                                   @Field("currentPassword") String currentPassword,
                                                   @Field("password") String newPassword,
                                                   @Field("repeatOPassword") String ConfirmPassword,
                                                   @Field("email") String email);





}
