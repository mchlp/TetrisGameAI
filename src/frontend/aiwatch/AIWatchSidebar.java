/*
 * Michael Pu
 * TetrisGameAI - AIWatchSidebar
 * ICS3U1 - Mr.Radulovic
 * December 28, 2017
 */

package frontend.aiwatch;

import ai.Watcher;
import backend.ControllerKeys;
import frontend.base.GameWindow;
import frontend.base.Sidebar;
import frontend.common.GameArea;
import frontend.common.OrganismStatusBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class AIWatchSidebar extends Sidebar {

    private OrganismStatusBox mOrganismStatusBox;
    private Watcher mWatcher;

    public AIWatchSidebar(GameWindow gameWindow, GameArea gameArea, Watcher watcher, double margins, double sideBarHeight, double sideBarWidth) {
        super(gameWindow, margins, sideBarHeight, sideBarWidth);

        mWatcher = watcher;

        AIWatchStatsBox aiWatchStatsBox = new AIWatchStatsBox(gameArea, mWatcher);
        getChildren().add(aiWatchStatsBox);
        mUpdateItems.add(0, aiWatchStatsBox);

        mOrganismStatusBox = new OrganismStatusBox("Current AI", mWatcher.getmCurOrganism());
        getChildren().add(mOrganismStatusBox);
        mUpdateItems.add(0, mOrganismStatusBox);

        addButtonBar();

        Button restartButton = new Button("Restart");
        mButtonBar.getChildren().add(restartButton);

        restartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent click) {
                mWatcher.getmGameController().keyPressed(ControllerKeys.RESTART);
            }
        });
    }
}
