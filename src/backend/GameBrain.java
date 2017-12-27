/*
 * Michael Pu
 * TetrisGameAI - GameArea
 * ICS3U1 - Mr. Radulovic
 * December 24, 2017
 */

package backend;

import frontend.common.*;

public class GameBrain {

    public static final int EXTRA_ROWS_AT_TOP = 2;
    public static final int NUM_ROWS = 20;
    public static final int NUM_COLS = 10;
    public static final int LINES_CLEAR_FOR_LEVEL_UP = 10;
    private static final int[] LINE_CLEAR_SCORING = {0, 40, 100, 300, 1200};
    private static final int MAX_LEVEL = 20;

    private GameGrid mGrid;
    private Tetromino mCurTetromino;
    private Tetromino mNextTetromino;
    private GameState mGameState;
    private GameMode mGameMode;
    private int mNumLinesCleared;
    private int mScore;
    private int mLevel;
    private int mLevelUpCountdown;

    public GameBrain(GameMode gameMode) {
        mGrid = new GameGrid(NUM_COLS, NUM_ROWS + EXTRA_ROWS_AT_TOP, EXTRA_ROWS_AT_TOP);
        mGameMode = gameMode;
        mGameState = GameState.LOADING;
    }

    public void newGame() {
        mGameState = GameState.PLAYING;
        mNumLinesCleared = 0;
        mLevelUpCountdown = LINES_CLEAR_FOR_LEVEL_UP;
        mLevel = 1;
        mScore = 0;
        mGrid.resetGrid();
        mCurTetromino = null;
        TetrominoBlueprint.resetGenerator();
    }

    public void moveLeft() {
        if (mGameState == GameState.PLAYING) {
            if (mCurTetromino != null) {
                mCurTetromino.moveLeft(false);
            }
        }
    }

    public void moveRight() {
        if (mGameState == GameState.PLAYING) {
            if (mCurTetromino != null) {
                mCurTetromino.moveRight(false);
            }
        }
    }

    public void moveDown() {
        if (mGameState == GameState.PLAYING) {
            if (mCurTetromino != null) {
                mCurTetromino.moveDown(false);
            }
        }
    }

    public void rotate() {
        if (mGameState == GameState.PLAYING) {
            if (mCurTetromino != null) {
                mCurTetromino.rotate(false);
            }
        }
    }

    public void drop() {
        if (mGameState == GameState.PLAYING) {
            mCurTetromino.drop(false);
        }
    }

    public void restart() {
        newGame();
    }

    public void togglePause() {
        if (mGameState == GameState.PAUSED) {
            mGameState = GameState.PLAYING;
        } else {
            mGameState = GameState.PAUSED;
        }
    }

    public void incrementLevel() {
        if (mLevel < MAX_LEVEL) {
            mLevel++;
        }
    }

    public GameGrid getmGrid() {
        return mGrid;
    }

    public void updateStats() {
        if (mGrid.checkGameOver()) {
            mGameState = GameState.OVER;
            return;
        }
        int numRowsCleared = mGrid.checkCompleteRows();
        mNumLinesCleared += numRowsCleared;
        mLevelUpCountdown -= numRowsCleared;

        if (mLevelUpCountdown <= 0) {
            incrementLevel();
            mLevelUpCountdown += LINES_CLEAR_FOR_LEVEL_UP;
        }

        numRowsCleared = numRowsCleared > 4 ? 4 : numRowsCleared;
        mScore += LINE_CLEAR_SCORING[numRowsCleared] * mLevel;
    }

    public void createTetromino() {

        if (mCurTetromino != null) {
            mScore += (mCurTetromino.getmCurPos().y - EXTRA_ROWS_AT_TOP);
            mGrid.applyTetromino(mCurTetromino);
        } else {
            selectNextTetromino();
        }

        updateStats();

        if (mGameState == GameState.OVER) {
            return;
        }

        mCurTetromino = mNextTetromino;
        selectNextTetromino();
    }

    public void selectNextTetromino() {
        TetrominoBlueprint nextBlueprint = TetrominoBlueprint.getRandomTetrominoBlueprint();
        mNextTetromino = new Tetromino(this, nextBlueprint, NUM_COLS);
    }

    public void update() {
        getmCurTetromino().update();
    }

    public int getmScore() {
        return mScore;
    }

    public int getmNumLinesCleared() {
        return mNumLinesCleared;
    }

    public int getmLevel() {
        return mLevel;
    }

    public Tetromino getmNextTetromino() {
        return mNextTetromino;
    }

    public Tetromino getmCurTetromino() {
        return mCurTetromino;
    }

    public int getNumCols() {
        return NUM_COLS;
    }

    public int getNumRows() {
        return NUM_ROWS;
    }

    public int getExtraRowsAtTop() {
        return EXTRA_ROWS_AT_TOP;
    }

    public GameState getmGameState() {
        return mGameState;
    }

    public GameMode getmGameMode() {
        return mGameMode;
    }

    public String getGridInString() {
        String grid = "";
        for (int i=0; i<mGrid.getmHeight(); i++) {
            for (int j=0; j<mGrid.getmWidth(); j++) {
                grid += mGrid.getCell(j, i).ismIsFilled() ? "X " : ". ";
            }
            grid += "\n";
        }
        return grid;
    }
}
