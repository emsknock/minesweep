package minesweep.game;

import java.util.Random;

public class BoardLogic {

    private Board board;
    private Random rng;
    private int mineCount;

    public BoardLogic(int height, int width, int mineCount, long randSeed) {
        if (mineCount >= height * width) {
            throw new IllegalArgumentException("Illegal amount of mines: must leave at least one blank square");
        }
        this.board = new Board(height, width);
        this.rng = new Random(randSeed);
        this.mineCount = mineCount;
    }

    private boolean reveal(Square guessedSquare) {
        if (guessedSquare.isRevealed) {
            // Revealing an already revealed square should reveal all its
            // neighbours, but only if the player has flagged the correct
            // amount of neighbours.
            if (board.numFlaggedNeighbours(guessedSquare) != guessedSquare.mineNeighbours) {
                // Since there's a wrong amount of flagged neighbours,
                // this reveal call should just be ignored
                return false;
            } else {
                // There's a correct amount of flagged neighbours, but
                // since it's possible the player's made a mistake, its
                // possible that this will reveal a mine — thus we return
                // true if any of the neighbour reveals returns true
                // (i.e. a mine is revealed)
                return board.getNeighbours(guessedSquare)
                    .stream()
                    .filter(neighbour -> !neighbour.isRevealed && !neighbour.isFlagged)
                    .anyMatch(this::reveal);
            }
        } else {
            // Revealing an unrevealed "0" should immediately reveal all
            // its unrevealed neighbours. Any neighbouring 0's will then
            // reveal all their unrevealed neighbours and so on.
            guessedSquare.isRevealed = true;

            if (guessedSquare.isMine) {
                board.hasHitMine = true;
                return true;
            }

            if (guessedSquare.mineNeighbours == 0) {
                board.getNeighbours(guessedSquare).stream().forEach(this::reveal);
            }

            return false;
        }
    }

    /**
     * Submits a guess at the given coordinates and places mines if needed.
     * 
     * When guessing an unrevealed "0" square, recursively reveals all the
     * possible empty space as per minesweeper rules.
     * 
     * When guessing a revealed non-"0" square, reveals all its neighbours
     * (and in case any of them are "0"'s, recursively all the empty space as
     * above) iff the square has exactly the amount of flagged neighbours that
     * corresponds to its value.
     * 
     * Since the first guess is guaranteed not to be a mine, the mines aren't
     * placed before the first time this method is called.
     * 
     * @param y
     * @param x
     * @return True if the guess hit a mine and false otherwise
     */
    public boolean guess(int y, int x) {

        Square guessedSquare = board.getSquare(y, x);

        // The first guess should place the mines so that we avoid placing
        // any mines near it
        if (board.guessCount == 0) {
            placeMines(y, x);
        }
        if (
            // Clicking flagged squares should be ignored
            guessedSquare.isFlagged ||
            // Clicking revealed "0" squares should be ignored
            (guessedSquare.isRevealed && guessedSquare.mineNeighbours == 0) ||
            // Clicking revealed squares over zero should only be ignored
            // when their neighbours are all revealed or already flagged,
            // i.e. there's nothing to reveal anymore
            guessedSquare.isRevealed && board.getNeighbours(guessedSquare)
                .stream()
                .allMatch(neighbour -> neighbour.isRevealed || neighbour.isFlagged)
        ) {
            return false;
        }
        
        board.guessCount++;
        return reveal(guessedSquare);

    }

    /**
     * Exactly the same as the coordinate based guess method, but extracts the
     * coordinates from a square. @see BoardLogic#guess(int, int)
     * @param guessedSquare
     * @return True if the guess hit a mine and false otherwise
     */
    public boolean guess(Square guessedSquare) {
        return guess(guessedSquare.y, guessedSquare.x);
    }

    /**
     * Toggles wether the given square is flagged or not.
     * Doesn't check wether the square is revealed or not.
     * @param s The square to flag or unflag
     * @return The new state of flagging or false if the square is revealed already
     */
    public boolean toggleFlag(Square s) {
        if (s.isRevealed) {
            return false;
        }
        board.flagCount += s.isFlagged ? -1 : 1;
        s.isFlagged = !s.isFlagged;
        return s.isFlagged;
    }

    /**
     * Exactly the same as the reference based toggleField method, but finds
     * the Square object based on coordinates. @see BoardLogic#toggleField(Square)
     * @param y
     * @param x
     * @return The new state of flagging or false if the square is revealed already
     */
    public boolean toggleFlag(int y, int x) {
        return toggleFlag(board.getSquare(y, x));
    }

    /**
     * Returns a reference to the Square[y][x] array that represents the board
     * @return A 2d array of Squares with [y][x] order
     */
    public Square[][] getRawGrid() {
        return board.grid;
    }

    public void setRawGrid(Square[][] newGrid) {
        board.grid = newGrid;
    }

    public int getGuessCount() {
        return board.guessCount;
    }

    public void setGuessCount(int guessCount) {
        board.guessCount = guessCount;
    }

    public int getFlagCount() {
        return board.flagCount;
    }

    public void setFlagCount(int flagCount) {
        board.flagCount = flagCount;
    }

    public int getMineCount() {
        return board.mineCount;
    }

    public int getBoardWidth() {
        return board.grid[0].length;
    }

    public int getBoardHeight() {
        return board.grid.length;
    }

    /**
     * Checks if the game is won, i.e. exactly all mine squares are flagged and
     * exactly all safe squares are unflagged. Doesn't tell wether the game is lost:
     * @see BoardLogic#isGameLost()
     * @return True if the game has been won
     */
    public boolean isGameWon() {
        if (getGuessCount() < 1 || getFlagCount() != getMineCount()) {
            return false;
        }
        // All mines have to be flagged and all safe squares must be unflagged
        for (Square[] row : getRawGrid()) {
            for (Square col : row) {
                if ((col.isMine && !col.isFlagged) || (!col.isMine && col.isFlagged)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the game is lost, i.e. any mine is uncovered. Doesn't tell wether the
     * game is won:
     * @see BoardLogic#isGameWon()
     * @return
     */
    public boolean isGameLost() {
        return board.hasHitMine;
    }

    public void placeMines(int avoidY, int avoidX) {
        int numMinesPlaced = 0;
        while(numMinesPlaced != mineCount) {

            int y = rng.nextInt(board.height);
            int x = rng.nextInt(board.width);
            Square potentialMine = board.getSquare(y, x);

            if (
                // Cannot place on top of existing one:
                !potentialMine.isMine &&
                // Cannot place in the first clicked square:
                y != avoidY && x != avoidX &&
                // Cannot place to any of the first clicked square's neighbours:
                board.getNeighbours(potentialMine)
                    .stream()
                    .allMatch(neighbour -> neighbour.y != avoidY && neighbour.x != avoidX)
            ) {
                // All of the above predicates passed; we can place a mine here
                potentialMine.isMine = true;
                numMinesPlaced++;
                board.mineCount++;
                for (Square neighbour : board.getNeighbours(potentialMine)) {
                    neighbour.mineNeighbours++;
                }
            }

        }
    }

    public String toString() {
        StringBuilder output = new StringBuilder();
        for (Square[] row : getRawGrid()) {
            for (Square col : row) {
                output.append(col.isMine ? "X" : col.mineNeighbours);
            }
            output.append("\n");
        }
        return output.toString();
    }

}
