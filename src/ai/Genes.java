/*
 * Michael Pu
 * TetrisGameAI - Genes
 * ICS3U1 - Mr. Radulovic
 * December 25, 2017
 */

package ai;

public enum Genes {
    NUM_HOLES("Number of holes"),
    ROUGHNESS("Roughness"),
    LINES_CLEARED("Lines cleared"),
    NUM_VALLEYS("Number of valleys"),
    NUM_MOUNTAINS("Number of mountains"),
    HEIGHT("Height of tallest column");

    private final String name;

    Genes(String name) {
        this.name = name;
    }

}
