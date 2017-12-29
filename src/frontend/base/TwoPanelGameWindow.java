/*
 * Michael Pu
 * TetrisGameAI - TwoPanelGameWindow
 * ICS3U1 - Mr. Radulovic
 * December 29, 2017
 */

package frontend.base;

import backend.GameMode;
import frontend.common.GameController;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public abstract class TwoPanelGameWindow extends GameWindow {

    protected StackPane mGamePane;
    protected GameController mGameController;

    public TwoPanelGameWindow(Stage stage, double height, double width, GameMode gameMode) {
        super(stage, height, width, gameMode);

        mSideBarHeight = height;
        mSideBarWidth = width - mGameAreaWidth;

        mGamePane = new StackPane();
        mGamePane.setPrefHeight(mGameAreaHeight);
        mGamePane.setPrefWidth(mGameAreaWidth);
        setLeft(mGamePane);
    }

    public GameController getmGameController() {
        return mGameController;
    }
}
