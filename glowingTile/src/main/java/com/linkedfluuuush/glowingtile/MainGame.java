package com.linkedfluuuush.glowingtile;


import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.linkedfluuuush.glowingtile.core.Game;
import com.linkedfluuuush.glowingtile.gui.GameBoard;
import com.linkedfluuuush.glowingtile.gui.touchListeners.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class MainGame extends Activity {
    private static final String TAG = MainGame.class.getName();

    private Game game;
	private int level = 5;
    private List<String> allLevels;

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

        allLevels = new ArrayList<>();
        AssetManager am = getResources().getAssets();

        try {
            allLevels = Arrays.asList(am.list("levels"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        final GameBoard boardView = (GameBoard) this.findViewById(R.id.gameBoard);
        this.game = new Game();
        nextLevel();

        boardView.setOnTouchListener(new BoardGameTouchListener(this));
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
        boolean loaded = false;
		final GameBoard boardView = (GameBoard) this.findViewById(R.id.gameBoard);
		this.level++;

        AssetManager am = getResources().getAssets();

        try {
            String[] allTutoLevels = am.list("levels/tutorial");

            for(String name : allTutoLevels){
                if(name.startsWith(this.level + "")){
                    BufferedReader br = new BufferedReader(new InputStreamReader(am.open("levels/tutorial/" + name)));
                    String line;
                    String levelJSON = "";

                    while((line = br.readLine()) != null){
                        levelJSON += line + "\n";
                    }

                    br.close();

                    game.initGame(levelJSON);
                    loaded = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(!loaded) {
            Random r = new Random();

            if(r.nextBoolean() && !allLevels.isEmpty()){
                try {
                    int nLevel = r.nextInt(allLevels.size());
                    String name = allLevels.get(nLevel);
                    BufferedReader br = new BufferedReader(new InputStreamReader(am.open("levels/" + name)));

                    String line;
                    String levelJSON = "";

                    while((line = br.readLine()) != null){
                        levelJSON += line + "\n";
                    }

                    br.close();

                    allLevels.remove(nLevel);

                    game.initGame(levelJSON);
                } catch (IOException e) {
                    this.game.initGame(level, boardView.getWidth() / 60, boardView.getHeight() / 60);
                }
            } else {
                this.game.initGame(level, boardView.getWidth() / 60, boardView.getHeight() / 60);
            }
        }

        //am.close();

        boardView.setGame(this.game);
	}
}
