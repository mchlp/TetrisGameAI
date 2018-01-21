/*
 * Michael Pu
 * TetrisGameAI - SinglePlayerTest
 * ICS3U1 - Mr.Radulovic
 * January 20, 2018
 */

package unittests.frontend;

import backend.ControllerKeys;
import backend.GameState;
import backend.Tetromino;
import frontend.common.GameGrid;
import frontend.player.PlayerGameWindow;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import org.junit.After;
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
    public void noAction_emptyStartingGrid() {
        assertThat(playerGameWindow.getmGameProcessor().getmGameState(), is(GameState.PLAYING));
        assertThat(playerGameWindow.getmGameProcessor().getmScore(), is(0));
        assertThat(playerGameWindow.getmGameProcessor().getmNumLinesCleared(), is(0));
        assertThat(playerGameWindow.getmGameProcessor().getmLevel(), is(1));
        GameGrid gameGrid = playerGameWindow.getmGameProcessor().getmGrid();
        for (int i = 0; i < gameGrid.getmWidth(); i++) {
            for (int j = 0; j < gameGrid.getmHeight(); j++) {
                assertThat(gameGrid.getCell(i, j).ismIsFilled(), is(false));
            }
        }
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

        playerGameWindow.getmGameController().keyPressed(ControllerKeys.DROP);
        WaitForAsyncUtils.waitForFxEvents();
        sleep(1000);

        assertThat(playerGameWindow.getmGameProcessor().getmScore(), not(0));
        clickOn("Restart");
        WaitForAsyncUtils.waitForFxEvents();
        noAction_emptyStartingGrid();
    }

    @Test
    public void moveLeft_NoError() {
        int startX = playerGameWindow.getmGameProcessor().getmCurTetromino().getmCurPos().x;
        for (int i = 0; i < startX + 3; i++) {
            push(KeyCode.LEFT);
            WaitForAsyncUtils.waitForFxEvents();
            if (i >= startX) {
                assertThat(playerGameWindow.getmGameProcessor().getmCurTetromino().getmCurPos().x, is(startX - startX));
            } else {
                assertThat(playerGameWindow.getmGameProcessor().getmCurTetromino().getmCurPos().x, is(startX - 1 - i));
            }
        }
    }

    @Test
    public void moveRight_NoError() {
        int startX = playerGameWindow.getmGameProcessor().getmCurTetromino().getmCurPos().x;
        int numMove = playerGameWindow.getmGameProcessor().getmGrid().getmWidth()
                - startX - playerGameWindow.getmGameProcessor().getmCurTetromino().getmBody()[0].length;
        for (int i = 0; i < numMove + 3; i++) {
            push(KeyCode.RIGHT);
            WaitForAsyncUtils.waitForFxEvents();
            if (i >= numMove) {
                assertThat(playerGameWindow.getmGameProcessor().getmCurTetromino().getmCurPos().x, is(startX + numMove));
            } else {
                assertThat(playerGameWindow.getmGameProcessor().getmCurTetromino().getmCurPos().x, is(startX + 1 + i));
            }
        }
    }

    @Test
    public void drop_NoError() {
        push(KeyCode.SPACE);
        WaitForAsyncUtils.waitForFxEvents();
        sleep(500);

        GameGrid gameGrid = playerGameWindow.getmGameProcessor().getmGrid();

        boolean filled = false;
        int bottom = gameGrid.getmHeight() - 1;
        for (int i = 0; i < gameGrid.getmWidth(); i++) {
            if (gameGrid.getCell(i, bottom).ismIsFilled()) {
                filled = true;
                break;
            }
        }
        assertThat(filled, is(true));
    }

    @Test
    public void rotate_NoError() {
        int[][] beforeBody;
        Color beforeColour;
        int numTetrominos = 7;
        for (int i = 0; i < numTetrominos; i++) {
            beforeBody = playerGameWindow.getmGameProcessor().getmCurTetromino().getmBody();
            beforeColour = playerGameWindow.getmGameProcessor().getmCurTetromino().getmColour();
            int numRotations = 4;
            for (int j = 0; j < numRotations; j++) {
                push(KeyCode.UP);
                WaitForAsyncUtils.waitForFxEvents();
                Tetromino curTetromino = playerGameWindow.getmGameProcessor().getmCurTetromino();
                assertThat(curTetromino.getmColour().toString(), is(beforeColour.toString()));
                assertThat(curTetromino.getmBody()[curTetromino.getmBody().length - 1][0], is(beforeBody[0][0]));
                beforeBody = curTetromino.getmBody();
            }
            playerGameWindow.getmGameController().keyPressed(ControllerKeys.DROP);
            WaitForAsyncUtils.waitForFxEvents();
            sleep(1000);
        }
    }

    @Test
    public void scoreIncreasing_NoError() {
        int score = 0;
        assertThat(playerGameWindow.getmGameProcessor().getmScore(), is(0));

        int numTimes = 6;
        for (int i = 0; i < numTimes; i++) {
            playerGameWindow.getmGameController().keyPressed(ControllerKeys.DROP);
            WaitForAsyncUtils.waitForFxEvents();
            sleep(1000);
            assertThat(playerGameWindow.getmGameProcessor().getmScore(), not(score));
            score = playerGameWindow.getmGameProcessor().getmScore();
        }
    }

    @Test
    public void checkGameOver_NoError() {
        int numTimes = 17;
        for (int i=0; i<numTimes; i++) {
            playerGameWindow.getmGameController().keyPressed(ControllerKeys.DROP);
            sleep(500);
        }
        assertThat(playerGameWindow.getmGameProcessor().getmGameState(), is(GameState.OVER));
    }

    @After
    @Override
    public void afterEachTest() throws TimeoutException {
        clickOn("Main Menu");
        super.afterEachTest();
    }
}
