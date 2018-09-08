package com.example.nora.tamsui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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

    public void CompontSetting() {
        Name_et = (EditText) findViewById(R.id.Name_et);
        Address_et = (EditText) findViewById(R.id.Address_et);
        Description_et = (EditText) findViewById(R.id.Description_et);
        Image = (ImageView) findViewById(R.id.Image);
        Back = (Button) findViewById(R.id.SentData_bt);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateData();
            }
        });
    }

    SceneData data;

    private void getData() {
        data = getIntent().getParcelableExtra("Data");
    }

    private void setData() {
        Name_et.setText(data.getName());
        Address_et.setText(data.getAddress());
        Description_et.setText(data.getDescription());
        Glide.with(this)
                .load(data.getImagePath())
                .apply(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .into(Image);
    }


    SceneData result = null;

    private SceneData getSceneData() {
        if (result == null)
            result = new SceneData(Name_et.getText().toString(), Description_et.getText().toString(),
                    Address_et.getText().toString(), data.getImagePath().toString(),data.getScene());
        return result;
    }

    String TAG = "Notified_Data";
    ProgressDialog dialog;
    FirebaseFirestore db;

    private void UpdateData() {
        dialog = ProgressDialog.show(Notified_Data_Activity.this,
                "上傳中", "請等待...", true);
        db = FirebaseFirestore.getInstance();

        String collection = "Tamsui";
        final String document = getSceneData().getScene();
        Log.e(TAG, collection + " " + document);
        Map<String, String> map = getSceneData().getMap("1");
        db.collection(collection).document(document).update(getSceneData().getName(),map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dialog.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(Notified_Data_Activity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(Notified_Data_Activity.this, "錯誤～~請CHECK網路是否正常", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void Set() {
        db = FirebaseFirestore.getInstance();

        String collection = "Tamsui";
        String document = "Scene5";
        Log.e(TAG, collection + " " + document);

        db.collection(collection).document(document).set(getSceneData().getMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dialog.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(Notified_Data_Activity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
