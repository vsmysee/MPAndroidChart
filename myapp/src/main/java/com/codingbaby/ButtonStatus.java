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


    public boolean selectPoem = true;

    public boolean selectWord = false;
    public boolean selectIdiom = false;


    public boolean selectPoemForStudent = true;
    public boolean selectPoemForPrimary = false;
    public boolean selectPoemForAll = false;


    public boolean selectWordForStudent;
    public boolean selectWordForPrimary;
    public boolean selectWordForSenior;
    public boolean selectWordForAll = true;


    public boolean selectIdiomStudent = false;
    public boolean selectIdiomForAll = true;


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

    public ButtonStatus(Context context, View view) {
        this.context = context;
        this.view = view;
    }


    public void startAnimation() {
        if (!functionAnimator.isStarted()) {
            functionAnimator.start();
        }
    }


    public void onLongClick() {
        longPress = !longPress;
    }


    public void drawButton(Canvas canvas, Paint paint) {

        if (!longPress) {
            return;
        }


        int gap = sp2px(40);
        int textSize = sp2px(15);

        paint.setTextSize(textSize);


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

        int n = 0;

        paint.setColor(selectPoem ? Color.BLUE : Color.GRAY);
        canvas.drawCircle(fromX + n * gap, fromY, radius, paint);
        paint.setColor(Color.WHITE);
        canvas.drawText("诗", wordFrom, wordY, paint);

        n = 1;

        paint.setColor(selectWord ? Color.BLUE : Color.GRAY);
        canvas.drawCircle(fromX + n * gap, fromY, radius, paint);
        paint.setColor(Color.WHITE);
        canvas.drawText("字", wordFrom + n * gap, wordY, paint);

        n = 2;

        paint.setColor(selectIdiom ? Color.BLUE : Color.GRAY);
        canvas.drawCircle(fromX + n * gap, fromY, radius, paint);
        paint.setColor(Color.WHITE);
        canvas.drawText("成", wordFrom + n * gap, wordY, paint);

    }


    public void drawBottomButton(Canvas canvas, Paint paint, int height) {

        if (!longPress) {
            return;
        }

        int gap = sp2px(40);
        int textSize = sp2px(15);
        paint.setTextSize(textSize);


        int wordY = height - sp2px(25);

        if (functionAnimator.isRunning()) {
            wordY = wordY + (int) functionAnimator.getAnimatedValue();
        }

        int radius = sp2px(15);
        int fromX = sp2px(30);
        int fromY = height - sp2px(30);

        if (functionAnimator.isRunning()) {
            fromY = fromY + (int) functionAnimator.getAnimatedValue();
        }

        int wordFrom = sp2px(22);


        if (selectPoem) {

            int n = 0;

            paint.setColor(selectPoemForStudent ? Color.BLUE : Color.GRAY);
            canvas.drawCircle(fromX + n * gap, fromY, radius, paint);
            paint.setColor(Color.WHITE);
            canvas.drawText("学", wordFrom, wordY, paint);

            n = 1;

            paint.setColor(selectPoemForPrimary ? Color.BLUE : Color.GRAY);
            canvas.drawCircle(fromX + n * gap, fromY, radius, paint);
            paint.setColor(Color.WHITE);
            canvas.drawText("初", wordFrom + n * gap, wordY, paint);

            n = 2;

            paint.setColor(selectPoemForAll ? Color.BLUE : Color.GRAY);
            canvas.drawCircle(fromX + n * gap, fromY, radius, paint);
            paint.setColor(Color.WHITE);
            canvas.drawText("高", wordFrom + n * gap, wordY, paint);


        }

        if (selectWord) {

            int n = 0;

            paint.setColor(selectWordForStudent ? Color.BLUE : Color.GRAY);
            canvas.drawCircle(fromX + n * gap, fromY, radius, paint);
            paint.setColor(Color.WHITE);
            canvas.drawText("学", wordFrom, wordY, paint);


            n = 1;

            paint.setColor(selectWordForPrimary ? Color.BLUE : Color.GRAY);
            canvas.drawCircle(fromX + n * gap, fromY, radius, paint);
            paint.setColor(Color.WHITE);
            canvas.drawText("中", wordFrom + n * gap, wordY, paint);

            n = 2;

            paint.setColor(selectWordForSenior ? Color.BLUE : Color.GRAY);
            canvas.drawCircle(fromX + n * gap, fromY, radius, paint);
            paint.setColor(Color.WHITE);
            canvas.drawText("高", wordFrom + n * gap, wordY, paint);


            n = 3;

            paint.setColor(selectWordForAll ? Color.BLUE : Color.GRAY);
            canvas.drawCircle(fromX + n * gap, fromY, radius, paint);
            paint.setColor(Color.WHITE);
            canvas.drawText("全", wordFrom + n * gap, wordY, paint);
        }


        if (selectIdiom) {

            int n = 0;

            paint.setColor(selectIdiomStudent ? Color.BLUE : Color.GRAY);
            canvas.drawCircle(fromX + n * gap, fromY, radius, paint);
            paint.setColor(Color.WHITE);
            canvas.drawText("学", wordFrom, wordY, paint);

            n = 1;

            paint.setColor(selectIdiomForAll ? Color.BLUE : Color.GRAY);
            canvas.drawCircle(fromX + n * gap, fromY, radius, paint);
            paint.setColor(Color.WHITE);
            canvas.drawText("全", wordFrom + n * gap, wordY, paint);
        }



    }

    /**
     * 检查是否点击了按钮
     *
     * @param y
     * @param x
     * @return
     */
    public boolean checkFuncTouch(float y, float x) {

        int gap = sp2px(40);
        int textSize = sp2px(15);

        if (!longPress) {
            return false;
        }

        int topButtonX = textSize;
        if (y >= topButtonX && y <= 3 * topButtonX && x >= topButtonX && x <= 3 * topButtonX) {

            selectPoem = true;
            selectWord = false;
            selectIdiom = false;

            return true;
        }

        int n = 1;

        if (y >= topButtonX && y <= 3 * topButtonX && x >= topButtonX + n * gap && x <= 3 * topButtonX + n * gap) {

            selectWord = true;
            selectPoem = false;
            selectIdiom = false;

            return true;

        }

        n = 2;

        if (y >= topButtonX && y <= 3 * topButtonX && x >= topButtonX + n * gap && x <= 3 * topButtonX + n * gap) {

            selectIdiom = true;
            selectWord = false;
            selectPoem = false;
            return true;


        }


        return false;
    }


    public boolean checkBottomTouch(float y, float x, int height) {

        if (!longPress) {
            return false;
        }

        int gap = sp2px(40);
        int textSize = sp2px(15);


        int topButtonX = height - 3 * textSize;

        int n = 0;
        if (y >= topButtonX && y <= height - textSize && x >= textSize + n * gap && x <= 3 * textSize + n * gap) {

            if (selectPoem) {
                selectPoemForStudent = true;
                selectPoemForPrimary = false;
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


            return true;

        }


        n = 1;
        if (y >= topButtonX && y <= height - textSize && x >= textSize + n * gap && x <= 3 * textSize + n * gap) {

            if (selectPoem) {
                selectPoemForAll = false;
                selectPoemForPrimary = true;
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

            return true;


        }

        n = 2;
        if (y >= topButtonX && y <= height - textSize && x >= textSize + n * gap && x <= 3 * textSize + n * gap) {

            if (selectPoem) {
                selectPoemForAll = true;
                selectPoemForPrimary = false;
                selectPoemForStudent = false;
            }

            if (selectWord) {
                selectWordForStudent = false;
                selectWordForPrimary = false;
                selectWordForSenior = true;
                selectWordForAll = false;

            }

            return true;

        }

        n = 3;
        if (y >= topButtonX && y <= height - textSize && x >= textSize + n * gap && x <= 3 * textSize + n * gap) {

            if (selectWord) {
                selectWordForStudent = false;
                selectWordForPrimary = false;
                selectWordForSenior = false;
                selectWordForAll = true;
            }


            return true;

        }

        return false;

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
