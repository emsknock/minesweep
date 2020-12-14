package minesweep.game;

import java.util.Random;

public class BoardLogic {

    private Board board;
    private Random rng;
    private int mineCount;

    public BoardLogic(int height, int width, int mineCount, long randSeed) {
        this.board = new Board(height, width);
        this.rng = new Random(randSeed);
        this.mineCount = mineCount;
    }

    public boolean reveal(int y, int x) {

        Square guess = board.getSquare(y, x);

        if (guess.isRevealed) {
            // Revealing an already revealed square should reveal all its
            // neighbours, but only if the player has flagged the correct
            // amount of neighbours.
            if (board.numFlaggedNeighbours(guess) != guess.mineNeighbours) {
                // Since there's a wrong amount of flagged neighbours,
                // this reveal call should just be ignored
                return false;
            } else {
                // There's a correct amount of flagged neighbours, but
                // since it's possible the player's made a mistake, its
                // possible that this will reveal a mine — thus we return
                // true if any of the neighbour reveals returns true
                // (i.e. a mine is revealed)
                return board.getNeighbours(guess)
                    .stream()
                    .filter(neighbour -> !neighbour.isRevealed && !neighbour.isFlagged)
                    .anyMatch(this::reveal);
            }
        } else {
            // Revealing an unrevealed "0" should immediately reveal all
            // its unrevealed neighbours. Any neighbouring 0's will then
            // reveal all their unrevealed neighbours and so on.
            guess.isRevealed = true;

            if (guess.isMine) {
                return true;
            }

            if (guess.mineNeighbours == 0) {
                board.getNeighbours(guess).stream().forEach(this::reveal);
            }

            return false;
        }

    }

    public boolean reveal(Square guess) {
        return reveal(guess.y, guess.x);
    }

    public boolean guess(Square guess) {
        if (board.guessCount == 0) {
            placeMines(guess.y, guess.x);
        }
        if (guess.isFlagged) {
            return false;
        } else {
            board.guessCount++;
            return reveal(guess);
        }
    }

    /**
     * Toggles wether the given square is flagged or not.
     * Doesn't check wether the square is revealed or not.
     * @param s The square to flag or unflag
     * @return The new state of flagging
     */
    public boolean toggleFlag(Square s) {
        board.flagCount += s.isFlagged ? -1 : 1;
        s.isFlagged = !s.isFlagged;
        return s.isFlagged;
    }

    public Square[][] getRawGrid() {
        return board.getRawGrid();
    }

    public int getGuessCount() {
        return board.guessCount;
    }

    public int getFlagCount() {
        return board.flagCount;
    }

    public int getMineCount() {
        return board.mineCount;
    }

    public boolean isGameWon() {
        if (getFlagCount() != getMineCount()) {
            return false;
        }
        // All mines have to be flagged and all safe squares must be unflagged
        for (Square[] row : getRawGrid()) {
            for (Square col : row) {
                if (col.isMine && !col.isFlagged) {
                    return false;
                }
                if (!col.isMine && col.isFlagged) {
                    return false;
                }
            }
        }
        return true;
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

}
