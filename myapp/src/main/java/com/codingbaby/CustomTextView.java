package com.codingbaby;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;


public class CustomTextView extends View {

    // 画笔
    private Paint paint;

    private GestureDetector detector;


    private static List<String> poems = new ArrayList<>();

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


    private void random() {
        Random rand = new Random();
        int index = rand.nextInt(poems.size());
        poem = poems.get(index);
        history.push(poem);

    }


    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        detector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                if (cursor == 0) {
                    random();
                }
                invalidate();
            }

        });

        // load poem
        AssetManager assets = context.getAssets();
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(assets.open("poem.txt")))) {
            String line;
            while ((line = bf.readLine()) != null) {
                poems.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        random();

    }


    private void init() {

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setTextSize(sp2px(30));
        paint.setStyle(Paint.Style.STROKE);

    }

    @Override
    public void draw(Canvas canvas) {

        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();

        init();

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.rgb(205, 205, 205));
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);

        canvas.translate(getWidth() / 2, getHeight() / 2);

        drawPoem(canvas);

        super.draw(canvas);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
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

        paint.setColor(Color.BLACK);


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
                paint.setColor(Color.BLACK);
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
