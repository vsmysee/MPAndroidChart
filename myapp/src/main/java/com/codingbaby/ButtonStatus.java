package com.codingbaby;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.TypedValue;
import android.view.View;

public class ButtonStatus {

    private Context context;

    private View view;

    private DataHolder dataHolder;


    public boolean selectPoem = true;

    public boolean selectWord = false;
    public boolean selectIdiom = false;
    public boolean selectEnglishWord = false;
    public boolean selectShortEnglish = false;


    public boolean selectPoemForStudent;
    public boolean selectPoemForAll = true;


    public boolean selectWordForStudent;
    public boolean selectWordForPrimary;
    public boolean selectWordForSenior;
    public boolean selectWordForAll = true;


    public boolean selectIdiomStudent = false;
    public boolean selectIdiomForAll = true;


    public boolean selectEnglishStudent = false;
    public boolean selectEnglishPrimary = false;
    public boolean selectEnglishForAll = true;


    private boolean longPress = false;


    //功能按钮动画
    private ValueAnimator functionAnimator;

    {
        functionAnimator = ValueAnimator.ofInt(0, 500);
        functionAnimator.setDuration(2000);
        functionAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.invalidate();
            }
        });
        functionAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                longPress = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public ButtonStatus(Context context, View view, DataHolder dataHolder) {
        this.context = context;
        this.view = view;
        this.dataHolder = dataHolder;
    }

    public void cancelAnimation() {
        if (functionAnimator.isRunning()) {
            functionAnimator.cancel();
        }
    }

    public void startAnimation() {
        if (!functionAnimator.isStarted()) {
            functionAnimator.start();
        }
    }

    public boolean isRunAnimation() {
        return functionAnimator.isRunning();
    }


    public void onLongClick() {
        longPress = !longPress;
        if (longPress) {
            cancelAnimation();
        }
    }


    public void drawButton(Canvas canvas, Paint paint) {

        int gap = sp2px(40);
        int textSize = sp2px(15);

        if (!longPress) {
            return;
        }

        int wordY = sp2px(35);

        if (functionAnimator.isRunning()) {
            wordY = wordY - (int) functionAnimator.getAnimatedValue();
        }

        int radius = sp2px(15);
        int fromX = sp2px(30);
        int fromY = sp2px(30);

        if (functionAnimator.isRunning()) {
            fromY = fromY - (int) functionAnimator.getAnimatedValue();
        }

        int wordFrom = sp2px(22);

        paint.setColor(selectPoem ? Color.BLUE : Color.GRAY);
        canvas.drawCircle(fromX, fromY, radius, paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize(textSize);
        canvas.drawText("诗", wordFrom, wordY, paint);

        int n = 1;

        paint.setColor(selectWord ? Color.BLUE : Color.GRAY);
        canvas.drawCircle(fromX + n * gap, fromY, radius, paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize(textSize);
        canvas.drawText("字", wordFrom + n * gap, wordY, paint);

        n = 2;

        paint.setColor(selectIdiom ? Color.BLUE : Color.GRAY);
        canvas.drawCircle(fromX + n * gap, fromY, radius, paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize(textSize);
        canvas.drawText("成", wordFrom + n * gap, wordY, paint);

        n = 3;
        paint.setColor(selectEnglishWord ? Color.BLUE : Color.GRAY);
        canvas.drawCircle(fromX + n * gap, fromY, radius, paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize(textSize);
        canvas.drawText("英", wordFrom + n * gap, wordY, paint);


        n = 4;
        paint.setColor(selectShortEnglish ? Color.BLUE : Color.GRAY);
        canvas.drawCircle(fromX + n * gap, fromY, radius, paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize(textSize);
        canvas.drawText("短", wordFrom + n * gap, wordY, paint);
    }


    public void drawBottomButton(Canvas canvas, Paint paint, int height) {

        if (!longPress) {
            return;
        }

        int radius = 40;
        int bottomY = height - 100;
        paint.setTextSize(40);


        if (functionAnimator.isRunning()) {
            bottomY = bottomY + (int) functionAnimator.getAnimatedValue();
        }


        if (selectPoem) {

            int n = 0;


            paint.setColor(selectPoemForStudent ? Color.BLUE : Color.GRAY);
            canvas.drawCircle(100 + n * 100, bottomY, radius, paint);
            paint.setColor(Color.WHITE);
            canvas.drawText("学", 80 + n * 100, bottomY + 12, paint);

            n = 1;

            paint.setColor(selectPoemForAll ? Color.BLUE : Color.GRAY);
            canvas.drawCircle(100 + n * 100, bottomY, radius, paint);
            paint.setColor(Color.WHITE);
            canvas.drawText("全", 80 + n * 100, bottomY + 12, paint);


        }

        if (selectWord) {

            int n = 0;

            paint.setColor(selectWordForStudent ? Color.BLUE : Color.GRAY);
            canvas.drawCircle(100 + n * 100, bottomY, radius, paint);
            paint.setColor(Color.WHITE);
            canvas.drawText("学", 80 + n * 100, bottomY + 12, paint);


            n = 1;

            paint.setColor(selectWordForPrimary ? Color.BLUE : Color.GRAY);
            canvas.drawCircle(100 + n * 100, bottomY, radius, paint);
            paint.setColor(Color.WHITE);
            canvas.drawText("中", 80 + n * 100, bottomY + 12, paint);

            n = 2;

            paint.setColor(selectWordForSenior ? Color.BLUE : Color.GRAY);
            canvas.drawCircle(100 + n * 100, bottomY, radius, paint);
            paint.setColor(Color.WHITE);
            canvas.drawText("高", 80 + n * 100, bottomY + 12, paint);


            n = 3;

            paint.setColor(selectWordForAll ? Color.BLUE : Color.GRAY);
            canvas.drawCircle(100 + n * 100, bottomY, radius, paint);
            paint.setColor(Color.WHITE);
            canvas.drawText("全", 80 + n * 100, bottomY + 12, paint);
        }


        if (selectIdiom) {

            int n = 0;

            paint.setColor(selectIdiomStudent ? Color.BLUE : Color.GRAY);
            canvas.drawCircle(100 + n * 100, bottomY, radius, paint);
            paint.setColor(Color.WHITE);
            canvas.drawText("学", 80 + n * 100, bottomY + 12, paint);

            n = 1;

            paint.setColor(selectIdiomForAll ? Color.BLUE : Color.GRAY);
            canvas.drawCircle(100 + n * 100, bottomY, radius, paint);
            paint.setColor(Color.WHITE);
            canvas.drawText("全", 80 + n * 100, bottomY + 12, paint);
        }


        if (selectEnglishWord) {

            int n = 0;

            paint.setColor(selectEnglishStudent ? Color.BLUE : Color.GRAY);
            canvas.drawCircle(100 + n * 100, bottomY, radius, paint);
            paint.setColor(Color.WHITE);
            canvas.drawText("学", 80 + n * 100, bottomY + 12, paint);

            n = 1;

            paint.setColor(selectEnglishPrimary ? Color.BLUE : Color.GRAY);
            canvas.drawCircle(100 + n * 100, bottomY, radius, paint);
            paint.setColor(Color.WHITE);
            canvas.drawText("初", 80 + n * 100, bottomY + 12, paint);


            n = 2;

            paint.setColor(selectEnglishForAll ? Color.BLUE : Color.GRAY);
            canvas.drawCircle(100 + n * 100, bottomY, radius, paint);
            paint.setColor(Color.WHITE);
            canvas.drawText("全", 80 + n * 100, bottomY + 12, paint);
        }

    }

    /**
     * 检查是否点击了按钮
     *
     * @param y
     * @param x
     * @return
     */
    public void checkTouch(float y, float x, int height) {

        int gap = sp2px(40);
        int textSize = sp2px(15);

        if (!longPress) {
            return;
        }

        int topButtonX = textSize;
        if (y >= topButtonX && y <= 3 * topButtonX && x >= topButtonX && x <= 3 * topButtonX) {

            selectPoem = true;
            selectWord = false;
            selectShortEnglish = false;
            selectIdiom = false;
            selectEnglishWord = false;

        }

        int n = 1;

        if (y >= topButtonX && y <= 3 * topButtonX && x >= topButtonX + n * gap && x <= 3 * topButtonX + n * gap) {

            selectWord = true;
            selectPoem = false;
            selectShortEnglish = false;
            selectIdiom = false;
            selectEnglishWord = false;

        }

        n = 2;

        if (y >= topButtonX && y <= 3 * topButtonX && x >= topButtonX + n * gap && x <= 3 * topButtonX + n * gap) {

            selectIdiom = true;
            selectWord = false;
            selectShortEnglish = false;
            selectPoem = false;
            selectEnglishWord = false;


        }

        n = 3;

        if (y >= topButtonX && y <= 3 * topButtonX && x >= topButtonX + n * gap && x <= 3 * topButtonX + n * gap) {

            selectEnglishWord = true;
            selectShortEnglish = false;
            selectIdiom = false;
            selectWord = false;
            selectPoem = false;


        }


        n = 4;

        if (y >= topButtonX && y <= 3 * topButtonX && x >= topButtonX + n * gap && x <= 3 * topButtonX + n * gap) {

            selectShortEnglish = true;
            selectEnglishWord = false;
            selectIdiom = false;
            selectWord = false;
            selectPoem = false;


        }

        //选择大功能end

        n = 0;
        if (x > 60 + 100 * n && y > height - 140 && y < height - 60 && x < 140 + 100 * n) {

            if (selectPoem) {
                selectPoemForStudent = true;
                selectPoemForAll = false;
            }

            if (selectWord) {
                selectWordForStudent = true;
                selectWordForPrimary = false;
                selectWordForSenior = false;
                selectWordForAll = false;
            }

            if (selectIdiom) {
                selectIdiomStudent = true;
                selectIdiomForAll = false;
            }

            if (selectEnglishWord) {
                selectEnglishStudent = true;
                selectEnglishPrimary = false;
                selectEnglishForAll = false;
            }


        }


        n = 1;
        if (x > 60 + 100 * n && y > height - 140 && y < height - 60 && x < 140 + 100 * n) {
            if (selectPoem) {
                selectPoemForAll = true;
                selectPoemForStudent = false;
            }
            if (selectWord) {
                selectWordForStudent = false;
                selectWordForPrimary = true;
                selectWordForSenior = false;
                selectWordForAll = false;


            }

            if (selectIdiom) {
                selectIdiomStudent = false;
                selectIdiomForAll = true;
            }

            if (selectEnglishWord) {
                selectEnglishStudent = false;
                selectEnglishPrimary = true;
                selectEnglishForAll = false;
            }


        }

        n = 2;
        if (x > 60 + 100 * n && y > height - 140 && y < height - 60 && x < 140 + 100 * n) {

            if (selectWord) {
                selectWordForStudent = false;
                selectWordForPrimary = false;
                selectWordForSenior = true;
                selectWordForAll = false;

            }

            if (selectEnglishWord) {
                selectEnglishStudent = false;
                selectEnglishPrimary = false;
                selectEnglishForAll = true;
            }

        }

        n = 3;
        if (x > 60 + 100 * n && y > height - 140 && y < height - 60 && x < 140 + 100 * n) {

            if (selectWord) {
                selectWordForStudent = false;
                selectWordForPrimary = false;
                selectWordForSenior = false;
                selectWordForAll = true;


            }

        }

        startAnimation();

    }


    /**
     * sp转px
     *
     * @param sp sp值
     * @return px值
     */
    public int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                context.getResources().getDisplayMetrics());
    }


}
