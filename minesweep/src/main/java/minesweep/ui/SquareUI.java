package minesweep.ui;

import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import minesweep.game.*;

public class SquareUI extends StackPane {

    public final static int SQUARE_SIZE = 20;
    public final static Color[] SQUARE_COLOURS = {
        Color.BLACK, // 0 included for simplicity's sake
        Color.BLUE,
        Color.GREEN,
        Color.RED,
        Color.PURPLE,
        Color.MAROON,
        Color.DARKSLATEGRAY,
        Color.BLACK,
        Color.DARKSLATEGRAY,
    };

    private Square s;

    public SquareUI(BoardLogic b, Square s, GridPane g) {
        
        this.s = s;

        addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                b.guess(s);
                g.getChildren().forEach(child -> { ((SquareUI) child).update(); });
            } else {
                b.toggleFlag(s);
            }
            update();
        });

        update();

    }

    public void update() {
        this.getChildren().clear();

        Rectangle box = new Rectangle(SQUARE_SIZE, SQUARE_SIZE);

        Label text;
        if ((s.isFlagged && !s.isRevealed) || (s.mineNeighbours == 0 && !s.isMine)) {
            text = new Label("");
        } else if (s.isMine) {
            text = new Label("X");
        } else {
            text = new Label(String.valueOf(s.mineNeighbours));
        }

        text.setTextFill(s.isMine ? Color.BLACK : SQUARE_COLOURS[s.mineNeighbours]);
        text.setVisible(s.isRevealed);

        if (s.isRevealed) {
            box.setFill(Color.LIGHTGREY);
            box.setStroke(Color.GREY);
        } else if (s.isFlagged) {
            box.setFill(Color.ORANGE);
            box.setStroke(Color.DARKORANGE);
        } else {
            box.setFill(Color.BLUE);
            box.setStroke(Color.DARKBLUE);
        }

        getChildren().addAll(box, text);
    }

}
