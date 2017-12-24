/*
 * Michael Pu
 * TetrisGameAI - TetrominoBlueprint
 * ICS3U1 - Mr. Radulovic
 * December 23, 2017
 */

package backend;

import javafx.scene.paint.Color;

public enum TetrominoBlueprint {

    I(Color.CYAN, new int[][]{
            {0, 0, 0, 0},
            {1, 1, 1, 1},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
    }),
    J(Color.BLUE, new int[][] {
            {1, 0, 0},
            {1, 1, 1},
            {0, 0, 0}
    }),
    L(Color.ORANGE, new int[][] {
            {0, 0, 1},
            {1, 1, 1},
            {0, 0, 0}
    }),
    O(Color.YELLOW, new int[][] {
            {1, 1},
            {1, 1}
    }),
    S(Color.GREEN, new int[][] {
            {0, 1, 1},
            {1, 1, 0},
            {0, 0, 0},
    }),
    Z(Color.RED, new int[][] {
            {1, 1, 0},
            {0, 1, 1},
            {0, 0, 0},
    }),
    T(Color.PURPLE, new int[][] {
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
