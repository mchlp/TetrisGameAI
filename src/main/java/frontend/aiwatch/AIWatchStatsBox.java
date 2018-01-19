/*
 * Michael Pu
 * TetrisGameAI - AIWatchStatsBox
 * ICS3U1 - Mr.Radulovic
 * January 13, 2018
 */

package frontend.aiwatch;

import ai.Watcher;
import backend.GameProcessor;
import frontend.common.GameArea;
import frontend.common.StatsBox;
import frontend.common.StatsBar;

public class AIWatchStatsBox extends frontend.base.StatsBox {
    private StatsBar mOrganismBar;
    private Watcher mWatcher;
    private StatsBar mTimeBar;
    private GameArea mGameArea;
    private GameProcessor mGameProcessor;
    private StatsBox mStatsBox;

    public AIWatchStatsBox(GameArea gameArea, Watcher watcher) {
        super(gameArea.getmGameProcessor().getmGameMode());
        mWatcher = watcher;
        mGameArea = gameArea;
        mGameProcessor = mGameArea.getmGameProcessor();

        mStatsBox = new StatsBox(mGameProcessor, this, true);

        mOrganismBar = new StatsBar("Organism Name", "Loading...");
        getChildren().add(mOrganismBar);

        mTimeBar = new StatsBar("Time", "0:0:00");
        getChildren().add(mTimeBar);
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        mStatsBox.update(deltaTime);
        mOrganismBar.setValue(mWatcher.getmCurOrganism().getmName());
        mTimeBar.setValue(getTimeInString((int) mGameArea.getmElapsedTime()));
    }
}
