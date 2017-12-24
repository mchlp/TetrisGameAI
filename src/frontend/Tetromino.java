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

    private int[][] body;
    private Color colour;
    private Point curPos;

    public Tetromino(TetrominoBlueprint blueprint, int numCols) {
        body = blueprint.body.clone();
        colour = blueprint.colour;
        curPos = new Point((numCols/2)-(body[0].length/2), 0);
    }

    public void rotate() {
        int[][] newBody = new int[body.length][body[0].length];
        for (int i=0; i<newBody.length; i++) {
            for (int j=0; j<newBody[0].length; j++) {
                newBody[newBody.length-j-1][i] = body[i][j];
            }
        }
        body = newBody;
    }

    public int[][] getBody() {
        return body;
    }

    public Point getCurPos() {
        return curPos;
    }

    public Color getColour() {
        return colour;
    }

    public void moveLeft() {
        curPos.x--;
    }

    public void moveRight() {
        curPos.x++;
    }

    public void moveDown() {
        curPos.y++;
    }

}
