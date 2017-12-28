/*
 * Michael Pu
 * TetrisGameAI - AIWatchGameWindow
 * ICS3U1 - Mr. Radulovic
 * December 26, 2017
 */

package frontend.aiWatch;

import ai.Organism;
import ai.Watcher;
import frontend.base.GameWindow;
import frontend.common.GameArea;
import frontend.common.GameController;
import backend.GameMode;

import java.io.File;

public class AIWatchGameWindow extends GameWindow {

    private Watcher mWatcher;
    private Organism mOrganism;
    private GameArea mGameArea;

    public AIWatchGameWindow(Organism organism, double height, double width) {
        super(height, width, GameMode.AI_WATCHER);

        mWindowTitle = "Tetris Game - AI Watch Version";
        mOrganism = organism;

        mGameArea = new GameArea(mBackgroundMusic, mCanvasWidth, mCanvasHeight, GAME_BACKGROUND_COLOUR, mGameMode);
        mGamePane.getChildren().add(mGameArea);
        mGameController = new GameController(mGameArea);
        mUpdateItems.add(mGameArea);

        mWatcher = new Watcher(mGameArea.getmGameBrain(), mGameController, mOrganism);
        mUpdateItems.add(mWatcher);

        AIWatchSidebar aiWatchSidebar = new AIWatchSidebar(this, mGameArea, mWatcher, DEFAULT_MARGINS, mSideBarHeight, mSideBarWidth);
        setRight(aiWatchSidebar);
        mUpdateItems.add(aiWatchSidebar);
    }

}
