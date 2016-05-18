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
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        MarginLayoutParams params = null;
        int cl = 0 ,cr = 0 , ct = 0 , cb = 0;
        for(int i = 0 ; i < count ; i++){
            View view = getChildAt(i);
            params = (MarginLayoutParams) view.getLayoutParams();
            switch (i){
                case 0:
                    cl = params.leftMargin;
                    ct = params.topMargin;
                    break;
                case 1:
                    cl = getWidth() - view.getWidth() - params.rightMargin - params.leftMargin;
                    ct = params.topMargin;
                    break;
                case 2:
                    cl = params.leftMargin;
                    ct = getHeight() - view.getHeight() - params.bottomMargin - params.topMargin;
                    break;
                case 3:
                    cl = getWidth() - view.getWidth() - params.rightMargin - params.leftMargin;
                    ct = getHeight() - view.getHeight() - params.bottomMargin - params.topMargin;
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

        for(int i = 0 ; i < count ; i++){
            View childView = getChildAt(i);
            params = (MarginLayoutParams) childView.getLayoutParams();
            if(i == 0 || i == 1){
                tWidth += childView.getWidth() + params.leftMargin + params.rightMargin;
            }
            if(i == 2 || i ==3 ){
                bWidth += childView.getWidth() + params.leftMargin + params.rightMargin;
            }
            if(i == 0 || i == 2){
                lHeight += childView.getHeight() + params.bottomMargin + params.topMargin;
            }
            if(i == 1 || i == 3){
                rHeight += childView.getHeight() + params.bottomMargin + params.topMargin;
            }
        }

        width = Math.max(tWidth,bWidth);
        height = Math.max(rHeight,lHeight);

        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY ? widthSize:width),(heightMode == MeasureSpec.EXACTLY ? heightSize:height));
    }
}
