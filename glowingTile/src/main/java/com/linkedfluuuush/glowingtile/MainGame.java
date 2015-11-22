package com.linkedfluuuush.glowingtile;


import android.app.Activity;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.linkedfluuuush.glowingtile.core.Game;
import com.linkedfluuuush.glowingtile.gui.GameBoard;


public class MainGame extends Activity {
    private static final String TAG = MainGame.class.getName();

    private Game game;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_game);

        final GameBoard boardView = (GameBoard) this.findViewById(R.id.gameBoard);
        this.game = new Game();
        this.game.initGame(1, 10, 10);
        boardView.setGame(this.game);

        boardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                MotionEvent.PointerCoords coords = new MotionEvent.PointerCoords();
                event.getPointerCoords(0, coords);

                float deltaX = Math.abs(event.getX() - (game.getHowdy().getX() * 40) + 10);
                float deltaY = Math.abs(event.getY() - (game.getHowdy().getY() * 40) + 10);

                Log.d(TAG, "Deltas : X = " + deltaX + " (" + event.getX() + "-" +
                        ((game.getHowdy().getX() * 40) + 10) + "), Y = " + deltaY +
                        " (" + event.getY() + "-" + ((game.getHowdy().getY() * 40) + 10) + ")");

                if(deltaX > deltaY){ /* On considère que l'on a appuyé pour un mouvement horizontal (plus proche de l'axe horizontal de Howdy) */
                    if(event.getX() > (game.getHowdy().getX() * 40) + 10){
                        Log.d(TAG, "Moving right");
                        game.moveRight();
                    } else if(event.getX() < (game.getHowdy().getX() * 40) + 10) {
                        Log.d(TAG, "Moving left");
                        game.moveLeft();
                    }
                } else if(deltaX < deltaY) { /* On considère que l'on a appuyé pour un mouvement vertical (plus proche de l'axe vertical de Howdy) */
                    if(event.getY() > (game.getHowdy().getY() * 40) + 10){
                        Log.d(TAG, "Moving down");
                        game.moveDown();
                    } else if(event.getY() < (game.getHowdy().getY() * 40) + 10) {
                        Log.d(TAG, "Moving up");
                        game.moveUp();
                    }
                }

                /* Dans le cas ou les deltas sont égaux, ou le cas ou le joueur appuie précisément sur Howdy, on ne fait rien par indécision. */

                boardView.setGame(game);
                boardView.invalidate();

                return true;
            }
        });
    }
}
