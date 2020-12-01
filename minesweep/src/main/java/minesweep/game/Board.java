package minesweep.game;

import java.util.*;

public class Board {

    private static final int[][] neighbourDeltas = {
        { -1, -1 },
        { -1, 0 },
        { -1, 1 },
        { 0, -1 },
        { 0, 1 },
        { 1, -1 },
        { 1, 0 },
        { 1, 1 },
    };

    private int GRID_W;
    private int GRID_H;
    private Random rng;

    private Square[][] grid;

    public Board(int height, int width, int mineCount, long randSeed) {

        this.rng = new Random(randSeed);
        
        this.GRID_H = height;
        this.GRID_W = width;

        this.grid = new Square[height][width];
        for (int rowIdx = 0; rowIdx < height; rowIdx++) {
            this.grid[rowIdx] = new Square[width];
            for (int colIdx = 0; colIdx < width; colIdx++) {
                this.grid[rowIdx][colIdx] = new Square();
            }
        }

        int placedMines = 0;
        while (placedMines != mineCount) {

            int rowIdx = rng.nextInt(height);
            int colIdx = rng.nextInt(width);
            Square sq = this.grid[rowIdx][colIdx];

            if (sq.isMine) continue;
            
            sq.isMine = true;
            placedMines++;

            for (Square neighbour : this.getNeighbours(rowIdx, colIdx)) {
                neighbour.mineNeighbours++;
            }

        }

    }

    public boolean isInBounds(int y, int x) {
        return y >= 0 && y < this.GRID_H && x >= 0 && x < this.GRID_W;
    }

    public ArrayList<Square> getNeighbours(int y, int x) {
        ArrayList<Square> neighbours = new ArrayList<Square>();
        for (int[] delta : Board.neighbourDeltas) {
            int ny = y + delta[0];
            int nx = x + delta[1];
            if (this.isInBounds(ny, nx)) {
                neighbours.add(this.grid[ny][nx]);
            }
        }
        return neighbours;
    }

    public boolean reveal(int y, int x) {
        this.grid[y][x].isRevealed = true;
        return this.grid[y][x].isMine;
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