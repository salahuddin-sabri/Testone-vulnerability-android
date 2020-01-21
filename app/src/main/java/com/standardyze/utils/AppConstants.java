package com.standardyze.utils;


import com.standardyze.wrapper.propertyModel;

import java.util.List;

public class AppConstants {

	/**
	 *  BasicAWSCredentials
	 */
	public static String KEY = "AKIAJ4DDXAATIVJBCJPQ";
	public static String SECRET = "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";
	public static String BUCKET_NAME= "jamshed";

	public static String USER_NAME = "UserName";
	public static String USER_EMAIL = "UserEmail";
	public static String USER_PASSWORD = "UserPassword";
	public static String USER_ID = "UserID";
	public static String ACTIVATION_CODE = "ActivationCode";

	public static boolean onTest = true;
	public static final String LOG_TAG = "Standardyze";

	/**
	 * Server Base URL And Configuration Section Start
	 */

	public static final String SERVER_BASE_URL = "http://ec2-3-92-174-152.compute-1.amazonaws.com:4554/standardyze/";


	/**
	 * Server Base URL And Configuration Section End
	 */

	/***
	 * Live URL
	 */
	public static final String SERVER_BASE_URL_Live = "";


	/**
	 * Preferences Constants Section Start
	 */

	public static final String IS_LOGIN = "is_logged_in";
	public static final String SAVED_TO_SERVER="Saved";
	public static final String SUBMITTED_TO_SERVER="Submitted";
	public static final String IS_Created = "is_Created";
	public static final String KEY_USER = "key_user";

	public static final int lastUpdateDate = 1570186488;
	public static final String getDate = "updatedDate";


	public static List<propertyModel> propertyList;

	/**
	 * Preferences Constants Section End
	 */


	/***
	 * Database table Keys
	 */

	public static String PROPERTY_TABLE = "property_table";
	public static String DEPT_TABLE = "department_table";
	public static String TRAINER_TABLE = "trainer_table";
	public static String ASSESSMENT_TITLE_TABLE = "aasessment_title_table";
	public static String ROLES_TABLE = "roles_table";
	public static String EMPLOYEE_TABLE = "employee_table";
	public static String USER_TABLE = "user_table";
	public static  String PROPERTY_DEPARTMENT_TABLE = "property_department_mapping_table";
	public static  String DEPT_ASSESSMENT_TABLE = "department_assessment_mapping_table";
	public static  String ASSESSMENT_ROLE_TABLE = "assessment_role_mapping_table";
	public static  String PROPERTY_TRAINER_TABLE = "property_trainer_mapping_table";
	public  static String STANDARD_QUESTIONS_TABLE= "standard_questions_table";
	public static  String CATEGORIES_TABLE ="categories_table";
	public static  String SELECTION_TABLE ="selection_table";
	public static  String GENERAL_INFO_TABLE="General_info_table";


	public static  String ASSESSMENT_LISTING_TABLE = "assessment_listing_table";
	public static  String QUESTIONS_RESPONSE = "questions_reponse";
	public static  String ASSESSMENT_Categories = "assessment_categories";


	/**
	 *  DataBase Table Queries
	 */
	public static String CREATE_PROPERTY = "CREATE TABLE " + PROPERTY_TABLE + "" +
			"(id INTEGER PRIMARY KEY not null UNIQUE, name TEXT, companyId INTEGER)";

	public static String CREATE_DEPT = "CREATE TABLE " + DEPT_TABLE + "" +
			"(id INTEGER PRIMARY KEY not null UNIQUE , name TEXT, companyId INTEGER)";

	public static String CREATE_ASSESMENT_TITLE = "CREATE TABLE " + ASSESSMENT_TITLE_TABLE + "" +
			"(id INTEGER PRIMARY KEY not null UNIQUE, title TEXT, rolesId INTEGER, departmentId INTEGER ,companyId INTEGER ,assessmentKey TEXT)";

   /* public static String CREATE_TRAINER = "CREATE TABLE " + TRAINER_TABLE + "" +
            "(id INTEGER , name TEXT, companyId INTEGER)";*/

	public static String CREATE_ROLES = "CREATE TABLE " + ROLES_TABLE + "" +
			"(id INTEGER PRIMARY KEY not null UNIQUE, name TEXT, companyId INTEGER , departmentId INTEGER, roleKey TEXT, isChecked INTEGER)";

	public static String CREATE_EMPLOYEES = "CREATE TABLE " + EMPLOYEE_TABLE + "" +
			"(id INTEGER PRIMARY KEY not null UNIQUE, fname TEXT, lname TEXT , email TEXT ,role TEXT ,companyId INTEGER , departmentId INTEGER, " +
			" isChecked INTEGER , isTemporary INTEGER)";

	public static String Create_USER =  "CREATE TABLE " + USER_TABLE + "" +
			"(id INTEGER PRIMARY KEY not null UNIQUE, fname TEXT, lname TEXT , email TEXT ,role TEXT ,companyId INTEGER)";

	public static String CREATE_PROP_DEPT_MAPPING = "CREATE TABLE " + PROPERTY_DEPARTMENT_TABLE + "" +
			"(id INTEGER PRIMARY KEY not null UNIQUE, propertyId INTEGER , departmentId INTEGER, companyId INTEGER)";

	public static String CREATE_DEPT_ASSESMENT_MAPPING = "CREATE TABLE " + DEPT_ASSESSMENT_TABLE + "" +
			"(id INTEGER PRIMARY KEY not null UNIQUE, assessmentId INTEGER , departmentId INTEGER, companyId INTEGER)";

	public static String CREATE_ASSESMENT_ROLE_MAPPING = "CREATE TABLE " + ASSESSMENT_ROLE_TABLE + "" +
			"(id INTEGER PRIMARY KEY not null UNIQUE, assessmentId INTEGER , roleId INTEGER, companyId INTEGER)";
	public static String CREATE_PROPERTY_TRAINER_MAPPING =  "CREATE TABLE " + PROPERTY_TRAINER_TABLE + "" +
			"(id INTEGER PRIMARY KEY not null UNIQUE, propertyId INTEGER , employeeId INTEGER, companyId INTEGER)";

	public static String CREATE_STANDARD_QUESTIONS =  "CREATE TABLE " + STANDARD_QUESTIONS_TABLE + "" +
			"(id INTEGER PRIMARY KEY not null UNIQUE, assessmentNumber TEXT , assessmentQuestion TEXT, sectionName TEXT, companyId INTEGER)";
	public static String CREATE_CATEGORIES = "CREATE TABLE " + CATEGORIES_TABLE + "" +
			"(id INTEGER PRIMARY KEY not null UNIQUE, name TEXT , companyId INTEGER, categoryKey TEXT, response TEXT)";

	public static  String CREATE_SELECTION_ID = "CREATE TABLE "+ SELECTION_TABLE + "" +
			"(assessmentId INTEGER PRIMARY KEY not null UNIQUE, propertyId INTEGER, DeptId INTEGER, trainerId INTEGER, assessmentTitleId INTEGER)";

	public static  String CREATE_ASSESSMENT_LISTING = "CREATE TABLE " + ASSESSMENT_LISTING_TABLE + "" +
			"(id INTEGER PRIMARY KEY not null UNIQUE, assessmentUniqueId INTEGER, companyId INTEGER, userId INTEGER, trainerName TEXT ,departmentName TEXT , propertyName TEXT," +
			"assessmentTitle TEXT , status TEXT ,score INTEGER ,category_scores TEXT , roles TEXT,employees TEXT, general_infromations TEXT , propertyId INTEGER" +
			",assessmetId INTEGER, departmentId INTEGER, datetime TEXT)";

	public static String CREATE_QUESTIONS_RESPONSE = "CREATE TABLE " + QUESTIONS_RESPONSE + "" +
			"(id INTEGER PRIMARY KEY not null UNIQUE, assessmentUniqueId INTEGER, selection TEXT , comment TEXT, imgId INTEGER,  questionText TEXT, questionId INTEGER, categories_responses TEXT)";

	public static String CREATE_ASSESSMENT_CATEGORIES= "CREATE TABLE " + ASSESSMENT_Categories + "" +"(id INTEGER PRIMARY KEY not null UNIQUE, categories TEXT)";
	/**
	 * Drop Tables
	 */

	public static String DROP_CREATE_PROPERTY = "DROP TABLE IF EXISTS "+PROPERTY_TABLE;
	public static String DROP_CREATE_DEPT = "DROP TABLE IF EXISTS "+DEPT_TABLE;
	public static String DROP_CREATE_ASSESSMENT_TITLE = "DROP TABLE IF EXISTS "+ASSESSMENT_TITLE_TABLE;
	public static String DROP_CREATE_ROLES = "DROP TABLE IF EXISTS "+ROLES_TABLE;
	public static String DROP_CREATE_EMPLOYEES = "DROP TABLE IF EXISTS "+EMPLOYEE_TABLE;
	public static String DROP_Create_USER = "DROP TABLE IF EXISTS "+USER_TABLE;
	public static String DROP_CREATE_PROP_DEPT_MAPPING = "DROP TABLE IF EXISTS "+PROPERTY_DEPARTMENT_TABLE;
	public static String DROP_CREATE_DEPT_ASSESSMENT_MAPPING = "DROP TABLE IF EXISTS "+DEPT_ASSESSMENT_TABLE;
	public static String DROP_CREATE_ASSESSMENT_ROLE_MAPPING = "DROP TABLE IF EXISTS "+ASSESSMENT_ROLE_TABLE;
	public static String DROP_CREATE_STANDARD_QUESTIONS = "DROP TABLE IF EXISTS "+STANDARD_QUESTIONS_TABLE;
	public static String DROP_CREATE_CATEGORIES = "DROP TABLE IF EXISTS "+CATEGORIES_TABLE;
	public static String DROP_CREATE_SELECTION = "DROP TABLE IF EXISTS "+SELECTION_TABLE;
	public static String DROP_CREATE_PROPERTY_TRAINER_MAPPING = "DROP TABLE IF EXISTS "+PROPERTY_TRAINER_TABLE;
	public static String DROP_CREATE_ASSESMENT_LISTING = "DROP TABLE IF EXISTS "+ASSESSMENT_LISTING_TABLE;
	public static String DROP_CREATE_QUESTIONS_RESPONSE = "DROP TABLE IF EXISTS "+QUESTIONS_RESPONSE;


}
