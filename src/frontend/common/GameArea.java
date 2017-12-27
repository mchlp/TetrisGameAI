/*
 * Michael Pu
 * TetrisGameAI - GameArea
 * ICS3U1 - Mr. Radulovic
 * December 26, 2017
 */

package frontend.common;

import backend.GameBrain;
import backend.TetrominoBlueprint;
import backend.Updatable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.Random;

public class GameArea extends Canvas implements Updatable {

    public static final double CELL_OUTLINE_WIDTH = 3;
    public static final Color CELL_OUTLINE_COLOUR = Color.BLACK;
    private static final Color LINE_COLOUR = Color.GREY;
    private static final double LINE_WIDTH = 0.5;

    private double mWidth;
    private double mHeight;
    private Color mBgColour;
    private GraphicsContext mGc;
    private double mCellWidth;
    private double mCellHeight;
    private double mTetrominoUpdateTime;
    private boolean mShowGridlines;
    private GameBrain mGameBrain;
    private double mElapsedTime;

    public GameArea(double width, double height, Color bgColour, GameMode gameMode) {
        super(width, height);
        mGameBrain = new GameBrain(gameMode);
        mHeight = height;
        mWidth = width;
        mBgColour = bgColour;
        mCellHeight = this.mHeight / mGameBrain.getNumRows();
        mCellWidth = this.mWidth / mGameBrain.getNumCols();
        mGc = getGraphicsContext2D();
        mShowGridlines = true;
        newGame();
    }

    private void newGame() {
        mGameBrain.newGame();
        mElapsedTime = 0;
        spawnTetromino();
        drawGame();
    }

    void moveLeft() {
        mGameBrain.moveLeft();
    }

    void moveRight() {
        mGameBrain.moveRight();
    }

    void moveDown() {
        mGameBrain.moveDown();
    }

    void rotate() {
        mGameBrain.rotate();
    }

    void drop() {
        mGameBrain.drop();
    }

    void restart() {
        newGame();
    }

    void togglePause() {
        mGameBrain.togglePause();
    }

    void incrementLevel() {
        mGameBrain.incrementLevel();
    }

    void toggleGridlines() {
        mShowGridlines = !mShowGridlines;
    }

    public GameGrid getmGrid() {
        return mGameBrain.getmGrid();
    }

    private void drawCell(int x, int y, Color colour) {
        if (y >= mGameBrain.getExtraRowsAtTop()) {
            int screenY = y - mGameBrain.getExtraRowsAtTop();
            mGc.setFill(CELL_OUTLINE_COLOUR);
            mGc.fillRoundRect(x * mCellWidth, screenY * mCellHeight, mCellWidth, mCellHeight, 5, 5);
            mGc.setFill(colour);
            mGc.fillRoundRect(x * mCellWidth + CELL_OUTLINE_WIDTH, screenY * mCellHeight + CELL_OUTLINE_WIDTH,
                    mCellWidth - (CELL_OUTLINE_WIDTH * 2), mCellHeight - (CELL_OUTLINE_WIDTH * 2), 5, 5);
        }
    }

    public void updateStats() {
        mGameBrain.updateStats();
    }

    void spawnTetromino() {
        mGameBrain.createTetromino();
        mTetrominoUpdateTime = calculateDropSpeed();
    }

    private void drawGame() {

        setEffect(null);
        mGc.setFill(mBgColour);
        mGc.fillRect(0, 0, mWidth, mHeight);

        for (int i = 0; i < mGameBrain.getmGrid().getmWidth(); i++) {
            for (int j = 0; j < mGameBrain.getmGrid().getmHeight(); j++) {
                if (mGameBrain.getmGrid().isFilled(i, j)) {
                    drawCell(i, j, mGameBrain.getmGrid().getColour(i, j));
                }
            }
        }

        int[][] tBody = mGameBrain.getmCurTetromino().getmBody();
        Point tPos = mGameBrain.getmCurTetromino().getmCurPos();
        for (int i = 0; i < tBody.length; i++) {
            for (int j = 0; j < tBody[0].length; j++) {
                if (tBody[i][j] == 1) {
                    drawCell(tPos.x + j, tPos.y + i, mGameBrain.getmCurTetromino().getmColour());
                }
            }
        }

        if (mGameBrain.getmGameState() == GameState.PAUSED) {
            setEffect(new GaussianBlur(15));
        }

        if (mShowGridlines) {
            mGc.setStroke(LINE_COLOUR);
            mGc.setLineWidth(LINE_WIDTH);

            for (int col = 0; col < mGameBrain.getNumCols(); col++) {
                mGc.strokeLine(col * mCellWidth, 0, col * mCellWidth, mHeight);
            }

            for (int row = 0; row < mGameBrain.getNumRows(); row++) {
                mGc.strokeLine(0, row * mCellHeight, mWidth, row * mCellHeight);
            }
        }
    }

    public void update(double deltaTime) {
        if (mGameBrain.getmGameState() == GameState.PLAYING) {
            mElapsedTime += deltaTime;
            if (mGameBrain.getmCurTetromino() != null) {
                mTetrominoUpdateTime -= deltaTime;
                if (mTetrominoUpdateTime <= 0) {
                    mGameBrain.update();
                    mTetrominoUpdateTime = calculateDropSpeed();
                }
            }
        }
        drawGame();
    }

    private double calculateDropSpeed() {
        // return -0.04242*mLevel + 0.6884;
        return (725 * Math.pow(0.85, mGameBrain.getmLevel()) + mGameBrain.getmLevel()) / 1000;
        //return Math.pow(0.8 - ((mLevel - 1) * 0.007), mLevel - 1);
    }

    public double getmCellWidth() {
        return mCellWidth;
    }

    public double getmCellHeight() {
        return mCellHeight;
    }

    public double getmElapsedTime() {
        return mElapsedTime;
    }

    public GameBrain getmGameBrain() {
        return mGameBrain;
    }
}
