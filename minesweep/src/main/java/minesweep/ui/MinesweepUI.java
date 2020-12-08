package minesweep.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import minesweep.game.Board;
import minesweep.game.Square;

public class MinesweepUI extends Application {

    private final static int WINDOW_PADDING = 15;

    private static Board board;

    @Override
    public void start(Stage primaryStage) throws Exception {

        System.out.println(board.toString());

        VBox mainPane = new VBox();
        mainPane.setPadding(new Insets(WINDOW_PADDING));

        GridPane grid = new GridPane();
        grid.setHgap(2);
        grid.setVgap(2);

        for (Square[] row : board.getGrid()) {
            for (Square square : row) {

                Node squareNode = new SquareUI(board, square, grid);

                grid.add(
                    squareNode,
                    square.x,
                    square.y
                );

            }
        }

        mainPane.getChildren().add(grid);

        Scene mainScene = new Scene(mainPane);

        primaryStage.setScene(mainScene);
        primaryStage.setMinWidth(2 * WINDOW_PADDING + 30 * SquareUI.SQUARE_SIZE);
        primaryStage.setMinHeight(2 * WINDOW_PADDING + 16 * SquareUI.SQUARE_SIZE);
        primaryStage.show();

    }

    public static void main(String[] args) {
        board = new Board(16, 30, 99, System.currentTimeMillis());
        launch(args);
    }

}