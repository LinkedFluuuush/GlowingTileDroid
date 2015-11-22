package com.linkedfluuuush.glowingtile.gui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.linkedfluuuush.glowingtile.R;
import com.linkedfluuuush.glowingtile.core.Game;
import com.linkedfluuuush.glowingtile.core.Tile;

public class GameBoard extends View {
    private Game game;
    private Paint mBoardPaint;

    public GameBoard(Context context) {
        super(context);
        init();
    }

    public GameBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GameBoard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mBoardPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBoardPaint.setColor(Color.BLACK);

        this.game = new Game();
    }

    public void setGame(Game game){
        this.game = game;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        String fileName;

        Drawable oneTileDrawable, resizedTileDrawable;
        Bitmap toResizeBitmap;

        mBoardPaint.setColor(Color.WHITE);

        for(Tile t : game.getLabyrinth().getTiles()){
            switch (t.getType()){
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

            switch (t.getEtat()){
                case NEUF:
                    fileName += "";
                    break;
                case USE:
                    fileName += "_glowing";
                    break;
                case CASSE:
                    fileName += "_broken";
                    break;
                default:
                    fileName += "";
                    break;
            }

            //canvas.drawRect(t.getX() * 50, t.getY() * 50, (t.getX() * 50) + 50, (t.getY() * 50) + 50, mBoardPaint);
            oneTileDrawable = getResources().getDrawable(getResources().getIdentifier(fileName, "drawable", "com.linkedfluuuush.glowingtile"));

            toResizeBitmap = ((BitmapDrawable) oneTileDrawable).getBitmap();
            resizedTileDrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(toResizeBitmap, 60, 60, true));
            resizedTileDrawable.setBounds(t.getX() * 40 - 10, t.getY() * 40 - 10, (t.getX() * 40) + 50, (t.getY() * 40) + 50);
            resizedTileDrawable.draw(canvas);
        }

        mBoardPaint.setColor(Color.BLACK);
        canvas.drawArc(new RectF(game.getHowdy().getX() * 40 + 10, game.getHowdy().getY() * 40 + 10, game.getHowdy().getX() * 40 + 30, game.getHowdy().getY() * 40 + 30), 0, 360, true, mBoardPaint);

        oneTileDrawable = getResources().getDrawable(R.drawable.howdy_shade);

        toResizeBitmap = ((BitmapDrawable) oneTileDrawable).getBitmap();
        resizedTileDrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(toResizeBitmap, 280, 280, true));
        resizedTileDrawable.setBounds((this.game.getHowdy().getX() - 3) * 40, (this.game.getHowdy().getY() - 3) * 40, ((this.game.getHowdy().getX() - 3) * 40) + 280, ((this.game.getHowdy().getY() - 3) * 40) + 280);
        resizedTileDrawable.draw(canvas);

        mBoardPaint.setColor(Color.BLACK);
        canvas.drawRect(0, 0, this.getWidth(), (this.game.getHowdy().getY() - 3) * 40, mBoardPaint);
        canvas.drawRect(0, 0, (this.game.getHowdy().getX() - 3) * 40, this.getHeight(), mBoardPaint);
        canvas.drawRect(0, (this.game.getHowdy().getY() + 3) * 40, this.getWidth(), this.getHeight(), mBoardPaint);
        canvas.drawRect((this.game.getHowdy().getX() + 3) * 40, 0, this.getWidth(), this.getHeight(), mBoardPaint);
    }
}
