/*
 * Michael Pu
 * TetrisGameAI - GameController
 * ICS3U1 - Mr. Radulovic
 * December 23, 2017
 */

package backend;

import frontend.GameArea;

public class GameController implements Updatable {

    private static final double KEY_TIMEOUT_LENGTH = 0.5;

    private GameArea mGameArea;
    private boolean[] mKeyPressed;
    private double[] mKeyTimeouts;

    public GameController(GameArea gameArea) {
        mGameArea = gameArea;
        mKeyTimeouts = new double[ControllerKeys.getNumKeys()];

        for (ControllerKeys key : ControllerKeys.values()) {
            mKeyPressed[key.pos] = false;
            mKeyTimeouts[key.pos] = 0;
        }
    }

    public void setKeyPressed(ControllerKeys key, boolean pressed) {
        if (pressed) {
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
            }
            mKeyPressed[key.pos] = true;
            mKeyTimeouts[key.pos] = KEY_TIMEOUT_LENGTH;
        } else {
            mKeyPressed[key.pos] = false;
            mKeyTimeouts[key.pos] = -1;
        }

    }

    @Override
    public void update(double deltaTime) {
        for (ControllerKeys key : ControllerKeys.values()) {
            if (mKeyPressed[key.pos]) {
                mKeyTimeouts[key.pos] -= deltaTime;
                if (mKeyTimeouts[key.pos] <= 0) {
                    setKeyPressed(key, true);
                }
            }
        }
    }
}
