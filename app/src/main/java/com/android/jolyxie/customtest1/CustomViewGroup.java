package com.android.jolyxie.customtest1;

import android.content.Context;
import android.util.AttributeSet;
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

    }
}
