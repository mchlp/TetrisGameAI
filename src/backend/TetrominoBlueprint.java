/*
 * Michael Pu
 * TetrisGameAI - TetrominoBlueprint
 * ICS3U1 - Mr. Radulovic
 * December 23, 2017
 */

package backend;

import javafx.scene.paint.Color;

public enum TetrominoBlueprint {

    I(Color.rgb(0, 159, 218), new int[][]{
            {0, 0, 0, 0},
            {1, 1, 1, 1},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
    }),
    J(Color.rgb(0, 101, 189), new int[][] {
            {1, 0, 0},
            {1, 1, 1},
            {0, 0, 0}
    }),
    L(Color.rgb(255, 121, 0), new int[][] {
            {0, 0, 1},
            {1, 1, 1},
            {0, 0, 0}
    }),
    O(Color.rgb(254, 203, 0), new int[][] {
            {1, 1},
            {1, 1}
    }),
    S(Color.rgb(105, 190, 40), new int[][] {
            {0, 1, 1},
            {1, 1, 0},
            {0, 0, 0},
    }),
    Z(Color.rgb(237, 41, 57), new int[][] {
            {1, 1, 0},
            {0, 1, 1},
            {0, 0, 0},
    }),
    T(Color.rgb(149, 45, 152), new int[][] {
            {0, 1, 0},
            {1, 1, 1},
            {0, 0, 0},
    });

    public final int[][] body;
    public final Color colour;

    TetrominoBlueprint(Color colour, int[][] body) {
        this.colour = colour;
        this.body = body;
    }

}
