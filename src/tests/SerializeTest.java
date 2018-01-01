/*
 * Michael Pu
 * TetrisGameAI - SerializeTest
 * ICS3U1 - Mr. Radulovic
 * December 28, 2017
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
        Population readPop = null;
        try {
            readPop = Population.loadPopulationFromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(readPop.getOrganism(0).getmName());
    }
}
