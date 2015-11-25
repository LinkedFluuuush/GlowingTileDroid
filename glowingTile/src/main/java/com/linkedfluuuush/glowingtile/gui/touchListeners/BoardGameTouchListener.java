package com.linkedfluuuush.glowingtile.gui.touchListeners;
import android.view.View.*;
import android.view.*;
import android.util.*;
import com.linkedfluuuush.glowingtile.gui.*;
import com.linkedfluuuush.glowingtile.*;
import com.linkedfluuuush.glowingtile.core.*;
import android.widget.*;

public class BoardGameTouchListener implements OnTouchListener {

	private static final String TAG = BoardGameTouchListener.class.getName();

	private MainGame mainGame;

	private float x1,x2,y1,y2;
    private boolean movedByProxi = false;
	private static final int MIN_DISTANCE=50;

	public BoardGameTouchListener(MainGame mainGame) {
		super();
		this.mainGame = mainGame;
	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		GameBoard boardView = (GameBoard) view;
		Game game = mainGame.getGame();

		/*MotionEvent.PointerCoords coords = new MotionEvent.PointerCoords();
         event.getPointerCoords(0, coords);

         float deltaX = Math.abs(event.getX() - (game.getHowdy().getX() * 40) + 10);
         float deltaY = Math.abs(event.getY() - (game.getHowdy().getY() * 40) + 10);

         Log.d(TAG, "Deltas : X = " + deltaX + " (" + event.getX() + "-" +
         ((game.getHowdy().getX() * 40) + 10) + "), Y = " + deltaY +
         " (" + event.getY() + "-" + ((game.getHowdy().getY() * 40) + 10) + ")");

         if(deltaX > deltaY){ /* On considère que l'on a appuyé pour un mouvement horizontal (plus proche de l'axe horizontal de Howdy) */
        /*if(event.getX() > (game.getHowdy().getX() * 40) + 10){
         Log.d(TAG, "Moving right");
         game.moveRight();
         } else if(event.getX() < (game.getHowdy().getX() * 40) + 10) {
         Log.d(TAG, "Moving left");
         game.moveLeft();
         }
         } else if(deltaX < deltaY) { /* On considère que l'on a appuyé pour un mouvement vertical (plus proche de l'axe vertical de Howdy) */
        /*if(event.getY() > (game.getHowdy().getY() * 40) + 10){
         Log.d(TAG, "Moving down");
         game.moveDown();
         } else if(event.getY() < (game.getHowdy().getY() * 40) + 10) {
         Log.d(TAG, "Moving up");
         game.moveUp();
         }
         }

         /* Dans le cas ou les deltas sont égaux, ou le cas ou le joueur appuie précisément sur Howdy, on ne fait rien par indécision. */

		/*boardView.setGame(game);
         boardView.invalidate();*/
         
         float x = event.getX();
         float y = event.getY();
         
        Log.d(TAG, "Howdy at " + game.getHowdy().getX() * 40 + "," + game.getHowdy().getY() * 40);
        Log.d(TAG, "Down with coords " + x + "," + y);

        if (x >= game.getHowdy().getX() * 40 && x <= (game.getHowdy().getX() + 1) * 40) {
            Log.d(TAG, "Same row");
            if (y <= game.getHowdy().getY() * 40 && y >= (game.getHowdy().getY() - 1) * 40) {
                Log.d(TAG, "Go Up");
                game.moveUp();
                boardView.setGame(game);
                boardView.invalidate();
                movedByProxi = true;
            } else if (y <= (game.getHowdy().getY() + 2) * 40 && y >= (game.getHowdy().getY() + 1) * 40) {
                Log.d(TAG, "Go Down");
                game.moveDown();
                boardView.setGame(game);
                boardView.invalidate();
                movedByProxi = true;
            }
        } else {
            if (y >= game.getHowdy().getY() * 40 && y <= (game.getHowdy().getY() + 1) * 40) {
                Log.d(TAG, "Same column");
                if (x <= game.getHowdy().getX() * 40 && x >= (game.getHowdy().getX() - 1) * 40) {
                    Log.d(TAG, "Go Left");
                    game.moveLeft();
                    boardView.setGame(game);
                    boardView.invalidate();
                    movedByProxi = true;
                } else if (x <= (game.getHowdy().getX() + 2) * 40 && x >= (game.getHowdy().getX() + 1) * 40) {
                    Log.d(TAG, "Go Right");
                    game.moveRight();
                    boardView.setGame(game);
                    boardView.invalidate();
                    movedByProxi = true;
                }
            }
        }

		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				x1 = event.getX();
				y1 = event.getY();
				break;
			case MotionEvent.ACTION_UP:
                if (movedByProxi) {
                    Log.d(TAG, "Proximity move !");
                    movedByProxi = false;
                } else {
                    x2 = event.getX();
                    y2 = event.getY();

                    float deltaX = (x1 - x2);
                    float deltaY = (y1 - y2);

                    Log.d(TAG, "X : " + deltaX + ", Y : " + deltaY);

                    if (Math.abs(deltaX) >= MIN_DISTANCE || Math.abs(deltaY) >= MIN_DISTANCE) {
                        if (Math.abs(deltaX) > Math.abs(deltaY)) { //mvt h
                            if (deltaX < 0) {
                                //Toast.makeText(mainGame.getApplicationContext(),"Go Right",Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "Go Right");
                                game.moveRight();
                            } else {
                                //Toast.makeText(mainGame.getApplicationContext(),"Go Left",Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "Go Left");
                                game.moveLeft();
                            }
                        } else if (Math.abs(deltaX) < Math.abs(deltaY)) { //mvt v
                            if (deltaY < 0) {
                                //Toast.makeText(mainGame.getApplicationContext(),"Go Down",Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "Go Down");
                                game.moveDown();
                            } else {
                                //Toast.makeText(mainGame.getApplicationContext(),"Go Up",Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "Go Up");
                                game.moveUp();
                            }
                        }

                        boardView.setGame(game);
                        boardView.invalidate();
                    }
                }
                
                if (game.isLost()) {
                    mainGame.loseGame();
                }

                if (game.isWon()) {
                    mainGame.winGame();
                }
				break;
		}

		return true;
	}
}
