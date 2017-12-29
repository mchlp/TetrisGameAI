/*
 * Michael Pu
 * TetrisGameAI - AIWatchStatsBox
 * ICS3U1 - Mr.Radulovic
 * December 28, 2017
 */

package frontend.aiWatch;

import ai.Watcher;
import backend.GameBrain;
import frontend.common.GameAreaStatsBox;
import frontend.base.StatsBox;
import frontend.common.GameArea;
import frontend.common.StatsBar;

public class AIWatchStatsBox extends StatsBox {
    private StatsBar mOrganismBar;
    private Watcher mWatcher;
    private StatsBar mTimeBar;
    private GameArea mGameArea;
    private GameBrain mGameBrain;
    private GameAreaStatsBox mGameAreaStatsBox;

    public AIWatchStatsBox(GameArea gameArea, Watcher watcher) {
        super(gameArea.getmGameBrain().getmGameMode());
        mWatcher = watcher;
        mGameArea = gameArea;
        mGameBrain = mGameArea.getmGameBrain();

        mGameAreaStatsBox = new GameAreaStatsBox(mGameBrain, this);

        mOrganismBar = new StatsBar("Organism Name", "Loading...");
        mOrganismBar.setSmallerFont();
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
