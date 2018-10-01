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
import java.util.Map;

public class AddData_Activity extends AppCompatActivity {
    EditText Name_et, Address_et, Description_et;
    ImageView Image;
    Button SentData_bt;
    Spinner sceneSpinner;


    private Uri filepath;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_data);
        ComponentSetting();
        SpinnerSetting();
        Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showfilechooser();
            }
        });
    }

    private void ComponentSetting() {
        sceneSpinner = (Spinner) findViewById(R.id.spinner);
        Name_et = (EditText) findViewById(R.id.Name_et);
        Address_et = (EditText) findViewById(R.id.Address_et);
        Description_et = (EditText) findViewById(R.id.Description_et);
        Image = (ImageView) findViewById(R.id.Image);
        SentData_bt = (Button) findViewById(R.id.SentData_bt);
    }

    private void SpinnerSetting() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Scene, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sceneSpinner.setAdapter(adapter);
    }

    private final int PICKFILE_RESULT_CODE = 1;

    private void showfilechooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent, "Select the image"), PICKFILE_RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICKFILE_RESULT_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filepath = data.getData();
            fileupload(filepath);
            Log.e("DEBUG", "MatchUpload Temp : " + filepath.toString());
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                Image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String TAG = "AddData Activity";
    private StorageReference databaseReference;

    private void fileupload(Uri filepath) {
        progressDialog = ProgressDialog.show(this,
                "上傳中", "請等待...", true);

        String picName = Name_et.getText().toString() + ".png";
        databaseReference = FirebaseStorage.getInstance().getReference();

        final StorageReference imgReference = databaseReference.child(picName);
        UploadTask uploadTask = imgReference.putFile(filepath);

        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
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
                    UpdateData(taskResult.toString());
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
        Map<String, String> map = result.getMap("1");
        db.collection(collection).document(document).update(result.getName(), map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(AddData_Activity.this, "SUCCESS", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(AddData_Activity.this, "錯誤～~請CHECK網路是否正常", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(AddData_Activity.this,Download_Activity.class);
                startActivity(intent);
            }
        });

    }

    private SceneData getSceneData(String DownloadUrl, String Scene) {
        SceneData result = null;
        result = new SceneData(Name_et.getText().toString(), Description_et.getText().toString(),
                Address_et.getText().toString(), DownloadUrl, Scene);
        return result;
    }

}
