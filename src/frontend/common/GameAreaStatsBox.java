/*
 * Michael Pu
 * TetrisGameAI - GameAreaStatsBox
 * ICS3U1 - Mr. Radulovic
 * December 29, 2017
 */

package frontend.common;

import backend.GameBrain;
import javafx.scene.layout.VBox;

public class GameAreaStatsBox {

    protected GameBrain mGameBrain;
    private StatsBar mScoreBar;
    private StatsBar mLinesBar;
    private StatsBar mLevelBar;
    private StatsBar mStateBar;

    public GameAreaStatsBox(GameBrain gameBrain, VBox parent) {

        mGameBrain = gameBrain;

        mStateBar = new StatsBar("Game State", "Loading...");
        parent.getChildren().add(mStateBar);

        mScoreBar = new StatsBar("Score", "0");
        parent.getChildren().add(mScoreBar);

        mLinesBar = new StatsBar("Lines Cleared", "0");
        parent.getChildren().add(mLinesBar);

        mLevelBar = new StatsBar("Level", "0");
        parent.getChildren().add(mLevelBar);
    }

    public void update(double deltaTime) {
        mStateBar.setValue(mGameBrain.getmGameState().message);
        mStateBar.setColour(mGameBrain.getmGameState().colour);
        mScoreBar.setValue(Integer.toString(mGameBrain.getmScore()));
        mLinesBar.setValue(Integer.toString(mGameBrain.getmNumLinesCleared()));
        mLevelBar.setValue(Integer.toString(mGameBrain.getmLevel()));
    }
}
