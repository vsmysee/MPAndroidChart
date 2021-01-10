package com.codingbaby;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public interface AnimatorAction {


    void draw(Canvas canvas, Paint paint, int height, int width, Bitmap bitmap,int value);


}
