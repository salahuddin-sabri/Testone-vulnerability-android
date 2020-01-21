package com.standardyze.activites;


import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.standardyze.R;
import com.standardyze.adapters.employees_adapter;
import com.standardyze.baseActivity;
import com.standardyze.models.addEmployee;
import com.standardyze.utils.DatabaseHelper;
import com.standardyze.utils.SharedPrefManager;
import com.standardyze.wrapper.Employee;

import java.util.ArrayList;
import java.util.Collections;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class addEmployees extends baseActivity {


    @BindView(R.id.recycler_view_employee)
    RecyclerView recyclerView;
    @BindView(R.id.customEdtxt)
    EditText customEdtxt;
    @BindView(R.id.ad_btn)
    Button add_btn;
    @BindView(R.id.add_employee)
    Button button;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Employee> arrayList;
    employees_adapter employees_adapter;
    addEmployee addEmployees;
    DatabaseHelper databaseHelper;
    Employee employees;
    ArrayList<Employee> getArrayList;
    boolean customEmpAdded;
    SharedPrefManager sharedPrefManager;
    int tempId = 0;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employees);
        showActionBar("Add Employees", null, false, false, false, false);
        ButterKnife.bind(this);
        init();
        Typeface copperplateGothicLight = Typeface.createFromAsset(this.getAssets(), "font/GothamBold.ttf");
        add_btn.setTypeface(copperplateGothicLight);

        Typeface copperplateGoth = Typeface.createFromAsset(this.getAssets(), "font/GothamBold.ttf");
        button.setTypeface(copperplateGoth);
    }

    public void init() {
        sharedPrefManager = new SharedPrefManager(this);
        arrayList = new ArrayList<>();
        databaseHelper = new DatabaseHelper(this);
        getArrayList = new ArrayList<>();
        employees = new Employee();

        button.setEnabled(false);
        add_btn.setEnabled(false);
        //  add_btn.setBackground(getResources().getDrawable(R.drawable.button_selector));add_btn
        add_btn.setAlpha(0.2f);
        button.setAlpha(0.2f);
        arrayList.addAll(databaseHelper.selectEmployees());


        //if Saved Employee getting Id from dashboard
        int getAssessmentUniqueId = sharedPrefManager.getIntegerByKey("assessmmentUniqID");
        Log.e("assessmmentUniqID", "" + getAssessmentUniqueId);


        customEdtxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                enableSubmitIfReady();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });


        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        employees_adapter = new employees_adapter(this, getArrayList, new employees_adapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position, int isChecked) {
                pos = position;


                if (isChecked == 1) {
                    button.setEnabled(true);
                    button.setAlpha(0.9f);
                    button.setBackground(getResources().getDrawable(R.drawable.button_layout));

                } else {
                    button.setEnabled(true);
                    button.setAlpha(0.9f);
                    button.setBackground(getResources().getDrawable(R.drawable.button_layout));
                    //  button.setBackground(getResources().getDrawable(R.drawable.button_selector));
                    // button.setEnabled(false);
                    //button.setAlpha(0.2f);

                    // button.setBackground(getResources().getDrawable(R.drawable.button_layout));

                }
            }
        });
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(employees_adapter);
        for (int i = 0; i < arrayList.size(); i++) {
            if (getAssessmentUniqueId == 0 || getAssessmentUniqueId == arrayList.get(i).isEmployeeAdded()) {
                if (arrayList.get(i).isEmployeeAdded() == 1) {
                    getArrayList.add(0, arrayList.get(i));

                } else {
                    getArrayList.add(arrayList.get(i));
                }
            } else if (databaseHelper.selectUpdatedTempEmployee(i).getFname() != null) {

                if (databaseHelper.selectUpdatedTempEmployee(i).isEmployeeAdded() == 1) {

                    getArrayList.add(0, databaseHelper.selectUpdatedTempEmployee(i));
                } else {
                    getArrayList.add(databaseHelper.selectUpdatedTempEmployee(i));
                }
            }

        }
        if (sharedPrefManager.getStringByKey("new")!=null){
            sharedPrefManager.clearKey("new");
            updateEmployeeStatus();
        }
        else if (sharedPrefManager.getStringByKey("savedEmployees")!=null){
            updateEmployeeStatus();
            sharedPrefManager.clearKey("new");
        }


        if (sharedPrefManager.getStringByKey("submittedEmployees") != null) {
            customEdtxt.setEnabled(false);
            add_btn.setEnabled(false);
            updateSubmittedEmployeeStatus();
        } else {
            sharedPrefManager.clearKey("submittedEmployees");
            customEdtxt.setEnabled(true);
            add_btn.setEnabled(true);
        }
    }

    private void updateEmployeeStatus() {


        String employees = sharedPrefManager.getStringByKey("savedEmployees");
        if (employees != null && !employees.isEmpty()) {
            String[] item = employees.split(",");
             for (int i = 0; i < item.length; i++) {
                String employeeName = item[i];
                if (employeeName != null && !employeeName.isEmpty()) {
                    Employee employee = new Employee();
                    if (employeeName.contains(" ")) {
                        String[] splittedEmployeeName = employeeName.split(" ");
                        if (splittedEmployeeName.length > 1) {
                            employee.setFname(item[i].split(" ")[0]);
                            employee.setLname(item[i].split(" ")[1]);
                           /* if (employee.isEmployeeAdded()==null){
                                employee.setId(databaseHelper.countEmployeeRowa() + 1);
                                employee.setIsChecked(1);
                                getArrayList.add(0, employee);
                            }*/
                        } else {

                            //For custom employee


                         /*   if (employee.getId()==null && employee.getIsChecked()==0 ){
                                employee.setIsChecked(1);
                                employee.setId(databaseHelper.countEmployeeRowa()+1);
                                getArrayList.add( employee);
                            }else {*/
                                employee.setFname(employeeName);
                                employee.setLname("");
                         //   }

//                            employee.setId(databaseHelper.countEmployeeRowa() + 1);
//                            getArrayList.add(0, employee);


                         /*   if (employee.isEmployeeAdded() == null) {
                                employee.setId(databaseHelper.countEmployeeRowa() + 1);
                              //  employee.setId(0);
                                employee.setIsChecked(1);
                              //  getArrayList.add(0, employee);
                                Log("SavedEmployee",""+employee);
                            }*/

                        }
                    } else {
                        employee.setFname(employeeName);
                        employee.setLname("");
                        employee.setIsChecked(1);
                    }

                    int index = getArrayList.indexOf(employee);

                    if (index >= 0) {

                        getArrayList.get(index).setIsChecked(1);
                        Log("index",""+index);
                    } else {
                       /* if (employee.getIsChecked()==0){
                            employee.setId(databaseHelper.countEmployeeRowa() + 1);
                            employee.setIsChecked(1);
                            getArrayList.add(0, employee);

                        }*/
                       // getArrayList.get(index).setIsChecked(1);
                /*        employee.setId(databaseHelper.countEmployeeRowa() + 1);
                        employee.setIsChecked(1);
                        Log.e("ID",""+employee.getId());

                        getArrayList.add(0, employee);*/
                    }
                }
            }
        }
        sharedPrefManager.setStringForKey(new Gson().toJson(getArrayList), "EmployeesSaved");
        employees_adapter.notifyDataSetChanged();


    }

    private void updateSubmittedEmployeeStatus() {
        String submittedEmployee = sharedPrefManager.getStringByKey("submittedEmployees");
        if (submittedEmployee != null && !submittedEmployee.isEmpty()) {
            String[] item = submittedEmployee.split(",");
            for (int i = 0; i < item.length; i++) {
                String employeeName = item[i];
                if (employeeName != null && !employeeName.isEmpty()) {
                    Employee employee = new Employee();
                    if (employeeName.contains(" ")) {
                        String[] splittedEmployeeName = employeeName.split(" ");
                        if (splittedEmployeeName.length > 1) {
                            employee.setFname(item[i].split(" ")[0]);
                            employee.setLname(item[i].split(" ")[1]);
                        } else {
                            //For custom employee
                            employee.setFname(employeeName);
                          //  employee.setLname("");
                            if (employee.isEmployeeAdded() == null) {
                                employee.setId(databaseHelper.countEmployeeRowa() + 1);
                                employee.setIsChecked(1);
                                getArrayList.add(0, employee);

                            }
                        }
                    } else {
                        employee.setFname(employeeName);
                    //    employee.setLname("");
                        if (employeeName.contains("")) {
                            if (employee.isEmployeeAdded() == null) {
                                employee.setId(databaseHelper.countEmployeeRowa() + 1);
                                employee.setIsChecked(1);
                                getArrayList.add(0, employee);

                            }
                        }
                    }


                    int index = getArrayList.indexOf(employee);
                    if (index >= 0) {
                        getArrayList.get(index).setIsChecked(1);

                    }

                }
            }
        }
        sharedPrefManager.setStringForKey(new Gson().toJson(getArrayList), "EmployeesSubmitted");
        employees_adapter.notifyDataSetChanged();
    }

    public void enableSubmitIfReady() {

        boolean isReady = customEdtxt.getText().toString().length() > 0;
        if (isReady) {
            add_btn.setEnabled(isReady);
            add_btn.setAlpha(0.9f);
            //  add_btn.setBackground(getResources().getDrawable(R.drawable.button_layout));
        } else {
            add_btn.setEnabled(isReady);
            add_btn.setAlpha(0.2f);
            // add_btn.setBackground(getResources().getDrawable(R.drawable.button_selector));

        }


    }

    @OnClick(R.id.ad_btn)
    public void setAdd_btn() {


        String customEmployee = customEdtxt.getText().toString();
        if (!customEdtxt.getText().toString().isEmpty()) {
            button.setEnabled(true);
            button.setAlpha(0.9f);
            employees.setFname(customEmployee);
            employees.setLname("");
            employees.setCompanyId(0);
            employees.setId(databaseHelper.countEmployeeRowa() + 1);
            customEdtxt.setText("");
            employees.setIsChecked(1);

            getArrayList.add(0, employees);

            employees_adapter.notifyDataSetChanged();

        } else {
            add_btn.setEnabled(false);
            add_btn.setAlpha(0.2f);
        }

    }



    @OnClick(R.id.add_employee)
    public void addEmployee() {
        if (employees.getFname()!=null || employees.getIsChecked()!=0){
            insertCustomEmployee(employees);
        }

        StringBuilder sb1 = new StringBuilder();
        for (int i = 0; i < getArrayList.size(); i++) {
            if (getArrayList.get(i).getIsChecked() == 1) {

                databaseHelper.updateEmployees(1, getArrayList.get(i).getId());
                sb1.append(getArrayList.get(i).getFname()+" "+getArrayList.get(i).getLname());
                sb1.append(",");
                sharedPrefManager.setStringForKey(String.valueOf(sb1),"savedEmployees");
                sharedPrefManager.setStringForKey(new Gson().toJson(getArrayList),"new");
                Log.e("sb1",""+sb1);
               /* sb1.append(getArrayList.get(i).getFname()+" "+getArrayList.get(i).getLname());
                sb1.append(",");
                Log.e("sb1",""+sb1);
                if (getArrayList.get(i).getFname().contains("null") && getArrayList.get(i).getLname().contains("null")){
                    getArrayList.remove(i).getFname();
                }
                else {
                    sharedPrefManager.setStringForKey(String.valueOf(sb1),"savedEmployees");

                    sharedPrefManager.setStringForKey(new Gson().toJson(getArrayList), "newEmployees");
                }*/
            } else {
                databaseHelper.updateEmployees(0, getArrayList.get(i).getId());
                if (sharedPrefManager.getIntegerByKey("assessmentID")!=0)
                {
                    databaseHelper.deleteCustomEmployees( sharedPrefManager.getIntegerByKey("assessmentID"));
                }
            //
                Log.e("AssID",""+sharedPrefManager.getIntegerByKey("assessmentID"));
                //sharedPrefManager.clearKey("savedEmployees");
             //   databaseHelper.updateCustomEmployee( sharedPrefManager.getIntegerByKey("assessmentID"));
              //  sharedPrefManager.clearKey("savedEmployees");
            }
        }

        if (databaseHelper.totalSum() > 0) {
            showDialogAlert(R.string.addEmployee_alert);

        } else {
            showDialogAlert(R.string.alert_msg_information);
        }

    }


    public void insertCustomEmployee(Employee employee) {

        if (sharedPrefManager.getIntegerByKey("assessmmentUniqID") != 0 || sharedPrefManager.getIntegerByKey("assessmentID") != 0) {
            databaseHelper.insertEmployess(employee, sharedPrefManager.getIntegerByKey("assessmmentUniqID"));
        }
        else  {
            databaseHelper.insertEmployess(employee, sharedPrefManager.getIntegerByKey("assessmentID"));
        }

    }
}
