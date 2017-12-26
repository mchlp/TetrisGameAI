/*
 * Michael Pu
 * TetrisGameAI - AIWatchSidebar
 * ICS3U1 - Mr. Radulovic
 * December 25, 2017
 */

package frontend.aiWatch;

import ai.Watcher;
import backend.ControllerKeys;
import frontend.base.Sidebar;
import frontend.common.GameArea;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class AIWatchSidebar extends Sidebar{

    public AIWatchSidebar(GameArea gameArea, Watcher watcher, double margins, double sideBarHeight, double sideBarWidth) {
        super(margins, sideBarHeight, sideBarWidth);

        AIWatchStatsBox aiWatchStatsBox = new AIWatchStatsBox(gameArea, watcher);
        getChildren().add(aiWatchStatsBox);
        mUpdateItems.add(aiWatchStatsBox);

        Button restartButton = new Button("Restart");
        getChildren().add(restartButton);

        restartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent click) {
                watcher.getmGameController().keyPressed(ControllerKeys.RESTART);
            }
        });
    }
}
