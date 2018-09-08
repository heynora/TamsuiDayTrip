package com.example.nora.tamsui;

import android.util.Log;

/**
 * Created by Nora on 2017/12/17.
 */

public class RandomData {

    String[] address1 = {"真理大學","淡江中學","英國領事館","淡水海關碼頭園區"};
    String[] address2 = {"鄞山寺","淡水龍山寺","清水祖師廟","福佑宮","多田榮吉故居","馬階醫院原址與淡水教會","淡水老街","淡水殼牌倉庫"};
    String[] address3 = {"滬尾砲台","英商嘉士洋行","淡水情人橋","淡水港","小白宮","一滴水紀念館","世界巧克力夢工廠"};
    String[] address4 = {"淡水莊園","紫藤咖啡館"};
    String[] image1 = {"a1","a2","a3","a4"};
    String[] image2 = {"b1","b2","b3","b4","b5","b6","b7","b8"};
    String[] image3 = {"c1","c2","c3","c4","c5","c6","c7"};
    String[] image4 = {"d1","d2"};

    private void Rand() {
        for (int i = 0; i < 10; i++) {
            int n1 = (int) (Math.random() * address1.length);
            int n2 = (int) (Math.random() * address1.length);

            String temp1 = address1[n1];
            address1[n1] = address1[n2];
            address1[n2] = temp1;

            String temp2 = image1[n1];
            image1[n1] = image1[n2];
            image1[n2] = temp2;
        }
        for (int i = 0; i < 10; i++) {
            int n1 = (int) (Math.random() * address2.length);
            int n2 = (int) (Math.random() * address2.length);
            String temp1 = address2[n1];
            address2[n1] = address2[n2];
            address2[n2] = temp1;

            String temp2 = image2[n1];
            image2[n1] = image2[n2];
            image2[n2] = temp2;
        }
        for (int i = 0; i < 10; i++) {
            int n1 = (int) (Math.random() * address3.length);
            int n2 = (int) (Math.random() * address3.length);
            String temp1 = address3[n1];
            address3[n1] = address3[n2];
            address3[n2] = temp1;

            String temp2 = image3[n1];
            image3[n1] = image3[n2];
            image3[n2] = temp2;
        }
        for (int i = 0; i < 10; i++) {
            int n1 = (int) (Math.random() * address4.length);
            int n2 = (int) (Math.random() * address4.length);
            String temp1 = address4[n1];
            address4[n1] = address4[n2];
            address4[n2] = temp1;

            String temp2 = image4[n1];
            image4[n1] = image4[n2];
            image4[n2] = temp2;
        }

    }
    public String[] getTextData(){
        Rand();
        String[] string = {address1[0],address2[0],address3[0],address4[0]};
        return string;
    }

    public String[] getImageData(){
        String[] string = {image1[0],image2[0],image3[0],image4[0]};
        return string;
    }

}
