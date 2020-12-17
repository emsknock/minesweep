import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import minesweep.game.Board;

public class BoardTest {
    
    Board board;
    @Before
    public void initialise() {
        board = new Board(256, 256);
    }

    @Test
    public void isInBoundsReturnsTrueInBounds() {
        for (int y = 0; y < 256; y++) {
            for (int x = 0; x < 256; x++) {
                assertTrue(board.isInBounds(y, x));
            }
        }
    }

    @Test
    public void isInBoundsReturnsFalseOutOfBounds() {
        // Underflow:
        assertFalse(board.isInBounds(-1, -1));
        assertFalse(board.isInBounds(-1, 0));
        assertFalse(board.isInBounds(0, -1));
        // Overflow:
        assertFalse(board.isInBounds(256, 256));
        assertFalse(board.isInBounds(256, 0));
        assertFalse(board.isInBounds(0, 256));
    }

    @Test
    public void getSquareReturnsCorrectSquare() {
        for (int y = 0; y < 256; y++) {
            for (int x = 0; x < 256; x++) {
                assertTrue(board.getSquare(y, x).y == y);
                assertTrue(board.getSquare(y, x).x == x);
            }
        }
    }

    @Test
    public void getNeighboursReturnsEightElementsWhenInMiddleOfBoard() {
        assertEquals(8, board.getNeighbours(128, 128).size());
    }

    @Test
    public void getNeighboursReturnsThreeElementsWhenInCorner() {
        assertEquals(3, board.getNeighbours(0, 0).size());
        assertEquals(3, board.getNeighbours(0, 255).size());
        assertEquals(3, board.getNeighbours(255, 0).size());
        assertEquals(3, board.getNeighbours(255, 255).size());
    }

    @Test
    public void numFlaggedNeighboursIsZeroWhenNoNeighboursAreFlagged() {
        assertEquals(0, board.numFlaggedNeighbours(board.getSquare(128, 128)));
    }

    @Test
    public void numFlaggedNeighboursIsEightWhenAllNeighboursAreFlagged() {
        board.getNeighbours(128, 128)
            .stream()
            .forEach(neighbour -> neighbour.isFlagged = true);
        assertEquals(8, board.numFlaggedNeighbours(board.getSquare(128, 128)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsWhenGivenUnderOneHeight() {
        new Board(0, 256);
    }
    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsWhenGivenUnderOneWidth() {
        new Board(256, 0);
    }
    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsWhenGivenUnderOneHeightAndWidth() {
        new Board(0, 0);
    }
    

}
