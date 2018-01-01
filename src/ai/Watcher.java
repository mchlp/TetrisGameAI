/*
 * Michael Pu
 * TetrisGameAI - Watcher
 * ICS3U1 - Mr. Radulovic
 * December 30, 2017
 */

package ai;

import backend.GameProcessor;
import frontend.common.GameController;

public class Watcher extends GUIBrain {

    public Watcher(GameProcessor gameProcessor, GameController gameController, Organism organism, boolean fastMode) {
        super(gameProcessor, gameController, fastMode);
        mCurOrganism = organism;
    }
}
