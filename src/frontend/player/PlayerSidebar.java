/*
 * Michael Pu
 * TetrisGameAI - PlayerSidebar
 * ICS3U1 - Mr. Radulovic
 * December 25, 2017
 */

package frontend.player;

import backend.ControllerKeys;
import backend.GameController;
import frontend.GameArea;
import frontend.Sidebar;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class PlayerSidebar extends Sidebar {
    public PlayerSidebar(GameArea gameArea, GameController gameController, double margins, double sideBarHeight, double sideBarWidth, Color gameBgColour) {
        super(gameArea, gameController, margins, sideBarHeight, sideBarWidth);

        NextTetrominoBox nextTetrominoBox = new NextTetrominoBox(gameArea, sideBarWidth/2, gameBgColour);
        getChildren().add(0, nextTetrominoBox);
        mUpdateItems.add(nextTetrominoBox);

        HBox buttonBar = new HBox(10);
        buttonBar.setAlignment(Pos.CENTER);
        getChildren().add(buttonBar);

        Button restartButton = new Button("Restart");
        buttonBar.getChildren().add(restartButton);

        Button pauseButton = new Button("Toggle Pause");
        buttonBar.getChildren().add(pauseButton);

        Button gridlinesButton = new Button("Toggle Gridlines");
        buttonBar.getChildren().add(gridlinesButton);

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
