/*
 * Michael Pu
 * TetrisGameAI - FrontendTestBase
 * ICS3U1 - Mr.Radulovic
 * January 13, 2018
 */

package unittests.frontend;

import com.sun.javafx.robot.impl.FXRobotHelper;
import frontend.Game;
import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import java.util.concurrent.TimeoutException;

public abstract class FrontendTestBase extends ApplicationTest {

    protected Game game;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void beforeEachTest() throws Exception {
        game = (Game) (FxToolkit.setupApplication(Game.class));
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.show();
    }

    @After
    public void afterEachTest() throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    protected Stage getTopMostWindow() {
        return (FXRobotHelper.getStages().get(FXRobotHelper.getStages().size()-1));
    }
}
