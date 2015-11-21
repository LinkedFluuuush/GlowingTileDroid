package core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Random;

public class Game {
    private Labyrinth labyrinth;
    private Howdy howdy;

    private static Logger LOGGER = LogManager.getLogger(Game.class);

    public Game(Labyrinth labyrinth, Howdy howdy) {
        this.labyrinth = labyrinth;
        this.howdy = howdy;
    }

    public Game() {
        this.labyrinth = null;
        this.howdy = null;
    }

    public Game initGame(int lvl, int maxWidth, int maxHeight){
        LOGGER.debug("Generating labyrinth...");
        this.setLabyrinth(Labyrinth.generateLabyrinth(lvl, maxWidth, maxHeight));
        this.setHowdy(Howdy.getHowdy());

        LOGGER.debug("Setting Howdy");
        for(Tile t : this.getLabyrinth().getTiles()){
            if(t.getType().equals(Tile.Type.DEPART)){
                this.getHowdy().setPosition(t.getX(), t.getY());
            }
        }
        LOGGER.debug("Howdy set to (" + this.getHowdy().getX() + ", " + this.getHowdy().getY() + ")");


        return this;
    }

    public Game initGame(String lvlName){
        try {
            LOGGER.debug("Loading labyrinth " + lvlName + "...");
            this.setLabyrinth(Labyrinth.loadLabyrinth(lvlName));
            this.setHowdy(Howdy.getHowdy());

            LOGGER.debug("Setting Howdy");
            for(Tile t : this.getLabyrinth().getTiles()){
                if(t.getType().equals(Tile.Type.DEPART)){
                    this.getHowdy().setPosition(t.getX(), t.getY());
                }
            }
            LOGGER.debug("Howdy set to (" + this.getHowdy().getX() + ", " + this.getHowdy().getY() + ")");
        } catch (IOException e) {
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
        LOGGER.debug("Re-initiating...");
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
