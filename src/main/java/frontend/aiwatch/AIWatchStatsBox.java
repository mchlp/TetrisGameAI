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
import frontend.common.BasicStatsBox;
import frontend.common.StatsBar;

/**
 * StatsBox for watching AI play mode.
 */
public class AIWatchStatsBox extends frontend.base.StatsBox {
    private StatsBar mOrganismBar;
    private Watcher mWatcher;
    private StatsBar mTimeBar;
    private GameArea mGameArea;
    private GameProcessor mGameProcessor;
    private BasicStatsBox mBasicStatsBox;

    public AIWatchStatsBox(GameArea gameArea, Watcher watcher) {
        super(gameArea.getmGameProcessor().getmGameMode());
        mWatcher = watcher;
        mGameArea = gameArea;
        mGameProcessor = mGameArea.getmGameProcessor();

        // set up basic statsBox
        mBasicStatsBox = new BasicStatsBox(mGameProcessor, this, true);

        // set up statsBars for this mode
        mOrganismBar = new StatsBar("Organism Name", "Loading...");
        getChildren().add(mOrganismBar);

        mTimeBar = new StatsBar("Time", "0:0:00");
        getChildren().add(mTimeBar);
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        mBasicStatsBox.update(deltaTime);
        mOrganismBar.setValue(mWatcher.getmCurOrganism().getmName());
        mTimeBar.setValue(getTimeInString((int) mGameArea.getmElapsedTime()));
    }
}
