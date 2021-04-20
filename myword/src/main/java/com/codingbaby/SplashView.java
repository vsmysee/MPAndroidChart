package com.codingbaby;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class SplashView extends View {

    private Paint paint = new Paint();


    public SplashView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void initPint() {

        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setTextSize(sp2px(60));

    }


    @Override
    public void draw(Canvas canvas) {


        //draw background
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);


        initPint();

        canvas.translate(getWidth() / 2, getHeight() / 2);

        drawWord(canvas);

        super.draw(canvas);

    }


    private void drawWord(Canvas canvas) {

        Paint.FontMetrics fontMetrics = paint.getFontMetrics();

        // 文本高度
        float textHeight = fontMetrics.bottom - fontMetrics.top;

        List<String> rows = new ArrayList<>();
        rows.add("我");
        rows.add("爱");
        rows.add("汉");
        rows.add("字");

        int textLines = rows.size();

        // 中间文本的baseline
        float ascent = paint.ascent();
        float descent = paint.descent();

        float abs = Math.abs(ascent + descent);
        float centerBaselineY = abs / 2;

        for (int i = 0; i < textLines; i++) {

            float baseY = centerBaselineY + (i - (textLines - 1) / 2.0f) * textHeight;

            String content = rows.get(i);

            float textWidth = paint.measureText(content);

            canvas.drawText(content, -textWidth / 2, baseY, paint);
        }


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
