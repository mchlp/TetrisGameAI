/*
 * Michael Pu
 * TetrisGameAI - Genes
 * ICS3U1 - Mr.Radulovic
 * December 28, 2017
 */

package ai;

public enum Genes {
    LINES_CLEARED(1, "Lines cleared"),
    NUM_HOLES(-1, "Number of holes"),
    INCREASED_NUMBER_OF_HOLES(-1, "Number of holes increased"),
    SUM_BLOCKS_ABOVE_HOLE(-0.5, "Number of blocks above a hole"),
    ROUGHNESS(0, "Roughness"),
    TOTAL_HEIGHT(0, "Total of heights"),
    MAX_HEIGHT(-1, "Height of tallest column"),
    MIN_MAX_HEIGHT_DIFFERENCE(0, "Difference between lowest and highest column"),
    BLOCKS_TOUCHING_SIDE(0, "Blocks touching the sides");

    public final String name;
    public final double modifier;

    Genes(double modifier, String name) {
        this.modifier = modifier;
        this.name = name;
    }

}
