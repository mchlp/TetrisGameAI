/*
 * Michael Pu
 * TetrisGameAI - SerializeTest
 * ICS3U1 - Mr. Radulovic
 * December 25, 2017
 */

package tests;

import ai.Population;

import java.io.File;
import java.io.IOException;

public class SerializeTest {

    public static void main(String[] args) {
        File file = new File("test.ser");
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Population savePop = new Population(file);
        savePop.saveToFile();
        Population readPop = Population.loadPopulationFromFile(file);
        System.out.println(readPop.getOrganism(0).getmName());
    }
}
