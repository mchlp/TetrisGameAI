/*
 * Michael Pu
 * TetrisGameAI - AIFastTrainSidebar
 * ICS3U1 - Mr. Radulovic
 * December 30, 2017
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

public class AIFastTrainSidebar extends Sidebar {

    private FastTrainer mFastTrainer;
    private Button mSaveEliteButton;

    public AIFastTrainSidebar(GameWindow gameWindow, GameProcessor gameProcessor, FastTrainer fastTrainer, double margins, double sideBarHeight, double sideBarWidth) {
        super(gameWindow, margins, sideBarHeight, sideBarWidth);

        mFastTrainer = fastTrainer;

        AIFastTrainStatsBox aiFastTrainStatsBox = new AIFastTrainStatsBox(gameProcessor, fastTrainer);
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
                mSaveEliteButton.setDisable(false);
            }
        }
    }
}
