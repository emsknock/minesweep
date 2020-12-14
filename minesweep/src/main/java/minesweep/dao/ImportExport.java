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
        data.add((byte) board.getGuessCount());

        FileUtils.writeByteArrayToFile(
            file,
            // ArrayList.toArray doesn't return a Byte[] but Object[] instead
            // unless we explicitly point it to a Byte[] — I guess it's too
            // stupid to infer the array type. Because that's not enough rage-
            // inducing enough, writeByteArrayToFile doesn't accept a Byte[]
            // but demands a byte[]. Thus we also need to convert Byte[] to
            // byte[] using ArrayUtils to appease Java's lust to make the
            // programmer's life miserable
            ArrayUtils.toPrimitive(data.toArray(new Byte[data.size()]))
        );

        return true;

    }

    public static BoardLogic importGame(File file) throws IOException {
        
        byte[] readData = FileUtils.readFileToByteArray(file);
        byte[] gridData = ArrayUtils.subarray(readData, 0, readData.length - 1);
        int guessCount = readData[readData.length - 1];

        int height = 0;
        int width = 0;
        for (byte b : gridData) {
            if (b == (byte) 0xff) {
                height++;
            } else if (height == 0) {
                width++;
            }
        }

        Square[][] grid = new Square[height][width];
        int mineCount = 0;
        int y = 0;
        int x = 0;
        for (byte b : gridData) {
            if (b == (byte) 0xff) {
                y++;
                x = 0;
            } else {
                Square deserialised = Square.deserialise(b, y, x);
                grid[y][x] = deserialised;
                x++;
                if (deserialised.isMine) {
                    mineCount++;
                }
            }
        }
        
        BoardLogic output = new BoardLogic(height, width, mineCount, 1L);
        output.setGuessCount(guessCount);
        output.setRawGrid(grid);

        return output;

    }

}
