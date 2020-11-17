package minesweep.game;

import java.util.Random;

public class Board {

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

        }

    }

    public Square[][] getGrid() {
        return this.grid;
    }

}