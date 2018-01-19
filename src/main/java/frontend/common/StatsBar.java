/*
 * Michael Pu
 * TetrisGameAI - StatsBar
 * ICS3U1 - Mr.Radulovic
 * January 13, 2018
 */

package frontend.common;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class StatsBar extends AnchorPane {

    private static final int FONT_SIZE = 13;
    private static final int SMALLER_FONT_SIZE = 10;
    private static final int STRING_LENGTH_FOR_SMALLER_FONT = 20;

    private static final int PADDING_SIZE = 10;

    private Text mLabelText;
    private Text mValueText;

    public StatsBar(String label, String value) {
        super();
        mLabelText = new Text(label);
        mLabelText.setFont(new Font(FONT_SIZE));
        mValueText = new Text(value);
        mValueText.setFont(new Font(FONT_SIZE));
        mValueText.setTextAlignment(TextAlignment.RIGHT);
        mValueText.wrappingWidthProperty().bind(widthProperty().divide(2));

        //setAlignment(mLabelText, Pos.CENTER);
        //setAlignment(mValueText, Pos.CENTER);
        setPadding(new Insets(PADDING_SIZE));
        setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        setTopAnchor(mLabelText, 0.0);
        setLeftAnchor(mLabelText, 0.0);
        getChildren().add(mLabelText);
        setTopAnchor(mValueText, 0.0);
        setRightAnchor(mValueText, 0.0);
        getChildren().add(mValueText);
    }

    public void setValue(String value) {
        if (value.length() > STRING_LENGTH_FOR_SMALLER_FONT) {
            mValueText.setFont(new Font(SMALLER_FONT_SIZE));
        } else {
            mValueText.setFont(new Font(FONT_SIZE));
        }
        mValueText.setText(value);
    }

    public void setColour(Color colour) {
        setBackground(new Background(new BackgroundFill(colour, CornerRadii.EMPTY, Insets.EMPTY)));
    }
}
