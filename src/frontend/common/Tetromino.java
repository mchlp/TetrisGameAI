/*
 * Michael Pu
 * TetrisGameAI - Tetromino
 * ICS3U1 - Mr. Radulovic
 * December 24, 2017
 */

package frontend.common;

import backend.TetrominoBlueprint;
import javafx.scene.paint.Color;

import java.awt.*;

public class Tetromino {

    private static final int MOVE_TIMEOUT = 2;

    private GameArea mGameArea;
    private int[][] mBody;
    private Color mColour;
    private Point mCurPos;
    private double mLastMove;
    private boolean mCanMove;

    public Tetromino(GameArea gameArea, TetrominoBlueprint blueprint, int numCols) {
        int spawnYPos = 0;
        mGameArea = gameArea;
        mBody = blueprint.body.clone();
        mColour = blueprint.colour;
        mCurPos = new Point((numCols / 2) - (mBody[0].length / 2), spawnYPos);
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

    private boolean tryMove(Point tryPos, int[][] tryBody, boolean tryMove) {

        if (!mCanMove) {
            return false;
        } else {

            boolean ableToMove = true;

            checkLoop:
            for (int i = 0; i < tryBody.length; i++) {
                for (int j = 0; j < tryBody[0].length; j++) {

                    if (tryBody[i][j] == 1) {

                        if (tryPos.x + j < 0 || tryPos.x + j > mGameArea.getmGrid().length - 1) {
                            ableToMove = false;
                            break checkLoop;
                        }

                        if (tryPos.y + i > mGameArea.getmGrid()[0].length - 1) {
                            ableToMove = false;
                            break checkLoop;
                        }

                        if (mGameArea.getmGrid()[tryPos.x + j][tryPos.y + i].ismIsFilled()) {
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
            mGameArea.spawnTetromino();
        }
    }

    public void freeze() {
        mCanMove = false;
    }
}
