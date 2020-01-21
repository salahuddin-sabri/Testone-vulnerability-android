package com.standardyze.activites;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orm.SugarContext;
import com.standardyze.App.SessionClass;
import com.standardyze.MainActivity;
import com.standardyze.R;
import com.standardyze.adapters.checkbox_adapter;
import com.standardyze.adapters.spinnersAdapter.AssessmentTitle_adapter;
import com.standardyze.adapters.spinnersAdapter.departmentSpinner_adapter;
import com.standardyze.adapters.spinnersAdapter.propertySpinner_adapter;
import com.standardyze.adapters.spinnersAdapter.trainers_adapter;
import com.standardyze.baseActivity;
import com.standardyze.models.checkbox_items;
import com.standardyze.models.pdf_roles;
import com.standardyze.networking.WebServiceFactory;
import com.standardyze.utils.AppConstants;
import com.standardyze.utils.DatabaseHelper;
import com.standardyze.utils.SharedPrefManager;
import com.standardyze.wrapper.AssmessmentModel;
import com.standardyze.wrapper.Employee;

import com.standardyze.wrapper.User;
import com.standardyze.wrapper.departmentModel;
import com.standardyze.wrapper.newAssessmentResponse;
import com.standardyze.wrapper.propertyModel;
import com.standardyze.wrapper.roles;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class new_assessment extends baseActivity {

    @BindView(R.id.recycler_view_checkbox)
    RecyclerView recyclerView;
    @BindView(R.id.spinner1)
    Spinner sp_property;
    @BindView(R.id.spinner2)
    Spinner sp_department;
    @BindView(R.id.spinner3)
    Spinner sp_trainer;
    @BindView(R.id.spinner4)
    Spinner sp_assessmentTitle;
    @BindView(R.id.continue_btn)
    Button button;
    @BindView(R.id.rotateloadingLogin)
    RotateLoading rotateLoading;

    LinearLayoutManager linearLayoutManager;
    private ArrayList<propertyModel> propertyLists;
    private ArrayList<departmentModel> departmentLists;
    private ArrayList<roles> rolesArrayList;
    private ArrayList<Employee> trainerArrayList;
    private ArrayList<AssmessmentModel> assmessmentModelArrayList;

    private propertySpinner_adapter propertySpinnerAdapter;
    private departmentSpinner_adapter departmentSpinnerAdapter;
    private checkbox_adapter checkbox_adapter;
    private trainers_adapter trainers_adapter;
    private AssessmentTitle_adapter assesmentTitle_adapter;

    private propertyModel property_Model;
    private departmentModel departmentModel;
    private roles roles;
    AssmessmentModel assmessmentModel;
    Employee employee;
    User userModel;
    checkbox_items items;
    SharedPrefManager sharedPrefManager;

    DatabaseHelper databaseHelper;
    String assesmentKey;
    String assessmentName, departmentName, propertyName;
    ArrayList<roles> selectedRoles;
    int assessmentId, propertyId, trainerId, deptId, assessmentTitleId;
    ArrayList<pdf_roles> pdf_roles1;
    // private String[] property = {"Copley Square Hotel", "Copley Square Hotel", "Copley Square Hotel", "Copley Square Hotel", "Copley Square Hotel", "Copley Square Hotel", "Copley Square Hotel"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_assessment);
        showActionBar("New Assessment", new Intent(this, MainActivity.class), false, false, false, false);
        ButterKnife.bind(this);
        SugarContext.init(this);

        init();
        sharedPrefManager.clearKey("updatedrolesArrayList");
        sharedPrefManager.clearKey("rolesArrayList");
        sharedPrefManager.clearKey("submittedRolesArraylist");


        for (int i = 0; i < databaseHelper.selectRoles().size(); i++) {
            databaseHelper.updateRoles(0, databaseHelper.selectRoles().get(i).getId());
        }
    }


    public void init() {
        databaseHelper = new DatabaseHelper(this);
        sharedPrefManager = new SharedPrefManager(this);
        selectedRoles = new ArrayList<>();
        pdf_roles1 = new ArrayList<>();

        Long tsLong = System.currentTimeMillis() / 1000;
        assessmentId = Integer.parseInt(tsLong.toString());
        sharedPrefManager.setIntegerForKey(assessmentId, "assessmmentUniqID");
        Log("AssesId",""+assessmentId);

        /**
         * Properties
         */
        property_Model = new propertyModel();
        propertyLists = new ArrayList<>();
        propertySpinnerAdapter = new propertySpinner_adapter(getApplicationContext(), propertyLists);
        sp_property.setAdapter(propertySpinnerAdapter);


        /**
         * Departments
         */
        departmentLists = new ArrayList<>();
        departmentModel = new departmentModel();
        sp_department.setBackgroundResource(R.drawable.spinner_back);
        departmentModel.setName("Select a Department");
        departmentSpinnerAdapter = new departmentSpinner_adapter(getApplicationContext(), departmentLists);
        sp_department.setAdapter(departmentSpinnerAdapter);


        /**
         * Trainers
         */
        trainerArrayList = new ArrayList<>();
        employee = new Employee();
        employee.setFname("Select a Trainer");
        employee.setLname("");
        userModel = new User();
        trainers_adapter = new trainers_adapter(getApplicationContext(), trainerArrayList);
        sp_trainer.setAdapter(trainers_adapter);


        /**
         * Assessments Titles
         */
        assmessmentModelArrayList = new ArrayList<>();
        assmessmentModel = new AssmessmentModel();
        assmessmentModel.setTitle("Select a Title");
        assesmentTitle_adapter = new AssessmentTitle_adapter(getApplicationContext(), assmessmentModelArrayList);
        sp_assessmentTitle.setAdapter(assesmentTitle_adapter);


        /**
         * Roles
         */
        roles = new roles();
        rolesArrayList = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.VERTICAL, false);
        checkbox_adapter = new checkbox_adapter(this, rolesArrayList);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(checkbox_adapter);


        if (sharedPrefManager.getIntegerByKey(AppConstants.getDate) == 1570186488) {
            getDropDownData(SessionClass.getInstance().getUser(new_assessment.this).getCompanyId(), 1570186488);
        } else {

            getDropDownData(SessionClass.getInstance().getUser(new_assessment.this).getCompanyId(), sharedPrefManager.getIntegerByKey(AppConstants.getDate));

        }

        //  getDropDownData(SessionClass.getInstance().getUser(new_assessment.this).getCompanyId(), 1573739487);

    }

    /**
     * Getting saveData from Database
     */

    public void getDataFromDB() {
        //Properties
        propertyLists.clear();
        property_Model.setName("Select a Property");
        propertyLists.add(0, property_Model);
        propertyLists.addAll(databaseHelper.selectProperties());
        // propertyLists.add(databaseHelper.selectProperties().lastIndexOf(property_Model)+1,property_Model);
        //  propertyLists.addAll(databaseHelper.selectProperties());
        propertySpinnerAdapter.notifyDataSetChanged();


        sp_property.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sp_property.getSelectedItemPosition() == 0) {
                    //do nothing

                } else if (sp_property.getSelectedItemPosition() != 0) {
                    sp_department.setSelection(0);
                    sp_trainer.setSelection(0);
                    sp_assessmentTitle.setSelection(0);
                    rolesArrayList.clear();

                    sp_department.setBackgroundResource(R.drawable.drop_down);
                    sp_trainer.setBackgroundResource(R.drawable.drop_down);
                    databaseHelper.selectPropertyDepartmentMapping(databaseHelper.selectProperties().get(sp_property.getSelectedItemPosition() - 1).getId(),
                            SessionClass.getInstance().getUser(getApplicationContext()).getCompanyId());
                    propertyId = databaseHelper.selectProperties().get(position - 1).getId();
                    Log.e("propertyId", "" + propertyId);

                    //Departments
                    departmentLists.clear();
                    departmentLists.add(0, departmentModel);
                    departmentLists.addAll(databaseHelper.selectPropertyDepartmentMapping(databaseHelper.selectProperties().get(sp_property.getSelectedItemPosition() - 1).getId(), SessionClass.getInstance().getUser(getApplicationContext()).getCompanyId()));
                    departmentSpinnerAdapter.notifyDataSetChanged();
                    trainerArrayList.clear();
                    trainerArrayList.add(0, employee);
                    trainerArrayList.addAll(databaseHelper.selectPropertyTrainerMappings(databaseHelper.selectProperties().get(sp_property.getSelectedItemPosition() - 1).getId()));

                    trainers_adapter.notifyDataSetChanged();

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        sp_department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Assessment Title
                if (sp_department.getSelectedItemPosition() == 0) {
                    //do nothing
                } else if (sp_department.getSelectedItemPosition() != 0) {

                    sp_assessmentTitle.setBackgroundResource(R.drawable.drop_down);
                    deptId = databaseHelper.selectDepartment().get(position - 1).getId();
                    Log.e("deptId", "" + deptId);
                    assmessmentModelArrayList.clear();
                    assmessmentModelArrayList.add(0, assmessmentModel);
                    assmessmentModelArrayList.addAll(databaseHelper.selectDepartmentAssessmentMapping(databaseHelper.selectDepartment().get(sp_department.getSelectedItemPosition() - 1).getId()));
                    assesmentTitle_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Trainers

        /**
         * SignIn user Added in trainer list
         */
      /*  if ( SessionClass.getInstance().getUser(this).getRole().equalsIgnoreCase("Trainer")){
            employee.setFname(SessionClass.getInstance().getUser(this).getFname());
            employee.setLname(SessionClass.getInstance().getUser(this).getLname());
            trainerArrayList.add(employee);

        }*/
        //  trainerArrayList.addAll(databaseHelper.selectEmployees());

        sp_trainer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sp_trainer.getSelectedItemPosition() == 0) {
                    //do nothing
                }
              /* if (SessionClass.getInstance().getUser(App.getContext()).getRole().equalsIgnoreCase("Trainer")){
                   employee.setId(SessionClass.getInstance().getUser(App.getContext()).getId());
                   trainerId = employee.getId();
               }*/
                else if (sp_trainer.getSelectedItemPosition() != 0) {
                  Log.e("Trainer",""+trainerArrayList.get(sp_trainer.getSelectedItemPosition()).getId())  ;
                  //  trainerId = databaseHelper.selectEmployees().get(sp_property.getSelectedItemPosition() - 1).getId();
                    trainerId = trainerArrayList.get(sp_trainer.getSelectedItemPosition()).getId();
                    Log.e("trainerId", "" + trainerId);


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        sp_assessmentTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (sp_assessmentTitle.getSelectedItemPosition() == 0) {
                    for (int i = 0; i < rolesArrayList.size(); i++) {
                        if (rolesArrayList.get(i).getIsChecked() == 1) {

                            rolesArrayList.get(i).setIsChecked(0);

                            databaseHelper.updateRoles(0, databaseHelper.selectRoles().get(i).getId());
                        }
                    }
                    checkbox_adapter.notifyDataSetChanged();
                    //do nothing
                } else if (sp_assessmentTitle.getSelectedItemPosition() != 0) {

                    assesmentKey = databaseHelper.selectAssessmentTitle().get(position - 1).getAssessmentKey();
                    assessmentTitleId = databaseHelper.selectAssessmentTitle().get(position - 1).getId();
                    Log.e("assessmentTitleId", "" + assessmentTitleId);
                    //Roles
                    rolesArrayList.clear();
                    rolesArrayList.addAll(databaseHelper.selectAssessmentRoleMappings(databaseHelper.selectAssessmentTitle().get(sp_assessmentTitle.getSelectedItemPosition() - 1).getId()));


                    checkbox_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sp_property.getSelectedItemPosition() <= 0 && sp_department.getSelectedItemPosition() <= 0 || sp_assessmentTitle.getSelectedItemPosition() <= 0 || sp_trainer.getSelectedItemPosition() <= 0) {
                    showDialogAlert(R.string.alert_msg_assessment_details);
                } else {

                    //  for (int i= 0; i<databaseHelper.selectRoles().size();i++)

                    setContinue_btn(assesmentKey, databaseHelper.selectRoles());
                    databaseHelper.insertSelectedIds(assessmentId, propertyId, deptId, trainerId, assessmentTitleId);
                }


            }
        });

    }

    public void getDropDownData(int companyId, int lastUpdateDate) {

        WebServiceFactory.getInstance().newAssessmentService(companyId, lastUpdateDate).enqueue(new Callback<newAssessmentResponse>() {
            @Override
            public void onResponse(Call<newAssessmentResponse> call, Response<newAssessmentResponse> response) {
                try {
rotateLoading.start();
                    JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                    newAssessmentResponse AssessmentResponse = new Gson().fromJson(jsonObject.toString(), newAssessmentResponse.class);
                    Log("Response", "" + AssessmentResponse.getStatuCode());
                    if (AssessmentResponse.getStatuCode() == 0) {
                        rotateLoading.stop();
                        //showDialogAlert(R.string.dataUptoDate);
                        getDataFromDB();
                    } else {
                        rotateLoading.stop();
                        getDataFromDB();
                        /**
                         * Categories update and delete
                         */
                        for (int i = 0; i < AssessmentResponse.getCategories().size(); i++) {
                            // delete
                            if (AssessmentResponse.getCategories().get(i).getAction().equalsIgnoreCase("delete")) {
                                databaseHelper.deleteCategories(AssessmentResponse.getCategories().get(i).getId());
                            }
                            // update
                            if (AssessmentResponse.getCategories().get(i).getAction().equalsIgnoreCase("edit")) {
                                databaseHelper.updateCategories(AssessmentResponse.getCategories().get(i).getId().toString(), AssessmentResponse.getCategories().get(i).getName(),
                                        AssessmentResponse.getCategories().get(i).getCompanyId().toString(), AssessmentResponse.getCategories().get(i).getCategoryKey());
                            }

                            if (AssessmentResponse.getCategories().get(i).getAction().equalsIgnoreCase("add")) {
                                databaseHelper.insertCategories(AssessmentResponse.getCategories().get(i));
                            }

                        }
                        /**
                         * Properties update and delete
                         */
                        //Properties
                        for (int i = 0; i < AssessmentResponse.getProperties().size(); i++) {
                            //delete
                            if (AssessmentResponse.getProperties().get(i).getAction().equalsIgnoreCase("delete")) {
                                databaseHelper.deleteProperties(AssessmentResponse.getProperties().get(i).getId());
                            }
                            //update
                            if (AssessmentResponse.getProperties().get(i).getAction().equalsIgnoreCase("edit")) {
                                databaseHelper.updateProperties(AssessmentResponse.getProperties().get(i).getId().toString(),
                                        AssessmentResponse.getProperties().get(i).getName(), AssessmentResponse.getProperties().get(i).getCompanyId());
                                Log("PropertEdit", AssessmentResponse.getProperties().get(i).getName());
                            }
                            //add
                            if (AssessmentResponse.getProperties().get(i).getAction().equalsIgnoreCase("add")) {
                                databaseHelper.insertPropertyModel(AssessmentResponse.getProperties().get(i));
                            }
                        }
                        /**
                         * Departments update and delete
                         */
                        //department
                        for (int i = 0; i < AssessmentResponse.getDepartments().size(); i++) {
                            //delete
                            if (AssessmentResponse.getDepartments().get(i).getAction().equalsIgnoreCase("delete")) {
                                databaseHelper.deleteDepartments(AssessmentResponse.getDepartments().get(i).getId());
                            }

                            //update
                            if (AssessmentResponse.getDepartments().get(i).getAction().equalsIgnoreCase("edit")) {
                                databaseHelper.updateDepartments(AssessmentResponse.getDepartments().get(i).getId().toString(), AssessmentResponse.getDepartments().get(i).getName()
                                        , AssessmentResponse.getDepartments().get(i).getCompanyId());
                                Log("getDepartments", AssessmentResponse.getProperties().get(i).getName());
                            }
                            if (AssessmentResponse.getDepartments().get(i).getAction().equalsIgnoreCase("add")) {
                                databaseHelper.insertDepartmentModel(AssessmentResponse.getDepartments().get(i));
                            }
                        }


                        /**
                         * Roles update and Delete
                         */
                        //Roles
                        for (int i = 0; i < AssessmentResponse.getRoles().size(); i++) {
                            //delete
                            if (AssessmentResponse.getRoles().get(i).getAction().equalsIgnoreCase("delete")) {
                                databaseHelper.deleteRoles(AssessmentResponse.getRoles().get(i).getId());
                            }
                            if (AssessmentResponse.getRoles().get(i).getAction().equalsIgnoreCase("edit")) {
                                databaseHelper.updateRole(AssessmentResponse.getRoles().get(i).getId().toString(), AssessmentResponse.getRoles().get(i).getName(),
                                        AssessmentResponse.getRoles().get(i).getCompanyId(), AssessmentResponse.getRoles().get(i).getDepartmentId(),
                                        AssessmentResponse.getRoles().get(i).getRoleKey());
                            }
                            if (AssessmentResponse.getRoles().get(i).getAction().equalsIgnoreCase("add")) {
                                databaseHelper.insertRoles(AssessmentResponse.getRoles().get(i));
                            }
                        }

                        /**
                         * Assessment Title update and delete
                         */
                        //Assessment Title
                        for (int i = 0; i < AssessmentResponse.getAssmessments().size(); i++) {
                            //update
                            if (AssessmentResponse.getAssmessments().get(i).getAction().equalsIgnoreCase("edit")) {
                                databaseHelper.updateAssessmentTitle(AssessmentResponse.getAssmessments().get(i).getId().toString(),
                                        AssessmentResponse.getAssmessments().get(i).getTitle(),
                                        AssessmentResponse.getAssmessments().get(i).getCompanyId(),
                                        AssessmentResponse.getAssmessments().get(i).getDepartmentId(),
                                        AssessmentResponse.getAssmessments().get(i).getRolesId(),
                                        AssessmentResponse.getAssmessments().get(i).getAssessmentKey());
                            }
                            //delete
                            if (AssessmentResponse.getAssmessments().get(i).getAction().equalsIgnoreCase("delete")) {
                                databaseHelper.deleteAssessmentTitles(AssessmentResponse.getAssmessments().get(i).getId());
                            }
                            if (AssessmentResponse.getAssmessments().get(i).getAction().equalsIgnoreCase("add")) {
                                databaseHelper.insertAssessmentTitles(AssessmentResponse.getAssmessments().get(i));
                            }

                        }
                        /**
                         * Employees update and delete
                         */

                        for (int i = 0; i < AssessmentResponse.getEmployees().size(); i++) {
                            //update
                            if (AssessmentResponse.getEmployees().get(i).getAction().equalsIgnoreCase("edit")) {
                                databaseHelper.updateEmployeeTable(AssessmentResponse.getEmployees().get(i).getId(),
                                        AssessmentResponse.getEmployees().get(i).getFname(),
                                        AssessmentResponse.getEmployees().get(i).getLname(),
                                        AssessmentResponse.getEmployees().get(i).getEmail(),
                                        AssessmentResponse.getEmployees().get(i).getRole(),
                                        AssessmentResponse.getEmployees().get(i).getCompanyId(),
                                        AssessmentResponse.getEmployees().get(i).getDepartmentId());
                            }
                            //delete
                            if (AssessmentResponse.getEmployees().get(i).getAction().equalsIgnoreCase("delete")) {
                                databaseHelper.deleteEmployees(AssessmentResponse.getEmployees().get(i).getId());
                            }
                            //add
                            if (AssessmentResponse.getEmployees().get(i).getAction().equalsIgnoreCase("add")) {
                                databaseHelper.insertEmployess(AssessmentResponse.getEmployees().get(i),0);
                            }
                        }
                        /**
                         * property_department_mapping update, add and delete
                         */
                        for (int i = 0; i < AssessmentResponse.getPropertyDepartmentMapping().size(); i++) {
                            //update
                            if (AssessmentResponse.getPropertyDepartmentMapping().get(i).getAction().equalsIgnoreCase("edit")) {
                                databaseHelper.updatePropertyDepartmentmMpping(AssessmentResponse.getPropertyDepartmentMapping().get(i).getId(),
                                        AssessmentResponse.getPropertyDepartmentMapping().get(i).getPropertyId(),
                                        AssessmentResponse.getPropertyDepartmentMapping().get(i).getDepartmentId(),
                                        AssessmentResponse.getPropertyDepartmentMapping().get(i).getCompanyId());
                            }
                            //delete
                            if (AssessmentResponse.getPropertyDepartmentMapping().get(i).getAction().equalsIgnoreCase("delete")) {
                                databaseHelper.deletePropertyDepartment(AssessmentResponse.getPropertyDepartmentMapping().get(i).getId());
                            }
                            //add
                            if (AssessmentResponse.getPropertyDepartmentMapping().get(i).getAction().equalsIgnoreCase("add")) {
                                databaseHelper.insertPropertyDepartmentMapping(AssessmentResponse.getPropertyDepartmentMapping().get(i));
                            }
                        }

                        /**
                         * property trainer mapping update, delete and add
                         */
                        for (int i =0; i<AssessmentResponse.getPropertyTrainerMapping().size();i++){
                            if (AssessmentResponse.getPropertyTrainerMapping().get(i).getAction().equalsIgnoreCase("update")){
                                databaseHelper.updatePropertyTrainerMpping(AssessmentResponse.getPropertyTrainerMapping().get(i).getId(),
                                        AssessmentResponse.getPropertyTrainerMapping().get(i).getPropertyId(),AssessmentResponse.getPropertyTrainerMapping().get(i).getEmployeeId(),
                                        AssessmentResponse.getPropertyTrainerMapping().get(i).getCompanyId());

                            }
                            //delete
                            if (AssessmentResponse.getPropertyTrainerMapping().get(i).getAction().equalsIgnoreCase("delete")){
                                  databaseHelper.deletePropertyTrainer(AssessmentResponse.getPropertyTrainerMapping().get(i).getId());
                            }
                            //add
                            if (AssessmentResponse.getPropertyTrainerMapping().get(i).getAction().equalsIgnoreCase("add")){
                                databaseHelper.insertPropertyTrainerMapping(AssessmentResponse.getPropertyTrainerMapping().get(i));
                            }
                        }


                    }


//else {
/*


    if (assessmentResponse.getStatuCode() == 1) {
        //property
        propertyLists.addAll(assessmentResponse.getProperties());
        propertySpinnerAdapter.notifyDataSetChanged();
        //departments
        departmentLists.addAll(assessmentResponse.getDepartments());
        departmentSpinnerAdapter.notifyDataSetChanged();
        //Trainer

        //AssessmentTitle

        //Roles
        rolesArrayList.addAll(assessmentResponse.getRoles());
        checkbox_adapter.notifyDataSetChanged();
    }
}
*/
                /*    }
                    else {
                        Toast.makeText(new_assessment.this, "", Toast.LENGTH_SHORT).show();
                    }
*/
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<newAssessmentResponse> call, Throwable t) {

            }
        });
    }

    public void setContinue_btn(String assesmentKey, ArrayList<roles> roles) {

     /*   for (int i = 0; i < databaseHelper.selectEmployees().size(); i++) {
            databaseHelper.updateEmployees(0, databaseHelper.selectEmployees().get(i).getId());
        }*/


     sharedPrefManager.clearKey("savedTrainerName");
     sharedPrefManager.clearKey("submittedTrainerName");
     sharedPrefManager.clearKey("submittedDepartmentName");
     sharedPrefManager.clearKey("savedDepartmentName");
        Intent intent = new Intent(this, arrival_service.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        sharedPrefManager.clearKey("GIData");
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("assessmentKey", assesmentKey);
        intent.putExtra("rolesKey", roles);
        startActivity(intent);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
