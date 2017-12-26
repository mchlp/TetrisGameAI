/*
 * Michael Pu
 * TetrisGameAI - OutputConsole
 * ICS3U1 - Mr. Radulovic
 * December 26, 2017
 */

package frontend.aiFastTrain;

import javafx.scene.control.TextArea;

public class OutputConsole extends TextArea {

    public static final int MAX_NUMBER_OF_LINES = 300;

    public OutputConsole() {
        super();
    }

    @Override
    public void appendText(String text) {
        if (getText().split("\n").length > MAX_NUMBER_OF_LINES) {
            clear();
        }
        super.appendText(text);
    }
}
