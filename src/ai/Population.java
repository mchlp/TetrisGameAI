/*
 * Michael Pu
 * TetrisGameAI - Population
 * ICS3U1 - Mr.Radulovic
 * December 28, 2017
 */

package ai;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Population implements Serializable {

    private static final int MAX_NUMBER_OF_ORGANISMS = 50;
    private static final int MAX_NUMBER_OF_ELITES = 5;
    private static final double REPRODUCING_PERCENTAGE = 0.5;

    private Organism[] mOrganisms;
    private ArrayList<Organism> mElites;
    private transient File mSaveFile;
    private int mGeneration;
    private double mTrainTime;
    private int mTotalFitness;
    private int mTopFitness;
    private int mMinFitness;
    private int mAvgFitness;
    private int mEliteFitness;
    private int mTop25PerFitness;
    private int mBottom25PerFitness;

    public Population(File saveFile) {

        mGeneration = 1;
        mTrainTime = 0;
        mSaveFile = saveFile;
        mTopFitness = 0;
        mMinFitness = 0;
        mTotalFitness = 0;
        mAvgFitness = 0;
        mOrganisms = new Organism[MAX_NUMBER_OF_ORGANISMS];
        mElites = new ArrayList<>();

        for (int i = 0; i < MAX_NUMBER_OF_ORGANISMS; i++) {
            mOrganisms[i] = new Organism();
        }
    }

    public static Population loadPopulationFromFile(File loadFile) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(loadFile);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        Population loadedPopulation = (Population) in.readObject();
        loadedPopulation.mSaveFile = loadFile;
        return loadedPopulation;
    }

    public void evolve() {
        mGeneration++;
        Organism[] survivors = selectAndKill();
        breed(survivors);
        saveToFile();
    }

    public Organism[] selectAndKill() {
        mOrganisms = sort(Arrays.asList(mOrganisms)).toArray(mOrganisms);
        addElite(mOrganisms[0]);
        mEliteFitness = mElites.get(0).calculateFitness();
        mTopFitness = mOrganisms[0].calculateFitness();
        mMinFitness = mOrganisms[getNumOrganisms() - 1].calculateFitness();
        int totalFitness = 0;
        for (Organism organism : mOrganisms) {
            totalFitness += organism.calculateFitness();
        }
        int top25Fitness = 0;
        int bottom25Fitness = 0;
        int num25Per = getNumOrganisms() / 4;
        for (int i = 0; i < num25Per; i++) {
            top25Fitness += mOrganisms[i].calculateFitness();
            bottom25Fitness += mOrganisms[getNumOrganisms() - i - 1].calculateFitness();
        }
        mTop25PerFitness = top25Fitness / num25Per;
        mBottom25PerFitness = bottom25Fitness / num25Per;
        mTotalFitness = totalFitness;
        mAvgFitness = totalFitness / getNumOrganisms();
        Organism[] survivors = Arrays.copyOfRange(mOrganisms, 0, (int) (mOrganisms.length * REPRODUCING_PERCENTAGE) - mElites.size());
        for (int i = 0; i < mElites.size(); i++) {
            survivors[survivors.length - i - 1] = mElites.get(i).clone();
        }
        survivors = sort(Arrays.asList(survivors)).toArray(survivors);
        return survivors;
    }

    private List<Organism> sort(List<Organism> organisms) {
        organisms.sort(new OrganismComparator());
        Collections.reverse(organisms);
        return organisms;
    }


    private void breed(Organism[] parents) {
        int childIndex = 0;
        /*for (int i=0; i<mElites.size(); i++) {
            mOrganisms[childIndex] = mElites.get(0).clone();
            childIndex++;
        }
        for (int i=0; i<(parents.length/3); i++) {
            mOrganisms[childIndex] = parents[i].breed(parents[i+1]);
            childIndex++;
            mOrganisms[childIndex] = parents[i].breed(parents[i+1]);
            childIndex++;
        }*/
        while (childIndex < MAX_NUMBER_OF_ORGANISMS) {
            mOrganisms[childIndex] = pickRandomOrganism(parents).breed(pickRandomOrganism(parents));
            childIndex++;
        }
    }

    private Organism pickRandomOrganism(Organism[] organisms) {
        int index = (int) (Math.random() * organisms.length);
        return organisms[index];
    }

    private void addElite(Organism elite) {
        mElites.add(elite);
        mElites = new ArrayList<>(sort(mElites));
        while (mElites.size() > MAX_NUMBER_OF_ELITES) {
            mElites.remove(mElites.size() - 1);
        }
    }

    public void saveToFile() {
        try {
            FileOutputStream fileOut = new FileOutputStream(mSaveFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Organism getElite() {
        if (mElites.size() == 0) {
            if (mOrganisms[0].getTotalScore() == 0) {
                return null;
            } else {
                return mOrganisms[0];
            }
        }
        return mElites.get(0);
    }

    public Organism getOrganism(int index) {
        return mOrganisms[index];
    }

    public int getNumOrganisms() {
        return mOrganisms.length;
    }

    public int getmGeneration() {
        return mGeneration;
    }

    public double getmTrainTime() {
        return mTrainTime;
    }

    public void addmTrainTime(double addTime) {
        mTrainTime += addTime;
    }

    public int getmTotalFitness() {
        return mTotalFitness;
    }

    public int getmTopFitness() {
        return mTopFitness;
    }

    public int getmMinFitness() {
        return mMinFitness;
    }

    public int getmAvgFitness() {
        return mAvgFitness;
    }

    public int getmEliteFitness() {
        return mEliteFitness;
    }

    public int getmTop25PerFitness() {
        return mTop25PerFitness;
    }

    public int getmBottom25PerFitness() {
        return mBottom25PerFitness;
    }
}
