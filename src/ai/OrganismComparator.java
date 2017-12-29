/*
 * Michael Pu
 * TetrisGameAI - OrganismComparator
 * ICS3U1 - Mr.Radulovic
 * December 28, 2017
 */

package ai;

import java.util.Comparator;

public class OrganismComparator implements Comparator<Organism> {
    @Override
    public int compare(Organism organism1, Organism organism2) {
        return Integer.compare(organism1.calculateFitness(), organism2.calculateFitness());
    }
}
