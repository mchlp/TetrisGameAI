/*
 * Michael Pu
 * TetrisGameAI - SinglePlayerControllerHandler
 * ICS3U1 - Mr.Radulovic
 * January 13, 2018
 */

package frontend.common;

import backend.ControllerKeys;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class SinglePlayerControllerHandler implements EventHandler<KeyEvent> {

    private GameController mGameController;
    private GameController mSecondGameController;

    /**
     * @param gameController
     * @param secondGameController
     */
    public SinglePlayerControllerHandler(GameController gameController, GameController secondGameController) {
        mGameController = gameController;
        mSecondGameController = secondGameController;
    }

    @Override
    public void handle(KeyEvent keyPressed) {
        KeyCode code = keyPressed.getCode();
        switch (code) {
            case LEFT:
                mGameController.keyPressed(ControllerKeys.LEFT);
                break;
            case RIGHT:
                mGameController.keyPressed(ControllerKeys.RIGHT);
                break;
            case UP:
                mGameController.keyPressed(ControllerKeys.ROTATE);
                break;
            case DOWN:
                mGameController.keyPressed(ControllerKeys.DOWN);
                break;
            case SPACE:
                mGameController.keyPressed(ControllerKeys.DROP);
                break;
            case R:
                mGameController.keyPressed(ControllerKeys.RESTART);
                if (mSecondGameController != null) {
                    mSecondGameController.keyPressed(ControllerKeys.RESTART);
                }
                break;
            case P:
                mGameController.keyPressed(ControllerKeys.TOGGLE_PAUSE);
                if (mSecondGameController != null) {
                    mSecondGameController.keyPressed(ControllerKeys.TOGGLE_PAUSE);
                }
                break;
            case G:
                mGameController.keyPressed(ControllerKeys.TOGGLE_GRIDLINES);
                if (mSecondGameController != null) {
                    mSecondGameController.keyPressed(ControllerKeys.TOGGLE_GRIDLINES);
                }
                break;
            case ADD:
                mGameController.keyPressed(ControllerKeys.NEXT_LEVEL);
                if (mSecondGameController != null) {
                    mSecondGameController.keyPressed(ControllerKeys.NEXT_LEVEL);
                }
                break;
        }
        keyPressed.consume();
    }
}
