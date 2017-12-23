/*
 * Michael Pu
 * TetrisGameAI - Keys
 * ICS3U1 - Mr. Radulovic
 * December 23, 2017
 */

package backend;

public enum ControllerKeys {
    LEFT(0),
    RIGHT(1),
    UP(2),
    DOWN(3);

    public final int pos;

    ControllerKeys(int pos) {
        this.pos = pos;
    }

    static int getNumKeys() {
        return values().length;
    }
}
