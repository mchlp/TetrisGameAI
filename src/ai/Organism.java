/*
 * Michael Pu
 * TetrisGameAI - Organism
 * ICS3U1 - Mr.Radulovic
 * December 28, 2017
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
    private int mGeneration;
    private ArrayList<Integer> mScoreList;
    private String mName;

    public Organism() {
        this(Genome.getInitialGenome());
    }

    public Organism(Genome genome) {
        mName = UUID.randomUUID().toString();
        mGeneration = -1;
        mMaxScore = 0;
        mMaxLinesCleared = 0;
        mMaxLevel = 0;
        mScoreList = new ArrayList<>();
        mGenome = genome;
    }

    public static Organism loadOrganismFromFile(File loadFile) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(loadFile);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        Organism loadedOrganism = (Organism) in.readObject();
        return loadedOrganism;
    }

    public Organism breed(Organism otherParent) {
        return new Organism(mGenome.merge(otherParent.mGenome));
    }

    public int calculateFitness() {
        return getTotalScore() / (Math.max(mScoreList.size(), 1));
    }

    public int getmMaxScore() {
        return mMaxScore;
    }

    public void setmMaxScore(int mMaxScore) {
        this.mMaxScore = mMaxScore;
    }

    public int getTotalScore() {
        int sum = 0;
        for (int i : mScoreList) {
            sum += i;
        }
        return sum;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public Genome getmGenome() {
        return mGenome;
    }

    public String getStatus() {
        String message = "";
        message += "ORGANISM NAME\n" + mName + "\n\n";
        message += "Generation: " + mGeneration + "\n";
        message += "Max Score: " + mMaxScore + "\n";
        message += "Max Level: " + mMaxLevel + "\n";
        message += "Max Lines: " + mMaxLinesCleared + "\n";
        message += "Fitness: " + calculateFitness() + "\n";
        message += "Scores: " + mScoreList.toString() + "\n";
        message += "Total Score: " + getTotalScore() + "\n";
        message += "\nGenes\n";
        for (Genes geneType : Genes.values()) {
            message += geneType.name() + ": " + mGenome.getGeneValue(geneType) + "\n";
        }
        return message;
    }

    public void saveToFile(File saveFile) {
        try {
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

    public void setmMaxLinesCleared(int mMaxLinesCleared) {
        this.mMaxLinesCleared = mMaxLinesCleared;
    }

    public int getmMaxLevel() {
        return mMaxLevel;
    }

    public void setmMaxLevel(int mMaxLevel) {
        this.mMaxLevel = mMaxLevel;
    }

    public void addScore(int score) {
        mScoreList.add(score);
    }

    public void setmGeneration(int mGeneration) {
        this.mGeneration = mGeneration;
    }
}
