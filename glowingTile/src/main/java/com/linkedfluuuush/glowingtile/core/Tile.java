package com.linkedfluuuush.glowingtile.core;

public class Tile {
    private int x, y;


    public enum Type {
        DEPART,
        ARRIVEE,
        NEUTRE,
        VIDE
    }

    public enum Etat {
        NEUF,
        USE,
        CASSE
    }

    private Type type;
    private Etat etat;

    public Tile(int x, int y, Type type) {
        this.x = x;
        this.y = y;
        this.type = type;

        if(type.equals(Type.DEPART)){
            this.etat = Etat.USE;
        } else {
            this.etat = Etat.NEUF;
        }
    }

    public Tile(int x, int y, String type) {
        this(x, y, Type.NEUTRE);
        if (type.equals("D")) {
            this.setType(Type.DEPART);
            this.setEtat(Etat.USE);
        } else if (type.equals("N")) {
            this.setType(Type.NEUTRE);
        } else if (type.equals("A")) {
            this.setType(Type.ARRIVEE);
        } else if (type.equals("V")) {
            this.setType(Type.VIDE);
        } else if (type.equals("U")) {
            this.setType(Type.NEUTRE);
            this.setEtat(Etat.USE);
        } else {
            this.setType(Type.VIDE);
        }
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public void use() {
        switch(this.getEtat()){
            case NEUF:
                this.setEtat(Etat.USE);
                break;
            case USE:
                this.setEtat(Etat.CASSE);
                break;
            case CASSE:
                break;
            default:
                break;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tile)) return false;

        Tile tile = (Tile) o;

        return (getX() != tile.getX()) && (getY() == tile.getY());
    }

    @Override
    public int hashCode() {
        int result = getX();
        result = 31 * result + getY();
        return result;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "x=" + x +
                ", y=" + y +
                ", type=" + type +
                ", etat=" + etat +
                '}';
    }
}
