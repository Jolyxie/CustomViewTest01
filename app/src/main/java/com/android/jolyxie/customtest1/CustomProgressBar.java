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
    private int process         = 0;
    private boolean isNext      = false;

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

        //当view处于不显示的状态。或者先显示，再设置不显示的情况。线程依旧会运行。而且应用关闭后线程依旧会运行。
        //我模仿你的代码后，我使用属性动画（也是学你的）解决这个问题。
       // 我重写了onVisibilityChanged() onAttachedToWindow()和onDetachedFromWindow()三个方法，分别在设置显示状态，绑定，解除绑定的时候调用开始或结束动画。这样就不会有内存泄露了
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    process+=5;
                    if(process == 360){
                        process = 0;
                        isNext = !isNext;
                    }
                    postInvalidate();
                    try{
                        Thread.sleep(speed);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /*
    * 在onDraw中不能经常new，不然会占用太多内存，造成手机卡顿。
    * */

    @Override
    protected void onDraw(Canvas canvas) {
        int centre = getWidth() / 2; // 获取圆心的x坐标
        int radius = centre - circleWidth/2;// 半径
        mPaint.setStrokeWidth(circleWidth); // 设置圆环的宽度
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStyle(Paint.Style.STROKE); // 设置空心
        RectF oval = new RectF(centre - radius, centre - radius, centre + radius, centre + radius); // 用于定义的圆弧的形状和大小的界限

        if(isNext) {
            mPaint.setColor(mFirstColor);
            canvas.drawCircle(centre,centre,radius,mPaint);
            mPaint.setColor(mSecondColor);
            canvas.drawArc(oval, -90, process, false, mPaint);
        }else{
            mPaint.setColor(mSecondColor);
            canvas.drawCircle(centre,centre,radius,mPaint);
            mPaint.setColor(mFirstColor);
            canvas.drawArc(oval, -90, process, false, mPaint);
        }
    }
}
