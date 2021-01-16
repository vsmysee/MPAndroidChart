package com.codingbaby;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AnimatorMeta {

    private AnimatorStatus animatorStatus = new AnimatorStatus();

    private CustomTextView view;

    private Map<String, ValueAnimator> keyWords = new HashMap<>();
    private Map<String, AnimatorAction> actions = new HashMap<>();

    public List<ValuePair> current = new ArrayList<>();

    private static Set<String> stopKey = new HashSet<>();

    static {
        stopKey.add("云");
        stopKey.add("山");
        stopKey.add("酒");
        stopKey.add("莲");
        stopKey.add("荷");
        stopKey.add("日暮");
    }

    public boolean isStop(String k) {
        return stopKey.contains(k);
    }

    public void stop() {
        animatorStatus.close();
        for (ValueAnimator value : keyWords.values()) {
            value.cancel();
        }
    }


    private ValueAnimator buildRepeatAnimator(int number, int duration) {

        ValueAnimator animator = ValueAnimator.ofInt(0, number);
        animator.setDuration(duration);
        animator.setRepeatCount(1);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.invalidate();
            }
        });
        //animator.setStartDelay(1000);
        return animator;
    }

    private ValueAnimator buildAnimator(int number, int duration) {

        final ValueAnimator animator = ValueAnimator.ofInt(0, number);
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.invalidate();
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        //animator.setStartDelay(1000);
        return animator;
    }

    public AnimatorMeta(final CustomTextView view, final BitMapHolder bitMapHolder) {

        this.view = view;


        keyWords.put("月", buildRepeatAnimator(180, 6 * 1000));
        actions.put("月", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(bitMapHolder.moonMap, width / 2 - value, -height / 2, paint);
            }
        });

        keyWords.put("竹", buildRepeatAnimator(800, 10 * 1000));
        actions.put("竹", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(bitMapHolder.zhuMap, -width / 2 - bitMapHolder.zhuMap.getWidth() + value, height / 2 - bitMapHolder.zhuMap.getHeight(), paint);
            }
        });

        keyWords.put("梅", buildRepeatAnimator(700, 10 * 1000));
        actions.put("梅", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(bitMapHolder.meiMap, -width / 2 - bitMapHolder.meiMap.getWidth() + value, height / 2 - bitMapHolder.meiMap.getHeight(), paint);
            }
        });


        ValueAnimator sunVa = buildRepeatAnimator(150, 6 * 1000);

        keyWords.put("日出", sunVa);
        actions.put("日出", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(bitMapHolder.sunMap, -width / 2 - bitMapHolder.sunMap.getWidth() + value, -height / 2, paint);
            }
        });


        keyWords.put("白日", sunVa);
        actions.put("白日", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(bitMapHolder.sunMap, -width / 2 - bitMapHolder.sunMap.getWidth() + value, -height / 2, paint);
            }
        });


        keyWords.put("雨", buildAnimator(600, 10 * 1000));
        actions.put("雨", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(bitMapHolder.rainMap, -width / 2, -height / 2 - bitMapHolder.rainMap.getHeight() + value, paint);
            }
        });


        ValueAnimator xiyangVa = buildRepeatAnimator(600, 10 * 1000);

        keyWords.put("夕阳", xiyangVa);
        actions.put("夕阳", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(bitMapHolder.xiyangMap, -width / 2, -height / 2 - bitMapHolder.xiyangMap.getHeight() + value, paint);
            }
        });

        keyWords.put("落日", xiyangVa);
        actions.put("落日", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(bitMapHolder.xiyangMap, -width / 2, -height / 2 - bitMapHolder.xiyangMap.getHeight() + value, paint);
            }
        });


        keyWords.put("黄昏", xiyangVa);
        actions.put("黄昏", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(bitMapHolder.xiyangMap, -width / 2, -height / 2 - bitMapHolder.xiyangMap.getHeight() + value, paint);
            }
        });

        keyWords.put("日暮", xiyangVa);
        actions.put("日暮", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(bitMapHolder.xiyangMap, -width / 2, -height / 2 - bitMapHolder.xiyangMap.getHeight() + value, paint);
            }
        });

        keyWords.put("残阳", xiyangVa);
        actions.put("残阳", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(bitMapHolder.xiyangMap, -width / 2, -height / 2 - bitMapHolder.xiyangMap.getHeight() + value, paint);
            }
        });


        ValueAnimator snowVa = buildAnimator(2500, 20 * 1000);

        keyWords.put("雪", snowVa);
        actions.put("雪", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(bitMapHolder.snowMap, -width / 2, -height / 2 - bitMapHolder.snowMap.getHeight() + value, paint);
            }
        });

        keyWords.put("霜", snowVa);
        actions.put("霜", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(bitMapHolder.snowMap, -width / 2, -height / 2 - bitMapHolder.snowMap.getHeight() + value, paint);
            }
        });

        keyWords.put("冬", snowVa);
        actions.put("冬", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(bitMapHolder.snowMap, -width / 2, -height / 2 - bitMapHolder.snowMap.getHeight() + value, paint);
            }
        });


        ValueAnimator autumnVa = buildRepeatAnimator(500, 10 * 1000);

        keyWords.put("秋", autumnVa);
        actions.put("秋", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(bitMapHolder.autumnMap, -width / 2 - 50, height / 2 - value, paint);
            }
        });

        keyWords.put("黄叶", autumnVa);
        actions.put("黄叶", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(bitMapHolder.autumnMap, -width / 2 - 50, height / 2 - value, paint);
            }
        });

        keyWords.put("叶红", autumnVa);
        actions.put("叶红", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(bitMapHolder.autumnMap, -width / 2 - 50, height / 2 - value, paint);
            }
        });


        keyWords.put("春", buildRepeatAnimator(300, 6 * 1000));
        actions.put("春", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(bitMapHolder.springMap, -width / 2, height / 2 - value, paint);
            }
        });


        ValueAnimator peachVa = buildAnimator(600, 15 * 1000);
        keyWords.put("桃", peachVa);
        actions.put("桃", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(bitMapHolder.peachMap, width / 2 - value, height / 2 - bitMapHolder.peachMap.getHeight(), paint);
            }
        });

        ValueAnimator fishVa = buildAnimator(700, 15 * 1000);
        keyWords.put("鱼", fishVa);
        actions.put("鱼", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(bitMapHolder.fishMap, width / 2 - value, -100 + bitMapHolder.fishMap.getHeight() + value, paint);
            }
        });
        keyWords.put("渔", fishVa);
        actions.put("渔", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(bitMapHolder.fishMap, width / 2 - value, -100 + bitMapHolder.fishMap.getHeight() + value, paint);
            }
        });
        keyWords.put("钓", fishVa);
        actions.put("钓", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(bitMapHolder.fishMap, width / 2 - value, -100 + bitMapHolder.fishMap.getHeight() + value, paint);
            }
        });


        keyWords.put("云", buildAnimator(500, 10 * 1000));
        actions.put("云", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(bitMapHolder.cloudMap, -width / 2, -height / 2 - bitMapHolder.cloudMap.getHeight() + value, paint);
            }
        });


        ValueAnimator grassVa = buildRepeatAnimator(350, 8 * 1000);

        keyWords.put("草", grassVa);
        actions.put("草", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(bitMapHolder.grassMap, -width / 2, height / 2 + bitMapHolder.grassMap.getHeight() - value, paint);
            }
        });

        keyWords.put("花", grassVa);
        actions.put("花", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(bitMapHolder.grassMap, -width / 2, height / 2 + bitMapHolder.grassMap.getHeight() - value, paint);
            }
        });


        keyWords.put("菊", buildRepeatAnimator(350, 8 * 1000));
        actions.put("菊", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(bitMapHolder.juMap, -width / 2, height / 2 - value, paint);
            }
        });


        ValueAnimator lianVa = buildAnimator(350, 8 * 1000);

        keyWords.put("莲", lianVa);
        actions.put("莲", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(bitMapHolder.lianMap, 0, height / 2 - value, paint);
            }
        });

        keyWords.put("荷", lianVa);
        actions.put("荷", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(bitMapHolder.lianMap, 0, height / 2 - value, paint);
            }
        });


        keyWords.put("蛙", buildRepeatAnimator(100, 5 * 1000));
        actions.put("蛙", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(bitMapHolder.frogMap, -width / 2 - bitMapHolder.frogMap.getWidth() + value, height / 2 - bitMapHolder.frogMap.getHeight() * 2, paint);
            }
        });

        keyWords.put("酒", buildAnimator(200, 5 * 1000));
        actions.put("酒", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(bitMapHolder.wireMap, -width / 2 - bitMapHolder.wireMap.getWidth() + value, height / 2 - bitMapHolder.wireMap.getHeight() - 50, paint);
            }
        });

        keyWords.put("山", buildAnimator(1000, 15 * 1000));
        actions.put("山", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(bitMapHolder.mountainMap, -width / 2, height / 2 + bitMapHolder.mountainMap.getHeight() - value, paint);
            }
        });

        ValueAnimator duckVa = buildRepeatAnimator(1300, 20 * 1000);

        keyWords.put("鸭", duckVa);
        actions.put("鸭", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(bitMapHolder.duckMap, width / 2 + bitMapHolder.duckMap.getWidth() - value, height / 2 - bitMapHolder.duckMap.getHeight(), paint);
            }
        });


        keyWords.put("鹅", duckVa);
        actions.put("鹅", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(bitMapHolder.duckMap, width / 2 + bitMapHolder.duckMap.getWidth() - value, height / 2 - bitMapHolder.duckMap.getHeight(), paint);
            }
        });


        ValueAnimator boatVa = buildRepeatAnimator(1300, 20 * 1000);

        keyWords.put("船", boatVa);
        actions.put("船", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(bitMapHolder.boatMap, -width / 2 - bitMapHolder.boatMap.getWidth() + value, height / 2 - bitMapHolder.boatMap.getHeight(), paint);
            }
        });

        keyWords.put("舟", boatVa);
        actions.put("舟", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(bitMapHolder.boatMap, -width / 2 - bitMapHolder.boatMap.getWidth() + value, height / 2 - bitMapHolder.boatMap.getHeight(), paint);
            }
        });

        keyWords.put("小艇", boatVa);
        actions.put("小艇", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(bitMapHolder.boatMap, -width / 2 - bitMapHolder.boatMap.getWidth() + value, height / 2 - bitMapHolder.boatMap.getHeight(), paint);
            }
        });

        keyWords.put("帆", boatVa);
        actions.put("帆", new AnimatorAction() {
            @Override
            public void draw(ValueAnimator va, Canvas canvas, Paint paint, int height, int width, int value) {
                canvas.drawBitmap(bitMapHolder.boatMap, -width / 2 - bitMapHolder.boatMap.getWidth() + value, height / 2 - bitMapHolder.boatMap.getHeight(), paint);
            }
        });


    }


    public AnimatorAction action(String key) {
        return actions.get(key);
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
