package object;
import java.util.*;
import java.io.*;

public class Piece {
    char[][] shape;
    char aid;
    boolean used;

    public Piece(char aid,char[][] shape) {
        this.shape= shape;
        this.aid= aid;
        this.used= false;
    }

    public char getaid() {
        return aid;
    }

    public char[][] getShape() {
        return shape;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used= used;
    }
}
