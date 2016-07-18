package org.mazhuang.androiduidemos.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by mazhuang on 2016/7/17.
 */
public class TrafficBarView extends ImageView {
    private List<TrafficSegment> mData;
    private int mNoDateColor = Color.argb(255, 26, 166, 239);
    private int mGoodColor = Color.parseColor("#05C300");
    private int mOkayColor = Color.parseColor("#FFD615");
    private int mBadColor = Color.argb(255, 255, 93, 91);
    private int mVeryBadColor = Color.argb(255, 179, 17, 15);
    private Paint mPaint;
    private RectF mColorRectF;
    private boolean mIsAfterLayout;

    public TrafficBarView(Context context) {
        super(context);
        init();
    }

    public TrafficBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TrafficBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    mIsAfterLayout = true;
                    if (mData != null) {
                        update(mData);
                    }
                }
            });
        }
    }

    public void update(List<TrafficSegment> segments) {
        mData = segments;
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
        } else {
            float totalDistance = 0.0f;
            for (TrafficSegment segment : mData) {
                totalDistance += segment.mLength;
            }

            mPaint.setStyle(Paint.Style.FILL);

            int width = getWidth();
            int height = getHeight();

            Bitmap fakeCanvas = Bitmap.createBitmap(width, height,
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(fakeCanvas);
            float drawedDistance = 0.0f;
            for (TrafficSegment segment : mData) {
                int color = mNoDateColor;
                switch (segment.mTrafficLevel) {
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
                mPaint.setColor(color);

                float bottom = height * ((totalDistance - drawedDistance) / totalDistance);
                drawedDistance += segment.mLength;
                float top = height * ((totalDistance - drawedDistance) / totalDistance);
                mColorRectF.set(0.0f, top, width, bottom);

                canvas.drawRect(mColorRectF, mPaint);
            }

            return fakeCanvas;
        }
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
