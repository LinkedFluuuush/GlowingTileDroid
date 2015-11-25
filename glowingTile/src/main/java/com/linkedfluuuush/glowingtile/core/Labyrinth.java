package com.linkedfluuuush.glowingtile.core;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.*;
import java.util.LinkedList;
import java.util.Random;

public class Labyrinth {
    private static String TAG = Game.class.getName();

    private LinkedList<Tile> tiles;

    public Labyrinth(LinkedList<Tile> tiles) {
        this.tiles = tiles;
    }

	public String getJsonMap()
	{
		String jSon = "[";
		
		for(Tile t : this.getTiles()){
			jSon += "{\"x\":" + t.getX() + ",";
			jSon += "\"y\":" + t.getY() + ",";
			jSon += "\"t\":\"";
			
			if(t.getEtat() == Tile.Etat.USE){
				if(t.getType() == Tile.Type.ARRIVEE){
					jSon += "AU";
				} else if(t.getType() == Tile.Type.DEPART){
					jSon += "D";
				} else {
					jSon += "U";
				}
			} else {
				switch(t.getType()){
					case DEPART:
						jSon += "D";
						break;
					case ARRIVEE:
						jSon += "A";
						break;
					case NEUTRE:
						jSon += "N";
						break;
					case VIDE:
						jSon += "V";
						break;
				}
			}
			
			jSon += "\"";
			jSon += "},";
		}
		
		jSon = jSon.substring(0, jSon.length()-1);
		
		jSon += "]";
		
		Log.v(TAG, "Generated Json : " + jSon);
		
		return jSon;
	}

    public LinkedList<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(LinkedList<Tile> tiles) {
        this.tiles = tiles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Labyrinth)) return false;

        Labyrinth labyrinth = (Labyrinth) o;

        return !(getTiles() != null ? !getTiles().equals(labyrinth.getTiles()) : labyrinth.getTiles() != null);

    }

    @Override
    public int hashCode() {
        return getTiles() != null ? getTiles().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Labyrinth{" +
                "tiles=" + tiles +
                '}';
    }

    public boolean useTile(int x, int y){
        for(Tile t : this.getTiles()){
            if(t.getX() == x && t.getY() == y){
                return useTile(t);
            }
        }

        return true;
    }

    public boolean useTile(Tile tile){
        tile.use();
        return tile.getEtat().equals(Tile.Etat.CASSE);
    }

    public static Labyrinth generateLabyrinth(int lvl, int maxWidth, int maxHeight){
        Log.d(TAG, "Generating Labyrinth with params lvl = " + lvl + ", maxWidth = " + maxWidth + ", mawHeight = " + maxHeight);
        if(maxHeight > 0 && maxWidth > 0) {
            Random r = new Random();
            Tile tileDepart = new Tile(r.nextInt(maxWidth), r.nextInt(maxHeight), Tile.Type.DEPART);
            LinkedList<Tile> tiles = new LinkedList<>();
            tiles.add(tileDepart);

            LinkedList<Tile> newTiles = generateStep(tiles, lvl, maxWidth, maxHeight);

            while (newTiles == null) {
                newTiles = generateStep(tiles, lvl, maxWidth, maxHeight);
            }

            newTiles.getLast().setType(Tile.Type.ARRIVEE);
            Log.d(TAG, newTiles.toString());


            return new Labyrinth(newTiles);
        } else {
            Log.e(TAG, "Wrong params for generation ! maxWidth = " + maxWidth + ", mawHeight = " + maxHeight);
            return null;
        }
    }

    private static LinkedList<Tile> generateStep(LinkedList<Tile> tiles, int lvl, int maxWidth, int maxHeight){
        Random r = new Random();
        Log.d(TAG, "Current step : " + tiles.size());

        boolean notValid = true;
        int stepDirInit = r.nextInt(4) + 1;
        int stepDir = stepDirInit;

        int nextStepX = tiles.getLast().getX();
        int nextStepY = tiles.getLast().getY();

        boolean oneStep = false;

        while(notValid){
            switch(stepDir){
                case 1:
                    nextStepX = tiles.getLast().getX() - 1;
                    nextStepY = tiles.getLast().getY();
                    break;
                case 2:
                    nextStepX = tiles.getLast().getX();
                    nextStepY = tiles.getLast().getY() - 1;
                    break;
                case 3:
                    nextStepX = tiles.getLast().getX() + 1;
                    nextStepY = tiles.getLast().getY();
                    break;
                case 4:
                    nextStepX = tiles.getLast().getX();
                    nextStepY = tiles.getLast().getY() + 1;
                    break;
            }

            notValid = false;

            if(nextStepX < 0 || nextStepX > maxWidth || nextStepY < 0 || nextStepY > maxHeight){
                if (stepDir != stepDirInit || !oneStep) {
                    oneStep = true;
                    notValid = true;
                    stepDir = (stepDir % 4) + 1;
                } else {
                    if (tiles.size() >= 5 * lvl) {
                        return tiles;
                    } else {
                        return null;
                    }
                }
            } else {
                for(Tile t : tiles){
                    if(nextStepX == t.getX() && nextStepY == t.getY()){
                        if (stepDir != stepDirInit || !oneStep) {
                            oneStep = true;
                            notValid = true;
                            stepDir = (stepDir % 4) + 1;
                            break;
                        } else {
                            if (tiles.size() >= 5 * lvl) {
                                return tiles;
                            } else {
                                return null;
                            }
                        }
                    }
                }
            }

            if(!notValid){
                tiles.add(new Tile(nextStepX, nextStepY, Tile.Type.NEUTRE));

                LinkedList<Tile> nextTiles;

                nextTiles = generateStep((LinkedList<Tile>) tiles.clone(), lvl, maxWidth, maxHeight);

                if(nextTiles != null){
                    return nextTiles;
                } else {
                    if (stepDir != stepDirInit || !oneStep) {
                        oneStep = true;
                        notValid = true;
                        stepDir = (stepDir % 4) + 1;
                    } else {
                        if (tiles.size() >= 5 * lvl) {
                            return tiles;
                        } else {
                            return null;
                        }
                    }
                }
            }
        }

        return null;
    }

    public boolean detectWin(Howdy howdy){
        for(Tile t : this.getTiles()){
            if(!t.getEtat().equals(Tile.Etat.USE)){
                return false;
            }

            if(t.getType().equals(Tile.Type.ARRIVEE)){
                if(howdy.getX() != t.getX() || howdy.getY() != t.getY()){
                    return false;
                }
            }
        }

        return true;
    }

    public boolean existsTile(int x, int y) {
        for(Tile t : this.getTiles()){
            if(t.getX() == x && t.getY() == y){
                return true;
            }
        }

        return false;
    }

    public boolean isLost() {
        for(Tile t : this.getTiles()){
            if(t.getEtat().equals(Tile.Etat.CASSE)){
                return true;
            }
        }

        return false;
    }

    public static Labyrinth loadLabyrinth(String mapJSONSource) throws IOException, JSONException {
        LinkedList<Tile> tiles = new LinkedList<>();

        JSONArray tileArray = new JSONArray(mapJSONSource);

        for(int i = 0 ; i < tileArray.length() ; i++){
            tiles.add(new Tile(tileArray.getJSONObject(i).getInt("x"),
                    tileArray.getJSONObject(i).getInt("y"),
                    tileArray.getJSONObject(i).getString("t")));
        }

        return new Labyrinth(tiles);
    }
}
