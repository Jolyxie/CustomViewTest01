package com.android.jolyxie.customtest1;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Jolyxie on 2016/5/17.
 */
public class CustomVolumeControl extends View {

    private int     finishedColor;
    private int     unfinishedColor;
    private int     dotCount;
    private int     splitSize;
    private int     circleWidth;
    private Bitmap  imageView;
    private int     finishedCount = 8;
    private Paint   mPaint;

    private Rect    mRect;

    public CustomVolumeControl(Context context) {
        this(context,null);
    }

    public CustomVolumeControl(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomVolumeControl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,R.styleable.CustomVolumeControl,defStyleAttr,0);
        finishedColor   = a.getColor(R.styleable.CustomVolumeControl_finishedColor, Color.GREEN);
        unfinishedColor = a.getColor(R.styleable.CustomVolumeControl_unfinishedColor, Color.GREEN);
        circleWidth     = (int)a.getDimension(R.styleable.CustomVolumeControl_circle_Width,15);
        dotCount        = a.getInteger(R.styleable.CustomVolumeControl_dotCount,12);
        splitSize       = a.getInteger(R.styleable.CustomVolumeControl_splitSize,10);
        imageView       = BitmapFactory.decodeResource(getResources(),a.getResourceId(R.styleable.CustomVolumeControl_imageView,0));
        a.recycle();
        mPaint          = new Paint();
        mRect           = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStrokeWidth(circleWidth); // 设置圆环的宽度
        mPaint.setStrokeCap(Paint.Cap.ROUND); // 定义线段断电形状为圆头
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStyle(Paint.Style.STROKE); // 设置空心
        int centre = getWidth() / 2; // 获取圆心的x坐标
        int radius = centre - circleWidth / 2;// 半径

        RectF oval = new RectF(centre - radius, centre - radius, centre + radius, centre + radius); // 用于定义的圆弧的形状和大小的界限
        int eachDotAngle = 360/dotCount - splitSize;

        mPaint.setColor(finishedColor);
        for(int i = 0 ; i < finishedCount ; i++) {
            canvas.drawArc(oval, i*(eachDotAngle+splitSize), eachDotAngle, false, mPaint);
        }
        mPaint.setColor(unfinishedColor);
        for(int i = finishedCount ; i < dotCount ; i++) {
            canvas.drawArc(oval, i*(eachDotAngle+splitSize), eachDotAngle, false, mPaint);
        }

        int realRadius = radius - circleWidth/2;
        mRect.left      = (int)(realRadius - Math.sqrt(2)*1.0f/2*realRadius) + circleWidth;
        mRect.top       = (int)(realRadius - Math.sqrt(2)*1.0f/2*realRadius) + circleWidth;
        mRect.right     = (int)(mRect.left + Math.sqrt(2)*realRadius);
        mRect.bottom    = (int)(mRect.left + Math.sqrt(2)*realRadius);
        if(imageView.getWidth() < Math.sqrt(2) * realRadius){
            mRect.left = mRect.left + (int)(realRadius*Math.sqrt(2)/2) - imageView.getWidth()/2;
            mRect.top  = mRect.top + (int)(realRadius*Math.sqrt(2)/2) - imageView.getHeight()/2;
            mRect.bottom = mRect.top + imageView.getWidth();
            mRect.right  = mRect.left + imageView.getHeight();
        }
        canvas.drawBitmap(imageView,null,mRect,mPaint);
    }
}
