/*
 * Michael Pu
 * TetrisGameAI - PlayerSidebar
 * ICS3U1 - Mr.Radulovic
 * December 28, 2017
 */

package frontend.player;

import backend.ControllerKeys;
import frontend.base.GameWindow;
import frontend.base.Sidebar;
import frontend.common.GameArea;
import frontend.common.GameController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class PlayerSidebar extends Sidebar {
    public PlayerSidebar(GameWindow gameWindow, GameArea gameArea, GameController gameController, double margins, double sideBarHeight, double sideBarWidth, Color gameBgColour) {
        super(gameWindow, margins, sideBarHeight, sideBarWidth);

        NextTetrominoBox nextTetrominoBox = new NextTetrominoBox(gameArea, sideBarWidth / 2, gameBgColour);
        getChildren().add(nextTetrominoBox);
        mUpdateItems.add(nextTetrominoBox);

        PlayerStatsBox playerStatsBox = new PlayerStatsBox(gameArea);
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
                gameController.keyPressed(ControllerKeys.RESTART);
            }
        });

        pauseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent click) {
                gameController.keyPressed(ControllerKeys.TOGGLE_PAUSE);
            }
        });

        gridlinesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent click) {
                gameController.keyPressed(ControllerKeys.TOGGLE_GRIDLINES);
            }
        });
    }
}
