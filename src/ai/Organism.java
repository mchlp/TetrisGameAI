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

    public String printFitness() {
        String message = "";
        message += "-\n";
        message += "Organism - " + mId.toString() + "\n";
        message += "Score: " + mScore + "\n";
        message += "Level: " + mLevel + "\n";
        message += "Lines: " + mLinesCleared + "\n";
        message += "Fitness: " + calculateFitness() + "\n";
        return message;
    }

    public String printGenes() {
        String message = "";
        message += "-\n";
        message += "Organism - " + mId.toString() + "\n";
        for (Genes geneType : Genes.values()) {
            message += geneType.name() + ": " + mGenome.getGeneValue(geneType) + "\n";
        }
        return message;
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
