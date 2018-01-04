/*
 * Michael Pu
 * TetrisGameAI - SinglePlayerTest
 * ICS3U1 - Mr. Radulovic
 * January 03, 2018
 */

package unittests.frontend;

import backend.ControllerKeys;
import backend.GameState;
import frontend.common.GameGrid;
import frontend.player.PlayerGameWindow;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.util.WaitForAsyncUtils;

import java.util.concurrent.TimeoutException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class SinglePlayerTest extends FrontendTestBase {

    private PlayerGameWindow playerGameWindow;

    @Override
    @Before
    public void beforeEachTest() throws Exception {
        super.beforeEachTest();
        clickOn("Single Player");
        WaitForAsyncUtils.waitForFxEvents();
        playerGameWindow = (PlayerGameWindow) FxToolkit.registerPrimaryStage().getScene().getRoot();
    }

    @Test
    public void pauseButton_NoError() throws TimeoutException {
        assertThat(playerGameWindow.getmGameProcessor().getmGameState(), is(GameState.PLAYING));
        clickOn("Pause");
        WaitForAsyncUtils.waitForFxEvents();
        assertThat(playerGameWindow.getmGameProcessor().getmGameState(), is(GameState.PAUSED));
        clickOn("Unpause");
        WaitForAsyncUtils.waitForFxEvents();
        assertThat(playerGameWindow.getmGameProcessor().getmGameState(), is(GameState.PLAYING));
    }

    @Test
    public void gridlinesButton_NoError() throws TimeoutException {
        assertThat(playerGameWindow.getmGameArea().ismShowGridlines(), is(true));
        clickOn("Toggle Gridlines");
        WaitForAsyncUtils.waitForFxEvents();
        assertThat(playerGameWindow.getmGameArea().ismShowGridlines(), is(false));
        clickOn("Toggle Gridlines");
        WaitForAsyncUtils.waitForFxEvents();
        assertThat(playerGameWindow.getmGameArea().ismShowGridlines(), is(true));
    }

    @Test
    public void restartButton_NoError() {

        int dropTimes = 4;

        while (dropTimes-- > 0) {
            playerGameWindow.getmGameController().keyPressed(ControllerKeys.DROP);
            WaitForAsyncUtils.waitForFxEvents();
            sleep(500);
        }

        assertThat(playerGameWindow.getmGameProcessor().getmScore(), not(0));
        clickOn("Restart");
        WaitForAsyncUtils.waitForFxEvents();
        assertThat(playerGameWindow.getmGameProcessor().getmScore(), is(0));
        GameGrid gameGrid = playerGameWindow.getmGameProcessor().getmGrid();
        for (int i=0; i<gameGrid.getmWidth(); i++) {
            for (int j=0; j<gameGrid.getmHeight(); j++) {
                assertThat(gameGrid.getCell(i, j).ismIsFilled(), is(false));
            }
        }
    }

    @Test
    public void moveLeft_NoError() {
        int leftTimes = 6;
        while (leftTimes-- > 0) {
            playerGameWindow.getmGameController().keyPressed(ControllerKeys.LEFT);
        }
        playerGameWindow.getmGameController().keyPressed(ControllerKeys.DROP);
        WaitForAsyncUtils.waitForFxEvents();
        sleep(1000);

        GameGrid gameGrid = playerGameWindow.getmGameProcessor().getmGrid();

        boolean filled = false;
        for (int i=0; i<gameGrid.getmHeight(); i++) {
            if (gameGrid.getCell(0, i).ismIsFilled()) {
                filled = true;
                break;
            }
        }
        assertThat(filled, is(true));
    }

    @Test
    public void drop_NoError() {
        playerGameWindow.getmGameController().keyPressed(ControllerKeys.DROP);
        WaitForAsyncUtils.waitForFxEvents();
        sleep(1000);

        GameGrid gameGrid = playerGameWindow.getmGameProcessor().getmGrid();

        boolean filled = false;
        int bottom = gameGrid.getmHeight()-1;
        for (int i=0; i<gameGrid.getmWidth(); i++) {
            if (gameGrid.getCell(i, bottom).ismIsFilled()) {
                filled = true;
                break;
            }
        }
        assertThat(filled, is(true));
    }

    @Test
    public void scoreIncreasing_NoError() {
        int score = 0;
        assertThat(playerGameWindow.getmGameProcessor().getmScore(), is(0));

        int numTimes = 4;
        while (numTimes-- > 0) {
            playerGameWindow.getmGameController().keyPressed(ControllerKeys.DROP);
            WaitForAsyncUtils.waitForFxEvents();
            sleep(1000);
            assertThat(playerGameWindow.getmGameProcessor().getmScore(), not(score));
            score = playerGameWindow.getmGameProcessor().getmScore();
        }
    }

}
