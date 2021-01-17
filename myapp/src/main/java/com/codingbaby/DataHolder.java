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
import java.util.Set;
import java.util.Stack;

public class DataHolder {

    private List<String> poems = new ArrayList<>();
    private List<String> poems_students = new ArrayList<>();

    private List<Character> words = new ArrayList<>();
    private List<Character> words_students = new ArrayList<>();
    private List<Character> words_primary = new ArrayList<>();
    private List<Character> words_senior = new ArrayList<>();

    //拼音映射
    public Map<Character, String> wordPinMap = new LinkedHashMap<>();

    //全局拼音库
    private Map<String, String> pinyin = new HashMap<>();
    public Map<String, String> oldWord = new HashMap<>();


    private static Map<Character, List<String>> english = new LinkedHashMap<>();
    private static List<String> english_primary = new ArrayList<>();
    private static List<String> english_senior = new ArrayList<>();
    private List<String> shortEnglish = new ArrayList<>();


    private List<String> idioms = new ArrayList<>();
    private List<String> idioms_students = new ArrayList<>();

    private Stack<String> history = new Stack<>();


    public List<String> getByChar(Character c) {
        return english.get(c);
    }

    public String pop() {
        return history.pop();
    }


    public String randomPoem(boolean selectPoemForStudent, boolean selectPoemForAll) {
        Random rand = new Random();

        String poem = "";

        if (selectPoemForStudent) {
            int index = rand.nextInt(poems_students.size());
            poem = poems_students.get(index);
            history.push(poem);
        }

        if (selectPoemForAll) {
            int index = rand.nextInt(poems.size());
            poem = poems.get(index);
            history.push(poem);
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


    public String randShortEnglish() {
        Random rand = new Random();
        int index = rand.nextInt(shortEnglish.size());
        return shortEnglish.get(index);
    }

    public String randEnglish(boolean selectEnglishStudent, boolean selectEnglishPrimary) {
        String englishWord;
        Random rand = new Random();
        Set<Character> characters = english.keySet();
        List<Character> keyList = new ArrayList<>();
        for (Character character : characters) {
            keyList.add(character);
        }
        int index = rand.nextInt(keyList.size());
        Character character = keyList.get(index);
        List<String> wordList = english.get(character);
        rand = new Random();
        englishWord = wordList.get(rand.nextInt(wordList.size()));

        if (selectEnglishStudent) {
            index = rand.nextInt(english_primary.size());
            englishWord = english_primary.get(index);
        }

        if (selectEnglishPrimary) {
            index = rand.nextInt(english_senior.size());
            englishWord = english_senior.get(index);
        }
        return englishWord;

    }


    public DataHolder(Context context) {
        final AssetManager assets = context.getAssets();

        poems.addAll(FileReader.loadPoem(assets));
        poems_students.addAll(FileReader.loadStudentPoem(assets));

        new Thread(new Runnable() {
            @Override
            public void run() {

                idioms.addAll(FileReader.loadIdiom(assets));
                idioms_students.addAll(FileReader.loadStudentIdiom(assets));
                shortEnglish.addAll(FileReader.loadCet4Short(assets));
                english.putAll(FileReader.loadEnglishWord(assets));
                english_primary.addAll(FileReader.freqEnglish(assets, "cet4/freq1.txt"));
                english_senior.addAll(FileReader.freqEnglish(assets, "cet4/freq.txt"));

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
