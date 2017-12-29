/*
 * Michael Pu
 * TetrisGameAI - AITrainSidebar
 * ICS3U1 - Mr.Radulovic
 * December 28, 2017
 */

package frontend.aiTrain;

import ai.Trainer;
import frontend.base.GameWindow;
import frontend.base.Sidebar;
import frontend.common.GameArea;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.io.File;

public class AITrainSidebar extends Sidebar {
    public AITrainSidebar(GameWindow gameWindow, GameArea gameArea, Trainer trainer, double margins, double sideBarHeight, double sideBarWidth) {
        super(gameWindow, margins, sideBarHeight, sideBarWidth);

        AITrainStatsBox aiTrainStatsBox = new AITrainStatsBox(gameArea, trainer);
        getChildren().add(aiTrainStatsBox);
        mUpdateItems.add(aiTrainStatsBox);

        addButtonBar();

        Button saveEliteButton = new Button("Save Elite");
        mButtonBar.getChildren().add(saveEliteButton);

        saveEliteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent click) {
                trainer.getmPopulation().saveElite(new File("/home/mpu/Desktop"));
            }
        });

    }
}
