package minesweep.ui;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import minesweep.game.BoardLogic;

public class StatusUI extends HBox {
    
    BoardLogic b;

    public StatusUI(BoardLogic b) {
        this.b = b;
        update();
    }

    public void update() {

        getChildren().clear();

        Label guessNumber = new Label("Guess number: " + b.getGuessCount());
        Label flagNumber = new Label("Flagged squares: " + b.getFlagCount());

        getChildren().addAll(guessNumber, flagNumber);

    }

}
