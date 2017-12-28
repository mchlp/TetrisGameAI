/*
 * Michael Pu
 * TetrisGameAI - Menu
 * ICS3U1 - Mr. Radulovic
 * December 28, 2017
 */

package menu;

import backend.GameMode;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class Menu extends VBox {

    private static final int DEFAULT_PADDING = 20;

    private boolean mAdvancedMode;
    private MenuItem mSinglePlayerButton;
    private MenuItem mPlayAIButton;
    private MenuItem mWatchAIButton;
    private MenuItem mTrainAIButton;


    public Menu() {

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
                ((MenuScreen) getParent()).mGameMode = GameMode.AI_WATCHER;
            }
        });

        mTrainAIButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ((MenuScreen) getParent()).mGameMode = GameMode.MAIN_MENU;
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
}
