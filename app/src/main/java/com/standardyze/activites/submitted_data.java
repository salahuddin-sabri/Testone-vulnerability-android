package com.standardyze.activites;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
import com.google.gson.Gson;
import com.standardyze.App.SessionClass;
import com.standardyze.R;
import com.standardyze.adapters.savedData_adapter;
import com.standardyze.adapters.submitted_adapter;
import com.standardyze.baseActivity;
import com.standardyze.customViews.CustomTextViewLight;
import com.standardyze.models.CategoryModel;
import com.standardyze.models.options;
import com.standardyze.networking.WebServiceFactory;
import com.standardyze.utils.AppConstants;
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

import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class submitted_data extends baseActivity {

    @BindView(R.id.recycler_view_questions)
    RecyclerView recyclerView;
    @BindView(R.id.date_txt)
    CustomTextViewLight dateText;
    @BindView(R.id.next_btn)
    Button next_btn;
    LinearLayoutManager linearLayoutManager;
    ArrayList<options> arrayList;
    savedData_adapter service_adapter;
    ArrayList<com.standardyze.wrapper.getAssessmentResponseObj> questionsResultArrayList;
    ArrayList<com.standardyze.wrapper.saveResponseObject> saveResponseObjectArrayList;
    com.standardyze.wrapper.saveResponseObject saveResponseObject;
    String roleKey;
    FilterQuestionsResponse filterQuestionsResponse;
    DatabaseHelper databaseHelper;
    QuestionsResult questionsResult;
    getAssessmentResponseObj getAssessmentResponseObj;
    String status;
    ArrayList<General_infromations> general_infromationsArrayList;
    List<CategoryModel> commonData;
    int count = 0;
    int pos;
    File finalFile;
    AmazonS3Client s3Client;
    URL presignedUrl;
    String fileName;
    submitted_adapter submitted_adapter;
    String dateTime;
    private ArrayList<roles> rolesArraylist;
    String submittedEmployee ;
    SharedPrefManager sharedPrefManager;
    @BindView(R.id.rotateloadingAS)
    RotateLoading  rotateLoading;
    String gI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrival_service);
        ButterKnife.bind(this);
        showActionBar("Arrival Service", null, false, false, true, false);

sharedPrefManager = new SharedPrefManager(this);
        init();
        AWSMobileClient.getInstance().initialize(this).execute();
        BasicAWSCredentials credentials = new BasicAWSCredentials(AppConstants.KEY, AppConstants.SECRET);
        s3Client = new AmazonS3Client(credentials);
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
      //  checkPermission();

        next_btn.setVisibility(View.GONE);
        getAssessmentResponseObj = new getAssessmentResponseObj();
        saveResponseObjectArrayList = new ArrayList<>();
        databaseHelper = new DatabaseHelper(this);
        questionsResultArrayList = new ArrayList<>();
        Intent intent = getIntent();
        assert intent != null;
        status = intent.getStringExtra("status");
        dateTime = intent.getStringExtra("dateTime");
        submittedEmployee =intent.getStringExtra("submitted");
        sharedPrefManager.setStringForKey(submittedEmployee,"submitted");


            gI = intent.getStringExtra("generalInfo");
        if (gI!=null){
            sharedPrefManager.setStringForKey((gI),"SubmittedGI");
        }

        sharedPrefManager.clearKey("SavedGI");
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
/*
        service_adapter = new savedData_adapter(this, questionsResultArrayList, new savedData_adapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position, int responseTblId, String questionText, String selection, String comment, String imgId, int totalSelection,String imgURL) {
                pos = position;

                saveResponseObject = new saveResponseObject(responseTblId, selection, comment, imgId, questionText,imgURL);

                saveResponseObjectArrayList.clear();

                saveResponseObjectArrayList.add(saveResponseObject);
*//*
                for (int i = 0; i < saveResponseObjectArrayList.size(); i++) {
                    Log.e("Selection", "" + saveResponseObjectArrayList.get(i).getSelection());
                    if (saveResponseObjectArrayList.get(i).getSelection().equalsIgnoreCase("YES")) {
                        Toast.makeText(savedData.this, "" + count, Toast.LENGTH_SHORT).show();

                    }
                }*//*
            }
        });*/
       submitted_adapter = new submitted_adapter(this, questionsResultArrayList, new submitted_adapter.OnItemClickListener() {
           @Override
           public void onItemClicked(int position, int responseTblId, String questionText, String selection, String comment, String imgId, String ImgURl) {
               pos = position;
               saveResponseObject = new saveResponseObject(responseTblId, selection, comment, imgId, questionText, getImageUrl(imgId));

               saveResponseObjectArrayList.clear();

               saveResponseObjectArrayList.add(saveResponseObject);
               submitted_adapter.notifyDataSetChanged();
           }
       });

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(submitted_adapter);

/*
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.e("dattte", "" +  DateFormat.getDateInstance(DateFormat.MEDIUM).format(( date)));
        dateText.setText("Dated: "+ DateFormat.getDateInstance(DateFormat.MEDIUM).format(( date)));*/
        String date =new SimpleDateFormat("d MMM yyyy", Locale.getDefault()).format(new Date());
        dateText.setText("Dated: " +dateTime);


        int uID = SessionClass.getInstance().getUser(this).getId();
        int assessmenntID = intent.getIntExtra("id", 0);
        int companyId = SessionClass.getInstance().getUser(this).getCompanyId();



        getSubmittedData(uID, companyId, assessmenntID);

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
                        int AssessmentTblId  = getAssessmentQuestionsResponse.getResults().getAssessmentTblId();
                        Gson gson = new Gson();
                        String rolesList = gson.toJson( getAssessmentQuestionsResponse.getResults().getRoles());
                        HashMap<String, String> hashMap = getAssessmentQuestionsResponse.getResults().getRoles();
                        for (String key : hashMap.keySet()) {
                            roles roles = new roles();
                            roles.setName(hashMap.get(key));
                            rolesArraylist.add(roles);
                        }

                        SharedPrefManager sharedPrefManager = new SharedPrefManager(submitted_data.this);
                        sharedPrefManager.setIntegerForKey(AssessmentTblId,"AssessmentTblId");
                       // sharedPrefManager.setStringForKey(rolesList,"rolesList");
                        questionsResultArrayList.addAll(getAssessmentQuestionsResponse.getResults().getResponseObject());
                        submitted_adapter.notifyDataSetChanged();


                        for (int i = 0; i < getAssessmentQuestionsResponse.getResults().getResponseObject().size(); i++) {
                            HashMap<String, String> categories =  getAssessmentQuestionsResponse.getResults().getResponseObject().get(i).getCategories();

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
                        }
                    }
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


        String general_info = gson.toJson(general_infromationsArrayList);
        Intent intent = new Intent(this, summary.class);
        intent.putExtra("updatedList",  gson.toJson(questionsResultArrayList));
        intent.putExtra("savedData","savedData");
        intent.putExtra("status",status);
     /*   if (gI.equalsIgnoreCase("N/A")&&general_infromationsArrayList==null){
            intent.putExtra("Saved_general_info",  gI);
        }else {
            intent.putExtra("Saved_general_info",  gson.toJson(general_infromationsArrayList));
        }*/
      //  intent.putExtra("Submitted_general_info",  gI);
        intent.putExtra("catList", gson.toJson(commonData));
        intent.putExtra("submittedRolesArrayList", gson.toJson(rolesArraylist));
        startActivity(intent);


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
                Log.e("uri", "" + resultUri);
                finalFile = new File(resultUri.getPath());
                fileName = finalFile.getName().replace("cropped", "").replace(".jpg",".jpeg");
                Log.e("filename", finalFile.getName().replace("cropped", "").replace(".jpg",".jpeg"));
                //   setImages(resultUri.toString());
                uploadPhotoToS3(finalFile, fileName);
                getAssessmentResponseObj.setImgId(fileName);
              //  questionsResultArrayList.get(pos).setImgId(fileName);


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

        // KEY and SECRET are gotten when we create an IAM user above


        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(this)
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(s3Client)
                        .build();

        TransferObserver uploadObserver =
                transferUtility.upload(AppConstants.BUCKET_NAME, fileName, path, CannedAccessControlList.PublicRead);


        uploadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    Log.e("state", "" + state);
                    // Handle a completed download.
                    Toast.makeText(submitted_data.this, "Upload Completed!", Toast.LENGTH_SHORT).show();
                    getImageUrl(fileName);


                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                Log.e("", "Toast");
            }

            @Override
            public void onError(int id, Exception ex) {
                Toast.makeText(submitted_data.this, "error!" + ex, Toast.LENGTH_SHORT).show();
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
        /*GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(AppConstants.BUCKET_NAME, ImgKey)
                        .withMethod(HttpMethod.GET);
        presignedUrl = s3Client.generatePresignedUrl(generatePresignedUrlRequest);*/
       // questionsResultArrayList.get(pos).setImageURL(String.valueOf(presignedUrl));
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


