/*
 * Michael Pu
 * TetrisGameAI - PlayerGameWindow
 * ICS3U1 - Mr. Radulovic
 * December 25, 2017
 */

package frontend.player;

import frontend.base.GameWindow;
import frontend.common.GameController;
import frontend.common.GameMode;

public class PlayerGameWindow extends GameWindow {

    public PlayerGameWindow(double height, double width) {
        super(height, width, GameMode.PLAYER);

        PlayerSidebar playerSidebar = new PlayerSidebar(mGameArea, mGameController, DEFAULT_MARGINS, mSideBarHeight, mSideBarWidth, GAME_BACKGROUND_COLOUR);
        setRight(playerSidebar);
        mUpdateItems.add(playerSidebar);
    }

    public GameController getmGameController() {
        return mGameController;
    }
}
