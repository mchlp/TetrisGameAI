/*
 * Michael Pu
 * TetrisGameAI - GameArea
 * ICS3U1 - Mr. Radulovic
 * December 24, 2017
 */

package frontend.common;

import backend.TetrominoBlueprint;
import backend.Updatable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.Random;

public class GameArea extends Canvas implements Updatable {

    public static final int EXTRA_ROWS_AT_TOP = 2;
    public static final double CELL_OUTLINE_WIDTH = 3;
    public static final Color CELL_OUTLINE_COLOUR = Color.BLACK;
    private static final int NUM_ROWS = 20;
    private static final int NUM_COLS = 10;
    private static final int LINES_CLEAR_FOR_LEVEL_UP = 10;
    private static final int[] LINE_CLEAR_SCORING = {0, 40, 100, 300, 1200};
    private static final Color LINE_COLOUR = Color.GREY;
    private static final double LINE_WIDTH = 0.5;
    private static final int MAX_LEVEL = 20;

    private Color mBgColour;
    private GraphicsContext mGc;
    private double mWidth;
    private double mHeight;
    private double mCellWidth;
    private double mCellHeight;
    private GameGrid mGrid;
    private Tetromino mCurTetromino;
    private Tetromino mNextTetromino;
    private double mTetrominoUpdateTime;
    private Random mGenerator;
    private GameState mGameState;
    private GameMode mGameMode;
    private int mNumLinesCleared;
    private int mScore;
    private int mLevel;
    private int mLevelUpCountdown;
    private double mElapsedTime;
    private boolean mShowGridlines;

    public GameArea(double width, double height, Color bgColour, GameMode gameMode) {
        super(width, height);
        mHeight = height;
        mWidth = width;
        mBgColour = bgColour;
        mCellHeight = this.mHeight / NUM_ROWS;
        mCellWidth = this.mWidth / NUM_COLS;
        mGenerator = new Random(100);
        mGc = getGraphicsContext2D();
        mGrid = new GameGrid(NUM_COLS, NUM_ROWS + EXTRA_ROWS_AT_TOP, EXTRA_ROWS_AT_TOP);
        mGameMode = gameMode;
        mShowGridlines = true;
        newGame();
    }

    private void newGame() {
        mElapsedTime = 0;
        mGameState = GameState.PLAYING;
        mNumLinesCleared = 0;
        mLevelUpCountdown = LINES_CLEAR_FOR_LEVEL_UP;
        mLevel = 1;
        mScore = 0;
        mGrid.resetGrid();
        mCurTetromino = null;
        spawnTetromino();
        drawGame();
    }

    void moveLeft() {
        if (mGameState == GameState.PLAYING) {
            if (mCurTetromino != null) {
                mCurTetromino.moveLeft(false);
            }
        }
    }

    void moveRight() {
        if (mGameState == GameState.PLAYING) {
            if (mCurTetromino != null) {
                mCurTetromino.moveRight(false);
            }
        }
    }

    void moveDown() {
        if (mGameState == GameState.PLAYING) {
            if (mCurTetromino != null) {
                mCurTetromino.moveDown(false);
            }
        }
    }

    void rotate() {
        if (mGameState == GameState.PLAYING) {
            if (mCurTetromino != null) {
                mCurTetromino.rotate(false);
            }
        }
    }

    void drop() {
        if (mGameState == GameState.PLAYING) {
            mCurTetromino.drop(false);
        }
    }

    void restart() {
        newGame();
    }

    void togglePause() {
        if (mGameState == GameState.PAUSED) {
            mGameState = GameState.PLAYING;
        } else {
            mGameState = GameState.PAUSED;
        }
    }

    void incrementLevel() {
        if (mLevel < MAX_LEVEL) {
            mLevel++;
        }
    }

    void toggleGridliens() {
        mShowGridlines = !mShowGridlines;
    }

    public GameGrid getmGrid() {
        return mGrid;
    }

    private void drawCell(int x, int y, Color colour) {
        if (y >= EXTRA_ROWS_AT_TOP) {
            int screenY = y - EXTRA_ROWS_AT_TOP;
            mGc.setFill(CELL_OUTLINE_COLOUR);
            mGc.fillRoundRect(x * mCellWidth, screenY * mCellHeight, mCellWidth, mCellHeight, 5, 5);
            mGc.setFill(colour);
            mGc.fillRoundRect(x * mCellWidth + CELL_OUTLINE_WIDTH, screenY * mCellHeight + CELL_OUTLINE_WIDTH,
                    mCellWidth - (CELL_OUTLINE_WIDTH * 2), mCellHeight - (CELL_OUTLINE_WIDTH * 2), 5, 5);
        }
    }

    void spawnTetromino() {

        if (mCurTetromino != null) {
            mScore += (mCurTetromino.getmCurPos().y - EXTRA_ROWS_AT_TOP);
            mGrid.applyTetromino(mCurTetromino);
        } else {
            selectNextTetromino();
        }

        if (mGrid.checkGameOver()) {
            mGameState = GameState.OVER;
            return;
        }

        int numRowsCleared = mGrid.checkCompleteRows();
        mNumLinesCleared += numRowsCleared;
        mLevelUpCountdown -= numRowsCleared;

        if (mLevelUpCountdown <= 0) {
            mLevel++;
            mLevelUpCountdown += LINES_CLEAR_FOR_LEVEL_UP;
        }

        numRowsCleared = numRowsCleared > 4 ? 4 : numRowsCleared;
        mScore += LINE_CLEAR_SCORING[numRowsCleared] * mLevel;

        mTetrominoUpdateTime = calculateDropSpeed();
        mCurTetromino = mNextTetromino;
        selectNextTetromino();
    }

    private void selectNextTetromino() {
        int selectListLength = TetrominoBlueprint.values().length;
        int randSelect = mGenerator.nextInt(selectListLength);
        TetrominoBlueprint selectedTetromino = TetrominoBlueprint.values()[randSelect];
        //selectedTetromino = TetrominoBlueprint.I;
        mNextTetromino = new Tetromino(this, selectedTetromino, NUM_COLS);
    }

    private void drawGame() {

        setEffect(null);
        mGc.setFill(mBgColour);
        mGc.fillRect(0, 0, mWidth, mHeight);

        for (int i = 0; i < mGrid.getmWidth(); i++) {
            for (int j = 0; j < mGrid.getmHeight(); j++) {
                if (mGrid.isFilled(i, j)) {
                    drawCell(i, j, mGrid.getColour(i, j));
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

        if (mGameState == GameState.PAUSED) {
            setEffect(new GaussianBlur(15));
        }

        if (mShowGridlines) {
            mGc.setStroke(LINE_COLOUR);
            mGc.setLineWidth(LINE_WIDTH);

            for (int col = 0; col < NUM_COLS; col++) {
                mGc.strokeLine(col * mCellWidth, 0, col * mCellWidth, mHeight);
            }

            for (int row = 0; row < NUM_ROWS; row++) {
                mGc.strokeLine(0, row * mCellHeight, mWidth, row * mCellHeight);
            }
        }
    }

    public void update(double deltaTime) {
        if (mGameState == GameState.PLAYING) {
            mElapsedTime += deltaTime;
            if (mCurTetromino != null) {
                mTetrominoUpdateTime -= deltaTime;
                if (mTetrominoUpdateTime <= 0) {
                    mCurTetromino.update();
                    mTetrominoUpdateTime = calculateDropSpeed();
                }
            }
        }
        drawGame();
    }

    public int getmScore() {
        return mScore;
    }

    public int getmNumLinesCleared() {
        return mNumLinesCleared;
    }

    private double calculateDropSpeed() {
        // return -0.04242*mLevel + 0.6884;
        return (725 * Math.pow(0.85, mLevel) + mLevel) / 1000;
        //return Math.pow(0.8 - ((mLevel - 1) * 0.007), mLevel - 1);
    }

    public int getmLevel() {
        return mLevel;
    }

    public Tetromino getmNextTetromino() {
        return mNextTetromino;
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

    public GameState getmGameState() {
        return mGameState;
    }

    public GameMode getmGameMode() {
        return mGameMode;
    }
}
