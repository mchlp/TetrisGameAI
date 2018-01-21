/*
 * Michael Pu
 * TetrisGameAI - AIWatchSidebar
 * ICS3U1 - Mr.Radulovic
 * January 20, 2018
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

/**
 * Sidebar for watching AI play mode.
 */
public class AIWatchSidebar extends Sidebar {

    private OrganismStatusBox mOrganismStatusBox;
    private Watcher mWatcher;
    private boolean mMuted;

    public AIWatchSidebar(GameWindow gameWindow, GameArea gameArea, Watcher watcher, double margins, double sideBarHeight, double sideBarWidth) {
        super(gameWindow, margins, sideBarHeight, sideBarWidth);

        mWatcher = watcher;

        // statsBox
        AIWatchStatsBox aiWatchStatsBox = new AIWatchStatsBox(gameArea, mWatcher);
        getChildren().add(aiWatchStatsBox);
        mUpdateItems.add(0, aiWatchStatsBox);

        // organism details
        mOrganismStatusBox = new OrganismStatusBox("Current AI", mWatcher.getmCurOrganism());
        getChildren().add(mOrganismStatusBox);
        mUpdateItems.add(0, mOrganismStatusBox);

        // add button bar
        addButtonBar();

        // add buttons to button bar
        Button restartButton = new Button("Restart");
        mButtonBar.getChildren().add(restartButton);

        Button muteButton = new Button("Mute");
        mButtonBar.getChildren().add(muteButton);
        mMuted = false;

        // set actions for button
        restartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent click) {
                mWatcher.getmGameController().keyPressed(ControllerKeys.RESTART);
            }
        });

        muteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (mMuted) {
                    mMuted = false;
                    muteButton.setText("Mute");
                } else {
                    mMuted = true;
                    muteButton.setText("Unmute");
                }
                mGameWindow.getmBackgroundMusic().setMute(mMuted);
            }
        });
    }
}
