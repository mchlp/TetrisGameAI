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

    private static final Color BACKGROUND_COLOUR = Color.BLACK;

    private static final Color LINE_COLOUR = Color.GREY;
    private static final double LINE_WIDTH = 0.5;

    private GraphicsContext mGc;
    private double mWidth;
    private double mHeight;
    private double mCellWidth;
    private double mCellHeight;

    public GameArea(double width, double height) {
        super(width, height);
        mHeight = height;
        mWidth = width;
        mCellHeight = mHeight/NUM_ROWS;
        mCellWidth = mWidth/NUM_COLS;
        mGc = getGraphicsContext2D();
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

    public void update(double deltaTime) {

        mGc.setFill(BACKGROUND_COLOUR);
        mGc.fillRect(0, 0, mWidth, mHeight);

        mGc.setStroke(LINE_COLOUR);
        mGc.setLineWidth(LINE_WIDTH);

        for (int col=0; col<NUM_COLS; col++) {
            mGc.strokeLine(col*mCellWidth, 0,col*mCellWidth, mHeight);
        }

        for (int row=0; row<NUM_ROWS; row++) {
            mGc.strokeLine(0,row*mCellHeight, mWidth, row*mCellHeight);
        }
    }

}
