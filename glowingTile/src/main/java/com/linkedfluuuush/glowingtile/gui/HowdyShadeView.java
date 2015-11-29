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

public class HowdyShadeView extends View {
    private static final String TAG = HowdyShadeView.class.getName();

    private Paint mBoardPaint;
    private Paint transparentPaint;

    public HowdyShadeView(Context context) {
        super(context);
        init();
    }

    public HowdyShadeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HowdyShadeView(Context context, AttributeSet attrs, int defStyleAttr) {
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

        transparentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "Position : " + this.getX() + ", " + this.getY());

        super.onDraw(canvas);

        Drawable oneTileDrawable = getResources().getDrawable(R.drawable.howdy_shade);

        Bitmap toResizeBitmap = ((BitmapDrawable) oneTileDrawable).getBitmap();
        Drawable resizedTileDrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(toResizeBitmap, 280, 280, true));
        resizedTileDrawable.setBounds(120, 120, 400, 400);
        resizedTileDrawable.draw(canvas);

        canvas.drawRect(0, 0, 121, 520, mBoardPaint);
        canvas.drawRect(0, 0, 520, 121, mBoardPaint);
        canvas.drawRect(399,0,520,520,mBoardPaint);
        canvas.drawRect(0,399,520,520,mBoardPaint);
    }
}
