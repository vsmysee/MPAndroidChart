package com.codingbaby;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimatorMeta {

    private AnimatorStatus animatorStatus = new AnimatorStatus();

    public AnimatorMeta(final Bitmap moonMap, final Bitmap rainMap, final Bitmap grassMap,
                        final Bitmap boatMap, final Bitmap sunMap, final Bitmap snowMap, final Bitmap autumnMap,
                        final Bitmap springMap, final Bitmap peachMap, final Bitmap cloudMap,
                        final View view) {

        String moon = "月";

        final ValueAnimator moonVa = buildAnimator(180, 6 * 1000);
        moonVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (animatorStatus.isClose()) {
                    return;
                }
                if ((int) valueAnimator.getAnimatedValue() != 180 && animatorStatus.isOpen()) {
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

        ValueAnimator sunVa = buildAnimator(180, 6 * 1000);
        sunVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (animatorStatus.isClose()) {
                    return;
                }
                if ((int) valueAnimator.getAnimatedValue() != 180 && animatorStatus.isOpen()) {
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
                canvas.drawBitmap(sunMap, -width / 2 + value, -height / 2, paint);
            }
        });

        keyWords.put("白日", sunVa);
        actions.put("白日", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(sunMap, -width / 2 + value, -height / 2, paint);
            }
        });


        String rain = "雨";

        ValueAnimator rainVa = buildAnimator(500, 10 * 1000);
        rainVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (animatorStatus.isClose()) {
                    return;
                }
                if ((int) valueAnimator.getAnimatedValue() != 500 && animatorStatus.isOpen()) {
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
                if (animatorStatus.isClose()) {
                    return;
                }
                if ((int) valueAnimator.getAnimatedValue() != 2000 && animatorStatus.isOpen()) {
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

        keyWords.put("霜", snowVa);
        actions.put("霜", new AnimatorAction() {
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
                if (animatorStatus.isClose()) {
                    return;
                }
                if ((int) valueAnimator.getAnimatedValue() != 500 && animatorStatus.isOpen()) {
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

        ValueAnimator springVa = buildAnimator(300, 6 * 1000);
        springVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (animatorStatus.isClose()) {
                    return;
                }
                if ((int) valueAnimator.getAnimatedValue() != 300 && animatorStatus.isOpen()) {
                    view.invalidate();
                }
            }
        });

        keyWords.put(spring, springVa);
        actions.put(spring, new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(springMap, -width / 2, height / 2 - value, paint);
            }
        });


        String peach = "桃";

        ValueAnimator peachVa = buildAnimator(600, 10 * 1000);
        peachVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (animatorStatus.isClose()) {
                    return;
                }
                if ((int) valueAnimator.getAnimatedValue() != 600 && animatorStatus.isOpen()) {
                    view.invalidate();
                }
            }
        });

        keyWords.put(peach, peachVa);
        actions.put(peach, new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(peachMap, width / 2 - value, height / 2 - peachMap.getHeight(), paint);
            }
        });

        String cloud = "云";

        ValueAnimator cloudVa = buildAnimator(500, 10 * 1000);
        cloudVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (animatorStatus.isClose()) {
                    return;
                }
                if ((int) valueAnimator.getAnimatedValue() != 500 && animatorStatus.isOpen()) {
                    view.invalidate();
                }
            }
        });

        keyWords.put(cloud, cloudVa);
        actions.put(cloud, new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(cloudMap, -width / 2, -height / 2 - cloudMap.getHeight() + value, paint);
            }
        });


        String grass = "草";

        ValueAnimator grassVa = buildAnimator(350, 8 * 1000);
        grassVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (animatorStatus.isClose()) {
                    return;
                }
                if ((int) valueAnimator.getAnimatedValue() != 350 && animatorStatus.isOpen()) {
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
                if (animatorStatus.isClose()) {
                    return;
                }
                if ((int) valueAnimator.getAnimatedValue() != 1300 && animatorStatus.isOpen()) {
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


    private Map<String, ValueAnimator> keyWords = new HashMap<>();
    private Map<String, AnimatorAction> actions = new HashMap<>();

    public List<ValuePair> current = new ArrayList<>();

    public void stop() {
        animatorStatus.close();
        for (ValueAnimator value : keyWords.values()) {
            value.end();
        }
    }

    public boolean hasAnimator() {
        boolean res = false;
        for (ValueAnimator value : keyWords.values()) {
            if (value.isStarted()) {
                res = true;
            }
        }
        return res;
    }

    static class ValuePair {
        private String key;
        private ValueAnimator valueAnimator;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public ValueAnimator getValueAnimator() {
            return valueAnimator;
        }

        public void setValueAnimator(ValueAnimator valueAnimator) {
            this.valueAnimator = valueAnimator;
        }
    }

    public void start(String poem) {

        animatorStatus.open();
        current.clear();

        String[] split = poem.split(";");
        StringBuffer sb = new StringBuffer();
        for (int i = 3; i < split.length; i++) {
            sb.append(split[i]);
        }

        String p = sb.toString();

        for (String key : keyWords.keySet()) {
            ValueAnimator valueAnimator = keyWords.get(key);

            if (p.contains(key)) {

                if (key.equals("月")) {
                    if (!p.contains("岁月")
                            && !p.contains("一月")
                            && !p.contains("二月")
                            && !p.contains("三月")
                            && !p.contains("四月")
                            && !p.contains("五月")
                            && !p.contains("六月")
                            && !p.contains("七月")
                            && !p.contains("八月")
                            && !p.contains("九月")
                            && !p.contains("十月")
                            && !p.contains("十一月")
                            && !p.contains("十二月")) {
                        valueAnimator.start();

                        ValuePair vp = new ValuePair();
                        vp.key = key;
                        vp.valueAnimator = valueAnimator;
                        current.add(vp);
                    }
                } else {
                    valueAnimator.start();
                    ValuePair vp = new ValuePair();
                    vp.key = key;
                    vp.valueAnimator = valueAnimator;
                    current.add(vp);                }
            }

        }

    }


    public boolean isOpen() {
        return animatorStatus.isOpen();
    }
}
