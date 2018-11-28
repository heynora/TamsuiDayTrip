package com.example.nora.tamsui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * Created by Nora on 2017/12/17.
 */

public class Random extends AppCompatActivity {
    Button back;
    ImageView[] Image;
    TextView[] PlaceName;
    LinearLayout scene1,scene2,scene3,scene4;
    LinearLayout arrow1,arrow2,arrow3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rand);
        Log.e(TAG,"GETDATA");
        getData();
        Log.e(TAG,"CompontSetting");
        CompontSetting();
        Log.e(TAG,"setData");
        setData();
        //返回按鈕設定
        back = (Button)findViewById(R.id.rand_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Random.this, Tamsui_menu.class);
                startActivity(intent);
                Random.this.finish();

            }
        });
    }

    //元件設定
    private void CompontSetting() {
        scene1 = (LinearLayout) findViewById(R.id.scene1);
        scene2 = (LinearLayout) findViewById(R.id.scene2);
        scene3 = (LinearLayout) findViewById(R.id.scene3);
        scene4 = (LinearLayout) findViewById(R.id.scene4);

        arrow1 = (LinearLayout) findViewById(R.id.arrow1);
        arrow2 = (LinearLayout) findViewById(R.id.arrow2);
        arrow3 = (LinearLayout) findViewById(R.id.arrow3);
        //設定返回按鈕
        back = (Button) findViewById(R.id.rand_back);
        //設定景點圖片
        Image = new ImageView[4];
        Image[0] = (ImageView) findViewById(R.id.PlaceImage_1);
        Image[1] = (ImageView) findViewById(R.id.PlaceImage_2);
        Image[2] = (ImageView) findViewById(R.id.PlaceImage_3);
        Image[3] = (ImageView) findViewById(R.id.PlaceImage_4);
        //設定景點名稱
        PlaceName = new TextView[4];
        PlaceName[0] = (TextView) findViewById(R.id.PlaceName_1);
        PlaceName[1] = (TextView) findViewById(R.id.PlaceName_2);
        PlaceName[2] = (TextView) findViewById(R.id.PlaceName_3);
        PlaceName[3] = (TextView) findViewById(R.id.PlaceName_4);

        for(int i = 0;i<Image.length;i++)
            Image[i].setOnClickListener(onClickListener);

       // AwesomeEnum SpinnerIndex = getIntent().getIntExtra("TimeEnum",3);
        TimeEnum Time = (TimeEnum) getIntent().getSerializableExtra("TimeEnum");
        if (Time != TimeEnum.NineHr){
            scene4.setVisibility(View.INVISIBLE);
            arrow3.setVisibility(View.INVISIBLE);
            if (Time != TimeEnum.SixHr){
                scene3.setVisibility(View.INVISIBLE);
                arrow2.setVisibility(View.INVISIBLE);
            }
        }
    }

    //點擊偵聽器
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            for(int index = 0;index<Image.length;index++){
                if(view.getId() == Image[index].getId()){
                    Intent intent = new Intent(Random.this,Introduction.class);
                    intent.putExtra("IntroData",RandomData.get(index));
                    startActivity(intent);
                }
            }

        }
    };

    private String TAG = "Random";
    private static ArrayList<SceneData> RandomData;
    private void getData(){
        RandomData = getIntent().getParcelableArrayListExtra("RandomData");
    }
    private void setData(){
        for(int i = 0;i<RandomData.size();i++){
            SceneData data = RandomData.get(i);
            Log.e(TAG,data.getName());
            PlaceName[i].setText(data.getName());
            Glide.with(this)
                    .load(data.getImagePath())
                    .apply(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                    .into(Image[i]);
            Log.d(TAG, "Random Image: "+data.getImagePath());
            Log.d(TAG, "Random data Name: "+ data.getName());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Random.this.finish();
    }
}
