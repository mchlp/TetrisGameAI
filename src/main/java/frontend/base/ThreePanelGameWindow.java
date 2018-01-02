/*
 * Michael Pu
 * TetrisGameAI - ThreePanelGameWindow
 * ICS3U1 - Mr. Radulovic
 * December 29, 2017
 */

package frontend.base;

import backend.GameMode;
import frontend.common.GameController;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public abstract class ThreePanelGameWindow extends GameWindow {

    protected StackPane mLeftGamePane;
    protected StackPane mRightGamePane;

    protected GameController mLeftGameController;
    protected GameController mRightGameController;

    public ThreePanelGameWindow(Stage stage, double height, double width, GameMode gameMode) {
        super(stage, height, width, gameMode);

        mSideBarHeight = height;
        mSideBarWidth = width - (mGameAreaWidth * 2);

        mLeftGamePane = new StackPane();
        mLeftGamePane.setPrefHeight(mGameAreaHeight);
        mLeftGamePane.setPrefWidth(mGameAreaWidth);
        setLeft(mLeftGamePane);

        mRightGamePane = new StackPane();
        mRightGamePane.setPrefHeight(mGameAreaHeight);
        mRightGamePane.setPrefWidth(mGameAreaWidth);
        setRight(mRightGamePane);
    }

    public GameController getmLeftGameController() {
        return mLeftGameController;
    }

    public GameController getmRightGameController() {
        return mRightGameController;
    }
}
