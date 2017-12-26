/*
 * Michael Pu
 * TetrisGameAI - Watcher
 * ICS3U1 - Mr. Radulovic
 * December 25, 2017
 */

package ai;

import backend.GameBrain;
import frontend.common.GameController;

public class Watcher extends GUIBrain {

    public Watcher(GameBrain gameBrain, GameController gameController, Organism organism) {
        super(gameBrain, gameController);
        mCurOrganism = organism;
    }
}
