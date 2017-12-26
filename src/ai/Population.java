/*
 * Michael Pu
 * TetrisGameAI - Population
 * ICS3U1 - Mr. Radulovic
 * December 25, 2017
 */

package ai;

import com.sun.deploy.util.ArrayUtil;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class Population implements Serializable {

    private static final int MAX_NUMBER_OF_ORGANISMS = 50;
    private static final int MAX_NUMBER_OF_ELITES = 5;

    private Organism[] mOrganisms;
    private List<Organism> mElites;
    private transient File mSaveFile;
    private int mGeneration;
    private double mTrainTime;

    public Population(File saveFile) {

        mGeneration = 1;
        mTrainTime = 0;
        mSaveFile = saveFile;
        mOrganisms = new Organism[MAX_NUMBER_OF_ORGANISMS];
        mElites = new ArrayList<>();

        for (int i = 0; i < MAX_NUMBER_OF_ORGANISMS; i++) {
            mOrganisms[i] = new Organism();
        }
    }

    public static Population loadPopulationFromFile(File loadFile) {
        try {
            FileInputStream fileIn = new FileInputStream(loadFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Population loadedPopulation = (Population) in.readObject();
            loadedPopulation.mSaveFile = loadFile;
            return loadedPopulation;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void evolve() {
        mGeneration++;
        Organism[] survivors = selectAndKill();
        System.out.println("Top Fitness: " + survivors[0].calculateFitness());
        int totalFitness = 0;
        for (Organism organism : survivors) {
            totalFitness += organism.calculateFitness();
        }
        System.out.println("Total Fitness: " + totalFitness);
        breed(survivors);
        saveToFile();
        System.out.println("SAVED TO FILE.");
    }

    public Organism[] selectAndKill() {
        mOrganisms = sort(Arrays.asList(mOrganisms)).toArray(mOrganisms);
        addElite(mOrganisms[0]);
        Organism[] survivors = Arrays.copyOfRange(mOrganisms, 0, mOrganisms.length / 2 - mElites.size());
        for (int i=0; i<mElites.size(); i++) {
            survivors[survivors.length-i-1] = mElites.get(i);
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
        for (int i=0; i<MAX_NUMBER_OF_ORGANISMS; i++) {
            mOrganisms[i] = pickRandomOrganism(parents).breed(pickRandomOrganism(parents));
        }
    }

    private Organism pickRandomOrganism(Organism[] organisms) {
        int index = (int) (Math.random()*organisms.length);
        return organisms[index];
    }

    private void addElite(Organism elite) {
        mElites.add(elite);
        mElites = sort(mElites);
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

    public void saveElite(File saveFolder) {
        Organism[] organisms = mOrganisms.clone();
        Arrays.sort(organisms, new OrganismComparator());
        organisms[0].saveToFile(saveFolder);
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
}
