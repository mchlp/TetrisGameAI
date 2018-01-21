/*
 * Michael Pu
 * TetrisGameAI - GameProcessor
 * ICS3U1 - Mr.Radulovic
 * January 20, 2018
 */

package backend;

import frontend.common.GameGrid;

/**
 * Handles the game logic and processing of the inputs.
 */
public class GameProcessor {

    /**
     * Number of rows at the top of the grid that are hidden from view to allow new Tetrominos to spawn when the top
     * rows are filled.
     */
    public static final int EXTRA_ROWS_AT_TOP = 2;

    /**
     * Number of rows in the grid visible to the player.
     */
    public static final int NUM_ROWS = 20;

    /**
     * Number of columns in the grid visible to player.
     */
    public static final int NUM_COLS = 10;

    /**
     * Number of lines that need to be cleared to advance to the next level.
     */
    public static final int LINES_CLEAR_FOR_LEVEL_UP = 10;

    /**
     * The point reward multiplier associated with clearing a certain number of lines.
     * <code>LINE_CLEAR_SCORING[n]</code> retrieves the multiplier for clearing <code>n</code> lines.
     */
    private static final int[] LINE_CLEAR_SCORING = {0, 40, 100, 300, 1200};

    /**
     * Stores the all of the cells in playing field.
     */
    private GameGrid mGrid;

    /**
     * Stores the Tetromino that is currently falling.
     */
    private Tetromino mCurTetromino;

    /**
     * Stores the Tetromino that will appear next.
     */
    private Tetromino mNextTetromino;

    /**
     * The random Tetromino generator used to generate the next Tetromino.
     */
    private RandomBlueprintGenerator mBlueprintGenerator;

    /**
     * Stores the current state of the game.
     */
    private GameState mGameState;

    /**
     * Stores the current mode of the game.
     */
    private GameMode mGameMode;

    /**
     * Stores the number of lines cleared in the current game.
     */
    private int mNumLinesCleared;

    /**
     * Stores the score of the current game.
     */
    private int mScore;

    /**
     * Stores the level of the current game.
     */
    private int mLevel;

    /**
     * Stores the number of lines that need to be cleared to advance to the next level.
     */
    private int mLevelUpCountdown;

    /**
     * Creates a new {@link GameProcessor} with the specified {@link GameMode}.
     *
     * @param gameMode The mode of the game.
     */
    public GameProcessor(GameMode gameMode) {
        mGrid = new GameGrid(NUM_COLS, NUM_ROWS + EXTRA_ROWS_AT_TOP, EXTRA_ROWS_AT_TOP);
        mBlueprintGenerator = new RandomBlueprintGenerator();
        mGameMode = gameMode;
        mGameState = GameState.LOADING;
    }

    /**
     * Starts a new game
     */
    public void newGame() {
        mGameState = GameState.PLAYING;
        // reset variables
        mNumLinesCleared = 0;
        mLevelUpCountdown = LINES_CLEAR_FOR_LEVEL_UP;
        mLevel = 1;
        mScore = 0;
        mGrid.resetGrid();
        mCurTetromino = null;
    }

    /**
     * Moves the dropping Tetromino one cell to the left.
     */
    public void moveLeft() {
        if (mGameState == GameState.PLAYING) {
            if (mCurTetromino != null) {
                mCurTetromino.moveLeft(false);
            }
        }
    }

    /**
     * Moves the dropping Tetromino one cell to the right.
     */
    public void moveRight() {
        if (mGameState == GameState.PLAYING) {
            if (mCurTetromino != null) {
                mCurTetromino.moveRight(false);
            }
        }
    }

    /**
     * Moves the dropping Tetromino one cell down.
     */
    public void moveDown() {
        if (mGameState == GameState.PLAYING) {
            if (mCurTetromino != null) {
                mCurTetromino.moveDown(false);
            }
        }
    }

    /**
     * Rotates the dropping Tetromino 90 degrees counterclockwise.
     */
    public void rotate() {
        if (mGameState == GameState.PLAYING) {
            if (mCurTetromino != null) {
                mCurTetromino.rotate(false);
            }
        }
    }

    /**
     * Drops the Tetromino to its final resting position at the lowest position.
     */
    public void drop() {
        if (mGameState == GameState.PLAYING) {
            mCurTetromino.drop(false);
        }
    }

    public void togglePause() {
        if (mGameState == GameState.PAUSED) {
            mGameState = GameState.PLAYING;
        } else {
            mGameState = GameState.PAUSED;
        }
    }

    public void incrementLevel() {
        mLevel++;
    }

    public GameGrid getmGrid() {
        return mGrid;
    }

    /**
     * Updates the statistics and score of the game.
     */
    public void updateStats() {
        // check if the game is over
        if (mGrid.checkGameOver()) {
            mGameState = GameState.OVER;
            return;
        }

        // get the number of lines cleared
        int numRowsCleared = mGrid.checkCompleteRows();
        mNumLinesCleared += numRowsCleared;

        // update the number of lines that still need to be cleared to level up
        mLevelUpCountdown -= numRowsCleared;

        // check if the next level has been reached
        if (mLevelUpCountdown <= 0) {
            incrementLevel();
            mLevelUpCountdown += LINES_CLEAR_FOR_LEVEL_UP;
        }

        // add the line clear bonus to the score
        numRowsCleared = numRowsCleared > 4 ? 4 : numRowsCleared;
        mScore += LINE_CLEAR_SCORING[numRowsCleared] * mLevel;
    }

    /**
     * Spawns the next Tetromino at the top of the playing field.
     */
    public void createTetromino() {
        if (mCurTetromino != null) {
            // if this is not the first Tetromino to spawn, update the score of the last Tetromino and apply it to the grid
            mScore += (mCurTetromino.getmCurPos().y - EXTRA_ROWS_AT_TOP);
            mGrid.applyTetromino(mCurTetromino);
        } else {
            // if this is the first Tetromino, initialize the nextBlueprint
            selectNextTetromino();
        }

        // update the score and state of the game
        updateStats();

        // check if the game is over
        if (mGameState == GameState.OVER) {
            return;
        }

        // set the next tetromino as the current tetromino
        mCurTetromino = mNextTetromino;

        // choose the next tetromino
        selectNextTetromino();
    }

    /**
     * Randomly selects a {@link TetrominoBlueprint} using the {@link #mBlueprintGenerator} and assigns it to {@link
     * #mNextTetromino}
     */
    public void selectNextTetromino() {
        TetrominoBlueprint nextBlueprint = mBlueprintGenerator.getNextTetromino();
        mNextTetromino = new Tetromino(this, nextBlueprint);
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

    public GameState getmGameState() {
        return mGameState;
    }

    public GameMode getmGameMode() {
        return mGameMode;
    }

    public String getGridInString() {
        StringBuilder grid = new StringBuilder();
        for (int i = 0; i < mGrid.getmHeight(); i++) {
            for (int j = 0; j < mGrid.getmWidth(); j++) {
                grid.append(mGrid.getCell(j, i).ismIsFilled() ? "X " : ". ");
            }
            grid.append("\n");
        }
        return grid.toString();
    }
}
