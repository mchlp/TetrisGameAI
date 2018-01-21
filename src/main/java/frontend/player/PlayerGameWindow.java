/*
 * Michael Pu
 * TetrisGameAI - PlayerGameWindow
 * ICS3U1 - Mr.Radulovic
 * January 20, 2018
 */

package frontend.player;

import backend.GameMode;
import frontend.base.TwoPanelGameWindow;
import frontend.common.GameArea;
import frontend.common.GameController;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Game window for single player mode.
 */
public class PlayerGameWindow extends TwoPanelGameWindow {

    private GameArea mGameArea;

    public PlayerGameWindow(Stage stage, double height, double width) {
        super(stage, height, width, GameMode.PLAYER);

        mWindowTitle = "Tetris Game - Human Player Version";

        // set up left side of window (game area)
        Rectangle gameAreaBackground = new Rectangle(mCanvasWidth + 10, mCanvasHeight + 10, GAME_OUTLINE_COLOUR);
        mGamePane.getChildren().add(gameAreaBackground);
        mGameArea = new GameArea(mBackgroundMusic, mCanvasWidth, mCanvasHeight, GAME_BACKGROUND_COLOUR, mGameMode);
        mGameProcessor = mGameArea.getmGameProcessor();
        mGamePane.getChildren().add(mGameArea);
        mGameController = new GameController(mGameArea);
        mUpdateItems.add(mGameArea);

        // set up right side of window (sidebar)
        PlayerSidebar playerSidebar = new PlayerSidebar(this, mGameArea, mGameController, DEFAULT_MARGINS, mSideBarHeight, mSideBarWidth, GAME_BACKGROUND_COLOUR);
        setRight(playerSidebar);
        mUpdateItems.add(playerSidebar);
    }

    public GameArea getmGameArea() {
        return mGameArea;
    }
}
