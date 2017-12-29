/*
 * Michael Pu
 * TetrisGameAI - AITrainSidebar
 * ICS3U1 - Mr.Radulovic
 * December 28, 2017
 */

package frontend.aiTrain;

import ai.Trainer;
import frontend.base.GameWindow;
import frontend.common.SaveOrganismDialog;
import frontend.base.Sidebar;
import frontend.common.GameArea;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class AITrainSidebar extends Sidebar {

    private Trainer mTrainer;
    private Button mSaveEliteButton;

    public AITrainSidebar(GameWindow gameWindow, GameArea gameArea, Trainer trainer, double margins, double sideBarHeight, double sideBarWidth) {
        super(gameWindow, margins, sideBarHeight, sideBarWidth);

        mTrainer = trainer;

        AITrainStatsBox aiTrainStatsBox = new AITrainStatsBox(gameArea, trainer);
        getChildren().add(aiTrainStatsBox);
        mUpdateItems.add(aiTrainStatsBox);

        addButtonBar();

        mSaveEliteButton = new Button("Save Elite");
        mSaveEliteButton.setDisable(true);
        mButtonBar.getChildren().add(mSaveEliteButton);

        mSaveEliteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent click) {
                new SaveOrganismDialog(mGameWindow.getmStage(), trainer.getmPopulation().getElite());
            }
        });
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        if (mSaveEliteButton.isDisabled()) {
            if (mTrainer.getmPopulation().getElite() != null) {
                mSaveEliteButton.setDisable(false);
            }
        }
    }
}
