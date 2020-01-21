package com.standardyze.activites;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.core.content.FileProvider;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.standardyze.App.SessionClass;
import com.standardyze.R;
import com.standardyze.adapters.newResponseAdapter;
import com.standardyze.adapters.pdf_categories_adapter;
import com.standardyze.adapters.pdf_employee_adapter;
import com.standardyze.adapters.pdf_newQuestions_adapter;
import com.standardyze.adapters.pdf_responses_adapter;
import com.standardyze.adapters.pdf_roles_adapter;
import com.standardyze.adapters.roles_adapter;
import com.standardyze.baseActivity;
import com.standardyze.customViews.CustomTextViewBold;
import com.standardyze.customViews.CustomTextViewMedium;
import com.standardyze.models.pdfCategoryScores;
import com.standardyze.utils.DatabaseHelper;
import com.standardyze.utils.SharedPrefManager;
import com.standardyze.wrapper.Employee;
import com.standardyze.wrapper.General_infromations;
import com.standardyze.wrapper.QuestionsResult;
import com.standardyze.wrapper.categories_score;
import com.standardyze.wrapper.getAssessmentResponseObj;
import com.standardyze.wrapper.roles;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class pdfActivity extends baseActivity {
    Display mDisplay;
    String imagesUri;
    String path;
    Bitmap b;
    int totalHeight;
    int totalWidth;
    public static final int READ_PHONE = 110;
    String file_name = "Standardyze";
    File myPath;
    Button bt_take_screenshot;
    pdf_responses_adapter pdf_responses_adapter;
    pdf_newQuestions_adapter questionsAdapter;
    newResponseAdapter newResponseAdapte;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.recycler_view_employee)
    RecyclerView recyclerViewEmployee;
    pdf_employee_adapter pdfEmployeeAdapter;
    LinearLayoutManager linearLayoutManager;
    LinearLayoutManager linearLayoutManager2;
    @BindView(R.id.trainer_name)
    CustomTextViewMedium trainerName;
    @BindView(R.id.mealperiod)
    CustomTextViewMedium mealPeriod;
    @BindView(R.id.departmenr_txt)
    CustomTextViewMedium departmenr_txt;
    @BindView(R.id.dateTime)
    CustomTextViewMedium dateTime;
    @BindView(R.id.room_number)
    CustomTextViewMedium roomNumber;
    SharedPrefManager sharedPrefManager;
    DatabaseHelper databaseHelper;
    @BindView(R.id.score)
    CustomTextViewMedium scoreText;

    @BindView(R.id.recycler_view_score)
    RecyclerView category_recyclerView;

    @BindView(R.id.recycler_view_roles)
    RecyclerView recyclerView_roles;
    pdf_categories_adapter pdf_categories_adapter;
    ArrayList<pdfCategoryScores> pdfCategoryScoresArrayList;
    ArrayList<QuestionsResult> questionsResultArrayList;
    ArrayList<getAssessmentResponseObj> savedList;
    ArrayList<QuestionsResult> list;
    private LinearLayoutManager linearLayoutManager3;
    private GridLayoutManager gridLayoutManager;
    ArrayList<Employee> employeeArrayList;
    Employee employee;
    roles_adapter adapter;
    private LinearLayoutManager linearLayoutManager4;
    private GridLayoutManager gridLayoutManager2;
    ArrayList<roles> rolesArrayList;
    ArrayList<roles> updatedRolesList;
    ArrayList<roles> submittedRolesArraylist;
    pdf_roles_adapter pdf_roles_adapter;

    ArrayList<Employee> updatedEmployeeList;
    ArrayList<Employee> submittedEmployeeList;
    ArrayList<categories_score> savedCategoryList;
    ArrayList<categories_score> categories_scoresList;
    ArrayList<General_infromations> general_infromationsArrayList;
    General_infromations general_infromations;
    General_infromations general_infromationsSubmitted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        ButterKnife.bind(this);
        // getSupportActionBar().hide();
        showSummaryActionbar("", true, false);
        //  WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //   mDisplay = wm.getDefaultDisplay();

        init();

    }

    private void init() {
        questionsResultArrayList = new ArrayList<>();
        employeeArrayList = new ArrayList<>();
        updatedRolesList = new ArrayList<>();
        submittedRolesArraylist = new ArrayList<>();
        updatedEmployeeList = new ArrayList<>();
        submittedEmployeeList = new ArrayList<>();

        savedList = new ArrayList<>();
        rolesArrayList = new ArrayList<>();
        savedCategoryList = new ArrayList<>();
        categories_scoresList = new ArrayList<>();
        general_infromationsArrayList = new ArrayList<>();
        roles roles = new roles();
        list = new ArrayList<>();
        Gson gson = new Gson();
        Type type2 = new TypeToken<List<getAssessmentResponseObj>>() {
        }.getType();
        Type type3 = new TypeToken<List<QuestionsResult>>() {
        }.getType();
        Type type = new TypeToken<List<roles>>() {

        }.getType();
        Type type1 = new TypeToken<List<roles>>() {

        }.getType();
        Type type5 = new TypeToken<List<roles>>() {

        }.getType();

        Type type4 = new TypeToken<List<categories_score>>() {
        }.getType();
        Type ty = new TypeToken<List<General_infromations>>() {

        }.getType();
        Type ty2 = new TypeToken<List<Employee>>() {

        }.getType();
        pdfCategoryScoresArrayList = new ArrayList<>();
        sharedPrefManager = new SharedPrefManager(this);
        databaseHelper = new DatabaseHelper(this);

        savedList = gson.fromJson(sharedPrefManager.getStringByKey("savedList"), type2);
        questionsResultArrayList = gson.fromJson(sharedPrefManager.getStringByKey("newList"), type3);
        updatedRolesList = gson.fromJson(sharedPrefManager.getStringByKey("updatedrolesArrayList"), type);
        rolesArrayList = gson.fromJson(sharedPrefManager.getStringByKey("rolesArrayList"), type1);
        submittedRolesArraylist = gson.fromJson(sharedPrefManager.getStringByKey("submittedRolesArraylist"), type5);
        savedCategoryList = gson.fromJson(sharedPrefManager.getStringByKey("savedCategories"), type4);
        categories_scoresList = gson.fromJson(sharedPrefManager.getStringByKey("newCategories"), type4);
        employeeArrayList = gson.fromJson(sharedPrefManager.getStringByKey("newEmployees"), ty2);


        /**
         * General Information Data
         */
        if (sharedPrefManager.getStringByKey("SavedGI") != null) {
            if (sharedPrefManager.getStringByKey("SavedGI").equalsIgnoreCase("N/A")) {
                roomNumber.setText("N/A");
                mealPeriod.setText("N/A");
            } else {
                general_infromations = gson.fromJson(sharedPrefManager.getStringByKey("SavedGI"), General_infromations.class);
            }
        }


        if (sharedPrefManager.getStringByKey("SubmittedGI") != null) {
            if (sharedPrefManager.getStringByKey("SubmittedGI").equalsIgnoreCase("N/A") || sharedPrefManager.getStringByKey("SubmittedGI").equalsIgnoreCase("NA")) {
                roomNumber.setText("N/A");
                mealPeriod.setText("N/A");
            } else {
                general_infromationsSubmitted = gson.fromJson(sharedPrefManager.getStringByKey("SubmittedGI"), General_infromations.class);
            }
        }

        if (sharedPrefManager.getStringByKey("GIData") != null) {
            if (sharedPrefManager.getStringByKey("GIData").equalsIgnoreCase("N/A")) {
                roomNumber.setText("N/A");
                mealPeriod.setText("N/A");
            } else {
                general_infromationsArrayList = gson.fromJson(sharedPrefManager.getStringByKey("GIData"), ty);
            }
        }


        ;
        /**
         * Employees
         */
        if (sharedPrefManager.getStringByKey("savedEmployees") != null) {
            sharedPrefManager.clearKey("submitted");


            String[] item = sharedPrefManager.getStringByKey("savedEmployees").split(",");
            for (int i = 0; i < item.length; i++) {
                String employeeName = item[i];
                employee = new Employee();
                if (employeeName.contains(" ")) {
                    String[] splittedEmployeeName = employeeName.split(" ");
                    if (splittedEmployeeName.length > 1) {
                        employee.setFname(item[i].split(" ")[0]);
                        employee.setLname(item[i].split(" ")[1]);
                       // updatedEmployeeList.add(employee);

                    }
                    else {
                        //For custom employee
                        employee.setFname(employeeName);
                        employee.setLname("");
                        employee.setIsChecked(1);
                        if (employee.getId()==null){
                            employee.setId(databaseHelper.countEmployeeRowa() + 1);
                            updatedEmployeeList.add(0, employee);
                        }
                    }
                }else {
                    employee.setFname(employeeName);
                    employee.setLname("");
                    employee.setIsChecked(1);

                }

                int index = updatedEmployeeList.indexOf(employee);
                if (index >=0) {

                    updatedEmployeeList.get(index).setIsChecked(1);
                    Log("index",""+index);
                } else {
                    employee.setId(databaseHelper.countEmployeeRowa() + 1);
                    employee.setIsChecked(1);

                    updatedEmployeeList.add(0, employee);
                }
            }

            if (updatedEmployeeList != null) {
                pdfEmployeeAdapter = new pdf_employee_adapter(updatedEmployeeList, this);
                Log("" + updatedEmployeeList, "updatedEmployeeList");
                recyclerViewEmployee.setAdapter(pdfEmployeeAdapter);
            }
            pdfEmployeeAdapter.notifyDataSetChanged();
        }

        if (sharedPrefManager.getStringByKey("submitted") != null) {
            String[] item = sharedPrefManager.getStringByKey("submitted").split(",");
            for (int i = 0; i < item.length; i++) {
                String employeeName = item[i];
                employee = new Employee();
                if (employeeName.contains(" ")) {
                    String[] splittedEmployeeName = employeeName.split(" ");
                    if (splittedEmployeeName.length > 1) {
                        employee.setFname(item[i].split(" ")[0]);
                        employee.setLname(item[i].split(" ")[1]);
                        submittedEmployeeList.add(employee);

                    }
                    else {
                        //For custom employee
                        employee.setFname(employeeName);
                        employee.setIsChecked(1);
                        employee.setLname("");
                    }

                }
                else {
                    employee.setFname(employeeName);
                    employee.setLname("");
                    employee.setIsChecked(1);

                }

                int index = submittedEmployeeList.indexOf(employee);
                if (index >= 0) {

                    submittedEmployeeList.get(index).setIsChecked(1);
                    Log("index",""+index);
                } else {
                    employee.setId(databaseHelper.countEmployeeRowa() + 1);
                    employee.setIsChecked(1);

                    submittedEmployeeList.add(0, employee);
                }

            }

            if (submittedEmployeeList != null) {
                pdfEmployeeAdapter = new pdf_employee_adapter(submittedEmployeeList, this);
                Log("" + submittedEmployeeList, "submittedEmployeeList");
                recyclerViewEmployee.setAdapter(pdfEmployeeAdapter);
            }

            pdfEmployeeAdapter.notifyDataSetChanged();
        }

      /*  if (sharedPrefManager.getStringByKey("newEmployees")!=null){
            for (int i =0; i<employeeArrayList.size();i++){
                if (employeeArrayList.get(i).getIsChecked()==1){

                    Log("Employees",employeeArrayList.get(i).getFname());
                    pdfEmployeeAdapter = new pdf_employee_adapter(employeeArrayList, this);
                    recyclerViewEmployee.setAdapter(pdfEmployeeAdapter);
                }
            }


        }*/

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, READ_PHONE);
            }
        }
        bt_take_screenshot = (Button) findViewById(R.id.bt_take_screenshot);
        bt_take_screenshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_take_screenshot.setVisibility(View.GONE);
                takeScreenShot();
            }
        });
        // scoreText.setText();

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.VERTICAL, false);
        gridLayoutManager2 = new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.VERTICAL, false);

        category_recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLayoutManager(linearLayoutManager2);
        recyclerView.setLayoutManager(linearLayoutManager3);
        recyclerViewEmployee.setLayoutManager(gridLayoutManager);
        recyclerView_roles.setLayoutManager(gridLayoutManager2);

/**
 * Pending data QuestionsList
 */
        if (savedList != null) {
            pdf_responses_adapter = new pdf_responses_adapter(this, savedList);
            recyclerView.setAdapter(pdf_responses_adapter);
        }

        if (questionsResultArrayList != null) {

            questionsAdapter = new pdf_newQuestions_adapter(this, questionsResultArrayList);
            recyclerView.setAdapter(questionsAdapter);

        }


/**
 * Roles
 */
        if (updatedRolesList != null) {
            pdf_roles_adapter = new pdf_roles_adapter(updatedRolesList, this);
            Log("" + updatedRolesList, "updatedRoles");
            recyclerView_roles.setAdapter(pdf_roles_adapter);

        }
        if (rolesArrayList != null) {
            adapter = new roles_adapter(rolesArrayList, this);
            recyclerView_roles.setAdapter(adapter);
        }
        if (submittedRolesArraylist != null) {
            pdf_roles_adapter = new pdf_roles_adapter(submittedRolesArraylist, this);
            Log("" + updatedRolesList, "submittedRolesArraylist");
            recyclerView_roles.setAdapter(pdf_roles_adapter);
        }


/**
 * Categories
 */

        if (savedCategoryList != null) {
            pdf_categories_adapter = new pdf_categories_adapter(this, savedCategoryList);
            category_recyclerView.setAdapter(pdf_categories_adapter);
            sharedPrefManager.clearKey("newCategories");

        }
        if (categories_scoresList != null) {
            pdf_categories_adapter = new pdf_categories_adapter(this, categories_scoresList);
            category_recyclerView.setAdapter(pdf_categories_adapter);
        }

        scoreText.setText(String.valueOf(sharedPrefManager.getIntegerByKey("totalScore"))
                + "%");
        settingGeneralInfoData();
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
                    // Intent in = new Intent(pdfActivity.this, pdfActivity.class);
                    //startActivity(in);
                    takeScreenShot();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public void settingGeneralInfoData() {
        int id = sharedPrefManager.getIntegerByKey("assessmmentUniqID");
      //  int id = sharedPrefManager.getIntegerByKey("assessmentID");
        Log.e("assessmentID",""+id);
        int deptID = databaseHelper.selectIds(id).getDeptid();
        int trainerId = databaseHelper.selectIds(id).getTrainerId();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        String currentDateandTime = sdf.format(new Date());

        //Saved trianerName
       if (sharedPrefManager.getStringByKey("savedTrainerName")!=null){
           trainerName.setText(sharedPrefManager.getStringByKey("savedTrainerName"));
       }
       else
           if (sharedPrefManager.getStringByKey("submittedTrainerName")!=null){
               trainerName.setText(sharedPrefManager.getStringByKey("submittedTrainerName"));
       }
           else {
               trainerName.setText(databaseHelper.selectTrainer(trainerId));
           }

        Log.e("trainerName",""+databaseHelper.selectTrainer(trainerId));
        Log.e("departmenr_txt",""+databaseHelper.selectDeptName(deptID));

        if (sharedPrefManager.getStringByKey("savedDepartmentName")!=null){
            departmenr_txt.setText(sharedPrefManager.getStringByKey("savedDepartmentName"));
        }
        else if (sharedPrefManager.getStringByKey("submittedDepartmentName")!=null){
            departmenr_txt.setText(sharedPrefManager.getStringByKey("submittedDepartmentName"));
        }
        else {
            departmenr_txt.setText(databaseHelper.selectDeptName(deptID));
        }

        dateTime.setText(currentDateandTime);

        //after Saved GI data
        if (general_infromations != null) {
            mealPeriod.setText(general_infromations.getMealPeriod());
            if (general_infromations.getRoom()!=0){
                roomNumber.setText(general_infromations.getRoom().toString());
            }
            else {
                roomNumber.setText("N/A");
            }

        }

        //Submitted GI Data
        if (general_infromationsSubmitted != null) {
            mealPeriod.setText(general_infromationsSubmitted.getMealPeriod());
            if (general_infromationsSubmitted.getRoom()!=0){
                roomNumber.setText(general_infromationsSubmitted.getRoom().toString());
            }
            else {
                roomNumber.setText("N/A");
            }

        }
        if (general_infromationsArrayList.size() != 0) {
            mealPeriod.setText(general_infromationsArrayList.get(general_infromationsArrayList.size()-1).getMealPeriod());
            if (general_infromationsArrayList.get(0).getRoom()!=0){
                roomNumber.setText(general_infromationsArrayList.get(general_infromationsArrayList.size()-1).getRoom().toString());
            }
            else {
                roomNumber.setText("N/A");
            }

        } /*else {
            mealPeriod.setText("N/A");
            roomNumber.setText("N/A");
        }*/

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void takeScreenShot() {

        File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/ScreenShot/");

        if (!folder.exists()) {
            boolean success = folder.mkdir();
        }

        path = folder.getAbsolutePath();
        path = path + "/" + file_name + System.currentTimeMillis() + ".pdf";

        // List<Integer> hights = new ArrayList<>();
        View u = findViewById(R.id.scroll);

        NestedScrollView z = (NestedScrollView) findViewById(R.id.scroll);
        totalHeight = z.getChildAt(0).getHeight();
        totalWidth = z.getChildAt(0).getWidth();

        Log.e("totalHeight--", "" + totalHeight);
        Log.e("totalWidth--", "" + totalWidth);

        //Save bitmap
        String extr = Environment.getExternalStorageDirectory() + "/ScreenShot/";
        File file = new File(extr);
        if (!file.exists())
            file.mkdir();
        String fileName = file_name + ".jpg";
        myPath = new File(extr, fileName);
        imagesUri = myPath.getPath();
        FileOutputStream fos = null;
        b = getBitmapFromView(u, totalHeight, totalWidth);

        try {
            fos = new FileOutputStream(myPath);
            b.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        createPdf();

    }

    public Bitmap getBitmapFromView(View view, int totalHeight, int totalWidth) {

        Bitmap returnedBitmap = Bitmap.createBitmap(totalWidth, totalHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createPdf() {

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(b.getWidth(), b.getHeight(), 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();


        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#ffffff"));
        canvas.drawPaint(paint);


        Bitmap bitmap = Bitmap.createScaledBitmap(b, b.getWidth(), b.getHeight(), true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        document.finishPage(page);
        File filePath = new File(path);
        try {
            document.writeTo(new FileOutputStream(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();
        if (myPath.exists())
            myPath.delete();

        openPdf(path);
    }

    public void openPdf(String path) {
        File file = new File(path);

        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("application/pdf");
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Standardyze");
        //  emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "From My App");
        //   emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        emailIntent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".file_paths", file));
       /* Uri apkURI = FileProvider.getUriForFile(this,getApplicationContext().getPackageName()+".file_paths",file)
        emailIntent.setDataAndType(apkURI, "text/plain");
        emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);*/
        emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        startActivity(Intent.createChooser(emailIntent, "Send mail..."));


    }
}
