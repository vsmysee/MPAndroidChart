package com.codingbaby;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.TypedValue;
import android.view.View;

public class ButtonStatus {

    private Context context;

    private View view;





    private boolean longPress = false;



    public ButtonStatus(Context context, View view) {
        this.context = context;
        this.view = view;
    }





    public void onLongClick() {
        longPress = !longPress;
    }


    public void drawButton(Canvas canvas, Paint paint) {

        if (!longPress) {
            return;
        }


        int gap = sp2px(40);
        int textSize = sp2px(15);

        paint.setTextSize(textSize);


        int wordY = sp2px(35);



        int radius = sp2px(15);
        int fromX = sp2px(30);
        int fromY = sp2px(30);



        int wordFrom = sp2px(22);

        int n = 0;

        paint.setColor(Color.BLUE);
        canvas.drawCircle(fromX + n * gap, fromY, radius, paint);
        paint.setColor(Color.WHITE);
        canvas.drawText("藏", wordFrom, wordY, paint);





    }


    /**
     * 检查是否点击了按钮
     *
     * @param y
     * @param x
     * @return
     */
    public boolean checkFuncTouch(float y, float x) {

        int gap = sp2px(40);
        int textSize = sp2px(15);

        if (!longPress) {
            return false;
        }

        int topButtonX = textSize;
        if (y >= topButtonX && y <= 3 * topButtonX && x >= topButtonX && x <= 3 * topButtonX) {



            return true;
        }




        return false;
    }



    /**
     * sp转px
     *
     * @param sp sp值
     * @return px值
     */
    public int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                context.getResources().getDisplayMetrics());
    }


}
