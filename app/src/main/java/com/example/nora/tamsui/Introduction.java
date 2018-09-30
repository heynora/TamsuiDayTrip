package com.example.nora.tamsui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introdution);
        getData();
        CompontSetting();
        setData();
        setWebView();
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
        image = (ImageView) findViewById(R.id.intorduction_image);
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
        Glide.with(this)
                .load(data.getImagePath())
                .apply(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .into(image);
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
                webviewReady = true;//webview已經載入完畢
//                final String centerURL = "javascript:centerAt(" +
//                        mostRecentLocation.getLatitude() + "," +
//                        mostRecentLocation.getLongitude()+ ")";
//                if (webviewReady) webView.loadUrl(centerURL);
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
}
