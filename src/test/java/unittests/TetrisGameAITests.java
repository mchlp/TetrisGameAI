/*
 * Michael Pu
 * TetrisGameAI - TetrisGameAITests
 * ICS3U1 - Mr. Radulovic
 * January 02, 2018
 */

package unittests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        PopulationTest.class,
        OrganismTest.class
})
public class TetrisGameAITests {

    public static final double DELTA = 1E-15;

}
