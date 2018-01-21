/*
 * Michael Pu
 * TetrisGameAI - InstructionsDialog
 * ICS3U1 - Mr.Radulovic
 * January 20, 2018
 */

package frontend.instructions;

import backend.Utilities;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Dialog that opens to display the controls for the game.
 */
public class InstructionsDialog extends Stage {

    // images for keys
    private static final String UP_IMAGE = "up.png";
    private static final String DOWN_IMAGE = "down.png";
    private static final String LEFT_IMAGE = "left.png";
    private static final String RIGHT_IMAGE = "right.png";
    private static final String G_IMAGE = "g.png";
    private static final String P_IMAGE = "p.png";
    private static final String R_IMAGE = "r.png";
    private static final String SPACE_IMAGE = "space.png";


    private static final int ELEMENT_SPACING = 10;
    private static final int PADDING = 10;
    private static final int WINDOW_WIDTH = 400;

    public InstructionsDialog(Stage parent) {

        // set up VBox
        VBox keyBox = new VBox(ELEMENT_SPACING);
        keyBox.setPadding(new Insets(PADDING));

        // add keys to VBox
        keyBox.getChildren().add(new KeyInstructionsBar("Rotate", Utilities.getImage(UP_IMAGE)));
        keyBox.getChildren().add(new KeyInstructionsBar("Move Down", Utilities.getImage(DOWN_IMAGE)));
        keyBox.getChildren().add(new KeyInstructionsBar("Move Left", Utilities.getImage(LEFT_IMAGE)));
        keyBox.getChildren().add(new KeyInstructionsBar("Move Right", Utilities.getImage(RIGHT_IMAGE)));
        keyBox.getChildren().add(new KeyInstructionsBar("Drop", Utilities.getImage(SPACE_IMAGE)));
        keyBox.getChildren().add(new KeyInstructionsBar("Toggle Gridlines", Utilities.getImage(G_IMAGE)));
        keyBox.getChildren().add(new KeyInstructionsBar("Toggle Pause", Utilities.getImage(P_IMAGE)));
        keyBox.getChildren().add(new KeyInstructionsBar("Restart", Utilities.getImage(R_IMAGE)));

        setWidth(WINDOW_WIDTH);

        // set up window
        setTitle("Instructions");
        setScene(new Scene(keyBox));
        setResizable(false);
        initOwner(parent);
        initModality(Modality.WINDOW_MODAL);
    }
}
