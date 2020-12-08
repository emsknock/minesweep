package minesweep.game;

import java.util.*;

/**
 * The Board class keeps track of a nxm grid of Squares, generates a valid
 * minesweeper game from a random seed, and handles the logic of revealing
 * a square on the game grid.
 */
public class Board {

    // The position differences of a given square to its neighbours
    private static final int[][] NEIGHBOUR_DELTAS = {
        { -1, -1 },
        { -1, 0 },
        { -1, 1 },
        { 0, -1 },
        { 0, 1 },
        { 1, -1 },
        { 1, 0 },
        { 1, 1 },
    };

    private int gridW;
    private int gridH;
    private int mineCount;
    private Random rng;
    
    private Square[][] grid;

    public Board(int height, int width, int mineCount, long randSeed) {

        this.rng = new Random(randSeed);
        
        this.gridH = height;
        this.gridW = width;
        this.mineCount = mineCount;

        clearBoard();
        placeMines();

    }

    /** Sets the grid to contain only "0" squares */
    public void clearBoard() {
        this.grid = new Square[gridH][gridW];
        for (int rowIdx = 0; rowIdx < gridH; rowIdx++) {
            this.grid[rowIdx] = new Square[gridW];
            for (int colIdx = 0; colIdx < gridW; colIdx++) {
                this.grid[rowIdx][colIdx] = new Square(rowIdx, colIdx);
            }
        }
    }

    /**
     * Places the amount of mines given in the constructor to the board.
     * 
     * @implNote Assumes the board only contains "0" squares and no mines beforehand
     */
    public void placeMines() {
        int placedMines = 0;
        while (placedMines != mineCount) {

            int rowIdx = rng.nextInt(gridH);
            int colIdx = rng.nextInt(gridW);
            Square sq = this.grid[rowIdx][colIdx];

            if (sq.isMine) { 
                continue;
            }
            
            sq.isMine = true;
            placedMines++;

            for (Square neighbour : this.getNeighbours(rowIdx, colIdx)) {
                neighbour.mineNeighbours++;
            }

        }
    }

    /**
     * Checks whether the given coordinates are in bound of the board
     * @param y
     * @param x
     */
    public boolean isInBounds(int y, int x) {
        return y >= 0 && y < this.gridH && x >= 0 && x < this.gridW;
    }

    /**
     * For a given coordinate, return a list containing all of its neighbours.
     * Most squares have 8 neighbours, but e.g. the corners only have 3.
     * 
     * @param y The Y coordinate
     * @param x The X coordinate
     */
    public ArrayList<Square> getNeighbours(int y, int x) {
        ArrayList<Square> neighbours = new ArrayList<Square>();
        for (int[] delta : Board.NEIGHBOUR_DELTAS) {
            int ny = y + delta[0];
            int nx = x + delta[1];
            if (this.isInBounds(ny, nx)) {
                neighbours.add(this.grid[ny][nx]);
            }
        }
        return neighbours;
    }

    /**
     * Reveal the square at the given coordinates. If the square is a "0",
     * will continue recursively revealing all of its neighbours, until no "0"
     * squares remain.
     * @param y The Y coordinate
     * @param x The X coordinate
     * @return `true` iff the revealed square was a mine
     */
    public boolean reveal(int y, int x) {
        
        Square guessedSquare = this.grid[y][x];
        guessedSquare.isRevealed = true;

        if (guessedSquare.isMine) {
            return true;
        }
        if (guessedSquare.mineNeighbours > 0) {
            return false;
        }

        for (Square neighbour : this.getNeighbours(y, x)) {
            reveal(neighbour);
        }

        return false;

    }
    
    /**
     * Reveal the given square with the same recursion as the coordinate-based
     * reveal method.
     * @see minesweep.game.Board#reveal(int, int) The coordinate based reveal
     * @param guess
     * @return
     */
    public boolean reveal(Square guess) {
        return this.reveal(guess.y, guess.x);
    }

    public Square[][] getGrid() {
        return this.grid;
    }

    public String toString() { 
        StringBuilder out = new StringBuilder();
        for (Square[] row : this.grid) {
            for (Square col : row) {
                out.append(col.isMine ? "X" : col.mineNeighbours);
            }
            out.append("\n");
        }
        return out.toString();
    }     

}