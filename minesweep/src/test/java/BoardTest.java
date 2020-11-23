import org.junit.Test;
import static org.junit.Assert.*;

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

}