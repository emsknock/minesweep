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

    private int gridW;
    private int gridH;
    protected boolean areMinesPlaced = false;

    private Square[][] grid;

    public Board(int height, int width) {

        this.gridH = height;
        this.gridW = width;

        if (height < 1 || width < 1) {
            throw new IllegalArgumentException("Board width and height must both be at least 1");
        }

        clearBoard();

    }

    /**
     * Sets the grid to contain only "0" squares
     */
    public void clearBoard() {
        this.areMinesPlaced = false;
        this.grid = new Square[gridH][gridW];
        for (int rowIdx = 0; rowIdx < gridH; rowIdx++) {
            this.grid[rowIdx] = new Square[gridW];
            for (int colIdx = 0; colIdx < gridW; colIdx++) {
                this.grid[rowIdx][colIdx] = new Square(rowIdx, colIdx);
            }
        }
    }

    /**
     * Checks whether the given coordinates are in bound of the board
     * 
     * @param y The Y coordinate
     * @param x The X coordinate
     */
    public boolean isInBounds(int y, int x) {
        return y >= 0 && y < this.gridH && x >= 0 && x < this.gridW;
    }

    public Square getSquare(int y, int x) {
        return this.grid[y][x];
    }

    /**
     * For a given coordinate, return a list containing all of its neighbours. Most
     * squares have 8 neighbours, but e.g. the corners only have 3.
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

    public ArrayList<Square> getNeighbours(Square s) {
        return getNeighbours(s.y, s.x);
    }

    public int numFlaggedNeighbours(Square s) {
        return getNeighbours(s).stream().reduce(
            0,
            (flaggedNeighbours, neighbour) ->
                flaggedNeighbours + (neighbour.isFlagged ? 1 : 0),
            Integer::sum
        );
    }

    public Square[][] getGrid() {
        return this.grid;
    }

    public void toggleFlag(Square s) {
        s.isFlagged = !s.isFlagged;
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