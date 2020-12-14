package minesweep.ui;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import minesweep.game.BoardLogic;

public class LoseWinUI extends VBox {

    private BoardLogic b;

    public LoseWinUI(BoardLogic b) {
        this.b = b;
        update();
    }

    public void update() {

        if(!b.isGameWon() && !b.isGameLost()) {
            setVisible(false);
            return;
        }

        setVisible(true);

        setAlignment(Pos.CENTER);
        setVisible(true);
        setPickOnBounds(false);

        FadeTransition a = new FadeTransition(Duration.millis(2000), this);
        a.setFromValue(0.0);
        a.setToValue(1.0);

        setPadding(new Insets(60));
        setStyle("-fx-background-color: #FFFFFFEE");

        Label outcome = new Label(b.isGameWon() ? "You won!" : "You lost...");
        outcome.setStyle("-fx-font-size: 36px");

        getChildren().add(outcome);

        a.play();

    }
    
}
