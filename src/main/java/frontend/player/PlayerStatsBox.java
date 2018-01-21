/*
 * Michael Pu
 * TetrisGameAI - PlayerStatsBox
 * ICS3U1 - Mr.Radulovic
 * January 20, 2018
 */

package frontend.player;

import backend.GameProcessor;
import frontend.base.StatsBox;
import frontend.common.GameArea;
import frontend.common.BasicStatsBox;
import frontend.common.StatsBar;

/**
 * BasicStatsBox for single player mode.
 */
public class PlayerStatsBox extends StatsBox {

    private StatsBar mTimeBar;
    private GameArea mGameArea;
    private GameProcessor mGameProcessor;
    private BasicStatsBox mBasicStatsBox;

    public PlayerStatsBox(GameArea gameArea) {
        super(gameArea.getmGameProcessor().getmGameMode());

        // set up source of stats
        mGameArea = gameArea;
        mGameProcessor = mGameArea.getmGameProcessor();

        // set up basic statsBox
        mBasicStatsBox = new BasicStatsBox(mGameProcessor, this, true);

        // add time bar
        mTimeBar = new StatsBar("Time", "0:0:00");
        getChildren().add(mTimeBar);

    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        mBasicStatsBox.update(deltaTime);
        mTimeBar.setValue(getTimeInString((int) mGameArea.getmElapsedTime()));
    }
}
