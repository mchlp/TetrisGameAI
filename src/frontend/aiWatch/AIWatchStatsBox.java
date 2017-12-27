/*
 * Michael Pu
 * TetrisGameAI - AIWatchStatsBox
 * ICS3U1 - Mr. Radulovic
 * December 26, 2017
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
    private StatsBar mTimeBar;
    private StatsBar mStateBar;
    private GameArea mGameArea;

    public AIWatchStatsBox(GameArea gameArea, Watcher watcher) {
        super(gameArea.getmGameBrain());
        mWatcher = watcher;
        mGameArea = gameArea;

        mStateBar = new StatsBar("Game State", "Loading...");
        getChildren().add(1, mStateBar);

        mOrganismBar = new StatsBar("Organism ID", "Loading...");
        mOrganismBar.setSmallerFont();
        getChildren().add(mOrganismBar);

        mTimeBar = new StatsBar("Time", "0:0:00");
        getChildren().add(mTimeBar);
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        mStateBar.setValue(mGameBrain.getmGameState().message);
        mStateBar.setColour(mGameBrain.getmGameState().colour);
        mOrganismBar.setValue(mWatcher.getmCurOrganism().getmId().toString());
        mTimeBar.setValue(getTimeInString((int) mGameArea.getmElapsedTime()));
    }
}
