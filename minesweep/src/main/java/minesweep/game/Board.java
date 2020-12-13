package minesweep.game;

import java.util.*;

/**
 * The Board class keeps track of a nxm grid of Squares, generates a valid
 * minesweeper game from a random seed, and handles the logic of revealing a
 * square on the game grid.
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

    protected int height;
    protected int width;
    protected int guessCount = 0;

    private Square[][] grid;

    /**
     * Create a new board with the given height and width and with all squares
     * set to "0". Both height and width must both be over 0.
     * @param height The height of the board. Must be over 0.
     * @param width The width of the board. Must be over 0.
     */
    public Board(int height, int width) {

        this.height = height;
        this.width = width;

        if (height < 1 || width < 1) {
            throw new IllegalArgumentException("Board width and height must both be at least 1");
        }

        clearBoard();

    }

    /**
     * Resets the board:
     * - Sets the guess count to 0
     * - Sets all squares to unflagged, unrevealed "0" squares
     */
    public void clearBoard() {
        this.guessCount = 0;
        this.grid = new Square[height][width];
        for (int rowIdx = 0; rowIdx < height; rowIdx++) {
            this.grid[rowIdx] = new Square[width];
            for (int colIdx = 0; colIdx < width; colIdx++) {
                this.grid[rowIdx][colIdx] = new Square(rowIdx, colIdx);
            }
        }
    }

    /**
     * Checks whether the given coordinates are in bound of the board
     * @param y The Y coordinate
     * @param x The X coordinate
     */
    public boolean isInBounds(int y, int x) {
        return y >= 0 && y < this.height && x >= 0 && x < this.width;
    }

    /**
     * Returns a reference to the square object at the given coordinates
     * @param y The Y coordinate
     * @param x The X coordinate
     * @return The square object at the given coordinates
     */
    public Square getSquare(int y, int x) {
        return this.grid[y][x];
    }

    /**
     * For a given coordinate, returns a list containing all of its neighbours.
     * Most squares have 8 neighbours, but e.g. the corners only have 3.
     * @param y The Y coordinate
     * @param x The X coordinate
     */
    public List<Square> getNeighbours(int y, int x) {
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
     * For a given square, returns a list containing all of its neighbours.
     * @see Board#getNeighbours(int, int)
     * @param s The square to reveal
     * @return
     */
    public List<Square> getNeighbours(Square s) {
        return getNeighbours(s.y, s.x);
    }

    /**
     * For a given square, returns a list containing the number of flagged
     * neighbours it has.
     * @param s The square to count the flagged neighbours of
     * @return The number of flagged neighbours (0–8)
     */
    public int numFlaggedNeighbours(Square s) {
        return getNeighbours(s)
            .stream()
            .reduce(
                0,
                (flaggedNeighbours, neighbour) ->
                    flaggedNeighbours + (neighbour.isFlagged ? 1 : 0),
                Integer::sum
            );
    }

    /**
     * Toggles wether the given square is flagged or not.
     * Doesn't check wether the square is revealed or not.
     * @param s The square to flag or unflag
     * @return The new state of flagging
     */
    public boolean toggleFlag(Square s) {
        s.isFlagged = !s.isFlagged;
        return s.isFlagged;
    }

    /**
     * Returns a reference to the Square[y][x] array that represents the board
     * @return A 2d array of Squares with [y][x] order
     */
    public Square[][] getRawGrid() {
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