package com.example.nora.tamsui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

public class Download_Activity extends AppCompatActivity {
    ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);
        dialog = ProgressDialog.show(Download_Activity.this,
                "讀取中", "請等待...",true);
        DownloadData = new ArrayList<>();
        getDataFromFirebase();
    }

    int ScenenIndex = 1;
    String TAG = "CreateData";
    ArrayList<SceneData> DownloadData;
    String collection = "Tamsui";
    String documentName;

    void getDataFromFirebase() {
        //從資料庫下載資料
        documentName = "Scene" + ScenenIndex;
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection(collection).document(documentName);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        for (Map.Entry<String, Object> temp : document.getData().entrySet()) {
                            try {
                                SceneData sceneData = new SceneData((Map<String, String>) temp.getValue());
                                sceneData.setScene(documentName);
                                DownloadData.add(sceneData);
//                                Log.e(TAG, "sceneData : "+temp.getValue().toString());
//                                Log.e(TAG,"LIST GET: "+DownloadData.get(DownloadData.size()-1).getName());
                            } catch (Exception e) {
                                Log.e(TAG, "Exception");
                            }
                        }
                        if (ScenenIndex < 4) {
                            Log.e(TAG,"Index"+ScenenIndex);
                            ScenenIndex++;
                            getDataFromFirebase();

                        } else {
                            dialog.dismiss();
                            Intent intent = new Intent(Download_Activity.this,CreateData_Activity.class);
                            intent.putParcelableArrayListExtra("Data",DownloadData);
                            startActivity(intent);
                            Log.e(TAG,"mAdapter.notifyDataSetChanged()");
                        }
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
}
