/*
 * Michael Pu
 * TetrisGameAI - SinglePlayerTest
 * ICS3U1 - Mr. Radulovic
 * January 03, 2018
 */

package unittests.frontend;

import backend.GameState;
import frontend.player.PlayerGameWindow;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.util.WaitForAsyncUtils;

import java.util.concurrent.TimeoutException;

import static org.hamcrest.CoreMatchers.is;
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
        assertThat(playerGameWindow.getmGameProcessor().getmGameState(), is(GameState.PAUSED));
        clickOn("Unpause");
        assertThat(playerGameWindow.getmGameProcessor().getmGameState(), is(GameState.PLAYING));
    }

    @Test
    public void gridlinesButton_NoError() throws TimeoutException {
        assertThat(playerGameWindow.getmGameArea().ismShowGridlines(), is(true));
        clickOn("Toggle Gridlines");
        assertThat(playerGameWindow.getmGameArea().ismShowGridlines(), is(false));
        clickOn("Toggle Gridlines");
        assertThat(playerGameWindow.getmGameArea().ismShowGridlines(), is(true));
    }

}
