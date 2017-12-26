/*
 * Michael Pu
 * TetrisGameAI - Watcher
 * ICS3U1 - Mr. Radulovic
 * December 25, 2017
 */

package ai;

import backend.Updatable;
import frontend.common.GameArea;
import frontend.common.GameController;

public class Watcher extends Brain implements Updatable {

    public Watcher(GameArea gameArea, GameController gameController, Organism organism) {
        super(gameArea, gameController);
        mCurOrganism = organism;
    }
}
