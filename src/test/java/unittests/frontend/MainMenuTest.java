/*
 * Michael Pu
 * TetrisGameAI - MainMenuTest
 * ICS3U1 - Mr. Radulovic
 * January 03, 2018
 */

package unittests.frontend;

import org.junit.Test;

public class MainMenuTest extends GameTestBase {

    @Test
    public void clickOnBogusElement() {
        clickOn("#asdf");
    }

}
