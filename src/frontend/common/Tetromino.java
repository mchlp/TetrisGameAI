/*
 * Michael Pu
 * TetrisGameAI - Tetromino
 * ICS3U1 - Mr. Radulovic
 * December 26, 2017
 */

package frontend.common;

import backend.GameBrain;
import backend.TetrominoBlueprint;
import javafx.scene.paint.Color;

import java.awt.*;

public class Tetromino {

    private static final int MOVE_TIMEOUT = 2;

    private GameBrain mGameBrain;
    private int[][] mBody;
    private TetrominoBlueprint mBlueprint;
    private Color mColour;
    private Point mCurPos;
    private double mLastMove;
    private boolean mCanMove;
    private int mNumCols;

    public Tetromino(GameBrain gameBrain, TetrominoBlueprint blueprint, int numCols) {
        int spawnYPos = 0;
        mGameBrain = gameBrain;
        mBlueprint = blueprint;
        mBody = blueprint.body.clone();
        mColour = blueprint.colour;
        mNumCols = numCols;
        mCurPos = new Point((mNumCols / 2) - (mBody[0].length / 2), spawnYPos);
        mLastMove = MOVE_TIMEOUT;
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

    public boolean rotate(boolean tryMove) {
        int[][] newBody = new int[mBody.length][mBody[0].length];
        for (int i = 0; i < newBody.length; i++) {
            for (int j = 0; j < newBody[0].length; j++) {
                newBody[newBody.length - j - 1][i] = mBody[i][j];
            }
        }
        return tryMove(mCurPos, newBody, tryMove);
    }

    public boolean moveLeft(boolean tryMove) {
        Point tryPos = (Point) mCurPos.clone();
        tryPos.x--;
        return tryMove(tryPos, mBody, tryMove);
    }

    public boolean moveRight(boolean tryMove) {
        Point tryPos = (Point) mCurPos.clone();
        tryPos.x++;
        return tryMove(tryPos, mBody, tryMove);
    }

    public boolean moveDown(boolean tryMove) {
        Point tryPos = (Point) mCurPos.clone();
        tryPos.y++;
        if (tryMove(tryPos, mBody, tryMove)) {
            if (!tryMove) {
                mLastMove = MOVE_TIMEOUT;
            }
            return true;
        }
        return false;
    }

    public void drop(boolean tryMove) {
        while (moveDown(tryMove));
        freeze();
    }

    private void freeze() {
        mCanMove = false;
    }

    private boolean tryMove(Point tryPos, int[][] tryBody, boolean tryMove) {

        if (!mCanMove) {
            return false;
        } else {

            boolean ableToMove = true;

            checkLoop:
            for (int i = 0; i < tryBody.length; i++) {
                for (int j = 0; j < tryBody[0].length; j++) {

                    if (tryBody[i][j] == 1) {

                        if (tryPos.x + j < 0 || tryPos.x + j > mGameBrain.getmGrid().getmWidth() - 1) {
                            ableToMove = false;
                            break checkLoop;
                        }

                        if (tryPos.y + i > mGameBrain.getmGrid().getmHeight() - 1) {
                            ableToMove = false;
                            break checkLoop;
                        }

                        if (mGameBrain.getmGrid().isFilled(tryPos.x + j, tryPos.y + i)) {
                            ableToMove = false;
                            break checkLoop;
                        }
                    }
                }
            }

            if (ableToMove && !tryMove) {
                mCurPos = tryPos;
                mBody = tryBody;
            }

            return ableToMove;
        }
    }

    public void update() {
        if (mCanMove) {
            mLastMove--;
            if (mLastMove < 0) {
                if (!moveDown(true)) {
                    mCanMove = false;
                }
            }
            moveDown(false);
        } else {
            mGameBrain.createTetromino();
        }
    }

    public Tetromino clone() {
        Tetromino newTetromino = new Tetromino(mGameBrain, mBlueprint, mNumCols);
        newTetromino.mCurPos = new Point(mCurPos.x, mCurPos.y);
        newTetromino.mLastMove = mLastMove;
        newTetromino.mCanMove = mCanMove;
        return newTetromino;
    }
}
