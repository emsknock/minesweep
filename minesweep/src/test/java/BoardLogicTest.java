import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;


import minesweep.game.BoardLogic;
import minesweep.game.Square;

public class BoardLogicTest {

    /*
        The example game looks like
        0000000000000000
        0000000000011211
        000000000001####
        0000111000011211
        01111#1001110000
        01#1122112#10111
        011101####2101##
        0000011111101221
        0000000000001#10
        0000000000001110
        0000001110000000
        0000001#21100000
        0000001###100000
        0000001###100000
        0111001###111111
        01#1001#########
    */
    BoardLogic exampleGame;
    @Before
    public void createExampleGame() {
        exampleGame = new BoardLogic(16, 16, 16, 1L);
        exampleGame.guess(0, 0);
    }

    @Test
    public void createdBoardIsCorrectSize() {
        for (int height = 1; height < 16; height++) {
            for (int width = 1; width < 16; width++) {

                BoardLogic board = new BoardLogic(height, width, 0, 1L);
                Square[][] grid = board.getRawGrid();
                
                assertEquals(height, grid.length);
                for (Square[] row : grid) {
                    assertEquals(width, row.length);
                }

            }
        }
    }

    @Test
    public void createdBoardHasCorrectNumOfMines() {
        for (int mineCount = 1; mineCount < 16; mineCount++) {

            BoardLogic board = new BoardLogic(16, 16, mineCount, 1L);
            board.guess(0, 0);
            Square[][] grid = board.getRawGrid();

            int foundMines = 0;
            for (Square[] row : grid) {
                for (Square col : row) {
                    if (col.isMine) {
                        foundMines++;
                    }
                }
            }

            assertEquals(mineCount, foundMines);

        }
    }

    private int getRevealedCount(BoardLogic board) {
        int revealedMines = 0;
        for (Square [] row : board.getRawGrid()) {
            for (Square col : row) {
                if (col.isRevealed) {
                    revealedMines++;
                }
            }
        }
        return revealedMines;
    }

    @Test
    public void firstGuessIncreasesAmountOfRevealedMines() {
        assertTrue(null, getRevealedCount(exampleGame) > 0);
    }

    @Test
    public void guessingRevealedSquareWithUnrevealedNeighboursRevealsThoseNeighbours() {
        exampleGame.toggleFlag(5, 10);
        exampleGame.toggleFlag(6, 9);
        int revealedCountBeforeGuess = getRevealedCount(exampleGame);
        exampleGame.guess(5, 9);
        assertTrue(null, revealedCountBeforeGuess < getRevealedCount(exampleGame));
    }

    @Test
    public void guessingMineReturnsTrue() {
        assertTrue(exampleGame.guess(2, 14));
    }

    @Test
    public void guessingMineSetsFlagInBoard() {
        exampleGame.guess(2, 14);
        assertTrue(exampleGame.isGameLost());
    }

    @Test
    public void guessingFlaggedSquareIsIgnored() {
        int revealedSquareCountBeforeGuess = getRevealedCount(exampleGame);
        exampleGame.toggleFlag(2, 14);
        assertFalse(exampleGame.guess(2, 14));
        assertTrue(revealedSquareCountBeforeGuess == getRevealedCount(exampleGame));
    }

    @Test
    public void guessingRevealedZerosIsIgnored() {
        int revealedSquareCountBeforeGuess = getRevealedCount(exampleGame);
        assertFalse(exampleGame.guess(0, 0));
        assertTrue(revealedSquareCountBeforeGuess == getRevealedCount(exampleGame));
    }

    @Test
    public void flaggingRevealedSquareIsIgnored() {
        assertFalse(exampleGame.toggleFlag(0, 0));
    }

    @Test
    public void wonGameIsRecognised() {
        exampleGame.toggleFlag(2, 12);
        exampleGame.toggleFlag(2, 14);
        exampleGame.toggleFlag(4, 5);
        exampleGame.toggleFlag(5, 2);
        exampleGame.toggleFlag(5, 10);
        exampleGame.toggleFlag(6, 6);
        exampleGame.toggleFlag(6, 9);
        exampleGame.toggleFlag(6, 14);
        exampleGame.toggleFlag(8, 13);
        exampleGame.toggleFlag(11, 7);
        exampleGame.toggleFlag(12, 9);
        exampleGame.toggleFlag(14, 7);
        exampleGame.toggleFlag(15, 2);
        exampleGame.toggleFlag(15, 8);
        exampleGame.toggleFlag(15, 11);
        exampleGame.toggleFlag(15, 14);
        assertTrue(exampleGame.isGameWon());
    }

    @Test
    public void gameIsntWonIfMineIsLeftUnflagged() {
        exampleGame.toggleFlag(2, 12);
        exampleGame.toggleFlag(2, 14);
        exampleGame.toggleFlag(4, 5);
        exampleGame.toggleFlag(5, 2);
        exampleGame.toggleFlag(5, 10);
        exampleGame.toggleFlag(6, 6);
        exampleGame.toggleFlag(6, 9);
        exampleGame.toggleFlag(6, 14);
        exampleGame.toggleFlag(8, 13);
        exampleGame.toggleFlag(11, 7);
        exampleGame.toggleFlag(12, 9);
        exampleGame.toggleFlag(14, 7);
        exampleGame.toggleFlag(15, 2);
        exampleGame.toggleFlag(15, 8);
        exampleGame.toggleFlag(15, 11);
        exampleGame.toggleFlag(15, 15);
        assertFalse(exampleGame.isGameWon());
    }

    @Test
    public void gameIsntWonBeforeAnyGuesses() {
        BoardLogic b = new BoardLogic(16, 16, 16, 1L);
        assertFalse(b.isGameWon());
    }

}