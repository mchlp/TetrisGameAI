/*
 * Michael Pu
 * TetrisGameAI - TetrominoBlueprint
 * ICS3U1 - Mr.Radulovic
 * January 18, 2018
 */

package backend;

import javafx.scene.paint.Color;

/**
 * All the possible Tetrominos.
 */
public enum TetrominoBlueprint {

    I(Color.rgb(0, 159, 218), new int[][]{
            {0, 0, 0, 0},
            {1, 1, 1, 1},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
    }),
    J(Color.rgb(0, 101, 189), new int[][]{
            {1, 0, 0},
            {1, 1, 1},
            {0, 0, 0}
    }),
    L(Color.rgb(255, 121, 0), new int[][]{
            {0, 0, 1},
            {1, 1, 1},
            {0, 0, 0}
    }),
    O(Color.rgb(254, 203, 0), new int[][]{
            {1, 1},
            {1, 1}
    }),
    S(Color.rgb(105, 190, 40), new int[][]{
            {0, 1, 1},
            {1, 1, 0},
            {0, 0, 0},
    }),
    Z(Color.rgb(237, 41, 57), new int[][]{
            {1, 1, 0},
            {0, 1, 1},
            {0, 0, 0},
    }),
    T(Color.rgb(149, 45, 152), new int[][]{
            {0, 1, 0},
            {1, 1, 1},
            {0, 0, 0},
    });

    /**
     * An integer array presenting the structure of the Tetromino. An integer array was used to easier human
     * readability.
     */
    public final int[][] body;

    /**
     * The colour of the Tetromino.
     */
    public final Color colour;

    TetrominoBlueprint(Color colour, int[][] body) {
        this.colour = colour;
        this.body = body;
    }
}
