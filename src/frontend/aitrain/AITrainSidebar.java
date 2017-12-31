/*
 * Michael Pu
 * TetrisGameAI - AITrainSidebar
 * ICS3U1 - Mr.Radulovic
 * December 28, 2017
 */

package frontend.aitrain;

import ai.Trainer;
import backend.ControllerKeys;
import frontend.base.GameWindow;
import frontend.base.Sidebar;
import frontend.common.GameArea;
import frontend.common.SaveOrganismDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class AITrainSidebar extends Sidebar {

    private Trainer mTrainer;
    private Button mSaveEliteButton;
    private boolean mMuted;
    private boolean mPaused;

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

        Button muteButton = new Button("Mute");
        mButtonBar.getChildren().add(muteButton);
        mMuted = false;

        Button pauseButton = new Button("Pause");
        mButtonBar.getChildren().add(pauseButton);
        mPaused = false;

        mSaveEliteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent click) {
                new SaveOrganismDialog(mGameWindow.getmStage(), trainer.getmPopulation().getElite());
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
                trainer.getmGameController().keyPressed(ControllerKeys.TOGGLE_PAUSE);
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
