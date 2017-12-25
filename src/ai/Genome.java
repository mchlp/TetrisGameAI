/*
 * Michael Pu
 * TetrisGameAI - Genome
 * ICS3U1 - Mr. Radulovic
 * December 25, 2017
 */

package ai;

import java.io.Serializable;

public class Genome implements Serializable {

    private Gene[] mGeneList;

    public Genome(Gene[] geneList) {
        mGeneList = geneList;
    }

    public static Genome getRandomGenome() {
        Gene[] geneList = new Gene[Genes.values().length];

        for (Genes geneType : Genes.values()) {
            double initalValue = Math.random() * Math.random() >= 0.5 ? 1 : -1;
            geneList[geneType.ordinal()] = new Gene(geneType, initalValue);
        }
        return new Genome(geneList);
    }

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
}
