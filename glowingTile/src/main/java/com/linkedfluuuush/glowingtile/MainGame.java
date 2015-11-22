package com.linkedfluuuush.glowingtile;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.linkedfluuuush.glowingtile.core.Game;
import com.linkedfluuuush.glowingtile.core.Labyrinth;
import com.linkedfluuuush.glowingtile.gui.GameBoard;


public class MainGame extends Activity {
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_game);

        GameBoard boardView = (GameBoard) this.findViewById(R.id.gameBoard);
        this.game = new Game();
        this.game.initGame(1, 10, 10);
        boardView.setGame(this.game);
    }
}
