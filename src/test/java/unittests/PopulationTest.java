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
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.IOException;

public class PopulationTest {

    private File saveFile;

    private static final String LOAD_POPULATION_FROM_FILE_TESTFILE = "res/Test_loadPopulationFromFile_NoError.pop.ser";

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void initializePopulation() throws IOException {
        saveFile = new File("Test_population.pop.ser");
    }

    @Test
    public void loadPopulationFromFile_NoError() throws IOException, ClassNotFoundException {

        File loadFile = new File(LOAD_POPULATION_FROM_FILE_TESTFILE);
        Population loadedPopulation = Population.loadPopulationFromFile(loadFile);

        Assert.assertEquals(50, loadedPopulation.getNumOrganisms());
        Assert.assertEquals(Organism.class, loadedPopulation.getElite().getClass());
        for (int i=0; i<50; i++) {
            Assert.assertNotNull(loadedPopulation.getOrganism(i));
            Assert.assertEquals(Organism.class, loadedPopulation.getOrganism(0).getClass());
        }
        Assert.assertEquals(8.339000000000743, loadedPopulation.getmTrainTime(), DELTA);
        Assert.assertEquals(2, loadedPopulation.getmGeneration());
        Assert.assertEquals(9270, loadedPopulation.getmAvgFitness());
        Assert.assertEquals(228, loadedPopulation.getmBottom25PerFitness());
        Assert.assertEquals(398516, loadedPopulation.getmEliteFitness());
        Assert.assertEquals(180, loadedPopulation.getmMinFitness());
        Assert.assertEquals(37650, loadedPopulation.getmTop25PerFitness());
        Assert.assertEquals(398516, loadedPopulation.getmTopFitness());
        Assert.assertEquals(463541, loadedPopulation.getmTotalFitness());
    }

    @Test
    public void addPopulationTrainTime_NoError() {
        Population population = new Population(saveFile);
        Assert.assertEquals(0, population.getmTrainTime(), DELTA);
        population.addmTrainTime(0);
        Assert.assertEquals(0, population.getmTrainTime(), DELTA);
        population.addmTrainTime(10);
        Assert.assertEquals(10.0, population.getmTrainTime(), DELTA);
        population.addmTrainTime(34.1231344532);
        Assert.assertEquals(44.1231344532, population.getmTrainTime(), DELTA);
    }

    @Test
    public void addPopulationTrainTime_NegativeNumber() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Negative number entered.");

        Population population = new Population(saveFile);
        population.addmTrainTime(-1);
        Assert.assertEquals(0, population.getmTrainTime(), DELTA);
    }

    @Test
    public void selectAndKill_NoError() {
        Population population = new Population(saveFile);

        Organism organism1 = population.getOrganism(1);
        organism1.addScore(1254);
        organism1.addScore(5544);
        organism1.setmMaxLevel(10);
        organism1.setmMaxLinesCleared(124);
        organism1.setmMaxScore(1254);

        Organism organism2 = population.getOrganism(2);
        organism2.addScore(1225);
        organism2.addScore(576);
        organism2.setmMaxLevel(8);
        organism2.setmMaxLinesCleared(8);
        organism2.setmMaxScore(1225);

        Organism organism3 = population.getOrganism(3);
        organism3.addScore(5);
        organism3.addScore(0);
        organism3.setmMaxLevel(1);
        organism3.setmMaxLinesCleared(2);
        organism3.setmMaxScore(1);

        Organism[] survivors = population.selectAndKill();
        Assert.assertEquals(organism1, survivors[0]);
        Assert.assertEquals(organism2, survivors[1]);
        Assert.assertEquals(organism3, survivors[2]);

        Assert.assertEquals(0, survivors[3].getTotalScore());
        Assert.assertEquals(0, survivors[3].getmMaxScore());
        Assert.assertEquals(0, survivors[3].getmMaxLevel());
        Assert.assertEquals(0, survivors[3].getmMaxLinesCleared());
    }

    @Test
    public void evolve_NoError() throws IOException {
        Population population = new Population(saveFile);

        Organism organism1 = population.getOrganism(1);
        organism1.addScore(1254);
        organism1.addScore(5544);
        organism1.setmMaxLevel(10);
        organism1.setmMaxLinesCleared(124);
        organism1.setmMaxScore(1254);

        Organism organism2 = population.getOrganism(2);
        organism2.addScore(1225);
        organism2.addScore(576);
        organism2.setmMaxLevel(8);
        organism2.setmMaxLinesCleared(8);
        organism2.setmMaxScore(1225);

        Organism organism3 = population.getOrganism(3);
        organism3.addScore(5);
        organism3.addScore(0);
        organism3.setmMaxLevel(1);
        organism3.setmMaxLinesCleared(2);
        organism3.setmMaxScore(1);

        population.evolve();

        for (int i=0; i<50; i++) {
            Assert.assertNotNull(population.getOrganism(i));
            Assert.assertEquals(Organism.class, population.getOrganism(i).getClass());
            Assert.assertEquals(2, population.getOrganism(i).getmGeneration());
        }
    }

    @Test
    public void getElite_NoError() throws IOException {
        Population population = new Population(saveFile);
        population.evolve();
        Organism elite = population.getElite();
        Assert.assertEquals(elite.getClass(), Organism.class);
    }

    @Test
    public void getElite_EmptyList() {
        Population population = new Population(saveFile);
        Organism elite = population.getElite();
        Assert.assertNull(elite);
    }

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