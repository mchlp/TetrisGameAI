/*
 * Michael Pu
 * TetrisGameAI - PlayerStatsBox
 * ICS3U1 - Mr.Radulovic
 * December 28, 2017
 */

package frontend.player;

import backend.GameProcessor;
import frontend.base.StatsBox;
import frontend.common.GameArea;
import frontend.common.GameAreaStatsBox;
import frontend.common.StatsBar;

public class PlayerStatsBox extends StatsBox {

    private StatsBar mTimeBar;
    private GameArea mGameArea;
    private GameProcessor mGameProcessor;
    private GameAreaStatsBox mGameAreaStatsBox;

    public PlayerStatsBox(GameArea gameArea) {
        super(gameArea.getmGameProcessor().getmGameMode());

        mGameArea = gameArea;
        mGameProcessor = mGameArea.getmGameProcessor();

        mGameAreaStatsBox = new GameAreaStatsBox(mGameProcessor, this, true);
        mTimeBar = new StatsBar("Time", "0:0:00");
        getChildren().add(mTimeBar);

    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        mGameAreaStatsBox.update(deltaTime);
        mTimeBar.setValue(getTimeInString((int) mGameArea.getmElapsedTime()));
    }
}
