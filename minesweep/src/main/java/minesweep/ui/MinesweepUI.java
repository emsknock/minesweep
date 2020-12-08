package minesweep.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import minesweep.game.Board;
import minesweep.game.Square;

public class MinesweepUI extends Application {

    private final static int SQUARE_SIZE = 20;
    private final static Color[] SQUARE_COLOURS = {
        Color.BLACK,
        Color.BLUE,
        Color.GREEN,
        Color.RED,
        Color.PURPLE,
        Color.MAROON,
        Color.TURQUOISE,
        Color.BLACK,
        Color.GREY,
    };

    /**
     * Creates a StackPane representing a square on the board
     * 
     * @param isRevealed
     * @param value    This square's mine neighbours (0–8) or -1 for a mine
     * @return
     */
    public StackPane square(boolean isRevealed, int value) {

        StackPane square = new StackPane();
        Rectangle box = new Rectangle(SQUARE_SIZE, SQUARE_SIZE);

        Label text;
        switch (value) {
            case 0:
                text = new Label("");
                break;
            case -1:
                text = new Label("X");
                break;
            default:
                text = new Label(String.valueOf(value));
        }

        if (value >= 0)
            text.setTextFill(SQUARE_COLOURS[value]);
        
        box.setFill(isRevealed ? Color.LIGHTGREY : Color.BLUE);
        box.setStroke(isRevealed ? Color.GREY : Color.DARKBLUE);
        
        text.setVisible(isRevealed);

        square.getChildren().addAll(box, text);

        return square;

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        VBox mainPane = new VBox(10);
        mainPane.setPadding(new Insets(15));

        GridPane grid = new GridPane();
        grid.setHgap(2);
        grid.setVgap(2);

        Board board = new Board(16, 30, 99, 1L);
        for (Square[] row : board.getGrid()) {
            for (Square square : row) {
                grid.add(square(true, square.isMine ? -1 : square.mineNeighbours), square.x, square.y);
            }
        }

        mainPane.getChildren().add(grid);

        Scene mainScene = new Scene(mainPane, 10, 10);

        primaryStage.setScene(mainScene);
        primaryStage.setMinWidth(15 + SQUARE_SIZE * 30 + 15);
        primaryStage.setMinHeight(15 + SQUARE_SIZE * 16 + 15);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}