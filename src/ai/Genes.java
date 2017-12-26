/*
 * Michael Pu
 * TetrisGameAI - Genes
 * ICS3U1 - Mr. Radulovic
 * December 25, 2017
 */

package ai;

public enum Genes {
    LINES_CLEARED(1, "Lines cleared"),
    NUM_HOLES(-1, "Number of holes"),
    INCREASED_NUMBER_OF_HOLES(-1, "Number of holes increased"),
    SUM_BLOCKS_ABOVE_HOLE(-1, "Number of blocks above a hole"),
    ROUGHNESS(0, "Roughness"),
    TOTAL_HEIGHT(-1, "Total of heights"),
    MAX_HEIGHT(-1, "Height of tallest column"),
    MIN_MAX_HEIGHT_DIFFERENCE(-1, "Difference between lowest and highest column");

    public final String name;
    public final int modifier;

    Genes(int modifier, String name) {
        this.modifier = modifier;
        this.name = name;
    }

}
