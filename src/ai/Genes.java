/*
 * Michael Pu
 * TetrisGameAI - Genes
 * ICS3U1 - Mr. Radulovic
 * January 01, 2018
 */

package ai;

/**
 *
 */
public enum Genes {
    LINES_CLEARED(1),
    NUM_HOLES(-1),
    INCREASED_NUMBER_OF_HOLES(-1),
    SUM_BLOCKS_ABOVE_HOLE(-0.5),
    ROUGHNESS(0),
    TOTAL_HEIGHT(0),
    MAX_HEIGHT(-1),
    MIN_MAX_HEIGHT_DIFFERENCE(0),
    BLOCKS_TOUCHING_SIDE(0);

    public final double modifier;

    Genes(double modifier) {
        this.modifier = modifier;
    }

}
