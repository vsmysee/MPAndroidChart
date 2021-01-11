package com.codingbaby;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

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
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.Stack;


public class CustomTextView extends View {

    private static int DEFAULT_COLOR = Color.BLACK;

    private Bitmap moonMap = BitmapFactory.decodeResource(getResources(), R.drawable.moon);
    private Bitmap grassMap = BitmapFactory.decodeResource(getResources(), R.drawable.grass);
    private Bitmap rainMap = BitmapFactory.decodeResource(getResources(), R.drawable.rain);
    private Bitmap boatMap = BitmapFactory.decodeResource(getResources(), R.drawable.boat);
    private Bitmap sunMap = BitmapFactory.decodeResource(getResources(), R.drawable.sun);
    private Bitmap snowMap = BitmapFactory.decodeResource(getResources(), R.drawable.snow);
    private Bitmap autumn = BitmapFactory.decodeResource(getResources(), R.drawable.autumn);
    private Bitmap spring = BitmapFactory.decodeResource(getResources(), R.drawable.spring);
    private Bitmap peach = BitmapFactory.decodeResource(getResources(), R.drawable.peach);
    private Bitmap cloudMap = BitmapFactory.decodeResource(getResources(), R.drawable.cloud);

    private AnimatorMeta animatorMeta;

    // 画笔
    private Paint paint = new Paint();

    //虚线笔
    Paint virtualLine = new Paint();
    Paint virtualLineBlue = new Paint();

    {
        virtualLine.setStyle(Paint.Style.STROKE);
        virtualLine.setAntiAlias(true);
        virtualLine.setStrokeWidth(3);
        virtualLine.setColor(Color.RED);
        virtualLine.setPathEffect(new DashPathEffect(new float[]{4, 4}, 0));
    }

    {
        virtualLineBlue.setStyle(Paint.Style.STROKE);
        virtualLineBlue.setAntiAlias(true);
        virtualLineBlue.setStrokeWidth(3);
        virtualLineBlue.setColor(Color.GRAY);
        virtualLineBlue.setPathEffect(new DashPathEffect(new float[]{4, 4}, 0));
    }


    private List<String> poems = new ArrayList<>();
    private List<String> poems_students = new ArrayList<>();

    private List<Character> words = new ArrayList<>();
    private List<Character> words_students = new ArrayList<>();
    private Map<Character, String> word = new LinkedHashMap<>();

    private static Map<Character, List<String>> english = new LinkedHashMap<>();
    private List<String> shortEnglish = new ArrayList<>();


    private List<String> idioms = new ArrayList<>();
    private List<String> idioms_students = new ArrayList<>();

    private Map<String, String> pinyin = new HashMap<>();
    private Map<String, String> oldWord = new HashMap<>();


    private String poem;
    private static List<String> cache = new ArrayList<>();
    private static int cursor = 0;

    private static String time;
    private static String author;
    private static String title;

    private static float firstY = 0;
    private static float endY = 0;
    private static float maxWidth = 0;

    private Stack<String> history = new Stack<>();


    private boolean longPress = false;
    private boolean switchShow = false;
    protected int runTime = 0;


    private boolean selectPoem = true;
    private boolean selectWord = false;
    private boolean selectIdiom = false;
    private boolean selectEnglishWord = false;
    private boolean selectShortEnglish = false;


    private void randomPoem() {
        Random rand = new Random();

        if (selectPoemForStudent) {
            int index = rand.nextInt(poems_students.size());
            poem = poems_students.get(index);
            history.push(poem);
            return;
        }

        if (selectPoemForAll) {
            int index = rand.nextInt(poems.size());
            poem = poems.get(index);
            history.push(poem);
        }

    }

    private Character randomWord() {
        Random rand = new Random();
        int index = rand.nextInt(words.size());
        return words.get(index);
    }

    private Character randomStudentsWord() {
        Random rand = new Random();
        int index = rand.nextInt(words_students.size());
        return words_students.get(index);
    }


    private String randIdiom(boolean students) {
        Random rand = new Random();
        int index = rand.nextInt(students ? idioms_students.size() : idioms.size());
        return students ? idioms_students.get(index) : idioms.get(index);
    }

    private String randShortEnglish() {
        Random rand = new Random();
        int index = rand.nextInt(shortEnglish.size());
        return shortEnglish.get(index);
    }

    private String randEnglish() {
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
        return wordList.get(rand.nextInt(wordList.size()));
    }


    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        animatorMeta = new AnimatorMeta(moonMap, rainMap, grassMap, boatMap, sunMap, snowMap, autumn, spring, peach, cloudMap, this);

        final AssetManager assets = context.getAssets();


        poems.addAll(FileReader.loadPoem(assets));
        poems_students.addAll(FileReader.loadStudentPoem(assets));


        randomPoem();


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
                                        word.put(c, Pinyin.toPinyin(c));
                                    } else {
                                        word.put(c, pinyin.get(String.valueOf(c)));
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                words_students.addAll(FileReader.freqChinese(assets));

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {

                idioms.addAll(FileReader.loadIdiom(assets));
                idioms_students.addAll(FileReader.loadStudentIdiom(assets));

                shortEnglish.addAll(FileReader.loadCet4Short(assets));


                // load english word
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


            }
        }).start();


        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                longPress = !longPress;
                runTime = 0;
                switchShow = false;

                animatorMeta.stop();

                invalidate();
                return true;
            }
        });

    }


    private void initPint() {

        paint.setAntiAlias(true);
        paint.setColor(DEFAULT_COLOR);
        paint.setTextSize(sp2px(30));

    }

    @Override
    public void draw(Canvas canvas) {

        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();


        //draw background
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.rgb(205, 205, 205));
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);


        //draw button
        if (longPress) {
            drawButton(canvas);
            switchShow = true;
        }

        if (switchShow) {
            if (runTime == 2) {
                switchShow = false;
                longPress = false;
            } else {
                drawButton(canvas);
            }
        }


        initPint();

        int radius = 40;

        if (selectEnglishWord && longPress) {

            paint.setTextSize(40);


            paint.setColor(Color.GRAY);
            canvas.drawCircle(100, getHeight() - 100, radius, paint);
            paint.setColor(Color.WHITE);
            canvas.drawText("四", 80, getHeight() - 100 + 12, paint);


            paint.setColor(Color.GRAY);
            canvas.drawCircle(100 + 100, getHeight() - 100, radius, paint);
            paint.setColor(Color.WHITE);
            canvas.drawText("六", 80 + 100, getHeight() - 100 + 12, paint);
        }


        if (selectPoem && longPress) {

            int n = 0;
            paint.setTextSize(40);


            paint.setColor(selectPoemForStudent ? Color.BLUE : Color.GRAY);
            canvas.drawCircle(100 + n * 100, getHeight() - 100, radius, paint);
            paint.setColor(Color.WHITE);
            canvas.drawText("学", 80 + n * 100, getHeight() - 100 + 12, paint);

            n = 1;

            paint.setColor(selectPoemForAll ? Color.BLUE : Color.GRAY);
            canvas.drawCircle(100 + n * 100, getHeight() - 100, radius, paint);
            paint.setColor(Color.WHITE);
            canvas.drawText("全", 80 + n * 100, getHeight() - 100 + 12, paint);

            n = 2;

            paint.setColor(Color.GRAY);
            canvas.drawCircle(100 + n * 100, getHeight() - 100, radius, paint);
            paint.setColor(Color.WHITE);
            canvas.drawText("唐", 80 + n * 100, getHeight() - 100 + 12, paint);

            n = 3;

            paint.setColor(Color.GRAY);
            canvas.drawCircle(100 + n * 100, getHeight() - 100, radius, paint);
            paint.setColor(Color.WHITE);
            canvas.drawText("宋", 80 + n * 100, getHeight() - 100 + 12, paint);

        }

        if (selectWord && longPress) {

            int n = 0;

            paint.setColor(selectWordForStudent ? Color.BLUE : Color.GRAY);
            canvas.drawCircle(100 + n * 100, getHeight() - 100, radius, paint);
            paint.setColor(Color.WHITE);
            paint.setTextSize(40);
            canvas.drawText("学", 80 + n * 100, getHeight() - 100 + 12, paint);


            n = 1;

            paint.setColor(selectWordForAll ? Color.BLUE : Color.GRAY);
            canvas.drawCircle(100 + n * 100, getHeight() - 100, radius, paint);
            paint.setColor(Color.WHITE);
            paint.setTextSize(40);
            canvas.drawText("全", 80 + n * 100, getHeight() - 100 + 12, paint);
        }


        if (selectIdiom && longPress) {

            int n = 0;

            paint.setColor(selectIdiomStudent ? Color.BLUE : Color.GRAY);
            canvas.drawCircle(100 + n * 100, getHeight() - 100, radius, paint);
            paint.setColor(Color.WHITE);
            paint.setTextSize(40);
            canvas.drawText("学", 80 + n * 100, getHeight() - 100 + 12, paint);

            n = 1;

            paint.setColor(selectIdiomForAll ? Color.BLUE : Color.GRAY);
            canvas.drawCircle(100 + n * 100, getHeight() - 100, radius, paint);
            paint.setColor(Color.WHITE);
            paint.setTextSize(40);
            canvas.drawText("全", 80 + n * 100, getHeight() - 100 + 12, paint);
        }


        canvas.translate(getWidth() / 2, getHeight() / 2);


        if (selectPoem) {

            for (String key : animatorMeta.keySet()) {
                if (animatorMeta.isOn(key)) {
                    int value = animatorMeta.getValue(key);
                    ValueAnimator va = animatorMeta.va(key);
                    animatorMeta.action(key).draw(va, canvas, paint, getHeight(), getWidth(), value);
                }
            }


            if (drawLinePoint.size() > 0) {
                for (LinePoint linePoint : drawLinePoint) {
                    canvas.drawLine(linePoint.sx, linePoint.sy, linePoint.ex, linePoint.ey, virtualLine);
                }
                drawPoem(canvas, true);
            } else {
                drawPoem(canvas, false);
            }


        }

        if (selectWord) {
            drawWord(canvas);
        }

        if (selectIdiom) {
            drawIdiom(canvas);
        }

        if (selectEnglishWord) {
            drawEnglishWord(canvas);
        }

        if (selectShortEnglish) {
            drawShortEnglish(canvas);
        }

        super.draw(canvas);

    }


    int gap = sp2px(40);
    int textSize = sp2px(15);
    int wordY = sp2px(35);

    private void drawButton(Canvas canvas) {

        int radius = sp2px(15);
        int from = sp2px(30);
        int wordFrom = sp2px(22);

        paint.setColor(selectPoem ? Color.BLUE : Color.GRAY);
        canvas.drawCircle(from, from, radius, paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize(textSize);
        canvas.drawText("诗", wordFrom, wordY, paint);

        int n = 1;

        paint.setColor(selectWord ? Color.BLUE : Color.GRAY);
        canvas.drawCircle(from + n * gap, from, radius, paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize(textSize);
        canvas.drawText("字", wordFrom + n * gap, wordY, paint);

        n = 2;

        paint.setColor(selectIdiom ? Color.BLUE : Color.GRAY);
        canvas.drawCircle(from + n * gap, from, radius, paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize(textSize);
        canvas.drawText("成", wordFrom + n * gap, wordY, paint);

        n = 3;
        paint.setColor(selectEnglishWord ? Color.BLUE : Color.GRAY);
        canvas.drawCircle(from + n * gap, from, radius, paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize(textSize);
        canvas.drawText("英", wordFrom + n * gap, wordY, paint);


        n = 4;
        paint.setColor(selectShortEnglish ? Color.BLUE : Color.GRAY);
        canvas.drawCircle(from + n * gap, from, radius, paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize(textSize);
        canvas.drawText("短", wordFrom + n * gap, wordY, paint);
    }


    private List<LinePoint> drawLinePoint = new ArrayList<>();


    @Override
    public boolean onTouchEvent(MotionEvent event) {


        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            float y = event.getY();
            float x = event.getX();


            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            float clickX = x - centerX;

            boolean touchPoemItem = false;

            for (LinePoint linePoint : linePoints) {
                if (clickX > linePoint.sx && clickX < linePoint.ex) {
                    if (linePoint.ey + centerY - poemWordHeight < y && y < linePoint.ey + centerY) {
                        if (drawLinePoint.contains(linePoint)) {
                            drawLinePoint.remove(linePoint);
                        } else {
                            drawLinePoint.add(linePoint);
                        }
                        touchPoemItem = true;
                        break;
                    }
                }
            }

            if (touchPoemItem) {
                invalidate();
                return super.onTouchEvent(event);
            }

            if (!touchPoemItem) {
                drawLinePoint.clear();
            }


            if (checkTouch(y, x)) {
                invalidate();
                return super.onTouchEvent(event);
            }


            if (selectPoem && cursor == 0) {

                randomPoem();

                animatorMeta.start(poem);

            }

            invalidate();

        }

        return super.onTouchEvent(event);
    }


    private boolean checkTouch(float y, float x) {

        int topButtonX = textSize;
        if (y >= topButtonX && y <= 3 * topButtonX && x >= topButtonX && x <= 3 * topButtonX) {

            selectPoem = true;
            selectWord = false;
            selectShortEnglish = false;
            selectIdiom = false;
            selectEnglishWord = false;

            longPress = false;
            switchShow = true;

            runTime = 0;
            return true;
        }

        int n = 1;

        if (y >= topButtonX && y <= 3 * topButtonX && x >= topButtonX + n * gap && x <= 3 * topButtonX + n * gap) {

            selectWord = true;
            selectPoem = false;
            selectShortEnglish = false;
            selectIdiom = false;
            selectEnglishWord = false;


            longPress = false;

            switchShow = true;
            runTime = 0;

            return true;

        }

        n = 2;

        if (y >= topButtonX && y <= 3 * topButtonX && x >= topButtonX + n * gap && x <= 3 * topButtonX + n * gap) {

            selectIdiom = true;
            selectWord = false;
            selectShortEnglish = false;
            selectPoem = false;
            selectEnglishWord = false;


            longPress = false;

            switchShow = true;
            runTime = 0;

            return true;

        }

        n = 3;

        if (y >= topButtonX && y <= 3 * topButtonX && x >= topButtonX + n * gap && x <= 3 * topButtonX + n * gap) {

            selectEnglishWord = true;
            selectShortEnglish = false;
            selectIdiom = false;
            selectWord = false;
            selectPoem = false;

            longPress = false;

            switchShow = true;
            runTime = 0;

            return true;

        }


        n = 4;

        if (y >= topButtonX && y <= 3 * topButtonX && x >= topButtonX + n * gap && x <= 3 * topButtonX + n * gap) {

            selectShortEnglish = true;
            selectEnglishWord = false;
            selectIdiom = false;
            selectWord = false;
            selectPoem = false;

            longPress = false;

            switchShow = true;
            runTime = 0;

            return true;

        }


        if (x > 60 && y > getHeight() - 140 && y < getHeight() - 60 && x < 140) {
            if (selectPoem) {
                selectPoemForStudent = true;
                selectPoemForAll = false;
            }
            if (selectWord) {
                selectWordForStudent = true;
                selectWordForAll = false;
            }

            if (selectIdiom) {
                selectIdiomStudent = true;
                selectIdiomForAll = false;
            }
        }

        if (x > 60 + 100 && y > getHeight() - 140 && y < getHeight() - 60 && x < 140 + 100) {
            if (selectPoem) {
                selectPoemForAll = true;
                selectPoemForStudent = false;
            }
            if (selectWord) {
                selectWordForStudent = false;
                selectWordForAll = true;
            }

            if (selectIdiom) {
                selectIdiomStudent = false;
                selectIdiomForAll = true;
            }

        }


        return false;
    }


    private boolean selectPoemForStudent;
    private boolean selectPoemForAll = true;

    private boolean selectWordForStudent;
    private boolean selectWordForAll = true;


    private boolean selectIdiomStudent;
    private boolean selectIdiomForAll = true;


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {

            case KeyEvent.KEYCODE_VOLUME_UP:

                cache.clear();
                reset();

                try {
                    String pop = history.pop();
                    if (pop.equals(poem)) {
                        poem = history.pop();
                    } else {
                        poem = pop;
                    }

                } catch (Exception e) {
                    randomPoem();
                }

                animatorMeta.start(poem);

                invalidate();
                return true;


            case KeyEvent.KEYCODE_VOLUME_DOWN:

                if (selectPoem && cursor == 0) {
                    randomPoem();

                    animatorMeta.start(poem);

                }


                invalidate();
                return true;

        }

        return super.onKeyDown(keyCode, event);
    }


    private void reset() {
        cursor = 0;
        firstY = 0;
        endY = 0;
        maxWidth = 0;
    }

    private void drawEnglishWord(Canvas canvas) {

        paint.setTextSize(sp2px(30));

        Paint.FontMetrics fontMetrics = paint.getFontMetrics();

        // 文本高度
        float textHeight = fontMetrics.bottom - fontMetrics.top;

        String englishWord = randEnglish();

        List<String> rows = new ArrayList<>();
        int start = englishWord.indexOf("[");
        rows.add(englishWord.substring(0, start));
        int end = englishWord.indexOf("]");
        rows.add(englishWord.substring(start, end + 1));
        rows.add(englishWord.substring(end + 1));


        int textLines = rows.size();

        // 中间文本的baseline
        float ascent = paint.ascent();
        float descent = paint.descent();

        float abs = Math.abs(ascent + descent);
        float centerBaselineY = abs / 2;


        for (int i = 0; i < textLines; i++) {

            if (i == 0) {
                paint.setColor(Color.BLUE);
            } else {
                paint.setColor(DEFAULT_COLOR);
            }

            float baseY = centerBaselineY + (i - (textLines - 1) / 2.0f) * textHeight;

            String content = rows.get(i);

            float textWidth = paint.measureText(content);

            canvas.drawText(content, -textWidth / 2, baseY, paint);
            if (i == 0 || i == 1) {
                canvas.drawLine(-textWidth, baseY + descent, textWidth, baseY + descent, paint);
            }
        }


        runTime++;


    }

    private void drawShortEnglish(Canvas canvas) {
        paint.setColor(DEFAULT_COLOR);

        paint.setTextSize(sp2px(18));

        Paint.FontMetrics fontMetrics = paint.getFontMetrics();

        // 文本高度
        float textHeight = fontMetrics.bottom - fontMetrics.top;

        List<String> rows = new ArrayList<>();

        for (int n = 0; n < 10; n++) {
            rows.add(randShortEnglish());
        }

        int textLines = rows.size();

        // 中间文本的baseline
        float ascent = paint.ascent();
        float descent = paint.descent();

        float abs = Math.abs(ascent + descent);
        float centerBaselineY = abs / 2;

        for (int i = 0; i < textLines; i++) {

            float baseY = centerBaselineY + (i - (textLines - 1) / 2.0f) * textHeight;

            String content = rows.get(i);
            float textWidth = paint.measureText(content);

            canvas.drawText(content, -textWidth / 2, baseY, paint);
        }

        runTime++;

    }


    private void drawIdiom(Canvas canvas) {
        paint.setColor(DEFAULT_COLOR);

        paint.setTextSize(sp2px(35));

        Paint.FontMetrics fontMetrics = paint.getFontMetrics();

        // 文本高度
        float textHeight = fontMetrics.bottom - fontMetrics.top;

        List<String> rows = new ArrayList<>();

        for (int n = 0; n < 5; n++) {
            rows.add(randIdiom(selectIdiomStudent));
        }

        int textLines = rows.size();

        // 中间文本的baseline
        float ascent = paint.ascent();
        float descent = paint.descent();

        float abs = Math.abs(ascent + descent);
        float centerBaselineY = abs / 2;

        for (int i = 0; i < textLines; i++) {

            float baseY = centerBaselineY + (i - (textLines - 1) / 2.0f) * textHeight;

            String content = rows.get(i);
            float textWidth = paint.measureText(content);

            canvas.drawText(content, -textWidth / 2, baseY, paint);
            canvas.drawLine(-textWidth / 2, baseY + descent, textWidth / 2, baseY + descent, paint);
        }

        runTime++;

    }


    private void drawWord(Canvas canvas) {
        paint.setColor(DEFAULT_COLOR);
        paint.setTextSize(sp2px(80));

        Paint.FontMetrics fontMetrics = paint.getFontMetrics();

        // 文本高度
        float textHeight = fontMetrics.bottom - fontMetrics.top;

        List<String> rows = new ArrayList<>();

        Character chineseWord = randomWord();

        if (selectWordForStudent) {
            chineseWord = randomStudentsWord();
        }


        rows.add(word.get(chineseWord));
        String w = String.valueOf(chineseWord);
        rows.add(w);
        if (!oldWord.get(w).equals(w)) {
            rows.add(oldWord.get(w));
        }

        int textLines = rows.size();

        // 中间文本的baseline
        float ascent = paint.ascent();
        float descent = paint.descent();

        float abs = Math.abs(ascent + descent);
        float centerBaselineY = abs / 2;

        float max = 0;

        for (int i = 0; i < textLines; i++) {

            float baseY = centerBaselineY + (i - (textLines - 1) / 2.0f) * textHeight;

            String content = rows.get(i);

            float textWidth = paint.measureText(content);
            if (textWidth > max) {
                max = textWidth;
            }

            if (i == 0) {
                paint.setColor(Color.BLUE);
            } else {
                paint.setColor(DEFAULT_COLOR);
            }

            canvas.drawText(content, -textWidth / 2, baseY, paint);
            canvas.drawLine(-max, baseY + descent, max, baseY + descent, paint);
        }

        runTime++;

    }


    private float poemWordHeight;
    private List<String> rows = new ArrayList<>();

    private void drawPoem(final Canvas canvas, boolean reDraw) {

        initPint();


        Paint.FontMetrics fontMetrics = paint.getFontMetrics();

        // 文本高度
        float textHeight = poemWordHeight = fontMetrics.bottom - fontMetrics.top;
        // 中间文本的baseline
        float ascent = paint.ascent();
        float descent = paint.descent();

        float abs = Math.abs(ascent + descent);
        float centerBaselineY = abs / 2;

        int showRow = (int) ((getHeight() / textHeight) - 5);

        if (!reDraw) {
            rows = new ArrayList<>();
        }
        linePoints.clear();

        if (!reDraw) {

            if (cache.size() == 0) {

                reset();

                String[] textsSplit = poem.split(";");

                time = textsSplit[0];
                author = textsSplit[1];
                title = textsSplit[2];

                for (int i = 3; i < textsSplit.length; i++) {
                    rows.add(textsSplit[i]);
                }

                if (rows.size() * textHeight > getHeight() - dp2px(200)) {

                    cache.clear();

                    for (String row : rows) {
                        cache.add(row);
                    }

                }
            }

            if (cache.size() > 0) {

                rows.clear();
                int end = cursor + showRow;

                for (int i = cursor; i < end; i++) {
                    try {
                        rows.add(cache.get(i));
                        cursor++;
                    } catch (Exception e) {
                        break;
                    }
                }

                //补齐
                if (rows.size() != showRow) {
                    List<String> padding = new ArrayList<>();
                    int missCount = showRow - rows.size();
                    int from = cache.size() - rows.size() - missCount;
                    for (int j = 0; j < missCount; j++) {
                        padding.add("w" + cache.get(from + j));
                    }
                    padding.addAll(rows);
                    rows = padding;
                    cache.clear();
                    randomPoem();
                }
            }

        }

        int textLines = rows.size();


        for (int i = 0; i < textLines; i++) {

            float baseY = centerBaselineY + (i - (textLines - 1) / 2.0f) * textHeight;

            if (i == 0 && firstY == 0) {
                firstY = baseY;
            }

            String content = rows.get(i);

            if (content.startsWith("w")) {
                content = content.replace("w", "");
                paint.setColor(Color.WHITE);
            } else {
                paint.setColor(DEFAULT_COLOR);
            }


            float textWidth = paint.measureText(content);
            if (textWidth > maxWidth) {
                maxWidth = textWidth;
            }


            if (i == rows.size() - 1 && endY == 0) {
                endY = baseY;
            }

            canvas.drawText(content, -textWidth / 2, baseY, paint);

            //触摸线
            if (!linePoints.contains(new LinePoint(-textWidth / 2, baseY + descent, textWidth / 2, baseY + descent))) {
                linePoints.add(new LinePoint(-textWidth / 2, baseY + descent, textWidth / 2, baseY + descent));
            }


            //分割线
            int cut = 4;
            if (cache.size() == 0 && rows.size() > cut && rows.size() % cut == 0 && i > 0 && ((i + 1) % cut == 0) && i + 1 != rows.size()) {
                //canvas.drawLine(-getWidth() / 2, baseY + descent + 3, getWidth() / 2, baseY + descent + 2, virtualLineBlue);
            }
            cut = 5;
            if (cache.size() == 0 && rows.size() > cut && rows.size() % cut == 0 && i > 0 && ((i + 1) % cut == 0) && i + 1 != rows.size()) {
                //canvas.drawLine(-getWidth() / 2, baseY + descent + 3, getWidth() / 2, baseY + descent + 2, virtualLineBlue);
            }

        }


        paint.setTextSize(sp2px(20));

        //draw title
        paint.setColor(Color.BLUE);
        float textWidth = paint.measureText(title);
        canvas.drawText(title, -textWidth / 2, firstY - textHeight, paint);


        //draw author
        paint.setColor(Color.RED);
        String endText = time + "  " + author;
        float endTextWidth = paint.measureText(endText);
        float timeWidth = paint.measureText(time);
        canvas.drawRoundRect(maxWidth / 2 - endTextWidth - 5, endY + 2 * descent + 10, maxWidth / 2 - endTextWidth + timeWidth + 5, endY + textHeight + descent, 10, 10, paint);
        paint.setColor(Color.WHITE);
        canvas.drawText(endText, maxWidth / 2 - endTextWidth, endY + textHeight, paint);


        runTime++;

    }

    private List<LinePoint> linePoints = new ArrayList<>();

    /**
     * dp转px
     *
     * @param dp dp值
     * @return px值
     */
    public int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getContext().getResources().getDisplayMetrics());
    }


    /**
     * sp转px
     *
     * @param sp sp值
     * @return px值
     */
    public int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                getContext().getResources().getDisplayMetrics());
    }

    static class LinePoint {
        private float sx;
        private float sy;
        private float ex;
        private float ey;

        public LinePoint(float sx, float sy, float ex, float ey) {
            this.sx = sx;
            this.sy = sy;
            this.ex = ex;
            this.ey = ey;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LinePoint linePoint = (LinePoint) o;
            return Float.compare(linePoint.sx, sx) == 0 &&
                    Float.compare(linePoint.sy, sy) == 0 &&
                    Float.compare(linePoint.ex, ex) == 0 &&
                    Float.compare(linePoint.ey, ey) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(sx, sy, ex, ey);
        }
    }
}
