package com.dashang.www.characterrecognition.utils;


import android.util.Log;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class RegexUtil {

    public static ArrayList<String> getNumbs(ArrayList<String> wordList) {

        ArrayList<String> numbs = new ArrayList<>();
        boolean hasFound = false;

        String regEx1 = "^第[0-9]+期$";
        String regEx2 = "^[①|②|③|④|⑤][0-9]{10}\\+[0-9]{4}$";

        for (String word : wordList) {

            if (!hasFound && Pattern.matches(regEx1, word)) {
                hasFound = true;
                numbs.add(word);
            }

            if (Pattern.matches(regEx2, word)) {
                numbs.add(word);
            }
        }

        return numbs;
    }


}