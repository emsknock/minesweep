package minesweep.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;

import minesweep.game.BoardLogic;
import minesweep.game.Square;

public class ImportExport {

    public static boolean exportGame(BoardLogic board, File file) throws IOException {
        
        Square[][] grid = board.getRawGrid();
        ArrayList<Byte> data = new ArrayList<Byte>();

        for (Square[] row : grid) {
            for (Square col : row) {
                data.add(col.serialise());
            }
            data.add((byte) 0xff);
        }

        FileUtils.writeByteArrayToFile(
            file,
            // WriteByteArrayToFile want's byte[] instead of Byte[], so we have
            // to use ArrayUtils to turn the Byte[] into byte[] — of course
            // ArrayUtils returns an Object instead of byte[], so we also need
            // to explicitly cast it into byte[] to appease Javas never ending
            // lust to make the programmers life total misery
            (byte[]) ArrayUtils.toPrimitive(data.toArray())
        );

        return true;

    }

    public static BoardLogic importGame(File file) throws IOException {
        
        byte[] readData = FileUtils.readFileToByteArray(file);

        int height = 0;
        int width = 0;
        for (byte b : readData) {
            if (b == (byte) 0xff) {
                height++;
            } else {
                width++;
            }
        }

        Square[][] grid = new Square[height][width];
        int mineCount = 0;
        int y = 0;
        int x = 0;
        for (byte b : readData) {
            if (b == (byte) 0xff) {
                y++;
                x = 0;
            } else {
                Square deserialised = Square.deserialise(b, y, x);
                grid[y][x] = deserialised;
                if (deserialised.isMine) {
                    mineCount++;
                }
            }
        }

        BoardLogic output = new BoardLogic(height, width, mineCount, 1L);
        output.setRawGrid(grid);

        return output;

    }

}
