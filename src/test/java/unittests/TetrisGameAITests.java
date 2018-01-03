/*
 * Michael Pu
 * TetrisGameAI - TetrisGameAITests
 * ICS3U1 - Mr. Radulovic
 * January 02, 2018
 */

package unittests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import unittests.backend.BackendTests;
import unittests.frontend.FrontendTests;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BackendTests.class,
        FrontendTests.class
})
public class TetrisGameAITests {
}
