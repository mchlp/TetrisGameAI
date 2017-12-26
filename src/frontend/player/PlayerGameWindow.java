/*
 * Michael Pu
 * TetrisGameAI - PlayerGameWindow
 * ICS3U1 - Mr. Radulovic
 * December 25, 2017
 */

package frontend.player;

import frontend.base.GameWindow;
import frontend.common.GameArea;
import frontend.common.GameController;
import frontend.common.GameMode;
import javafx.scene.shape.Rectangle;

public class PlayerGameWindow extends GameWindow {

    public PlayerGameWindow(double height, double width) {
        super(height, width, GameMode.PLAYER);

        Rectangle gameAreaBackground = new Rectangle(mCanvasWidth + 10, mCanvasHeight + 10, GAME_OUTLINE_COLOUR);
        mGamePane.getChildren().add(gameAreaBackground);

        mGameArea = new GameArea(mCanvasWidth, mCanvasHeight, GAME_BACKGROUND_COLOUR, mGameMode);
        mGamePane.getChildren().add(mGameArea);
        mGameController = new GameController(mGameArea);
        mUpdateItems.add(mGameArea);

        PlayerSidebar playerSidebar = new PlayerSidebar(mGameArea, mGameController, DEFAULT_MARGINS, mSideBarHeight, mSideBarWidth, GAME_BACKGROUND_COLOUR);
        setRight(playerSidebar);
        mUpdateItems.add(playerSidebar);
    }

    public GameController getmGameController() {
        return mGameController;
    }
}
