package minesweep.game;

import java.util.Random;

public class BoardLogic {

    Board b;

    BoardLogic(Board b) {
        this.b = b;
    }

    public boolean reveal(int y, int x) {

        if (!b.areMinesPlaced) {
            b.placeMines(y, x);
        }

        Square guess = b.getSquare(y, x);

        if (guess.isRevealed) {
            // Revealing an already revealed square should reveal all its
            // neighbours, but only if the player has flagged the correct
            // amount of neighbours.
            if (b.numFlaggedNeighbours(guess) != guess.mineNeighbours) {
                // Since there's a wrong amount of flagged neighbours,
                // this reveal call should just be ignored
                return false;
            } else {
                // There's a correct amount of flagged neighbours, but
                // since it's possible the player's made a mistake, its
                // possible that this will reveal a mine — thus we return
                // true if any of the neighbour reveals returns true
                // (i.e. a mine is revealed)
                return b.getNeighbours(guess).stream().anyMatch(b::reveal);
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
                b.getNeighbours(guess).stream().forEach(b::reveal);
            }

            return false;
        }

    }

    public boolean reveal(Square guess) {
        return reveal(guess.y, guess.x);
    }

    public void placeMines(int numMinesToPlace, int avoidX, int avoidY, long randSeed) {

        Random rng = new Random(randSeed);

        int numMinesPlaced = 0;
        while(numMinesPlaced != numMinesToPlace) {
            
            int y = rng.nextInt();
            int x = rng.nextInt();
            Square potentialMine = b.getSquare(y, x);

            if (
                // Cannot place on top of existing one:
                !potentialMine.isMine ||
                // Cannot place in the first clicked square:
                !(y == avoidY && x == avoidX) ||
                // Cannot place to any of the first clicked square's neighbours:
                !b.getNeighbours(potentialMine)
                    .stream()
                    .anyMatch(neighbour -> neighbour.y == avoidY && neighbour.x == avoidX)
            ) {
                // All of the above predicates passed; we can place a mine here
                potentialMine.isMine = true;
                numMinesPlaced++;
                for (Square neighbour : b.getNeighbours(potentialMine)) {
                    neighbour.mineNeighbours++;
                }
            }

        }

    }

}
