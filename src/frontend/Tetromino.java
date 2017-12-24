/*
 * Michael Pu
 * TetrisGameAI - Tetromino
 * ICS3U1 - Mr. Radulovic
 * December 23, 2017
 */

package frontend;

import backend.TetrominoBlueprint;
import javafx.scene.paint.Color;

public class Tetromino {

    private int[][] mBody;
    private Color mColour;

    public Tetromino(TetrominoBlueprint blueprint) {
        mBody = blueprint.body.clone();
        mColour = blueprint.colour;
    }

}
