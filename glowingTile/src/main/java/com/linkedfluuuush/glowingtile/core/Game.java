package com.linkedfluuuush.glowingtile.core;

import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.Random;
import java.io.*;
import org.json.*;

public class Game {
    private Labyrinth labyrinth;
    private Howdy howdy;

    private static String TAG = Game.class.getName();

    public Game(Labyrinth labyrinth, Howdy howdy) {
        this.labyrinth = labyrinth;
        this.howdy = howdy;
    }

    public Game() {
        this.labyrinth = null;
        this.howdy = null;
    }

	public void setHowdyPosition(String howdyPosition)
	{
		try
		{
			JSONObject hPos = new JSONObject(howdyPosition);
			this.getHowdy().setPosition((Integer) hPos.get("x"), (Integer) hPos.get("y"));
		}
		catch (JSONException e)
		{}
	}

	public String getJsonHowdy()
	{
		String jSon = "{\"x\":" + this.getHowdy().getX();
		jSon += ",\"y\":" + this.getHowdy().getY();
		
		jSon += "}";
		return jSon;
	}

    public Game initGame(int lvl, int maxWidth, int maxHeight){
        Log.d(TAG, "Generating labyrinth...");
        this.setLabyrinth(Labyrinth.generateLabyrinth(lvl, maxWidth, maxHeight));
        this.setHowdy(Howdy.getHowdy());

        Log.d(TAG, "Setting Howdy");
        for(Tile t : this.getLabyrinth().getTiles()){
            if(t.getType().equals(Tile.Type.DEPART)){
                this.getHowdy().setPosition(t.getX(), t.getY());
            }
        }
        Log.d(TAG, "Howdy set to (" + this.getHowdy().getX() + ", " + this.getHowdy().getY() + ")");


        return this;
    }

    public Game initGame(String mapJSONSource){
        try {
            //Log.d(TAG, "Loading labyrinth " + lvlName + "...");
            this.setLabyrinth(Labyrinth.loadLabyrinth(mapJSONSource));
            this.setHowdy(Howdy.getHowdy());

            Log.d(TAG, "Setting Howdy");
            for(Tile t : this.getLabyrinth().getTiles()){
                if(t.getType().equals(Tile.Type.DEPART)){
                    this.getHowdy().setPosition(t.getX(), t.getY());
                }
            }
            Log.d(TAG, "Howdy set to (" + this.getHowdy().getX() + ", " + this.getHowdy().getY() + ")");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return this;
    }

    public boolean moveUp(){
        if(this.getLabyrinth().existsTile(this.getHowdy().getX(), this.getHowdy().getY() - 1)) {
            this.getHowdy().moveVertically(-1);
            this.getLabyrinth().useTile(this.getHowdy().getX(), this.getHowdy().getY());
            return true;
        }

        return false;
    }

    public boolean moveDown(){
        if(this.getLabyrinth().existsTile(this.getHowdy().getX(), this.getHowdy().getY() + 1)) {
            this.getHowdy().moveVertically(1);
            this.getLabyrinth().useTile(this.getHowdy().getX(), this.getHowdy().getY());
            return true;
        }

        return false;
    }

    public boolean moveLeft(){
        if(this.getLabyrinth().existsTile(this.getHowdy().getX() - 1, this.getHowdy().getY())) {
            this.getHowdy().moveHorizontally(-1);
            this.getLabyrinth().useTile(this.getHowdy().getX(), this.getHowdy().getY());
            return true;
        }

        return false;
    }

    public boolean moveRight(){
        if(this.getLabyrinth().existsTile(this.getHowdy().getX() + 1, this.getHowdy().getY())) {
            this.getHowdy().moveHorizontally(1);
            this.getLabyrinth().useTile(this.getHowdy().getX(), this.getHowdy().getY());
            return true;
        }

        return false;
    }

    public boolean isWon(){
        return this.getLabyrinth().detectWin(this.getHowdy());
    }

    public boolean isLost(){
        return this.getLabyrinth().isLost();
    }

    public Labyrinth getLabyrinth() {
        return labyrinth;
    }

    public void setLabyrinth(Labyrinth labyrinth) {
        this.labyrinth = labyrinth;
    }

    public Howdy getHowdy() {
        return howdy;
    }

    public void setHowdy(Howdy howdy) {
        this.howdy = howdy;
    }

    public void reInitGame() {
        Log.d(TAG, "Re-initiating...");
        for(Tile t : this.getLabyrinth().getTiles()){
            if(t.getType().equals(Tile.Type.DEPART)){
                this.getHowdy().setPosition(t.getX(), t.getY());
                t.setEtat(Tile.Etat.USE);
            } else {
                t.setEtat(Tile.Etat.NEUF);
            }
        }
    }
}
