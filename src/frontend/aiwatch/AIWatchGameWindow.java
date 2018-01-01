/*
 * Michael Pu
 * TetrisGameAI - AIWatchGameWindow
 * ICS3U1 - Mr. Radulovic
 * December 30, 2017
 */

package frontend.aiwatch;

import ai.Organism;
import ai.Watcher;
import backend.GameMode;
import frontend.base.TwoPanelGameWindow;
import frontend.common.GameArea;
import frontend.common.GameController;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class AIWatchGameWindow extends TwoPanelGameWindow {

    private Watcher mWatcher;
    private Organism mOrganism;
    private GameArea mGameArea;

    public AIWatchGameWindow(Stage stage, Organism organism, double height, double width, boolean fastMode) {
        super(stage, height, width, GameMode.AI_WATCHER);

        mWindowTitle = "Tetris Game - AI Watch Version";
        mOrganism = organism;

        Rectangle gameAreaBackground = new Rectangle(mCanvasWidth + 10, mCanvasHeight + 10, GAME_OUTLINE_COLOUR);
        mGamePane.getChildren().add(gameAreaBackground);

        mGameArea = new GameArea(mBackgroundMusic, mCanvasWidth, mCanvasHeight, GAME_BACKGROUND_COLOUR, mGameMode);
        mGamePane.getChildren().add(mGameArea);
        mGameController = new GameController(mGameArea);
        mUpdateItems.add(mGameArea);

        mWatcher = new Watcher(mGameArea.getmGameProcessor(), mGameController, mOrganism, fastMode);
        mUpdateItems.add(mWatcher);

        AIWatchSidebar aiWatchSidebar = new AIWatchSidebar(this, mGameArea, mWatcher, DEFAULT_MARGINS, mSideBarHeight, mSideBarWidth);
        setRight(aiWatchSidebar);
        mUpdateItems.add(aiWatchSidebar);
    }

}
