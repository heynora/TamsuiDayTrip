package com.example.nora.tamsui;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SceneData implements Parcelable {
    private String Name;
    private String Description;
    private String Address;
    private String ImagePath;
    private String Scene="";

    SceneData(String _Name, String _Description, String _Address, String _ImagePath) {
        this.Name = _Name;
        this.Description = _Description;
        this.Address = _Address;
        this.ImagePath = _ImagePath;
    }

    SceneData(String _Name, String _Description, String _Address, String _ImagePath,String _Scene) {
        this(_Name,_Description,_Address,_ImagePath);
        this.Scene = _Scene;
    }
    SceneData(Map<String, String> data) {
        this.Name = data.get("Name");
        this.Description = data.get("Description");
        this.Address = data.get("Address");
        this.ImagePath = data.get("ImagePath");
        this.Scene = "";
    }

    protected SceneData(Parcel in) {
        Name = in.readString();
        Description = in.readString();
        Address = in.readString();
        ImagePath = in.readString();
        Scene = in.readString();
    }

    public static final Creator<SceneData> CREATOR = new Creator<SceneData>() {
        @Override
        public SceneData createFromParcel(Parcel in) {
            return new SceneData(in);
        }

        @Override
        public SceneData[] newArray(int size) {
            return new SceneData[size];
        }
    };

    public String toString() {
        return this.Name + " ," + this.Description.substring(0, 10) + "... \n"
                + this.Address + " ," + this.ImagePath;
    }
    public void setScene(String _Scene){this.Scene = _Scene;}
    public String getScene(){return this.Scene;}
    public String getName() {
        return this.Name;
    }

    public String getDescription() {
        return this.Description;
    }

    public String getAddress() {
        return this.Address;
    }

    public String getImagePath() {
        return this.ImagePath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Name);
        parcel.writeString(Description);
        parcel.writeString(Address);
        parcel.writeString(ImagePath);
        parcel.writeString(Scene);
    }

    public SceneData clone(){
        return new SceneData(this.Name,this.Description,this.Address,this.ImagePath,this.Scene);
    }

    public Map<String,Map<String,String>> getMap(){
        Map<String,Map<String,String>> map = new HashMap<>();
        map.put(getName(),getMap("1"));
        return map;
    }

    public Map<String,String> getMap(String total){
        Map<String,String> data = new HashMap<>();
        data.put("Name",getName());
        data.put("Address",getAddress());
        data.put("Description",getDescription());
        data.put("Scene",getScene());
        data.put("ImagePath",getImagePath());
        return data;
    }
}
