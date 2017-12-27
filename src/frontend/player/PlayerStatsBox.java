/*
 * Michael Pu
 * TetrisGameAI - PlayerStatsBox
 * ICS3U1 - Mr. Radulovic
 * December 26, 2017
 */

package frontend.player;

import frontend.base.StatsBox;
import frontend.common.GameArea;
import frontend.common.StatsBar;

public class PlayerStatsBox extends StatsBox {

    private StatsBar mTimeBar;
    private StatsBar mStateBar;
    private GameArea mGameArea;

    public PlayerStatsBox(GameArea gameArea) {
        super(gameArea.getmGameBrain());

        mGameArea = gameArea;

        mStateBar = new StatsBar("Game State", "Loading...");
        getChildren().add(1, mStateBar);

        mTimeBar = new StatsBar("Time", "0:0:00");
        getChildren().add(mTimeBar);
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        mStateBar.setValue(mGameBrain.getmGameState().message);
        mStateBar.setColour(mGameBrain.getmGameState().colour);
        mTimeBar.setValue(getTimeInString((int) mGameArea.getmElapsedTime()));
    }
}
