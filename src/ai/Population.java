/*
 * Michael Pu
 * TetrisGameAI - Population
 * ICS3U1 - Mr. Radulovic
 * December 25, 2017
 */

package ai;

import java.io.*;

public class Population implements Serializable {

    private static final int MAX_NUMBER_OF_ORGANISMS = 50;

    private Organism[] mOrganisms;
    private transient File mSaveFile;

    public Population(File saveFile) {

        mSaveFile = saveFile;
        mOrganisms = new Organism[MAX_NUMBER_OF_ORGANISMS];

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

    public void selectAndKill() {

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

    public Organism getOrganism(int index) {
        return mOrganisms[index];
    }

    public int getNumOrganisms() {
        return mOrganisms.length;
    }
}
