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

}