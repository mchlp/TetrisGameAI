/*
 * Michael Pu
 * TetrisGameAI - AIPlayTrainSidebar
 * ICS3U1 - Mr. Radulovic
 * December 29, 2017
 */

package frontend.aiplay;

import backend.ControllerKeys;
import backend.GameMode;
import frontend.base.GameWindow;
import frontend.base.Sidebar;
import frontend.common.DoubleStatsBox;
import frontend.common.GameArea;
import frontend.common.GameController;
import frontend.player.NextTetrominoBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class AIPlaySidebar extends Sidebar {

    private boolean mPaused;
    private boolean mMuted;

    public AIPlaySidebar(GameWindow gameWindow, GameArea aiGameArea, GameArea playerGameArea, GameController aiGameController, GameController playerGameController, double margins, double sideBarHeight, double sideBarWidth, Color gameBgColour) {
        super(gameWindow, margins, sideBarHeight, sideBarWidth);

        NextTetrominoBox nextTetrominoBox = new NextTetrominoBox(playerGameArea, sideBarWidth / 2, gameBgColour);
        getChildren().add(nextTetrominoBox);
        mUpdateItems.add(nextTetrominoBox);

        DoubleStatsBox playerStatsBox = new DoubleStatsBox(GameMode.AI_PLAY, aiGameArea, playerGameArea, "AI Stats (Left)", "Player Stats (Right)");
        getChildren().add(playerStatsBox);
        mUpdateItems.add(playerStatsBox);

        addButtonBar();

        Button restartButton = new Button("Restart");
        mButtonBar.getChildren().add(restartButton);

        Button pauseButton = new Button("Pause");
        mButtonBar.getChildren().add(pauseButton);
        mPaused = false;

        Button gridlinesButton = new Button("Toggle Gridlines");
        mButtonBar.getChildren().add(gridlinesButton);

        Button muteButton = new Button("Mute");
        mButtonBar.getChildren().add(muteButton);
        mMuted = false;

        restartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent click) {
                aiGameController.keyPressed(ControllerKeys.RESTART);
                playerGameController.keyPressed(ControllerKeys.RESTART);
            }
        });

        pauseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent click) {
                if (mPaused) {
                    mPaused = false;
                    pauseButton.setText("Pause");
                } else {
                    mPaused = true;
                    pauseButton.setText("Unpause");
                }
                aiGameController.keyPressed(ControllerKeys.TOGGLE_PAUSE);
                playerGameController.keyPressed(ControllerKeys.TOGGLE_PAUSE);
            }
        });

        gridlinesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent click) {
                aiGameController.keyPressed(ControllerKeys.TOGGLE_GRIDLINES);
                playerGameController.keyPressed(ControllerKeys.TOGGLE_GRIDLINES);
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
