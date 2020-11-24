package minesweep;
import minesweep.game.Board;

public class Main {

    public static void main(String[] args) {
        Board b = new Board(32, 64, 128, 1L);
        System.out.println(b.toString());
    }
    
}
