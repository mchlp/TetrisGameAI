/*
 * Michael Pu
 * TetrisGameAI - Organism
 * ICS3U1 - Mr. Radulovic
 * December 25, 2017
 */

package ai;

import java.io.Serializable;
import java.util.UUID;

public class Organism implements Serializable {

    private Genome mGenome;
    private int mScore;
    private int mLinesCleared;
    private int mLevel;
    private UUID mId;

    public Organism() {
        this(Genome.getRandomGenome());
    }

    public Organism(Genome genome) {
        mId = UUID.randomUUID();
        mGenome = genome;
    }

    public Organism breed(Organism otherParent) {
        return new Organism(mGenome.merge(otherParent.mGenome));
    }

    public int calculateFitness() {
        return mScore;
    }

    public void setmScore(int mScore) {
        this.mScore = mScore;
    }

    public void setmLevel(int mLevel) {
        this.mLevel = mLevel;
    }

    public void setmLinesCleared(int mLinesCleared) {
        this.mLinesCleared = mLinesCleared;
    }

    public UUID getmId() {
        return mId;
    }

    public Genome getmGenome() {
        return mGenome;
    }

    public void printFitness() {
        System.out.println("-");
        System.out.println("Organism - " + mId.toString());
        System.out.println("Score: " + mScore);
        System.out.println("Level: " + mLevel);
        System.out.println("Lines: " + mLinesCleared);
        System.out.println("Fitness: " + calculateFitness());
    }

    public void printGenes() {
        System.out.println("-");
        System.out.println("Organism - " + mId.toString());
        for (Genes geneType : Genes.values()) {
            System.out.println(geneType.name() + ": " + mGenome.getGeneValue(geneType));
        }
    }
}
