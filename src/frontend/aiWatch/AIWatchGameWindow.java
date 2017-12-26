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
import frontend.common.GameMode;
import javafx.scene.control.TextArea;

import java.io.File;

public class AIWatchGameWindow extends GameWindow {

    private Watcher mWatcher;
    private Organism mOrganism;

    public AIWatchGameWindow(double height, double width) {
        super(height, width, GameMode.AI_WATCHER);

        File orgFile = new File("/home/mpu/Desktop/4ca7754d-346d-4b1c-be70-e6b5deaf9c2b.org.ser");
        Organism mOrganism = Organism.loadOrganismFromFile(orgFile);

        mWatcher = new Watcher(mGameArea, mGameController, mOrganism);
        mUpdateItems.add(mWatcher);

        TextArea outputConsole = new TextArea();
        mGamePane.getChildren().add(outputConsole);

        AIWatchSidebar aiWatchSidebar = new AIWatchSidebar(mGameArea, mWatcher, DEFAULT_MARGINS, mSideBarHeight, mSideBarWidth);
        setRight(aiWatchSidebar);
        mUpdateItems.add(aiWatchSidebar);
    }

}
