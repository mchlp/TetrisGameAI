/*
 * Michael Pu
 * TetrisGameAI - Organism
 * ICS3U1 - Mr. Radulovic
 * December 25, 2017
 */

package ai;

import java.io.*;
import java.util.UUID;

public class Organism implements Serializable {

    private Genome mGenome;
    private transient int mScore;
    private transient int mLinesCleared;
    private transient int mLevel;
    private UUID mId;

    public Organism() {
        this(Genome.getInitialGenome());
    }

    public Organism(Genome genome) {
        mId = UUID.randomUUID();
        mScore = 0;
        mLinesCleared = 0;
        mLevel = 0;
        mGenome = genome;
    }

    public static Organism loadOrganismFromFile(File loadFile) {
        try {
            FileInputStream fileIn = new FileInputStream(loadFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Organism loadedOrganism = (Organism) in.readObject();
            return loadedOrganism;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
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

    public void saveToFile(File saveFolder) {
        try {
            File saveFile = new File(saveFolder, mId+".org.ser");
            FileOutputStream fileOut = new FileOutputStream(saveFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
