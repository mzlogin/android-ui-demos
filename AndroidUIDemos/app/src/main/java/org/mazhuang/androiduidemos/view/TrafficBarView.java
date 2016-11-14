package org.mazhuang.androiduidemos.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.mazhuang.androiduidemos.R;

import java.util.List;

/**
 * Created by mazhuang on 2016/7/17.
 */
public class TrafficBarView extends ImageView {

    // custom properties
    private int mBorderSize; // in px
    private int mOrientation;
    private int mBorderColor;
    private Drawable mIndicatroDrawable;
    private int mIndicatorExtrudeSize;
    private boolean mIndicatorBgClear;
    // ===

    private List<TrafficSegment> mData;
    private int mNoDateColor = Color.parseColor("#FF1AA6EF");
    private int mGoodColor = Color.parseColor("#FF05C300");
    private int mOkayColor = Color.parseColor("#FFFFD615");
    private int mBadColor = Color.parseColor("#FFFF5D5B");
    private int mVeryBadColor = Color.parseColor("#FFB3110F");
    private int mPassColor = Color.parseColor("#FFCCCCCC");

    private Paint mPaint;
    private RectF mColorRectF;
    private boolean mIsAfterLayout;
    private float mTotalDistance;
    private int mDistanceToEnd;

    public TrafficBarView(Context context) {
        this(context, null);
    }

    public TrafficBarView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TrafficBarView);
        try {
            mOrientation = ta.getInt(R.styleable.TrafficBarView_android_orientation, LinearLayout.VERTICAL);
            mBorderSize = ta.getDimensionPixelSize(R.styleable.TrafficBarView_borderSize, 0);
            mBorderColor = ta.getColor(R.styleable.TrafficBarView_borderColor, Color.WHITE);
            mIndicatroDrawable = ta.getDrawable(R.styleable.TrafficBarView_indicator);
            mIndicatorExtrudeSize = ta.getDimensionPixelSize(R.styleable.TrafficBarView_indicatorExtrude, 0);
            mIndicatorBgClear = ta.getBoolean(R.styleable.TrafficBarView_indicatorBgClear, false);
            int colorsId = ta.getResourceId(R.styleable.TrafficBarView_colors, 0);
            if (colorsId != 0) {
                try {
                    int[] colors = getResources().getIntArray(colorsId);
                    if (colors.length >= 6) {
                        mNoDateColor = colors[0];
                        mGoodColor = colors[1];
                        mOkayColor = colors[2];
                        mBadColor = colors[3];
                        mVeryBadColor = colors[4];
                        mPassColor = colors[5];
                    }
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            ta.recycle();
        }

        init();
    }

    public void init() {
        mPaint = new Paint();
        mColorRectF = new RectF();
        final ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                    mIsAfterLayout = true;
                    if (mData != null) {
                        update(mData, mDistanceToEnd);
                    }
                }
            });
        }
    }

    public void serOrientation(int orientation) {
        if (mOrientation != orientation) {
            mOrientation = orientation;
            update(mDistanceToEnd);
        }
    }

    public void update(List<TrafficSegment> segments, int disToEnd) {
        mData = segments;
        mTotalDistance = 0.0f;
        for (TrafficSegment segment : mData) {
            mTotalDistance += segment.mLength;
        }
        mDistanceToEnd = disToEnd;
        if (!mIsAfterLayout) {
            return;
        }
        Bitmap src = produceFinalBitmap();
        if (src != null) {
            setImageBitmap(src);
        }
    }

    public void update(int disToEnd) {
        mDistanceToEnd = disToEnd;
        if (!mIsAfterLayout) {
            return;
        }
        Bitmap src = produceFinalBitmap();
        if (src != null) {
            setImageBitmap(src);
        }
    }

    public Bitmap produceFinalBitmap() {
        if (mData == null) {
            return null;
        }

        if (mOrientation == LinearLayout.HORIZONTAL) {
            return produceHorizontalBitmap();
        } else {
            return produceVerticalBitmap();
        }
    }

    public Bitmap produceHorizontalBitmap() {

        mPaint.setStyle(Paint.Style.FILL);

        int width = getWidth();
        int height = getHeight();

        if (mIndicatorExtrudeSize > height) {
            mIndicatorExtrudeSize = 0;
        }

        if (width <= 4 * mBorderSize || height <= 4 * mBorderSize || mIndicatorExtrudeSize != 0) {
            mBorderSize = 0;
        }

        Bitmap fakeCanvas = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(fakeCanvas);

        // draw background
        mPaint.setColor(mBorderColor);
        mColorRectF.set(0, mIndicatorExtrudeSize, width, height);
        canvas.drawRect(mColorRectF, mPaint);

        // draw traffic segments
        float drawedDistance = 0.0f;
        for (TrafficSegment segment : mData) {
            int color = getColorByLevel(segment.mTrafficLevel);

            mPaint.setColor(color);

            float left = mBorderSize + (width - mBorderSize * 2) * (drawedDistance / mTotalDistance);
            drawedDistance += segment.mLength;
            float right = mBorderSize + (width - mBorderSize * 2) * (drawedDistance / mTotalDistance);
            mColorRectF.set(left, mBorderSize + mIndicatorExtrudeSize, right, height - mBorderSize);

            canvas.drawRect(mColorRectF, mPaint);
        }

        Xfermode oldXferMode = mPaint.getXfermode();

        // drawable passed
        float right = mBorderSize + (width - mBorderSize * 2) * ((mTotalDistance - mDistanceToEnd) / mTotalDistance);
        mColorRectF.set(mBorderSize, mBorderSize + mIndicatorExtrudeSize, right, height - mBorderSize);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        mPaint.setColor(mPassColor);
        canvas.drawRect(mColorRectF, mPaint);
        mPaint.setXfermode(oldXferMode);

        // drawable progress loc
        if (mIndicatroDrawable != null) {
            int locWidth = mIndicatroDrawable.getIntrinsicWidth();
            if (right < mBorderSize + (locWidth / 2)) {
                right = mBorderSize + locWidth;
            } else {
                right = Math.min(width - mBorderSize, right + (locWidth / 2));
            }

            Rect bound = new Rect(Math.round(right - locWidth), mBorderSize, Math.round(right), height - mBorderSize);

            if (mIndicatorBgClear) {
                mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                mPaint.setColor(Color.TRANSPARENT);
                canvas.drawRect(bound, mPaint);
                mPaint.setXfermode(oldXferMode);
            }

            mIndicatroDrawable.setBounds(bound);
            mIndicatroDrawable.draw(canvas);
        }

        return fakeCanvas;
    }

    public Bitmap produceVerticalBitmap() {
        mPaint.setStyle(Paint.Style.FILL);

        int width = getWidth();
        int height = getHeight();


        if (mIndicatorExtrudeSize > width) {
            mIndicatorExtrudeSize = 0;
        }

        if (width <= 4 * mBorderSize || height <= 4 * mBorderSize || mIndicatorExtrudeSize != 0) {
            mBorderSize = 0;
        }

        Bitmap fakeCanvas = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(fakeCanvas);

        // draw background
        mPaint.setColor(mBorderColor);
        mColorRectF.set(mIndicatorExtrudeSize, 0, width, height);
        canvas.drawRect(mColorRectF, mPaint);

        // draw traffic segments
        float drawedDistance = 0.0f;
        for (TrafficSegment segment : mData) {
            int color = getColorByLevel(segment.mTrafficLevel);

            mPaint.setColor(color);

            float bottom = mBorderSize + (height - mBorderSize * 2) * ((mTotalDistance - drawedDistance) / mTotalDistance);
            drawedDistance += segment.mLength;
            float top = mBorderSize + (height - mBorderSize * 2) * ((mTotalDistance - drawedDistance) / mTotalDistance);
            mColorRectF.set(mBorderSize + mIndicatorExtrudeSize, top, width - mBorderSize, bottom);

            canvas.drawRect(mColorRectF, mPaint);
        }

        Xfermode oldXferMode = mPaint.getXfermode();

        // drawable passed
        float top = mBorderSize + (height - mBorderSize * 2) * (mDistanceToEnd / mTotalDistance);
        mColorRectF.set(mBorderSize + mIndicatorExtrudeSize, top, width - mBorderSize, height - mBorderSize);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        mPaint.setColor(mPassColor);
        canvas.drawRect(mColorRectF, mPaint);
        mPaint.setXfermode(oldXferMode);

        // drawable progress loc
        if (mIndicatroDrawable != null) {
            int locHeight = mIndicatroDrawable.getIntrinsicHeight();
            if (top > height - mBorderSize - (locHeight / 2)) {
                top = height - mBorderSize - locHeight;
            } else {
                top = Math.max(mBorderSize, top - (locHeight / 2));
            }

            Rect bound = new Rect(mBorderSize, Math.round(top), width - mBorderSize, Math.round(top + locHeight));

            if (mIndicatorBgClear) {
                mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                mPaint.setColor(Color.TRANSPARENT);
                canvas.drawRect(bound, mPaint);
                mPaint.setXfermode(oldXferMode);
            }

            mIndicatroDrawable.setBounds(bound);
            mIndicatroDrawable.draw(canvas);
        }

        return fakeCanvas;
    }

    private int getColorByLevel(int level) {
        int color = mNoDateColor;
        switch (level) {
            case TrafficSegment.TRAFFIC_LEVEL_NO_DATA:
                color = mNoDateColor;
                break;
            case TrafficSegment.TRAFFIC_LEVEL_GOOD:
                color = mGoodColor;
                break;
            case TrafficSegment.TRAFFIC_LEVEL_OKAY:
                color = mOkayColor;
                break;
            case TrafficSegment.TRAFFIC_LEVEL_BAD:
                color = mBadColor;
                break;
            case TrafficSegment.S_TRAFFIC_LEVEL_VERY_BAD:
                color = mVeryBadColor;
                break;
        }
        return color;
    }

    public static class TrafficSegment {
        public static final int TRAFFIC_LEVEL_NO_DATA = 0; // 没数据
        public static final int TRAFFIC_LEVEL_GOOD = 1; // 畅通
        public static final int TRAFFIC_LEVEL_OKAY = 2; // 缓行
        public static final int TRAFFIC_LEVEL_BAD = 3; // 拥堵
        public static final int S_TRAFFIC_LEVEL_VERY_BAD = 4; // 严重拥堵

        public int mLength;
        public int mTrafficLevel;

        public TrafficSegment(int length, int trafficLevel) {
            mLength = length;
            mTrafficLevel = trafficLevel;
        }
    }
}
