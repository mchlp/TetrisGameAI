/*
 * Michael Pu
 * TetrisGameAI - Cell
 * ICS3U1 - Mr.Radulovic
 * January 18, 2018
 */

package frontend.common;

import javafx.scene.paint.Color;

/**
 * Represents one cell in the game grid.
 */
public class Cell {

    /**
     * The colour of the cell.
     */
    private Color mColour;

    /**
     * If the cell is filled.
     */
    private boolean mIsFilled;

    public Cell() {
        mIsFilled = false;
    }

    /**
     * Fills the cell with a specific colour.
     *
     * @param colour Colour of the cell.
     */
    public void fill(Color colour) {
        this.mColour = colour;
        mIsFilled = true;
    }

    public boolean ismIsFilled() {
        return mIsFilled;
    }

    Color getmColour() {
        return mColour;
    }

    /**
     * @return A deep copy of the {@link Cell}.
     */
    public Cell duplicate() {
        Cell newCell = new Cell();
        if (ismIsFilled()) {
            newCell.fill(mColour);
        }
        return newCell;
    }
}
