/*
 * Michael Pu
 * TetrisGameAI - AIPlayTrainSidebar
 * ICS3U1 - Mr. Radulovic
 * December 29, 2017
 */

package frontend.aiPlay;

import backend.ControllerKeys;
import backend.GameMode;
import frontend.common.DoubleStatsBox;
import frontend.base.GameWindow;
import frontend.base.Sidebar;
import frontend.common.GameArea;
import frontend.common.GameController;
import frontend.player.NextTetrominoBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class AIPlaySidebar extends Sidebar {
    public AIPlaySidebar(GameWindow gameWindow, GameArea aiGameArea, GameArea playerGameArea, GameController aiGameController, GameController playerGameController, double margins, double sideBarHeight, double sideBarWidth, Color gameBgColour) {
        super(gameWindow, margins, sideBarHeight, sideBarWidth);

        NextTetrominoBox nextTetrominoBox = new NextTetrominoBox(playerGameArea, sideBarWidth / 2, gameBgColour);
        getChildren().add(nextTetrominoBox);
        mUpdateItems.add(nextTetrominoBox);

        DoubleStatsBox playerStatsBox = new DoubleStatsBox(GameMode.AI_PLAY, aiGameArea, playerGameArea);
        getChildren().add(playerStatsBox);
        mUpdateItems.add(playerStatsBox);

        addButtonBar();

        Button restartButton = new Button("Restart");
        mButtonBar.getChildren().add(restartButton);

        Button pauseButton = new Button("Toggle Pause");
        mButtonBar.getChildren().add(pauseButton);

        Button gridlinesButton = new Button("Toggle Gridlines");
        mButtonBar.getChildren().add(gridlinesButton);

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
    }
}
