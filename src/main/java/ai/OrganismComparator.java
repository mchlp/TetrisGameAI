/*
 * Michael Pu
 * TetrisGameAI - OrganismComparator
 * ICS3U1 - Mr.Radulovic
 * January 13, 2018
 */

package ai;

import java.util.Comparator;

/**
 * A Comparator for comparing the order of two {@link Organism}.
 */
public class OrganismComparator implements Comparator<Organism> {

    /**
     * Compares the fitness of two {@link Organism}s. Returns a negative integer, zero, or positive integer if the
     * fitness of the first organism is less than, equal to, or greater than the fitness of the second organism.
     *
     * @param organism1 The first {@link Organism} to compare.
     * @param organism2 The second {@link Organism} to compare.
     * @return An integer representing the relative ordering of the two organisms.
     */
    @Override
    public int compare(Organism organism1, Organism organism2) {
        return Integer.compare(organism1.calculateFitness(), organism2.calculateFitness());
    }
}
