package com.android.jolyxie.customtest1;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Jolyxie on 2016/5/15.
 */
public class CustomView extends View {

    private String  titleText;
    private int     titleColor;
    private int     titleSize;

    private Paint   mPaint;
    private Rect    mBound;

    public CustomView(Context context) {
        this(context,null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,R.styleable.CustomView,defStyleAttr,0);
        titleColor = a.getColor(R.styleable.CustomView_TitleTextColor, Color.GREEN);
        titleText  = a.getString(R.styleable.CustomView_TitleText);
        titleSize  = a.getDimensionPixelSize(R.styleable.CustomView_TitleTextSize,20);
        a.recycle();

        mPaint = new Paint();
        mBound = new Rect();
        mPaint.setTextSize(titleSize);
        mPaint.getTextBounds(titleText,0,titleText.length(),mBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;

        if(widthMode == MeasureSpec.EXACTLY){
            width = widthSize;
        }else{
            width = mBound.width()+getPaddingLeft()+getPaddingRight();
        }

        if(heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }else{
            height = mBound.height()+getPaddingTop()+getPaddingBottom();
        }
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);
        mPaint.setColor(titleColor);
        canvas.drawText(titleText,(getWidth()-mBound.width())/2,(getHeight()+mBound.height())/2,mPaint);
    }
}
