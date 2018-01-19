/*
 * Michael Pu
 * TetrisGameAI - PopulationTest
 * ICS3U1 - Mr.Radulovic
 * January 13, 2018
 */

package unittests.backend;

import ai.Organism;
import ai.Population;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class PopulationTest {

    private static File saveFile;
    private static File readOnlyFile;

    private static final String LOAD_POPULATION_FROM_FILE_NO_ERROR = "res/Test_loadPopulationFromFile_NoError.pop.ser";
    private static final String LOAD_POPULATION_FROM_FILE_OUTDATED = "res/Test_loadPopulationFromFile_Outdated.pop.ser";
    private static final String LOAD_POPULATION_FROM_FILE_INVALID = "res/Test_loadPopulationFromFile_InvalidFile.pop.ser";

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void initialize() throws IOException {
        readOnlyFile = new File("Test_readOnlyPopulation.pop.ser");
        assertThat(readOnlyFile.createNewFile(), is(true));
        Set<PosixFilePermission> permissionSet = new HashSet<>();
        permissionSet.add(PosixFilePermission.OWNER_READ);
        permissionSet.add(PosixFilePermission.GROUP_READ);
        permissionSet.add(PosixFilePermission.OTHERS_READ);
        Files.setPosixFilePermissions(readOnlyFile.toPath(), permissionSet);
        saveFile = new File("Test_population.pop.ser");
    }

    @Test
    public void loadPopulationFromFile_NonExistentFile_FileNotFoundException() throws IOException, ClassNotFoundException {
        exception.expect(FileNotFoundException.class);
        File nonExistentFile = new File("fileDoesNotExist.pop.ser");
        Population.loadPopulationFromFile(nonExistentFile);
    }

    @Test
    public void loadPopulationFromFile_InvalidFile_StreamCorruptedException() throws IOException, ClassNotFoundException {
        exception.expect(StreamCorruptedException.class);
        File invalidFile = new File(LOAD_POPULATION_FROM_FILE_INVALID);
        Population.loadPopulationFromFile(invalidFile);
    }

    @Test
    public void loadPopulationFromFile_OutdatedFile_InvalidClassException() throws IOException, ClassNotFoundException {
        exception.expect(InvalidClassException.class);
        exception.expectMessage("local class incompatible");
        File outdatedFile = new File(LOAD_POPULATION_FROM_FILE_OUTDATED);
        Population.loadPopulationFromFile(outdatedFile);
    }

    @Test
    public void loadPopulationFromFile_NoError() throws IOException, ClassNotFoundException {

        File loadFile = new File(LOAD_POPULATION_FROM_FILE_NO_ERROR);
        Population loadedPopulation = Population.loadPopulationFromFile(loadFile);

        assertThat(loadedPopulation.getNumOrganisms(), is(50));
        assertThat(loadedPopulation.getElite(), instanceOf(Organism.class));
        for (int i = 0; i < 50; i++) {
            assertThat(loadedPopulation.getOrganism(i), notNullValue());
            assertThat(loadedPopulation.getOrganism(0), instanceOf(Organism.class));
        }
        assertThat(loadedPopulation.getmTrainTime(), is(8.339000000000743));
        assertThat(loadedPopulation.getmGeneration(), is(2));
        assertThat(loadedPopulation.getmAvgFitness(), is(9270));
        assertThat(loadedPopulation.getmBottom25PerFitness(), is(228));
        assertThat(loadedPopulation.getmEliteFitness(), is(398516));
        assertThat(loadedPopulation.getmMinFitness(), is(180));
        assertThat(loadedPopulation.getmTop25PerFitness(), is(37650));
        assertThat(loadedPopulation.getmTopFitness(), is(398516));
        assertThat(loadedPopulation.getmTotalFitness(), is(463541));
    }

    @Test
    public void addPopulationTrainTime_NoError() {
        Population population = new Population(saveFile);
        assertThat(population.getmTrainTime(), is(0.0));
        population.addmTrainTime(0);
        assertThat(population.getmTrainTime(), is(0.0));
        population.addmTrainTime(10);
        assertThat(population.getmTrainTime(), is(10.0));
        population.addmTrainTime(34.1231344532);
        assertThat(population.getmTrainTime(), is(44.1231344532));
    }

    @Test
    public void addPopulationTrainTime_NegativeNumber_IllegalArgumentException() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Negative number entered.");

        Population population = new Population(saveFile);
        population.addmTrainTime(-1);
        assertThat(population.getmTrainTime(), is(0));
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
        assertThat(organism1, is(survivors[0]));
        assertThat(organism2, is(survivors[1]));
        assertThat(organism3, is(survivors[2]));

        assertThat(0, is(survivors[3].getTotalScore()));
        assertThat(survivors[3].getmMaxScore(), is(0));
        assertThat(survivors[3].getmMaxLevel(), is(0));
        assertThat(survivors[3].getmMaxLinesCleared(), is(0));
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

        for (int i = 0; i < 50; i++) {
            assertThat(population.getOrganism(i), notNullValue());
            assertThat(population.getOrganism(i), instanceOf(Organism.class));
            assertThat(population.getOrganism(i).getmGeneration(), is(2));
        }
    }

    @Test
    public void getElite_NoError() throws IOException {
        Population population = new Population(saveFile);
        population.evolve();
        Organism elite = population.getElite();
        assertThat(elite, instanceOf(Organism.class));
    }

    @Test
    public void getElite_EmptyList_NullObject() {
        Population population = new Population(saveFile);
        Organism elite = population.getElite();
        assertThat(elite, nullValue());
    }

    @Test
    public void writeToFile_ReadOnlyFile_IOException() throws IOException {
        exception.expect(FileNotFoundException.class);
        exception.expectMessage("Test_readOnlyPopulation.pop.ser (Permission denied)");
        Population population = new Population(readOnlyFile);
        population.writeToFile();
    }

    @Test
    public void writeToFile_NoError() throws IOException {
        Population population = new Population(saveFile);
        population.writeToFile();
    }

    @AfterClass
    public static void cleanUp() {
        saveFile.delete();
        readOnlyFile.delete();
    }

}