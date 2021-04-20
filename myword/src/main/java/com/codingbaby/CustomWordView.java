package com.codingbaby;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class CustomWordView extends View {


    private static int DEFAULT_COLOR = Color.BLACK;


    // 画笔
    private Paint paint = new Paint();


    //汉字动画
    private ValueAnimator wordAnimator;


    private Character chineseWord;
    private Character nextChineseWord;


    public static DataHolder dataHolder;


    private ButtonStatus buttonStatus;


    public CustomWordView(Context context, AttributeSet attrs) {
        super(context, attrs);


        buttonStatus = new ButtonStatus(context, this);


        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                buttonStatus.onLongClick();


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

        if (wordAnimator == null) {

            wordAnimator = ValueAnimator.ofInt(0, getWidth());
            wordAnimator.setDuration(600);
            wordAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    invalidate();
                }
            });
            wordAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    chineseWord = nextChineseWord;
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }

        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();


        //draw background
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);


        //draw button
        buttonStatus.drawButton(canvas, paint);

        initPint();


        canvas.translate(getWidth() / 2, getHeight() / 2);


        drawWord(canvas);


        super.draw(canvas);

    }


    //触摸屏幕
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            float y = event.getY();
            float x = event.getX();


            buttonStatus.checkFuncTouch(y, x);


            nextChineseWord = dataHolder.randomWord();

            if (!wordAnimator.isStarted()) {
                wordAnimator.start();
            }


            invalidate();

        }

        return super.onTouchEvent(event);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {

            case KeyEvent.KEYCODE_VOLUME_UP:


                try {
                    nextChineseWord = dataHolder.popChinese();
                    if (nextChineseWord.equals(chineseWord)) {
                        nextChineseWord = dataHolder.popChinese();
                    }
                } catch (Exception e) {
                    nextChineseWord = dataHolder.randomWord();
                }

                if (!wordAnimator.isStarted()) {
                    wordAnimator.start();
                }

                invalidate();


                return true;


            case KeyEvent.KEYCODE_VOLUME_DOWN:


                if (!wordAnimator.isStarted()) {
                    wordAnimator.start();
                }


                invalidate();
                return true;

        }

        return super.onKeyDown(keyCode, event);
    }


    private void drawWord(Canvas canvas) {

        if (chineseWord == null) {
            chineseWord = dataHolder.randomWord();
        }

        paint.setColor(DEFAULT_COLOR);
        paint.setTextSize(sp2px(80));

        Paint.FontMetrics fontMetrics = paint.getFontMetrics();

        // 文本高度
        float textHeight = fontMetrics.bottom - fontMetrics.top;

        // 中间文本的baseline
        float ascent = paint.ascent();
        float descent = paint.descent();

        float abs = Math.abs(ascent + descent);
        float centerBaselineY = abs / 2;


        if (!wordAnimator.isStarted()) {
            drawWord(canvas, textHeight, descent, centerBaselineY, chineseWord, 0);
        } else {
            int offset = (int) wordAnimator.getAnimatedValue();
            drawWord(canvas, textHeight, descent, centerBaselineY, chineseWord, -offset);
        }

    }


    private void drawWord(Canvas canvas, float textHeight, float descent, float centerBaselineY, Character wordParam, int offset) {
        List<String> rows = new ArrayList<>();

        rows.add(dataHolder.wordPinMap.get(wordParam));
        String w = String.valueOf(wordParam);
        rows.add(w);
        if (!w.equals(dataHolder.oldWord.get(w))) {
            rows.add(dataHolder.oldWord.get(w));
        }


        int textLines = rows.size();


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

            canvas.drawText(content, -textWidth / 2 + offset, baseY, paint);
            canvas.drawLine(-max + offset, baseY + descent, max + offset, baseY + descent, paint);
        }
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
