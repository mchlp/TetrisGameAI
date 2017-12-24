/*
 * Michael Pu
 * TetrisGameAI - Cell
 * ICS3U1 - Mr. Radulovic
 * December 23, 2017
 */

package frontend;

import javafx.scene.paint.Color;

public class Cell {

    private Color colour;
    private boolean filled;

    public Cell() {
        filled = false;
    }

    public void fill(Color colour) {
        this.colour = colour;
        filled = true;
    }

    public boolean isFilled() {
        return filled;
    }

    public Color getColour() {
        return colour;
    }
}
