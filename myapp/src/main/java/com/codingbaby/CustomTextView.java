package com.codingbaby;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector;
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

    // 画笔
    private Paint paint = new Paint();

    //虚线笔
    Paint mPaintCircle = new Paint();

    {
        mPaintCircle.setStyle(Paint.Style.STROKE);
        mPaintCircle.setAntiAlias(true);
        mPaintCircle.setStrokeWidth(3);
        mPaintCircle.setColor(Color.RED);
        mPaintCircle.setPathEffect(new DashPathEffect(new float[]{4, 4}, 0));
    }

    private GestureDetector detector;

    private static List<String> poems = new ArrayList<>();
    private static List<Character> words = new ArrayList<>();
    private static Map<Character, String> word = new LinkedHashMap<>();
    private static Map<Character, List<String>> english = new LinkedHashMap<>();

    private String poem;
    private Character chineseWord;
    private String idiom;
    private String englishWord;


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


    private List<String> idioms = new ArrayList<>();

    private Map<String, String> pinyin = new HashMap<>();
    private Map<String, String> oldWord = new HashMap<>();

    private void randomPoem() {
        Random rand = new Random();
        int index = rand.nextInt(poems.size());
        poem = poems.get(index);
        history.push(poem);
    }

    private void randomWord() {
        Random rand = new Random();
        int index = rand.nextInt(words.size());
        chineseWord = words.get(index);
    }

    private void randIdiom() {
        Random rand = new Random();
        int index = rand.nextInt(idioms.size());
        idiom = idioms.get(index);
    }

    private void randEnglish() {
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
    }


    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        detector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
        });

        // load poem
        AssetManager assets = context.getAssets();
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(assets.open("poem.txt")))) {
            String line;
            while ((line = bf.readLine()) != null) {
                if (!line.trim().equals("")) {
                    poems.add(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        // load chinese word
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


        // load chinese idiom
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(assets.open("idiom.txt")))) {
            String line;
            while ((line = bf.readLine()) != null) {
                idioms.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


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


        randomPoem();
        randomWord();
        randIdiom();
        randEnglish();

        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                longPress = true;
                runTime = 0;
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

            if (runTime > 5) {
                switchShow = false;
                longPress = false;
            } else {
                drawButton(canvas);
            }

        }


        initPint();

        if (selectEnglishWord) {

            paint.setColor(Color.GRAY);
            canvas.drawCircle(100, getHeight() - 100, 40, paint);
            paint.setColor(Color.WHITE);
            paint.setTextSize(40);
            canvas.drawText("四", 80, getHeight() - 100 + 10, paint);


            paint.setColor(Color.GRAY);
            canvas.drawCircle(100 + 100, getHeight() - 100, 40, paint);
            paint.setColor(Color.WHITE);
            paint.setTextSize(40);
            canvas.drawText("六", 80 + 100, getHeight() - 100 + 10, paint);
        }


        if (selectPoem) {

            int n = 0;

            paint.setColor(Color.GRAY);
            canvas.drawCircle(100 + n * 100, getHeight() - 100, 40, paint);
            paint.setColor(Color.WHITE);
            paint.setTextSize(40);
            canvas.drawText("学", 80 + n * 100, getHeight() - 100 + 10, paint);

            n = 1;

            paint.setColor(Color.GRAY);
            canvas.drawCircle(100 + n * 100, getHeight() - 100, 40, paint);
            paint.setColor(Color.WHITE);
            paint.setTextSize(40);
            canvas.drawText("全", 80 + n * 100, getHeight() - 100 + 10, paint);

            n = 2;

            paint.setColor(Color.GRAY);
            canvas.drawCircle(100 + n * 100, getHeight() - 100, 40, paint);
            paint.setColor(Color.WHITE);
            paint.setTextSize(40);
            canvas.drawText("唐", 80 + n * 100, getHeight() - 100 + 10, paint);

            n = 3;

            paint.setColor(Color.GRAY);
            canvas.drawCircle(100 + n * 100, getHeight() - 100, 40, paint);
            paint.setColor(Color.WHITE);
            paint.setTextSize(40);
            canvas.drawText("宋", 80 + n * 100, getHeight() - 100 + 10, paint);

        }


        canvas.translate(getWidth() / 2, getHeight() / 2);

        if (selectPoem) {
            if (drawLinePoint.size() > 0) {
                for (LinePoint linePoint : drawLinePoint) {
                    canvas.drawLine(linePoint.sx, linePoint.sy, linePoint.ex, linePoint.ey, mPaintCircle);
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

        super.draw(canvas);

    }


    int gap = 120;

    private void drawButton(Canvas canvas) {


        if (selectPoem) {
            paint.setColor(Color.BLUE);
            canvas.drawCircle(100, 100, 50, paint);
            paint.setColor(Color.WHITE);
            paint.setTextSize(50);
            canvas.drawText("诗", 75, 120, paint);
        } else {
            paint.setColor(Color.GRAY);
            canvas.drawCircle(100, 100, 50, paint);
            paint.setColor(Color.WHITE);
            paint.setTextSize(50);
            canvas.drawText("诗", 75, 120, paint);
        }

        int n = 1;

        if (selectWord) {
            paint.setColor(Color.BLUE);
            canvas.drawCircle(100 + n * gap, 100, 50, paint);
            paint.setColor(Color.WHITE);
            paint.setTextSize(50);
            canvas.drawText("字", 75 + n * gap, 120, paint);
        } else {
            paint.setColor(Color.GRAY);
            canvas.drawCircle(100 + n * gap, 100, 50, paint);
            paint.setColor(Color.WHITE);
            paint.setTextSize(50);
            canvas.drawText("字", 75 + n * gap, 120, paint);
        }

        n = 2;
        if (selectIdiom) {
            paint.setColor(Color.BLUE);
            canvas.drawCircle(100 + n * gap, 100, 50, paint);
            paint.setColor(Color.WHITE);
            paint.setTextSize(50);
            canvas.drawText("语", 75 + n * gap, 120, paint);
        } else {
            paint.setColor(Color.GRAY);
            canvas.drawCircle(100 + n * gap, 100, 50, paint);
            paint.setColor(Color.WHITE);
            paint.setTextSize(50);
            canvas.drawText("语", 75 + n * gap, 120, paint);
        }

        n = 3;
        if (selectEnglishWord) {
            paint.setColor(Color.BLUE);
            canvas.drawCircle(100 + n * gap, 100, 50, paint);
            paint.setColor(Color.WHITE);
            paint.setTextSize(50);
            canvas.drawText("英", 75 + n * gap, 120, paint);
        } else {
            paint.setColor(Color.GRAY);
            canvas.drawCircle(100 + n * gap, 100, 50, paint);
            paint.setColor(Color.WHITE);
            paint.setTextSize(50);
            canvas.drawText("英", 75 + n * gap, 120, paint);
        }
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


            if (y >= 50 && event.getY() <= 150 && x >= 50 && x <= 150) {

                selectPoem = true;
                selectWord = false;
                selectIdiom = false;
                selectEnglishWord = false;

                longPress = false;
                switchShow = true;

                runTime = 0;
                invalidate();
                return super.onTouchEvent(event);
            }

            int n = 1;

            if (y >= 50 && event.getY() <= 150 && x >= 50 + n * gap && x <= 150 + n * gap) {

                selectWord = true;
                selectPoem = false;
                selectIdiom = false;
                selectEnglishWord = false;


                longPress = false;

                switchShow = true;
                runTime = 0;

                invalidate();
                return super.onTouchEvent(event);

            }

            n = 2;

            if (y >= 50 && event.getY() <= 150 && x >= 50 + n * gap && x <= 150 + n * gap) {

                selectIdiom = true;
                selectWord = false;
                selectPoem = false;
                selectEnglishWord = false;


                longPress = false;

                switchShow = true;
                runTime = 0;

                invalidate();
                return super.onTouchEvent(event);

            }

            n = 3;

            if (y >= 50 && event.getY() <= 150 && x >= 50 + n * gap && x <= 150 + n * gap) {

                selectEnglishWord = true;
                selectIdiom = false;
                selectWord = false;
                selectPoem = false;

                longPress = false;

                switchShow = true;
                runTime = 0;

                invalidate();
                return super.onTouchEvent(event);

            }

            if (selectWord) {
                randomWord();
            }

            if (selectPoem && cursor == 0) {
                randomPoem();
            }

            if (selectIdiom) {
                randIdiom();
            }

            if (selectEnglishWord) {
                randEnglish();
            }

            invalidate();

        }

        return super.onTouchEvent(event);
    }

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

                invalidate();
                return true;


            case KeyEvent.KEYCODE_VOLUME_DOWN:

                if (selectPoem && cursor == 0) {
                    randomPoem();
                }

                if (selectWord) {
                    randomWord();
                }

                if (selectIdiom) {
                    randIdiom();
                }

                if (selectEnglishWord) {
                    randEnglish();
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

        paint.setColor(DEFAULT_COLOR);

        paint.setTextSize(sp2px(30));

        Paint.FontMetrics fontMetrics = paint.getFontMetrics();

        // 文本高度
        float textHeight = fontMetrics.bottom - fontMetrics.top;

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

            float baseY = centerBaselineY + (i - (textLines - 1) / 2.0f) * textHeight;

            String content = rows.get(i);

            float textWidth = paint.measureText(content);

            canvas.drawText(content, -textWidth / 2, baseY, paint);
        }


        runTime++;


    }

    private void drawIdiom(Canvas canvas) {
        paint.setColor(DEFAULT_COLOR);

        paint.setTextSize(sp2px(30));

        Paint.FontMetrics fontMetrics = paint.getFontMetrics();

        // 文本高度
        float textHeight = fontMetrics.bottom - fontMetrics.top;

        List<String> rows = new ArrayList<>();

        String[] split = idiom.split(": ");

        rows.add(split[1]);
        rows.add(split[0]);

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


    private void drawWord(Canvas canvas) {
        paint.setColor(DEFAULT_COLOR);
        paint.setTextSize(sp2px(80));

        Paint.FontMetrics fontMetrics = paint.getFontMetrics();

        // 文本高度
        float textHeight = fontMetrics.bottom - fontMetrics.top;

        List<String> rows = new ArrayList<>();

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

    private void drawPoem(Canvas canvas, boolean reDraw) {

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
            if (!linePoints.contains(new LinePoint(-textWidth / 2, baseY + descent, textWidth / 2, baseY + descent))) {
                linePoints.add(new LinePoint(-textWidth / 2, baseY + descent, textWidth / 2, baseY + descent));
            }
        }


        //draw title
        paint.setColor(Color.BLUE);
        paint.setTextSize(sp2px(20));
        float textWidth = paint.measureText(title);
        canvas.drawText(title, -textWidth / 2, firstY - textHeight, paint);


        //draw author
        paint.setColor(Color.RED);
        String endText = time + " · " + author;
        float endTextWidth = paint.measureText(endText);
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
