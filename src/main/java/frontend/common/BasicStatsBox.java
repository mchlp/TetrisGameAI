/*
 * Michael Pu
 * TetrisGameAI - GameAreaStatsBox
 * ICS3U1 - Mr.Radulovic
 * January 13, 2018
 */

package frontend.common;

import backend.GameProcessor;
import javafx.scene.layout.VBox;

/**
 * Displays the stats of a GameArea.
 */
public class BasicStatsBox {

    /**
     * The stats of the {@link GameProcessor} to display.
     */
    protected GameProcessor mGameProcessor;

    /**
     * The StatsBar that displays the score.
     */
    private StatsBar mScoreBar;

    /**
     * The StatsBar that displays the lines cleared.
     */
    private StatsBar mLinesBar;

    /**
     * The StatsBar that displays the level.
     */
    private StatsBar mLevelBar;

    /**
     * The StatsBar that displays the current game state.
     */
    private StatsBar mStateBar;

    /**
     * To display the state of the game or not.
     */
    private boolean mShowGameState;

    /**
     * @param gameProcessor The game processor to display the stats of.
     * @param parent The VBox that will hold the contents of the BasicStatsBox.
     * @param showGameState To show the game state StatsBar or not.
     */
    public BasicStatsBox(GameProcessor gameProcessor, VBox parent, boolean showGameState) {

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
