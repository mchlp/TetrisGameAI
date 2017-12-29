/*
 * Michael Pu
 * TetrisGameAI - Menu
 * ICS3U1 - Mr.Radulovic
 * December 28, 2017
 */

package menu;

import ai.Organism;
import ai.Population;
import backend.GameMode;
import frontend.common.SelectOrganismDialog;
import frontend.common.SelectPopulationDialog;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Menu extends VBox {

    private static final int DEFAULT_PADDING = 20;

    private boolean mAdvancedMode;
    private MenuItem mSinglePlayerButton;
    private MenuItem mPlayAIButton;
    private MenuItem mWatchAIButton;
    private MenuItem mTrainAIButton;
    private Object mLoadedObject;

    public Menu(Stage stage) {

        mAdvancedMode = false;
        setSpacing(10);
        setPadding(new Insets(DEFAULT_PADDING));

        mSinglePlayerButton = new MenuItem("Single Player");
        getChildren().add(mSinglePlayerButton);

        mPlayAIButton = new MenuItem("Play Against AI");
        getChildren().add(mPlayAIButton);

        mWatchAIButton = new MenuItem("Watch AI");
        getChildren().add(mWatchAIButton);

        mTrainAIButton = new MenuItem("Train AI");
        getChildren().add(mTrainAIButton);
        mTrainAIButton.setVisible(false);

        mSinglePlayerButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ((MenuScreen) getParent()).mGameMode = GameMode.PLAYER;
            }
        });

        mPlayAIButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ((MenuScreen) getParent()).mGameMode = GameMode.MAIN_MENU;
            }
        });

        mWatchAIButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                SelectOrganismDialog selectOrganismDialog = new SelectOrganismDialog(stage);
                Organism loadedOrganism = selectOrganismDialog.showDialog();
                if (loadedOrganism != null) {
                    mLoadedObject = loadedOrganism;
                    ((MenuScreen) getParent()).mGameMode = GameMode.AI_WATCHER;
                }
            }
        });

        mTrainAIButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                SelectPopulationDialog selectPopulationDialog = new SelectPopulationDialog(stage);
                Population loadedPopulation = selectPopulationDialog.showDialog();
                if (loadedPopulation != null) {
                    mLoadedObject = loadedPopulation;
                    ((MenuScreen) getParent()).mGameMode = selectPopulationDialog.getmTrainMode();
                }
            }
        });
    }

    public void toggleAdvanced() {
        mAdvancedMode = !mAdvancedMode;
        if (mAdvancedMode) {
            mTrainAIButton.setVisible(true);
        } else {
            mTrainAIButton.setVisible(false);
        }
    }

    public Object getmLoadedObject() {
        return mLoadedObject;
    }
}
