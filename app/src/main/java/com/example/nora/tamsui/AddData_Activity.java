package com.example.nora.tamsui;

import android.app.ProgressDialog;
import android.os.Bundle;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class AddData_Activity extends AppCompatActivity {
    EditText Name_et, Address_et, Description_et;
    ImageView Image;
    Button SentData_bt;
    Spinner Spinner;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_data);
        ComponentSetting();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Scene,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner.setAdapter(adapter);

        Log.e("data",data+"");
    }
    public void ComponentSetting() {
        Spinner = (Spinner) findViewById(R.id.spinner);
        Name_et = (EditText) findViewById(R.id.Name_et);
        Address_et = (EditText) findViewById(R.id.Address_et);
        Description_et = (EditText) findViewById(R.id.Description_et);
        Image = (ImageView) findViewById(R.id.Image);
        SentData_bt = (Button) findViewById(R.id.SentData_bt);
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
