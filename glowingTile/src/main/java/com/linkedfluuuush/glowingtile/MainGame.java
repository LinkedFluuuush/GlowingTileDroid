package com.linkedfluuuush.glowingtile;


import android.app.*;
import android.content.*;
import android.content.res.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import com.linkedfluuuush.glowingtile.core.*;
import com.linkedfluuuush.glowingtile.gui.*;
import com.linkedfluuuush.glowingtile.gui.touchListeners.*;
import java.io.*;
import java.util.*;
import android.util.*;


public class MainGame extends Activity {
    private static final String TAG = MainGame.class.getName();

    private Game game;
	private int level = 5;
    private List<String> allDoneLevels;

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

        allDoneLevels = new ArrayList<>();
        AssetManager am = getResources().getAssets();

        final GameBoard boardView = (GameBoard) this.findViewById(R.id.gameBoard);
        final HowdyShadeView howdyShadeView = (HowdyShadeView) this.findViewById(R.id.howdyShadeView);
        final GlowingBoard glowingBoard = (GlowingBoard) this.findViewById(R.id.glowingBoard);
        final HowdyView howdyView = (HowdyView) this.findViewById(R.id.howdyView);
        this.game = new Game();

        boardView.setHowdyShadeView(howdyShadeView);
        boardView.setGlowingBoard(glowingBoard);
        boardView.setHowdyView(howdyView);
        boardView.setOnTouchListener(new BoardGameTouchListener(this));
    }

	@Override
	protected void onStart()
	{
		SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
		
		this.level = prefs.getInt("level", 0);
		String mapJson = prefs.getString("map", null);
		
		if(mapJson == null){
			this.nextLevel();
		} else {
			this.game.initGame(mapJson);
			String howdyPosition = prefs.getString("howdy", null);
			
			if(howdyPosition != null){
				this.game.setHowdyPosition(howdyPosition);
			}
            
            Toast.makeText(this, this.level + "", Toast.LENGTH_SHORT).show();
		}
        
        String allDoneLevelsString = prefs.getString("doneLevels", null);
        
        if(allDoneLevelsString != null){
            allDoneLevels = Arrays.asList(allDoneLevelsString.split(","));
        }
        
        ((GameBoard) findViewById(R.id.gameBoard)).setGame(this.game);
        findViewById(R.id.gameBoard).invalidate();
        ((GameBoard) findViewById(R.id.gameBoard)).getHowdyShadeView().invalidate();

		super.onStart();
	}

	@Override
	protected void onStop()
	{
		SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		
		editor.putInt("level", this.level);
		editor.putString("map", game.getLabyrinth().getJsonMap());
		editor.putString("howdy", game.getJsonHowdy());
        
        String allDoneLevelsString = "";
        
        for(String s : allDoneLevels){
            allDoneLevelsString += s + ",";
        }
        
        if(allDoneLevelsString.length() > 0){
            editor.putString("doneLevels", allDoneLevelsString.substring(0, allDoneLevelsString.length() - 1));
        }
		
		editor.commit();
		
		super.onStop();
	}
	
	public void loseGame(){
		final GameBoard boardView = (GameBoard) this.findViewById(R.id.gameBoard);

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
            List<String> allTutoLevels = new LinkedList<String>(Arrays.asList(am.list("levels/tutorial")));
            
            //if(addMsg){
            //    allTutoLevels.addAll(Arrays.asList(am.list("msg")));
            //}
            
            Log.d(TAG, allTutoLevels.toString());

            for(String name : allTutoLevels){
                if(name.startsWith(this.level + ".")){
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
            
            List<String> allLevels = new ArrayList<>();
            
            try {
                allLevels = Arrays.asList(am.list("levels"));
            } catch (IOException e) {}

            if(r.nextBoolean() && !allLevels.isEmpty() && allLevels.size() != allDoneLevels.size()){
                try {
                    int nLevel;
                    do{
                        nLevel = r.nextInt(allLevels.size());
                    } while (allDoneLevels.contains(allLevels.get(nLevel)));
                    
                    String name = allLevels.get(nLevel);
                    BufferedReader br = new BufferedReader(new InputStreamReader(am.open("levels/" + name)));

                    String line;
                    String levelJSON = "";

                    while((line = br.readLine()) != null){
                        levelJSON += line + "\n";
                    }

                    br.close();

                    allDoneLevels.add(name);

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
        boardView.invalidate();
        boardView.getHowdyShadeView().invalidate();
        
        Toast.makeText(this, this.level + "", Toast.LENGTH_SHORT).show();
	}
}
