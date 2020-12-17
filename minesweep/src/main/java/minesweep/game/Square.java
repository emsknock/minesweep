package minesweep.game;

public class Square {

    public boolean isRevealed = false;
    public boolean isFlagged = false;
    public boolean isMine = false;
    public int mineNeighbours = 0;

    public int y = 0;
    public int x = 0;

    public Square(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public byte serialise() {
        return (byte) (
            (isRevealed ? 1 : 0) << 6 |
            (isFlagged ? 1 : 0) << 5 |
            (isMine ? 1 : 0) << 4 |
            mineNeighbours
        );
    }

    public static Square deserialise(byte serialised, int y, int x) {
        Square out = new Square(y, x);
        out.isMine = ((serialised >> 4) & 1) == 1;
        out.isFlagged = ((serialised >> 5) & 1) == 1;
        out.isRevealed = ((serialised >> 6) & 1) == 1;
        out.mineNeighbours = serialised & 0b1111;
        return out;
    }

}