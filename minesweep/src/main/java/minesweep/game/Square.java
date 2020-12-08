package minesweep.game;

public class Square {

    public boolean isMine = false;
    public boolean isFlagged = false;
    public boolean isRevealed = false;
    public int mineNeighbours = 0;

    public int y = 0;
    public int x = 0;
    public Square(int y, int x) {
        this.y = y;
        this.x = x;
    }

}