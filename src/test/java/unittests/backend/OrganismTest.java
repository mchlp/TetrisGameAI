/*
 * Michael Pu
 * TetrisGameAI - OrganismTest
 * ICS3U1 - Mr.Radulovic
 * January 13, 2018
 */

package unittests.backend;

import ai.Genes;
import ai.Genome;
import ai.Organism;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class OrganismTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void constructor_NoError() {
        Organism organism = new Organism();
        assertThat(organism.getmMaxLinesCleared(), is(0));
        assertThat(organism.getmMaxLevel(), is(0));
        assertThat(organism.getmMaxScore(), is(0));
        assertThat(organism.calculateFitness(), is(0));
        assertThat(organism.getmName(), instanceOf(String.class));
        assertThat(organism.getTotalScore(), is(0));
        assertThat(organism.getStatus(), instanceOf(String.class));
        assertThat(organism.getmGeneration(), is(-1));
        assertThat(organism.getmGenome(), instanceOf(Genome.class));
    }

    @Test
    public void duplicate_NoError() {
        Organism organism1 = new Organism();
        Organism organism2 = organism1.duplicate();
        assertThat(organism2, not(sameInstance(organism1)));
        assertThat(organism2.getmName(), not(organism1.getmName()));
        Genome genome1 = organism1.getmGenome();
        Genome genome2 = organism2.getmGenome();
        for (Genes gene : Genes.values()) {
            assertThat(genome1.getGeneValue(gene), is(genome2.getGeneValue(gene)));
        }
    }

}