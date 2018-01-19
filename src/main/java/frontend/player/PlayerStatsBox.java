/*
 * Michael Pu
 * TetrisGameAI - PlayerStatsBox
 * ICS3U1 - Mr.Radulovic
 * January 13, 2018
 */

package frontend.player;

import backend.GameProcessor;
import frontend.common.GameArea;
import frontend.common.StatsBox;
import frontend.common.StatsBar;

public class PlayerStatsBox extends frontend.base.StatsBox {

    private StatsBar mTimeBar;
    private GameArea mGameArea;
    private GameProcessor mGameProcessor;
    private StatsBox mStatsBox;

    public PlayerStatsBox(GameArea gameArea) {
        super(gameArea.getmGameProcessor().getmGameMode());

        mGameArea = gameArea;
        mGameProcessor = mGameArea.getmGameProcessor();

        mStatsBox = new StatsBox(mGameProcessor, this, true);
        mTimeBar = new StatsBar("Time", "0:0:00");
        getChildren().add(mTimeBar);

    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        mStatsBox.update(deltaTime);
        mTimeBar.setValue(getTimeInString((int) mGameArea.getmElapsedTime()));
    }
}
