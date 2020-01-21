package com.standardyze.activites;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.google.gson.Gson;
import com.standardyze.App.SessionClass;
import com.standardyze.R;
import com.standardyze.adapters.savedData_adapter;
import com.standardyze.baseActivity;
import com.standardyze.customViews.CustomTextViewLight;
import com.standardyze.models.CategoryModel;
import com.standardyze.models.options;
import com.standardyze.networking.WebServiceFactory;
import com.standardyze.utils.AppConstants;
import com.standardyze.utils.Compressor;
import com.standardyze.utils.DatabaseHelper;
import com.standardyze.utils.SharedPrefManager;
import com.standardyze.wrapper.FilterQuestionsResponse;
import com.standardyze.wrapper.General_infromations;
import com.standardyze.wrapper.QuestionsResult;
import com.standardyze.wrapper.getAssessmentQuestionsResponse;
import com.standardyze.wrapper.getAssessmentResponseObj;
import com.standardyze.wrapper.roles;
import com.standardyze.wrapper.saveResponseObject;
import com.theartofdev.edmodo.cropper.CropImage;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class savedData extends baseActivity {
    @BindView(R.id.recycler_view_questions)
    RecyclerView recyclerView;
    @BindView(R.id.date_txt)
    CustomTextViewLight dateText;
    @BindView(R.id.next_btn)
    Button next_btn;
    LinearLayoutManager linearLayoutManager;
    ArrayList<options> arrayList;
    savedData_adapter service_adapter;
    ArrayList<getAssessmentResponseObj> questionsResultArrayList;
    ArrayList<com.standardyze.wrapper.saveResponseObject> saveResponseObjectArrayList;
    saveResponseObject saveResponseObject;
    String roleKey;
    FilterQuestionsResponse filterQuestionsResponse;
    DatabaseHelper databaseHelper;
    QuestionsResult questionsResult;
    getAssessmentResponseObj getAssessmentResponseObj;
    String status;
    String savedEmployees;
    ArrayList<General_infromations> general_infromationsArrayList;
    List<CategoryModel> commonData;
    int count = 0;
    int pos;
    File finalFile;
    AmazonS3Client s3Client;
    URL presignedUrl;
    String fileName;
    String dateTime;
    private ArrayList<roles> rolesArraylist;
    SharedPrefManager sharedPrefManager;
    private ArrayList<CategoryModel> categoriesList;
    @BindView(R.id.rotateloadingAS)
    RotateLoading rotateLoading;
    General_infromations general_infromations;
    String gI;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrival_service);
        ButterKnife.bind(this);
        showActionBar("Arrival Service", null, false, false, true, false);
        init();
        AWSMobileClient.getInstance().initialize(this).execute();
        BasicAWSCredentials credentials = new BasicAWSCredentials(AppConstants.KEY, AppConstants.SECRET);
        s3Client = new AmazonS3Client(credentials);
    //    s3Client.setObjectAcl(AppConstants.BUCKET_NAME,AppConstants.KEY, CannedAccessControlList.PublicReadWrite);
        BroadcastReceiver mServiceReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                general_infromationsArrayList = new ArrayList<>();

                general_infromationsArrayList = (ArrayList) intent.getSerializableExtra("general_info_list");
                Log.e("general_infromations", "" + general_infromationsArrayList);
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(mServiceReceiver, new IntentFilter("com.tutorialspoint.CUSTOM_INTENT"));
    }

    public void init() {
        // arrayList = new ArrayList<>();
        checkPermission();
        sharedPrefManager = new SharedPrefManager(this);
        next_btn.setVisibility(View.GONE);
        getAssessmentResponseObj = new getAssessmentResponseObj();
        saveResponseObjectArrayList = new ArrayList<>();
        databaseHelper = new DatabaseHelper(this);
        questionsResultArrayList = new ArrayList<>();
        intent = getIntent();
        assert intent != null;
        status = intent.getStringExtra("status");
        savedEmployees = intent.getStringExtra("savedEmployees");
        sharedPrefManager.setStringForKey(savedEmployees,"savedEmployees");




        dateTime = intent.getStringExtra("dateTime");
        gI  = intent.getStringExtra("generalInfo");
        Log.e("GI",""+gI);
        if (gI!=null){
            sharedPrefManager.setStringForKey((gI),"SavedGI");
        }

   sharedPrefManager.clearKey("SubmittedGI");

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        service_adapter = new savedData_adapter(this, questionsResultArrayList, new savedData_adapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position, int responseTblId, String questionText, String selection, String comment, String imgId, int totalSelection, String imgURL) {
                pos = position;

                saveResponseObject = new saveResponseObject(responseTblId, selection, comment, imgId, questionText, getImageUrl(imgId));

                saveResponseObjectArrayList.clear();

                saveResponseObjectArrayList.add(saveResponseObject);


/*
                for (int i = 0; i < saveResponseObjectArrayList.size(); i++) {
                    Log.e("Selection", "" + saveResponseObjectArrayList.get(i).getSelection());
                    if (saveResponseObjectArrayList.get(i).getSelection().equalsIgnoreCase("YES")) {
                        Toast.makeText(savedData.this, "" + count, Toast.LENGTH_SHORT).show();

                    }
                }*/
            }
        });


        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(service_adapter);

      /*  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.e("dattte", "" +  DateFormat.getDateInstance(DateFormat.MEDIUM).format(( date)));*/
        String date = new SimpleDateFormat("d MMM yyyy", Locale.getDefault()).format(new Date());
        dateText.setText("Dated: " + dateTime);


        int uID = SessionClass.getInstance().getUser(this).getId();
        int assessmenntID = intent.getIntExtra("id",0);
        int companyId = SessionClass.getInstance().getUser(this).getCompanyId();

        if (checkConnection()) {
            getSubmittedData(uID, companyId, assessmenntID);
        } else {
         //   getDataFromDb();
        }


    }


    public void getDataFromDb() {
        next_btn.setVisibility(View.VISIBLE);
        rolesArraylist = new ArrayList<>();
 /*     for (int i =0 ;i <databaseHelper.selectQuestionResults().size();i++){

          if (id == sharedPrefManager.getIntegerByKey("assessmentID")){
              Toast.makeText(this, "", Toast.LENGTH_SHORT).show();

              getAssessmentResponseObj  questionsResult = new getAssessmentResponseObj();
              questionsResult.setResponseTblId(databaseHelper.selectQuestionResults().get(i).getId());
              questionsResult.setImgId(databaseHelper.selectQuestionResults().get(i).getImgId());
              questionsResult.setSelection(databaseHelper.selectQuestionResults().get(i).getSelection());
              questionsResult.setComment(databaseHelper.selectQuestionResults().get(i).getComment());
              questionsResult.setQuestionText(databaseHelper.selectQuestionResults().get(i).getQuestion());
         */
        //   }
        //   databaseHelper.selectQuestionResults(sharedPrefManager.getIntegerByKey("assessmentID"));
        questionsResultArrayList.addAll(databaseHelper.selectQuestionResults(sharedPrefManager.getIntegerByKey("assessmentID")));
        service_adapter.notifyDataSetChanged();

        //Roles
        for (int j = 0; j < databaseHelper.selectSavedAssessmentRoles(sharedPrefManager.getIntegerByKey("assessmentID")).size(); j++) {
            String value = databaseHelper.selectSavedAssessmentRoles(sharedPrefManager.getIntegerByKey("assessmentID")).get(j).getRoles().
                    substring(1, databaseHelper.selectSavedAssessmentRoles(sharedPrefManager.getIntegerByKey("assessmentID")).get(j).getRoles().length() - 1);
            value = value.substring(1, value.length() - 1);           //remove curly brackets
            String[] keyValuePairs = value.split(",");              //split the string to creat key-value pairs
            Map<String, String> stringStringHashMap = new HashMap<>();

            for (String pair : keyValuePairs)                        //iterate over the pairs
            {
                String[] entry = pair.split(":");                   //split the pairs to get key and value
                stringStringHashMap.put(entry[0].trim(), entry[1].trim().substring(1, entry[1].length() - 1));
                roles roles = new roles();

                roles.setName(entry[1].trim().substring(1, entry[1].length() - 1));
                rolesArraylist.add(roles);//add them to the hashmap and trim whitespaces
            }
        }

        //Categories
        Log.e("arraylist",databaseHelper.selectAssessmentCategories().toString());
       /* for (int i =0;i<databaseHelper.selectAssessmentCategories().size();i++){
            String cat= databaseHelper.selectAssessmentCategories().get(i).getKey().substring(1,databaseHelper.selectAssessmentCategories().get(i).getKey().length()-1);
            cat = cat.substring(1,cat.length()-1);
            String[] strings = cat.split(",");
            Log.e("String",""+strings);
        }*/


    /*    //Categories.
        for (int k = 0; k < databaseHelper.selectAssessmentCategories(sharedPrefManager.getIntegerByKey("assessmentID")).size(); k++) {
            String categories = databaseHelper.selectAssessmentCategories(sharedPrefManager.getIntegerByKey("assessmentID")).get(k).getCategoryScores()
                    .substring(1,databaseHelper.selectAssessmentCategories(sharedPrefManager.getIntegerByKey("assessmentID")).get(k).getCategoryScores().length()-1);


             categories = categories.substring(1, categories.length()-1);           //remove curly brackets
             String[] Pairs=  categories.split(",") ;

            //split the string to creat key-value pairs
            Map<String, String> hashMap = new HashMap<>();
            commonData = new ArrayList<>();

            for (String s : Pairs) {
                String[] p = s.split(":");
                hashMap.put(p[0].trim().substring(0,p[0].length()-1), p[1].trim());
              //  Log.e("list", hashMap.put(p[0].trim(), p[1].trim()));


                CategoryModel categoryModel = new CategoryModel();

                categoryModel.setKey(p[0].trim().substring(0, p[0].length() - 1));
                categoryModel.setValue(p[1].trim());
                commonData.add(categoryModel);
            }
        }*/


    }

    public void getSubmittedData(int userId, int companyId, int assessmentId) {
        rotateLoading.start();
        WebServiceFactory.getInstance().getSubmittedData(userId, companyId, assessmentId).enqueue(new Callback<getAssessmentQuestionsResponse>() {
            @Override
            public void onResponse(Call<getAssessmentQuestionsResponse> call, Response<getAssessmentQuestionsResponse> response) {
                try {
                    JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                    getAssessmentQuestionsResponse getAssessmentQuestionsResponse = new Gson().fromJson(jsonObject.toString(), getAssessmentQuestionsResponse.class);
                    if (getAssessmentQuestionsResponse.getStatusCode() == 1) {
                        rotateLoading.stop();
                        next_btn.setVisibility(View.VISIBLE);
                        commonData = new ArrayList<>();
                        rolesArraylist = new ArrayList<>();
                        int AssessmentTblId = getAssessmentQuestionsResponse.getResults().getAssessmentTblId();
                      /*  Gson gson = new Gson();
                        gson.toJson(getAssessmentQuestionsResponse.getResults().getRoles());
                        */

                        HashMap<String, String> hashMap = getAssessmentQuestionsResponse.getResults().getRoles();
                        for (String key : hashMap.keySet()) {
                            roles roles = new roles();
                            roles.setName(hashMap.get(key));
                            rolesArraylist.add(roles);
                        }
                        SharedPrefManager sharedPrefManager = new SharedPrefManager(savedData.this);
                        sharedPrefManager.setIntegerForKey(AssessmentTblId, "AssessmentTblId");

                        questionsResultArrayList.addAll(getAssessmentQuestionsResponse.getResults().getResponseObject());
                        service_adapter.notifyDataSetChanged();


                        for (int i = 0; i < getAssessmentQuestionsResponse.getResults().getResponseObject().size(); i++) {
                            HashMap<String, String> categories = getAssessmentQuestionsResponse.getResults().getResponseObject().get(i).getCategories();


                            for (String key : categories.keySet()) {
                                CategoryModel categoryModel = new CategoryModel();
                                categoryModel.setKey(key);
                                if (commonData.contains(categoryModel)) {
                                    int index = commonData.indexOf(categoryModel);
                                    categoryModel = commonData.get(index);

                                } else {

                                    commonData.add(categoryModel);
                                }
                                categoryModel.setValue(categories.get(key));
                                if (categoryModel.getValue().equalsIgnoreCase("Yes")) {
                                    int yesCount = categoryModel.getYesCount();
                                    ++yesCount;
                                    categoryModel.setYesCount(yesCount);

                                } else {
                                    int noCount = categoryModel.getNoCount();
                                    ++noCount;
                                    categoryModel.setNoCount(noCount);
                                }
                            }


//                            categoryResults.add(filterQuestionsResponse.getResults().get(i).getCategories());


                        }
                        Log("testing");

                    }
                    Log.e("Response", "  " + getAssessmentQuestionsResponse.getResults().getResponseObject());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<getAssessmentQuestionsResponse> call, Throwable t) {

            }
        });
    }


    @OnClick(R.id.next_btn)
    public void setNext_btn() {
        Gson gson = new Gson();
      //  String general_info = gson.toJson(general_infromations);
        String general_info =intent.getStringExtra("generalInfo");
        if (databaseHelper.totalSum()>=0) {
            Intent intent = new Intent(this, summary.class);
            intent.putExtra("updatedList", gson.toJson(questionsResultArrayList));
            intent.putExtra("savedData", "savedData");
            intent.putExtra("status", status);
            if ( general_info.equalsIgnoreCase("N/A") || general_infromationsArrayList == null) {
                intent.putExtra("Saved_general_info", general_info);
            } else if (!general_infromationsArrayList.isEmpty()){
                intent.putExtra("general_info", gson.toJson(general_infromationsArrayList));
            }

            intent.putExtra("catList", gson.toJson(commonData));
            intent.putExtra("categoriesList", gson.toJson(categoriesList));
            intent.putExtra("updatedRolesArrayList", gson.toJson(rolesArraylist));
            startActivity(intent);
        }
 else {
                showDialogAlert(R.string.alert_msg_add_employee);

            }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            //Capture Image
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                Uri resultUri = result.getUri();

          /*      try {


                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
              *//*

                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100 *//**//*ignored for PNG*//**//*, bos);
                    byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
                    FileOutputStream fos = new FileOutputStream(finalFile);
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();*//*

                    compressImage(bitmap);
                    *//*OutputStream os = new BufferedOutputStream(new FileOutputStream(finalFile));
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, os);
                    os.close();*//*

                } catch (IOException e) {
                    e.printStackTrace();
                }*/


                Log.e("uri", "" + resultUri);
                finalFile = new File(resultUri.getPath());
                File compressedImgFile =
                        null;
                try {
                    compressedImgFile = new Compressor(this).compressToFile(finalFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                fileName = finalFile.getName().replace("cropped", "").replace(".jpg",".jpeg");
                Log.e("filename", finalFile.getName().replace("cropped", "").replace(".jpg",".jpeg"));
                uploadPhotoToS3(compressedImgFile, fileName);
                getAssessmentResponseObj.setImgId(fileName);
                questionsResultArrayList.get(pos).setImgId(fileName);


                // sendDataToServer("21", "6", Constants.IMG_CONTENT_TYPE, fileName, "N/A");


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }


        }
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                String list = data.getStringExtra("general_info_list");
                Log.e("DataFrom general Info", "" + list);
            }
        }
    }


    private void uploadPhotoToS3(final File path, final String fileName) {

        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(this)
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(s3Client)
                        .build();
     /*   PutObjectRequest putObjectRequest = new PutObjectRequest(AppConstants.BUCKET_NAME,AppConstants.KEY,fileName)
                .withCannedAcl(CannedAccessControlList.PublicRead);*/
      //  s3Client.generatePresignedUrl(AppConstants.BUCKET_NAME,fileName,null, HttpMethod.GET);
      //  Log.e("expireee",""+ s3Client.generatePresignedUrl(AppConstants.BUCKET_NAME,fileName,null, HttpMethod.GET));
        TransferObserver uploadObserver =
                transferUtility.upload(AppConstants.BUCKET_NAME, fileName, path,CannedAccessControlList.PublicRead);


        uploadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    Log.e("state", "" + state);


                    // Handle a completed download.
                    //  Toast.makeText(savedData.this, "Upload Completed!", Toast.LENGTH_SHORT).show();
                    getImageUrl2(fileName);
                    sharedPrefManager.setBooleanForKey(true, "isUploaded");
                    service_adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                Log.e("", "Toast");
            }

            @Override
            public void onError(int id, Exception ex) {
                Toast.makeText(savedData.this, "error!" + ex, Toast.LENGTH_SHORT).show();
                Log.e("", "" + ex);
            }
        });


// If your upload does not trigger the onStateChanged method inside your
// TransferListener, you can directly check the transfer state as shown here.
        if (TransferState.COMPLETED == uploadObserver.getState()) {
            // Handle a completed upload.
        }
    }


    public String getImageUrl(String ImgKey) {
     String url  ="https://jamshed.s3-us-west-2.amazonaws.com/"+ImgKey;
       /* GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(AppConstants.BUCKET_NAME, ImgKey)
                        .withMethod(HttpMethod.GET);*/
  //Log.e("expire",""+generatePresignedUrlRequest.getExpiration());

    //    presignedUrl = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
      //  questionsResultArrayList.get(pos).setImageURL(String.valueOf(presignedUrl));
        questionsResultArrayList.get(pos).setImageURL( url);
        System.out.println("Pre-Signed URL =" + url);
        return url;
    }

    public String getImageUrl2(String ImgKey) {
        String url  ="https://jamshed.s3-us-west-2.amazonaws.com/"+ImgKey;
      /*  GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(AppConstants.BUCKET_NAME, ImgKey)
                        .withMethod(HttpMethod.GET);
        Log.e("expire",""+generatePresignedUrlRequest.getExpiration());

        presignedUrl = s3Client.generatePresignedUrl(generatePresignedUrlRequest);*/
      //  questionsResultArrayList.get(pos).setImageURL(String.valueOf(presignedUrl));
        questionsResultArrayList.get(pos).setImageURL(url);
        System.out.println("Pre-Signed URL =" + url);
        return url;






    }

    /**
     * Check and request permission
     *
     * @return
     */
    private boolean checkPermission() {

        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)

        ) {

            // Permission is not granted
            return false;

        }
        return true;
    }

    public void requestPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO},
                110);
    }

}
