package com.codingbaby;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


public class CustomTextView extends View {

    // 画笔
    private Paint paint;


    private GestureDetector detector;

    private String[] texts = {"白日依山尽", "黄河入海流", "欲穷千里目", "更上一层楼"};


    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        detector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public void onLongPress(MotionEvent e) {
                texts[0] = "床前明月光";
                invalidate();
            }
        });
    }

    private void init() {
        // 画笔
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.GRAY);
        paint.setTextSize(sp2px(40));
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        init();

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.rgb(205, 205, 205));
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);

        canvas.translate(getWidth() / 2, getHeight() / 2);


        drawCenterMultiText3(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
    }

    private void drawCenterMultiText3(Canvas canvas) {

        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        // 行数
        int textLines = texts.length;


        // 文本高度
        float textHeight = fontMetrics.bottom - fontMetrics.top;


        // 中间文本的baseline
        float ascent = paint.ascent();
        float descent = paint.descent();

        float abs = Math.abs(ascent + descent);
        float centerBaselineY = abs / 2;


        paint.setColor(Color.BLACK);


        float first = 0;
        float end = 0;
        float width = 0;

        for (int i = 0; i < textLines; i++) {
            float baseY = centerBaselineY + (i - (textLines - 1) / 2.0f) * textHeight;

            if (i == 0) {
                first = baseY;
            }

            float textWidth = paint.measureText(texts[i]);

            if (i == texts.length - 1) {
                end = baseY;
                width = textWidth;
            }


            canvas.drawText(texts[i], -textWidth / 2, baseY, paint);
        }


        paint.setColor(Color.BLUE);

        paint.setTextSize(sp2px(20));

        float textWidth = paint.measureText("登鹳雀楼");

        canvas.drawText("登鹳雀楼", -textWidth / 2, first - textHeight, paint);

        paint.setColor(Color.RED);

        canvas.drawText("宋【王之涣】", width / 4, end + textHeight, paint);


    }

    /**
     * dp转px
     *
     * @param dp dp值
     * @return px值
     */
    public int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getContext().getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     *
     * @param sp sp值
     * @return px值
     */
    public int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                getContext().getResources().getDisplayMetrics());
    }
}
