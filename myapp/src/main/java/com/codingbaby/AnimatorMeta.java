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
    private int LONG = 500;


    public AnimatorMeta(final Bitmap moonMap, final Bitmap rainMap, final Bitmap grassMap, final View view) {

        String moon = "月";

        ValueAnimator moonVa = buildAnimator(SHORT, 6 * 1000);
        moonVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if ((int) valueAnimator.getAnimatedValue() != SHORT) {
                    view.invalidate();
                }
            }
        });

        keyWords.put(moon, moonVa);
        actions.put(moon, new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(moonMap, width / 2 - value, -height / 2, paint);
            }
        });


        String rain = "雨";

        ValueAnimator rainVa = buildAnimator(LONG, 10 * 1000);
        rainVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if ((int) valueAnimator.getAnimatedValue() != LONG) {
                    view.invalidate();
                }
            }
        });

        keyWords.put(rain, rainVa);
        actions.put(rain, new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(rainMap, -width / 2, -height / 2 - rainMap.getHeight() + value, paint);
            }
        });


        String grass = "草";

        ValueAnimator grassVa = buildAnimator(350, 10 * 1000);
        grassVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if ((int) valueAnimator.getAnimatedValue() != 350) {
                    view.invalidate();
                }
            }
        });

        keyWords.put(grass, grassVa);
        actions.put(grass, new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(grassMap, -width / 2, height / 2 + grassMap.getHeight() - value, paint);
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
