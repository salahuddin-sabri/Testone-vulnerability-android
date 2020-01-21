package com.standardyze.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.standardyze.App.App;
import com.standardyze.models.CategoryModel;
import com.standardyze.models.assessmentSavedData;
import com.standardyze.models.checkbox_items;
import com.standardyze.models.options;
import com.standardyze.wrapper.AssessmentRoleMapping;
import com.standardyze.wrapper.AssmessmentModel;
import com.standardyze.wrapper.Category;
import com.standardyze.wrapper.DepartmentAssessmentMapping;
import com.standardyze.wrapper.Employee;
import com.standardyze.wrapper.PropertyDepartmentMapping;
import com.standardyze.wrapper.PropertyTrainerMapping;
import com.standardyze.wrapper.QuestionsResult;
import com.standardyze.wrapper.StandardQuestion;
import com.standardyze.wrapper.User;
import com.standardyze.wrapper.departmentModel;
import com.standardyze.wrapper.getAssessmentResponseObj;
import com.standardyze.wrapper.propertyModel;
import com.standardyze.wrapper.roles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
  SQLiteDatabase sqLiteDatabase;

  public DatabaseHelper(@Nullable Context context) {
    super(context, "standaryzed.db", null, 14);
  }


  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(AppConstants.Create_USER);
    db.execSQL(AppConstants.CREATE_PROPERTY);
    db.execSQL(AppConstants.CREATE_DEPT);
    db.execSQL(AppConstants.CREATE_ROLES);
    db.execSQL(AppConstants.CREATE_EMPLOYEES);
    db.execSQL(AppConstants.CREATE_ASSESMENT_TITLE);

    db.execSQL(AppConstants.CREATE_PROP_DEPT_MAPPING);
    db.execSQL(AppConstants.CREATE_DEPT_ASSESMENT_MAPPING);
    db.execSQL(AppConstants.CREATE_ASSESMENT_ROLE_MAPPING);
    db.execSQL(AppConstants.CREATE_PROPERTY_TRAINER_MAPPING);
    db.execSQL(AppConstants.CREATE_STANDARD_QUESTIONS);
    db.execSQL(AppConstants.CREATE_CATEGORIES);
    db.execSQL(AppConstants.CREATE_SELECTION_ID);
    db.execSQL(AppConstants.CREATE_ASSESSMENT_LISTING);
    db.execSQL(AppConstants.CREATE_QUESTIONS_RESPONSE);
    db.execSQL(AppConstants.CREATE_ASSESSMENT_CATEGORIES);

  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL(AppConstants.DROP_Create_USER);
    db.execSQL(AppConstants.DROP_CREATE_PROPERTY);
    db.execSQL(AppConstants.DROP_CREATE_DEPT);
    db.execSQL(AppConstants.DROP_CREATE_ROLES);
    db.execSQL(AppConstants.DROP_CREATE_EMPLOYEES);
    db.execSQL(AppConstants.DROP_CREATE_ASSESSMENT_TITLE);
    db.execSQL(AppConstants.DROP_CREATE_PROP_DEPT_MAPPING);
    db.execSQL(AppConstants.DROP_CREATE_DEPT_ASSESSMENT_MAPPING);
    db.execSQL(AppConstants.DROP_CREATE_ASSESSMENT_ROLE_MAPPING);
    db.execSQL(AppConstants.DROP_CREATE_PROPERTY_TRAINER_MAPPING);
    db.execSQL(AppConstants.DROP_CREATE_STANDARD_QUESTIONS);
    db.execSQL(AppConstants.DROP_CREATE_CATEGORIES);
    db.execSQL(AppConstants.DROP_CREATE_SELECTION);
    db.execSQL(AppConstants.DROP_CREATE_ASSESMENT_LISTING);
    db.execSQL(AppConstants.DROP_CREATE_QUESTIONS_RESPONSE);
  }

  /**
   * Inserting saveData
   *
   * @return
   */
  //Properties
  public void insertPropertyModel(propertyModel propertyModel) {
    sqLiteDatabase = getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put("id", propertyModel.getId());
    values.put("name", propertyModel.getName());
    values.put("companyId", propertyModel.getCompanyId());



    try {
      sqLiteDatabase.insertWithOnConflict(AppConstants.PROPERTY_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    } catch (SQLiteException e) {
      e.printStackTrace();
    }
  }

  //Departments
  public void insertDepartmentModel(departmentModel departmentModel) {
    sqLiteDatabase = getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put("id", departmentModel.getId());
    values.put("name", departmentModel.getName());
    values.put("companyId", departmentModel.getCompanyId());


    try {
      sqLiteDatabase.insertWithOnConflict(AppConstants.DEPT_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    } catch (SQLiteException e) {
      e.printStackTrace();
    }
  }

  //Roles
  public void insertRoles(roles roles) {
    sqLiteDatabase = getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put("id", roles.getId());
    values.put("name", roles.getName());
    values.put("companyId", roles.getCompanyId());
    values.put("departmentId", roles.getDepartmentId());
    values.put("roleKey", roles.getRoleKey());
    values.put("isChecked", 0);


    try {
      sqLiteDatabase.insertWithOnConflict(AppConstants.ROLES_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    } catch (SQLiteException e) {
      e.printStackTrace();
    }
  }

  //checkedRoles
  public void insertCheckedRoles(checkbox_items checkbox_items) {
    sqLiteDatabase = getWritableDatabase();
    ContentValues values = new ContentValues();
    try {
      sqLiteDatabase.insertWithOnConflict(AppConstants.ROLES_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    } catch (SQLiteException e) {
      e.printStackTrace();
    }
  }


  //Employees
  public void insertEmployess(Employee employee,int tempEmp) {
    sqLiteDatabase = getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put("id", employee.getId());
    values.put("fname", employee.getFname());
    values.put("lname", employee.getLname());
    values.put("email", employee.getEmail());
    values.put("role", employee.getRole());
    values.put("companyId", employee.getCompanyId());
    values.put("departmentId", employee.getDepartmentId());
    values.put("isTemporary",tempEmp);
    values.put("isChecked", employee.getIsChecked());


    try {
      sqLiteDatabase.insertWithOnConflict(AppConstants.EMPLOYEE_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    } catch (SQLiteException e) {
      e.printStackTrace();
    }
  }

  //Assessments Title
  public void insertAssessmentTitles(AssmessmentModel assmessmentModel) {
    sqLiteDatabase = getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put("id", assmessmentModel.getId());
    values.put("title", assmessmentModel.getTitle());
    values.put("rolesId", assmessmentModel.getRolesId());
    values.put("companyId", assmessmentModel.getCompanyId());
    values.put("departmentId", assmessmentModel.getDepartmentId());
    values.put("assessmentKey", assmessmentModel.getAssessmentKey());


    try {
      sqLiteDatabase.insertWithOnConflict(AppConstants.ASSESSMENT_TITLE_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    } catch (SQLiteException e) {
      e.printStackTrace();
    }
  }



  //Inserting assessment data
  public void insertAssessmentData(assessmentSavedData assessmentSavedData){
    sqLiteDatabase = getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put("id",assessmentSavedData.getId());
    contentValues.put("assessmentUniqueId",assessmentSavedData.getAssessmentUniqueId());
    contentValues.put("companyId",assessmentSavedData.getCompanyId());
    contentValues.put("userId",assessmentSavedData.getUserId());
    contentValues.put("trainerName",assessmentSavedData.getTrainerName());
    contentValues.put("departmentName",assessmentSavedData.getDepartmentName());
    contentValues.put("propertyName",assessmentSavedData.getPropertyName());
    contentValues.put("assessmentTitle",assessmentSavedData.getAssessmentTitle());
    contentValues.put("status",assessmentSavedData.getStatus());
    contentValues.put("score",assessmentSavedData.getScore());
    contentValues.put("category_scores", assessmentSavedData.getCategoryScores());
    contentValues.put("roles", String.valueOf(assessmentSavedData.getRoles()));
    contentValues.put("employees",assessmentSavedData.getEmployees().toString());
    contentValues.put("general_infromations", assessmentSavedData.getGeneralInfromations().toString());
    contentValues.put("propertyId",assessmentSavedData.getPropertyId());
    contentValues.put("assessmetId",assessmentSavedData.getAssessmetId());
    contentValues.put("departmentId",assessmentSavedData.getDepartmentId());
    contentValues.put("datetime",assessmentSavedData.getDatetime());
    try {
      sqLiteDatabase.insertWithOnConflict(AppConstants.ASSESSMENT_LISTING_TABLE, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    } catch (SQLiteException e) {
      e.printStackTrace();
    }
  }

  public boolean updateAssessmentData(int id, String status, int score, String category_scores,String employees,String general_info){
    sqLiteDatabase = getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    String where = "id=?";
    String[] whereArgs = new String[]{String.valueOf(id)};

    contentValues.put("status",status);
    contentValues.put("score",score);
    contentValues.put("category_scores", category_scores);
    contentValues.put("employees",employees);
    contentValues.put("general_infromations", general_info);

    sqLiteDatabase.update(AppConstants.ASSESSMENT_LISTING_TABLE, contentValues, where, whereArgs);
    return true;
  }

  public void insertAssessmentQuestionsResponse(int questionId , String selection,String comment , String imgId, String questions,int id){
    sqLiteDatabase = getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put("assessmentUniqueId",id);
    contentValues.put("selection",selection);
    contentValues.put("comment",comment);
    contentValues.put("imgId",imgId);
    contentValues.put("questionText",questions);
    contentValues.put("questionId",questionId);
    //contentValues.put("categories_responses",categories);
    try {
      sqLiteDatabase.insertWithOnConflict(AppConstants.QUESTIONS_RESPONSE, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    } catch (SQLiteException e) {
      e.printStackTrace();
    }
  }

  public boolean updateAssessmentQuestionsResponse(int id, String selection, String comment , String imgId){
    try {
    ContentValues contentValues = new ContentValues();
    sqLiteDatabase = this.getWritableDatabase();
    contentValues.put("id",id);
    contentValues.put("selection",selection);
    contentValues.put("comment",comment);
    contentValues.put("imgId",imgId);
    String where = "id=?";
    String[] whereArgs = new String[]{String.valueOf(id)};

    sqLiteDatabase.update(AppConstants.QUESTIONS_RESPONSE, contentValues, where, whereArgs);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      Log.d("err", e.getMessage());
      return false;
    }

  }

  public void insertAssessmentCategories(String categories){
    sqLiteDatabase = getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put("categories", categories);
    try {
      sqLiteDatabase.insertWithOnConflict(AppConstants.ASSESSMENT_Categories, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    } catch (SQLiteException e) {
      e.printStackTrace();
    }
  }

  public void updateAssessmentCategories(HashMap<String,String> categories, int assessmentUniqueId, int questionID ) {
    sqLiteDatabase = this.getWritableDatabase();
    //val getSharedPrefs = SP(this.context)
    ContentValues values = new ContentValues();
    values.put("categories_responses", categories.toString());
    String where = "assessmentUniqueId=? AND questionId=?";
    String[] whereArgs = new String[]{String.valueOf(assessmentUniqueId), String.valueOf(questionID)};

    try {
      //UPDATE `response_rc_table` SET `ResponseText`='Yes' WHERE ResopnseID='1' AND ResponseAssesID='022619110702';
      sqLiteDatabase.update(
              AppConstants.QUESTIONS_RESPONSE,
              values,where,whereArgs

      );  /*sqLiteDatabase.insertWithOnConflict(
              AppConstants.QUESTIONS_RESPONSE, null,
              values,SQLiteDatabase.CONFLICT_REPLACE

      );
*/
    } catch (SQLiteException e) {
      e.printStackTrace();
    }
  }

  /**
   * Inserting mapping Data
   */
  //property And Department Mapping
  public void insertPropertyDepartmentMapping(PropertyDepartmentMapping propertyDepartmentMapping) {
    sqLiteDatabase = getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put("id", propertyDepartmentMapping.getId());
    values.put("propertyId", propertyDepartmentMapping.getPropertyId());
    values.put("companyId", propertyDepartmentMapping.getCompanyId());
    values.put("departmentId", propertyDepartmentMapping.getDepartmentId());


    try {
      sqLiteDatabase.insertWithOnConflict(AppConstants.PROPERTY_DEPARTMENT_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    } catch (SQLiteException e) {
      e.printStackTrace();
    }
  }


  public boolean updatePropertyDepartmentmMpping(int id,int propertyId,int departmentId, int companyId){
    try {
      ContentValues values = new ContentValues();
      sqLiteDatabase = this.getWritableDatabase();

      values.put("id", id);
      values.put("propertyId", propertyId);
      values.put("departmentId", departmentId);
      values.put("companyId", companyId);


      String where = "id=?";
      String[] whereArgs = new String[]{String.valueOf(id)};

      sqLiteDatabase.update(AppConstants.PROPERTY_DEPARTMENT_TABLE, values, where, whereArgs);

      return true;
    } catch (Exception e) {
      e.printStackTrace();
      Log.d("err", e.getMessage());
      return false;
    }

  }


  public boolean updatePropertyTrainerMpping(int id,int propertyId,int employeeId, int companyId){
    try {
      ContentValues values = new ContentValues();
      sqLiteDatabase = this.getWritableDatabase();

      values.put("id", id);
      values.put("propertyId", propertyId);
      values.put("employeeId", employeeId);
      values.put("companyId", companyId);


      String where = "id=?";
      String[] whereArgs = new String[]{String.valueOf(id)};

      sqLiteDatabase.update(AppConstants.PROPERTY_TRAINER_TABLE, values, where, whereArgs);

      return true;
    } catch (Exception e) {
      e.printStackTrace();
      Log.d("err", e.getMessage());
      return false;
    }

  }

  //Department And Assessment Mapping
  public void insertDepartmentAndAssessmentMapping(DepartmentAssessmentMapping mapping) {
    sqLiteDatabase = getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put("id", mapping.getId());
    values.put("assessmentId", mapping.getAssessmentId());
    values.put("companyId", mapping.getCompanyId());
    values.put("departmentId", mapping.getDepartmentId());


    try {
      sqLiteDatabase.insertWithOnConflict(AppConstants.DEPT_ASSESSMENT_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    } catch (SQLiteException e) {
      e.printStackTrace();
    }
  }

  //Assessment Role Mapping
  public void insertAssessmentRoleMapping(AssessmentRoleMapping roleMapping) {
    sqLiteDatabase = getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put("id", roleMapping.getId());
    values.put("assessmentId", roleMapping.getAssessmentId());
    values.put("companyId", roleMapping.getCompanyId());
    values.put("roleId", roleMapping.getRoleId());


    try {
      sqLiteDatabase.insertWithOnConflict(AppConstants.ASSESSMENT_ROLE_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    } catch (SQLiteException e) {
      e.printStackTrace();
    }
  }


  //Property Trainer Mapping
  public void insertPropertyTrainerMapping(PropertyTrainerMapping propertyTrainerMapping) {
    sqLiteDatabase = getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put("id", propertyTrainerMapping.getId());
    values.put("propertyId", propertyTrainerMapping.getPropertyId());
    values.put("employeeId", propertyTrainerMapping.getEmployeeId());
    values.put("companyId", propertyTrainerMapping.getCompanyId());


    try {
      sqLiteDatabase.insertWithOnConflict(AppConstants.PROPERTY_TRAINER_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    } catch (SQLiteException e) {
      e.printStackTrace();
    }
  }

  //Standard Questions
  public void insertStandardQuestions(StandardQuestion standardQuestion) {
    sqLiteDatabase = getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put("id", standardQuestion.getId());
    values.put("assessmentNumber", standardQuestion.getAssessmentNumber());
    values.put("assessmentQuestion", standardQuestion.getAssessmentQuestion());
    values.put("sectionName", standardQuestion.getSectionName());
    values.put("companyId", standardQuestion.getCompanyId());


    try {
      sqLiteDatabase.insertWithOnConflict(AppConstants.STANDARD_QUESTIONS_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    } catch (SQLiteException e) {
      e.printStackTrace();
    }
  }

  // Categories
  public void insertCategories(Category category) {
    sqLiteDatabase = getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put("id", category.getId());
    values.put("name", category.getName());
    values.put("companyId", category.getCompanyId());
    values.put("categoryKey", category.getCategoryKey());


    try {
      sqLiteDatabase.insertWithOnConflict(AppConstants.CATEGORIES_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    } catch (SQLiteException e) {
      e.printStackTrace();
    }
  }
  public int deleteCategories(int catID){
    String where = "id=?";
    String[] whereArgs = new String[] {String.valueOf(catID)};
    sqLiteDatabase = this.getWritableDatabase();
    int deleted = sqLiteDatabase.delete(AppConstants.CATEGORIES_TABLE, where, whereArgs);
    return deleted;

  }
  public int deleteEmployees(int employeeId){
    String where = "id=?";
    String[] whereArgs = new String[] {String.valueOf(employeeId)};
    sqLiteDatabase = this.getWritableDatabase();
    int deleted = sqLiteDatabase.delete(AppConstants.EMPLOYEE_TABLE, where, whereArgs);
    return deleted;

  }
  public int deleteProperties(int propertyId){
    String where = "id=?";
    String[] whereArgs = new String[] {String.valueOf(propertyId)};
    sqLiteDatabase = this.getWritableDatabase();
    int deleteProperty = sqLiteDatabase.delete(AppConstants.PROPERTY_TABLE,where,whereArgs);
    return deleteProperty;
  }

  public int deleteDepartments(int deptId){
    String where = "id=?";
    String[] whereArgs = new String[] {String.valueOf(deptId)};
    sqLiteDatabase = this.getWritableDatabase();
    int deleteProperty = sqLiteDatabase.delete(AppConstants.DEPT_TABLE,where,whereArgs);
    return deleteProperty;
  }

  public int deleteRoles(int rolesId){
    String where = "id=?";
    String[] whereArgs = new String[] {String.valueOf(rolesId)};
    sqLiteDatabase = this.getWritableDatabase();
    int deleteProperty = sqLiteDatabase.delete(AppConstants.ROLES_TABLE,where,whereArgs);
    return deleteProperty;
  }

  public int deleteAssessmentTitles(int AssessmentTitleId){
    String where = "id=?";
    String[] whereArgs = new String[] {String.valueOf(AssessmentTitleId)};
    sqLiteDatabase = this.getWritableDatabase();
    int deleteProperty = sqLiteDatabase.delete(AppConstants.ASSESSMENT_TITLE_TABLE,where,whereArgs);
    return deleteProperty;
  }
  public int deletePropertyDepartment(int PDid){
    String where = "id=?";
    String[] whereArgs = new String[] {String.valueOf(PDid)};
    sqLiteDatabase = this.getWritableDatabase();
    int deleteProperty = sqLiteDatabase.delete(AppConstants.PROPERTY_DEPARTMENT_TABLE,where,whereArgs);
    return deleteProperty;
  }

  public int deletePropertyTrainer(int PTid){
    String where = "id=?";
    String[] whereArgs = new String[] {String.valueOf(PTid)};
    sqLiteDatabase = this.getWritableDatabase();
    int deleteProperty = sqLiteDatabase.delete(AppConstants.PROPERTY_TRAINER_TABLE,where,whereArgs);
    return deleteProperty;
  }

  public boolean updateCategories(String id ,String name, String compandyId ,String catKey) {
    try {
      ContentValues values = new ContentValues();
      sqLiteDatabase = this.getWritableDatabase();

      values.put("id", id);
      values.put("name",name);
      values.put("companyId", compandyId);
      values.put("categoryKey", catKey);

      String where = "id=?";
      String[] whereArgs = new String[]{String.valueOf(id)};

      sqLiteDatabase.update(AppConstants.CATEGORIES_TABLE, values, where, whereArgs);

      return true;
    } catch (Exception e) {
      e.printStackTrace();
      Log.d("err", e.getMessage());
      return false;
    }
  }

  public boolean updateEmployeeTable(int id ,String fname, String lname, String email, String role,int companyId,int deptId) {
    try {
      ContentValues values = new ContentValues();
      sqLiteDatabase = this.getWritableDatabase();

      values.put("id", id);
      values.put("fname", fname);
      values.put("lname", lname);
      values.put("email",email);
      values.put("role", role);
      values.put("companyId", companyId);
      values.put("departmentId", deptId);


      String where = "id=?";
      String[] whereArgs = new String[]{String.valueOf(id)};

      sqLiteDatabase.update(AppConstants.EMPLOYEE_TABLE, values, where, whereArgs);

      return true;
    } catch (Exception e) {
      e.printStackTrace();
      Log.d("err", e.getMessage());
      return false;
    }
  }

  public boolean updateProperties(String id, String name, int companyId){
    try {
      ContentValues values = new ContentValues();
      sqLiteDatabase = this.getWritableDatabase();

      values.put("id", id);
      values.put("name",name);
      values.put("companyId", companyId);


      String where = "id=?";
      String[] whereArgs = new String[]{String.valueOf(id)};

      sqLiteDatabase.update(AppConstants.PROPERTY_TABLE, values, where, whereArgs);

      return true;

    } catch (Exception e) {
      e.printStackTrace();
      Log.d("err", e.getMessage());
      return false;
    }
  }
  public boolean updateAssessmentTitle(String id, String title,  int companyId,int departmentId,int rolesId,String assessmentKey){
    try {
      ContentValues values = new ContentValues();
      sqLiteDatabase = this.getWritableDatabase();

      values.put("id", id);
      values.put("title",title);
      values.put("companyId", companyId);
      values.put("departmentId", departmentId);
      values.put("rolesId", rolesId);
      values.put("assessmentKey", assessmentKey);


      String where = "id=?";
      String[] whereArgs = new String[]{String.valueOf(id)};

      sqLiteDatabase.update(AppConstants.ASSESSMENT_TITLE_TABLE, values, where, whereArgs);

      return true;

    } catch (Exception e) {
      e.printStackTrace();
      Log.d("err", e.getMessage());
      return false;
    }
  }

  public boolean updateDepartments(String id, String name, int companyId){
    try {
      ContentValues values = new ContentValues();
      sqLiteDatabase = this.getWritableDatabase();

      values.put("id", id);
      values.put("name",name);
      values.put("companyId", companyId);


      String where = "id=?";
      String[] whereArgs = new String[]{String.valueOf(id)};

      sqLiteDatabase.update(AppConstants.DEPT_TABLE, values, where, whereArgs);

      return true;

    } catch (Exception e) {
      e.printStackTrace();
      Log.d("err", e.getMessage());
      return false;
    }
  }
  public boolean updateRole(String id, String name, int companyId,int deptId,String roleKey){
    try {
      ContentValues values = new ContentValues();
      sqLiteDatabase = this.getWritableDatabase();

      values.put("id", id);
      values.put("name",name);
      values.put("companyId", companyId);
      values.put("departmentId", deptId);
      values.put("roleKey", roleKey);


      String where = "id=?";
      String[] whereArgs = new String[]{String.valueOf(id)};

      sqLiteDatabase.update(AppConstants.ROLES_TABLE, values, where, whereArgs);

      return true;

    } catch (Exception e) {
      e.printStackTrace();
      Log.d("err", e.getMessage());
      return false;
    }
  }

  public void insertSelectedIds(int assessmentId, int propertyId, int DeptId, int trainerId, int asessmentTitleId) {
    sqLiteDatabase = getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put("assessmentId", assessmentId);
    values.put("propertyId", propertyId);
    values.put("DeptId", DeptId);
    values.put("trainerId", trainerId);
    values.put("assessmentTitleId", asessmentTitleId);
    try {
      sqLiteDatabase.insertWithOnConflict(AppConstants.SELECTION_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    } catch (SQLiteException e) {
      e.printStackTrace();
    }
  }

  public void updateRoles(int isChecked, int roleId) {
    sqLiteDatabase = this.getWritableDatabase();
    //val getSharedPrefs = SP(this.context)
    ContentValues values = new ContentValues();
    values.put("isChecked", isChecked);


    try {
      //UPDATE `response_rc_table` SET `ResponseText`='Yes' WHERE ResopnseID='1' AND ResponseAssesID='022619110702';
      sqLiteDatabase.update(
              AppConstants.ROLES_TABLE,
              values,
              "id=" + "'" + roleId + "'",
              null
      );

    } catch (SQLiteException e) {
      e.printStackTrace();
    }
  }

  public void updateCategoriesee(String cat_key, String response) {
    sqLiteDatabase = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put("response", response);

    try {
      sqLiteDatabase.update(AppConstants.CATEGORIES_TABLE, values, "categoryKey=" + "'" + cat_key + "'",
              null);
    } catch (SQLiteException e) {
      e.printStackTrace();
    }

  }

  public void updateEmployees(int isChecked, int employeeId) {
    sqLiteDatabase = this.getWritableDatabase();
    //val getSharedPrefs = SP(this.context)
    ContentValues values = new ContentValues();
    values.put("isChecked", isChecked);


    try {
      //UPDATE `response_rc_table` SET `ResponseText`='Yes' WHERE ResopnseID='1' AND ResponseAssesID='022619110702';
      sqLiteDatabase.update(
              AppConstants.EMPLOYEE_TABLE,
              values,
              "id=" + "'" + employeeId + "'",
              null
      );

    } catch (SQLiteException e) {
      e.printStackTrace();
    }
  }
  public void updateCustomEmployee(int isTemporary){
    sqLiteDatabase = this.getWritableDatabase();
    //val getSharedPrefs = SP(this.context)

    String where = "isTemporary=?";
    String[] whereArgs = new String[] {String.valueOf(isTemporary)};

    try {
      //UPDATE `response_rc_table` SET `ResponseText`='Yes' WHERE ResopnseID='1' AND ResponseAssesID='022619110702';
      sqLiteDatabase.delete(
              AppConstants.EMPLOYEE_TABLE,
              where,whereArgs
      );

    } catch (SQLiteException e) {
      e.printStackTrace();
    }
  }

  public int countEmployeeRowa(){
    sqLiteDatabase = this.getReadableDatabase();
    Cursor mCount = sqLiteDatabase.rawQuery("select count(id) from employee_table", null);
    mCount.moveToFirst();
    int count = mCount.getInt(0);
    mCount.close();
    return count;

  }

  public ArrayList<Employee> selectTemporaryEmployee(int tempId){
    ArrayList<Employee>  arrayList = new ArrayList<>();
    sqLiteDatabase = this.getReadableDatabase();
    Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM employee_table where isTemporary ="+"'" +tempId+ "'", null);
      if (cursor.moveToFirst()) {
        do {
          Employee employee = new Employee();
          employee.setCompanyId(cursor.getInt(cursor.getColumnIndex("companyId")));
          employee.setDepartmentId(cursor.getInt(cursor.getColumnIndex("departmentId")));
          employee.setFname(cursor.getString(cursor.getColumnIndex("fname")));
          employee.setLname(cursor.getString(cursor.getColumnIndex("lname")));
          employee.setEmail(cursor.getString(cursor.getColumnIndex("email")));
          employee.setRole(cursor.getString(cursor.getColumnIndex("role")));
          employee.setId(cursor.getInt(cursor.getColumnIndex("id")));
          employee.setIsChecked(cursor.getInt(cursor.getColumnIndex("isChecked")));
          employee.setEmployeeAdded(cursor.getInt(cursor.getColumnIndex("isTemporary")));


          arrayList.add(employee);
        } while (cursor.moveToNext());
      }

      cursor.close();
      sqLiteDatabase.close();
      return arrayList;
  }

  public   Employee selectUpdatedTempEmployee(int pos) {
     ArrayList<Employee>  arrayList = new ArrayList<>();
    //  sqLiteDatabase = this.getReadableDatabase();
     // Cursor mCount = sqLiteDatabase.rawQuery("SELECT * FROM employee_table where isTemporary = 0 or isTemporary =" + "'" + tempId + "'", null);
      sqLiteDatabase = this.getReadableDatabase();
    Employee employee = new Employee();
    //  Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM employee_table where isTemporary = 0 AND id=" + "'" + pos+ "'", null, null);
      Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM employee_table where isTemporary = 0 AND id=" + "'" + pos+ "'", null, null);
      if (cursor.moveToFirst()) {
        do {

          employee.setCompanyId(cursor.getInt(cursor.getColumnIndex("companyId")));
          employee.setDepartmentId(cursor.getInt(cursor.getColumnIndex("departmentId")));
          employee.setFname(cursor.getString(cursor.getColumnIndex("fname")));
          employee.setLname(cursor.getString(cursor.getColumnIndex("lname")));
          employee.setEmail(cursor.getString(cursor.getColumnIndex("email")));
          employee.setRole(cursor.getString(cursor.getColumnIndex("role")));
          employee.setId(cursor.getInt(cursor.getColumnIndex("id")));
          employee.setIsChecked(cursor.getInt(cursor.getColumnIndex("isChecked")));
          employee.setEmployeeAdded(cursor.getInt(cursor.getColumnIndex("isTemporary")));


          arrayList.add(employee);
        } while (cursor.moveToNext());
      }

      cursor.close();
      sqLiteDatabase.close();
      return employee ;

  }
 /* public ArrayList<Employee> selectCustomEmployees() {
    ArrayList<Employee> arrayList = new ArrayList<>();
    String Query = " SELECT * FROM " + AppConstants.EMPLOYEE_TABLE +" where companyId =0";
    sqLiteDatabase = this.getReadableDatabase();
    Cursor cursor = sqLiteDatabase.rawQuery(Query, null);
    if (cursor.moveToFirst()) {
      do {
        Employee employee = new Employee();
        employee.setCompanyId(cursor.getInt(cursor.getColumnIndex("companyId")));
        employee.setFname(cursor.getString(cursor.getColumnIndex("fname")));
        employee.setId(cursor.getInt(cursor.getColumnIndex("id")));
        employee.setIsChecked(cursor.getInt(cursor.getColumnIndex("isChecked")));


        arrayList.add(employee);
      } while (cursor.moveToNext());
    }

    cursor.close();
    sqLiteDatabase.close();

    return arrayList;
  }
  public void updateCustomEmployee(int isChecked,String employeeName){
    sqLiteDatabase = this.getWritableDatabase();
    //val getSharedPrefs = SP(this.context)
    ContentValues values = new ContentValues();
    values.put("isChecked", isChecked);


    try {
      //UPDATE `response_rc_table` SET `ResponseText`='Yes' WHERE ResopnseID='1' AND ResponseAssesID='022619110702';
      sqLiteDatabase.update(
              AppConstants.EMPLOYEE_TABLE,
              values,
              "fname=" + "'" + employeeName + "'",
              null
      );

    } catch (SQLiteException e) {
      e.printStackTrace();
    }
  }
*/

  /**
   * Fetching Data
   *
   * @return
   */
  public ArrayList<propertyModel> selectProperties() {

    ArrayList<propertyModel> records = new ArrayList<>();
    String selectQuery = "SELECT * FROM property_table";

    sqLiteDatabase = this.getReadableDatabase();
    Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
    if (cursor.moveToFirst()) {
      do {
        propertyModel propertyModel = new propertyModel();
        propertyModel.setCompanyId(cursor.getInt(cursor.getColumnIndex("companyId")));

        propertyModel.setName(cursor.getString(cursor.getColumnIndex("name")));
        propertyModel.setId(cursor.getInt(cursor.getColumnIndex("id")));


        records.add(propertyModel);
      } while (cursor.moveToNext());
    }

    cursor.close();
    sqLiteDatabase.close();

    return records;
  }

  public ArrayList<assessmentSavedData> selectSavedAssessmentData(){
    ArrayList<assessmentSavedData> arrayList = new ArrayList<>();
    String Query = " SELECT * FROM " + AppConstants.ASSESSMENT_LISTING_TABLE;
    sqLiteDatabase = this.getReadableDatabase();
    Cursor cursor = sqLiteDatabase.rawQuery(Query, null);
    if (cursor.moveToFirst()) {
      do {
        assessmentSavedData savedData = new assessmentSavedData();
        savedData.setAssessmentUniqueId(cursor.getInt(cursor.getColumnIndex("assessmentUniqueId")));
        savedData.setCompanyId(cursor.getInt(cursor.getColumnIndex("companyId")));
        savedData.setUserId(cursor.getInt(cursor.getColumnIndex("userId")));
        savedData.setTrainerName(cursor.getString(cursor.getColumnIndex("trainerName")));
        savedData.setDepartmentName(cursor.getString(cursor.getColumnIndex("departmentName")));
        savedData.setPropertyName(cursor.getString(cursor.getColumnIndex("propertyName")));
        savedData.setAssessmentTitle(cursor.getString(cursor.getColumnIndex("assessmentTitle")));
        savedData.setStatus(cursor.getString(cursor.getColumnIndex("status")));
        savedData.setScore(cursor.getInt(cursor.getColumnIndex("score")));
        savedData.setCategoryScores(cursor.getString(cursor.getColumnIndex("category_scores")));
        savedData.setRoles(cursor.getString(cursor.getColumnIndex("roles")));
        savedData.setEmployees(cursor.getString(cursor.getColumnIndex("employees")));
        savedData.setGeneralInfromations(cursor.getString(cursor.getColumnIndex("general_infromations")));
        savedData.setPropertyId(cursor.getInt(cursor.getColumnIndex("propertyId")));
        savedData.setAssessmetId(cursor.getInt(cursor.getColumnIndex("assessmetId")));
        savedData.setDepartmentId(cursor.getInt(cursor.getColumnIndex("departmentId")));
        savedData.setDatetime(cursor.getString(cursor.getColumnIndex("datetime")));

        arrayList.add(savedData);
      } while (cursor.moveToNext());
    }

    cursor.close();
    sqLiteDatabase.close();

    return arrayList;
  }
  public  ArrayList<assessmentSavedData> selectSavedAssessmentRoles(int assessmentUniqueId){
    ArrayList<assessmentSavedData> arrayList = new ArrayList<>();
    String Query = " SELECT * FROM " + AppConstants.ASSESSMENT_LISTING_TABLE+" Where assessmentUniqueId= "+"'"+ assessmentUniqueId+"'";
    sqLiteDatabase = this.getReadableDatabase();
    Cursor cursor = sqLiteDatabase.rawQuery(Query, null);
    if (cursor.moveToFirst()) {
      do {
        assessmentSavedData savedData = new assessmentSavedData();
        savedData.setRoles(cursor.getString(cursor.getColumnIndex("roles")));
        savedData.setCategoryScores(cursor.getString(cursor.getColumnIndex("category_scores")));
        arrayList.add(savedData);
      } while (cursor.moveToNext());
    }

    cursor.close();
    sqLiteDatabase.close();

    return arrayList;
      }
public ArrayList<CategoryModel> selectAssessmentCategories(){
  ArrayList<CategoryModel> arrayList = new ArrayList<>();
 // String Query = "SELECT categories FROM " + AppConstants.ASSESSMENT_Categories;
  String Query = "SELECT * FROM " + AppConstants.ASSESSMENT_Categories;
  sqLiteDatabase = this.getReadableDatabase();
  Cursor cursor = sqLiteDatabase.rawQuery(Query, null);
  if (cursor.moveToFirst()) {
    do {
      CategoryModel savedData = new CategoryModel();
      savedData.setKey(cursor.getString(cursor.getColumnIndex("categories")));
       savedData.setId(cursor.getString(cursor.getColumnIndex("id")));
      arrayList.add(savedData);
      Log.e("catList",arrayList.toString());
    } while (cursor.moveToNext());
  }

  cursor.close();
  sqLiteDatabase.close();

  return arrayList;
}

 public ArrayList<getAssessmentResponseObj> selectQuestionResults(int assessmentUniqueId){
    ArrayList<getAssessmentResponseObj> questionsResults = new ArrayList<>();
   String Query = " SELECT * FROM " + AppConstants.QUESTIONS_RESPONSE +" Where assessmentUniqueId= "+"'"+ assessmentUniqueId+"'";
   sqLiteDatabase = this.getReadableDatabase();
   Cursor cursor = sqLiteDatabase.rawQuery(Query, null);
   if (cursor.moveToFirst()) {
     do {
       getAssessmentResponseObj savedData = new getAssessmentResponseObj();
       savedData.setResponseTblId(cursor.getInt(cursor.getColumnIndex("id")));
       savedData.setSelection(cursor.getString(cursor.getColumnIndex("selection")));
       savedData.setComment(cursor.getString(cursor.getColumnIndex("comment")));
       savedData.setImgId(cursor.getString(cursor.getColumnIndex("imgId")));
       savedData.setQuestionText(cursor.getString(cursor.getColumnIndex("questionText")));
       savedData.setQuestionsCategories(cursor.getString(cursor.getColumnIndex("categories_responses")));
       questionsResults.add(savedData);
     } while (cursor.moveToNext());
   }

   cursor.close();
   sqLiteDatabase.close();

   return questionsResults;

 }
  public ArrayList<departmentModel> selectDepartment() {
    ArrayList<departmentModel> arrayList = new ArrayList<>();
    String Query = " SELECT * FROM " + AppConstants.DEPT_TABLE;
    sqLiteDatabase = this.getReadableDatabase();
    Cursor cursor = sqLiteDatabase.rawQuery(Query, null);
    if (cursor.moveToFirst()) {
      do {
        departmentModel departmentModels = new departmentModel();
        departmentModels.setCompanyId(cursor.getInt(cursor.getColumnIndex("companyId")));
        departmentModels.setName(cursor.getString(cursor.getColumnIndex("name")));
        departmentModels.setId(cursor.getInt(cursor.getColumnIndex("id")));

        arrayList.add(departmentModels);
      } while (cursor.moveToNext());
    }

    cursor.close();
    sqLiteDatabase.close();

    return arrayList;
  }

  public options selectIds(int id) {
    options options = new options();
//        String selectQuery = "SELECT * FROM " + AppConstants.SELECTION_TABLE+"WHERE assesmentId  ='" + propertyId + "'";
    String query = "SELECT * FROM " + AppConstants.SELECTION_TABLE + " WHERE assessmentId  ='" + id + "'";
    sqLiteDatabase = this.getReadableDatabase();
    Cursor cursor = sqLiteDatabase.rawQuery(query, null);
    if (cursor.moveToFirst()) {
      do {

        options.setAsessmentId(cursor.getInt(cursor.getColumnIndex("assessmentId")));
        options.setDeptid(cursor.getInt(cursor.getColumnIndex("DeptId")));
        options.setProprtyId(cursor.getInt(cursor.getColumnIndex("propertyId")));
        options.setTrainerId(cursor.getInt(cursor.getColumnIndex("trainerId")));
        options.setAssessmentTitleId(cursor.getInt(cursor.getColumnIndex("assessmentTitleId")));
      }
      while (cursor.moveToNext());

    }
    cursor.close();
    sqLiteDatabase.close();
    return options;
  }

  public String selectPropertyName(int Id) {
    String query = "SELECT name FROM " + AppConstants.PROPERTY_TABLE + " WHERE id  ='" + Id + "'";
    sqLiteDatabase = this.getReadableDatabase();
    String name = "";
    Cursor cursor = sqLiteDatabase.rawQuery(query, null);
    if (cursor.getCount() > 0) {
      cursor.moveToFirst();
      name = cursor.getString(cursor.getColumnIndex("name"));

    }
    return name;
  }

  public String selectDeptName(int Id) {
    String query = "SELECT name FROM " + AppConstants.DEPT_TABLE + " WHERE id  ='" + Id + "'";
    sqLiteDatabase = this.getReadableDatabase();
    String name = "";
    Cursor cursor = sqLiteDatabase.rawQuery(query, null);
    if (cursor.getCount() > 0) {
      cursor.moveToFirst();
      name = cursor.getString(cursor.getColumnIndex("name"));

    }
    return name;
  }

  public String selectAssesmentTitle(int Id) {
    String query = "SELECT title FROM " + AppConstants.ASSESSMENT_TITLE_TABLE + " WHERE id  ='" + Id + "'";
    sqLiteDatabase = this.getReadableDatabase();
    String title = "";
    Cursor cursor = sqLiteDatabase.rawQuery(query, null);
    if (cursor.getCount() > 0) {
      cursor.moveToFirst();
      title = cursor.getString(cursor.getColumnIndex("title"));

    }
    return title;
  }

  public String selectTrainer(int Id) {
    String query = "SELECT fname ,lname  FROM " + AppConstants.EMPLOYEE_TABLE + " WHERE id  ='" + Id + "'";
    sqLiteDatabase = this.getReadableDatabase();
    String fname = "";
    String lname = "";
    String name = "";
    Cursor cursor = sqLiteDatabase.rawQuery(query, null);
    if (cursor.getCount() > 0) {
      cursor.moveToFirst();
      fname = cursor.getString(cursor.getColumnIndex("fname"));
      lname = cursor.getString(cursor.getColumnIndex("lname"));
      name = fname + " " + lname;
    }
    return name;
  }


  public ArrayList<roles> selectRoles() {
    ArrayList<roles> arrayList = new ArrayList<>();
    String Query = " SELECT * FROM " + AppConstants.ROLES_TABLE + " WHERE isChecked = ?";
    sqLiteDatabase = this.getReadableDatabase();
    Cursor cursor = sqLiteDatabase.rawQuery(Query, new String[]{"1"});
    if (cursor.moveToFirst()) {
      do {
        roles roles = new roles();
        roles.setRoleKey(cursor.getString(cursor.getColumnIndex("roleKey")));
        roles.setIsChecked(cursor.getInt(cursor.getColumnIndex("isChecked")));
        roles.setName(cursor.getString(cursor.getColumnIndex("name")));
        roles.setId(cursor.getInt(cursor.getColumnIndex("id")));

        arrayList.add(roles);
      } while (cursor.moveToNext());
    }

    cursor.close();
    sqLiteDatabase.close();

    return arrayList;
  }

  public ArrayList<Employee> selectEmployees() {
    ArrayList<Employee> arrayList = new ArrayList<>();
    String Query = " SELECT * FROM " + AppConstants.EMPLOYEE_TABLE;
    sqLiteDatabase = this.getReadableDatabase();
    Cursor cursor = sqLiteDatabase.rawQuery(Query, null);
    if (cursor.moveToFirst()) {
      do {
        Employee employee = new Employee();
        employee.setCompanyId(cursor.getInt(cursor.getColumnIndex("companyId")));
        employee.setDepartmentId(cursor.getInt(cursor.getColumnIndex("departmentId")));
        employee.setFname(cursor.getString(cursor.getColumnIndex("fname")));
        employee.setLname(cursor.getString(cursor.getColumnIndex("lname")));
        employee.setEmail(cursor.getString(cursor.getColumnIndex("email")));
        employee.setRole(cursor.getString(cursor.getColumnIndex("role")));
        employee.setId(cursor.getInt(cursor.getColumnIndex("id")));
        employee.setIsChecked(cursor.getInt(cursor.getColumnIndex("isChecked")));
        employee.setEmployeeAdded(cursor.getInt(cursor.getColumnIndex("isTemporary")));


        arrayList.add(employee);
      } while (cursor.moveToNext());
    }

    cursor.close();
    sqLiteDatabase.close();

    return arrayList;
  }



  public int totalSum(){
    sqLiteDatabase = this.getReadableDatabase();
    Cursor mCount = sqLiteDatabase.rawQuery("select count(isChecked) from employee_table WHERE isChecked = 1", null);
    mCount.moveToFirst();
    int count = mCount.getInt(0);
    mCount.close();
    return count;
  }

  public int totalTemp(){
    sqLiteDatabase = this.getReadableDatabase();
  //  Cursor mCount = sqLiteDatabase.rawQuery("select count(isTemporary) from employee_table WHERE isTemporary >0 ", null);
    Cursor mCount = sqLiteDatabase.rawQuery("SELECT * FROM employee_table where isTemporary !=0 ", null);
    mCount.moveToFirst();
    int count = mCount.getInt(0);
    mCount.close();
    return count;
  }
  public ArrayList<Employee> selectEmployeesIsChecked() {
    ArrayList<Employee> arrayList = new ArrayList<>();
    String Query = " SELECT * FROM " + AppConstants.EMPLOYEE_TABLE + " WHERE isChecked = ?";
    sqLiteDatabase = this.getReadableDatabase();
    Cursor cursor = sqLiteDatabase.rawQuery(Query, null);
    if (cursor.moveToFirst()) {
      do {
        Employee employee = new Employee();
        employee.setCompanyId(cursor.getInt(cursor.getColumnIndex("companyId")));
        employee.setDepartmentId(cursor.getInt(cursor.getColumnIndex("departmentId")));
        employee.setFname(cursor.getString(cursor.getColumnIndex("fname")));
        employee.setLname(cursor.getString(cursor.getColumnIndex("lname")));
        employee.setIsChecked(cursor.getInt(cursor.getColumnIndex("isChecked")));

        employee.setEmail(cursor.getString(cursor.getColumnIndex("email")));
        employee.setRole(cursor.getString(cursor.getColumnIndex("role")));
        employee.setId(cursor.getInt(cursor.getColumnIndex("id")));


        arrayList.add(employee);
      } while (cursor.moveToNext());
    }

    cursor.close();
    sqLiteDatabase.close();

    return arrayList;
  }

  public ArrayList<User> selectUsers() {
    ArrayList<User> arrayList = new ArrayList<>();
    String Query = " SELECT * FROM " + AppConstants.USER_TABLE;
    sqLiteDatabase = this.getReadableDatabase();
    Cursor cursor = sqLiteDatabase.rawQuery(Query, null);
    if (cursor.moveToFirst()) {
      do {
        User user = new User();
        user.setCompanyId(cursor.getInt(cursor.getColumnIndex("companyId")));
        user.setFname(cursor.getString(cursor.getColumnIndex("fname")));
        user.setLname(cursor.getString(cursor.getColumnIndex("lname")));
        user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
        user.setRole(cursor.getString(cursor.getColumnIndex("role")));
        user.setId(cursor.getInt(cursor.getColumnIndex("id")));

        arrayList.add(user);
      } while (cursor.moveToNext());
    }

    cursor.close();
    sqLiteDatabase.close();

    return arrayList;
  }

  public ArrayList<AssmessmentModel> selectAssessmentTitle() {
    ArrayList<AssmessmentModel> arrayList = new ArrayList<>();
    String Query = " SELECT * FROM " + AppConstants.ASSESSMENT_TITLE_TABLE;
    sqLiteDatabase = this.getReadableDatabase();
    Cursor cursor = sqLiteDatabase.rawQuery(Query, null);
    if (cursor.moveToFirst()) {
      do {
        AssmessmentModel assmessmentModel = new AssmessmentModel();
        assmessmentModel.setCompanyId(cursor.getInt(cursor.getColumnIndex("companyId")));
        assmessmentModel.setDepartmentId(cursor.getInt(cursor.getColumnIndex("departmentId")));
        assmessmentModel.setAssessmentKey(cursor.getString(cursor.getColumnIndex("assessmentKey")));
        assmessmentModel.setTitle(cursor.getString(cursor.getColumnIndex("title")));
        assmessmentModel.setId(cursor.getInt(cursor.getColumnIndex("id")));
        assmessmentModel.setRolesId(cursor.getInt(cursor.getColumnIndex("rolesId")));

        arrayList.add(assmessmentModel);
      } while (cursor.moveToNext());
    }

    cursor.close();
    sqLiteDatabase.close();

    return arrayList;
  }

  public ArrayList<StandardQuestion> selectStandardQuestions() {
    ArrayList<StandardQuestion> arrayList = new ArrayList<>();
    String Query = " SELECT * FROM " + AppConstants.STANDARD_QUESTIONS_TABLE;
    sqLiteDatabase = this.getReadableDatabase();
    Cursor cursor = sqLiteDatabase.rawQuery(Query, null);
    if (cursor.moveToFirst()) {
      do {
        StandardQuestion question = new StandardQuestion();
        question.setCompanyId(cursor.getInt(cursor.getColumnIndex("id")));
        question.setAssessmentNumber(cursor.getString(cursor.getColumnIndex("assessmentNumber")));

        question.setAssessmentQuestion(cursor.getString(cursor.getColumnIndex("assessmentQuestion")));
        question.setSectionName(cursor.getString(cursor.getColumnIndex("sectionName")));
        question.setCompanyId(cursor.getInt(cursor.getColumnIndex("companyId")));

        arrayList.add(question);
      } while (cursor.moveToNext());
    }

    cursor.close();
    sqLiteDatabase.close();

    return arrayList;
  }


  public ArrayList<Category> selectCategories() {
    ArrayList<Category> arrayList = new ArrayList<>();
    String Query = " SELECT * FROM " + AppConstants.CATEGORIES_TABLE;
    sqLiteDatabase = this.getReadableDatabase();
    Cursor cursor = sqLiteDatabase.rawQuery(Query, null);
    if (cursor.moveToFirst()) {
      do {
        Category question = new Category();
        question.setId(cursor.getInt(cursor.getColumnIndex("id")));
        question.setName(cursor.getString(cursor.getColumnIndex("name")));
        question.setCompanyId(cursor.getInt(cursor.getColumnIndex("companyId")));
        question.setCategoryKey(cursor.getString(cursor.getColumnIndex("categoryKey")));


        arrayList.add(question);
      } while (cursor.moveToNext());
    }

    cursor.close();
    sqLiteDatabase.close();

    return arrayList;
  }

    public int deleteCustomEmployees(int AssID){
        String where = "isChecked=0 AND isTemporary=?";
        String[] whereArgs = new String[] {String.valueOf(AssID),};
        sqLiteDatabase = this.getWritableDatabase();
        int deleted = sqLiteDatabase.delete(AppConstants.EMPLOYEE_TABLE, where, whereArgs);
        return deleted;

    }
  /**
   * Fetching Mapping List Data
   *
   * @return
   */
  public ArrayList<departmentModel> selectPropertyDepartmentMapping(int propertyId, int companyId) {
    ArrayList<departmentModel> arrayList = new ArrayList<>();

    String Query = "SELECT  d.id,d.name FROM department_table d JOIN property_department_mapping_table pd " +
            "ON pd.departmentId = d.id WHERE pd.propertyId ='" + propertyId + "'" + "AND pd.companyId =" + "'" + companyId + "'";


    sqLiteDatabase = this.getReadableDatabase();
    Cursor cursor = sqLiteDatabase.rawQuery(Query, null);
    if (cursor.getCount() > 0)
      if (cursor != null && cursor.moveToFirst()) {
        do {
          departmentModel departmentModel = new departmentModel();
          departmentModel.setName(cursor.getString(cursor.getColumnIndex("name")));
          departmentModel.setId(cursor.getInt(cursor.getColumnIndex("id")));

          arrayList.add(departmentModel);
        } while (cursor.moveToNext());
      }

    cursor.close();
    sqLiteDatabase.close();

    return arrayList;
  }

  // Department Assessment Mapping
  public ArrayList<AssmessmentModel> selectDepartmentAssessmentMapping(int departmentId) {
    ArrayList<AssmessmentModel> arrayList = new ArrayList<>();

    String Query = "SELECT  AT.id,AT.title FROM  aasessment_title_table AT" +
            " INNER JOIN department_assessment_mapping_table DAP ON DAP.assessmentId = AT.id WHERE  DAP.departmentId ='" + departmentId + "'";
    //" SELECT * FROM " + AppConstants.DEPT_ASSESSMENT_TABLE;
    sqLiteDatabase = this.getReadableDatabase();

    Cursor cursor = sqLiteDatabase.rawQuery(Query, null);
    if (cursor.getCount() > 0)
      if (cursor != null && cursor.moveToFirst()) {
        do {
          AssmessmentModel mapping = new AssmessmentModel();
          mapping.setId(cursor.getInt(cursor.getColumnIndex("id")));
          mapping.setTitle(cursor.getString(cursor.getColumnIndex("title")));
          arrayList.add(mapping);
        } while (cursor.moveToNext());
      }

    cursor.close();
    sqLiteDatabase.close();

    return arrayList;

  }

  //Assessment - Role Mapping
  public ArrayList<roles> selectAssessmentRoleMappings(int assessmentId) {
    ArrayList<roles> mappings = new ArrayList<>();
    String Query = "SELECT RM.id , RM.name from roles_table RM INNER JOIN assessment_role_mapping_table ARM ON RM.id = ARM.roleId" +
            "  where ARM.assessmentId ='" + assessmentId + "'" + "order by RM.name";
    //" SELECT * FROM " + AppConstants.ASSESSMENT_ROLE_TABLE;
    sqLiteDatabase = this.getReadableDatabase();
    Cursor cursor = sqLiteDatabase.rawQuery(Query, null);
    if (cursor.moveToFirst()) {
      do {
        roles rolesmapping = new roles();
        rolesmapping.setId(cursor.getInt(cursor.getColumnIndex("id")));
        rolesmapping.setName(cursor.getString(cursor.getColumnIndex("name")));

        mappings.add(rolesmapping);
      } while (cursor.moveToNext());
    }

    cursor.close();
    sqLiteDatabase.close();

    return mappings;
  }


  //Property Trainer Mapping
  public ArrayList<Employee> selectPropertyTrainerMappings(int propertyId) {
    ArrayList<Employee> employeeArrayList = new ArrayList<>();
    String Quesry = " SELECT E.fname ,E.lname, E.id FROM  employee_table E INNER JOIN property_trainer_mapping_table PTM ON E.id = PTM.employeeId WHERE PTM.propertyId ='" + propertyId + "'";
    // String Quesry = " SELECT E.fname ,E.lname, E.id FROM  employee_table E INNER JOIN property_trainer_mapping_table PTM ON E.id = PTM.employeeId WHERE PTM.propertyId ='" + propertyId + "'  AND E.role = 'Trainer'";

    sqLiteDatabase = this.getReadableDatabase();
    Cursor cursor = sqLiteDatabase.rawQuery(Quesry, null);
    if (cursor.getCount() > 0)
      if (cursor != null && cursor.moveToNext()) {
        do {

          Employee employee = new Employee();
          employee.setId(cursor.getInt(cursor.getColumnIndex("id")));
          employee.setFname(cursor.getString(cursor.getColumnIndex("fname")));
          employee.setLname(cursor.getString(cursor.getColumnIndex("lname")));
          employeeArrayList.add(employee);

        } while (cursor.moveToNext());
      }
    cursor.close();
    sqLiteDatabase.close();
    return employeeArrayList;
  }

}
