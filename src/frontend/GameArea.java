/*
 * Michael Pu
 * TetrisGameAI - GameArea
 * ICS3U1 - Mr. Radulovic
 * December 23, 2017
 */

package frontend;

import backend.Updatable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GameArea extends Canvas implements Updatable {

    private static final int NUM_ROWS = 20;
    private static final int NUM_COLS = 10;

    private static final Color LINE_COLOUR = Color.GREY;
    private static final double LINE_WIDTH = 0.5;

    private static final Color CELL_OUTLINE_COLOUR = Color.BLACK;
    private static final double CELL_OUTLINE_WIDTH = 3;

    private Color mBgColour;
    private GraphicsContext mGc;
    private double mWidth;
    private double mHeight;
    private double mCellWidth;
    private double mCellHeight;
    private Cell[][] mGrid;

    public GameArea(double width, double height, Color bgColour) {
        super(width, height);
        mHeight = height;
        mWidth = width;
        mBgColour = bgColour;
        mCellHeight = mHeight/NUM_ROWS;
        mCellWidth = mWidth/NUM_COLS;
        mGc = getGraphicsContext2D();
        mGrid = new Cell[NUM_COLS][NUM_ROWS];
        for (int i=0; i<NUM_COLS; i++) {
            for (int j=0; j<NUM_ROWS; j++) {
                mGrid[i][j] = new Cell();
                if (Math.random() < 0.2) {
                   mGrid[i][j].fill(Color.GREEN);
                }
            }
        }
    }

    public void moveLeft() {
        System.out.println("LEFT");
    }

    public void moveRight() {
        System.out.println("RIGHT");
    }

    public void moveDown() {
        System.out.println("DOWN");
    }

    public void rotate() {
        System.out.println("ROTATE");
    }

    public void drop() {
        System.out.println("DROP");
    }

    public void update(double deltaTime) {

        mGc.setFill(mBgColour);
        mGc.fillRect(0, 0, mWidth, mHeight);

        for (int i=0; i<NUM_COLS; i++) {
            for (int j=0; j<NUM_ROWS; j++) {
                if (mGrid[i][j].ismFilled()) {
                    //mGc.setStroke(CELL_OUTLINE_COLOUR);
                    //mGc.setLineWidth(2);
                    //mGc.strokeRect(i*mCellWidth, j*mCellHeight, mCellWidth, mCellHeight);
                    mGc.setFill(mGrid[i][j].getmColour().brighter());
                    mGc.fillRoundRect(i*mCellWidth, j*mCellHeight, mCellWidth, mCellHeight, 5, 5);
                    mGc.setFill(mGrid[i][j].getmColour());
                    mGc.fillRoundRect(i*mCellWidth+CELL_OUTLINE_WIDTH, j*mCellHeight+CELL_OUTLINE_WIDTH,
                            mCellWidth-(CELL_OUTLINE_WIDTH*2), mCellHeight-(CELL_OUTLINE_WIDTH*2), 5, 5);
                }
            }
        }

        mGc.setStroke(LINE_COLOUR);
        mGc.setLineWidth(LINE_WIDTH);

        /*
        for (int col=0; col<NUM_COLS; col++) {
            mGc.strokeLine(col*mCellWidth, 0,col*mCellWidth, mHeight);
        }

        for (int row=0; row<NUM_ROWS; row++) {
            mGc.strokeLine(0,row*mCellHeight, mWidth, row*mCellHeight);
        }*/
    }

}
