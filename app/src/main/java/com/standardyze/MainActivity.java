package com.standardyze;

import android.content.Intent;

import android.graphics.Color;
import android.graphics.Movie;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.gson.Gson;
import com.standardyze.App.SessionClass;
import com.standardyze.activites.account_setting;
import com.standardyze.activites.new_assessment;
import com.standardyze.adapters.dashboard_adapter;
import com.standardyze.customViews.CustomTextViewBold;
import com.standardyze.models.dashboard_items;
import com.standardyze.networking.WebServiceFactory;
import com.standardyze.utils.DatabaseHelper;
import com.standardyze.utils.PaginationScrollListener;
import com.standardyze.utils.SharedPrefManager;
import com.standardyze.wrapper.Pagination;
import com.standardyze.wrapper.dashboardAssessmentData;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindInt;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sakout.mehdi.StateViews.StateView;
import sakout.mehdi.StateViews.StateViewsBuilder;

public class MainActivity extends baseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.newAssessment_btn)
    Button launchNewAssessment_btn;
    LinearLayoutManager linearLayoutManager;
    @BindView(R.id.rotateloading)
    RotateLoading rotateLoading;
    DatabaseHelper databaseHelper;
    SharedPrefManager sharedPrefManager;
    ArrayList<dashboardAssessmentData> dashboard_itemsArrayList;
    dashboard_adapter dashboard_adapter;
    dashboard_items dashboard_items;

    RotateLoading progressBar;
    private static final int PAGE_START = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 3;
    private int perPage = 0;
    private int nextPage = 0;
    private int previousPage = 0;
    private int currentPage = PAGE_START;
    int uId;
    int companyId;
    @BindView(R.id.questions)
    CustomTextViewBold customTextViewBold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (RotateLoading) findViewById(R.id.main_progress);
        showActionBar("Dashboard", new Intent(this, account_setting.class), true, true, false, false);
        sharedPrefManager = new SharedPrefManager(this);
        sharedPrefManager.clearKey("savedEmployees");
        sharedPrefManager.clearKey("submittedEmployees");
        sharedPrefManager.clearKey("submitted");
        sharedPrefManager.clearKey("SavedGI");
        sharedPrefManager.clearKey("SubmittedGI");
        sharedPrefManager.clearKey("GIData");
        sharedPrefManager.clearKey("submittedDepartmentName");
        sharedPrefManager.clearKey("savedDepartmentName");
        sharedPrefManager.clearKey("submittedTrainerName");
        sharedPrefManager.clearKey("savedTrainerName");


        ButterKnife.bind(this);
        init();
        uId = SessionClass.getInstance().getUser(this).getId();
        companyId = SessionClass.getInstance().getUser(this).getCompanyId();
        Typeface copperplateGothicLight = Typeface.createFromAsset(this.getAssets(), "font/GothamBold.ttf");
        launchNewAssessment_btn.setTypeface(copperplateGothicLight);

        if (checkConnection()) {
            getDashboardData(5, 0, uId, companyId);
            //  getDataFromDb();
        } else {
            rotateLoading.stop();
            //  getDataFromDb();

        }
    }

    public void init() {
        progressBar.setVisibility(View.GONE);
        progressBar.stop();
        dashboard_itemsArrayList = new ArrayList<>();
        databaseHelper = new DatabaseHelper(this);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        dashboard_adapter = new dashboard_adapter(this, dashboard_itemsArrayList);


        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(dashboard_adapter);

        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                loadNextPage(5, currentPage, uId, companyId);
            }

            @Override
            public int getTotalPageCount() {
                return 0;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });


    }

    public void getDataFromDb() {
        databaseHelper.selectSavedAssessmentData();

        for (int i = 0; i < databaseHelper.selectSavedAssessmentData().size(); i++) {
            dashboardAssessmentData dashboardAssessmentData = new dashboardAssessmentData();
            dashboardAssessmentData.setAssessmentUniqueId(databaseHelper.selectSavedAssessmentData().get(i).getAssessmentUniqueId());
            dashboardAssessmentData.setTrainerName(databaseHelper.selectSavedAssessmentData().get(i).getTrainerName());
            dashboardAssessmentData.setDepartmentName(databaseHelper.selectSavedAssessmentData().get(i).getDepartmentName());
            dashboardAssessmentData.setPropertyName(databaseHelper.selectSavedAssessmentData().get(i).getPropertyName());
            dashboardAssessmentData.setAssessmentTitle(databaseHelper.selectSavedAssessmentData().get(i).getAssessmentTitle());
            dashboardAssessmentData.setStatus(databaseHelper.selectSavedAssessmentData().get(i).getStatus());
            dashboardAssessmentData.setScore(databaseHelper.selectSavedAssessmentData().get(i).getScore());
            dashboardAssessmentData.setCategoryScores(databaseHelper.selectSavedAssessmentData().get(i).getCategoryScores());
            dashboardAssessmentData.setEmployees(databaseHelper.selectSavedAssessmentData().get(i).getEmployees());
            dashboardAssessmentData.setGeneralInfromations(databaseHelper.selectSavedAssessmentData().get(i).getGeneralInfromations());
            dashboardAssessmentData.setDatetime(databaseHelper.selectSavedAssessmentData().get(i).getDatetime());
            dashboard_itemsArrayList.add(dashboardAssessmentData);
        }
        dashboard_adapter.notifyDataSetChanged();

    }

    public void getDashboardData(int nnp, int pageNo, int userId, int companyId) {
        rotateLoading.start();
        customTextViewBold.setVisibility(View.GONE);
        WebServiceFactory.getInstance().dashBoardData(nnp, pageNo, userId, companyId).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                if (response.isSuccessful()) {
                    rotateLoading.stop();

                    progressBar.setVisibility(View.GONE);

                    try {
                        JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));

                   /*     if (jsonObject.getInt("statusCode")==0){
                            customTextViewBold.setVisibility(View.VISIBLE);
                        }
                        else {
                            customTextViewBold.setVisibility(View.GONE);
                        }*/

                        JSONObject json = jsonObject.getJSONObject("results");
                        JSONArray jsonArray = json.getJSONArray("assessments");
                        JSONObject jsonObject2 = json.getJSONObject("pagination");
                        if (jsonObject2.has("next")) {
                            nextPage = jsonObject2.getInt("next");
                        }

                        if (jsonObject2.has("previousPage")) {
                            previousPage = jsonObject2.getInt("previousPage");
                        }
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            dashboardAssessmentData dashboardAssessmentData = new dashboardAssessmentData();
                            dashboardAssessmentData.setId(jsonObject1.getInt("id"));
                            dashboardAssessmentData.setAssessmentUniqueId(jsonObject1.getInt("assessmentUniqueId"));
                            dashboardAssessmentData.setDepartmentName(jsonObject1.getString("departmentName"));
                            dashboardAssessmentData.setPropertyName(jsonObject1.getString("propertyName"));
                            dashboardAssessmentData.setTrainerName(jsonObject1.getString("trainerName"));
                            dashboardAssessmentData.setAssessmentTitle(jsonObject1.getString("assessmentTitle"));
                            dashboardAssessmentData.setStatus(jsonObject1.getString("status"));
                            dashboardAssessmentData.setScore(jsonObject1.getInt("score"));
                            dashboardAssessmentData.setDatetime(jsonObject1.getString("datetime"));
                            dashboardAssessmentData.setEmployees(jsonObject1.getString("employees"));
                            dashboardAssessmentData.setGeneralInfromations(jsonObject1.getString("general_infromations"));
                            dashboard_itemsArrayList.add(dashboardAssessmentData);



                          /* if (currentPage != nnp) dashboard_adapter.addLoadingFooter();
                            else isLastPage = true;*/


                        }
                        if (dashboard_itemsArrayList.size()>0){
                            customTextViewBold.setVisibility(View.GONE);
                        }
                        else {
                            customTextViewBold.setVisibility(View.VISIBLE);
                        }


                        //  dashboard_adapter.addAll(dashboard_itemsArrayList);
                        if (currentPage <= TOTAL_PAGES) dashboard_adapter.addLoadingFooter();
                        else isLastPage = true;
                        dashboard_adapter.notifyDataSetChanged();



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    rotateLoading.stop();
                    progressBar.stop();
                    progressBar.setVisibility(View.GONE);
                   // customTextViewBold.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("Error", "" + t);
                rotateLoading.stop();
                progressBar.stop();
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    private void loadNextPage(int nnp, int pageNo, int userId, int companyId) {
        //rotateLoading.start();
        customTextViewBold.setVisibility(View.GONE);
        WebServiceFactory.getInstance().dashBoardData(nnp, pageNo, userId, companyId).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                if (response.isSuccessful()) {
                    rotateLoading.stop();
                    customTextViewBold.setVisibility(View.GONE);
                    dashboard_adapter.removeLoadingFooter();
                    isLoading = false;
                    progressBar.setVisibility(View.GONE);
                    try {
                        JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                      /* if (jsonObject.getInt("statusCode")==0){
                           customTextViewBold.setVisibility(View.VISIBLE);
                       }
                       else {
                           customTextViewBold.setVisibility(View.GONE);
                       }*/


                        JSONObject json = jsonObject.getJSONObject("results");
                        JSONArray jsonArray = json.getJSONArray("assessments");
                       /*  if (jsonArray.equals("[]")){
                             customTextViewBold.setVisibility(View.VISIBLE);
                         }
                         else {
                             customTextViewBold.setVisibility(View.GONE);
                         }*/
                        JSONObject jsonObject2 = json.getJSONObject("pagination");
                         if (jsonObject2.getInt("status")==0){

                         }
                        if (jsonObject2.has("next")) {
                            nextPage = jsonObject2.getInt("next");
                        }

                        if (jsonObject2.has("previousPage")) {
                            previousPage = jsonObject2.getInt("previousPage");
                        }
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            customTextViewBold.setVisibility(View.GONE);
                            dashboardAssessmentData dashboardAssessmentData = new dashboardAssessmentData();
                            dashboardAssessmentData.setId(jsonObject1.getInt("id"));
                            dashboardAssessmentData.setAssessmentUniqueId(jsonObject1.getInt("assessmentUniqueId"));
                            dashboardAssessmentData.setDepartmentName(jsonObject1.getString("departmentName"));
                            dashboardAssessmentData.setPropertyName(jsonObject1.getString("propertyName"));
                            dashboardAssessmentData.setTrainerName(jsonObject1.getString("trainerName"));
                            dashboardAssessmentData.setAssessmentTitle(jsonObject1.getString("assessmentTitle"));
                            dashboardAssessmentData.setStatus(jsonObject1.getString("status"));
                            dashboardAssessmentData.setScore(jsonObject1.getInt("score"));
                            dashboardAssessmentData.setDatetime(jsonObject1.getString("datetime"));
                            dashboardAssessmentData.setEmployees(jsonObject1.getString("employees"));
                            dashboardAssessmentData.setGeneralInfromations(jsonObject1.getString("general_infromations"));
                            dashboard_itemsArrayList.add(dashboardAssessmentData);
                            /*  if (dashboard_itemsArrayList.isEmpty()){
                                  customTextViewBold.setVisibility(View.VISIBLE);
                              }*/



                          /* if (currentPage != nnp) dashboard_adapter.addLoadingFooter();
                            else isLastPage = true;*/

                        }

                        if (dashboard_itemsArrayList.size()>0){
                            customTextViewBold.setVisibility(View.GONE);
                        }
                        else {
                            customTextViewBold.setVisibility(View.VISIBLE);
                        }
                        //   dashboard_adapter.addAll(dashboard_itemsArrayList);
                        if (currentPage != TOTAL_PAGES) dashboard_adapter.addLoadingFooter();
                        else isLastPage = true;
                      if (currentPage<=TOTAL_PAGES){
                          isLastPage = true;
                          isLoading = false;
                          dashboard_adapter.removeLoadingFooter();
                      }

                        dashboard_adapter.notifyDataSetChanged();


                    } catch (JSONException e) {
                        e.printStackTrace();
                        isLoading = false;
                        dashboard_adapter.removeLoadingFooter();
                    }

                } else {
                    progressBar.setVisibility(View.GONE);
                  //  rotateLoading.stop();
                    isLoading = false;

                    dashboard_adapter.removeLoadingFooter();
                   // customTextViewBold.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("Error", "" + t);
                isLoading = false;
                dashboard_adapter.removeLoadingFooter();
            }
        });
    }

    /**
     * Listener
     */

    @OnClick(R.id.newAssessment_btn)
    public void setLaunchNewAssessment_btn() {
  for (int i = 0; i < databaseHelper.selectEmployees().size(); i++) {
            databaseHelper.updateEmployees(0, databaseHelper.selectEmployees().get(i).getId());
        }
        sharedPrefManager.clearkey();
        sharedPrefManager.clearKey("updatedRolesArrayList");
        sharedPrefManager.clearKey("rolesArrayList");
        sharedPrefManager.clearKey("submittedRolesArraylist");
        startActivity(new Intent(this, new_assessment.class));

        sharedPrefManager.setIntegerForKey(0, "assessmentID");
        sharedPrefManager.clearKey("submittedEmployees");
        sharedPrefManager.clearKey("savedEmployees");
        sharedPrefManager.clearKey("SavedGI");
        sharedPrefManager.clearKey("SubmittedGI");
        sharedPrefManager.clearKey("GIData");
        sharedPrefManager.clearKey("submitted");
        sharedPrefManager.clearKey("savedEmployees");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // finish();
    }
}
