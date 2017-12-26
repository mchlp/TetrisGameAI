/*
 * Michael Pu
 * TetrisGameAI - AIWatchStatsBox
 * ICS3U1 - Mr. Radulovic
 * December 25, 2017
 */

package frontend.aiWatch;

import ai.Trainer;
import ai.Watcher;
import frontend.base.StatsBox;
import frontend.common.GameArea;
import frontend.common.StatsBar;

public class AIWatchStatsBox extends StatsBox {
    private StatsBar mOrganismBar;
    private Watcher mWatcher;

    public AIWatchStatsBox(GameArea gameArea, Watcher watcher) {
        super(gameArea);
        mWatcher = watcher;

        mOrganismBar = new StatsBar("Organism ID", "Loading...");
        mOrganismBar.setSmallerFont();
        getChildren().add(mOrganismBar);


    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        mOrganismBar.setValue(mWatcher.getmCurOrganism().getmId().toString());
    }
}
