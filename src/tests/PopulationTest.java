/*
 * Michael Pu
 * TetrisGameAI - PopulationTest
 * ICS3U1 - Mr. Radulovic
 * January 01, 2018
 */

package tests;

import ai.Population;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class PopulationTest {

    Population testPopulation;

    @Before
    public void initializePopulation() {
        File saveFile = new File("populationTestSaveFile.pop.ser");
        testPopulation = new Population(saveFile);
    }

    @Test
    public void loadPopulationFromFile_Successful() throws IOException {
        testPopulation.writeToFile();
    }

    @Test
    public void writeToFile() {
    }

}