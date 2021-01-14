package com.codingbaby;

import android.content.res.AssetManager;

import com.github.promeg.pinyinhelper.Pinyin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileReader {


    public static List<String> loadIdiom(AssetManager assets) {

        List<String> list = new ArrayList<>();

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


    public static List<String> loadPoem(AssetManager assets) {

        List<String> list = new ArrayList<>();

        try (BufferedReader bf = new BufferedReader(new InputStreamReader(assets.open("poem.txt")))) {
            String line;
            while ((line = bf.readLine()) != null) {
                if (!line.trim().equals("")) {
                    list.add(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    public static List<String> loadStudentPoem(AssetManager assets) {

        List<String> list = new ArrayList<>();

        try (BufferedReader bf = new BufferedReader(new InputStreamReader(assets.open("poem-students.txt")))) {
            String line;
            while ((line = bf.readLine()) != null) {
                list.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static List<String> freqEnglish(AssetManager assets) {

        List<String> list = new ArrayList<>();

        try (BufferedReader bf = new BufferedReader(new InputStreamReader(assets.open("cet4/freq.txt")))) {
            String line;
            while ((line = bf.readLine()) != null) {
                list.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    public static List<String> loadCet4Short(AssetManager assets) {

        List<String> list = new ArrayList<>();

        try (BufferedReader bf = new BufferedReader(new InputStreamReader(assets.open("cet4/short.txt")))) {
            String line;
            while ((line = bf.readLine()) != null) {
                list.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    public static List<Character> freqChinese(AssetManager assets) {

        List<Character> list = new ArrayList<>();

        try (BufferedReader bf = new BufferedReader(new InputStreamReader(assets.open("freqChinese.txt")))) {
            String line;
            while ((line = bf.readLine()) != null) {
                if (!line.trim().equals("")) {
                    for (char c : line.toCharArray()) {
                        if (Pinyin.isChinese(c)) {
                            list.add(c);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static Map<Character, List<String>> loadEnglishWord(AssetManager assets) {
        // load english word
        Map<Character, List<String>> english = new HashMap<>();
        for (char ch = 'A'; ch <= 'Z'; ch++) {
            english.put(ch, new ArrayList<String>());
            try (BufferedReader bf = new BufferedReader(new InputStreamReader(assets.open("cet4/" + ch + ".md")))) {
                String line;
                while ((line = bf.readLine()) != null) {
                    english.get(ch).add(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return english;
    }


}
