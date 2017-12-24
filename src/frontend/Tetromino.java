/*
 * Michael Pu
 * TetrisGameAI - Tetromino
 * ICS3U1 - Mr. Radulovic
 * December 23, 2017
 */

package frontend;

import backend.TetrominoBlueprint;
import javafx.scene.paint.Color;

import java.awt.*;

public class Tetromino {

    private GameArea mGameArea;
    private int[][] mBody;
    private Color mColour;
    private Point mCurPos;

    public Tetromino(GameArea gameArea, TetrominoBlueprint blueprint, int numCols) {
        mGameArea = gameArea;
        mBody = blueprint.body.clone();
        mColour = blueprint.colour;
        mCurPos = new Point((numCols/2)-(mBody[0].length/2), 0);
    }

    public void rotate() {
        int[][] newBody = new int[mBody.length][mBody[0].length];
        for (int i=0; i<newBody.length; i++) {
            for (int j=0; j<newBody[0].length; j++) {
                newBody[newBody.length-j-1][i] = mBody[i][j];
            }
        }
        mBody = newBody;
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

    public void moveLeft() {
        mCurPos.x--;
    }

    public void moveRight() {
        mCurPos.x++;
    }

    public void moveDown() {
        mCurPos.y++;
    }

    public void update() {
        //moveDown();
    }

}
