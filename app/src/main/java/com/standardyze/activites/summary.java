package com.standardyze.activites;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.standardyze.App.SessionClass;
import com.standardyze.MainActivity;
import com.standardyze.R;
import com.standardyze.adapters.pdf_roles_adapter;
import com.standardyze.adapters.roles_adapter;
import com.standardyze.adapters.summary_catScore_adapter;
import com.standardyze.customViews.CustomTextViewBold;
import com.standardyze.customViews.CustomTextViewBook;
import com.standardyze.customViews.CustomTextViewMedium;
import com.standardyze.models.CategoryModel;
import com.standardyze.utils.AppConstants;
import com.standardyze.utils.ConnectionDetector;
import com.standardyze.utils.DatabaseHelper;
import com.standardyze.utils.SharedPrefManager;
import com.standardyze.wrapper.Employee;
import com.standardyze.wrapper.General_infromations;
import com.standardyze.wrapper.QuestionsResult;
import com.standardyze.wrapper.SaveResponse;
import com.standardyze.wrapper.categories_score;
import com.standardyze.wrapper.getAssessmentResponseObj;
import com.standardyze.wrapper.roles;
import com.standardyze.wrapper.roles_ResponseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class summary extends AppCompatActivity {
    @BindView(R.id.recycler_view_score)
    RecyclerView recyclerView;
   // @BindView(R.id.roles_recyclerview)
   // RecyclerView roles_recyclerview;
    @BindView(R.id.chip_group)
    ChipGroup chipGroup;
    @BindView(R.id.text_score)
    CustomTextViewMedium score_txt;
    @BindView(R.id.inspired_service_txt)
    CustomTextViewMedium ins_service_txt;
    @BindView(R.id.save_btn)
    Button save_btn;
    @BindView(R.id.submit_btn)
    Button submit_btn;
    @BindView(R.id.close_btn)
    Button close_btn;
    ArrayList<categories_score> arrayList;
    ArrayList<CategoryModel> catList;
    ArrayList<categories_score> getArrayList;
    ArrayList<categories_score> updatedgetArrayList;
    ArrayList<roles> roles_arraylist;

    LinearLayoutManager linearLayoutManager;
    summary_catScore_adapter scoreAdapter;
    roles_adapter adapter;

    DatabaseHelper databaseHelper;
    List<QuestionsResult> QuestionList;
    ArrayList<getAssessmentResponseObj> savedList;
    SaveResponse saveResponse;
    roles_ResponseModel roles_responseModel;
    ArrayList<Employee> employeeArrayList;
    RequestQueue newRequestQueue;
    SharedPrefManager sharedPrefManager;
    List<General_infromations> general_infromationsArrayList;
    String status;
    private ArrayList<roles> updatedrolesArraylist;
    private ArrayList<roles> submittedRolesArraylist;
    pdf_roles_adapter pdf_roles_adapter;
    String savedEmployees;
    ConnectionDetector  connectionDetector;
    General_infromations Saved_general_infromations;
    ArrayList <String>  strings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        connectionDetector = new ConnectionDetector(this);
        showSummaryActionbar("Summary", true, false);
        ButterKnife.bind(this);
        init();
        Typeface copperplateGothicLight = Typeface.createFromAsset(this.getAssets(), "font/GothamBold.ttf");
        save_btn.setTypeface(copperplateGothicLight);
        submit_btn.setTypeface(copperplateGothicLight);

        //showActionBar("Summary", null, false, false, false, false);
    }

    public void showSummaryActionbar(String Title, boolean summary_email, boolean res) {
        LayoutInflater inflator = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_ab, null);
        ImageView email_ic = (ImageView) v.findViewById(R.id.email_ic);
        LinearLayout arrival_bakLayout = (LinearLayout) v.findViewById(R.id.arrival_back);
        arrival_bakLayout.setVisibility(View.GONE);
        CustomTextViewBold textView = (CustomTextViewBold) v.findViewById(R.id.title);
        textView.setText(Title);
        if (summary_email) {
            email_ic.setVisibility(View.VISIBLE);
            email_ic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // requestPermission();
                    Intent in = new Intent(summary.this, pdfActivity.class);
                    startActivity(in);

                }
            });
        } else {
            email_ic.setVisibility(View.GONE);
        }
        ImageView setting = (ImageView) v.findViewById(R.id.setting_btn);
        if (res) {
            setting.setImageResource(R.drawable.setting_btn);
            setting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setting.setVisibility(View.GONE);
                }
            });
        } else {
            setting.setImageResource(R.drawable.back_btn);
            setting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();

                }
            });
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setCustomView(v);
    }

    public void init() {
        general_infromationsArrayList = new ArrayList<>();
        sharedPrefManager = new SharedPrefManager(this);
        newRequestQueue = Volley.newRequestQueue(this);
        roles_responseModel = new roles_ResponseModel();
        saveResponse = new SaveResponse();
        QuestionList = new ArrayList<>();
        getArrayList = new ArrayList<>();
        updatedgetArrayList = new ArrayList<>();
        arrayList = new ArrayList<>();
        savedList = new ArrayList<>();
        catList = new ArrayList<>();
        updatedrolesArraylist = new ArrayList<>();
        submittedRolesArraylist = new ArrayList<>();
        roles_arraylist = new ArrayList<>();
        employeeArrayList = new ArrayList<>();
        databaseHelper = new DatabaseHelper(this);
        Intent intent = getIntent();
        Gson gson = new Gson();
      strings   = new ArrayList<>();
      //  savedGI = intent.getStringExtra("Saved_general_info");
        Type type4 = new TypeToken<List<General_infromations>>() {
        }.getType();
        Type type = new TypeToken<List<QuestionsResult>>() {
        }.getType();
        Type type2 = new TypeToken<List<getAssessmentResponseObj>>() {
        }.getType();
        Type type3 = new TypeToken<List<CategoryModel>>() {
        }.getType();
        ;
        Type type5 = new TypeToken<List<roles>>() {
        }.getType();
        Type type6 = new TypeToken<List<roles>>() {
        }.getType();

        Type  type1 = new TypeToken<List<Employee>>(){

        }.getType();
      sharedPrefManager.clearKey("newCategories");
      sharedPrefManager.clearKey("savedCategories");


      if (intent.getStringExtra("general_info")!=null){
          general_infromationsArrayList = gson.fromJson(intent.getStringExtra("general_info"), type4);
      }


        if ((intent.getStringExtra("Saved_general_info") != null)){
            if (Objects.equals(intent.getStringExtra("Saved_general_info"), "N/A")){
                Saved_general_infromations = null;
                general_infromationsArrayList=null;
            }else {
              //  general_infromationsArrayList = gson.fromJson(intent.getStringExtra("Saved_general_info"), type4);
               // if (!general_infromationsArrayList.isEmpty()){
                    Saved_general_infromations = gson.fromJson(intent.getStringExtra("Saved_general_info"),  General_infromations.class);
               // }
            }
           // Saved_general_infromations = gson.fromJson(intent.getStringExtra("Saved_general_info"),  General_infromations.class);
        }
        else {
            Saved_general_infromations = gson.fromJson(intent.getStringExtra("Saved_general_info"),  General_infromations.class);
        }

      //  Saved_general_infromations = gson.fromJson(intent.getStringExtra("Submitted_general_info"),  General_infromations.class);


        QuestionList = gson.fromJson(intent.getStringExtra("List"), type);
        savedList = gson.fromJson(intent.getStringExtra("updatedList"), type2);
        catList = gson.fromJson(intent.getStringExtra("catList"), type3);
        sharedPrefManager.setStringForKey(intent.getStringExtra("updatedList"), "savedList");
        sharedPrefManager.setStringForKey(intent.getStringExtra("List"),"newList");

        //savedData RolesList
        updatedrolesArraylist = gson.fromJson(intent.getStringExtra("updatedRolesArrayList"),type5);
        submittedRolesArraylist = gson.fromJson(intent.getStringExtra("submittedRolesArrayList"),type6);

        if (employeeArrayList!=null){
            employeeArrayList = gson.fromJson(sharedPrefManager.getStringByKey("EmployeesSaved"),type1);
            Log.e("EmployeesSaved",""+employeeArrayList.get(0).getIsChecked());
            StringBuilder sb1 = new StringBuilder();
            for (int i =0;i<employeeArrayList.size();i++){
                if (employeeArrayList.get(i).getIsChecked()==1){
                    sb1.append(employeeArrayList.get(i).getFname()+" "+employeeArrayList.get(i).getLname());
                    sb1.append(",");
                }
            }
            Log.e("EmployeesSaved",""+sb1);
        }



      //  GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.VERTICAL, false);
      //  roles_recyclerview.setLayoutManager(gridLayoutManager);


        status = intent.getStringExtra("status");


        String savedData = intent.getStringExtra("savedData");
        if (status != null && status.equalsIgnoreCase("Submitted")) {
            save_btn.setVisibility(View.GONE);
            submit_btn.setVisibility(View.GONE);
            close_btn.setVisibility(View.VISIBLE);
        }
        //Saved data roles
        if (status != null && status.equalsIgnoreCase(AppConstants.SAVED_TO_SERVER)) {
            for (int i = 0; i < updatedrolesArraylist.size(); i++) {
                roles pdf_roles = new roles();
                int finalI = i;
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        new Thread() {
                            @Override
                            public void run() {
                                pdf_roles.setName(updatedrolesArraylist.get(finalI).getName());
                                updatedrolesArraylist.add(pdf_roles);

                            }
                        }.start();
                    }
                });

            }
            sharedPrefManager.setStringForKey(gson.toJson(updatedrolesArraylist), "updatedrolesArrayList");

            for(roles genre : updatedrolesArraylist) {
                Chip chip = new Chip(this);
                chip.setText(genre.getName());
                chip.setWidth(2);
                chip.setTextSize(14);
                chip.setChipMinHeight(49);
                chipGroup.addView(chip);
            }
           // pdf_roles_adapter = new pdf_roles_adapter(updatedrolesArraylist, this);
          //  roles_recyclerview.setAdapter(pdf_roles_adapter);
        }
/*        //DBSAVED Roles
          if (status!=null && status.equalsIgnoreCase("DBSaved")){
            for (int i = 0; i < updatedrolesArraylist.size(); i++) {
                roles pdf_roles = new roles();
                int finalI = i;
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        new Thread() {
                            @Override
                            public void run() {
                                pdf_roles.setName(updatedrolesArraylist.get(finalI).getName());
                                updatedrolesArraylist.add(pdf_roles);

                            }
                        }.start();
                    }
                });

            }
            sharedPrefManager.setStringForKey(gson.toJson(updatedrolesArraylist), "updatedrolesArrayList");
            pdf_roles_adapter = new pdf_roles_adapter(updatedrolesArraylist, this);
            roles_recyclerview.setAdapter(pdf_roles_adapter);
        }*/

        if (status!=null && status.equalsIgnoreCase("Submitted")){
            for (int i=0;i<submittedRolesArraylist.size();i++){
                roles  roles = new roles();
                int finalI = i;
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        new Thread() {
                            @Override
                            public void run() {
                                roles.setName(submittedRolesArraylist.get(finalI).getName());
                                submittedRolesArraylist.add(roles);

                            }
                        }.start();
                    }
                });
            }
            sharedPrefManager.setStringForKey(gson.toJson(submittedRolesArraylist), "submittedRolesArraylist");
            for(roles genre : submittedRolesArraylist) {
                Chip chip = new Chip(this);
                chip.setText(genre.getName());
                chip.setWidth(2);
                chip.setTextSize(13);
                chip.setChipMinHeight(49);
                chipGroup.addView(chip);
            }
          //  pdf_roles_adapter = new pdf_roles_adapter(submittedRolesArraylist, this);
         //   roles_recyclerview.setAdapter(pdf_roles_adapter);
        }
        else if (status == null) {
            //new data entered roles
            roles roles;
            for (int i = 0; i < databaseHelper.selectRoles().size(); i++) {
                roles = new roles();
                roles.setName(databaseHelper.selectRoles().get(i).getName());
                roles_arraylist.add(roles);
            }
            sharedPrefManager.setStringForKey(gson.toJson(roles_arraylist), "rolesArrayList");
            for(roles genre : roles_arraylist) {
                Chip chip = new Chip(this);
                chip.setText(genre.getName());
                chip.setWidth(2);
                chip.setTextSize(13);
                chip.setChipMinHeight(49);
                chipGroup.addView(chip);
            }
          //  adapter = new roles_adapter(roles_arraylist, this);
          //  roles_recyclerview.setAdapter(adapter);
        }
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        scoreAdapter = new summary_catScore_adapter(summary.this, arrayList);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(scoreAdapter);
        /**
         * Setting Employess
         */

       // employeeArrayList = databaseHelper.selectEmployees();
        if (savedData != null && !savedData.isEmpty()) {

            score_txt.setText(String.valueOf(calculateUpdatedScore())+"%");
            calculateUpdatedCategoriesTotalScore2();


        } else {

            score_txt.setText(String.valueOf(calculateTotalScore())+"%");
            calculateCategoriesTotalScore2();



        }

    }

    public String saveData(String status) {
        String status_saved = AppConstants.SAVED_TO_SERVER;

        StringBuilder sb1 = new StringBuilder();

        for (int i = 0; i < employeeArrayList.size(); i++) {
            if (employeeArrayList.get(i).getIsChecked()==1){
                sb1.append(employeeArrayList.get(i).getFname()+" "+employeeArrayList.get(i).getLname());
                sb1.append(",");
            }
        }

        int id = sharedPrefManager.getIntegerByKey("assessmmentUniqID");
        int propertyID = databaseHelper.selectIds(id).getProprtyId();
        int deptID = databaseHelper.selectIds(id).getDeptid();
        int trainer = databaseHelper.selectIds(id).getTrainerId();
        int assementTitleId = databaseHelper.selectIds(id).getAssessmentTitleId();
        int userId = SessionClass.getInstance().getUser(this).getId();
        int companyId =SessionClass.getInstance().getUser(this).getCompanyId();
        String deptName =databaseHelper.selectDeptName(deptID);
        String assessmentName =  databaseHelper.selectAssesmentTitle(assementTitleId);
        String propertyName = databaseHelper.selectPropertyName(propertyID);
        String trainername  = databaseHelper.selectTrainer(trainer);

        final JSONObject dataObj = new JSONObject();
        JSONObject catObj = new JSONObject();
        JSONObject roleObj = new JSONObject();
        JSONObject generalInfoObj = new JSONObject();
        try {
            dataObj.put("assessmentUniqueId", id);

            dataObj.put("companyId",companyId );
            dataObj.put("userId",userId);
            dataObj.put("departmentName", deptName);
            dataObj.put("propertyName", propertyName);
            dataObj.put("assessmentTitle",assessmentName);
            dataObj.put("trainerName", trainername);
            dataObj.put("status", status);
            dataObj.put("score", calculateTotalScore());
            dataObj.put("category_scores", catObj);
//Category Scores
            for (int i = 0; i < getArrayList.size(); i++) {
               // catObj.put(getArrayList.get(i).getCategoryname(), getArrayList.get(i).getCategoryScore());
                catObj.put(getArrayList.get(i).getCategory_key(), getArrayList.get(i).getCategoryScore());
            }
            dataObj.put("roles", roleObj);
            for (int i = 0; i < databaseHelper.selectRoles().size(); i++) {
                roleObj.put(databaseHelper.selectRoles().get(i).getRoleKey(), databaseHelper.selectRoles().get(i).getName());
            }
            dataObj.put("propertyId", propertyID);
            dataObj.put("assessmetId", assementTitleId);
            dataObj.put("departmentId", deptID);
            dataObj.put("employees", sb1);
            dataObj.put("general_infromations", generalInfoObj);


            if (Saved_general_infromations!=null){


                if (Saved_general_infromations.getRoom() == null && Saved_general_infromations.getMealPeriod() == null) {
                    generalInfoObj.put("room", "N/A");
                    generalInfoObj.put("meal_period", "N/A");
                }
                generalInfoObj.put("room", Saved_general_infromations.getRoom());
                generalInfoObj.put("meal_period", Saved_general_infromations.getMealPeriod());
            }
          //  for (int i = 0; i < general_infromationsArrayList.size(); i++) {
                 if (general_infromationsArrayList!=null){
                     if (general_infromationsArrayList.get(0).getRoom() == null && general_infromationsArrayList.get(0).getMealPeriod() == null) {
                         generalInfoObj.put("room", "");
                         generalInfoObj.put("meal_period", "");
                     } if (general_infromationsArrayList.get(0).getRoom()!=0){
                         generalInfoObj.put("room", general_infromationsArrayList.get(0).getRoom());
                     }
                     else {
                         generalInfoObj.put("room", 0);
                     }
                   //  generalInfoObj.put("room", general_infromationsArrayList.get(0).getRoom());

                     generalInfoObj.put("meal_period", general_infromationsArrayList.get(0).getMealPeriod());
                 } else {
                     dataObj.put("general_infromations","N/A");
                 }


         /*   }*/



            JSONArray QuestionResponse = new JSONArray();
            dataObj.put("responseObject", QuestionResponse);

            for (int i = 0; i < QuestionList.size(); i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("questionId", QuestionList.get(i).getId());

                if (QuestionList.get(i).getSelection() == null) {
                    String selections = QuestionList.get(i).setSelection("");
                    jsonObject.put("selection", selections);
                }
                jsonObject.put("selection", QuestionList.get(i).getSelection());


                if (QuestionList.get(i).getComment() == null) {
                    String cmt = QuestionList.get(i).setComment("");
                    jsonObject.put("comment", cmt);
                }
                jsonObject.put("comment", QuestionList.get(i).getComment());

                if (QuestionList.get(i).getImgId() == null) {
                    String img = QuestionList.get(i).setImgId("");
                    jsonObject.put("imgId", img);
                }
                jsonObject.put("imgId", QuestionList.get(i).getImgId());
                jsonObject.put("questionText", QuestionList.get(i).getQuestion());
                QuestionResponse.put(jsonObject);
             /*   databaseHelper.insertAssessmentQuestionsResponse(new QuestionsResult(QuestionList.get(i).getId(),QuestionList.get(i).getQuestion(),QuestionList.get(i).getSelection()
                ,QuestionList.get(i).getComment(),QuestionList.get(i).getImgId(),QuestionList.get(i).getResponseTblId()));*/


            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        Log.e("saveData", "" + dataObj);


        if (checkConnection()){
            return dataObj.toString();
        }
        else {
            /*try {
                JSONObject jsonObject  = new JSONObject(dataObj.toString());
                int assessment_UniqueId =  jsonObject.getInt("assessmentUniqueId");
                int assessment_companyId =  jsonObject.getInt("companyId");
                int assessment_userId =  jsonObject.getInt("userId");
                int assessment_propertyId =  jsonObject.getInt("propertyId");
                int assessment_assessmetId =  jsonObject.getInt("assessmetId");
                int assessment_departmentId =  jsonObject.getInt("departmentId");
                String assessment_departmentName =  jsonObject.getString("departmentName");
                String assessment_propertyName =  jsonObject.getString("propertyName");
                String assessment_trainerName =  jsonObject.getString("trainerName");
                String assessment_assessmentTitle=  jsonObject.getString("assessmentTitle");
                String assessment_status =  jsonObject.getString("status");
                int assessment_score =  jsonObject.getInt("score");
                String assessment_employees = jsonObject.getString("employees");
                String assessment_category_scores = String.valueOf(jsonObject.get("category_scores"));
                String assessment_roles = jsonObject.getString("roles");
                String assessment_general_infromations=  jsonObject.getString("general_infromations");

                databaseHelper.insertAssessmentData(new assessmentSavedData(assessment_UniqueId,assessment_companyId,assessment_userId,assessment_propertyId,assessment_assessmetId,assessment_departmentId,
                        assessment_departmentName,assessment_propertyName,assessment_trainerName,assessment_assessmentTitle,assessment_status,
                        assessment_score,assessment_category_scores,assessment_roles,assessment_employees,assessment_general_infromations,""));
                JSONArray ResponseObj = jsonObject.getJSONArray("responseObject");{
                    for (int i =0;i<ResponseObj.length();i++){
                        JSONObject jsonObject1 = ResponseObj.getJSONObject(i);

                     *//* for (int k =0 ;k <catList.size();k++){
                          CategoryModel categoryModel = new CategoryModel();
                       categoryModel.setKey(catList.get(k).getKey());
                       categoryModel.setValue(catList.get(k).getValue());
                       categoryModel.setYesCount(catList.get(k).getYesCount());
                       categoryModel.setNoCount(catList.get(k).getNoCount());


                      }
*//*

                        databaseHelper.insertAssessmentQuestionsResponse(
                                jsonObject1.getInt("questionId"),
                                jsonObject1.getString("selection"),
                                jsonObject1.getString("comment"),
                                jsonObject1.getString("imgId"),
                                jsonObject1.getString("questionText"),
                                assessment_UniqueId);
                    Log.e("JSONNN",""+assessment_UniqueId);
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }*/
            return "";
        }


    }

    public String updatedData(String updatedStatus) {
        //int AssessmentTblId = sharedPrefManager.getIntegerByKey("AssessmentTblId");
        int AssessmentTblId = sharedPrefManager.getIntegerByKey("assessmentID");
        StringBuilder sb1 = new StringBuilder();

        for (int i = 0; i < employeeArrayList.size(); i++) {
            if (employeeArrayList.get(i).getIsChecked()==1){
              //  sb1.append(employeeArrayList.get(i).getId());
             sb1.append(employeeArrayList.get(i).getFname()+" "+employeeArrayList.get(i).getLname());

                sb1.append(",");
            }
             sharedPrefManager.setStringForKey(employeeArrayList.toString(),"updatedEmployee");
        }


        final JSONObject dataObj = new JSONObject();
        JSONObject catObj = new JSONObject();

        JSONObject generalInfoObj = new JSONObject();
        try {
            dataObj.put("assessmentTblId", AssessmentTblId);
            dataObj.put("userId", SessionClass.getInstance().getUser(this).getId());
            dataObj.put("score", calculateUpdatedScore());
            dataObj.put("status", updatedStatus);
            dataObj.put("category_scores", catObj);
            for (int i = 0; i < updatedgetArrayList.size(); i++) {
                //   catObj.put(updatedgetArrayList.get(i).getCategoryname(), updatedgetArrayList.get(i).getCategoryScore());
                catObj.put(updatedgetArrayList.get(i).getCategory_key(), updatedgetArrayList.get(i).getCategoryScore());
            }
            dataObj.put("employees", sb1);
            dataObj.put("general_infromations", generalInfoObj);
            if (Saved_general_infromations != null) {
                if (Saved_general_infromations.getRoom() == null && Saved_general_infromations.getMealPeriod() == null) {
                    generalInfoObj.put("room", "N/A");
                    generalInfoObj.put("meal_period", "N/A");
                }
                if (Saved_general_infromations.getRoom() != 0) {
                    generalInfoObj.put("room", Saved_general_infromations.getRoom());
                } else {
                    generalInfoObj.put("room", 0);
                }
                generalInfoObj.put("meal_period", Saved_general_infromations.getMealPeriod());
            }
            if (general_infromationsArrayList != null)
            {
                if (!general_infromationsArrayList.isEmpty()){
                    if (general_infromationsArrayList.get(0).getRoom() == null && general_infromationsArrayList.get(0).getMealPeriod() == null) {
                        generalInfoObj.put("room", "N/A");
                        generalInfoObj.put("meal_period", "N/A");
                    }
                    generalInfoObj.put("room", general_infromationsArrayList.get(0).getRoom());
                    generalInfoObj.put("meal_period", general_infromationsArrayList.get(0).getMealPeriod());
                }
            }
            else {
                dataObj.put("general_infromations","N/A");
            }

            JSONArray QuestionResponse = new JSONArray();
            dataObj.put("responseObject", QuestionResponse);
            if (savedList.size() != 0) {
                for (int i = 0; i < savedList.size(); i++) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("responseTblId", savedList.get(i).getResponseTblId());
                    jsonObject.put("selection", savedList.get(i).getSelection());
                    jsonObject.put("comment", savedList.get(i).getComment());
                    jsonObject.put("imgId", savedList.get(i).getImgId());
                    QuestionResponse.put(jsonObject);

                }
            }


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        Log.e("updatedData", "" + dataObj);
        if (checkConnection()){
            return dataObj.toString();
        }
        else {
            try {
                JSONObject jsonObject  = new JSONObject(dataObj.toString());

                String assessment_status =  jsonObject.getString("status");
                int assessment_score =  jsonObject.getInt("score");
                String assessment_employees = jsonObject.getString("employees");
                String assessment_category_scores = String.valueOf(jsonObject.get("category_scores"));
                String assessment_general_infromations=  jsonObject.getString("general_infromations");
                int id  = jsonObject.getInt("assessmentTblId");
                databaseHelper.updateAssessmentData(id,assessment_status,assessment_score,assessment_category_scores,assessment_employees,assessment_general_infromations);

                JSONArray ResponseObj = jsonObject.getJSONArray("responseObject");{
                    for (int i =0;i<ResponseObj.length();i++){

                        JSONObject jsonObject1 = ResponseObj.getJSONObject(i);
                     databaseHelper.updateAssessmentQuestionsResponse(id,jsonObject1.getString("selection"),
                             jsonObject1.getString("comment"), jsonObject1.getString("imgId"));

                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return "";
        }
    }

    /**
     * Save API Call
     * When User directly submit assessment without saving
     *
     * @param status
     */
    public void sendSaveData_Submitted(String status) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://ec2-3-92-174-152.compute-1.amazonaws.com:4554/standardyze/api/save/assessment",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response", "" + response);
                        Intent in = new Intent(summary.this, MainActivity.class);
                        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(in);
                        //submitDialog(R.string.alert_msg_submitted_response);
                       /* startActivity(new Intent(summary.this, MainActivity.class));
                        finish();*/
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", "" + error);
                //  showDialogAlert(R.string.alert_msg_saved_error_response);
            }

        }
        ) {


            @Override
            public byte[] getBody() throws AuthFailureError {
               if (saveData(status)!=null){
                   return saveData(status).getBytes();
               }
               else {
                   return null;
               }
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                6000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
//        //Adding request to the queue
        newRequestQueue.add(stringRequest);

    }


    /**
     * When user only save data of assessment
     *
     * @param status
     */
    public void sendSaveDataToServer(String status) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://ec2-3-92-174-152.compute-1.amazonaws.com:4554/standardyze/api/save/assessment",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response", "" + response);
                        Intent in = new Intent(summary.this, MainActivity.class);
                        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(in);
                   //     Alert_dialoge(R.string.datasaved);
                      //  showDialogAlert(R.string.alert_msg_save_response);
                       /* startActivity(new Intent(summary.this, MainActivity.class));
                        finish();*/
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", "" + error);
                //  showDialogAlert(R.string.alert_msg_saved_error_response);
            }

        }
        ) {


            @Override
            public byte[] getBody() throws AuthFailureError {
                if (saveData(status)!=null){
                    return saveData(status).getBytes();
                }
                else {
                    return null;
                }



            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                6000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
//        //Adding request to the queue
        newRequestQueue.add(stringRequest);

    }


    /**
     * Update API
     *
     * @param uStatus
     */
    public void sendUpdatedDataToServer(String uStatus) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, "http://ec2-3-92-174-152.compute-1.amazonaws.com:4554/standardyze/api/update/assessment", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent in = new Intent(summary.this, MainActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(in);

                Log.e("updated Response", "" + response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {

                return updatedData(uStatus).getBytes();

            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                6000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
//        //Adding request to the queue;
        newRequestQueue.add(stringRequest);

    }


    @OnClick(R.id.save_btn)
    public void setSave_btn() {
        if (checkConnection()){
            //  showSummaryActionbar("Summary", true, false);
            //showActionBar("Summary", null, false, false, false, true);


            if (status != null) {
                if (status.equalsIgnoreCase(AppConstants.SAVED_TO_SERVER)) {

          /*      for (int i = 0; i < savedList.size(); i++ ) {
                    if (!savedList.get(i).getSelection().equalsIgnoreCase(" ")) {
                        showDialogAlert(R.string.alert_msg_information);
                        break;
                    }
                    else {*/
                    submitDialog(R.string.alert_msg_are_you_sure_to_submit,AppConstants.SAVED_TO_SERVER);

                    // break;
                }
                if (status.equalsIgnoreCase("Submitted")) {

                }

            }
            if (QuestionList != null) {
              /*
                for (int i = 0; i < QuestionList.size(); i++) {
                    if (QuestionList.get(i).getSelection().equalsIgnoreCase(" ")) {

                        showDialogAlert(R.string.alert_msg_information);
                       break;
                    } else {*/
                        savedDataDialog(R.string.alert_msg_are_you_sure_to_save);



                   /*     break;
                    }*/
              //  }
            }

        }
        else {
            //updatedData("DBSaved");
            saveData("DBSaved");
          //  Alert_dialoge(R.string.datasaved);
        }


    }

    @OnClick(R.id.submit_btn)
    public void setSubmit_btn() {
        if (checkConnection()){
      /*  save_btn.setVisibility(View.GONE);
        submit_btn.setVisibility(View.GONE);*/
      //  close_btn.setVisibility(View.VISIBLE);
        if (status == null) {
            for (int i = 0; i < QuestionList.size(); i++) {
                if (QuestionList.get(i).getSelection().equalsIgnoreCase(" ")) {
                    showDialogAlert(R.string.alert_msg_information);
                    break;
                } else {
                  //  sendSaveData_Submitted("Submitted");
                    Alert_dialoge(R.string.alert_msg_are_you_sure_to_submit, AppConstants.SUBMITTED_TO_SERVER);
                    break;
                }
            }
        } else if (status.equalsIgnoreCase(AppConstants.SAVED_TO_SERVER)) {
            //sendUpdatedDataToServer("Submitted");
            submitDialog(R.string.alert_msg_are_you_sure_to_submit,AppConstants.SUBMITTED_TO_SERVER);
        }

        }
        else {
            showDialogAlert(R.string.checkNetwork);
        }
    }

    @OnClick(R.id.close_btn)
    public void setClose_btn() {
        startActivity(new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        // showActionBar("Summary", null, false, false, false, true);
    }

    public int calculateTotalScore() {
        double totalScore = 0;
        double totalNumOfYes = 0;
        double totalNumOfNo = 0;
        double totalYesNO;
        if (QuestionList != null) {
            for (int i = 0; i < QuestionList.size(); i++) {

                if (QuestionList.get(i).getSelection() == null) {
                    totalNumOfYes = 0;
                    totalNumOfNo = 0;
                } else if (QuestionList.get(i).getSelection().equalsIgnoreCase("YES")) {
                    ++totalNumOfYes;
                    // Log("totalYes", "" + totalNumOfYes);
                } else if (QuestionList.get(i).getSelection().equalsIgnoreCase("NO")) {
                    ++totalNumOfNo;
                    // Log("totalNo", "" + totalNumOfNo);
                }
                totalYesNO = totalNumOfYes + totalNumOfNo;
                totalScore = totalNumOfYes / totalYesNO * 100;
               //  Log("totalScore", "" + totalScore);

            }
        }
        sharedPrefManager.setIntegerForKey((int) Double.parseDouble(new DecimalFormat("##").format(totalScore)), "totalScore");
        return ((int)Double.parseDouble(new DecimalFormat("##").format(totalScore)));
       // return (totalScore);

    }

    public int calculateUpdatedScore() {
        double totalScore = 0;
        double totalNumOfYes = 0;
        double totalNumOfNo = 0;
        double totalYesNO;
        if (savedList != null)
            for (int i = 0; i < savedList.size(); i++) {
                if (savedList.get(i).getSelection().equalsIgnoreCase("YES")) {
                    ++totalNumOfYes;
                    //  Log("totalYes", "" + totalNumOfYes);
                }
                if (savedList.get(i).getSelection().equalsIgnoreCase("NO")) {
                    ++totalNumOfNo;
                    //   Log("totalNo", "" + totalNumOfNo);
                }
                totalYesNO = totalNumOfYes + totalNumOfNo;
                totalScore = totalNumOfYes / totalYesNO * 100;
                // Log("totalScore", "" + totalScore);

            }
        sharedPrefManager.setIntegerForKey((int) Double.parseDouble(new DecimalFormat("##").format(totalScore)), "totalScore");
            Log.e("Summary Score",""+(int) Double.parseDouble(new DecimalFormat("##").format(totalScore)));
        return (int)Double.parseDouble(new DecimalFormat("##").format(totalScore));
    }

    public double calculateCategoriesTotalScore() {
//        double totalCatScore = 0;
//        double totalCatYes = 0;
//        double totalCatNo = 0;
//        double totalCatYESNO = 0;

        float totalScore = 0f;
        float yesNo = 0f;
        float yes = 0f;

        for (CategoryModel categoryItem : catList) {
            // Log(categoryItem.toString());
            for (QuestionsResult questionItem : QuestionList) {
                //     Log(questionItem.getQuestion());
                for (String key : questionItem.getCategories().keySet()) {
                    if (key.equalsIgnoreCase(categoryItem.getKey())) {
                        if (questionItem.getCategories().get(key).equalsIgnoreCase("Yes")) {
                            String selected = questionItem.getSelection();
                            if (selected.equalsIgnoreCase("Yes")) {
                                ++yes;
                            }

                            if (selected.equalsIgnoreCase("Yes") || selected.equalsIgnoreCase("No")) {
                                ++yesNo;
                            }
                        }
                    }
                }
            }

            if (yesNo > 0 || yes > 0) {
                totalScore = (yes / yesNo) * 100;
            }

             // Log("=========================  Score  =============================");
            //  Log.e("Total score for " + categoryItem.getKey() + " : " + totalScore,"");
            categories_score categoryScore = new categories_score();
            for (int i=0;i <databaseHelper.selectCategories().size();i++){
                if (databaseHelper.selectCategories().get(i).getCategoryKey().equalsIgnoreCase(categoryItem.getKey())){
                    Log.e("keyyyyyyyyy",""+ databaseHelper.selectCategories().get(i).getName());
                    categoryScore.setCategoryname(databaseHelper.selectCategories().get(i).getName());

                    categoryScore.setCategory_key(databaseHelper.selectCategories().get(i).getCategoryKey());
                }
            }
            // categoryScore.setCategoryname(categoryItem.getKey());
           // categoryScore.setCategoryname(categoryItem.getKey());
            categoryScore.setCategoryScore((int)totalScore);
            arrayList.add(categoryScore);

        }
        return totalScore;
    }

    public HashMap<String, Integer> calculateCategoriesTotalScore2() {


        float totalScore = 0f;
        float yesNo = 0f;
        float yes = 0f;

        HashMap<String, Integer> returnParams = new HashMap<>();
        if (catList != null)
            for (CategoryModel categoryItem : catList) {
                //   Log(categoryItem.toString());
                for (QuestionsResult questionItem : QuestionList) {
                    if (QuestionList != null)
                        //     Log(questionItem.getQuestion());
                        for (String key : questionItem.getCategories().keySet()) {
                            if (key.equalsIgnoreCase(categoryItem.getKey())) {
                                if (questionItem.getCategories().get(key).equalsIgnoreCase("Yes")) {
                                    String selected = questionItem.getSelection();
                                    if (selected != null) {


                                        if (selected.equalsIgnoreCase("Yes")) {
                                            ++yes;
                                        }

                                        if (selected.equalsIgnoreCase("Yes") || selected.equalsIgnoreCase("No")) {
                                            ++yesNo;
                                        }
                                    }

                                }
                            }
                        }
                }

                if (yesNo > 0 || yes > 0) {
                    totalScore = (yes / yesNo) * 100;
                }
                //   Log("=========================  Score  =============================");
                String decimalFormat = new DecimalFormat("##.##").format(totalScore);
                //   Log("Total score for " + categoryItem.getKey() + " : " + totalScore);
                returnParams.put(categoryItem.getKey(), (int) totalScore);
                categories_score categoryScore = new categories_score();
                for (int i=0;i <databaseHelper.selectCategories().size();i++){
                    if (databaseHelper.selectCategories().get(i).getCategoryKey().equalsIgnoreCase(categoryItem.getKey())){
                        Log.e("keyyyyyyyyy",""+ databaseHelper.selectCategories().get(i).getName());
                        categoryScore.setCategoryname(databaseHelper.selectCategories().get(i).getName());
                        categoryScore.setCategory_key(databaseHelper.selectCategories().get(i).getCategoryKey());
                    }
                }
                // categoryScore.setCategoryname(categoryItem.getKey());
            //    categoryScore.setCategoryname(categoryItem.getKey());
                categoryScore.setCategoryScore((int )(totalScore));
               // categoryScore.setCategoryScore((int)(totalScore));
                arrayList.add(categoryScore);
                getArrayList.add(categoryScore);

            }
        sharedPrefManager.setStringForKey(new Gson().toJson(arrayList),"newCategories");
        Log.e("newCategories",""+new Gson().toJson(arrayList));
        return returnParams;
    }

    public HashMap<String, Integer> calculateUpdatedCategoriesTotalScore2() {
        float totalScore = 0f;
        float yesNo = 0f;
        float yes = 0f;

        HashMap<String, Integer> returnParams = new HashMap<>();
        if (catList != null)
            for (CategoryModel categoryItem : catList) {
                //Log(categoryItem.toString());
                for (getAssessmentResponseObj questionItem : savedList) {
                    //    Log(questionItem.getQuestionText());

                    for (String key : questionItem.getCategories().keySet()) {
                        questionItem.setQuestionsCategories(String.valueOf(questionItem.getCategories().keySet()));
                        if (key.equalsIgnoreCase(categoryItem.getKey())) {
                            if (questionItem.getCategories().get(key).equalsIgnoreCase("Yes")) {
                                String selected = questionItem.getSelection();
                                if (selected.equalsIgnoreCase("Yes")) {
                                    ++yes;
                                }

                                if (selected.equalsIgnoreCase("Yes") || selected.equalsIgnoreCase("No")) {
                                    ++yesNo;
                                }
                            }
                        }
                    }
                }

                if (yesNo > 0 || yes > 0) {
                    totalScore = (yes / yesNo) * 100;
                }
                //Log("=========================  Score  =============================");
                String decimalFormat = new DecimalFormat("##.##").format(totalScore);
                 Log.e("Total score for " + categoryItem.getKey(), " : " + (int)totalScore);
                returnParams.put(categoryItem.getKey(),(int) (Double.parseDouble(new DecimalFormat("##").format(totalScore))));
                categories_score categoryScore = new categories_score();
                for (int i=0;i <databaseHelper.selectCategories().size();i++){
                    if (databaseHelper.selectCategories().get(i).getCategoryKey().equalsIgnoreCase(categoryItem.getKey())){
                       Log.e("keyyyyyyyyy",""+ databaseHelper.selectCategories().get(i).getName());
                       categoryScore.setCategoryname(databaseHelper.selectCategories().get(i).getName());
                        categoryScore.setCategory_key(databaseHelper.selectCategories().get(i).getCategoryKey());
                    }
                }

               // categoryScore.setCategoryname(categoryItem.getKey());
                categoryScore.setCategoryScore((int)(Double.parseDouble(new DecimalFormat("##").format(totalScore))));
                Log.e("formatter",""+((int)Double.parseDouble(new DecimalFormat("##").format(totalScore))));

                arrayList.add(categoryScore);
                updatedgetArrayList.add(categoryScore);

            }
        sharedPrefManager.setStringForKey(new Gson().toJson(arrayList),"savedCategories");
            Log.e("Arrayklis",""+new Gson().toJson(arrayList));
        return returnParams;
    }

    public void Alert_dialoge(int error,String s) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_alert_dialog);
        dialog.setCancelable(false);
        CustomTextViewBook errormsg = (CustomTextViewBook) dialog.findViewById(R.id.alertText);
        LinearLayout linearLayout = (LinearLayout) dialog.findViewById(R.id.yesNoLayout);
        errormsg.setText(error);
        Button button = (Button) dialog.findViewById(R.id.ok_btn);
        Button yes = (Button) dialog.findViewById(R.id.yes_btn);
        Button no_btn = (Button) dialog.findViewById(R.id.no_btn);
        linearLayout.setVisibility(View.VISIBLE);
        button.setVisibility(View.GONE);
        errormsg.setText(error);

        yes.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                sendSaveData_Submitted(s);
              //  dialog.dismiss();

                dialog.dismiss();
            }

        });
        no_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                save_btn.setVisibility(View.VISIBLE);
                submit_btn.setVisibility(View.VISIBLE);
            }
        });
        dialog.show();

    }

    public void showDialogAlert(int error) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_alert_dialog);
        CustomTextViewBook errormsg = (CustomTextViewBook) dialog.findViewById(R.id.alertText);
        errormsg.setText(error);
        dialog.setCancelable(false);
        Button button = (Button) dialog.findViewById(R.id.ok_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    public void submitDialog(int error, String s){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_alert_dialog);
        CustomTextViewBook errormsg = (CustomTextViewBook) dialog.findViewById(R.id.alertText);
        LinearLayout linearLayout = (LinearLayout) dialog.findViewById(R.id.yesNoLayout);
        errormsg.setText(error);
        dialog.setCancelable(false);
        Button button = (Button) dialog.findViewById(R.id.ok_btn);
        Button yes = (Button) dialog.findViewById(R.id.yes_btn);
        Button no_btn = (Button) dialog.findViewById(R.id.no_btn);
        linearLayout.setVisibility(View.VISIBLE);
        button.setVisibility(View.GONE);
        yes.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                sendUpdatedDataToServer(s);
               // dialog.dismiss();

                dialog.dismiss();
            }

        });
        no_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                save_btn.setVisibility(View.VISIBLE);
                submit_btn.setVisibility(View.VISIBLE);
            }
        });
        dialog.show();
    }


    public void savedDataDialog(int error){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_alert_dialog);
        CustomTextViewBook errormsg = (CustomTextViewBook) dialog.findViewById(R.id.alertText);
        LinearLayout linearLayout = (LinearLayout) dialog.findViewById(R.id.yesNoLayout);
        errormsg.setText(error);
        dialog.setCancelable(false);
        Button button = (Button) dialog.findViewById(R.id.ok_btn);
        Button yes = (Button) dialog.findViewById(R.id.yes_btn);
        Button no_btn = (Button) dialog.findViewById(R.id.no_btn);
        linearLayout.setVisibility(View.VISIBLE);
        button.setVisibility(View.GONE);
        yes.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                sendSaveDataToServer(AppConstants.SAVED_TO_SERVER);
                save_btn.setEnabled(false);
                //  save_btn.setAlpha(0.2f);
               // save_btn.setText(R.string.saved);

                dialog.dismiss();
            }

        });
        no_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                save_btn.setEnabled(true);
              //  save_btn.setAlpha(0.9f);
               // save_btn.setText(R.string.save);
            }
        });
        dialog.show();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public boolean checkConnection() {
        boolean flag = false;
        if (connectionDetector.isNetworkAvailable(this)) {
            flag = true;
        } else {
            flag = false;
        }
        return flag;



    }
}
