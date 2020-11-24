import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

import minesweep.game.*;

public class BoardTest {

    @Test
    public void createdBoardIsCorrectSize() {
        Board _b = new Board(256, 128, 64, 1L);
        Square[][] grid = _b.getGrid();
        assertEquals(256, grid.length);
        for(Square[] row : grid) {
            assertEquals(128, row.length);
        }
    }


    private int getMineCount(Board b) {
        Square[][] grid = b.getGrid();
        int mineCount = 0;
        for (Square[] row : grid) {
            for (Square col : row) {
                mineCount += col.isMine ? 1 : 0;
            }
        }
        return mineCount;
    }
    @Test
    public void createdBoardHasCorrectNumOfMines() {
        Board[] _b = {
            new Board(256, 128, 8, 1L),
            new Board(256, 128, 4, 1L),
            new Board(256, 128, 2, 1L),
            new Board(256, 128, 1, 1L),
        };
        assertEquals(8, getMineCount(_b[0]));
        assertEquals(4, getMineCount(_b[1]));
        assertEquals(2, getMineCount(_b[2]));
        assertEquals(1, getMineCount(_b[3]));
    }

    @Test
    public void isInBoundsMethodReturnsFalseWhenOutOfBounds() {
        Board _b = new Board(256, 256, 32, 1L);
        assertFalse(_b.isInBounds(-1, -1));
        assertFalse(_b.isInBounds(256, 256));
        assertFalse(_b.isInBounds(0, 256));
        assertFalse(_b.isInBounds(256, 0));
    }

    @Test
    public void isInBoundsMethodReturnsTrueWhenInBounds() {
        Board _b = new Board(256, 256, 32, 1L);
        assertTrue(_b.isInBounds(0, 0));
        assertTrue(_b.isInBounds(255, 255));
        assertTrue(_b.isInBounds(127, 127));
    }

    @Test
    public void getNeighboursReturnsThreeSquaresWhenInCorner() {
        Board _b = new Board(256, 256, 32, 1L);
        assertEquals(3, _b.getNeighbours(0, 0).size());
        assertEquals(3, _b.getNeighbours(255, 0).size());
        assertEquals(3, _b.getNeighbours(0, 255).size());
        assertEquals(3, _b.getNeighbours(255, 255).size());
    }

    @Test
    public void getNeighboursReturnsEightSquaresWhenInMiddleOfGrid() {
        Board _b = new Board(256, 256, 32, 1L);
        assertEquals(8, _b.getNeighbours(127, 127).size());
    }

    @Test
    public void mineNeighboursCalculatedCorrectly() {
        Board _b = new Board(256, 256, 32, 1L);
        Square[][] _g = _b.getGrid();
        for (int y = 0; y < 256; y++) {
            for (int x = 0; x < 256; x++) {
                Square square = _g[y][x];
                ArrayList<Square> neighbours = _b.getNeighbours(y, x);
                int mines = 0;
                for (Square n : neighbours) {
                    mines += n.isMine ? 1 : 0;
                }
                assertEquals(mines, square.mineNeighbours);
            }
        }
    }

}