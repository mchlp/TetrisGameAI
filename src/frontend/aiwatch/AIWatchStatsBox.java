/*
 * Michael Pu
 * TetrisGameAI - AIWatchStatsBox
 * ICS3U1 - Mr.Radulovic
 * December 28, 2017
 */

package frontend.aiwatch;

import ai.Watcher;
import backend.GameProcessor;
import frontend.base.StatsBox;
import frontend.common.GameArea;
import frontend.common.GameAreaStatsBox;
import frontend.common.StatsBar;

public class AIWatchStatsBox extends StatsBox {
    private StatsBar mOrganismBar;
    private Watcher mWatcher;
    private StatsBar mTimeBar;
    private GameArea mGameArea;
    private GameProcessor mGameProcessor;
    private GameAreaStatsBox mGameAreaStatsBox;

    public AIWatchStatsBox(GameArea gameArea, Watcher watcher) {
        super(gameArea.getmGameProcessor().getmGameMode());
        mWatcher = watcher;
        mGameArea = gameArea;
        mGameProcessor = mGameArea.getmGameProcessor();

        mGameAreaStatsBox = new GameAreaStatsBox(mGameProcessor, this, true);

        mOrganismBar = new StatsBar("Organism Name", "Loading...");
        getChildren().add(mOrganismBar);

        mTimeBar = new StatsBar("Time", "0:0:00");
        getChildren().add(mTimeBar);
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        mGameAreaStatsBox.update(deltaTime);
        mOrganismBar.setValue(mWatcher.getmCurOrganism().getmName());
        mTimeBar.setValue(getTimeInString((int) mGameArea.getmElapsedTime()));
    }
}
