/*
 * Michael Pu
 * TetrisGameAI - PopulationTest
 * ICS3U1 - Mr. Radulovic
 * January 01, 2018
 */

import ai.Organism;
import ai.Population;
import org.junit.*;

import java.io.File;
import java.io.IOException;

public class PopulationTest {

    Population testPopulation;
    File saveFile;

    @Before
    public void initializePopulation() {
        saveFile = new File("Test_population.pop.ser");
        testPopulation = new Population(saveFile);
    }

    @Test
    public void loadPopulationFromFile_NoError() throws IOException, ClassNotFoundException {

        File loadFile = new File("res/Test_loadPopulationFromFile_NoError.pop.ser");
        Population loadedPopulation = Population.loadPopulationFromFile(loadFile);

        Assert.assertEquals(loadedPopulation.getNumOrganisms(), 50);
        Assert.assertEquals(loadedPopulation.getElite().getClass(), Organism.class);
    }

    @Test
    public void writeToFile_NoError() throws IOException {
        testPopulation.writeToFile();
    }

    @After
    public void cleanUp() {
        saveFile.delete();
    }

}