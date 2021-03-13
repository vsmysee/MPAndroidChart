package com.codingbaby;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class CustomTextView extends View {


    private static int DEFAULT_COLOR = Color.BLACK;


    private BitMapHolder bitMapHolder;

    private AnimatorMeta animatorMeta;

    // 画笔
    private Paint paint = new Paint();

    //虚线笔
    Paint virtualLine = new Paint();
    Paint virtualLineBlue = new Paint();

    {
        virtualLine.setStyle(Paint.Style.STROKE);
        virtualLine.setAntiAlias(true);
        virtualLine.setStrokeWidth(3);
        virtualLine.setColor(Color.RED);
        virtualLine.setPathEffect(new DashPathEffect(new float[]{4, 4}, 0));
    }

    {
        virtualLineBlue.setStyle(Paint.Style.STROKE);
        virtualLineBlue.setAntiAlias(true);
        virtualLineBlue.setStrokeWidth(3);
        virtualLineBlue.setColor(Color.GRAY);
        virtualLineBlue.setPathEffect(new DashPathEffect(new float[]{4, 4}, 0));
    }


    //汉字动画
    private ValueAnimator wordAnimator;


    private List<LinePoint> drawLinePoint = new ArrayList<>();


    private String poem;
    private static List<String> cache = new ArrayList<>();
    private static int cursor = 0;

    private Character chineseWord;
    private Character nextChineseWord;

    private List<String> showIdioms;

    private static String time;
    private static String author;
    private static String title;

    private List<LinePoint> linePoints = new ArrayList<>();


    public static DataHolder dataHolder;


    private ButtonStatus buttonStatus;


    private float poemWordHeight;
    private List<String> poemRows = new ArrayList<>();


    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        bitMapHolder = new BitMapHolder(getResources());

        animatorMeta = new AnimatorMeta(this, bitMapHolder);

        buttonStatus = new ButtonStatus(context, this);


        poem = dataHolder.randomPoem(buttonStatus.selectPoemForStudent, buttonStatus.selectPoemForPrimary, buttonStatus.selectPoemForAll);
        buildRows();


        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                buttonStatus.onLongClick();

                animatorMeta.stop();

                invalidate();
                return true;
            }
        });

    }


    private void initPint() {

        paint.setAntiAlias(true);
        paint.setColor(DEFAULT_COLOR);
        paint.setTextSize(sp2px(30));

    }


    @Override
    public void draw(Canvas canvas) {

        if (wordAnimator == null) {

            wordAnimator = ValueAnimator.ofInt(0, getWidth());
            wordAnimator.setDuration(600);
            wordAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    invalidate();
                }
            });
            wordAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    chineseWord = nextChineseWord;
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }

        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();


        //draw background
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);


        //draw button
        buttonStatus.drawButton(canvas, paint);

        initPint();

        buttonStatus.drawBottomButton(canvas, paint, getHeight());

        canvas.translate(getWidth() / 2, getHeight() / 2);

        if (buttonStatus.selectPoem) {

            //渲染动画
            if (animatorMeta.isOpen()) {
                for (AnimatorMeta.ValuePair v : animatorMeta.current) {
                    ValueAnimator valueAnimator = v.getValueAnimator();
                    if (valueAnimator.isRunning()) {
                        int value = (int) valueAnimator.getAnimatedValue();
                        animatorMeta.action(v.getKey()).draw(valueAnimator, canvas, paint, getHeight(), getWidth(), value);
                    }
                }
            }


            if (drawLinePoint.size() > 0) {
                for (LinePoint linePoint : drawLinePoint) {
                    canvas.drawLine(linePoint.sx, linePoint.sy, linePoint.ex, linePoint.ey, virtualLine);
                }
                drawPoem(canvas, true);
            } else {
                drawPoem(canvas, animatorMeta.isOpen());
            }

        }

        if (buttonStatus.selectWord) {
            drawWord(canvas);
        }

        if (buttonStatus.selectIdiom) {
            drawIdiom(canvas);
        }

        super.draw(canvas);

    }


    //触摸屏幕
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            float y = event.getY();
            float x = event.getX();


            if (buttonStatus.selectPoem) {

                animatorMeta.stop();

                int centerX = getWidth() / 2;
                int centerY = getHeight() / 2;
                float clickX = x - centerX;

                boolean touchPoemItem = false;

                for (LinePoint linePoint : linePoints) {
                    if (clickX > linePoint.sx && clickX < linePoint.ex) {
                        if (linePoint.ey + centerY - poemWordHeight < y && y < linePoint.ey + centerY) {
                            if (drawLinePoint.contains(linePoint)) {
                                drawLinePoint.remove(linePoint);
                            } else {
                                drawLinePoint.add(linePoint);
                            }
                            touchPoemItem = true;
                            break;
                        }
                    }
                }

                if (touchPoemItem) {
                    invalidate();
                    return super.onTouchEvent(event);
                }


                drawLinePoint.clear();

                if (cursor == 0) {
                    poem = dataHolder.randomPoem(buttonStatus.selectPoemForStudent, buttonStatus.selectPoemForPrimary, buttonStatus.selectPoemForAll);
                    buildRows();

                    animatorMeta.start(poem);
                }
            }


            if (buttonStatus.checkFuncTouch(y, x)) {
                buttonStatus.startAnimation();
            }

            if (buttonStatus.checkBottomTouch(y, x, getHeight())) {
                buttonStatus.startAnimation();
            }


            if (buttonStatus.selectWord) {

                nextChineseWord = dataHolder.randChineseWord(buttonStatus.selectWordForStudent, buttonStatus.selectWordForPrimary, buttonStatus.selectWordForSenior);

                if (!wordAnimator.isStarted()) {
                    wordAnimator.start();
                }
            }

            if (buttonStatus.selectIdiom) {
                showIdioms = dataHolder.randIdiom(buttonStatus.selectIdiomStudent);
            }


            invalidate();

        }

        return super.onTouchEvent(event);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {

            case KeyEvent.KEYCODE_VOLUME_UP:

                animatorMeta.stop();

                if (buttonStatus.selectPoem) {

                    cache.clear();
                    cursor = 0;

                    drawLinePoint.clear();

                    try {
                        String pop = dataHolder.popPoem();
                        if (pop.equals(poem)) {
                            poem = dataHolder.popPoem();
                        } else {
                            poem = pop;
                        }
                    } catch (Exception e) {
                        poem = dataHolder.randomPoem(buttonStatus.selectPoemForStudent, buttonStatus.selectPoemForPrimary, buttonStatus.selectPoemForAll);
                    }


                    buildRows();
                    animatorMeta.start(poem);

                    invalidate();

                }

                if (buttonStatus.selectWord) {

                    try {
                        nextChineseWord = dataHolder.popChinese();
                        if (nextChineseWord.equals(chineseWord)) {
                            nextChineseWord = dataHolder.popChinese();
                        }
                    } catch (Exception e) {
                        nextChineseWord = dataHolder.randChineseWord(buttonStatus.selectWordForStudent, buttonStatus.selectWordForPrimary, buttonStatus.selectWordForSenior);
                    }

                    if (!wordAnimator.isStarted()) {
                        wordAnimator.start();
                    }

                    invalidate();

                }



                return true;


            case KeyEvent.KEYCODE_VOLUME_DOWN:

                animatorMeta.stop();
                drawLinePoint.clear();

                if (buttonStatus.selectPoem && cursor == 0) {

                    poem = dataHolder.randomPoem(buttonStatus.selectPoemForStudent, buttonStatus.selectPoemForPrimary, buttonStatus.selectPoemForAll);

                    buildRows();

                    animatorMeta.start(poem);

                }

                if (buttonStatus.selectWord) {
                    if (!wordAnimator.isStarted()) {
                        wordAnimator.start();
                    }
                }


                if (buttonStatus.selectIdiom) {
                    showIdioms = dataHolder.randIdiom(buttonStatus.selectIdiomStudent);
                }

                invalidate();
                return true;

        }

        return super.onKeyDown(keyCode, event);
    }

    private void buildRows() {
        String[] textsSplit = poem.split(";");

        time = textsSplit[0];
        author = textsSplit[1];
        title = textsSplit[2];

        poemRows.clear();

        for (int i = 3; i < textsSplit.length; i++) {
            poemRows.add(textsSplit[i]);
        }
    }




    private void drawIdiom(Canvas canvas) {

        if (showIdioms == null) {
            showIdioms = dataHolder.randIdiom(buttonStatus.selectIdiomStudent);
        }

        paint.setColor(DEFAULT_COLOR);

        paint.setTextSize(sp2px(35));

        Paint.FontMetrics fontMetrics = paint.getFontMetrics();

        // 文本高度
        float textHeight = fontMetrics.bottom - fontMetrics.top;

        List<String> rows = new ArrayList<>();

        for (String item : showIdioms) {
            rows.add(item);
        }

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
            canvas.drawLine(-textWidth / 2, baseY + descent, textWidth / 2, baseY + descent, paint);
        }


    }


    private void drawWord(Canvas canvas) {

        if (chineseWord == null) {
            chineseWord = dataHolder.randChineseWord(buttonStatus.selectWordForStudent, buttonStatus.selectWordForPrimary, buttonStatus.selectWordForSenior);
        }

        paint.setColor(DEFAULT_COLOR);
        paint.setTextSize(sp2px(80));

        Paint.FontMetrics fontMetrics = paint.getFontMetrics();

        // 文本高度
        float textHeight = fontMetrics.bottom - fontMetrics.top;

        // 中间文本的baseline
        float ascent = paint.ascent();
        float descent = paint.descent();

        float abs = Math.abs(ascent + descent);
        float centerBaselineY = abs / 2;


        if (!wordAnimator.isStarted()) {
            drawWord(canvas, textHeight, descent, centerBaselineY, chineseWord, 0);
        } else {
            int offset = (int) wordAnimator.getAnimatedValue();
            drawWord(canvas, textHeight, descent, centerBaselineY, chineseWord, -offset);
        }

    }


    private void drawWord(Canvas canvas, float textHeight, float descent, float centerBaselineY, Character wordParam, int offset) {
        List<String> rows = new ArrayList<>();

        rows.add(dataHolder.wordPinMap.get(wordParam));
        String w = String.valueOf(wordParam);
        rows.add(w);
        if (!w.equals(dataHolder.oldWord.get(w))) {
            rows.add(dataHolder.oldWord.get(w));
        }


        int textLines = rows.size();


        float max = 0;
        for (int i = 0; i < textLines; i++) {

            float baseY = centerBaselineY + (i - (textLines - 1) / 2.0f) * textHeight;

            String content = rows.get(i);

            float textWidth = paint.measureText(content);
            if (textWidth > max) {
                max = textWidth;
            }

            if (i == 0) {
                paint.setColor(Color.BLUE);
            } else {
                paint.setColor(DEFAULT_COLOR);
            }

            canvas.drawText(content, -textWidth / 2 + offset, baseY, paint);
            canvas.drawLine(-max + offset, baseY + descent, max + offset, baseY + descent, paint);
        }
    }


    private void drawPoem(final Canvas canvas, boolean reDraw) {

        initPint();

        Paint.FontMetrics fontMetrics = paint.getFontMetrics();

        // 文本高度
        float textHeight = poemWordHeight = fontMetrics.bottom - fontMetrics.top;
        // 中间文本的baseline
        float ascent = paint.ascent();
        float descent = paint.descent();

        float abs = Math.abs(ascent + descent);
        float centerBaselineY = abs / 2;

        int showRow = (int) ((getHeight() / textHeight) - 5);


        linePoints.clear();


        if (cache.size() == 0) {

            cursor = 0;

            if (poemRows.size() * textHeight > getHeight() - dp2px(200)) {

                for (String row : poemRows) {
                    cache.add(row);
                }

                poemRows.clear();

                int end = cursor + showRow;

                for (int i = cursor; i < end; i++) {
                    poemRows.add(cache.get(i));
                    cursor++;
                }

            }
        }


        if (cache.size() > 0 && cursor > 0 && !reDraw) {

            poemRows.clear();

            int end = cursor + showRow;

            for (int i = cursor; i < end; i++) {
                try {
                    poemRows.add(cache.get(i));
                    cursor++;
                } catch (Exception e) {
                    break;
                }
            }

            //补齐
            if (poemRows.size() != showRow) {
                List<String> padding = new ArrayList<>();
                int missCount = showRow - poemRows.size();
                int from = cache.size() - poemRows.size() - missCount;
                for (int j = 0; j < missCount; j++) {
                    padding.add("w" + cache.get(from + j));
                }
                padding.addAll(poemRows);
                poemRows = padding;

                cache.clear();
                cursor = 0;
            }
        }


        int textLines = poemRows.size();

        float firstY = 0;
        float endY = 0;
        float maxWidth = 0;

        for (int i = 0; i < textLines; i++) {

            float baseY = centerBaselineY + (i - (textLines - 1) / 2.0f) * textHeight;

            if (i == 0 && firstY == 0) {
                firstY = baseY;
            }

            String content = poemRows.get(i);

            if (content.startsWith("w")) {
                content = content.replace("w", "");
                paint.setColor(Color.GRAY);
            } else {
                paint.setColor(DEFAULT_COLOR);
            }


            float textWidth = paint.measureText(content);
            if (textWidth > maxWidth) {
                maxWidth = textWidth;
            }


            if (i == poemRows.size() - 1 && endY == 0) {
                endY = baseY;
            }

            canvas.drawText(content, -textWidth / 2, baseY, paint);

            //触摸线
            if (!linePoints.contains(new LinePoint(-textWidth / 2, baseY + descent, textWidth / 2, baseY + descent))) {
                linePoints.add(new LinePoint(-textWidth / 2, baseY + descent, textWidth / 2, baseY + descent));
            }


            //分割线
            if (animatorMeta.canDrawUnderLine(poem)) {
                int cut = 4;
                if (cache.size() == 0 && poemRows.size() > cut && poemRows.size() % cut == 0 && i > 0 && ((i + 1) % cut == 0) && i + 1 != poemRows.size()) {
                    canvas.drawLine(-getWidth() / 2, baseY + descent + 3, getWidth() / 2, baseY + descent + 2, virtualLineBlue);
                }
                cut = 5;
                if (cache.size() == 0 && poemRows.size() > cut && poemRows.size() % cut == 0 && i > 0 && ((i + 1) % cut == 0) && i + 1 != poemRows.size()) {
                    canvas.drawLine(-getWidth() / 2, baseY + descent + 3, getWidth() / 2, baseY + descent + 2, virtualLineBlue);
                }

            }

        }


        paint.setTextSize(sp2px(20));

        //draw title
        paint.setColor(Color.BLUE);
        float textWidth = paint.measureText(title);
        canvas.drawText(title, -textWidth / 2, firstY - textHeight, paint);


        //draw author
        paint.setColor(Color.RED);
        String endText = time + "  " + author;
        float timeWidth = paint.measureText(time);
        float endTextWidth = paint.measureText(endText);

        canvas.drawRoundRect(maxWidth / 2 - endTextWidth - 5, endY + 2 * descent + 10, maxWidth / 2 - endTextWidth + timeWidth + 5, endY + textHeight + descent, 10, 10, paint);
        paint.setColor(Color.WHITE);
        canvas.drawText(time, maxWidth / 2 - endTextWidth, endY + textHeight, paint);
        paint.setColor(Color.GRAY);
        canvas.drawText(author, maxWidth / 2 - endTextWidth + timeWidth + 20, endY + textHeight, paint);
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

    static class LinePoint {
        private float sx;
        private float sy;
        private float ex;
        private float ey;

        public LinePoint(float sx, float sy, float ex, float ey) {
            this.sx = sx;
            this.sy = sy;
            this.ex = ex;
            this.ey = ey;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LinePoint linePoint = (LinePoint) o;
            return Float.compare(linePoint.sx, sx) == 0 &&
                    Float.compare(linePoint.sy, sy) == 0 &&
                    Float.compare(linePoint.ex, ex) == 0 &&
                    Float.compare(linePoint.ey, ey) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(sx, sy, ex, ey);
        }
    }
}
