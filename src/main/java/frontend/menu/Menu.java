/*
 * Michael Pu
 * TetrisGameAI - Menu
 * ICS3U1 - Mr.Radulovic
 * January 20, 2018
 */

package frontend.menu;

import ai.Organism;
import ai.Population;
import backend.GameMode;
import frontend.instructions.InstructionsDialog;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Holds the menu items and arranges them vertically.
 */
public class Menu extends VBox {

    private static final int DEFAULT_PADDING = 20;

    /**
     * Displays the AI Train button if <code>true</code>, otherwise it is hidden.
     */
    private boolean mAdvancedMode;

    private MenuItem mSinglePlayerButton;
    private MenuItem mPlayAIButton;
    private MenuItem mWatchAIButton;
    private MenuItem mInstructionsButton;
    private MenuItem mTrainAIButton;
    private MenuItem mExitButton;

    /**
     * Object loaded from a dialog (population or organism) that can be accessed by application.
     */
    private Object mLoadedObject;
    private boolean mFastMode;

    public Menu(Stage stage) {

        // set up display properties
        mAdvancedMode = false;
        setSpacing(10);
        setPadding(new Insets(DEFAULT_PADDING));
        setFillWidth(true);

        // buttons
        mInstructionsButton = new MenuItem("Instructions", this);
        getChildren().add(mInstructionsButton);

        mSinglePlayerButton = new MenuItem("Single Player", this);
        getChildren().add(mSinglePlayerButton);

        mPlayAIButton = new MenuItem("Play Against AI", this);
        getChildren().add(mPlayAIButton);

        mWatchAIButton = new MenuItem("Watch AI", this);
        getChildren().add(mWatchAIButton);

        mTrainAIButton = new MenuItem("Train AI", this);
        getChildren().add(mTrainAIButton);
        mTrainAIButton.setVisible(false);

        mExitButton = new MenuItem("Exit Game", this);
        getChildren().add(mExitButton);

        // actions for when each button is clicked
        mInstructionsButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                InstructionsDialog instructionsDialog = new InstructionsDialog(stage);
                instructionsDialog.showAndWait();
            }
        });

        mSinglePlayerButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ((MenuScreen) getParent()).mGameMode = GameMode.PLAYER;
            }
        });

        mPlayAIButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                SelectOrganismDialog selectOrganismDialog = new SelectOrganismDialog(stage);
                Organism loadedOrganism = selectOrganismDialog.showDialog();
                if (loadedOrganism != null) {
                    mLoadedObject = loadedOrganism;
                    mFastMode = selectOrganismDialog.getIsFastMode();
                    ((MenuScreen) getParent()).mGameMode = GameMode.AI_PLAY;
                }
            }
        });

        mWatchAIButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                SelectOrganismDialog selectOrganismDialog = new SelectOrganismDialog(stage);
                Organism loadedOrganism = selectOrganismDialog.showDialog();
                if (loadedOrganism != null) {
                    mLoadedObject = loadedOrganism;
                    mFastMode = selectOrganismDialog.getIsFastMode();
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

        mExitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.close();
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

    public boolean ismFastMode() {
        return mFastMode;
    }
}
