package com.linkedfluuuush.glowingtile.gui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.linkedfluuuush.glowingtile.core.Game;
import com.linkedfluuuush.glowingtile.core.Tile;
import android.util.*;

public class GameBoard extends View {
    private Game game;
    private Paint mBoardPaint;

	private HowdyShadeView howdyShadeView;
	private HowdyView howdyView;
    private GlowingBoard glowingBoard;

	private static final String TAG = GameBoard.class.getName();

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

    private void init() {
        mBoardPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBoardPaint.setColor(Color.BLACK);

        this.game = new Game();
    }

    public void setGame(Game game) {
        this.game = game;
        this.howdyShadeView.setPosition((this.game.getHowdy().getX() - 6) * 40, (this.game.getHowdy().getY() - 6) * 40);
        Log.d(TAG, "Position : " + this.howdyShadeView.getX() + ", " + this.howdyShadeView.getY());
        this.glowingBoard.setGame(game);
        this.howdyView.setPosition((this.game.getHowdy().getX()) * 40, (this.game.getHowdy().getY()) * 40);
        Log.d(TAG, "Position : " + this.howdyView.getX() + ", " + this.howdyView.getY());

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        String fileName;

        Drawable oneTileDrawable, resizedTileDrawable;
        Bitmap toResizeBitmap;

		mBoardPaint.setColor(Color.BLACK);
		canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), mBoardPaint);

        mBoardPaint.setColor(Color.WHITE);

		if (game.getLabyrinth() != null) {
			for (Tile t : game.getLabyrinth().getTiles()) {
				switch (t.getType()) {
					case DEPART:
						mBoardPaint.setColor(Color.BLUE);
						fileName = "start";
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
					case NEUF:
						fileName += "";
						break;
                    case USE:
                        fileName = "";
                        break;
					case CASSE:
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

			mBoardPaint.setColor(Color.BLACK);
			canvas.drawRect(0, 0, this.getWidth(), (this.game.getHowdy().getY() - 4) * 40, mBoardPaint);
			canvas.drawRect(0, 0, (this.game.getHowdy().getX() - 4) * 40, this.getHeight(), mBoardPaint);
			canvas.drawRect(0, (this.game.getHowdy().getY() + 5) * 40, this.getWidth(), this.getHeight(), mBoardPaint);
			canvas.drawRect((this.game.getHowdy().getX() + 5) * 40, 0, this.getWidth(), this.getHeight(), mBoardPaint);


			/*for (Tile t : game.getLabyrinth().getTiles()) {
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
			}*/

            this.howdyShadeView.invalidate();
            this.glowingBoard.invalidate();
		}
    }

    public void setHowdyShadeView(HowdyShadeView howdyShadeView) {
        this.howdyShadeView = howdyShadeView;
    }

    public HowdyShadeView getHowdyShadeView() {
        return howdyShadeView;
    }

    public GlowingBoard getGlowingBoard() {
        return glowingBoard;
    }

    public void setGlowingBoard(GlowingBoard glowingBoard) {
        this.glowingBoard = glowingBoard;
    }

    public HowdyView getHowdyView() {
        return howdyView;
    }

    public void setHowdyView(HowdyView howdyView) {
        this.howdyView = howdyView;
    }
}
