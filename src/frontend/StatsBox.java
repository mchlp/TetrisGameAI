/*
 * Michael Pu
 * TetrisGameAI - StatsBox
 * ICS3U1 - Mr. Radulovic
 * December 24, 2017
 */

package frontend;

import backend.Updatable;
import javafx.scene.layout.VBox;

public class StatsBox extends VBox implements Updatable {

    private GameArea mGameArea;

    private StatsBar mScoreBar;
    private StatsBar mLinesBar;
    private StatsBar mLevelBar;

    private static final int ELEMENT_SPACING = 10;

    public StatsBox(GameArea gameArea) {

        mGameArea = gameArea;

        setSpacing(ELEMENT_SPACING);

        mScoreBar = new StatsBar("Score", "0");
        getChildren().add(mScoreBar);

        mLinesBar = new StatsBar("Lines", "0");
        getChildren().add(mLinesBar);

        mLevelBar = new StatsBar("Level", "0");
        getChildren().add(mLevelBar);

    }

    @Override
    public void update(double deltaTime) {

        mScoreBar.setValue(Integer.toString(mGameArea.getmScore()));
        mLinesBar.setValue(Integer.toString(mGameArea.getmNumLinesCleared()));
        mLevelBar.setValue(Integer.toString(mGameArea.getmLevel()));

    }
}
