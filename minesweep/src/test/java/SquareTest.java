import static org.junit.Assert.assertTrue;

import org.junit.Test;

import minesweep.game.Square;

public class SquareTest {
    
    /*
        Serialised squares are represented as 7 bits as follows:
        | 1 bit (64's) | 1 bit (32's) | 1 bit (16's) | 4 bits (8's – 1's) |
        | isRevealed   | isFlagged    | isMine       | mineNeighbours     |
    */

    private int getMineNeighboursField(byte serialised) {
        return serialised & 0b0001111;
    }
    private boolean getRevealedField(byte serialised) {
        return (serialised & 0b1000000) >> 6 == 1;
    }
    private boolean getFlaggedField(byte serialised) {
        return (serialised & 0b0100000) >> 5 == 1;
    }
    private boolean getMineField(byte serialised) {
        return (serialised & 0b0010000) >> 4 == 1;
    }

    @Test
    public void mineStatusIsSerialisedCorrectly() {
        for (int mineNeighbours = 0; mineNeighbours < 9; mineNeighbours++) {
            Square s = new Square(0, 0);
            s.mineNeighbours = mineNeighbours;
            s.isMine = true;
            byte serialised = s.serialise();
            assertTrue(getMineNeighboursField(serialised) == mineNeighbours);
            assertTrue(getMineField(serialised));
        }
    }
    @Test
    public void flaggedStatusIsSerialisedCorrectly() {
        for (int mineNeighbours = 0; mineNeighbours < 9; mineNeighbours++) {
            Square s = new Square(0, 0);
            s.mineNeighbours = mineNeighbours;
            s.isFlagged= true;
            byte serialised = s.serialise();
            assertTrue(getMineNeighboursField(serialised) == mineNeighbours);
            assertTrue(getFlaggedField(serialised));
        }
    }
    @Test
    public void revealedStatusIsSerialisedCorrectly() {
        for (int mineNeighbours = 0; mineNeighbours < 9; mineNeighbours++) {
            Square s = new Square(0, 0);
            s.mineNeighbours = mineNeighbours;
            s.isRevealed = true;
            byte serialised = s.serialise();
            assertTrue(getMineNeighboursField(serialised) == mineNeighbours);
            assertTrue(getRevealedField(serialised));
        }
    }

    @Test
    public void mineNeighbourCountIsDeserialisedCorrectly() {
        for (int mineNeighbours = 0; mineNeighbours < 9; mineNeighbours++) {
            assertTrue(Square.deserialise((byte) mineNeighbours, 0, 0).mineNeighbours == mineNeighbours);
        }
    }

    @Test
    public void mineStatusIsDeserialisedCorrectly() {
        for (int mineNeighbours = 0; mineNeighbours < 9; mineNeighbours++) {
            Square d = Square.deserialise((byte) (0b0010000 | mineNeighbours), 0, 0);
            assertTrue(d.isMine);
            assertTrue(d.mineNeighbours == mineNeighbours);
        }
    }

    @Test
    public void flaggedStatusIsDeserialisedCorrectly() {
        for (int mineNeighbours = 0; mineNeighbours < 9; mineNeighbours++) {
            Square d = Square.deserialise((byte) (0b0100000 | mineNeighbours), 0, 0);
            assertTrue(d.isFlagged);
            assertTrue(d.mineNeighbours == mineNeighbours);
        }
    }

    @Test
    public void revealedStatusIsDeserialisedCorrectly() {
        for (int mineNeighbours = 0; mineNeighbours < 9; mineNeighbours++) {
            Square d = Square.deserialise((byte) (0b1000000 | mineNeighbours), 0, 0);
            assertTrue(d.isRevealed);
            assertTrue(d.mineNeighbours == mineNeighbours);
        }
    }

}
