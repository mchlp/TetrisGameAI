/*
 * Michael Pu
 * TetrisGameAI - Watcher
 * ICS3U1 - Mr.Radulovic
 * January 13, 2018
 */

package ai;

import backend.GameProcessor;
import frontend.common.GameController;

/**
 * Displays the gameplay of an AI {@link Organism}. Extends the {@link GUIBrain} class, which is used
 * to determines the best move and send it to the {@link GameController}. Each organism in the population plays one game during each generation before the
 * population is bred.
 */
public class Watcher extends GUIBrain {

    public Watcher(GameProcessor gameProcessor, GameController gameController, Organism organism, boolean fastMode) {
        super(gameProcessor, gameController, fastMode);
        mCurOrganism = organism;
    }
}
