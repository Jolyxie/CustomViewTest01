package com.android.jolyxie.customtest1;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Jolyxie on 2016/5/16.
 */
public class CustomProgressBar extends View {

    private int mFirstColor;
    private int mSecondColor;
    private int circleWidth;
    private int speed;

    private Paint mPaint;

    public CustomProgressBar(Context context) {
        this(context,null);
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a    = context.getTheme().obtainStyledAttributes(attrs,R.styleable.CustomProgressBar,defStyleAttr,0);
        mFirstColor     = a.getColor(R.styleable.CustomProgressBar_firstColor, Color.GREEN);
        mSecondColor    = a.getColor(R.styleable.CustomProgressBar_secondColor, Color.GREEN);
        circleWidth     = (int)a.getDimension(R.styleable.CustomProgressBar_circleWidth,50);
        speed           = a.getInteger(R.styleable.CustomProgressBar_speed,2);
        a.recycle();
        mPaint          = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int centre = getWidth() / 2; // 获取圆心的x坐标
        int radius = centre - circleWidth;// 半径
        mPaint.setStrokeWidth(circleWidth); // 设置圆环的宽度
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStyle(Paint.Style.STROKE); // 设置空心
        mPaint.setColor(mFirstColor);
        Log.e("jolyxie","00000000000000000>>"+centre+"<---->"+circleWidth+"<--->"+radius);
        RectF oval = new RectF(centre - radius, centre - radius, centre + radius, centre + radius); // 用于定义的圆弧的形状和大小的界限
        canvas.drawArc(oval,-90,60,false,mPaint);
    }
}
