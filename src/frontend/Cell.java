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
    private boolean mFilled;

    public Cell() {
        mFilled = false;
    }

    public void fill(Color colour) {
        mColour = colour;
        mFilled = true;
    }

    public boolean ismFilled() {
        return mFilled;
    }

    public Color getmColour() {
        return mColour;
    }
}
