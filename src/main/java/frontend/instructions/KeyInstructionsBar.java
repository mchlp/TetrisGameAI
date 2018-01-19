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

public class KeyInstructionsBar extends BorderPane {

    private static final int FONT_SIZE = 20;
    private static final int PADDING_SIZE = 10;
    private static final int BAR_HEIGHT = 40;

    public KeyInstructionsBar(String keyFunction, Image keyImage) {

        setPadding(new Insets(PADDING_SIZE));
        setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        ImageView keyImageView = new ImageView(keyImage);
        keyImageView.setPreserveRatio(true);
        keyImageView.setFitHeight(BAR_HEIGHT);
        setLeft(keyImageView);

        Text keyText = new Text(keyFunction);
        keyText.setFont(new Font(FONT_SIZE));
        setRight(keyText);
    }
}
