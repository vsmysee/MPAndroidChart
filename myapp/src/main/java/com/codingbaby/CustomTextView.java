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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class CustomTextView extends View {

    // 画笔
    private Paint paint;


    private GestureDetector detector;


    private static String db = "现;毛泽东;《咏蛙》;独坐池塘如虎踞;绿荫树下养精神;春来我不先开口;哪个虫儿敢作声\n" +
            "唐;李峤;《风》;解落三秋叶;能开二月花;过江千尺浪;入竹万竿斜\n" +
            "唐;孟浩然;《春晓》;春眠不觉晓;处处闻啼鸟;夜来风雨声;花落知多少\n" +
            "宋;朱熹;《春日》;胜日寻芳泗水滨;无边光景一时新;等闲识得东风面;万紫千红总是春\n" +
            "宋;无名;《悟道诗》;尽日寻春不见春;芒鞋踏遍陇头云;归来笑拈梅花嗅;春在枝头已十分\n" +
            "唐;崔护;《题都城南庄》;去年今日此门中;人面桃花相映红;人面不知何处去;桃花依旧笑春风\n" +
            "唐;白居易;《杂曲歌辞·忆江南》;江南好;风景旧曾谙;日出江花红胜火;春来江水绿如蓝;能不忆江南\n" +
            "唐;白居易;《杂曲歌辞·忆江南》;江南忆;其次忆吴宫;吴酒一杯春竹叶;吴娃双舞醉芙蓉;早晚复相逢\n" +
            "唐;杜甫;《春夜喜雨》;好雨知时节;当春乃发生;随风潜入夜;润物细无声;野径云俱黑;江船火独明;晓看红湿处;花重锦官城\n" +
            "唐;白居易;《钱塘湖春行》;孤山寺北贾亭西;水面初平云脚低;几处早莺争暖树;谁家新燕啄春泥;乱花渐欲迷人眼;浅草才能没马蹄;最爱湖东行不足;绿杨阴里白沙堤\n" +
            "唐;杜牧;《江南春》;千里莺啼绿映红;水村山郭酒旗风;南朝四百八十寺;多少楼台烟雨中\n" +
            "宋;叶绍翁;《游园不值》;应怜屐齿印苍苔;小扣柴扉久不开;春色满园关不住;一枝红杏出墙来\n" +
            "唐;贺知章;《咏柳》;碧玉妆成一树高;万条垂下绿丝绦;不知细叶谁裁出;二月春风似剪刀\n" +
            "宋;苏轼;《惠崇春江晚景》;竹外桃花三两枝;春江水暖鸭先知;篓蒿满地芦芽短;正是河豚欲上时\n" +
            "唐;韩愈;《早春呈水部张十八员外》;天街小雨润如酥;草色遥看近却无;最是一年春好处;绝胜烟柳满皇都\n" +
            "汉;无名;《江南》;江南可采莲;莲叶何田田;鱼戏莲叶间;鱼戏莲叶东;鱼戏莲叶西;鱼戏莲叶南;鱼戏莲叶北\n" +
            "宋;程颢;《春日偶成》;云淡风轻近午天;傍花随柳过前川;时人不识余心乐;将谓偷闲学少年\n" +
            "宋;杨万里;《晓出净慈寺送林子方》;毕竟西湖六月中;风光不与四时同;接天莲叶无穷碧;映日荷花别样红\n" +
            "宋;杨万里;《小池》;泉眼无声惜细流;树阴照水爱晴柔;小荷才露尖尖角;早有蜻蜓立上头\n" +
            "宋;赵师秀;《约客》;黄梅时节家家雨;青草池塘处处蛙;有约不来过夜半;闲敲棋子落灯花\n" +
            "宋;杨万里;《闲居初夏午睡起》;梅子留酸软齿牙;芭蕉分绿与窗纱;日长睡起无情思;闲看儿童捉柳花\n" +
            "宋;杨万里;《宿新市徐公店》;篱落疏疏一径深;树头花落未成阴;儿童急走追黄蝶;飞入菜花无处寻\n" +
            "宋;杨万里;《南溪弄水》;梅从山上过溪来;近爱清溪远爱梅;溪水声声留我住;梅花朵朵唤人回";

    private static List<String> poems = new ArrayList<>();

    static {
        for (String poem : db.split("\n")) {
            poems.add(poem);
        }
    }

    private static String poem;

    static void random() {
        Random rand = new Random();
        int index = rand.nextInt(poems.size());
        poem = poems.get(index);
    }


    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        detector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public void onLongPress(MotionEvent e) {
                random();
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


        random();
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

        String[] textsSplit = poem.split(";");

        List<String> rows = new ArrayList<>();

        for (int i = 3; i < textsSplit.length; i++) {
            rows.add(textsSplit[i]);
        }

        // 行数
        int textLines = rows.size();


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

            float textWidth = paint.measureText(rows.get(i));

            if (i == rows.size() - 1) {
                end = baseY;
                width = textWidth;
            }


            canvas.drawText(rows.get(i), -textWidth / 2, baseY, paint);
        }


        paint.setColor(Color.BLUE);

        paint.setTextSize(sp2px(20));

        float textWidth = paint.measureText(textsSplit[2]);

        canvas.drawText(textsSplit[2], -textWidth / 2, first - textHeight, paint);

        paint.setColor(Color.RED);

        canvas.drawText(textsSplit[0] + "【" + textsSplit[1] + "】", width / 5, end + textHeight, paint);


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
