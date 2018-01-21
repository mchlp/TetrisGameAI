/*
 * Michael Pu
 * TetrisGameAI - Population
 * ICS3U1 - Mr.Radulovic
 * January 18, 2018
 */

package ai;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Represents a population in the genetic algorithm training process. Among other values, it stores an array of {@link
 * Organism}s, a list of elite {@link Organism}s, statistics of the population, and the {@link File} to save the
 * population. It implements the {@link Serializable} interface to allow to it to be exported to a file and loaded from
 * a file for later use.
 */
public class Population implements Serializable {

    /**
     * The number of organisms in the population.
     */
    private static final int NUMBER_OF_ORGANISMS = 50;

    /**
     * The maximum number of elite organisms to be stored.
     */
    private static final int MAX_NUMBER_OF_ELITES = 5;

    /**
     * The percentage of the population that will be reproducing to produce the next generation of organisms.
     */
    private static final double REPRODUCING_PERCENTAGE = 0.5;

    /**
     * Stores the {@link Organism}s in the population in the current generation.
     */
    private Organism[] mOrganisms;

    /**
     * Stores the elite {@link Organism}s. Every generation, the fittest {@link Organism} is added to this list. If
     * there are more than {@value MAX_NUMBER_OF_ELITES} organisms, the organism with the lowest fitness is removed.
     * During every breeding session, each of the organisms in this list are added to the pool of reproducing
     * organisms.
     */
    private ArrayList<Organism> mElites;

    /**
     * The file to save the population to. This is marked <code>transient</code> as it is not saved when the population
     * is serialized and saved to a file.
     */
    private transient File mSaveFile;

    /**
     * The generation of organisms the population on. Equivalent to the number of times the population has been bred
     * plus one.
     */
    private int mGeneration;

    /**
     * The number of seconds the population has been training for.
     */
    private double mTrainTime;

    /**
     * The sum of the fitness of all of the organisms in the last generation.
     */
    private int mTotalFitness;

    /**
     * The fitness of the fittest organism in the last generation.
     */
    private int mTopFitness;

    /**
     * The fitness of the least fit organism in the last generation.
     */
    private int mMinFitness;

    /**
     * The average fitness of all the organisms in the last generation.
     */
    private int mAvgFitness;

    /**
     * The fitness of the fittest organism in the {@link #mElites} list. This is the fittest organism that has ever bred
     * in the population.
     */
    private int mEliteFitness;

    /**
     * The average fitness of the fittest 25 percent of the last generation.
     */
    private int mTop25PerFitness;

    /**
     * The average fitness of the least fit 25 percent of the last generation.
     */
    private int mBottom25PerFitness;

    /**
     * Creates a new population with new {@link Organism}s.
     *
     * @param saveFile The file to save the population.
     */
    public Population(File saveFile) {
        // initialize statistics, details, and elites
        mGeneration = 1;
        mTrainTime = 0;
        mSaveFile = saveFile;
        mTopFitness = 0;
        mMinFitness = 0;
        mTotalFitness = 0;
        mAvgFitness = 0;
        mOrganisms = new Organism[NUMBER_OF_ORGANISMS];
        mElites = new ArrayList<>();

        // create new organisms
        for (int i = 0; i < NUMBER_OF_ORGANISMS; i++) {
            mOrganisms[i] = new Organism();
        }
    }

    /**
     * Loads a previously saved population from a file.
     *
     * @param loadFile The file containing the population.
     * @return The population stored in the file.
     * @throws IOException            An error has occurred when reading the file.
     * @throws ClassNotFoundException An {@link Population} object cannot be found in the file.
     */
    public static Population loadPopulationFromFile(File loadFile) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(loadFile);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        Population loadedPopulation = (Population) in.readObject();
        loadedPopulation.mSaveFile = loadFile;
        return loadedPopulation;
    }

    /**
     * Evolves the population through selection and breeding of the population. It advances the population to the next
     * generation. The population is written to the {@link #mSaveFile}.
     *
     * @throws IOException An error has occurred when writing to the file.
     */
    public void evolve() throws IOException {
        mGeneration++;
        Organism[] survivors = selectAndKill();
        breed(survivors);
        writeToFile();
    }

    /**
     * Selects the fittest organisms from the current generation and adds them to a group of organisms that will be used
     * in the breeding stage. Each organism in the {@link #mElites} list is added to this group as well.
     *
     * @return The group of organisms that will be reproducing. It will contain <code>{@value
     * #REPRODUCING_PERCENTAGE}*{@value #NUMBER_OF_ORGANISMS}</code> organisms.
     */
    public Organism[] selectAndKill() {
        // sorts the organisms in order of decreasing fitness
        mOrganisms = sort(Arrays.asList(mOrganisms)).toArray(mOrganisms);
        // add the fittest organism to the elites group
        addElite(mOrganisms[0]);
        // update population statistics
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
        // copies the fittest organisms in the current generation to the reproducing group while leaving just enough space for the elites
        Organism[] survivors = Arrays.copyOfRange(mOrganisms, 0, (int) (mOrganisms.length * REPRODUCING_PERCENTAGE) - mElites.size());
        // adds elites to the reproducing group
        for (int i = 0; i < mElites.size(); i++) {
            survivors[survivors.length - i - 1] = mElites.get(i).duplicate();
        }
        // sorts the reproducing group in order of decreasing fitness
        survivors = sort(Arrays.asList(survivors)).toArray(survivors);
        return survivors;
    }

    /**
     * Sorts a list of {@link Organism}s in order of decreasing fitness.
     *
     * @param organisms The list of organisms to sort.
     * @return The list of sorted organisms.
     */
    private List<Organism> sort(List<Organism> organisms) {
        // sorts the list of organisms in order of increasing fitness
        organisms.sort(new OrganismComparator());
        // reverses to order of decreasing fitness
        Collections.reverse(organisms);
        return organisms;
    }

    /**
     * Generates a new generation of organisms by randomly selecting two organisms in the parents group and breeding
     * them until {@value #NUMBER_OF_ORGANISMS} organisms have been produced.
     *
     * @param parents The group of parent organisms from the last generation.
     */
    private void breed(Organism[] parents) {
        int childIndex = 0;
        while (childIndex < NUMBER_OF_ORGANISMS) {
            // randomly select two organisms in the parent group, breeds them, and adds the child produced to the population
            mOrganisms[childIndex] = pickRandomOrganism(parents).breed(pickRandomOrganism(parents));
            mOrganisms[childIndex].setmGeneration(mGeneration);
            childIndex++;
        }
    }

    /**
     * Chooses a random {@link Organism} from an array of organisms.
     *
     * @param organisms The array to chose from.
     * @return The chosen organism.
     */
    private Organism pickRandomOrganism(Organism[] organisms) {
        int index = (int) (Math.random() * organisms.length);
        return organisms[index];
    }

    /**
     * Adds an organism into {@link #mElites} list. If the capacity of the list is exceeded, the organism with the
     * lowest fitness is removed.
     *
     * @param elite The organism to add to the elite list.
     */
    private void addElite(Organism elite) {
        mElites.add(elite);
        mElites = new ArrayList<>(sort(mElites));
        while (mElites.size() > MAX_NUMBER_OF_ELITES) {
            mElites.remove(mElites.size() - 1);
        }
    }

    /**
     * Writes the {@link Population} object to {@link #mSaveFile}.
     *
     * @throws IOException An error has occurred when writing to the file.
     */
    public void writeToFile() throws IOException {
        FileOutputStream fileOut = new FileOutputStream(mSaveFile);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(this);
        out.close();
        fileOut.close();
    }

    /**
     * @return The fittest organism in the {@link #mElites} list. If the list is empty, the first organism in
     * the population. If the first organism has not played a single game, <code>null</code>.
     */
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
        if (addTime >= 0) {
            mTrainTime += addTime;
        } else {
            throw new IllegalArgumentException("Negative number entered.");
        }
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
