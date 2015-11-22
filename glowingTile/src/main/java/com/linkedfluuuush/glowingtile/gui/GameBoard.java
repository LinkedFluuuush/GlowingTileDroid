package com.linkedfluuuush.glowingtile.gui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.linkedfluuuush.glowingtile.core.Game;
import com.linkedfluuuush.glowingtile.core.Tile;

/**
 * Created by LinkedFluuuush on 22/11/2015.
 */
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

        mBoardPaint.setColor(Color.WHITE);

        for(Tile t : game.getLabyrinth().getTiles()){
            switch (t.getType()){
                case DEPART:
                    mBoardPaint.setColor(Color.BLUE);
                    break;
                case ARRIVEE:
                    mBoardPaint.setColor(Color.YELLOW);
                    break;
                case NEUTRE:
                    mBoardPaint.setColor(Color.WHITE);
                    break;
                default:
                    mBoardPaint.setColor(Color.BLACK);
                    break;
            }
            canvas.drawRect(t.getX() * 50, t.getY() * 50, (t.getX() * 50) + 50, (t.getY() * 50) + 50, mBoardPaint);
        }

        mBoardPaint.setColor(Color.RED);
        canvas.drawRect(new Rect(game.getHowdy().getX() * 50, game.getHowdy().getY() * 50, game.getHowdy().getX() * 50 + 10, game.getHowdy().getY() * 50 + 10), mBoardPaint);
    }
}
