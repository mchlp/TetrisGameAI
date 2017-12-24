/*
 * Michael Pu
 * TetrisGameAI - GameController
 * ICS3U1 - Mr. Radulovic
 * December 23, 2017
 */

package backend;

import frontend.GameArea;

public class GameController implements Updatable {

    private static final double KEY_TIMEOUT_LENGTH = 10.0;

    private GameArea gameArea;

    public GameController(GameArea gameArea) {
        this.gameArea = gameArea;
    }

    public void keyPressed(ControllerKeys key) {
        switch (key) {
            case UP:
                gameArea.rotate();
                break;
            case DOWN:
                gameArea.moveDown();
                break;
            case LEFT:
                gameArea.moveLeft();
                break;
            case RIGHT:
                gameArea.moveRight();
                break;
            case SPACE:
                gameArea.drop();
        }
    }

    @Override
    public void update(double deltaTime) {

    }
}
