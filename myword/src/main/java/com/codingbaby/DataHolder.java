package com.codingbaby;

import android.content.Context;
import android.content.res.AssetManager;

import com.github.promeg.pinyinhelper.Pinyin;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

public class DataHolder {


    private List<Character> words = new ArrayList<>();

    //拼音映射
    public Map<Character, String> wordPinMap = new LinkedHashMap<>();

    //全局拼音库
    private Map<String, String> pinyin = new HashMap<>();
    public Map<String, String> oldWord = new HashMap<>();


    private Stack<String> poemHistory = new Stack<>();
    private Stack<Character> chineseWordHistory = new Stack<>();



    public Character popChinese() {
        return chineseWordHistory.pop();
    }





    public Character randomWord() {
        Random rand = new Random();
        int index = rand.nextInt(words.size());
        return words.get(index);
    }


    public DataHolder(Context context) {

        final AssetManager assets = context.getAssets();



        new Thread(new Runnable() {
            @Override
            public void run() {

                // load chinese word meta data
                try (BufferedReader bf = new BufferedReader(new InputStreamReader(assets.open("word.json")))) {
                    StringBuffer sb = new StringBuffer();
                    String line;
                    while ((line = bf.readLine()) != null) {
                        sb.append(line);
                    }

                    JSONArray jsonArray = new JSONArray(sb.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        pinyin.put(jsonObject.getString("word"), jsonObject.getString("pinyin"));
                        oldWord.put(jsonObject.getString("word"), jsonObject.getString("oldword"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // load chinese word
                try (BufferedReader bf = new BufferedReader(new InputStreamReader(assets.open("chinese.txt")))) {
                    String line;
                    while ((line = bf.readLine()) != null) {
                        if (!line.trim().equals("")) {
                            for (char c : line.toCharArray()) {
                                if (Pinyin.isChinese(c)) {
                                    words.add(c);
                                    if (pinyin.get(String.valueOf(c)) == null) {
                                        wordPinMap.put(c, Pinyin.toPinyin(c));
                                    } else {
                                        wordPinMap.put(c, pinyin.get(String.valueOf(c)));
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }





            }
        }).start();
    }
}
