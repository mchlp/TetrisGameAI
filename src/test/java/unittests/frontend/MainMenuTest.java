/*
 * Michael Pu
 * TetrisGameAI - MainMenuTest
 * ICS3U1 - Mr. Radulovic
 * January 03, 2018
 */

package unittests.frontend;

import com.sun.javafx.robot.impl.FXRobotHelper;
import com.sun.javafx.stage.StageHelper;
import javafx.scene.input.KeyCode;
import javafx.stage.Window;
import org.junit.Test;
import org.testfx.api.*;
import org.testfx.util.WaitForAsyncUtils;
import java.util.concurrent.TimeoutException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testfx.api.FxAssert.verifyThat;

public class MainMenuTest extends FrontendTestBase {

    @Test
    public void clickOnNonExistentElement_NoError() {
        exception.expect(FxRobotException.class);
        exception.expectMessage("the query \"NonExistent\" returned no nodes.");
        clickOn("NonExistent");
    }

    @Test
    public void clickOnSinglePlayer_NoError() throws TimeoutException {
        clickOn("Single Player");
        WaitForAsyncUtils.waitForFxEvents();
        assertThat(getTopMostWindow().getTitle(), is("Tetris Game - Human Player Version"));
    }

    @Test
    public void clickOnPlayAgainstAI_NoError() throws TimeoutException {
        clickOn("Play Against AI");
        WaitForAsyncUtils.waitForFxEvents();
        assertThat(getTopMostWindow().getTitle(), is("Select an Organism"));
    }

    @Test
    public void clickOnWatchAI_NoError() throws TimeoutException {
        clickOn("Watch AI");
        WaitForAsyncUtils.waitForFxEvents();
        assertThat(getTopMostWindow().getTitle(), is("Select an Organism"));
    }

    @Test
    public void clickOnExitGame_NoError() throws TimeoutException {
        clickOn("Exit Game");
    }

    @Test
    public void clickOnTrainAI_NoAdvancedMode_ButtonNotFound() {
        exception.expect(FxRobotException.class);
        exception.expectMessage("the query \"Train AI\" returned 1 nodes, but no nodes were visible.");
        clickOn("Train AI");
    }

    @Test
    public void clickOnTrainAI_AdvancedMode_NoError() throws TimeoutException {
        push(KeyCode.A);
        clickOn("Train AI");
        WaitForAsyncUtils.waitForFxEvents();
        assertThat(getTopMostWindow().getTitle(), is("Select a Population"));
    }
}
