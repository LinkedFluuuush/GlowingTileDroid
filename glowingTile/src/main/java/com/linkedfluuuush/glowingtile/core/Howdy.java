package core;

public class Howdy {
    private int x, y;
    private static Howdy howdy;

    public static Howdy getHowdy(){
        if(howdy == null){
            howdy = new Howdy();
        }

        return howdy;
    }

    private Howdy(){}

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

    public void setPosition(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    public void moveHorizontally(int x){
        if(this.getX() + x >= 0){
            this.setX(this.getX() + x);
        }
    }

    public void moveVertically(int y){
        if(this.getY() + y >= 0){
            this.setY(this.getY() + y);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Howdy)) return false;

        Howdy howdy = (Howdy) o;

        return (getX() == howdy.getX()) && (getY() == howdy.getY());

    }

    @Override
    public int hashCode() {
        int result = getX();
        result = 31 * result + getY();
        return result;
    }

    @Override
    public String
    toString() {
        return "Howdy{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
