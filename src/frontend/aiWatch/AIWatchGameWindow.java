/*
 * Michael Pu
 * TetrisGameAI - AIWatchGameWindow
 * ICS3U1 - Mr. Radulovic
 * December 25, 2017
 */

package frontend.aiWatch;

import ai.Organism;
import ai.Watcher;
import frontend.base.GameWindow;
import frontend.common.GameArea;
import frontend.common.GameController;
import frontend.common.GameMode;
import javafx.scene.control.TextArea;

import java.io.File;

public class AIWatchGameWindow extends GameWindow {

    private Watcher mWatcher;
    private Organism mOrganism;
    private GameArea mGameArea;

    public AIWatchGameWindow(double height, double width) {
        super(height, width, GameMode.AI_WATCHER);

        File orgFile = new File("/home/mpu/Desktop/b599f6f7-3250-47a4-9e26-4a8a20255d6a.org.ser");
        Organism mOrganism = Organism.loadOrganismFromFile(orgFile);

        mGameArea = new GameArea(mCanvasWidth, mCanvasHeight, GAME_BACKGROUND_COLOUR, mGameMode);
        mGamePane.getChildren().add(mGameArea);
        mGameController = new GameController(mGameArea);
        mUpdateItems.add(mGameArea);

        mWatcher = new Watcher(mGameArea.getmGameBrain(), mGameController, mOrganism);
        mUpdateItems.add(mWatcher);

        AIWatchSidebar aiWatchSidebar = new AIWatchSidebar(mGameArea, mWatcher, DEFAULT_MARGINS, mSideBarHeight, mSideBarWidth);
        setRight(aiWatchSidebar);
        mUpdateItems.add(aiWatchSidebar);
    }

}
