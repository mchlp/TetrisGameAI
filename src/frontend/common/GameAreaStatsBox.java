/*
 * Michael Pu
 * TetrisGameAI - GameAreaStatsBox
 * ICS3U1 - Mr. Radulovic
 * December 30, 2017
 */

package frontend.common;

import backend.GameProcessor;
import javafx.scene.layout.VBox;

public class GameAreaStatsBox {

    protected GameProcessor mGameProcessor;
    private StatsBar mScoreBar;
    private StatsBar mLinesBar;
    private StatsBar mLevelBar;
    private StatsBar mStateBar;
    private boolean mShowGameState;

    public GameAreaStatsBox(GameProcessor gameProcessor, VBox parent, boolean showGameState) {

        mGameProcessor = gameProcessor;
        mShowGameState = showGameState;

        if (mShowGameState) {
            mStateBar = new StatsBar("Game State", "Loading...");
            parent.getChildren().add(mStateBar);
        }

        mScoreBar = new StatsBar("Score", "0");
        parent.getChildren().add(mScoreBar);

        mLinesBar = new StatsBar("Lines Cleared", "0");
        parent.getChildren().add(mLinesBar);

        mLevelBar = new StatsBar("Level", "0");
        parent.getChildren().add(mLevelBar);
    }

    public void update(double deltaTime) {
        if (mShowGameState) {
            mStateBar.setValue(mGameProcessor.getmGameState().message);
            mStateBar.setColour(mGameProcessor.getmGameState().colour);
        }
        mScoreBar.setValue(Integer.toString(mGameProcessor.getmScore()));
        mLinesBar.setValue(Integer.toString(mGameProcessor.getmNumLinesCleared()));
        mLevelBar.setValue(Integer.toString(mGameProcessor.getmLevel()));
    }
}
