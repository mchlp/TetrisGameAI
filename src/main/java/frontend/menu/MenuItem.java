/*
 * Michael Pu
 * TetrisGameAI - MenuItem
 * ICS3U1 - Mr. Radulovic
 * December 28, 2017
 */

package frontend.menu;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class MenuItem extends Button {

    private static final int PADDING_SIZE = 10;

    private static final Color SELECTED_COLOUR = Color.GREEN;
    private static final Color NORMAL_COLOUR = Color.LIGHTGREEN;

    public MenuItem(String text, Menu menu) {

        setText(text);
        setTextAlignment(TextAlignment.CENTER);
        prefWidthProperty().bind(menu.widthProperty());

        setPadding(new Insets(PADDING_SIZE));
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        setBackground(new Background(new BackgroundFill(NORMAL_COLOUR, CornerRadii.EMPTY, Insets.EMPTY)));

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent hover) {
                mouseEnter();
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseExit();
            }
        });
    }

    private void mouseEnter() {
        setBackground(new Background(new BackgroundFill(SELECTED_COLOUR, CornerRadii.EMPTY, Insets.EMPTY)));
        getScene().setCursor(Cursor.HAND);
    }

    private void mouseExit() {
        setBackground(new Background(new BackgroundFill(NORMAL_COLOUR, CornerRadii.EMPTY, Insets.EMPTY)));
        getScene().setCursor(Cursor.DEFAULT);
    }
}