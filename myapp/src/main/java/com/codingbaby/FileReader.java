package com.codingbaby;

import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileReader {


    public static List<String> loadIdiom(AssetManager assets) {

        List<String> list = new ArrayList<>();

        // load chinese idiom
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(assets.open("idiom.txt")))) {
            String line;
            while ((line = bf.readLine()) != null) {
                list.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    public static List<String> loadStudentIdiom(AssetManager assets) {

        List<String> list = new ArrayList<>();

        // load chinese idiom
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(assets.open("idiom-students.txt")))) {
            String line;
            while ((line = bf.readLine()) != null) {
                list.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}
