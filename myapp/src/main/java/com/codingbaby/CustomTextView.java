package com.codingbaby;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.github.promeg.pinyinhelper.Pinyin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;


public class CustomTextView extends View {

    private static int DEFAULT_COLOR = Color.BLACK;

    // 画笔
    private Paint paint = new Paint();

    private GestureDetector detector;


    private static List<String> poems = new ArrayList<>();
    private static List<Character> words = new ArrayList<>();
    private static Map<Character, String> word = new LinkedHashMap<>();

    private String poem;
    private Character chineseWord;


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


    private void random() {
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


        try (BufferedReader bf = new BufferedReader(new InputStreamReader(assets.open("word.txt")))) {
            String line;
            while ((line = bf.readLine()) != null) {
                if (!line.trim().equals("")) {
                    for (char c : line.toCharArray()) {
                        if (Pinyin.isChinese(c)) {
                            words.add(c);
                            word.put(c, Pinyin.toPinyin(c));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        random();
        randomWord();

    }


    private void init() {

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

            if (selectPoem) {
                paint.setColor(Color.BLUE);
                canvas.drawCircle(100, 100, 50, paint);
            } else {
                paint.setColor(Color.GRAY);
                canvas.drawCircle(100, 100, 50, paint);
            }

            if (selectWord) {
                paint.setColor(Color.BLUE);
                canvas.drawCircle(220, 100, 50, paint);
            } else {
                paint.setColor(Color.GRAY);
                canvas.drawCircle(220, 100, 50, paint);
            }

        }

        if (switchShow) {

            if (runTime > 2) {
                switchShow = false;
            } else {

                if (selectPoem) {
                    paint.setColor(Color.BLUE);
                    canvas.drawCircle(100, 100, 50, paint);
                } else {
                    paint.setColor(Color.GRAY);
                    canvas.drawCircle(100, 100, 50, paint);
                }

                if (selectWord) {
                    paint.setColor(Color.BLUE);
                    canvas.drawCircle(220, 100, 50, paint);
                } else {
                    paint.setColor(Color.GRAY);
                    canvas.drawCircle(220, 100, 50, paint);
                }

            }


        }


        init();


        canvas.translate(getWidth() / 2, getHeight() / 2);

        if (selectPoem) {
            drawPoem(canvas);
        }

        if (selectWord) {
            drawWord(canvas);
        }

        super.draw(canvas);

    }

    final Handler handler = new Handler();
    Runnable mLongPressed = new Runnable() {
        public void run() {
            longPress = true;
            invalidate();
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if ((event.getAction() == MotionEvent.ACTION_MOVE) || (event.getAction() == MotionEvent.ACTION_UP)) {
            handler.removeCallbacks(mLongPressed);
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            handler.postDelayed(mLongPressed, 2000);

            float y = event.getY();
            float x = event.getX();
            if (y >= 50 && event.getY() <= 150 && x >= 50 && x <= 150) {
                selectPoem = true;
                selectWord = false;
                longPress = false;

                switchShow = true;
                runTime = 0;
                invalidate();
                return super.onTouchEvent(event);
            }

            if (y >= 50 && event.getY() <= 150 && x >= 170 && x <= 270) {
                selectWord = true;
                selectPoem = false;
                longPress = false;

                switchShow = true;
                runTime = 0;

                invalidate();
                return super.onTouchEvent(event);

            }

            if (selectWord) {
                randomWord();
                invalidate();
            }

            if (selectPoem) {

                if (cursor == 0) {
                    random();
                }

                invalidate();
            }


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
                    random();
                }

                invalidate();
                return true;


            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (cursor == 0) {
                    random();
                }

                randomWord();

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

    private void drawWord(Canvas canvas) {


        paint.setTextSize(sp2px(60));

        Paint.FontMetrics fontMetrics = paint.getFontMetrics();

        // 文本高度
        float textHeight = fontMetrics.bottom - fontMetrics.top;

        List<String> rows = new ArrayList<>();

        rows.add(Pinyin.toPinyin(chineseWord).toLowerCase());
        rows.add(String.valueOf(chineseWord));

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


    private void drawPoem(Canvas canvas) {

        Paint.FontMetrics fontMetrics = paint.getFontMetrics();

        // 文本高度
        float textHeight = fontMetrics.bottom - fontMetrics.top;
        int showRow = (int) ((getHeight() / textHeight) - 2);

        List<String> rows = new ArrayList<>();

        if (cache.size() == 0) {

            reset();

            String[] textsSplit = poem.split(";");

            time = textsSplit[0];
            author = textsSplit[1];
            title = textsSplit[2];

            for (int i = 3; i < textsSplit.length; i++) {
                rows.add(textsSplit[i]);
            }

            if (rows.size() * textHeight > getHeight() - dp2px(100)) {

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
                random();
            }
        }


        int textLines = rows.size();

        // 中间文本的baseline
        float ascent = paint.ascent();
        float descent = paint.descent();

        float abs = Math.abs(ascent + descent);
        float centerBaselineY = abs / 2;


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
}
