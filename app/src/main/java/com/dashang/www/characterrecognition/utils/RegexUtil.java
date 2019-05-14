package com.dashang.www.characterrecognition.utils;


import android.util.Log;

import java.util.ArrayList;
import java.util.regex.Pattern;



public class RegexUtil {
    private static final String TAG = "RegexUtil";

    public static ArrayList<String> getNumbs(ArrayList<String> wordList) {

        ArrayList<String> numbs = new ArrayList<>();
        boolean hasFound = false;

//        String regEx1 = "^第[0-9]+期$";
//        String regEx1 = "第[0-9]+期";
        String regEx1 = "销售期[0-9]+";
//        String regEx2 = "^[①|②|③|④|⑤][0-9]{10}\\+[0-9]{4}$";
        String regEx2 = "蓝球[A-Za-z0-9]+";
        String regEx3 = "红球[A-Za-z0-9]+";

        for (String word : wordList) {

            if (!hasFound && Pattern.matches(regEx1, word)) {
                hasFound = true;
                numbs.add(word+"\n");
                Log.e(TAG, "getNumbs: 正则表达式" );
            }
            if (hasFound){
                Log.e(TAG, "getNumbs: 篮球1" );
                if (Pattern.matches(regEx2, word)) {
                    Log.e(TAG, "getNumbs: 篮球2" );
                    numbs.add(word+"\n");
                    Log.e(TAG, "getNumbs: 篮球3" );
                }
                if (Pattern.matches(regEx3,word)){
                    numbs.add(word);
                }
            }
        }

        if (numbs.isEmpty()){
            numbs.add("无匹配项");
            return numbs;
        }else {
            return numbs;
        }

    }


}