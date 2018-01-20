/*
 * Michael Pu
 * TetrisGameAI - GameGrid
 * ICS3U1 - Mr.Radulovic
 * January 13, 2018
 */

package frontend.common;

import backend.Tetromino;
import javafx.scene.paint.Color;

import java.awt.*;

/**
 * Represents the grid of the game which stores the colour and the state of each cell in the grid.
 */
public class GameGrid {

    private Cell[][] mGrid;
    private int mWidth;
    private int mHeight;

    /**
     * Number of rows on top of the grid that is not visible so that new Tetrominos can spawn even if the top of the
     * visible portion of the grid is filled.
     */
    private int mExtraRows;

    public GameGrid(int width, int height, int extraRows) {
        mWidth = width;
        mHeight = height;
        mExtraRows = extraRows;
        mGrid = new Cell[mWidth][mHeight];
        resetGrid();
    }

    public int getmWidth() {
        return mWidth;
    }

    public int getmHeight() {
        return mHeight;
    }

    public boolean isFilled(int x, int y) {
        return getCell(x, y).ismIsFilled();
    }

    public Color getColour(int x, int y) {
        return getCell(x, y).getmColour();
    }

    /**
     * Sets all cells in the grid to an unfilled, uncoloured cell.
     */
    public void resetGrid() {
        for (int i = 0; i < mWidth; i++) {
            for (int j = 0; j < mHeight; j++) {
                setCell(i, j, new Cell());
            }
        }
    }

    /**
     * Applies a {@link Tetromino} to the current game grid, finalizing its position.
     *
     * @param tetromino The {@link Tetromino} to apply.
     */
    public void applyTetromino(Tetromino tetromino) {
        Point tPos = tetromino.getmCurPos();
        int[][] tBody = tetromino.getmBody();
        // loop through each cell in the Tetromino and fill the corresponding cell in the grid with its colour
        for (int i = 0; i < tBody.length; i++) {
            for (int j = 0; j < tBody[0].length; j++) {
                if (tBody[i][j] == 1) {
                    getCell(tPos.x + j, tPos.y + i).fill(tetromino.getmColour());
                }
            }
        }
    }

    /**
     * @return <code>true</code> if the game is over, <code>false</code> otherwise.
     */
    public boolean checkGameOver() {
        // checks if any of the columns in the top visible row are filled
        for (int i = 0; i < mGrid.length; i++) {
            if (isFilled(i, mExtraRows)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks the grid for completed rows and removes them.
     *
     * @return The number of completed rows that were cleared.
     */
    public int checkCompleteRows() {
        int numRowsCleared = 0;
        // loop through each row
        for (int i = 0; i < mHeight; i++) {
            boolean rowFull = true;
            // loop through each column in the row
            for (int j = 0; j < mWidth; j++) {
                if (!isFilled(j, i)) {
                    rowFull = false;
                    break;
                }
            }
            // if the row is filled, clear it
            if (rowFull) {
                clearRow(i);
                numRowsCleared++;
            }
        }
        return numRowsCleared;
    }

    /**
     * Removes all the cells in the specified row and shifts all rows above it one space down to.
     * @param rowNum The number of the row to clear.
     */
    private void clearRow(int rowNum) {
        for (int i = rowNum; i >= 0; i--) {
            for (int j = 0; j < mGrid.length; j++) {
                if (i == 0) {
                    // if it is the first row in the grid, set it to an empty row (nothing to shift down)
                    setCell(j, i, new Cell());
                } else {
                    // if it is not the first row in the grid, shift it down one row
                    setCell(j, i, getCell(j, i - 1));
                }
            }
        }
    }

    private void setCell(int x, int y, Cell cell) {
        mGrid[x][y] = cell;
    }

    public Cell getCell(int x, int y) {
        return mGrid[x][y];
    }

    /**
     * @return A deep copy of the object.
     */
    public GameGrid duplicate() {
        GameGrid newGrid = new GameGrid(mWidth, mHeight, mExtraRows);
        for (int i = 0; i < newGrid.getmWidth(); i++) {
            for (int j = 0; j < newGrid.getmHeight(); j++) {
                Cell newCell = getCell(i, j).duplicate();
                newGrid.setCell(i, j, newCell);
            }
        }
        return newGrid;
    }

}
