/*
 * Michael Pu
 * TetrisGameAI - AIFastTrainSidebar
 * ICS3U1 - Mr.Radulovic
 * January 13, 2018
 */

package frontend.aifasttrain;

import ai.FastTrainer;
import backend.GameProcessor;
import frontend.base.GameWindow;
import frontend.base.Sidebar;
import frontend.common.SaveOrganismDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 * Side bar for AI fast train mode.
 */
public class AIFastTrainSidebar extends Sidebar {

    private FastTrainer mFastTrainer;
    private Button mSaveEliteButton;

    public AIFastTrainSidebar(GameWindow gameWindow, GameProcessor gameProcessor, FastTrainer fastTrainer, double margins, double sideBarHeight, double sideBarWidth) {
        super(gameWindow, margins, sideBarHeight, sideBarWidth);

        mFastTrainer = fastTrainer;

        // statsBox
        AIFastTrainStatsBox aiFastTrainStatsBox = new AIFastTrainStatsBox(gameProcessor, fastTrainer);
        getChildren().add(aiFastTrainStatsBox);
        mUpdateItems.add(aiFastTrainStatsBox);

        // add button bar to sidebar
        addButtonBar();

        // add buttons
        Button pauseButton = new Button("Pause");
        mButtonBar.getChildren().add(pauseButton);

        mSaveEliteButton = new Button("Save Elite");
        mSaveEliteButton.setDisable(true);
        mButtonBar.getChildren().add(mSaveEliteButton);

        // set actions for buttons
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
                fastTrainer.setmTraining(false);
                new SaveOrganismDialog(mGameWindow.getmStage(), fastTrainer.getmPopulation().getElite());
                fastTrainer.setmTraining(true);
            }
        });
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        if (mSaveEliteButton.isDisabled()) {
            if (mFastTrainer.getmPopulation().getElite() != null) {
                // only enable save button when an elite organism has been found
                mSaveEliteButton.setDisable(false);
            }
        }
    }
}
