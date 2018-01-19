/*
 * Michael Pu
 * TetrisGameAI - ControllerKeys
 * ICS3U1 - Mr.Radulovic
 * January 13, 2018
 */

package backend;

import frontend.common.GameController;

/**
 * The possible inputs that can be sent to a {@link GameController};
 */
public enum ControllerKeys {
    LEFT,
    RIGHT,
    ROTATE,
    DOWN,
    DROP,
    RESTART,
    TOGGLE_PAUSE,
    TOGGLE_GRIDLINES,
    NEXT_LEVEL
}
