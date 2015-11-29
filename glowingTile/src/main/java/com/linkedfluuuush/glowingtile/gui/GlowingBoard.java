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

import com.linkedfluuuush.glowingtile.core.Game;
import com.linkedfluuuush.glowingtile.core.Tile;

public class GlowingBoard extends View{
    private Game game;
    private Paint mBoardPaint;

    private static final String TAG = GlowingBoard.class.getName();

    public GlowingBoard(Context context) {
        super(context);
        init();
    }

    public GlowingBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GlowingBoard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mBoardPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBoardPaint.setColor(Color.BLACK);

        this.game = new Game();
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        String fileName;

        Drawable oneTileDrawable, resizedTileDrawable;
        Bitmap toResizeBitmap;


        mBoardPaint.setColor(Color.WHITE);

        if (game.getLabyrinth() != null) {
            for (Tile t : game.getLabyrinth().getTiles()) {
                switch (t.getType()) {
                    case DEPART:
                        mBoardPaint.setColor(Color.BLUE);
                        fileName = "tile";
                        break;
                    case ARRIVEE:
                        mBoardPaint.setColor(Color.YELLOW);
                        fileName = "goal";
                        break;
                    case NEUTRE:
                        mBoardPaint.setColor(Color.WHITE);
                        fileName = "tile";
                        break;
                    default:
                        mBoardPaint.setColor(Color.BLACK);
                        fileName = "";
                        break;
                }

                switch (t.getEtat()) {
                    case USE:
                        fileName += "_glowing";
                        break;
                    default:
                        fileName = "";
                        break;
                }

                if (!fileName.equals("")) {
                    Log.d(TAG, "Drawing " + fileName + " at " + t.getX() + "," + t.getY());
                    //canvas.drawRect(t.getX() * 50, t.getY() * 50, (t.getX() * 50) + 50, (t.getY() * 50) + 50, mBoardPaint);
                    oneTileDrawable = getResources().getDrawable(getResources().getIdentifier(fileName, "drawable", "com.linkedfluuuush.glowingtile"));

                    toResizeBitmap = ((BitmapDrawable) oneTileDrawable).getBitmap();
                    resizedTileDrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(toResizeBitmap, 60, 60, true));
                    resizedTileDrawable.setBounds(t.getX() * 40 - 10, t.getY() * 40 - 10, (t.getX() * 40) + 50, (t.getY() * 40) + 50);
                    resizedTileDrawable.draw(canvas);
                }
            }
        }
    }
}
