/*
 * Michael Pu
 * TetrisGameAI - AIFastTrainSidebar
 * ICS3U1 - Mr.Radulovic
 * December 28, 2017
 */

package frontend.aiFastTrain;

import ai.FastTrainer;
import backend.GameBrain;
import frontend.base.GameWindow;
import frontend.base.SaveOrganismDialog;
import frontend.base.Sidebar;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.io.File;

public class AIFastTrainSidebar extends Sidebar {

    private FastTrainer mFastTrainer;
    private Button mSaveEliteButton;

    public AIFastTrainSidebar(GameWindow gameWindow, GameBrain gameBrain, FastTrainer fastTrainer, double margins, double sideBarHeight, double sideBarWidth) {
        super(gameWindow, margins, sideBarHeight, sideBarWidth);

        mFastTrainer = fastTrainer;

        AIFastTrainStatsBox aiFastTrainStatsBox = new AIFastTrainStatsBox(gameBrain, fastTrainer);
        getChildren().add(aiFastTrainStatsBox);
        mUpdateItems.add(aiFastTrainStatsBox);

        addButtonBar();

        Button pauseButton = new Button("Pause");
        mButtonBar.getChildren().add(pauseButton);

        mSaveEliteButton = new Button("Save Elite");
        mSaveEliteButton.setDisable(true);
        mButtonBar.getChildren().add(mSaveEliteButton);

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

        mSaveEliteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent click) {
                new SaveOrganismDialog(mGameWindow.getmStage(), fastTrainer.getmPopulation().getElite());
            }
        });
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        if (mSaveEliteButton.isDisabled()) {
            if (mFastTrainer.getmPopulation().getElite() != null) {
                mSaveEliteButton.setDisable(false);
            }
        }
    }
}
