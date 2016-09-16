package org.mazhuang.androiduidemos.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

/**
 * Created by mazhuang on 2016/9/16.
 * reference https://github.com/GcsSloop/AndroidNote/blob/master/CustomView/Advance/%5B02%5DCanvas_BasicGraphics.md
 */

public class PieChartView extends View {

    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private float mStartAngle = 0;
    private List<PieData> mData;
    private int[] mColors = {0xff2da94f, 0xffcab79c, 0xffb54f51, 0xfff5c86e, 0xff746a81};
    private RectF mRectF;

    public PieChartView(Context context) {
        this(context, null);
    }

    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        float radius = (float)(Math.min(mWidth, mHeight) / 2 * 0.8);
        mRectF = new RectF(-radius, -radius, radius, radius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null == mData) {
            return;
        }
        float startAngle = mStartAngle;
        canvas.translate(mWidth / 2, mHeight / 2);

        for (PieData pie : mData) {
            mPaint.setColor(pie.color);
            canvas.drawArc(mRectF, startAngle, pie.angle, true, mPaint);
            startAngle += pie.angle;
        }
    }

    public void setStartAngle(float startAngle) {
        this.mStartAngle = startAngle;
        invalidate();
    }

    public void setData(List<PieData> data) {
        this.mData = data;
        prepareData();
        invalidate();
    }

    public void prepareData() {
        if (mData == null || mData.size() == 0) {
            return;
        }

        float sum = 0;
        for (int i = 0; i < mData.size(); i++) {
            PieData pie = mData.get(i);
            sum += pie.value;
            pie.color = mColors[i % mColors.length];
        }

        for (PieData pie : mData) {
            pie.percentage = pie.value / sum;
            pie.angle = pie.percentage * 360;
        }
    }

    public static class PieData {
        public String name;
        public float value;
        public float percentage;
        public int color = 0;
        private float angle = 0;

        public PieData(@NonNull String name, float value) {
            this.name = name;
            this.value = value;
        }
    }
}
