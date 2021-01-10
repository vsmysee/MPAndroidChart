package com.codingbaby;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;

public interface AnimatorAction {


    void draw(ValueAnimator va,Canvas canvas, Paint paint, int height, int width, int value);


}
