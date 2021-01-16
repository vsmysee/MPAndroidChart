package com.codingbaby;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimatorMeta {

    private AnimatorStatus animatorStatus = new AnimatorStatus();

    private CustomTextView view;

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

    public AnimatorMeta(final CustomTextView view) {

        this.view = view;


        keyWords.put("月", buildRepeatAnimator(180, 6 * 1000));
        actions.put("月", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(view.moonMap, width / 2 - value, -height / 2, paint);
            }
        });

        keyWords.put("竹", buildRepeatAnimator(800, 10 * 1000));
        actions.put("竹", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(view.zhuMap, -width / 2 - view.zhuMap.getWidth() + value, height / 2 - view.zhuMap.getHeight(), paint);
            }
        });

        keyWords.put("梅", buildRepeatAnimator(700, 10 * 1000));
        actions.put("梅", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(view.meiMap, -width / 2 - view.meiMap.getWidth() + value, height / 2 - view.meiMap.getHeight(), paint);
            }
        });


        ValueAnimator sunVa = buildRepeatAnimator(150, 6 * 1000);

        keyWords.put("日出", sunVa);
        actions.put("日出", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(view.sunMap, -width / 2 - view.sunMap.getWidth() + value, -height / 2, paint);
            }
        });


        keyWords.put("白日", sunVa);
        actions.put("白日", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(view.sunMap, -width / 2 - view.sunMap.getWidth() + value, -height / 2, paint);
            }
        });


        keyWords.put("雨", buildAnimator(600, 10 * 1000));
        actions.put("雨", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(view.rainMap, -width / 2, -height / 2 - view.rainMap.getHeight() + value, paint);
            }
        });


        ValueAnimator xiyangVa = buildRepeatAnimator(600, 10 * 1000);

        keyWords.put("夕阳", xiyangVa);
        actions.put("夕阳", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(view.xiyangMap, -width / 2, -height / 2 - view.xiyangMap.getHeight() + value, paint);
            }
        });

        keyWords.put("落日", xiyangVa);
        actions.put("落日", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(view.xiyangMap, -width / 2, -height / 2 - view.xiyangMap.getHeight() + value, paint);
            }
        });


        keyWords.put("黄昏", xiyangVa);
        actions.put("黄昏", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(view.xiyangMap, -width / 2, -height / 2 - view.xiyangMap.getHeight() + value, paint);
            }
        });

        keyWords.put("日暮", xiyangVa);
        actions.put("日暮", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(view.xiyangMap, -width / 2, -height / 2 - view.xiyangMap.getHeight() + value, paint);
            }
        });

        keyWords.put("残阳", xiyangVa);
        actions.put("残阳", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(view.xiyangMap, -width / 2, -height / 2 - view.xiyangMap.getHeight() + value, paint);
            }
        });


        ValueAnimator snowVa = buildAnimator(2500, 20 * 1000);

        keyWords.put("雪", snowVa);
        actions.put("雪", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(view.snowMap, -width / 2, -height / 2 - view.snowMap.getHeight() + value, paint);
            }
        });

        keyWords.put("霜", snowVa);
        actions.put("霜", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(view.snowMap, -width / 2, -height / 2 - view.snowMap.getHeight() + value, paint);
            }
        });

        keyWords.put("冬", snowVa);
        actions.put("冬", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(view.snowMap, -width / 2, -height / 2 - view.snowMap.getHeight() + value, paint);
            }
        });


        ValueAnimator autumnVa = buildRepeatAnimator(500, 10 * 1000);

        keyWords.put("秋", autumnVa);
        actions.put("秋", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(view.autumnMap, -width / 2 - 50, height / 2 - value, paint);
            }
        });

        keyWords.put("黄叶", autumnVa);
        actions.put("黄叶", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(view.autumnMap, -width / 2 - 50, height / 2 - value, paint);
            }
        });

        keyWords.put("叶红", autumnVa);
        actions.put("叶红", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(view.autumnMap, -width / 2 - 50, height / 2 - value, paint);
            }
        });


        keyWords.put("春", buildRepeatAnimator(300, 6 * 1000));
        actions.put("春", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(view.springMap, -width / 2, height / 2 - value, paint);
            }
        });


        ValueAnimator peachVa = buildAnimator(600, 15 * 1000);
        keyWords.put("桃", peachVa);
        actions.put("桃", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(view.peachMap, width / 2 - value, height / 2 - view.peachMap.getHeight(), paint);
            }
        });

        ValueAnimator fishVa = buildAnimator(700, 15 * 1000);
        keyWords.put("鱼", fishVa);
        actions.put("鱼", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(view.fishMap, width / 2 - value, -100 + view.fishMap.getHeight() + value, paint);
            }
        });
        keyWords.put("渔", fishVa);
        actions.put("渔", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(view.fishMap, width / 2 - value, -100 + view.fishMap.getHeight() + value, paint);
            }
        });
        keyWords.put("钓", fishVa);
        actions.put("钓", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(view.fishMap, width / 2 - value, -100 + view.fishMap.getHeight() + value, paint);
            }
        });


        keyWords.put("云", buildRepeatAnimator(500, 10 * 1000));
        actions.put("云", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(view.cloudMap, -width / 2, -height / 2 - view.cloudMap.getHeight() + value, paint);
            }
        });


        ValueAnimator grassVa = buildRepeatAnimator(350, 8 * 1000);

        keyWords.put("草", grassVa);
        actions.put("草", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(view.grassMap, -width / 2, height / 2 + view.grassMap.getHeight() - value, paint);
            }
        });

        keyWords.put("花", grassVa);
        actions.put("花", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(view.grassMap, -width / 2, height / 2 + view.grassMap.getHeight() - value, paint);
            }
        });


        ValueAnimator lianVa = buildRepeatAnimator(350, 8 * 1000);

        keyWords.put("莲", lianVa);
        actions.put("莲", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(view.lianMap, 0, height / 2 - value, paint);
            }
        });

        keyWords.put("荷", lianVa);
        actions.put("荷", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(view.lianMap, 0, height / 2 - value, paint);
            }
        });


        keyWords.put("蛙", buildRepeatAnimator(100, 5 * 1000));
        actions.put("蛙", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(view.frogMap, -width / 2 - view.frogMap.getWidth() + value, height / 2 - view.frogMap.getHeight() * 2, paint);
            }
        });

        keyWords.put("酒", buildRepeatAnimator(400, 5 * 1000));
        actions.put("酒", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(view.wireMap, -width / 2 - view.wireMap.getWidth() + value, height / 2 - view.wireMap.getHeight() - 100, paint);
            }
        });

        keyWords.put("山", buildRepeatAnimator(1200, 15 * 1000));
        actions.put("山", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(view.mountainMap, -width / 2, height / 2 + view.mountainMap.getHeight() - value, paint);
            }
        });

        ValueAnimator duckVa = buildRepeatAnimator(1300, 20 * 1000);

        keyWords.put("鸭", duckVa);
        actions.put("鸭", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(view.duckMap, width / 2 + view.duckMap.getWidth() - value, height / 2 - view.duckMap.getHeight(), paint);
            }
        });


        keyWords.put("鹅", duckVa);
        actions.put("鹅", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(view.duckMap, width / 2 + view.duckMap.getWidth() - value, height / 2 - view.duckMap.getHeight(), paint);
            }
        });


        ValueAnimator boatVa = buildRepeatAnimator(1300, 20 * 1000);

        keyWords.put("船", boatVa);
        actions.put("船", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(view.boatMap, -width / 2 - view.boatMap.getWidth() + value, height / 2 - view.boatMap.getHeight(), paint);
            }
        });

        keyWords.put("舟", boatVa);
        actions.put("舟", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(view.boatMap, -width / 2 - view.boatMap.getWidth() + value, height / 2 - view.boatMap.getHeight(), paint);
            }
        });

        keyWords.put("小艇", boatVa);
        actions.put("小艇", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(view.boatMap, -width / 2 - view.boatMap.getWidth() + value, height / 2 - view.boatMap.getHeight(), paint);
            }
        });

        keyWords.put("帆", boatVa);
        actions.put("帆", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(view.boatMap, -width / 2 - view.boatMap.getWidth() + value, height / 2 - view.boatMap.getHeight(), paint);
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
