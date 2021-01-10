package com.codingbaby;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

public class AnimatorMeta {


    private int SHORT = 180;


    public AnimatorMeta(final Bitmap moonMap, final View view) {

        String key = "月";


        ValueAnimator v = buildAnimator(SHORT, 6 * 1000);
        v.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if ((int) valueAnimator.getAnimatedValue() != SHORT) {
                    view.invalidate();
                }
            }
        });

        keyWords.put(key, v);
        actions.put(key, new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(moonMap, width / 2 - value, -height / 2, paint);
            }
        });


    }

    private ValueAnimator buildAnimator(int number, int duration) {

        ValueAnimator animator = ValueAnimator.ofInt(0, number);
        animator.setDuration(duration);

        return animator;
    }

    public AnimatorAction action(String key) {
        return actions.get(key);
    }

    public boolean isOn(String key) {
        return keyWords.containsKey(key) && keyWords.get(key).isStarted();
    }

    public ValueAnimator va(String key) {
        return keyWords.get(key);
    }

    public int getValue(String key) {
        return (int) keyWords.get(key).getAnimatedValue();
    }

    private Map<String, ValueAnimator> keyWords = new HashMap<>();
    private Map<String, AnimatorAction> actions = new HashMap<>();


    public void stop() {
        for (ValueAnimator value : keyWords.values()) {
            value.end();
        }
    }


    public void start(String poem) {

        stop();

        String[] split = poem.split(";");

        for (String key : keyWords.keySet()) {

            for (int i = 3; i < split.length; i++) {
                String p = split[i];
                if (p.contains(key)) {
                    if (!p.contains("岁")
                            && !p.contains("一")
                            && !p.contains("二")
                            && !p.contains("三")
                            && !p.contains("四")
                            && !p.contains("五")
                            && !p.contains("六")
                            && !p.contains("七")
                            && !p.contains("八")
                            && !p.contains("九")
                            && !p.contains("十")
                            && !p.contains("十一")
                            && !p.contains("十二")) {
                        keyWords.get(key).start();
                        break;
                    }
                }
            }

        }

    }


}
