/*
 * Michael Pu
 * TetrisGameAI - Cell
 * ICS3U1 - Mr. Radulovic
 * December 23, 2017
 */

package frontend;

import javafx.scene.paint.Color;

public class Cell {

    private Color mColour;
    private boolean mIsFilled;

    public Cell() {
        mIsFilled = false;
    }

    public void fill(Color colour) {
        this.mColour = colour;
        mIsFilled = true;
    }

    public boolean ismIsFilled() {
        return mIsFilled;
    }

    public Color getmColour() {
        return mColour;
    }
}
