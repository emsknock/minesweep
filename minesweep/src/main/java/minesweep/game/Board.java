package minesweep.game;

import java.util.*;

public class Board {

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

    public void clearBoard() {
        this.grid = new Square[gridH][gridW];
        for (int rowIdx = 0; rowIdx < gridH; rowIdx++) {
            this.grid[rowIdx] = new Square[gridW];
            for (int colIdx = 0; colIdx < gridW; colIdx++) {
                this.grid[rowIdx][colIdx] = new Square(rowIdx, colIdx);
            }
        }
    }

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

    public boolean isInBounds(int y, int x) {
        return y >= 0 && y < this.gridH && x >= 0 && x < this.gridW;
    }

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