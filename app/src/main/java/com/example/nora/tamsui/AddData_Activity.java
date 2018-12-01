package com.example.nora.tamsui;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddData_Activity extends AppCompatActivity {
    EditText Name_et, Address_et, Description_et;

    Button SentData_bt;
    Spinner sceneSpinner;


    ViewPager viewPager;
    ViewpagerAdapter adapter;

    private ArrayList<Uri> filepath;
    private Uri defaultUri;
    private String uploadUri="";
    private int uploadIndex = 0;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_data);
        ComponentSetting();
        SpinnerSetting();
        viewPagerSetting();
    }

    private void ComponentSetting() {
        sceneSpinner = (Spinner) findViewById(R.id.spinner);
        Name_et = (EditText) findViewById(R.id.Name_et);
        Address_et = (EditText) findViewById(R.id.Address_et);
        Description_et = (EditText) findViewById(R.id.Description_et);
        SentData_bt = (Button) findViewById(R.id.SentData_bt);
        viewPager = findViewById(R.id.viewpager);
        SentData_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(filepath == null){
                    Toast.makeText(AddData_Activity.this,"至少要選擇一張圖喔！！",Toast.LENGTH_SHORT).show();
                }else{
                    progressDialog = ProgressDialog.show(AddData_Activity.this,
                            "上傳中", "請等待...", true);
                    fileupload(uploadIndex);
                }
            }
        });
    }

    private void SpinnerSetting() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Scene, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sceneSpinner.setAdapter(adapter);
    }

    private void viewPagerSetting() {
        viewPagerShow();
    }

    private final int PICKFILE_RESULT_CODE = 1;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("AddDataActivity", "onActivityResult");
        if (requestCode == PICKFILE_RESULT_CODE && resultCode == RESULT_OK && data != null) {

            //只選擇一張時透過 data.getData()
            //  選擇多張時透過 data.getClipData()
            filepath = null;

            if (data.getClipData() != null) {
                ClipData clipData = data.getClipData();
                int fileCount = clipData.getItemCount();
                filepath = new ArrayList<Uri>();
                for (int i = 0; i < fileCount; i++) {
                    filepath.add(clipData.getItemAt(i).getUri());
                }
            } else if (data.getData() != null) {
                filepath = new ArrayList<Uri>();
                filepath.add(data.getData());
            }
            viewPagerShow();
        }
    }

    private String TAG = "AddData Activity";
    private StorageReference databaseReference;

    private void fileupload(int index) {

        if(index == filepath.size()){
            UpdateData(uploadUri);
            return;
        }
        String picName = Name_et.getText().toString() +index +".png";
        databaseReference = FirebaseStorage.getInstance().getReference();

        final StorageReference imgReference = databaseReference.child(picName);
        UploadTask uploadTask = imgReference.putFile(filepath.get(index));

        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    Log.e("AddDataActivity","!task.isSuccessful()" + task.getException().toString());
                    throw task.getException();
                }
                return imgReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri taskResult = task.getResult();
                    Log.e(TAG, taskResult.toString());
                    if(uploadIndex != filepath.size()-1)
                        uploadUri +=taskResult.toString()+";";
                    else
                        uploadUri +=taskResult.toString();
                    fileupload(++uploadIndex);
                }
            }
        });
    }

    FirebaseFirestore db;


    private void UpdateData(String DownloadUrl) {
        db = FirebaseFirestore.getInstance();
        String collection = "Tamsui";
        String document = sceneSpinner.getSelectedItem().toString();
        SceneData result = getSceneData(DownloadUrl, document);
        Map<String, String> map = result.SceneMap("1");
        db.collection(collection).document(document).update(result.getName(), map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    Toast toast = Toast.makeText(AddData_Activity.this, "SUCCESS", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(AddData_Activity.this, "錯誤～~請CHECK網路是否正常", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
//                Intent intent = new Intent(AddData_Activity.this,Download_Activity.class);
//                startActivity(intent);
                AddData_Activity.this.finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AddData_Activity.this.finish();
    }

    private SceneData getSceneData(String DownloadUrl, String Scene) {
        SceneData result = null;
        result = new SceneData(Name_et.getText().toString(), Description_et.getText().toString(),
                Address_et.getText().toString(), DownloadUrl, Scene);
        return result;
    }

    private void viewPagerShow() {
        if (filepath == null) {
            filepath = new ArrayList<>();
            if (defaultUri == null)
                defaultUri = Uri.parse("android.resource://com.example.nora.tamsui/drawable/click");

        }
        filepath.add(defaultUri);
        adapter = new ViewpagerAdapter(this, filepath);
        viewPager.setAdapter(adapter);
    }
}
