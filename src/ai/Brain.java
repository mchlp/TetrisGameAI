/*
 * Michael Pu
 * TetrisGameAI - Brain
 * ICS3U1 - Mr. Radulovic
 * January 01, 2018
 */

package ai;

import backend.ControllerKeys;
import backend.GameProcessor;
import backend.Updatable;
import frontend.common.GameGrid;
import backend.Tetromino;

import java.util.ArrayList;

/**
 * An abstract class that acts as the "brain" of an AI {@link Organism}. Uses the genes in the {@link Genome} of the
 * {@link Organism} to calculate the next move to make in the game. Classes that extend this abstract class will need to
 * assign an organism to use ({@link #mCurOrganism}) for calculating the next move and to relay the moves calculated by
 * this class to the actual game.
 */
public abstract class Brain implements Updatable {

    /**
     * The rating penalty for a move if the move results in losing the game.
     */
    private static final int GAME_OVER_RATING_PENALTY = 500;

    /**
     * Percentage of Tetrominos that are left to slowly descend to the bottom rather than dropped immediately when
     * {@link #mFastMode} is disabled.
     */
    private static final double DROP_RATE_FOR_SLOW_MODE = 0.3;

    /**
     * The {@link Organism} that will be used to make decisions as to which move to make.
     */
    protected Organism mCurOrganism;

    /**
     * The {@link GameProcessor} that is linked to the game that the AI is currently playing.
     */
    protected GameProcessor mGameProcessor;

    /**
     * If enabled, the AI will immediately drop all tetrominos after they are in their desired horizontal position and
     * orientation. If disabled, the AI will only drop {@value #DROP_RATE_FOR_SLOW_MODE} of tetrominos (mimics a human
     * playing).
     */
    private boolean mFastMode;

    public Brain(GameProcessor gameProcessor, boolean fastMode) {
        mGameProcessor = gameProcessor;
        mFastMode = fastMode;
    }

    /**
     * Calculates the optimal set of moves to make using the {@link Genome} of {@link #mCurOrganism} when provided with
     * a {@link GameGrid} and the current {@link Tetromino}.
     *
     * @param grid         Represents the current state of the game grid.
     * @param curTetromino The current tetromino that is dropping.
     * @return An ArrayList that contains the optimal set of moves to make represented as {@link ControllerKeys}.
     */
    protected ArrayList<ControllerKeys> getBestMove(GameGrid grid, Tetromino curTetromino) {

        // initialize the highest rating at the lowest possible number
        double highestRating = Double.NEGATIVE_INFINITY;

        // initialize the ArrayList that stores the set of optimal moves
        ArrayList<ControllerKeys> bestMoves = new ArrayList<>();

        // loop through all possible rotations (maximum of 4 possible rotation states for each tetromino)
        for (int numRotations = 0; numRotations < 4; numRotations++) {

            // loop through all possible horizontal translations (from left edge to right edge)
            for (int numTranslate = -(grid.getmWidth() / 2); numTranslate <= grid.getmWidth() / 2; numTranslate++) {

                // create a clone of the current game state and tetromino to apply the current set of testing moves on
                GameGrid testGrid = grid.duplicate();
                Tetromino testTetromino = curTetromino.duplicate();

                // rotate the tetromino
                for (int i = 0; i < numRotations; i++) {
                    testTetromino.rotate(false);
                }

                // horizontally translate the tetromino
                for (int i = 0; i < Math.abs(numTranslate); i++) {
                    if (numTranslate < 0) {
                        testTetromino.moveLeft(false);
                    } else {
                        testTetromino.moveRight(false);
                    }
                }

                // drop the tetromino to its final resting position
                testTetromino.drop(false);

                // calculate the rating of applied set of moves
                double curRating = getRating(testGrid, mCurOrganism.getmGenome(), testTetromino);

                // if the rating of the current set of moves is higher than the highest rating
                if (curRating > highestRating) {

                    // update the highest rating variable
                    highestRating = curRating;

                    // update the list that stores the optimal set of moves
                    bestMoves.clear();
                    for (int i = 0; i < numRotations; i++) {
                        bestMoves.add(ControllerKeys.ROTATE);
                    }
                    for (int i = 0; i < Math.abs(numTranslate); i++) {
                        if (numTranslate < 0) {
                            bestMoves.add(ControllerKeys.LEFT);
                        } else {
                            bestMoves.add(ControllerKeys.RIGHT);
                        }
                    }

                    // add drop to the set of optimal moves if desired (see comment for mFastMode)
                    if (mFastMode || Math.random() > DROP_RATE_FOR_SLOW_MODE) {
                        bestMoves.add(ControllerKeys.DROP);
                    }
                }
            }
        }
        return bestMoves;
    }

    /**
     * Calculates the rating for a game state described by a {@link GameGrid} and a {@link Tetromino} using a {@link
     * Genome}.
     *
     * @param grid      Represents the current state of the game grid.
     * @param genome    The genome to use to calculate the rating.
     * @param tetromino The current tetromino, which has already dropped to its final resting position.
     * @return A double representing the rating of the current game state.
     */
    protected double getRating(GameGrid grid, Genome genome, Tetromino tetromino) {

        // initialize the rating to zero
        double rating = 0;

        // calculate number of holes before the tetromino was dropped
        int beforeHoles = getNumHoles(grid);

        // apply the current tetromino to the game grid
        grid.applyTetromino(tetromino);

        // calculate the number of complete rows
        int numClearRows = grid.checkCompleteRows();

        // calculate the height of the tallest column and the lowest column
        int minHeight = getMinHeight(grid);
        int maxHeight = getMaxHeight(grid);

        // for each gene, multiply the gene value by the corresponding value in the game state and add it to the current rating
        // for more information on each gene, see the comments in the Genes enum class
        rating += genome.getGeneValue(Genes.LINES_CLEARED) * numClearRows;
        rating += genome.getGeneValue(Genes.NUM_HOLES) * getNumHoles(grid);
        rating += genome.getGeneValue(Genes.INCREASED_NUMBER_OF_HOLES) * getNumHoles(grid) - beforeHoles;
        rating += genome.getGeneValue(Genes.SUM_BLOCKS_ABOVE_HOLE) * getSumBlocksAboveHole(grid);
        rating += genome.getGeneValue(Genes.ROUGHNESS) * getRoughness(grid);
        rating += genome.getGeneValue(Genes.TOTAL_HEIGHT) * getTotalHeight(grid);
        rating += genome.getGeneValue(Genes.MAX_HEIGHT) * maxHeight;
        rating += genome.getGeneValue(Genes.MIN_MAX_HEIGHT_DIFFERENCE) * (maxHeight - minHeight);
        rating += genome.getGeneValue(Genes.BLOCKS_TOUCHING_SIDE) * getNumBlocksTouchingSide(grid);

        if (getGameOver(grid)) {
            rating -= GAME_OVER_RATING_PENALTY;
        }
        return rating;
    }

    /**
     * Determines if the game is over.
     *
     * @param grid Represents the current state of the game.
     * @return <code>true</code> if the game is over, <code>false</code> otherwise.
     */
    private boolean getGameOver(GameGrid grid) {
        return grid.checkGameOver();
    }

    /**
     * @param grid Represents the current state of the game.
     * @return The sum of the heights of each of the columns.
     */
    private int getTotalHeight(GameGrid grid) {
        int sumHeight = 0;
        // loop through columns
        for (int i = 0; i < grid.getmWidth(); i++) {
            // loop through rows from top to bottom
            for (int j = 0; j < grid.getmHeight(); j++) {
                if (grid.isFilled(i, j)) {
                    // found the top of the column
                    sumHeight += grid.getmHeight() - j;
                    break;
                }
            }
        }
        return sumHeight;
    }

    /**
     * @param grid Represents the current state of the game.
     * @return The number of filled cells in the grid that are touching the left or right edge.
     */
    private int getNumBlocksTouchingSide(GameGrid grid) {
        int numBlocksTouchingSide = 0;
        // loop through all rows
        for (int i = 0; i < grid.getmHeight(); i++) {
            if (grid.isFilled(0, i)) {
                // if cell beside left edge is filled
                numBlocksTouchingSide++;
            } else if (grid.isFilled(grid.getmWidth() - 1, i)) {
                // if cell beside right edge is filled
                numBlocksTouchingSide++;
            }
        }
        return numBlocksTouchingSide;
    }

    /**
     * @param grid Represents the current state of the game.
     * @return The sum of the number of blocks above each hole (an empty cell that has a filled cell above it) in the
     * grid.
     */
    private int getSumBlocksAboveHole(GameGrid grid) {
        int sumBlocksAboveHole = 0;
        // loop through all columns
        for (int i = 0; i < grid.getmWidth(); i++) {
            int numHolesInCol = 0;
            // loop through rows from bottom to top
            for (int j = grid.getmHeight() - 1; j >= 0; j--) {
                if (!grid.isFilled(i, j)) {
                    // found a hole
                    numHolesInCol++;
                } else {
                    // found a filled cell
                    sumBlocksAboveHole += numHolesInCol;
                }
            }
        }
        return sumBlocksAboveHole;
    }

    /**
     * @param grid Represents the current state of the game.
     * @return The height of tallest column.
     */
    private int getMaxHeight(GameGrid grid) {
        int maxHeight = 0;
        // loop through all columns
        for (int i = 0; i < grid.getmWidth(); i++) {
            // loop through rows from top to bottom
            for (int j = 0; j < grid.getmHeight(); j++) {
                if (grid.isFilled(i, j)) {
                    // found top of column
                    int height = grid.getmHeight() - j;
                    if (height > maxHeight) {
                        maxHeight = height;
                    }
                    break;
                }
            }
        }
        return maxHeight;
    }

    /**
     * @param grid Represents the current state of the game.
     * @return The height of the shortest column.
     */
    private int getMinHeight(GameGrid grid) {
        int minHeight = Integer.MAX_VALUE;
        // loop through all columns
        for (int i = 0; i < grid.getmWidth(); i++) {
            // loop through all rows from top to bottom
            for (int j = 0; j < grid.getmHeight(); j++) {
                if (grid.isFilled(i, j)) {
                    // found top of column
                    int height = grid.getmHeight() - j;
                    if (height < minHeight) {
                        minHeight = height;
                    }
                    break;
                }
            }
        }
        return minHeight;
    }

    /**
     * @param grid Represents the current state of the game.
     * @return The sum of the absolute differences between adjacent columns.
     */
    private int getRoughness(GameGrid grid) {
        int prevHeight = 0;
        int sumAbsDiff = 0;
        // loop through columns
        for (int i = 0; i < grid.getmWidth(); i++) {
            // loop through rows from top to bottom
            for (int j = 0; j < grid.getmHeight(); j++) {
                if (grid.isFilled(i, j)) {
                    // found top of column
                    int height = grid.getmHeight() - j;
                    if (!(i == 0)) {
                        // if not the first column, add the absolute difference in column height to the sum
                        sumAbsDiff += Math.abs(height - prevHeight);
                    }
                    prevHeight = height;
                    break;
                }
            }
        }
        return sumAbsDiff;
    }

    /**
     * @param grid Represents the current state of the game.
     * @return The number of holes in the grid
     */
    private int getNumHoles(GameGrid grid) {
        int numHoles = 0;
        // loop through the columns
        for (int i = 0; i < grid.getmWidth(); i++) {
            boolean foundTop = false;
            // loop through the rows from top to bottom
            for (int j = 0; j < grid.getmHeight(); j++) {
                if (foundTop) {
                    // if currently past the top of the column
                    if (!grid.isFilled(i, j)) {
                        // if the cell is not filled
                        numHoles++;
                    }
                } else {
                    if (grid.isFilled(i, j)) {
                        // found the top of the column
                        foundTop = true;
                    }
                }
            }
        }
        return numHoles;
    }

    /**
     * @return The AI organism that is used to calculate the next move
     */
    public Organism getmCurOrganism() {
        return mCurOrganism;
    }

    /**
     * To be called every frame when the JavaFX application is updated.
     *
     * @param deltaTime The amount of time that has passed since the last time this function was called in seconds.
     */
    @Override
    public abstract void update(double deltaTime);
}
