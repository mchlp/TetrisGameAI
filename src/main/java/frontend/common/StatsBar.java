/*
 * Michael Pu
 * TetrisGameAI - StatsBar
 * ICS3U1 - Mr.Radulovic
 * January 20, 2018
 */

package frontend.common;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Displays a stat in a rectangular box with the name of the stat on the left and the value of the stat on the right.
 */
public class StatsBar extends AnchorPane {

    private static final int FONT_SIZE = 13;
    private static final int SMALLER_FONT_SIZE = 10;
    private static final int STRING_LENGTH_FOR_SMALLER_FONT = 20;

    private static final int PADDING_SIZE = 10;

    private Text mLabelText;
    private Text mValueText;

    public StatsBar(String label, String value) {
        super();

        // label of the stat
        mLabelText = new Text(label);
        mLabelText.setFont(new Font(FONT_SIZE));

        // value of the stat
        mValueText = new Text(value);
        mValueText.setFont(new Font(FONT_SIZE));
        mValueText.setTextAlignment(TextAlignment.RIGHT);
        // set wrap length for the value label to half of the width of the statsBar
        mValueText.wrappingWidthProperty().bind(widthProperty().divide(2));

        // set up display properties
        setPadding(new Insets(PADDING_SIZE));
        setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        // add the label text to the pane
        setTopAnchor(mLabelText, 0.0);
        setLeftAnchor(mLabelText, 0.0);
        getChildren().add(mLabelText);

        // add the value text to the pane
        setTopAnchor(mValueText, 0.0);
        setRightAnchor(mValueText, 0.0);
        getChildren().add(mValueText);
    }

    /**
     * @param value The new value of the stat.
     */
    public void setValue(String value) {
        if (value.length() > STRING_LENGTH_FOR_SMALLER_FONT) {
            mValueText.setFont(new Font(SMALLER_FONT_SIZE));
        } else {
            mValueText.setFont(new Font(FONT_SIZE));
        }
        mValueText.setText(value);
    }

    /**
     * @param colour The new background colour of the StatsBar.
     */
    public void setColour(Color colour) {
        setBackground(new Background(new BackgroundFill(colour, CornerRadii.EMPTY, Insets.EMPTY)));
    }
}
