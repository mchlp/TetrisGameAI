/*
 * Michael Pu
 * TetrisGameAI - StatsBar
 * ICS3U1 - Mr. Radulovic
 * December 23, 2017
 */

package frontend;

import backend.Updatable;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class StatsBar extends BorderPane implements Updatable {

    private static final int FONT_SIZE = 15;
    private static final int PADDING_SIZE = 10;
    private static final String DEFAULT_STYLE = "" +
            "-fx-background-color: lightgrey; " +
            "-fx-opacity: 1;";

    private Text mLabelText;
    private Text mValueText;

    public StatsBar(String label, String value) {
        super();
        mLabelText = new Text(label);
        mLabelText.setFont(new Font(FONT_SIZE));
        mValueText = new Text(value);
        mLabelText.setFont(new Font(FONT_SIZE));

        setPadding(new Insets(PADDING_SIZE));
        setStyle(DEFAULT_STYLE);
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        setLeft(mLabelText);
        setRight(mValueText);
    }

    @Override
    public void update(double deltaTime) {

    }
}
