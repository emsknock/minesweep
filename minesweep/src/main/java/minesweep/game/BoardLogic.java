package minesweep.game;

import java.util.Random;

public class BoardLogic {

    Board board;
    Random rng;
    int mineCount;

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
        try {
            return reveal(guess.y, guess.x);
        } catch (StackOverflowError e) {
            e.printStackTrace();
            System.exit(1);
            return true;
        }
    }

    public boolean guess(Square guess) {
        if (board.guessCount == 0) {
            placeMines(guess.y, guess.x);
        }
        board.guessCount++;
        return reveal(guess);
    }

    /**
     * @see Board#toggleFlag(Square)
     * @param s The square to flag
     * @return The new state of flagging
     */
    public boolean toggleFlag(Square s) {
        return board.toggleFlag(s);
    }

    public Square[][] getRawGrid() {
        return board.getRawGrid();
    }

    public void placeMines(int avoidY, int avoidX) {
        int numMinesPlaced = 0;
        while(numMinesPlaced != mineCount) {

            System.out.println("Placed mines so far: " + numMinesPlaced);
            
            int y = rng.nextInt(board.height);
            int x = rng.nextInt(board.width);

            System.out.println("Trying to place mine at " + x + ":" + y);

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
                System.out.println("Mine placement successful");
                potentialMine.isMine = true;
                numMinesPlaced++;
                for (Square neighbour : board.getNeighbours(potentialMine)) {
                    neighbour.mineNeighbours++;
                }
            }

        }
        System.out.println("Mines placed!");
        System.out.println(board);
    }

}
