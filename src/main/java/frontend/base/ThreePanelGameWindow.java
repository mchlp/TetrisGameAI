/*
 * Michael Pu
 * TetrisGameAI - ThreePanelGameWindow
 * ICS3U1 - Mr.Radulovic
 * January 13, 2018
 */

package frontend.base;

import backend.GameMode;
import frontend.common.GameArea;
import frontend.common.GameController;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Abstract class that is the base for a three panel window for all game modes that require three panels in their game
 * window. These are game modes where there are two games running at once (Player vs. AI).
 */
public abstract class ThreePanelGameWindow extends GameWindow {

    /**
     * The left pane of the game window which will store the game on the left.
     */
    protected StackPane mLeftGamePane;

    /**
     * The right pane of the game window which will display the game on the right.
     */
    protected StackPane mRightGamePane;

    /**
     * The {@link GameController} for the game on the left.
     */
    protected GameController mLeftGameController;

    /**
     * The {@link GameController} for the game on the right.
     */
    protected GameController mRightGameController;

    /**
     * Creates a game window with three panels that can display three panes at once.
     *
     * @param stage The stage of the window.
     * @param height The height of the window.
     * @param width The width of the window.
     * @param gameMode The current game mode.
     */
    public ThreePanelGameWindow(Stage stage, double height, double width, GameMode gameMode) {
        super(stage, height, width, gameMode);

        // set up side bar in the middle
        mSideBarHeight = height;
        mSideBarWidth = width - (mGameAreaWidth * 2);

        // set up the game on the left
        mLeftGamePane = new StackPane();
        mLeftGamePane.setPrefHeight(mGameAreaHeight);
        mLeftGamePane.setPrefWidth(mGameAreaWidth);
        setLeft(mLeftGamePane);

        // set up the game on the right
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
