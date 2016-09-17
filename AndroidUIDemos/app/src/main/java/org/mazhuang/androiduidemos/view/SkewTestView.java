package org.mazhuang.androiduidemos.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by mazhuang on 2016/9/17.
 */

public class SkewTestView extends View {

    private static String TAG = SkewTestView.class.getName();

    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private float mSkewX;
    private float mSkewY;
    private RectF mRectF;

    public SkewTestView(Context context) {
        this(context, null);
    }

    public SkewTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        float edgeLength = (float) (Math.min(w,h) * 0.2);
        mRectF = new RectF(0, 0, edgeLength, edgeLength);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.i(TAG, "skewx: " + mSkewX + " ,skewy: " + mSkewY);

        canvas.translate(mWidth / 5, mHeight / 5);

        mPaint.setColor(Color.BLACK);
        canvas.drawRect(mRectF, mPaint);

        canvas.skew(mSkewX, 0);
        canvas.skew(0, mSkewY);

        mPaint.setColor(Color.BLUE);
        canvas.drawRect(mRectF, mPaint);
    }

    public void setData(float skewX, float skewY) {
        mSkewX = skewX;
        mSkewY = skewY;
        invalidate();
    }
}
