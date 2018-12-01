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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Notified_Data_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifi_data);
        CompontSetting();
        getData();
        setData();

    }

    EditText Name_et, Address_et, Description_et;
    ImageView Image;
    Button Back;
    ViewPager viewPager;
    ViewpagerAdapter adapter;
    private ArrayList<Uri> filepath;
    private Uri defaultUri;
    private String uploadUri="";
    private int uploadIndex = 0;

    public void CompontSetting() {
        Name_et = (EditText) findViewById(R.id.Name_et);
        Address_et = (EditText) findViewById(R.id.Address_et);
        Description_et = (EditText) findViewById(R.id.Description_et);
        Image = (ImageView) findViewById(R.id.Image);
        Back = (Button) findViewById(R.id.SentData_bt);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = ProgressDialog.show(Notified_Data_Activity.this,
                        "上傳中", "請等待...", true);
//                haven fix the picture
                if(filepath != null)
                    fileupload(uploadIndex);
                else
                    UpdateData();
            }
        });
        viewPager = findViewById(R.id.viewpager);
    }

    SceneData data;

    private void getData() {
        data = getIntent().getParcelableExtra("Data");
    }

    private void setData() {
        Name_et.setText(data.getName());
        Address_et.setText(data.getAddress());
        Description_et.setText(data.getDescription());
        adapter = new ViewpagerAdapter(this, data.getImagePath().split(";"),true);
        viewPager.setAdapter(adapter);
    }

    private void viewPagerShow() {
        if (filepath == null) {
            filepath = new ArrayList<>();
        }
        if (defaultUri == null)
            defaultUri = Uri.parse("android.resource://com.example.nora.tamsui/drawable/click");
        filepath.add(defaultUri);
        adapter = new ViewpagerAdapter(this, filepath);
        viewPager.setAdapter(adapter);
    }


    private SceneData getSceneData(String DownloadUrl, String Scene) {
        SceneData result = null;
        result = new SceneData(Name_et.getText().toString(), Description_et.getText().toString(),
                Address_et.getText().toString(), DownloadUrl, Scene);
        return result;
    }

    String TAG = "Notified_Data";
    ProgressDialog dialog;
    FirebaseFirestore db;

    private void UpdateData(String DownloadUrl) {
        db = FirebaseFirestore.getInstance();
        String collection = "Tamsui";
        String document = data.getScene();
        SceneData result = getSceneData(DownloadUrl, document);
        Map<String, String> map = result.SceneMap("1");
        db.collection(collection).document(document).update(result.getName(), map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dialog.dismiss();
                if (task.isSuccessful()) {
                    Toast toast = Toast.makeText(Notified_Data_Activity.this, "SUCCESS", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();

                } else {
                    Toast toast =Toast.makeText(Notified_Data_Activity.this, "錯誤～~請CHECK網路是否正常", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }
                Notified_Data_Activity.this.finish();
            }
        });
    }

    private void UpdateData(){
        String Url = data.getImagePath();
        UpdateData(Url);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Intent intent = new Intent(Notified_Data_Activity.this, Tamsui_menu.class);
        Notified_Data_Activity.this.finish();
    }

    private final int PICKFILE_RESULT_CODE = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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


}
