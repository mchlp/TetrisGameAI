/*
 * Michael Pu
 * TetrisGameAI - GameController
 * ICS3U1 - Mr. Radulovic
 * December 23, 2017
 */

package backend;

import frontend.GameArea;

public class GameController implements Updatable {

    private GameArea mGameArea;

    public GameController(GameArea gameArea) {
        this.mGameArea = gameArea;
    }

    public void keyPressed(ControllerKeys key) {
        switch (key) {
            case UP:
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
            case SPACE:
                mGameArea.drop();
                break;
            case RESTART:
                mGameArea.restart();
                break;
            case TOGGLE_PAUSE:
                mGameArea.togglePause();
                break;
            case TOGGLE_GRIDLINES:
                mGameArea.toggleGridliens();
        }
    }

    @Override
    public void update(double deltaTime) {
    }
}
