/*
 * Michael Pu
 * TetrisGameAI - AIWatchSidebar
 * ICS3U1 - Mr. Radulovic
 * December 26, 2017
 */

package frontend.aiWatch;

import ai.Watcher;
import backend.ControllerKeys;
import frontend.base.Sidebar;
import frontend.common.GameArea;
import frontend.common.OrganismStatusBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class AIWatchSidebar extends Sidebar{

    private OrganismStatusBox mOrganismStatusBox;
    private Watcher mWatcher;

    public AIWatchSidebar(GameArea gameArea, Watcher watcher, double margins, double sideBarHeight, double sideBarWidth) {
        super(margins, sideBarHeight, sideBarWidth);

        mWatcher = watcher;

        AIWatchStatsBox aiWatchStatsBox = new AIWatchStatsBox(gameArea, mWatcher);
        getChildren().add(aiWatchStatsBox);
        mUpdateItems.add(aiWatchStatsBox);

        mOrganismStatusBox = new OrganismStatusBox("Current AI", mWatcher.getmCurOrganism());
        getChildren().add(mOrganismStatusBox);
        mUpdateItems.add(mOrganismStatusBox);

        Button restartButton = new Button("Restart");
        getChildren().add(restartButton);

        restartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent click) {
                mWatcher.getmGameController().keyPressed(ControllerKeys.RESTART);
            }
        });
    }
}
