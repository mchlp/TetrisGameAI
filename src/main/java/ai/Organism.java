/*
 * Michael Pu
 * TetrisGameAI - Organism
 * ICS3U1 - Mr. Radulovic
 * January 01, 2018
 */

package ai;

import java.io.*;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Represents an organism in the genetic algorithm training process. Among other values, it stores a {@link Genome} and
 * statistics of its past games. It implements the {@link Serializable} interface to allow to it to be exported to a file
 * and loaded from a file for later use.
 */
public class Organism implements Serializable {

    /**
     * The genome of the current organism.
     */
    private Genome mGenome;

    /**
     * The maximum score this organism has reached in a game.
     */
    private int mMaxScore;

    /**
     * The maximum number of lines this organism has cleared in a game.
     */
    private int mMaxLinesCleared;

    /**
     * The maximum level this organism ahs reached in a game.
     */
    private int mMaxLevel;

    /**
     * The generation this organism was created.
     */
    private int mGeneration;

    /**
     * A list of the scores of the games this organism has played.
     */
    private ArrayList<Integer> mScoreList;

    /**
     * The name of this organism. Initially it is a string representation of a random 128-bit number.
     *
     * @see UUID
     */
    private String mName;

    /**
     * Creates an first generation organism with a random or partly-random genome.
     */
    public Organism() {
        this(Genome.getInitialGenome());
    }

    /**
     * Creates an organism with a set genome.
     *
     * @param genome The genome for the organism.
     */
    public Organism(Genome genome) {
        // generate random unique name for organism
        mName = UUID.randomUUID().toString();
        // the generation of the organism is to be set by its population
        mGeneration = -1;
        // initialize statistics
        mMaxScore = 0;
        mMaxLinesCleared = 0;
        mMaxLevel = 0;
        mScoreList = new ArrayList<>();
        mGenome = genome;
    }

    /**
     * Loads an {@link Organism} object from a file.
     *
     * @param loadFile The file where the organism is stored.
     * @return The organism loaded from the file.
     * @throws IOException            An error has occurred when reading the file.
     * @throws ClassNotFoundException An {@link Organism} object cannot be found in the file.
     */
    public static Organism loadOrganismFromFile(File loadFile) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(loadFile);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        Organism loadedOrganism = (Organism) in.readObject();
        return loadedOrganism;
    }

    /**
     * Breeds an organism with another organism to produce a child organism.
     *
     * @param otherParent The other parent organism.
     * @return The child organism.
     */
    public Organism breed(Organism otherParent) {
        return new Organism(mGenome.merge(otherParent.mGenome));
    }

    /**
     * The fitness of the organism is defined as the average of the scores of all the games this organism has played.
     *
     * @return An integer representing the fitness of the organism.
     */
    public int calculateFitness() {
        return getTotalScore() / (Math.max(mScoreList.size(), 1));
    }

    public int getmMaxScore() {
        return mMaxScore;
    }

    public void setmMaxScore(int mMaxScore) {
        this.mMaxScore = mMaxScore;
    }

    /**
     * @return An integer representing the sum of the scores of all the games this organism has played.
     */
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

    /**
     * Generates a string which includes the details of the organism in a user-readable format.
     *
     * @return A string which contains the details of the organism, statistics of the organism, and its genome.
     */
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

    /**
     * Writes the {@link Organism} object to a file.
     *
     * @param saveFile The file to write to.
     * @throws IOException An error has occurred when writing to the file.
     */
    public void writeToFile(File saveFile) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(saveFile);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(this);
        out.close();
        fileOut.close();
    }

    /**
     * @return A deep copy of the {@link Organism} object.
     */
    public Organism duplicate() {
        return new Organism(mGenome.duplicate());
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

    /**
     * Adds a score to the list of scores.
     *
     * @param score The score to add.
     */
    public void addScore(int score) {
        mScoreList.add(score);
    }

    public void setmGeneration(int mGeneration) {
        this.mGeneration = mGeneration;
    }

    public int getmGeneration() {
        return mGeneration;
    }
}
