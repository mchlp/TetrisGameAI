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
import java.util.Random;

public class GameArea extends Canvas implements Updatable {

    public static final int EXTRA_ROWS_AT_TOP = 2;

    private static final int NUM_ROWS = 20;
    private static final int NUM_COLS = 10;

    private static final int[] LINE_CLEAR_SCORING = {0, 40, 100, 300, 1200};

    private static final Color LINE_COLOUR = Color.GREY;
    private static final double LINE_WIDTH = 0.5;

    private static final double CELL_OUTLINE_WIDTH = 3;

    private Color mBgColour;
    private GraphicsContext mGc;
    private double mWidth;
    private double mHeight;
    private double mCellWidth;
    private double cellHeight;
    private Cell[][] mGrid;
    private Tetromino mCurTetromino;
    private double mTetrominoUpdateTime;
    private Random mGenerator;
    private GameState mGameState;
    private int mNumLinesCleared;
    private int mScore;
    private int mLevel;

    public GameArea(double width, double height, Color bgColour) {
        super(width, height);
        mHeight = height;
        mWidth = width;
        mBgColour = bgColour;
        cellHeight = this.mHeight / NUM_ROWS;
        mCellWidth = this.mWidth / NUM_COLS;
        mGenerator = new Random(100);
        mGc = getGraphicsContext2D();
        mGrid = new Cell[NUM_COLS][NUM_ROWS + EXTRA_ROWS_AT_TOP];
        newGame();
    }

    private void newGame() {
        mGameState = GameState.PLAYING;
        mNumLinesCleared = 0;
        mLevel = 3;
        mScore = 0;
        for (int i = 0; i < mGrid.length; i++) {
            for (int j = 0; j < mGrid[0].length; j++) {
                mGrid[i][j] = new Cell();
            }
        }
        mCurTetromino = null;
        spawnTetromino();
        drawGame();
    }

    public void moveLeft() {
        if (mCurTetromino != null) {
            mCurTetromino.moveLeft(false);
        }
    }

    public void moveRight() {
        if (mCurTetromino != null) {
            mCurTetromino.moveRight(false);
        }
    }

    public void moveDown() {
        if (mCurTetromino != null) {
            mCurTetromino.moveDown(false);
        }
    }

    public void rotate() {
        if (mCurTetromino != null) {
            mCurTetromino.rotate(false);
        }
    }

    public void restart() {
        newGame();
    }

    public void drop() {
        while (mCurTetromino.moveDown(false)) ;
        mCurTetromino.freeze();
    }

    public Cell[][] getmGrid() {
        return mGrid;
    }

    private void drawCell(int x, int y, Color colour) {
        if (y >= EXTRA_ROWS_AT_TOP) {
            int screenY = y - EXTRA_ROWS_AT_TOP;
            mGc.setFill(Color.BLACK);
            mGc.fillRoundRect(x * mCellWidth, screenY * cellHeight, mCellWidth, cellHeight, 5, 5);
            mGc.setFill(colour);
            mGc.fillRoundRect(x * mCellWidth + CELL_OUTLINE_WIDTH, screenY * cellHeight + CELL_OUTLINE_WIDTH,
                    mCellWidth - (CELL_OUTLINE_WIDTH * 2), cellHeight - (CELL_OUTLINE_WIDTH * 2), 5, 5);
        }
    }

    public void spawnTetromino() {

        if (mCurTetromino != null) {
            mScore += (mCurTetromino.getmCurPos().y - EXTRA_ROWS_AT_TOP);
            Point tPos = mCurTetromino.getmCurPos();
            int[][] tBody = mCurTetromino.getmBody();
            for (int i = 0; i < tBody.length; i++) {
                for (int j = 0; j < tBody[0].length; j++) {
                    if (tBody[i][j] == 1) {
                        mGrid[tPos.x + j][tPos.y + i].fill(mCurTetromino.getmColour());
                    }
                }
            }
        }

        if (checkGameOver()) {
            mGameState = GameState.OVER;
            return;
        }

        int numRowsCleared = checkCompleteRows();
        mNumLinesCleared += numRowsCleared;

        numRowsCleared = numRowsCleared > 4 ? 4 : numRowsCleared;
        mScore += LINE_CLEAR_SCORING[numRowsCleared]*mLevel;

        mLevel = (mNumLinesCleared/10)+1;

        mTetrominoUpdateTime = calculateDropSpeed();
        int selectListLength = TetrominoBlueprint.values().length;
        int randSelect = mGenerator.nextInt(selectListLength);
        TetrominoBlueprint selectedTetromino = TetrominoBlueprint.values()[randSelect];
        mCurTetromino = new Tetromino(this, selectedTetromino, NUM_COLS);
    }

    private boolean checkGameOver() {
        for (int i = 0; i < mGrid.length; i++) {
            if (mGrid[i][0].ismIsFilled()) {
                return true;
            }
        }
        return false;
    }


    private int checkCompleteRows() {
        int numRowsCleared = 0;
        for (int i = 0; i < mGrid[0].length; i++) {
            boolean rowFull = true;
            for (int j = 0; j < mGrid.length; j++) {
                if (!mGrid[j][i].ismIsFilled()) {
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
                    mGrid[j][i] = new Cell();
                } else {
                    mGrid[j][i] = mGrid[j][i - 1];
                }
            }
        }
    }

    private void drawGame() {
        mGc.setFill(mBgColour);
        mGc.fillRect(0, 0, mWidth, mHeight);

        for (int i = 0; i < mGrid.length; i++) {
            for (int j = 0; j < mGrid[0].length; j++) {
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

        mGc.setStroke(LINE_COLOUR);
        mGc.setLineWidth(LINE_WIDTH);

        for (int col = 0; col < NUM_COLS; col++) {
            mGc.strokeLine(col * mCellWidth, 0, col * mCellWidth, mHeight);
        }

        for (int row = 0; row < NUM_ROWS; row++) {
            mGc.strokeLine(0, row * cellHeight, mWidth, row * cellHeight);
        }
    }

    public void update(double deltaTime) {
        if (mGameState == GameState.PLAYING) {
            if (mCurTetromino != null) {
                mTetrominoUpdateTime -= deltaTime;
                if (mTetrominoUpdateTime <= 0) {
                    mCurTetromino.update();
                    mTetrominoUpdateTime = calculateDropSpeed();
                }
            }
            drawGame();
        }
    }

    public int getmScore() {
        return mScore;
    }

    public int getmNumLinesCleared() {
        return mNumLinesCleared;
    }

    private double calculateDropSpeed() {
        return Math.pow(0.8-((mLevel-1)*0.007), mLevel-1);
    }

    public int getmLevel() {
        return mLevel;
    }
}
