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

    private View view;

    private ValueAnimator buildRepeatAnimator(int number, int duration) {

        ValueAnimator animator = ValueAnimator.ofInt(0, number);
        animator.setDuration(duration);
        animator.setRepeatCount(1);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (animatorStatus.isOpen() && valueAnimator.isRunning()) {
                    view.invalidate();
                }

            }
        });
        animator.setStartDelay(1000);
        return animator;
    }

    private ValueAnimator buildAnimator(int number, int duration) {

        ValueAnimator animator = ValueAnimator.ofInt(0, number);
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (animatorStatus.isOpen() && valueAnimator.isRunning()) {
                    view.invalidate();
                }

            }
        });
        animator.setStartDelay(1000);
        return animator;
    }

    public AnimatorMeta(final Bitmap moonMap, final Bitmap rainMap, final Bitmap grassMap,
                        final Bitmap boatMap, final Bitmap sunMap, final Bitmap snowMap, final Bitmap autumnMap,
                        final Bitmap springMap, final Bitmap peachMap, final Bitmap cloudMap, final Bitmap xiyangMap,
                        final Bitmap lianMap, final Bitmap meiMap, final Bitmap zhuMap, final Bitmap duckMap,
                        final Bitmap frogMap, final Bitmap mountainMap, final Bitmap wireMap, final View view) {

        this.view = view;


        keyWords.put("月", buildRepeatAnimator(180, 6 * 1000));
        actions.put("月", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(moonMap, width / 2 - value, -height / 2, paint);
            }
        });

        keyWords.put("竹", buildRepeatAnimator(800, 10 * 1000));
        actions.put("竹", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(zhuMap, -width / 2 - zhuMap.getWidth() + value, height / 2 - zhuMap.getHeight(), paint);
            }
        });

        keyWords.put("梅", buildRepeatAnimator(700, 10 * 1000));
        actions.put("梅", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(meiMap, -width / 2 - meiMap.getWidth() + value, height / 2 - meiMap.getHeight(), paint);
            }
        });


        ValueAnimator sunVa = buildRepeatAnimator(150, 6 * 1000);

        keyWords.put("日出", sunVa);
        actions.put("日出", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(sunMap, -width / 2 - sunMap.getWidth() + value, -height / 2, paint);
            }
        });


        keyWords.put("白日", sunVa);
        actions.put("白日", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(sunMap, -width / 2 - sunMap.getWidth() + value, -height / 2, paint);
            }
        });


        ValueAnimator rainVa = buildAnimator(600, 10 * 1000);

        keyWords.put("雨", rainVa);
        actions.put("雨", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(rainMap, -width / 2, -height / 2 - rainMap.getHeight() + value, paint);
            }
        });


        ValueAnimator xiyangVa = buildRepeatAnimator(600, 10 * 1000);

        keyWords.put("夕阳", xiyangVa);
        actions.put("夕阳", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(xiyangMap, -width / 2, -height / 2 - xiyangMap.getHeight() + value, paint);
            }
        });

        keyWords.put("落日", xiyangVa);
        actions.put("落日", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(xiyangMap, -width / 2, -height / 2 - xiyangMap.getHeight() + value, paint);
            }
        });


        keyWords.put("黄昏", xiyangVa);
        actions.put("黄昏", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(xiyangMap, -width / 2, -height / 2 - xiyangMap.getHeight() + value, paint);
            }
        });

        keyWords.put("日暮", xiyangVa);
        actions.put("日暮", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(xiyangMap, -width / 2, -height / 2 - xiyangMap.getHeight() + value, paint);
            }
        });

        keyWords.put("残阳", xiyangVa);
        actions.put("残阳", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(xiyangMap, -width / 2, -height / 2 - xiyangMap.getHeight() + value, paint);
            }
        });


        ValueAnimator snowVa = buildAnimator(2500, 20 * 1000);

        keyWords.put("雪", snowVa);
        actions.put("雪", new AnimatorAction() {
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

        keyWords.put("冬", snowVa);
        actions.put("冬", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(snowMap, -width / 2, -height / 2 - snowMap.getHeight() + value, paint);
            }
        });


        ValueAnimator autumnVa = buildRepeatAnimator(500, 10 * 1000);

        keyWords.put("秋", autumnVa);
        actions.put("秋", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(autumnMap, -width / 2 - 50, height / 2 - value, paint);
            }
        });

        keyWords.put("黄叶", autumnVa);
        actions.put("黄叶", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(autumnMap, -width / 2 - 50, height / 2 - value, paint);
            }
        });

        keyWords.put("叶红", autumnVa);
        actions.put("叶红", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(autumnMap, -width / 2 - 50, height / 2 - value, paint);
            }
        });


        ValueAnimator springVa = buildRepeatAnimator(300, 6 * 1000);

        keyWords.put("春", springVa);
        actions.put("春", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(springMap, -width / 2, height / 2 - value, paint);
            }
        });


        ValueAnimator peachVa = buildAnimator(600, 15 * 1000);

        keyWords.put("桃", peachVa);
        actions.put("桃", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(peachMap, width / 2 - value, height / 2 - peachMap.getHeight(), paint);
            }
        });


        ValueAnimator cloudVa = buildRepeatAnimator(500, 10 * 1000);

        keyWords.put("云", cloudVa);
        actions.put("云", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(cloudMap, -width / 2, -height / 2 - cloudMap.getHeight() + value, paint);
            }
        });


        ValueAnimator grassVa = buildRepeatAnimator(350, 8 * 1000);

        keyWords.put("草", grassVa);
        actions.put("草", new AnimatorAction() {
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


        ValueAnimator lianVa = buildRepeatAnimator(350, 8 * 1000);

        keyWords.put("莲", lianVa);
        actions.put("莲", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(lianMap, 0, height / 2 - value, paint);
            }
        });

        keyWords.put("荷", lianVa);
        actions.put("荷", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(lianMap, 0, height / 2 - value, paint);
            }
        });


        keyWords.put("蛙", buildRepeatAnimator(100, 5 * 1000));
        actions.put("蛙", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(frogMap, -width / 2 - frogMap.getWidth() + value, height / 2 - frogMap.getHeight() * 2, paint);
            }
        });

        keyWords.put("酒", buildRepeatAnimator(400, 5 * 1000));
        actions.put("酒", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(wireMap, -width / 2 - wireMap.getWidth() + value, height / 2 - wireMap.getHeight() -100, paint);
            }
        });

        keyWords.put("山", buildRepeatAnimator(1200, 15 * 1000));
        actions.put("山", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(mountainMap, -width / 2, height / 2 + mountainMap.getHeight() - value, paint);
            }
        });

        ValueAnimator duckVa = buildRepeatAnimator(1300, 20 * 1000);

        keyWords.put("鸭", duckVa);
        actions.put("鸭", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(duckMap, width / 2 + duckMap.getWidth() - value, height / 2 - duckMap.getHeight(), paint);
            }
        });


        keyWords.put("鹅", duckVa);
        actions.put("鹅", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(duckMap, width / 2 + duckMap.getWidth() - value, height / 2 - duckMap.getHeight(), paint);
            }
        });


        ValueAnimator boatVa = buildRepeatAnimator(1300, 20 * 1000);

        keyWords.put("船", boatVa);
        actions.put("船", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(boatMap, -width / 2 - boatMap.getWidth() + value, height / 2 - boatMap.getHeight(), paint);
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


    public AnimatorAction action(String key) {
        return actions.get(key);
    }


    private Map<String, ValueAnimator> keyWords = new HashMap<>();
    private Map<String, AnimatorAction> actions = new HashMap<>();

    public List<ValuePair> current = new ArrayList<>();

    public void stop() {
        animatorStatus.close();
        for (ValueAnimator value : keyWords.values()) {
            value.cancel();
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
                    current.add(vp);
                }
            }

        }

    }


    public boolean isOpen() {
        return animatorStatus.isOpen();
    }
}
