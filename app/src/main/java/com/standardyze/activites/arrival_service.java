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
import com.standardyze.adapters.arrival_service_adapter;
import com.standardyze.baseActivity;
import com.standardyze.customViews.CustomTextViewBold;
import com.standardyze.customViews.CustomTextViewLight;
import com.standardyze.models.CategoryModel;
import com.standardyze.models.options;
import com.standardyze.networking.WebServiceFactory;
import com.standardyze.utils.AppConstants;
import com.standardyze.utils.Compressor;
import com.standardyze.utils.DatabaseHelper;
import com.standardyze.utils.SharedPrefManager;
import com.standardyze.wrapper.Category;
import com.standardyze.wrapper.CategoryResults;
import com.standardyze.wrapper.FilterQuestionsResponse;
import com.standardyze.wrapper.General_infromations;
import com.standardyze.wrapper.QuestionsResult;
import com.standardyze.wrapper.roles;
import com.standardyze.wrapper.saveResponseObject;
import com.theartofdev.edmodo.cropper.CropImage;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class arrival_service extends baseActivity {

    @BindView(R.id.recycler_view_questions)
    RecyclerView recyclerView;
    @BindView(R.id.date_txt)
    CustomTextViewLight dateText;
    @BindView(R.id.next_btn)
    Button next_btn;
    LinearLayoutManager linearLayoutManager;
    ArrayList<options> arrayList;
    arrival_service_adapter service_adapter;
    ArrayList<QuestionsResult> questionsResultArrayList;
    ArrayList<saveResponseObject> saveResponseObjectArrayList;
    saveResponseObject saveResponseObject;
    String roleKey;
    FilterQuestionsResponse filterQuestionsResponse;
    DatabaseHelper databaseHelper;
    QuestionsResult questionsResult;
    ArrayList<CategoryResults> categoryResults;
    CategoryResults categories2;
    File finalFile;
    AmazonS3Client s3Client;
    URL presignedUrl;
    int pos;
    String fileName;
    Intent intent;
    String general_info;
    int count = 0;
    List<CategoryModel> commonData;
    @BindView(R.id.rotateloadingAS)
    RotateLoading rotateLoading;
    // MyReceiver myReceiver;
    ArrayList<General_infromations> general_infromationsArrayList;
    boolean isuploaded = false;
    SharedPrefManager sharedPrefManager;
    HashMap<String, String> categories;
    @BindView(R.id.questions)
    CustomTextViewBold customTextViewBold;
    ArrayList <Boolean>  strings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrival_service);
        ButterKnife.bind(this);
strings = new ArrayList<>();
        BroadcastReceiver mServiceReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                general_infromationsArrayList = new ArrayList<>();

                general_infromationsArrayList = (ArrayList) intent.getSerializableExtra("general_info_list");
                Log.e("general_infromations", "" + general_infromationsArrayList);

            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(mServiceReceiver, new IntentFilter("com.tutorialspoint.CUSTOM_INTENT"));

        showActionBar("Arrival Service", null, false, false, true, false);

        init();
    }


    public void init() {
        next_btn.setVisibility(View.GONE);
        sharedPrefManager = new SharedPrefManager(this);
        checkPermission();
        intent = getIntent();
        general_info = intent.getStringExtra("general_info");
        categoryResults = new ArrayList<>();

        saveResponseObjectArrayList = new ArrayList<>();
        databaseHelper = new DatabaseHelper(this);
        questionsResultArrayList = new ArrayList<>();
        questionsResult = new QuestionsResult();


        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);


        service_adapter = new arrival_service_adapter(this, questionsResultArrayList, new arrival_service_adapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position, int questionId, String questionText, String selection, String comment, String imgId, int count, String url ,boolean isSelected) {
                pos = position;

                if (selection == null) {
                    saveResponseObject = new saveResponseObject(questionId, null, comment, imgId, questionText, null);

                    saveResponseObjectArrayList.clear();

                    saveResponseObjectArrayList.add(saveResponseObject);

                }
                saveResponseObject = new saveResponseObject(questionId, selection, comment, imgId, questionText, url);

                saveResponseObjectArrayList.clear();

                saveResponseObjectArrayList.add(saveResponseObject);
                strings.add(isSelected);


               /* for (int i = 0; i < saveResponseObjectArrayList.size(); i++) {
                    Log.e("Selection", "" + saveResponseObjectArrayList.get(i).getSelection());
                    if (saveResponseObjectArrayList.get(i).getSelection().equalsIgnoreCase("YES")) {
                        Toast.makeText(arrival_service.this, "" + count, Toast.LENGTH_SHORT).show();

                    }
                }*/


            }
        });


        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(service_adapter);


        String date = new SimpleDateFormat("d MMM yyyy", Locale.getDefault()).format(new Date());
        dateText.setText("Dated: " + date);
        Intent intent = getIntent();
        assert intent != null;
        ArrayList<roles> list = databaseHelper.selectRoles();

        int companyId = SessionClass.getInstance().getUser(this).getCompanyId();
        String assessmentKey = intent.getStringExtra("assessmentKey");


        if (list.size() > 0) {
            StringBuilder sb1 = new StringBuilder();

            for (int i = 0; i < list.size(); i++) {

                sb1.append(list.get(i).getRoleKey());

                sb1.append(",");
            }
            Log.e("List", "" + sb1.substring(0, sb1.length() - 1));
            getQuestionsFromServer(companyId, assessmentKey, sb1.toString());
        } else {
            getQuestionsFromServer(companyId, assessmentKey, "");
        }
    }

    public void getQuestionsFromServer(int companyId, String assessmentKey, String roleKeys) {
        rotateLoading.start();
        customTextViewBold.setVisibility(View.GONE);
        WebServiceFactory.getInstance().filterQuestionsService(companyId, assessmentKey, roleKeys).enqueue(new Callback<FilterQuestionsResponse>() {
            @Override
            public void onResponse(Call<FilterQuestionsResponse> call, Response<FilterQuestionsResponse> response) {

                try {
                    JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));

                    FilterQuestionsResponse filterQuestionsResponse = new Gson().fromJson(jsonObject.toString(), FilterQuestionsResponse.class);
                    commonData = new ArrayList<>();
                    if (filterQuestionsResponse.getStatusCode() == 1) {
                        rotateLoading.stop();
                        next_btn.setVisibility(View.VISIBLE);

                        questionsResultArrayList.addAll(filterQuestionsResponse.getResults());
                        service_adapter.notifyDataSetChanged();

                        for (int i = 0; i < filterQuestionsResponse.getResults().size(); i++) {
                          categories = filterQuestionsResponse.getResults().get(i).getCategories();
                         //    databaseHelper.insertAssessmentCategories(filterQuestionsResponse.getResults().get(i).getCategories().toString());
                            //databaseHelper.updateAssessmentCategories(categories,sharedPrefManager.getIntegerByKey("assessmmentUniqID"),filterQuestionsResponse.getResults().get(i).getId());
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


                            //  categoryResults.add(categories);
                            Log.e("Categories", "" + filterQuestionsResponse.getResults().get(i).getCategories());

                            Log.e("Assessmenst", "" + filterQuestionsResponse.getResults().get(i).getAssessments());
                            Log.e("Roles", "" + filterQuestionsResponse.getResults().get(i).getRoles());
                            Log.e("QId", "" + filterQuestionsResponse.getResults().get(i).getId());
                        }
                        Log("testing");

                    }else {
                        rotateLoading.stop();
                        customTextViewBold.setVisibility(View.VISIBLE);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<FilterQuestionsResponse> call, Throwable t) {
                Log.e("ERROR", "" + t);
                rotateLoading.stop();
            }
        });
    }


    @OnClick(R.id.next_btn)
    public void setNext_btn() {

        Gson gson = new Gson();
        int j;
        for (j = 0; j < questionsResultArrayList.size(); j++) {
            if (questionsResultArrayList.get(j).getSelection() == null) {
                questionsResultArrayList.get(j).setSelection(" ");

            }

        }

      //  for (int i = 0; i < databaseHelper.selectEmployees().size(); i++) {

            if (databaseHelper.totalSum()>0) {
                if (strings.size() > 0) {
                   // strings.clear();
                    String jsonCars = gson.toJson(questionsResultArrayList);
                    String general_info = gson.toJson(general_infromationsArrayList);

                    Intent intent = new Intent(this, summary.class);
                    intent.putExtra("List", jsonCars);
                    intent.putExtra("catList", gson.toJson(commonData));
                    intent.putExtra("newStatus", "newStatus");
                    intent.putExtra("general_info", general_info);
                    startActivity(intent);
                    //  break;

                } else {
                    showDialogAlert(R.string.alert_msg_information);
                }
                //
            }   else {
                showDialogAlert(R.string.alert_msg_add_employee);
               // break;
            }

        }


 /*       for (int i = 0; i < databaseHelper.selectEmployees().size(); i++) {
            if (databaseHelper.selectEmployees().get(i).getIsChecked() == 1) {
                String jsonCars = gson.toJson(questionsResultArrayList);
                String general_info = gson.toJson(general_infromationsArrayList);

                Intent intent = new Intent(this, summary.class);
                intent.putExtra("List", jsonCars);
                intent.putExtra("catList", gson.toJson(commonData));
                intent.putExtra("newStatus", "newStatus");
                intent.putExtra("general_info", general_info);
                startActivity(intent);
                break;
            }
            else {
                showDialogAlert(R.string.alert_msg_add_employee);
                break;
            }
        }*/


    @Override
    public void onBackPressed() {
        super.onBackPressed();
      /*  for (int i = 0; i < databaseHelper.selectEmployees().size(); i++) {
            databaseHelper.updateEmployees(0, databaseHelper.selectEmployees().get(i).getId());
        }
*/
        sharedPrefManager.clearKey("selectedMeal");
        sharedPrefManager.clearKey("room");
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
                File compressedImgFile =
                        null;
                try {
                    compressedImgFile = new Compressor(this).compressToFile(finalFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                fileName = finalFile.getName().replace("cropped", "").replace(".jpg",".jpeg");
                Log.e("filename", finalFile.getName().replace("cropped", "").replace(".jpg",".jpeg"));
                //   setImages(resultUri.toString());
                uploadPhotoToS3(compressedImgFile, fileName);
                questionsResult.setImgId(fileName);
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
        AWSMobileClient.getInstance().initialize(this).execute();
        // KEY and SECRET are gotten when we create an IAM user above
        BasicAWSCredentials credentials = new BasicAWSCredentials(AppConstants.KEY, AppConstants.SECRET);
        s3Client = new AmazonS3Client(credentials);

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
                //    Toast.makeText(arrival_service.this, "Upload Completed!", Toast.LENGTH_SHORT).show();
                    getImageUrl(fileName);

                    sharedPrefManager.setBooleanForKey(true, "isUploaded");

                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                Log.e("", "Toast");
            }

            @Override
            public void onError(int id, Exception ex) {
                Toast.makeText(arrival_service.this, "error!" + ex, Toast.LENGTH_SHORT).show();
                Log.e("", "" + ex);
            }
        });


// If your upload does not trigger the onStateChanged method inside your
// TransferListener, you can directly check the transfer state as shown here.
        if (TransferState.COMPLETED == uploadObserver.getState()) {
            // Handle a completed upload.
        }
    }


    public void getImageUrl(String ImgKey) {
        String url  ="https://jamshed.s3-us-west-2.amazonaws.com/"+ImgKey;
//        GeneratePresignedUrlRequest generatePresignedUrlRequest =
//                new GeneratePresignedUrlRequest(AppConstants.BUCKET_NAME, ImgKey)
//                        .withMethod(HttpMethod.GET);
//        presignedUrl = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
//        questionsResultArrayList.get(pos).setImgURL(String.valueOf(presignedUrl));
        questionsResultArrayList.get(pos).setImgURL(url);
        System.out.println("Pre-Signed URL =" + url);
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

    @Override
    protected void onResume() {
        super.onResume();
    }


}

