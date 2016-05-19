package com.android.jolyxie.customtest1;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
/**
 * Created by Administrator on 2016/5/18.
 */
public class CustomViewGroup extends ViewGroup {

    public CustomViewGroup(Context context) {
        super(context);
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        //return super.generateLayoutParams(attrs);
        return new MarginLayoutParams(getContext(),attrs);
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int cWidth = 0;
        int cHeight = 0;
        MarginLayoutParams params = null;

        for(int i = 0 ; i < count ; i++){
            View view = getChildAt(i);
            cWidth = view.getMeasuredWidth();
            cHeight = view.getMeasuredHeight();
            params = (MarginLayoutParams) view.getLayoutParams();

            int cl = 0 ,cr = 0 , ct = 0 , cb = 0;

            switch (i){
                case 0:
                    cl = params.leftMargin;
                    ct = params.topMargin;
                    break;
                case 1:
                    cl = getWidth() - cWidth - params.rightMargin - params.leftMargin;
                    ct = params.topMargin;
                    break;
                case 2:
                    cl = params.leftMargin;
                    ct = getHeight() - cHeight - params.bottomMargin;
                    break;
                case 3:
                    cl = getWidth() - cWidth - params.rightMargin - params.leftMargin;
                    ct = getHeight() - cHeight - params.bottomMargin;
                    break;
            }
            cr = view.getWidth()+cl;
            cb = view.getHeight()+ct;
            view.layout(cl,ct,cr,cb);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //计算所有childview的宽高
        measureChildren(widthMeasureSpec,heightMeasureSpec);

        int width;
        int height;
        int tWidth = 0 , bWidth = 0 , lHeight = 0 , rHeight = 0;
        int count = getChildCount();
        MarginLayoutParams params = null;
        int cWidth = 0;
        int cHeight = 0;

        for(int i = 0 ; i < count ; i++){
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            params = (MarginLayoutParams) childView.getLayoutParams();
            if(i == 0 || i == 1){
                tWidth += cWidth + params.leftMargin + params.rightMargin;
            }
            if(i == 2 || i ==3 ){
                bWidth += cWidth + params.leftMargin + params.rightMargin;
            }
            if(i == 0 || i == 2){
                lHeight += cHeight + params.bottomMargin + params.topMargin;
            }
            if(i == 1 || i == 3){
                rHeight += cHeight + params.bottomMargin + params.topMargin;
            }
        }

        width = Math.max(tWidth,bWidth);
        height = Math.max(rHeight,lHeight);

        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY ? widthSize:width),(heightMode == MeasureSpec.EXACTLY ? heightSize:height));
    }

    /*public CustomViewGroup(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public CustomViewGroup(Context context)
    {
        super(context);
    }

    public CustomViewGroup(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int width = 0;
        int height = 0;

        int cCount = getChildCount();

        int cWidth = 0;
        int cHeight = 0;
        MarginLayoutParams cParams = null;
        int lHeight = 0;
        int rHeight = 0;
        int tWidth = 0;
        int bWidth = 0;

        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            cParams = (MarginLayoutParams) childView.getLayoutParams();

            if (i == 0 || i == 1) {
                tWidth += cWidth + cParams.leftMargin + cParams.rightMargin;
            }

            if (i == 2 || i == 3) {
                bWidth += cWidth + cParams.leftMargin + cParams.rightMargin;
            }

            if (i == 0 || i == 2) {
                lHeight += cHeight + cParams.topMargin + cParams.bottomMargin;
            }

            if (i == 1 || i == 3) {
                rHeight += cHeight + cParams.topMargin + cParams.bottomMargin;
            }

        }

        width = Math.max(tWidth, bWidth);
        height = Math.max(lHeight, rHeight);

        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth : width, (heightMode == MeasureSpec.EXACTLY) ? sizeHeight : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        int cCount = getChildCount();
        int cWidth = 0;
        int cHeight = 0;
        MarginLayoutParams cParams = null;

        for (int i = 0; i < cCount; i++)
        {
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            cParams = (MarginLayoutParams) childView.getLayoutParams();

            int cl = 0, ct = 0, cr = 0, cb = 0;

            switch (i) {
                case 0:
                    cl = cParams.leftMargin;
                    ct = cParams.topMargin;
                    break;
                case 1:
                    cl = getWidth() - cWidth - cParams.leftMargin - cParams.rightMargin;
                    ct = cParams.topMargin;

                    break;
                case 2:
                    cl = cParams.leftMargin;
                    ct = getHeight() - cHeight - cParams.bottomMargin;
                    break;
                case 3:
                    cl = getWidth() - cWidth - cParams.leftMargin - cParams.rightMargin;
                    ct = getHeight() - cHeight - cParams.bottomMargin;
                    break;

            }
            cr = cl + cWidth;
            cb = cHeight + ct;
            childView.layout(cl, ct, cr, cb);
        }
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new MarginLayoutParams(p);
    }*/
}
