/*
     The MIT License (MIT)
     Copyright (c) 2017 Jenly Yu
     https://github.com/jenly1314

     Permission is hereby granted, free of charge, to any person obtaining
     a copy of this software and associated documentation files
     (the "Software"), to deal in the Software without restriction, including
     without limitation the rights to use, copy, modify, merge, publish,
     distribute, sublicense, and/or sell copies of the Software, and to permit
     persons to whom the Software is furnished to do so, subject to the
     following conditions:

     The above copyright notice and this permission notice shall be included
     in all copies or substantial portions of the Software.

     THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
     IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
     FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
     AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
     LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
     FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
     DEALINGS IN THE SOFTWARE.
 */
package com.king.view;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

/**
 * @author Jenly <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * @since 2017/4/5
 */

public class CounterView extends TextView {

    public static final String DEFAULT_INT_FORMAT = getDecimalFormat(0);

    private static final int DEFAULT_DURATION = 1000;

    private int mDuration = DEFAULT_DURATION;

    private float mNumber;

    private String mFormat;

    private TimeInterpolator interpolator;

    private OnUpdateListener mOnUpdateListener;

    public interface OnUpdateListener{
        boolean onUpdate(float curValue);
    }

    public CounterView(Context context) {
        this(context,null);
    }

    public CounterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CounterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs){
        interpolator = new AccelerateDecelerateInterpolator();
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.CounterView);
        mDuration = a.getInt(R.styleable.CounterView_duration,DEFAULT_DURATION);

        a.recycle();
    }

    public OnUpdateListener getOnUpdateListener() {
        return mOnUpdateListener;
    }

    public void setOnUpdateListener(OnUpdateListener onUpdateListener) {
        this.mOnUpdateListener = onUpdateListener;
    }

    public void setInterpolator(TimeInterpolator interpolator){
        this.interpolator = interpolator;
    }

    public void showAnimation(float number){
        showAnimation(0,number,mDuration,mFormat);
    }

    public void showAnimation(float number,String format){
        showAnimation(0,number,mDuration,format);
    }

    public void showAnimation(float from,float to,int duration){
        showAnimation(from,to,duration,mFormat);
    }

    public void showAnimation(float number,int duration,String format){
        showAnimation(0,number,duration,format);
    }

    public void showAnimation(float from,float to,int duration,String format){
        this.mNumber = to;
        this.mDuration = duration;
        this.mFormat = (format == null ? DEFAULT_INT_FORMAT : format);

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(from,to);
        valueAnimator.setDuration(duration);
        valueAnimator.setInterpolator(interpolator);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float curValue = (float)animation.getAnimatedValue();

                if(mOnUpdateListener==null || !mOnUpdateListener.onUpdate(curValue)){
                    updateNumber(curValue);
                }

            }
        });
        valueAnimator.start();
    }

    public void setFormat(int decimals){
        this.mFormat = getDecimalFormat(decimals);
    }

    /**
     *
     * @param format  default %1$.0f
     */
    public void setFormat(String format){
        this.mFormat = format;
    }

    public void setDuration(int duration){
        this.mDuration = duration;
    }

    private void updateNumber(float number){
        updateNumber(number,mFormat);
    }

    private void updateNumber(float number,String format){
        setText(String.format(format,number));
    }

    /**
     *
     * @param decimals
     * @return  "%1$." + decimals + "f"
     */
    public static String getDecimalFormat(int decimals){
        return "%1$." + decimals + "f";
    }

}
