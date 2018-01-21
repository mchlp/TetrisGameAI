/*
 * Michael Pu
 * TetrisGameAI - GameController
 * ICS3U1 - Mr.Radulovic
 * January 19, 2018
 */

package frontend.common;

import backend.ControllerKeys;

/**
 * Serves as a connection between the player or AI and the game, allowing the game to be controlled.
 */
public class GameController {

    private GameArea mGameArea;

    public GameController(GameArea gameArea) {
        this.mGameArea = gameArea;
    }

    public void keyPressed(ControllerKeys key) {
        switch (key) {
            case ROTATE:
                mGameArea.rotate();
                break;
            case DOWN:
                mGameArea.moveDown();
                break;
            case LEFT:
                mGameArea.moveLeft();
                break;
            case RIGHT:
                mGameArea.moveRight();
                break;
            case DROP:
                mGameArea.drop();
                break;
            case RESTART:
                mGameArea.restart();
                break;
            case TOGGLE_PAUSE:
                mGameArea.togglePause();
                break;
            case TOGGLE_GRIDLINES:
                mGameArea.toggleGridlines();
                break;
            case NEXT_LEVEL:
                mGameArea.incrementLevel();
        }
    }
}
