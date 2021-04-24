package org.mazhuang.androiduidemos.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class LineDrawingBoardView extends View {

    private final Path path;
    private final Paint paint;

    private int startX;
    private int startY;
    private int endX;
    private int endY;

    private boolean isDrawing = false;

    public LineDrawingBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        path = new Path();

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
    }

    public LineDrawingBoardView(Context context) {
        this(context, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        path.reset();

        path.moveTo(startX, startY);
        path.lineTo(endX, endY);

        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int action = event.getAction();
        if (MotionEvent.ACTION_DOWN == action) {
            isDrawing = true;
            startX = x;
            startY = y;
            endX = x;
            endY = y;
            return true;
        }

        endX = x;
        endY = y;

        if (isDrawing) {
            invalidate();
        }

        if (MotionEvent.ACTION_UP == action) {
            isDrawing = false;
        }
        return true;
    }

    public void onDrawTypeChanged(boolean isDash) {
        paint.setPathEffect(isDash ? new DashPathEffect(new float[]{20, 20}, 0) : null);
    }
}