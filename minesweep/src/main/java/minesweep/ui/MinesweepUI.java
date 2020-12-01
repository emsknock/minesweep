package minesweep.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MinesweepUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        VBox mainPane = new VBox(10);
        Scene mainScene = new Scene(mainPane, 300, 250);

        primaryStage.setScene(mainScene);
        primaryStage.show();
        
    }

    public static void main(String[] args) {
        launch(args);
    }

}