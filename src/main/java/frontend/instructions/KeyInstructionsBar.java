/*
 * Michael Pu
 * TetrisGameAI - KeyInstructionsBar
 * ICS3U1 - Mr.Radulovic
 * January 18, 2018
 */

package frontend.instructions;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Display a key binding in an rectangular box with the image of the key on the left and the action the key performs on
 * the right.
 */
public class KeyInstructionsBar extends BorderPane {

    private static final int FONT_SIZE = 15;
    private static final int PADDING_SIZE = 10;
    private static final int BAR_HEIGHT = 30;

    public KeyInstructionsBar(String keyFunction, Image keyImage) {

        // set up display properties
        setPadding(new Insets(PADDING_SIZE));
        setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        // set up image on the left
        ImageView keyImageView = new ImageView(keyImage);
        keyImageView.setPreserveRatio(true);
        keyImageView.setFitHeight(BAR_HEIGHT);
        setLeft(keyImageView);

        // set up the text on the right
        Text keyText = new Text(keyFunction);
        keyText.setFont(new Font(FONT_SIZE));
        setRight(keyText);
    }
}
