package com.codingbaby;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AnimatorMeta {


    private int SHORT = 180;
    private int LONG = 500;


    public AnimatorMeta(final Bitmap moonMap, final Bitmap rainMap, final Bitmap grassMap,
                        final Bitmap boatMap, final Bitmap sunMap, final Bitmap snowMap, final Bitmap autumnMap,
                        final Bitmap springMap, final Bitmap springMap2, final Bitmap peachMap,
                        final View view) {

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

        String sun = "日出";

        ValueAnimator sunVa = buildAnimator(SHORT, 6 * 1000);
        sunVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if ((int) valueAnimator.getAnimatedValue() != SHORT) {
                    view.invalidate();
                }
            }
        });

        keyWords.put(sun, sunVa);
        actions.put(sun, new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(sunMap, -width / 2 + value, -height / 2, paint);
            }
        });

        keyWords.put("落日", sunVa);
        actions.put("落日", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(sunMap, -width / 2 - value, -height / 2, paint);
            }
        });

        keyWords.put("白日", sunVa);
        actions.put("白日", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(sunMap, -width / 2 - value, -height / 2, paint);
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


        String snow = "雪";

        ValueAnimator snowVa = buildAnimator(2000, 20 * 1000);
        snowVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if ((int) valueAnimator.getAnimatedValue() != 2000) {
                    view.invalidate();
                }
            }
        });

        keyWords.put(snow, snowVa);
        actions.put(snow, new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(snowMap, -width / 2, -height / 2 - snowMap.getHeight() + value, paint);
            }
        });

        String autumn = "秋";

        ValueAnimator autumnVa = buildAnimator(500, 10 * 1000);
        autumnVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if ((int) valueAnimator.getAnimatedValue() != 500) {
                    view.invalidate();
                }
            }
        });

        keyWords.put(autumn, autumnVa);
        actions.put(autumn, new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(autumnMap, -width / 2 - 50, height / 2 - value, paint);
            }
        });


        String spring = "春";

        ValueAnimator springVa = buildAnimator(100, 10 * 1000);
        springVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if ((int) valueAnimator.getAnimatedValue() != 100) {
                    view.invalidate();
                }
            }
        });

        keyWords.put(spring, springVa);
        actions.put(spring, new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                for (int i = 0; i < 20; i++) {
                    canvas.drawBitmap(springMap, -width / 2 + i * 80, height / 2 - value, paint);
                    if (i == 5) {
                        canvas.drawBitmap(springMap2, -width / 2 + i * 70, height / 2 - value, paint);
                    }
                }
            }
        });


        String peach = "桃";

        ValueAnimator peachVa = buildAnimator(1000, 10 * 1000);
        peachVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if ((int) valueAnimator.getAnimatedValue() != 1000) {
                    view.invalidate();
                }
            }
        });

        keyWords.put(peach, peachVa);
        actions.put(peach, new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(peachMap, -width / 2, height / 2 + grassMap.getHeight() - value, paint);
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

        keyWords.put("花", grassVa);
        actions.put("花", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(grassMap, -width / 2, height / 2 + grassMap.getHeight() - value, paint);
            }
        });


        String boat = "船";

        ValueAnimator boatVa = buildAnimator(1300, 20 * 1000);
        boatVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if ((int) valueAnimator.getAnimatedValue() != 1300) {
                    view.invalidate();
                }
            }
        });

        keyWords.put(boat, boatVa);
        actions.put(boat, new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(boatMap, -width / 2 - boatMap.getWidth() + value, height / 2, paint);
            }
        });

        keyWords.put("舟", boatVa);
        actions.put("舟", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(boatMap, -width / 2 - boatMap.getWidth() + value, height / 2 - boatMap.getHeight(), paint);
            }
        });

        keyWords.put("小艇", boatVa);
        actions.put("小艇", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(boatMap, -width / 2 - boatMap.getWidth() + value, height / 2 - boatMap.getHeight(), paint);
            }
        });

        keyWords.put("帆", boatVa);
        actions.put("帆", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(boatMap, -width / 2 - boatMap.getWidth() + value, height / 2 - boatMap.getHeight(), paint);
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

    public Set<String> keySet() {
        return keyWords.keySet();
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
