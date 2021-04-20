package com.codingbaby;

import android.content.Context;
import android.content.res.AssetManager;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

public class DataHolder {

    private List<String> poems = new ArrayList<>();

    private List<Character> words = new ArrayList<>();

    //拼音映射
    public Map<Character, String> wordPinMap = new LinkedHashMap<>();

    //全局拼音库
    private Map<String, String> pinyin = new HashMap<>();
    public Map<String, String> oldWord = new HashMap<>();


    private Stack<String> poemHistory = new Stack<>();


    public String popPoem() {
        return poemHistory.pop();
    }



    public String randomPoem() {
        Random rand = new Random();

        String poem = "";

        int index = rand.nextInt(poems.size());
        poem = poems.get(index);
        poemHistory.push(poem);


        return poem;

    }





    public DataHolder(Context context) {

        final AssetManager assets = context.getAssets();

        poems.addAll(FileReader.loadPoem(assets, "poem.txt"));



    }
}
