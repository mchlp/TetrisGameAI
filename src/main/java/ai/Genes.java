/*
 * Michael Pu
 * TetrisGameAI - Genes
 * ICS3U1 - Mr.Radulovic
 * January 18, 2018
 */

package ai;

/**
 * All the possible types for a {@link Gene}.
 */
public enum Genes {
    /**
     * The number of lines cleared.
     */
    LINES_CLEARED(1),

    /**
     * The number of holes in the grid. A hole is defined as an empty cell that has a filled cell above it.
     */
    NUM_HOLES(-1),

    /**
     * The difference in the number of holes between before the move was applied and after.
     */
    INCREASED_NUMBER_OF_HOLES(-1),

    /**
     * The sum of the number of filled cells above each hole in the grid.
     */
    SUM_BLOCKS_ABOVE_HOLE(-0.5),

    /**
     * The absolute difference in height between adjacent columns.
     */
    ROUGHNESS(0),

    /**
     * The sum of the heights of each of the columns.
     */
    TOTAL_HEIGHT(0),

    /**
     * The height of the tallest column.
     */
    MAX_HEIGHT(-1),

    /**
     * The difference in height between the tallest and shortest column.
     */
    MIN_MAX_HEIGHT_DIFFERENCE(0),

    /**
     * The number of cells that are adjacent to the left or right edge.
     */
    BLOCKS_TOUCHING_SIDE(0);

    /**
     * The number that is multiplied with a random value between 0 and 1 to generate the initial value of the gene. If
     * this is <code>0</code>, either <code>1</code> or <code>-1</code> is randomly selected. The absolute value of this
     * number defines the magnitude at which mutations for this gene occurs. Every time this gene mutates, the mutation
     * value is multiplied with the absolute value of this number.
     */
    public final double modifier;

    Genes(double modifier) {
        this.modifier = modifier;
    }

}
