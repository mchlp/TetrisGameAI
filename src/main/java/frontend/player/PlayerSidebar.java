/*
 * Michael Pu
 * TetrisGameAI - PlayerSidebar
 * ICS3U1 - Mr.Radulovic
 * January 20, 2018
 */

package frontend.player;

import backend.ControllerKeys;
import frontend.base.GameWindow;
import frontend.base.Sidebar;
import frontend.common.GameArea;
import frontend.common.GameController;
import frontend.common.NextTetrominoBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

/**
 * Sidebar for single player mode.
 */
public class PlayerSidebar extends Sidebar {

    private boolean mMuted;
    private boolean mPaused;

    public PlayerSidebar(GameWindow gameWindow, GameArea gameArea, GameController gameController, double margins, double sideBarHeight, double sideBarWidth, Color gameBgColour) {
        super(gameWindow, margins, sideBarHeight, sideBarWidth);

        // box for displaying next Tetromino
        NextTetrominoBox nextTetrominoBox = new NextTetrominoBox(gameArea, sideBarWidth / 2, gameBgColour);
        getChildren().add(nextTetrominoBox);
        mUpdateItems.add(nextTetrominoBox);

        // statsBox
        PlayerStatsBox playerStatsBox = new PlayerStatsBox(gameArea);
        getChildren().add(playerStatsBox);
        mUpdateItems.add(playerStatsBox);

        // add button bar to side bar
        addButtonBar();

        // add buttons
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

        // add actions for buttons
        restartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent click) {
                gameController.keyPressed(ControllerKeys.RESTART);
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
                gameController.keyPressed(ControllerKeys.TOGGLE_PAUSE);
            }
        });

        gridlinesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent click) {
                gameController.keyPressed(ControllerKeys.TOGGLE_GRIDLINES);
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
