/*
 * Michael Pu
 * TetrisGameAI - Cell
 * ICS3U1 - Mr. Radulovic
 * January 01, 2018
 */

package frontend.common;

import javafx.scene.paint.Color;

public class Cell {

    private Color mColour;
    private boolean mIsFilled;

    public Cell() {
        mIsFilled = false;
    }

    void fill(Color colour) {
        this.mColour = colour;
        mIsFilled = true;
    }

    public boolean ismIsFilled() {
        return mIsFilled;
    }

    Color getmColour() {
        return mColour;
    }

    public Cell duplicate() {
        Cell newCell = new Cell();
        if (ismIsFilled()) {
            newCell.fill(mColour);
        }
        return newCell;
    }
}