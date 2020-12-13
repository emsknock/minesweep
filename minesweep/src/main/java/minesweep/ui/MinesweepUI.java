package minesweep.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import minesweep.game.*;

public class MinesweepUI extends Application {

    private final static int WINDOW_PADDING = 15;

    private static BoardLogic board;

    @Override
    public void start(Stage primaryStage) throws Exception {

        StatusUI status = new StatusUI(board);

        VBox mainPane = new VBox();
        mainPane.setPadding(new Insets(WINDOW_PADDING));

        GridPane grid = new GridPane();
        grid.setHgap(2);
        grid.setVgap(2);

        for (Square[] row : board.getRawGrid()) {
            for (Square square : row) {

                Node squareNode = new SquareUI(board, square, grid, status);

                grid.add(
                    squareNode,
                    square.x,
                    square.y
                );

            }
        }

        mainPane.getChildren().addAll(grid, status);

        Scene mainScene = new Scene(mainPane);

        primaryStage.setScene(mainScene);
        primaryStage.setMinWidth(2 * WINDOW_PADDING + 30 * SquareUI.SQUARE_SIZE);
        primaryStage.setMinHeight(2 * WINDOW_PADDING + 16 * SquareUI.SQUARE_SIZE);
        primaryStage.show();

    }

    public static void main(String[] args) {
        board = new BoardLogic(16, 30, 99, System.currentTimeMillis());
        launch(args);
    }

}