package org.mazhuang.androiduidemos.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

import static android.graphics.Canvas.ALL_SAVE_FLAG;

/**
 * Created by mazhuang on 2016/11/10.
 */

public class XfermodeTestView extends View {
    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private Xfermode mXfermode;

    public XfermodeTestView(Context context) {
        this(context, null);
    }

    public XfermodeTestView(Context context, AttributeSet attrs) {
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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //创建一个图层，在图层上演示图形混合后的效果
        int sc = canvas.saveLayer(null, mPaint, ALL_SAVE_FLAG);

        canvas.translate(mWidth / 2, mHeight / 2);

        int radius = Math.min(mWidth, mHeight) / 6;
        mPaint.setColor(0xFFFEC336);
        canvas.drawCircle(0, 0, radius, mPaint);

        Xfermode oldMode = mPaint.getXfermode();
        if (mXfermode != null) {
            mPaint.setXfermode(mXfermode);
        }

        int size = Math.min(mWidth, mHeight) / 4;
        mPaint.setColor(0xFF5596FF);
        canvas.drawRect(0, 0, size, size, mPaint);

        mPaint.setXfermode(oldMode);

        canvas.restoreToCount(sc);
    }

    public void setXferMode(Xfermode xferMode) {
        mXfermode = xferMode;
        invalidate();
    }
}
