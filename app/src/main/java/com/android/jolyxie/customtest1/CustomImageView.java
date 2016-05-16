package com.android.jolyxie.customtest1;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Jolyxie on 2016/5/16.
 */
public class CustomImageView extends View {

    private String  titleText;
    private int     titleColor;
    private int     titleSize;

    private Bitmap  imageView;
    private int     scaleType;

    private Paint   mPaint;
    private Rect    mBound;
    private Rect    mImageBound;
    private int     mWidth;
    private int     mHeight;

    private final int IMAGE_SCALE_CENTER = 1;
    private final int IMAGE_SCALE_FITXY = 0;

    public CustomImageView(Context context) {
        this(context,null);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,R.styleable.CustomImageView,defStyleAttr,0);
        titleText   = a.getString(R.styleable.CustomImageView_titleTextImage);
        titleColor  = a.getColor(R.styleable.CustomImageView_textColor, Color.RED);
        titleSize   = a.getDimensionPixelSize(R.styleable.CustomImageView_textSize,22);
        scaleType   = a.getInt(R.styleable.CustomImageView_imageScaleType,0);
        imageView   = BitmapFactory.decodeResource(getResources(),a.getResourceId(R.styleable.CustomImageView_image,0));
        a.recycle();

        mPaint      = new Paint();
        mBound      = new Rect();
        mImageBound = new Rect();
        mPaint.setTextSize(titleSize);
        mPaint.getTextBounds(titleText,0,titleText.length(),mBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int specMode;
        int specSize;

        specMode = MeasureSpec.getMode(widthMeasureSpec);
        specSize  = MeasureSpec.getSize(widthMeasureSpec);
        if(specMode == MeasureSpec.EXACTLY){
            mWidth = specSize;
        }else{
            int desireWidth = getPaddingLeft()+getPaddingRight()+Math.max(imageView.getWidth(),mBound.width());
            mWidth = Math.min(desireWidth,specSize);
        }

        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize  = MeasureSpec.getSize(heightMeasureSpec);
        if(specMode == MeasureSpec.EXACTLY){
            mHeight = specSize;
        }else{
            int desireHeight = getPaddingTop()+getPaddingBottom()+imageView.getHeight()+mBound.height();
            mHeight = Math.min(desireHeight,specSize);
        }
        setMeasuredDimension(mWidth,mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mImageBound.left    = getPaddingLeft();
        mImageBound.right   = mWidth - getPaddingRight();
        mImageBound.top     = getPaddingTop();
        mImageBound.bottom  = mHeight - getPaddingBottom();

        mPaint.setColor(titleColor);
        mPaint.setStyle(Paint.Style.FILL);
        if(mBound.width() < mWidth){
            TextPaint paint = new TextPaint(mPaint);
            String msg = TextUtils.ellipsize(titleText,paint,mWidth - getPaddingLeft()-getPaddingRight(), TextUtils.TruncateAt.END).toString();
            canvas.drawText(msg,getPaddingLeft(),mHeight-getPaddingBottom(),mPaint);
        }else{
            canvas.drawText(titleText,(mWidth - mBound.width())/2,mHeight-getPaddingBottom(),mPaint);
        }

        if(scaleType == IMAGE_SCALE_FITXY){
            mImageBound.bottom -= mBound.height();
        }if(scaleType == IMAGE_SCALE_CENTER){
            mImageBound.left    = mWidth / 2 - imageView.getWidth() / 2;
            mImageBound.right   = mWidth / 2 + imageView.getWidth() / 2;
            mImageBound.top     = (mHeight - mBound.height()) / 2 - imageView.getHeight() / 2;
            mImageBound.bottom  = (mHeight - mBound.height()) / 2 + imageView.getHeight() / 2;
        }
        canvas.drawBitmap(imageView,null,mImageBound,mPaint);
    }
}
