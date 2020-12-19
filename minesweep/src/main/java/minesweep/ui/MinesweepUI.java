package minesweep.ui;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import minesweep.dao.ImportExport;
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
                update(primaryStage);
            } catch (NumberFormatException ex) {
                Alert errorAlert = new Alert(AlertType.ERROR, "One or more variable not allowed: width and height must both be over zero, and mine amount must be less than (width * height - 9)", ButtonType.OK);
                errorAlert.showAndWait();
                return;
            } catch (Exception ex) {
                Alert errorAlert = new Alert(AlertType.ERROR, "Couldn't start game: " + ex.getMessage(), ButtonType.OK);
                errorAlert.showAndWait();
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

        Menu fileMenu = new Menu("File");
        MenuItem saveMenuItem = new MenuItem("Save game");
        MenuItem loadMenuItem = new MenuItem("Load game");
        fileMenu.getItems().addAll(saveMenuItem, loadMenuItem);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(fileMenu);

        saveMenuItem.addEventHandler(ActionEvent.ACTION, ev -> {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showSaveDialog(primaryStage);
            try {
                ImportExport.exportGame(board, selectedFile);
            } catch (IOException ex) {
                Alert errorAlert = new Alert(AlertType.ERROR, "Couldn't save game file: " + ex.getMessage(), ButtonType.OK);
                errorAlert.showAndWait();
            }
        });
        loadMenuItem.addEventHandler(ActionEvent.ACTION, ev -> {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            try {
                BoardLogic imported = ImportExport.importGame(selectedFile);
                MinesweepUI.board = imported;
                update(primaryStage);
            } catch (IOException ex) {
                Alert errorAlert = new Alert(AlertType.ERROR, "Couldn't load game file: " + ex.getMessage(), ButtonType.OK);
                errorAlert.showAndWait();
            } catch (Exception ex) {
                Alert errorAlert = new Alert(AlertType.ERROR, "Couldn't parse or run loaded game file. Are you sure you selected a valid minesweep save game file? " + ex.getMessage(), ButtonType.OK);
                errorAlert.showAndWait();
            }
        });

        mainPane.getChildren().addAll(menuBar, gameArea, status, newGameArea);

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