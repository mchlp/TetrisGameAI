/*
 * Michael Pu
 * TetrisGameAI - Tetromino
 * ICS3U1 - Mr.Radulovic
 * January 13, 2018
 */

package backend;

import javafx.scene.paint.Color;

import java.awt.*;

/**
 * Represents a Tetromino. Among other variables, it stores its {@link TetrominoBlueprint}, position, colour, and its
 * state.
 */
public class Tetromino {

    /**
     * After the Tetromino has reached its lowest position, this is the number of update cycles for which it will still
     * be movable.
     */
    private static final int MOVE_TIMEOUT = 2;

    /**
     * The {@link GameProcessor} that this Tetromino will be spawned in.
     */
    private GameProcessor mGameProcessor;

    /**
     * An integer array storing the structure of the Tetromino.
     */
    private int[][] mBody;

    /**
     * The {@link TetrominoBlueprint} of the Tetromino.
     */
    private TetrominoBlueprint mBlueprint;

    /**
     * The colour of the Tetromino.
     */
    private Color mColour;

    /**
     * The current position of the Tetromino in the grid.
     */
    private Point mCurPos;

    /**
     * The number of cycles until the Tetromino is no longer movable.
     */
    private double mCountdownToFreeze;

    /**
     * If the Tetromino can still be moved.
     */
    private boolean mCanMove;

    /**
     * The number of columns in the grid where this Tetromino will be spawned.
     */
    private int mNumCols;

    /**
     * Creates a new Tetromino object.
     *
     * @param gameProcessor The {@link GameProcessor} that the Tetromino will be spawned in.
     * @param blueprint     The {@link TetrominoBlueprint} of the Tetromino
     */
    public Tetromino(GameProcessor gameProcessor, TetrominoBlueprint blueprint) {
        // set the Y position that the Tetromino will be spawned at
        int spawnYPos = 0;
        // initialize variables
        mGameProcessor = gameProcessor;
        mBlueprint = blueprint;
        mBody = blueprint.body.clone();
        mColour = blueprint.colour;
        mNumCols = gameProcessor.getmGrid().getmWidth();
        mCurPos = new Point((mNumCols / 2) - (mBody[0].length / 2), spawnYPos);
        mCountdownToFreeze = MOVE_TIMEOUT;
        mCanMove = true;
    }

    public int[][] getmBody() {
        return mBody;
    }

    public Point getmCurPos() {
        return mCurPos;
    }

    public Color getmColour() {
        return mColour;
    }

    /**
     * Rotates the Tetromino 90 degrees clockwise.
     *
     * @param tryMove <code>false</code> if the move should not be actually applied to the Tetromino. <code>true</code>
     *                otherwise.
     * @return If the rotation can be performed successfully.
     */
    public boolean rotate(boolean tryMove) {
        int[][] newBody = new int[mBody.length][mBody[0].length];
        // loops through each row in the body of the Tetromino
        for (int i = 0; i < newBody.length; i++) {
            // loops through each column in the body of the Tetromino
            for (int j = 0; j < newBody[0].length; j++) {
                // transposes the cell in the body along the diagonal extending from the bottom-left corner to the upper-right corner
                newBody[newBody.length - j - 1][i] = mBody[i][j];
            }
        }
        return tryMove(mCurPos, newBody, tryMove);
    }

    /**
     * Rotates the Tetromino 1 cell to the left.
     *
     * @param tryMove <code>false</code> if the move should not be actually applied to the Tetromino. <code>true</code>
     *                otherwise.
     * @return If the transformation can be performed successfully.
     */
    public boolean moveLeft(boolean tryMove) {
        Point tryPos = (Point) mCurPos.clone();
        tryPos.x--;
        return tryMove(tryPos, mBody, tryMove);
    }

    /**
     * Rotates the Tetromino 1 cell to the right.
     *
     * @param tryMove <code>false</code> if the move should not be actually applied to the Tetromino. <code>true</code>
     *                otherwise.
     * @return If the transformation can be performed successfully.
     */
    public boolean moveRight(boolean tryMove) {
        Point tryPos = (Point) mCurPos.clone();
        tryPos.x++;
        return tryMove(tryPos, mBody, tryMove);
    }

    /**
     * Rotates the Tetromino 1 cell down.
     *
     * @param tryMove <code>false</code> if the move should not be actually applied to the Tetromino. <code>true</code>
     *                otherwise.
     * @return If the transformation can be performed successfully.
     */
    public boolean moveDown(boolean tryMove) {
        Point tryPos = (Point) mCurPos.clone();
        tryPos.y++;
        if (tryMove(tryPos, mBody, tryMove)) {
            if (!tryMove) {
                mCountdownToFreeze = MOVE_TIMEOUT;
            }
            return true;
        }
        return false;
    }

    /**
     * Drops the Tetromino to its lowest possible resting position.
     *
     * @param tryMove <code>false</code> if the move should not be actually applied to the Tetromino. <code>true</code>
     *                otherwise.
     */
    public void drop(boolean tryMove) {
        while (moveDown(tryMove)) ;
        freeze();
    }

    /**
     * Freezes the Tetromino in its current position and prevents it from being moved.
     */
    private void freeze() {
        mCanMove = false;
    }

    /**
     * Checks if a new position or orientation is legal in the game.
     *
     * @param tryPos  The new position.
     * @param tryBody The new body.
     * @param tryMove <code>false</code> if the move should be actually applied if it is legal, <code>false</code>
     *                otherwise.
     * @return <code>true</code> if the new position and orientation is legal, <code>false</code> otherwise.
     */
    private boolean tryMove(Point tryPos, int[][] tryBody, boolean tryMove) {

        if (!mCanMove) {
            // if the Tetromino piece cannot move, the new position and orientation is illegal
            return false;
        } else {

            boolean ableToMove = true;

            // checks if any part of the Tetromino is in an already filled cell or is outside the bounds of the grid
            checkLoop:
            // loops through each row of the body
            for (int i = 0; i < tryBody.length; i++) {
                // loops through each column of the body
                for (int j = 0; j < tryBody[0].length; j++) {

                    if (tryBody[i][j] == 1) {
                        // if that cell in the body is filled

                        // check if it exceeds the left or right edge
                        if (tryPos.x + j < 0 || tryPos.x + j > mGameProcessor.getmGrid().getmWidth() - 1) {
                            ableToMove = false;
                            break checkLoop;
                        }

                        // check if it exceeds the bottom edge
                        if (tryPos.y + i > mGameProcessor.getmGrid().getmHeight() - 1) {
                            ableToMove = false;
                            break checkLoop;
                        }

                        // checks if it is in the same cell as an already filled cell
                        if (mGameProcessor.getmGrid().isFilled(tryPos.x + j, tryPos.y + i)) {
                            ableToMove = false;
                            break checkLoop;
                        }
                    }
                }
            }

            // if the move is legal and the move should be applied, apply it and set it as the current position and orientation
            if (ableToMove && !tryMove) {
                mCurPos = tryPos;
                mBody = tryBody;
            }

            return ableToMove;
        }
    }

    /**
     * Updates the position and state of the Tetromino.
     */
    public void update() {
        if (mCanMove) {
            // if the Tetromino can still move
            // update the counter until the Tetromino is frozen
            mCountdownToFreeze--;
            if (mCountdownToFreeze < 0) {
                // if the Tetromino should be frozen
                if (!moveDown(true)) {
                    // if the Tetromino cannot move down any further, freeze it
                    freeze();
                }
            }
            // if the Tetromino can still move, move it one space down
            moveDown(false);
        } else {
            // if the Tetromino can no longer move, spawn the next Tetromino
            mGameProcessor.createTetromino();
        }
    }

    /**
     * @return A deep copy of the {@link Tetromino} object.
     */
    public Tetromino duplicate() {
        Tetromino newTetromino = new Tetromino(mGameProcessor, mBlueprint);
        newTetromino.mCurPos = new Point(mCurPos.x, mCurPos.y);
        newTetromino.mCountdownToFreeze = mCountdownToFreeze;
        newTetromino.mCanMove = mCanMove;
        return newTetromino;
    }
}
