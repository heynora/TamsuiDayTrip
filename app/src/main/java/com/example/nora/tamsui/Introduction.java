package com.example.nora.tamsui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introdution);
        getData();
        CompontSetting();
        setData();
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
}
