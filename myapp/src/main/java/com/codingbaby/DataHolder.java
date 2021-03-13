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

    private List<String> poems = new ArrayList<>();
    private List<String> poems_students = new ArrayList<>();
    private List<String> poems_primary = new ArrayList<>();

    private List<Character> words = new ArrayList<>();
    private List<Character> words_students = new ArrayList<>();
    private List<Character> words_primary = new ArrayList<>();
    private List<Character> words_senior = new ArrayList<>();

    //拼音映射
    public Map<Character, String> wordPinMap = new LinkedHashMap<>();

    //全局拼音库
    private Map<String, String> pinyin = new HashMap<>();
    public Map<String, String> oldWord = new HashMap<>();



    private List<String> idioms = new ArrayList<>();
    private List<String> idioms_students = new ArrayList<>();


    private Stack<String> poemHistory = new Stack<>();
    private Stack<Character> chineseWordHistory = new Stack<>();
    private Stack<String> englishWordHistory = new Stack<>();


    public String popPoem() {
        return poemHistory.pop();
    }

    public Character popChinese() {
        return chineseWordHistory.pop();
    }


    public String randomPoem(boolean selectPoemForStudent, boolean selectPoemForPrimary, boolean selectPoemForAll) {
        Random rand = new Random();

        String poem = "";

        if (selectPoemForStudent) {
            int index = rand.nextInt(poems_students.size());
            poem = poems_students.get(index);
            poemHistory.push(poem);
        }

        if (selectPoemForPrimary) {
            int index = rand.nextInt(poems_primary.size());
            poem = poems_primary.get(index);
            poemHistory.push(poem);
        }


        if (selectPoemForAll) {
            int index = rand.nextInt(poems.size());
            poem = poems.get(index);
            poemHistory.push(poem);
        }

        return poem;

    }


    public Character randChineseWord(boolean selectWordForStudent, boolean selectWordForPrimary, boolean selectWordForSenior) {

        Character word = randomWord();

        Random rand = new Random();

        if (selectWordForStudent) {
            int index = rand.nextInt(words_students.size());
            word = words_students.get(index);
        }

        if (selectWordForPrimary) {
            int index = rand.nextInt(words_primary.size());
            word = words_primary.get(index);
        }


        if (selectWordForSenior) {
            int index = rand.nextInt(words_senior.size());
            word = words_senior.get(index);
        }

        chineseWordHistory.push(word);

        return word;
    }

    private Character randomWord() {
        Random rand = new Random();
        int index = rand.nextInt(words.size());
        return words.get(index);
    }


    public List<String> randIdiom(boolean students) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {

            Random rand = new Random();
            int index = rand.nextInt(students ? idioms_students.size() : idioms.size());
            list.add(students ? idioms_students.get(index) : idioms.get(index));
        }
        return list;
    }



    public DataHolder(Context context) {

        final AssetManager assets = context.getAssets();

        poems.addAll(FileReader.loadPoem(assets, "poem.txt"));
        poems_students.addAll(FileReader.loadPoem(assets, "poem-students.txt"));
        poems_primary.addAll(FileReader.loadPoem(assets, "poem-students2.txt"));

        new Thread(new Runnable() {
            @Override
            public void run() {

                idioms.addAll(FileReader.loadIdiom(assets, "idiom.txt"));
                idioms_students.addAll(FileReader.loadIdiom(assets, "idiom-students.txt"));

            }
        }).start();


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


                words_students.addAll(FileReader.freqChinese(assets, "freqChinese.txt"));
                words_primary.addAll(FileReader.freqChinese(assets, "freqChinese2.txt"));
                words_senior.addAll(FileReader.freqChinese(assets, "freqChinese3.txt"));


            }
        }).start();
    }
}
