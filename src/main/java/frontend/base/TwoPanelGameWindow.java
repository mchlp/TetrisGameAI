/*
 * Michael Pu
 * TetrisGameAI - TwoPanelGameWindow
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
 * Abstract class that is the base for a two panel window for all game modes that require two panels in their game
 * window. These are game modes where there is one game running (Single Player, Watch AI, Train AI) at once.
 */
public abstract class TwoPanelGameWindow extends GameWindow {

    /**
     * The pane that will display the game.
     */
    protected StackPane mGamePane;

    /**
     * The {@link GameController} for the game.
     */
    protected GameController mGameController;

    /**
     * Creates a game window with two panels that can display a two panes at once.
     *
     * @param stage The stage of the window.
     * @param height The height of the window.
     * @param width The width of the window.
     * @param gameMode The current game mode.
     */
    public TwoPanelGameWindow(Stage stage, double height, double width, GameMode gameMode) {
        super(stage, height, width, gameMode);

        // set up the side bar on the right
        mSideBarHeight = height;
        mSideBarWidth = width - mGameAreaWidth;

        // set up the game on the left
        mGamePane = new StackPane();
        mGamePane.setPrefHeight(mGameAreaHeight);
        mGamePane.setPrefWidth(mGameAreaWidth);
        setLeft(mGamePane);
    }

    public GameController getmGameController() {
        return mGameController;
    }
}
