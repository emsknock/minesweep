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
        Color.BLACK, // 0 included for simplicity's sake
        Color.BLUE,
        Color.GREEN,
        Color.RED,
        Color.PURPLE,
        Color.MAROON,
        Color.TURQUOISE,
        Color.BLACK,
        Color.GREY,
    };

    public StackPane renderSquare(Square s) {

        StackPane square = new StackPane();
        Rectangle box = new Rectangle(SQUARE_SIZE, SQUARE_SIZE);

        Label text;
        if (s.isFlagged || s.mineNeighbours == 0) {
            text = new Label("");
        } else if (s.isMine) {
            text = new Label("X");
        } else {
            text = new Label(String.valueOf(s.mineNeighbours));
        }

        text.setTextFill(SQUARE_COLOURS[s.mineNeighbours]);
        
        if (s.isRevealed) {
            box.setFill(Color.LIGHTGREY);
            box.setStroke(Color.GREY);
        } else if (s.isFlagged) {
            box.setFill(Color.RED);
            box.setStroke(Color.DARKRED);
        } else {
            box.setFill(Color.BLUE);
            box.setStroke(Color.DARKBLUE);
        }
        
        text.setVisible(s.isRevealed);

        square.getChildren().addAll(box, text);

        return square;

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        VBox mainPane = new VBox();
        mainPane.setPadding(new Insets(15));

        GridPane grid = new GridPane();
        grid.setHgap(2);
        grid.setVgap(2);

        Board board = new Board(16, 30, 99, System.currentTimeMillis());
        for (Square[] row : board.getGrid()) {
            for (Square square : row) {
                grid.add(
                    renderSquare(square),
                    square.x,
                    square.y
                );
            }
        }

        mainPane.getChildren().add(grid);

        Scene mainScene = new Scene(mainPane);

        primaryStage.setScene(mainScene);
        primaryStage.setMinWidth(15 + SQUARE_SIZE * 30 + 15);
        primaryStage.setMinHeight(15 + SQUARE_SIZE * 16 + 15);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}