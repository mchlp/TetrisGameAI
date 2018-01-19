/*
 * Michael Pu
 * TetrisGameAI - InstructionsWindow
 * ICS3U1 - Mr.Radulovic
 * January 18, 2018
 */

package frontend.instructions;

import backend.Utilities;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class InstructionsDialog extends Stage {

    private static final String UP_IMAGE = Utilities.IMAGES_DIRECTORY  + "up.png";
    private static final String DOWN_IMAGE = Utilities.IMAGES_DIRECTORY  + "down.png";
    private static final String LEFT_IMAGE = Utilities.IMAGES_DIRECTORY  + "left.png";
    private static final String RIGHT_IMAGE = Utilities.IMAGES_DIRECTORY  + "right.png";
    private static final String G_IMAGE = Utilities.IMAGES_DIRECTORY  + "g.png";
    private static final String P_IMAGE = Utilities.IMAGES_DIRECTORY  + "p.png";
    private static final String R_IMAGE = Utilities.IMAGES_DIRECTORY  + "r.png";
    private static final String SPACE_IMAGE = Utilities.IMAGES_DIRECTORY  + "space.png";


    private static final int ELEMENT_SPACING = 10;
    private static final int PADDING = 10;
    private static final int WINDOW_WIDTH = 400;

    public InstructionsDialog(Stage parent) {

        VBox keyBox = new VBox(ELEMENT_SPACING);
        keyBox.setPadding(new Insets(PADDING));

        keyBox.getChildren().add(new KeyInstructionsBar("Rotate", getImage(UP_IMAGE)));
        keyBox.getChildren().add(new KeyInstructionsBar("Move Down", getImage(DOWN_IMAGE)));
        keyBox.getChildren().add(new KeyInstructionsBar("Move Left", getImage(LEFT_IMAGE)));
        keyBox.getChildren().add(new KeyInstructionsBar("Move Right", getImage(RIGHT_IMAGE)));
        keyBox.getChildren().add(new KeyInstructionsBar("Drop", getImage(SPACE_IMAGE)));
        keyBox.getChildren().add(new KeyInstructionsBar("Toggle Gridlines", getImage(G_IMAGE)));
        keyBox.getChildren().add(new KeyInstructionsBar("Toggle Pause", getImage(P_IMAGE)));
        keyBox.getChildren().add(new KeyInstructionsBar("Restart", getImage(R_IMAGE)));

        setWidth(WINDOW_WIDTH);

        setTitle("Instructions");
        setScene(new Scene(keyBox));
        setResizable(false);
        initOwner(parent);
        initModality(Modality.WINDOW_MODAL);
    }

    private Image getImage(String imageName) {
        return new Image(Utilities.getResourceAsURLString(imageName));
    }
}
