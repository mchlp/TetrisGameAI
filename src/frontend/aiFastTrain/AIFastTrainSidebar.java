/*
 * Michael Pu
 * TetrisGameAI - AIFastTrainSidebar
 * ICS3U1 - Mr. Radulovic
 * December 26, 2017
 */

package frontend.aiFastTrain;

import ai.FastTrainer;
import backend.GameBrain;
import frontend.base.GameWindow;
import frontend.base.Sidebar;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.io.File;

public class AIFastTrainSidebar extends Sidebar {
    public AIFastTrainSidebar(GameWindow gameWindow, GameBrain gameBrain, FastTrainer fastTrainer, double margins, double sideBarHeight, double sideBarWidth) {
        super(gameWindow, margins, sideBarHeight, sideBarWidth);

        AIFastTrainStatsBox aiFastTrainStatsBox = new AIFastTrainStatsBox(gameBrain, fastTrainer);
        getChildren().add(aiFastTrainStatsBox);
        mUpdateItems.add(aiFastTrainStatsBox);

        Button pauseButton = new Button("Pause");
        mButtonBar.getChildren().add(pauseButton);

        Button saveEliteButton = new Button("Save Elite");
        mButtonBar.getChildren().add(saveEliteButton);

        pauseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent click) {
                if (fastTrainer.ismTraining()) {
                    fastTrainer.setmTraining(false);
                    pauseButton.setText("Play");
                } else {
                    fastTrainer.setmTraining(true);
                    pauseButton.setText("Pause");
                }
            }
        });

        saveEliteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent click) {
                fastTrainer.getmPopulation().saveElite(new File("/home/mpu/Desktop"));
            }
        });
    }
}
