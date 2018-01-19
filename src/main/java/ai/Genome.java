/*
 * Michael Pu
 * TetrisGameAI - Genome
 * ICS3U1 - Mr.Radulovic
 * January 13, 2018
 */

package ai;

import java.io.Serializable;

/**
 * Represents a genome in the genetic algorithm training process. It stores an array of {@link Gene}s, which contains a
 * {@link Gene} for each type of gene in the {@link Genes} enum. It implements the {@link Serializable} interface to
 * allow the organism it is a part of to be exported to a file and loaded from a file for later use.
 */
public class Genome implements Serializable {

    /**
     * An array storing all of the genes that are a part of the genome.
     */
    private Gene[] mGeneList;

    /**
     * Creates a {@link Genome} from an array of {@link Gene}s.
     *
     * @param geneList The array of {@link Gene}s that will be stored.
     */
    public Genome(Gene[] geneList) {
        mGeneList = geneList;
    }

    /**
     * Produces an entirely random genome, where the value of each gene is a random value between <code>-1</code> and
     * <code>1</code>. The <code>modifier</code> of the {@link Gene} is not considered.
     *
     * @return A random genome.
     */
    public static Genome getRandomGenome() {
        Gene[] geneList = new Gene[Genes.values().length];

        for (Genes geneType : Genes.values()) {
            double randomValue = (Math.random() * 2) - 1;
            geneList[geneType.ordinal()] = new Gene(geneType, randomValue);
        }
        return new Genome(geneList);
    }

    /**
     * Produces a semi-random genome, where the value of each gene is the product of a random value between
     * <code>0</code> and <code>1</code> and the <code>modifier</code> of the {@link Gene}. If the <code>modifier</code>
     * of the {@link Gene} is 0, either <code>1</code> or <code>-1</code> is randomly selected to be used instead.
     *
     * @return A semi-random genome.
     */
    public static Genome getInitialGenome() {
        Gene[] geneList = new Gene[Genes.values().length];

        for (Genes geneType : Genes.values()) {
            double randomValue = Math.random();
            if (geneType.modifier == 0) {
                randomValue *= (Math.random() > 0.5 ? 1 : -1);
            } else {
                randomValue *= geneType.modifier;
            }
            geneList[geneType.ordinal()] = new Gene(geneType, randomValue);
        }
        return new Genome(geneList);
    }

    /**
     * Merges two {@link Genome}s together by calling the <code>merge</code> method for each corresponding {@link Gene}
     * in the two parent {@link Genome}s, producing one child {@link Genome}.
     *
     * @param otherParent The other parent genome.
     * @return A child genome.
     */
    public Genome merge(Genome otherParent) {
        Gene[] childGeneList = new Gene[mGeneList.length];
        for (int i = 0; i < mGeneList.length; i++) {
            childGeneList[i] = mGeneList[i].merge(otherParent.mGeneList[i]);
        }
        return new Genome(childGeneList);
    }

    public double getGeneValue(Genes gene) {
        return mGeneList[gene.ordinal()].getmValue();
    }

    /**
     * @return A deep copy of the {@link Genome} object.
     */
    public Genome duplicate() {
        Gene[] clonedGeneList = new Gene[mGeneList.length];
        for (int i = 0; i < mGeneList.length; i++) {
            clonedGeneList[i] = mGeneList[i].duplicate();
        }
        return new Genome(clonedGeneList);
    }
}
