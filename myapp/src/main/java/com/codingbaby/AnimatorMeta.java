package com.codingbaby;

import android.animation.ValueAnimator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimatorMeta {


    class AnimatorData {

        private ValueAnimator valueAnimator;

        private AnimatorAction animatorAction;

        public ValueAnimator getValueAnimator() {
            return valueAnimator;
        }

        public void setValueAnimator(ValueAnimator valueAnimator) {
            this.valueAnimator = valueAnimator;
        }

        public AnimatorAction getAnimatorAction() {
            return animatorAction;
        }

        public void setAnimatorAction(AnimatorAction animatorAction) {
            this.animatorAction = animatorAction;
        }
    }

    private  Map<String, AnimatorData> keyWords = new HashMap<>();

    private  AnimatorData buildAnimator(int number, int duration,AnimatorAction action) {
        ValueAnimator animator = ValueAnimator.ofInt(0, number);
        animator.setDuration(duration);

        AnimatorData data = new AnimatorData();
        data.valueAnimator = animator;

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

            }
        });

        data.animatorAction = action;
        return data;
    }


    public  void stop() {
        for (AnimatorData value : keyWords.values()) {
            value.valueAnimator.end();
        }
    }

    public AnimatorMeta() {
    }

    public  List<AnimatorData> checkIf(String poem) {

        List<AnimatorData> valueAnimators = new ArrayList<>();

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
