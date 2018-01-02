/*
 * Michael Pu
 * TetrisGameAI - Gene
 * ICS3U1 - Mr. Radulovic
 * January 01, 2018
 */

package ai;

import java.io.Serializable;

/**
 * Represents a gene in the genetic algorithm training process. It stores the type of gene it represents as well as the
 * value of the gene. It implements the {@link Serializable} interface to allow the genome it is a part of to be
 * exported to a file and loaded from a file for later use.
 */
public class Gene implements Serializable {

    /**
     * The chance that a child gene will undergo mutation.
     */
    private static final double MUTATION_RATE = 0.1;

    /**
     * The type of information this gene stores.
     */
    private Genes mType;

    /**
     * The value stored by the gene.
     */
    private double mValue;

    public Gene(Genes type, double value) {
        mType = type;
        mValue = value;
    }

    public double getmValue() {
        return mValue;
    }

    /**
     * Merges two genes together to form a child gene. The two parents genes undergo crossover and possibly mutation to
     * create the child gene. The child gene will contain the value of either parent gene. For {@value #MUTATION_RATE}
     * of all merges, a value between -1 and 1 multiplied by the multiplier for that type of gene is added to the value
     * of the child gene.
     *
     * @param parentGene The other parent gene.
     * @return The child gene.
     */
    public Gene merge(Gene parentGene) {
        double newValue = Math.random() >= 0.5 ? mValue : parentGene.mValue;
        if (Math.random() < MUTATION_RATE) {
            newValue += ((Math.random() * 2) - 1) * Math.abs(mType.modifier);
        }
        return new Gene(mType, newValue);
    }

    /**
     * @return A deep copy of the {@link Gene} object.
     */
    public Gene duplicate() {
        return new Gene(mType, mValue);
    }
}
