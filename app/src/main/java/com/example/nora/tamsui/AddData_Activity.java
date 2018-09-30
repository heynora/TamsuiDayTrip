package com.example.nora.tamsui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Map;

public class AddData_Activity extends AppCompatActivity {
    EditText Name_et, Address_et, Description_et;
    ImageView Image;
    Button SentData_bt;
    Spinner Spinner;

    private static final int PICKFILE_RESULT_CODE = 1;
    private Uri filepath;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_data);
        ComponentSetting();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Scene,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner.setAdapter(adapter);
        setImageView();
        Log.e("data",data+"");
    }

    static StorageReference databaseReference;
    public static void SendStorage(String filename, Uri filepath,
                                   OnSuccessListener<UploadTask.TaskSnapshot> success,
                                   OnFailureListener fail) {
        databaseReference = FirebaseStorage.getInstance().getReference();
        databaseReference.putFile(filepath).addOnSuccessListener(success).addOnFailureListener(fail);
    }
    public void setImageView(){
        Image.setOnClickListener(onClick);
    }
    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showfilechooser();
        }
    };

    private void showfilechooser(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent,"Select the image"),PICKFILE_RESULT_CODE);
    }
    private void fileupload(){
        progressDialog = ProgressDialog.show(this,
                "上傳中", "請等待...", true);
        SendStorage(Name_et.getText().toString(),filepath,successListener,failureListener);
    }
    OnSuccessListener<UploadTask.TaskSnapshot> successListener = new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            progressDialog.dismiss();
            Log.e("DEBUG","Upload Pic OK");
        }
    };
    OnFailureListener failureListener = new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Log.e("DEBUG","ERRRRRRR"+e.toString());
        }
    };
    public void ComponentSetting() {
        Spinner = (Spinner) findViewById(R.id.spinner);
        Name_et = (EditText) findViewById(R.id.Name_et);
        Address_et = (EditText) findViewById(R.id.Address_et);
        Description_et = (EditText) findViewById(R.id.Description_et);
        Image = (ImageView) findViewById(R.id.Image);
        SentData_bt = (Button) findViewById(R.id.SentData_bt);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== PICKFILE_RESULT_CODE && resultCode==RESULT_OK && data!=null && data.getData()!=null){

            filepath=data.getData();
            fileupload();
            String temp = filepath.toString();
            temp = temp.substring(temp.lastIndexOf("."));
//            imagepath = temp;
            Log.e("DEBUG","MatchUpload Temp : "+temp);
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
                Image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    SceneData result = null;
    SceneData data;

    private SceneData getSceneData() {
        if (result == null)
            result = new SceneData(Name_et.getText().toString(), Description_et.getText().toString(),
                    Address_et.getText().toString(), data.getImagePath().toString(),data.getScene());
        return result;
    }

    ProgressDialog dialog;
    FirebaseFirestore db;

    private void UpdateData() {
        dialog = ProgressDialog.show(AddData_Activity.this,
                "上傳中", "請等待...", true);
        db = FirebaseFirestore.getInstance();

        String collection = "Tamsui";
        final String document = getSceneData().getScene();
        Map<String, String> map = getSceneData().getMap("1");
        db.collection(collection).document(document).update(getSceneData().getName(),map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dialog.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(AddData_Activity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(AddData_Activity.this, "錯誤～~請CHECK網路是否正常", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



}
