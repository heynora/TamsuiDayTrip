package com.example.nora.tamsui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Tamsui_menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tamsui_menu);

        TextView title = (TextView) findViewById(R.id.menu_title);
        title.setTextSize(60);
        Button rand = (Button) findViewById(R.id.rand);
        rand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("ScenceData", "RAND CLICK");
                if (alldata == null) {
                    alldata = new ArrayList<>();
                    getData();
                } else {
                    GoToRandomScene();
                    Tamsui_menu.this.finish();
                }
            }
        });

        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Tamsui_menu.this, SingInActivity.class);
                startActivity(intent);
            }
        });
    }

    SceneData data;

    String TAG = "Tamsui_menu";
    public static ArrayList<ArrayList<SceneData>> alldata = null;
    static int ScenenIndex = 1;

    void getData() {
        //從資料庫下載資料
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        String collection = "Tamsui";
        String document = "Scene" + ScenenIndex;
        DocumentReference docRef = db.collection(collection).document(document);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        ArrayList<SceneData> data = new ArrayList<>();
                        for (Map.Entry<String, Object> temp : document.getData().entrySet()) {
                            try {
                                SceneData sceneData = new SceneData((Map<String, String>) temp.getValue());
                                data.add(sceneData);
                                //Log.e(TAG, sceneData.toString());
                            } catch (Exception e) {
                                Log.e(TAG, "Exception");
                            }
                        }
                        alldata.add(data);
                        if (ScenenIndex < 4) {
                            ScenenIndex++;
                            getData();
                        } else {

                            GoToRandomScene();
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

    private ArrayList<SceneData> RandomData;

    public void shuffle() {
        RandomData = new ArrayList<>();
        for (int i = 0; i < alldata.size(); i++) {
            ArrayList<SceneData> data = alldata.get(i);
            int index = (int) (Math.random() * data.size());
            RandomData.add(data.get(index));
        }
    }

    private void GoToRandomScene() {
        shuffle();
        Intent intent = new Intent(Tamsui_menu.this, Random.class);
        Bundle bundle = new Bundle();
        try {
            intent.putExtra("RandomData",RandomData);
            startActivity(intent);
        }catch (Exception e){
            Log.e(TAG,e.toString());
        }

//
    }
}
