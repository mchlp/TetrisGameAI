/*
 * Michael Pu
 * TetrisGameAI - Genes
 * ICS3U1 - Mr. Radulovic
 * December 25, 2017
 */

package ai;

public enum Genes {
    LINES_CLEARED("Lines cleared"),
    NUM_HOLES("Number of holes"),
    ROUGHNESS("Roughness"),
    RELATIVE_HEIGHT("Sum of absolute differences of heights"),
    TOTAL_HEIGHT("Total of heights"),
    MAX_HEIGHT("Height of tallest column");

    private final String name;

    Genes(String name) {
        this.name = name;
    }

}
