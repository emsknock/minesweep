package minesweep.ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import minesweep.game.*;

public class MinesweepUI extends Application {

    private final static int WINDOW_PADDING = 15;

    private static BoardLogic board;

    @Override
    public void start(Stage primaryStage) throws Exception {
        update(primaryStage);
    }

    public void update(Stage primaryStage) {
        VBox mainPane = new VBox();
        mainPane.setPadding(new Insets(WINDOW_PADDING));

        StatusUI status = new StatusUI(board);
        LoseWinUI loseWinScreen = new LoseWinUI(board);
        
        StackPane gameArea = new StackPane();
        GridPane grid = new GridPane();
        grid.setHgap(2);
        grid.setVgap(2);

        for (Square[] row : board.getRawGrid()) {
            for (Square square : row) {

                Node squareNode = new SquareUI(board, square, grid, status, loseWinScreen);

                grid.add(
                    squareNode,
                    square.x,
                    square.y
                );

            }
        }

        StackPane.setAlignment(loseWinScreen, Pos.CENTER);
        gameArea.setPrefSize(board.getRawGrid()[0].length * SquareUI.SQUARE_SIZE, board.getRawGrid().length * SquareUI.SQUARE_SIZE);
        gameArea.getChildren().addAll(grid, loseWinScreen);

        VBox newGameArea = new VBox();
        TextField widthField = new TextField();
        TextField heightField = new TextField();
        TextField minesField = new TextField();
        Button newGameButton = new Button("Go");
        newGameArea.addEventHandler(ActionEvent.ACTION, ev -> {
            try {
                int width = Integer.valueOf(widthField.getText());
                int height = Integer.valueOf(heightField.getText());
                int mines = Integer.valueOf(minesField.getText());
                MinesweepUI.board = new BoardLogic(height, width, mines, System.currentTimeMillis());
                start(primaryStage);
            } catch (NumberFormatException ex) {
                return;
            } catch (Exception ex) {
                return;
            }
        });
        newGameArea.getChildren().addAll(
            new Label("New game:"),
            new Label("Width:"),
            widthField,
            new Label("Height:"),
            heightField,
            new Label("Mines:"),
            minesField,
            newGameButton
        );

        mainPane.getChildren().addAll(gameArea, status, newGameArea);

        Scene mainScene = new Scene(mainPane);

        primaryStage.setScene(mainScene);
        primaryStage.setMinWidth(2 * WINDOW_PADDING + board.getRawGrid()[0].length * SquareUI.SQUARE_SIZE);
        primaryStage.setMinHeight(2 * WINDOW_PADDING + board.getRawGrid().length * SquareUI.SQUARE_SIZE);
        primaryStage.show();
    }

    public static void main(String[] args) {
        board = new BoardLogic(16, 16, 10, System.currentTimeMillis());
        launch(args);
    }

}