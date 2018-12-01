package com.example.nora.tamsui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

public class ViewpagerAdapter extends PagerAdapter {
    Activity activity;
    String[] images;
    LayoutInflater inflater;
    ArrayList<Uri> imagesUri;
    boolean edit = false;
    public ViewpagerAdapter(Activity activity, String[] images) {
        this.activity = activity;
        this.images = images;
    }
    public ViewpagerAdapter(Activity activity, String[] images,boolean edit) {
        this.activity = activity;
        this.images = images;
        this.edit = edit;
    }
    public ViewpagerAdapter(Activity activity, ArrayList<Uri> images) {
        this.activity = activity;
        this.imagesUri = images;
    }

    @Override
    public int getCount() {
        if (images != null)
            return images.length;
        else if (imagesUri != null)
            return imagesUri.size();
        else return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.viewpager_item, container, false);

        ImageView image;
        image = (ImageView) itemView.findViewById(R.id.imageView);
        DisplayMetrics dis = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dis);
        int height = dis.heightPixels;
        int width = dis.widthPixels;

        image.setMinimumHeight(height);
        image.setMaxWidth(width);
        if (images != null) {
            try {
                if(edit && position == getCount()-1) {
                    image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showfilechooser();
                        }
                    });
                }
                Picasso.with(activity.getApplicationContext())
                        .load(images[position])
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .into(image);
            } catch (Exception e) {
                Log.e("ViewPagerAdapter", "ERROR");
                Log.e("ViewPagerAdapter", e.toString());
            }
        } else if (imagesUri != null) {
            if (position == getCount()-1)
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showfilechooser();
                    }
                });
            try {
                Picasso.with(activity.getApplicationContext())
                        .load(imagesUri.get(position))
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .into(image);
            } catch (Exception e) {
                Log.e("ViewPagerAdapter", "ERROR");
                Log.e("ViewPagerAdapter", e.toString());
            }
        }

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    private final int PICKFILE_RESULT_CODE = 1;

    private void showfilechooser() {
        Log.e("AddDataActivity", "showfilechooser");
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(intent.createChooser(intent, "Select the image"), PICKFILE_RESULT_CODE);
    }
}
