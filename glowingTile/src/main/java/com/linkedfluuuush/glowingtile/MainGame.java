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
import com.linkedfluuuush.glowingtile.gui.touchListeners.*;
import android.content.*;


public class MainGame extends Activity {
    private static final String TAG = MainGame.class.getName();

    private Game game;
	private int level = 0;

	public Game getGame(){
		return game;
	}


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
        nextLevel();

        boardView.setOnTouchListener(new BoardGameTouchListener(this));
    }

	@Override
	protected void onResume()
	{
		SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
		
		this.level = prefs.getInt("level", 0);
		
		super.onResume();
	}

	@Override
	protected void onPause()
	{
		SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		
		editor.putInt("level", this.level);
		editor.putString("map", game.getLabyrinth().getJsonMap());
		editor.putString("howdy", game.getJsonHowdy());
		
		editor.commit();
		
		super.onPause();
	}
	
	public void loseGame(){
		final GameBoard boardView = (GameBoard) this.findViewById(R.id.gameBoard);
		
		boardView.killHowdy();
		
		game.reInitGame();
		
		boardView.setGame(game);
		boardView.invalidate();
	}
	
	public void winGame(){
		nextLevel();
	}
	
	public void nextLevel(){
		final GameBoard boardView = (GameBoard) this.findViewById(R.id.gameBoard);
		this.level++;
		
		this.game.initGame(level, boardView.getWidth() / 60, boardView.getHeight() / 60);
        boardView.setGame(this.game);
	}
}
