package com.codingbaby;

import android.animation.ValueAnimator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimatorMeta {


    private static Map<String, ValueAnimator> keyWords = new HashMap<>();

    private static ValueAnimator buildAnimator(int number, int duration) {
        ValueAnimator animator = ValueAnimator.ofInt(0, number);
        animator.setDuration(duration);
        return animator;
    }

    public static ValueAnimator get(String key) {
        return keyWords.get(key);
    }

    public static void stop() {
        for (ValueAnimator value : keyWords.values()) {
            value.end();
        }
    }

    static {
        keyWords.put("月", buildAnimator(200, 3 * 1000));
        keyWords.put("星", buildAnimator(300, 4 * 1000));
        keyWords.put("雪", buildAnimator(400, 4 * 1000));
        keyWords.put("日出", buildAnimator(200, 3 * 1000));
        keyWords.put("白日", buildAnimator(200, 3 * 1000));
        keyWords.put("花", buildAnimator(200, 3 * 1000));
        keyWords.put("船", buildAnimator(1000, 6 * 1000));
        keyWords.put("舟", buildAnimator(1000, 6 * 1000));
        keyWords.put("雨", buildAnimator(300, 5 * 1000));
        keyWords.put("草", buildAnimator(200, 3 * 1000));
        keyWords.put("云", buildAnimator(200, 3 * 1000));
        keyWords.put("鸟", buildAnimator(200, 3 * 1000));
    }

    public static List<ValueAnimator> checkIf(String poem) {

        List<ValueAnimator> valueAnimators = new ArrayList<>();

        String[] split = poem.split(";");

        for (int i = 3; i < split.length; i++) {
            for (String keyWord : keyWords.keySet()) {
                if (split[i].contains(keyWord)) {
                    valueAnimators.add(keyWords.get(keyWord));
                }
            }
        }

        return valueAnimators;

    }


}
