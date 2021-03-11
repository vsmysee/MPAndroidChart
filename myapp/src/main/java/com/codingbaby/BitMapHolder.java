package com.codingbaby;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitMapHolder {

    public Bitmap mountainMap;
    public Bitmap moonMap;
    public Bitmap grassMap;
    public Bitmap rainMap;
    public Bitmap boatMap;
    public Bitmap sunMap;
    public Bitmap snowMap;
    public Bitmap autumnMap;
    public Bitmap peachMap;
    public Bitmap cloudMap;
    public Bitmap xiyangMap;
    public Bitmap lianMap;
    public Bitmap meiMap;
    public Bitmap zhuMap;
    public Bitmap duckMap;
    public Bitmap frogMap;
    public Bitmap wireMap;
    public Bitmap fishMap;
    public Bitmap juMap;

    public BitMapHolder(Resources resources) {

        mountainMap = BitmapFactory.decodeResource(resources, R.drawable.mountain);
        moonMap = BitmapFactory.decodeResource(resources, R.drawable.moon);
        grassMap = BitmapFactory.decodeResource(resources, R.drawable.grass);
        rainMap = BitmapFactory.decodeResource(resources, R.drawable.rain);
        boatMap = BitmapFactory.decodeResource(resources, R.drawable.boat);
        sunMap = BitmapFactory.decodeResource(resources, R.drawable.sun);
        snowMap = BitmapFactory.decodeResource(resources, R.drawable.snow);
        autumnMap = BitmapFactory.decodeResource(resources, R.drawable.autumn);
        peachMap = BitmapFactory.decodeResource(resources, R.drawable.peach);
        cloudMap = BitmapFactory.decodeResource(resources, R.drawable.cloud);
        xiyangMap = BitmapFactory.decodeResource(resources, R.drawable.xiyang);
        lianMap = BitmapFactory.decodeResource(resources, R.drawable.lian);
        meiMap = BitmapFactory.decodeResource(resources, R.drawable.mei);
        zhuMap = BitmapFactory.decodeResource(resources, R.drawable.zhu);
        duckMap = BitmapFactory.decodeResource(resources, R.drawable.duck);
        frogMap = BitmapFactory.decodeResource(resources, R.drawable.frog);
        wireMap = BitmapFactory.decodeResource(resources, R.drawable.wire);
        fishMap = BitmapFactory.decodeResource(resources, R.drawable.fish);
        juMap = BitmapFactory.decodeResource(resources, R.drawable.ju);
    }
}
