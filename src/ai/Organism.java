/*
 * Michael Pu
 * TetrisGameAI - Organism
 * ICS3U1 - Mr. Radulovic
 * December 26, 2017
 */

package ai;

import java.io.*;
import java.util.ArrayList;
import java.util.UUID;

public class Organism implements Serializable {

    private Genome mGenome;
    private int mMaxScore;
    private int mMaxLinesCleared;
    private int mMaxLevel;
    private ArrayList<Integer> mScoreList;
    private UUID mId;

    public Organism() {
        this(Genome.getInitialGenome());
    }

    public Organism(Genome genome) {
        mId = UUID.randomUUID();
        mMaxScore = 0;
        mMaxLinesCleared = 0;
        mMaxLevel = 0;
        mScoreList = new ArrayList<>();
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
        return getTotalScore()/(Math.max(mScoreList.size(), 1));
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

    public int getTotalScore() {
        int sum = 0;
        for (int i : mScoreList) {
            sum += i;
        }
        return sum;
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
        message += "Max Score: " + mMaxScore + "\n";
        message += "Max Level: " + mMaxLevel + "\n";
        message += "Max Lines: " + mMaxLinesCleared + "\n";
        message += "Fitness: " + calculateFitness() + "\n";
        message += "Scores: " + mScoreList.toString() + "\n";
        message += "Total Score: " + getTotalScore() + "\n";
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

    public int getmMaxLinesCleared() {
        return mMaxLinesCleared;
    }

    public int getmMaxLevel() {
        return mMaxLevel;
    }

    public void addScore(int score) {
        mScoreList.add(score);
    }
}
