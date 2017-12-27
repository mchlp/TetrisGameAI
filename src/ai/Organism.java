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
    private int mMaxScore;
    private int mMaxLinesCleared;
    private int mMaxLevel;
    private int mTotalScore;
    private int mNumGames;
    private UUID mId;

    public Organism() {
        this(Genome.getInitialGenome());
    }

    public Organism(Genome genome) {
        mId = UUID.randomUUID();
        mMaxScore = 0;
        mMaxLinesCleared = 0;
        mMaxLevel = 0;
        mTotalScore = 0;
        mNumGames = 0;
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
        return mTotalScore/ Math.max(mNumGames, 1);
    }

    public void setmMaxScore(int mMaxScore) {
        this.mMaxScore = mMaxScore;
    }

    public int getmMaxScore() {
        return mMaxScore;
    }

    public void setmMaxLevel(int mMaxLevel) {
        this.mMaxLevel = mMaxLevel;
    }

    public void setmMaxLinesCleared(int mMaxLinesCleared) {
        this.mMaxLinesCleared = mMaxLinesCleared;
    }

    public UUID getmId() {
        return mId;
    }

    public Genome getmGenome() {
        return mGenome;
    }

    public String printFitness() {
        String message = "";
        message += "Organism - " + mId.toString() + "\n";
        message += "Score: " + mMaxScore + "\n";
        message += "Level: " + mMaxLevel + "\n";
        message += "Lines: " + mMaxLinesCleared + "\n";
        message += "Fitness: " + calculateFitness() + "\n";
        message += "Total Score: " + mTotalScore + "\n";
        return message;
    }

    public String printGenes() {
        String message = "";
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

    public Organism clone() {
        return new Organism(mGenome.clone());
    }

    public int getmTotalScore() {
        return mTotalScore;
    }

    public int getmMaxLinesCleared() {
        return mMaxLinesCleared;
    }

    public int getmMaxLevel() {
        return mMaxLevel;
    }

    public void addTotalScore(int mTotalScore) {
        this.mTotalScore += mTotalScore;
        mNumGames++;
    }
}
