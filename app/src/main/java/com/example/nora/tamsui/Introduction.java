package com.example.nora.tamsui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

/**
 * Created by Nora on 2017/12/17.
 */

public class Introduction extends AppCompatActivity {
    private SceneData data;
    Button back;
    TextView title;
    TextView content;
    TextView address;
    ImageView image;
    Button location;
    WebView locationMap;
    String[] images;
    ViewPager viewPager;
    ViewpagerAdapter adapter;

//    private  String[] images = {
//            // "https://goo.gl/images/VZj3GA"
//            //  ,"https://goo.gl/images/1f3oZB"
//              "https://firebasestorage.googleapis.com/v0/b/tamsui-b5f6f.appspot.com/o/%E5%A4%A9%E5%85%83%E5%AE%AE.png?alt=media&token=c12b1772-1f1b-4955-ab40-a58069a94811"
//            ,"https://www.google.com/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwjvwefrz_TeAhXSfysKHdFyApwQjRx6BAgBEAQ&url=https%3A%2F%2Fcloudinary.com%2Fblog%2Fintroducing_smart_cropping_intelligent_quality_selection_and_automated_responsive_images&psig=AOvVaw0k-bS5GCwfCCPdx0eQJ9d2&ust=1543409839264893"
//    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introdution);
        getData();
        CompontSetting();
        setData();
        setWebView();
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        adapter = new ViewpagerAdapter(Introduction.this,images);
        viewPager.setAdapter(adapter);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Introduction.this.finish();
            }
        });
    }

    private void CompontSetting() {
        back = (Button) findViewById(R.id.introdution_back);
        title = (TextView) findViewById(R.id.introdution_title);
        content = (TextView) findViewById(R.id.introdution_content);
        address = (TextView) findViewById(R.id.introdution_address);
      //  image = (ImageView) findViewById(R.id.intorduction_image);
        location = (Button)findViewById(R.id.location_bt);
        location.setOnClickListener(location_click);
        locationMap = (WebView) findViewById(R.id.locationMap);
    }

    public void getData() {
        data = getIntent().getParcelableExtra("IntroData");
    }

    private void setData() {

        title.setText(data.getName());
        content.setText(data.getDescription());
        address.setText("地址"+data.getAddress());
        images =  data.getImagePath().split(";");
        Log.e(TAG,"LENGTH: "+images.length);
        if(images.length > 1){
            String[] temp = new String[images.length-1];
            for(int i = 0;i<temp.length;i++){
                temp[i] = images[i];
            }
            images = temp;
        }
        Log.e(TAG,"data:"+data.toString());

//        Glide.with(this)
//                .load(data.getImagePath())
//                .apply(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
//                .into(image);
    }

    private static final String MAP_URL = "file:///android_asset/googleMap.html";
    boolean webviewReady = false;
    public void setWebView(){
        WebSettings webSettings = locationMap.getSettings();
        webSettings.setJavaScriptEnabled(true);
        locationMap.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url)
            {
                getLocationFromFirebase();
            }

        });
        locationMap.loadUrl(MAP_URL);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Introduction.this.finish();
    }

    boolean mapVisible = false;
    View.OnClickListener location_click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mapVisible = !mapVisible;
            if(mapVisible) {
                content.setVisibility(View.INVISIBLE);
                locationMap.setVisibility(View.VISIBLE);
            }else{
                content.setVisibility(View.VISIBLE);
                locationMap.setVisibility(View.INVISIBLE);
            }
        }
    };

    String TAG = "Introduction";
    private void getLocationFromFirebase(){
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Tamsui").document("Map");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task)
            {
                if(task.isSuccessful())
                {
                    DocumentSnapshot document = task.getResult();
                    for (Map.Entry<String, Object> temp : document.getData().entrySet()) {
                        if(temp.getKey().toString().equals(data.getName())){
                            Log.e(TAG,data.getName().toString());
                            Map<String,Double> map = (Map<String,Double>)temp.getValue();
                            String latitude = map.get("latitude").toString();
                            String longitude = map.get("longitude").toString();
                            final String centerURL = "javascript:centerAt(" +
                                    latitude + "," +
                                    longitude+ ")";
                            locationMap.loadUrl(centerURL);
                        }
                    }

                }


            }
        });
    }
}
