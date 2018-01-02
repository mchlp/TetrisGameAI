/*
 * Michael Pu
 * TetrisGameAI - PopulationTest
 * ICS3U1 - Mr. Radulovic
 * January 02, 2018
 */

package unittests;

import static unittests.TetrisGameAITests.DELTA;
import ai.Organism;
import ai.Population;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class PopulationTest {

    File saveFile;

    @Before
    public void initializePopulation() {
        saveFile = new File("Test_population.pop.ser");
    }

    @Test
    public void loadPopulationFromFile_NoError() throws IOException, ClassNotFoundException {

        File loadFile = new File("res/Test_loadPopulationFromFile_NoError.pop.ser");
        Population loadedPopulation = Population.loadPopulationFromFile(loadFile);

        Assert.assertEquals(50, loadedPopulation.getNumOrganisms());
        Assert.assertEquals(Organism.class, loadedPopulation.getElite().getClass());
        for (int i=0; i<50; i++) {
            Assert.assertNotNull(loadedPopulation.getOrganism(i));
            Assert.assertEquals(loadedPopulation.getOrganism(0).getClass(), Organism.class);
        }
        Assert.assertEquals(33.759000000010566, loadedPopulation.getmTrainTime(), DELTA);
        Assert.assertEquals(3, loadedPopulation.getmGeneration());
        Assert.assertEquals(33509, loadedPopulation.getmAvgFitness());
        Assert.assertEquals(519, loadedPopulation.getmBottom25PerFitness());
        Assert.assertEquals(362076, loadedPopulation.getmEliteFitness());
        Assert.assertEquals(337, loadedPopulation.getmMinFitness());
        Assert.assertEquals(128044, loadedPopulation.getmTop25PerFitness());
        Assert.assertEquals(362076, loadedPopulation.getmTopFitness());
        Assert.assertEquals(1675470, loadedPopulation.getmTotalFitness());
    }

    @Test
    public void addPopulationTrainTime_NoError() {
        Population population = new Population(saveFile);
        Assert.assertEquals(0, population.getmTrainTime(), DELTA);
        population.addmTrainTime(10);
    }

    //"Negative number entered."

    @Test
    public void writeToFile_NoError() throws IOException {
        Population population = new Population(saveFile);
        population.writeToFile();
    }

    @After
    public void cleanUp() {
        saveFile.delete();
    }

}