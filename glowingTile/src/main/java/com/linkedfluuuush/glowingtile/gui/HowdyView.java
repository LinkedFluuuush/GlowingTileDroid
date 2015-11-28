package com.linkedfluuuush.glowingtile.gui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.linkedfluuuush.glowingtile.R;

public class HowdyView extends View {
    private static final String TAG = HowdyView.class.getName();

    private Paint mBoardPaint;

    public HowdyView(Context context) {
        super(context);
        init();
    }

    public HowdyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HowdyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setPosition(int X, int Y){
        this.setX(X);
        this.setY(Y);
    }

    private void init(){
        mBoardPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBoardPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "Position : " + this.getX() + ", " + this.getY());

        super.onDraw(canvas);

        mBoardPaint.setColor(Color.BLACK);
        canvas.drawArc(new RectF(10, 10, 30, 30), 0, 360, true, mBoardPaint);
    }
}
