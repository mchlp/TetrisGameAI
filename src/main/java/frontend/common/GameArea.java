/*
 * Michael Pu
 * TetrisGameAI - GameArea
 * ICS3U1 - Mr.Radulovic
 * January 13, 2018
 */

package frontend.common;

import backend.GameProcessor;
import backend.GameMode;
import backend.GameState;
import backend.Updatable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

import java.awt.*;

/**
 * A {@link Canvas} with added methods and variables to display the game grid.
 */
public class GameArea extends Canvas implements Updatable {

    public static final double CELL_OUTLINE_WIDTH = 3;
    public static final Color CELL_OUTLINE_COLOUR = Color.BLACK;
    private static final Color LINE_COLOUR = Color.GREY;
    private static final double LINE_WIDTH = 0.5;
    private static final int MAX_SPEED_LEVEL = 20;
    private static final double BACKGROUND_MUSIC_VOLUME = 0.5;

    private double mWidth;
    private double mHeight;
    private Color mBgColour;
    private GraphicsContext mGc;
    private double mCellWidth;
    private double mCellHeight;
    private double mTetrominoUpdateTime;
    private boolean mShowGridlines;
    private GameProcessor mGameProcessor;
    private double mElapsedTime;
    private MediaPlayer mBackgroundMusic;

    public GameArea(MediaPlayer backgroundMusic, double width, double height, Color bgColour, GameMode gameMode) {
        super(width, height);
        mBackgroundMusic = backgroundMusic;
        mBackgroundMusic.setVolume(BACKGROUND_MUSIC_VOLUME);
        mGameProcessor = new GameProcessor(gameMode);
        mHeight = height;
        mWidth = width;
        mBgColour = bgColour;
        mCellHeight = this.mHeight / mGameProcessor.getNumRows();
        mCellWidth = this.mWidth / mGameProcessor.getNumCols();
        mGc = getGraphicsContext2D();
        mShowGridlines = true;
        newGame();
    }

    private void newGame() {
        mBackgroundMusic.stop();
        mBackgroundMusic.play();
        mGameProcessor.newGame();
        mElapsedTime = 0;
        spawnTetromino();
        drawGame();
    }

    void moveLeft() {
        mGameProcessor.moveLeft();
    }

    void moveRight() {
        mGameProcessor.moveRight();
    }

    void moveDown() {
        mGameProcessor.moveDown();
    }

    void rotate() {
        mGameProcessor.rotate();
    }

    void drop() {
        mGameProcessor.drop();
    }

    void restart() {
        newGame();
    }

    void togglePause() {
        if (mGameProcessor.getmGameState() == GameState.PAUSED) {
            mBackgroundMusic.play();
        } else {
            mBackgroundMusic.pause();
        }
        mGameProcessor.togglePause();
    }

    void incrementLevel() {
        mGameProcessor.incrementLevel();
    }

    void toggleGridlines() {
        mShowGridlines = !mShowGridlines;
    }

    public GameGrid getmGrid() {
        return mGameProcessor.getmGrid();
    }

    /**
     * Fills a cell in the grid a certain colour.
     *
     * @param x The column of the cell to fill.
     * @param y The row of the cell to fill.
     * @param colour The colour to fill the cell.
     */
    private void drawCell(int x, int y, Color colour) {
        // checks if the cell should be visible to the player (is it above the top of the grid?)
        if (y >= mGameProcessor.getExtraRowsAtTop()) {
            int screenY = y - mGameProcessor.getExtraRowsAtTop();
            mGc.setFill(CELL_OUTLINE_COLOUR);
            mGc.fillRoundRect(x * mCellWidth, screenY * mCellHeight, mCellWidth, mCellHeight, 5, 5);
            mGc.setFill(colour);
            mGc.fillRoundRect(x * mCellWidth + CELL_OUTLINE_WIDTH, screenY * mCellHeight + CELL_OUTLINE_WIDTH,
                    mCellWidth - (CELL_OUTLINE_WIDTH * 2), mCellHeight - (CELL_OUTLINE_WIDTH * 2), 5, 5);
        }
    }

    void spawnTetromino() {
        mGameProcessor.createTetromino();
        mTetrominoUpdateTime = calculateDropSpeed();
    }

    /**
     * Draws the game grid to canvas.
     */
    private void drawGame() {

        // repaints over everything in the game grid
        setEffect(null);
        mGc.setFill(mBgColour);
        mGc.fillRect(0, 0, mWidth, mHeight);

        // loops through the grid and draws the cell to the canvas
        for (int i = 0; i < mGameProcessor.getmGrid().getmWidth(); i++) {
            for (int j = 0; j < mGameProcessor.getmGrid().getmHeight(); j++) {
                if (mGameProcessor.getmGrid().isFilled(i, j)) {
                    drawCell(i, j, mGameProcessor.getmGrid().getColour(i, j));
                }
            }
        }

        // draws the tetromino to the canvas
        int[][] tBody = mGameProcessor.getmCurTetromino().getmBody();
        Point tPos = mGameProcessor.getmCurTetromino().getmCurPos();
        for (int i = 0; i < tBody.length; i++) {
            for (int j = 0; j < tBody[0].length; j++) {
                if (tBody[i][j] == 1) {
                    drawCell(tPos.x + j, tPos.y + i, mGameProcessor.getmCurTetromino().getmColour());
                }
            }
        }

        // if it's paused, blur it
        if (mGameProcessor.getmGameState() == GameState.PAUSED) {
            setEffect(new GaussianBlur(15));
        }

        // draw the grid lines
        if (mShowGridlines) {
            mGc.setStroke(LINE_COLOUR);
            mGc.setLineWidth(LINE_WIDTH);

            for (int col = 0; col < mGameProcessor.getNumCols(); col++) {
                mGc.strokeLine(col * mCellWidth, 0, col * mCellWidth, mHeight);
            }

            for (int row = 0; row < mGameProcessor.getNumRows(); row++) {
                mGc.strokeLine(0, row * mCellHeight, mWidth, row * mCellHeight);
            }
        }
    }

    /**
     * This function is run every frame of the game.
     *
     * @param deltaTime The number of seconds passed since the last update.
     */
    @Override
    public void update(double deltaTime) {
        if (mGameProcessor.getmGameState() == GameState.PLAYING) {
            // if the game is still playing
            mElapsedTime += deltaTime;
            if (mGameProcessor.getmCurTetromino() != null) {
                // if the next tetromino has spawned
                mTetrominoUpdateTime -= deltaTime;
                if (mTetrominoUpdateTime <= 0) {
                    // if the tetromino update time has passed (the amount of time before dropping one line)
                    mGameProcessor.update();
                    mTetrominoUpdateTime = calculateDropSpeed();
                }
            }
        } else if (mGameProcessor.getmGameState() == GameState.OVER) {
            // if the game is paused or over
            mBackgroundMusic.stop();
        }
        drawGame();
    }

    /**
     *
     *
     * @return
     */
    private double calculateDropSpeed() {
        int dropSpeedLevel = Math.min(mGameProcessor.getmLevel(), MAX_SPEED_LEVEL);
        // return -0.04242*mLevel + 0.6884;
        return (725 * Math.pow(0.85, dropSpeedLevel) + dropSpeedLevel) / 1000;
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

    public GameProcessor getmGameProcessor() {
        return mGameProcessor;
    }

    public boolean ismShowGridlines() {
        return mShowGridlines;
    }
}
