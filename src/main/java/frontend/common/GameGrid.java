/*
 * Michael Pu
 * TetrisGameAI - GameGrid
 * ICS3U1 - Mr. Radulovic
 * January 01, 2018
 */

package frontend.common;

import backend.Tetromino;
import javafx.scene.paint.Color;

import java.awt.*;

public class GameGrid {

    private Cell[][] mGrid;
    private int mWidth;
    private int mHeight;
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

    public void resetGrid() {
        for (int i = 0; i < mWidth; i++) {
            for (int j = 0; j < mHeight; j++) {
                setCell(i, j, new Cell());
            }
        }
    }

    public void applyTetromino(Tetromino tetromino) {
        Point tPos = tetromino.getmCurPos();
        int[][] tBody = tetromino.getmBody();
        for (int i = 0; i < tBody.length; i++) {
            for (int j = 0; j < tBody[0].length; j++) {
                if (tBody[i][j] == 1) {
                    getCell(tPos.x + j, tPos.y + i).fill(tetromino.getmColour());
                }
            }
        }
    }

    public boolean checkGameOver() {
        for (int i = 0; i < mGrid.length; i++) {
            if (isFilled(i, mExtraRows)) {
                return true;
            }
        }
        return false;
    }

    public int checkCompleteRows() {
        int numRowsCleared = 0;
        for (int i = 0; i < mHeight; i++) {
            boolean rowFull = true;
            for (int j = 0; j < mWidth; j++) {
                if (!isFilled(j, i)) {
                    rowFull = false;
                    break;
                }
            }
            if (rowFull) {
                clearRow(i);
                numRowsCleared++;
            }
        }
        return numRowsCleared;
    }

    private void clearRow(int rowNum) {
        for (int i = rowNum; i >= 0; i--) {
            for (int j = 0; j < mGrid.length; j++) {
                if (i == 0) {
                    setCell(j, i, new Cell());
                } else {
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
