package com.codingbaby;

import android.content.res.AssetManager;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileReader {

    public static int length(final CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }

    public static boolean isBlank(final CharSequence cs) {
        final int strLen = length(cs);
        if (strLen == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }




    public static List<String> loadPoem(AssetManager assets, String file) {

        List<String> list = new ArrayList<>();

        try (BufferedReader bf = new BufferedReader(new InputStreamReader(assets.open(file)))) {
            String line;
            while ((line = bf.readLine()) != null) {
                if (!isBlank(line)) {
                    list.add(line);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }






}
