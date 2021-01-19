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


    private static Map<Character, List<String>> english = new LinkedHashMap<>();
    private static List<String> english_primary1 = new ArrayList<>();
    private static List<String> english_primary2 = new ArrayList<>();
    private static List<String> english_primary3 = new ArrayList<>();
    private static List<String> english_primary4 = new ArrayList<>();
    private static List<String> english_primary5 = new ArrayList<>();
    private static List<String> english_primary6 = new ArrayList<>();


    private List<String> shortEnglish = new ArrayList<>();


    private List<String> idioms = new ArrayList<>();
    private List<String> idioms_students = new ArrayList<>();


    private Stack<String> poemHistory = new Stack<>();
    private Stack<Character> chineseWordHistory = new Stack<>();
    private Stack<String> englishWordHistory = new Stack<>();


    public List<String> getByChar(Character c) {
        return english.get(c);
    }

    public String popPoem() {
        return poemHistory.pop();
    }

    public Character popChinese() {
        return chineseWordHistory.pop();
    }

    public String popEnglish() {
        return englishWordHistory.pop();
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


    public List<String> randShortEnglish() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Random rand = new Random();
            int index = rand.nextInt(shortEnglish.size());
            list.add(shortEnglish.get(index));
        }
        return list;
    }

    public String randEnglish(ButtonStatus buttonStatus) {
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

        if (buttonStatus.english1) {
            index = rand.nextInt(english_primary1.size());
            englishWord = english_primary1.get(index);
        }

        if (buttonStatus.english2) {
            index = rand.nextInt(english_primary2.size());
            englishWord = english_primary2.get(index);
        }


        if (buttonStatus.english3) {
            index = rand.nextInt(english_primary3.size());
            englishWord = english_primary3.get(index);
        }

        if (buttonStatus.english4) {
            index = rand.nextInt(english_primary4.size());
            englishWord = english_primary4.get(index);
        }

        if (buttonStatus.english5) {
            index = rand.nextInt(english_primary5.size());
            englishWord = english_primary5.get(index);
        }


        if (buttonStatus.english6) {
            index = rand.nextInt(english_primary6.size());
            englishWord = english_primary6.get(index);
        }

        englishWordHistory.push(englishWord);

        return englishWord;

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

                shortEnglish.addAll(FileReader.loadCet4Short(assets));

                english.putAll(FileReader.loadEnglishWord(assets));
                english_primary1.addAll(FileReader.freqEnglish(assets, "cet4/freq1.txt"));
                english_primary2.addAll(FileReader.freqEnglish(assets, "cet4/freq2.txt"));
                english_primary3.addAll(FileReader.freqEnglish(assets, "cet4/freq3.txt"));
                english_primary4.addAll(FileReader.freqEnglish(assets, "cet4/freq4.txt"));
                english_primary5.addAll(FileReader.freqEnglish(assets, "cet4/freq5.txt"));
                english_primary6.addAll(FileReader.freqEnglish(assets, "cet4/freq.txt"));

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
