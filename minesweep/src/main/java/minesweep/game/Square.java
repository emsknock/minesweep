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

    /**
     * Returns a serialised byte-representation of this Square that
     * can be deserialised back to an equal instance with the deserialise
     * method. @see Square#deserialise(byte, int, int)
     * @return A byte representation of this Square
     */
    public byte serialise() {
        return (byte) (
            (isRevealed ? 1 : 0) << 6 |
            (isFlagged ? 1 : 0) << 5 |
            (isMine ? 1 : 0) << 4 |
            mineNeighbours
            ); // Checkstyle wants this indentation, I don't like it
    }

    /**
     * Creates a Square instance based on a serialised byte-representation
     * of a Square. @see Square#deserialise()
     * @param serialised The serialised Square data
     * @param y The Square's original Y coordinate in the grid
     * @param x The Square's original X coordinate in the grid
     * @return The deserialised Square instance
     */
    public static Square deserialise(byte serialised, int y, int x) {
        Square out = new Square(y, x);
        out.isMine = ((serialised >> 4) & 1) == 1;
        out.isFlagged = ((serialised >> 5) & 1) == 1;
        out.isRevealed = ((serialised >> 6) & 1) == 1;
        out.mineNeighbours = serialised & 0b1111;
        return out;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Square)) {
            return false;
        }
        Square c = (Square) o;
        return (
            c.isMine == isMine &&
            c.isFlagged == isFlagged &&
            c.isRevealed == isRevealed &&
            c.mineNeighbours == mineNeighbours &&
            c.y == y &&
            c.x == x
            ); // Checkstyle wants this indentation, I don't like it
    }

}