/*
 * Michael Pu
 * TetrisGameAI - GameArea
 * ICS3U1 - Mr. Radulovic
 * December 23, 2017
 */

package frontend;

import backend.TetrominoBlueprint;
import backend.Updatable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;

public class GameArea extends Canvas implements Updatable {

    private static final int NUM_ROWS = 20;
    private static final int NUM_COLS = 10;

    private static final Color LINE_COLOUR = Color.GREY;
    private static final double LINE_WIDTH = 0.5;

    private static final Color CELL_OUTLINE_COLOUR = Color.BLACK;
    private static final double CELL_OUTLINE_WIDTH = 3;

    private static final double TETROMINO_UPDATE_INTERVAL = 1;

    private Color mBgColour;
    private GraphicsContext mGc;
    private double mWidth;
    private double mHeight;
    private double mCellWidth;
    private double cellHeight;
    private Cell[][] mGrid;
    private Tetromino mCurTetromino;
    private double  mTetrominoUpdateTime;

    public GameArea(double width, double height, Color bgColour) {
        super(width, height);
        this.mHeight = height;
        this.mWidth = width;
        this.mBgColour = bgColour;
        cellHeight = this.mHeight /NUM_ROWS;
        mCellWidth = this.mWidth /NUM_COLS;
        mGc = getGraphicsContext2D();
        mGrid = new Cell[NUM_COLS][NUM_ROWS];
        for (int i=0; i<NUM_COLS; i++) {
            for (int j=0; j<NUM_ROWS; j++) {
                mGrid[i][j] = new Cell();
                if (Math.random() < 0.3 && j > 10) {
                    mGrid[i][j].fill(Color.BLUE);
                }
            }
        }
        mTetrominoUpdateTime = TETROMINO_UPDATE_INTERVAL;
        mCurTetromino = new Tetromino(this, TetrominoBlueprint.S, NUM_COLS);
    }

    public void moveLeft() {
        System.out.println("LEFT");
        if (mCurTetromino != null) {
            mCurTetromino.moveLeft();
        }
    }

    public void moveRight() {
        System.out.println("RIGHT");
        if (mCurTetromino != null) {
            mCurTetromino.moveRight();
        }
    }

    public void moveDown() {
        System.out.println("DOWN");
        if (mCurTetromino != null) {
            mCurTetromino.moveDown();
        }
    }

    public void rotate() {
        System.out.println("ROTATE");
        if (mCurTetromino != null) {
            mCurTetromino.rotate();
        }
    }

    public void drop() {
        System.out.println("DROP");
        while (mCurTetromino.moveDown());
        mCurTetromino.freeze();
    }

    public Cell[][] getmGrid() {
        return mGrid;
    }

    private void drawCell(int x, int y, Color colour) {
        mGc.setFill(colour.brighter());
        mGc.fillRoundRect(x* mCellWidth, y* cellHeight, mCellWidth, cellHeight, 5, 5);
        mGc.setFill(colour);
        mGc.fillRoundRect(x* mCellWidth +CELL_OUTLINE_WIDTH, y* cellHeight +CELL_OUTLINE_WIDTH,
                mCellWidth -(CELL_OUTLINE_WIDTH*2), cellHeight -(CELL_OUTLINE_WIDTH*2), 5, 5);
    }

    public void update(double deltaTime) {

        mGc.setFill(mBgColour);
        mGc.fillRect(0, 0, mWidth, mHeight);

        for (int i=0; i<NUM_COLS; i++) {
            for (int j=0; j<NUM_ROWS; j++) {
                if (mGrid[i][j].ismIsFilled()) {
                    drawCell(i, j, mGrid[i][j].getmColour());
                }
            }
        }

        int[][] tBody = mCurTetromino.getmBody();
        Point tPos = mCurTetromino.getmCurPos();
        for (int i = 0; i < tBody.length; i++) {
            for (int j = 0; j < tBody[0].length; j++) {
                if (tBody[i][j] == 1) {
                    drawCell(tPos.x + j, tPos.y + i, mCurTetromino.getmColour());
                }
            }
        }

        if (mCurTetromino != null) {
            mTetrominoUpdateTime -= deltaTime;
            if (mTetrominoUpdateTime <= 0) {
                mCurTetromino.update();
                mTetrominoUpdateTime = TETROMINO_UPDATE_INTERVAL;
            }
        }

        mGc.setStroke(LINE_COLOUR);
        mGc.setLineWidth(LINE_WIDTH);

        for (int col=0; col<NUM_COLS; col++) {
            mGc.strokeLine(col*mCellWidth, 0,col*mCellWidth, mHeight);
        }

        for (int row=0; row<NUM_ROWS; row++) {
            mGc.strokeLine(0,row*cellHeight, mWidth, row*cellHeight);
        }
    }

}
