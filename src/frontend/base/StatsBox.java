/*
 * Michael Pu
 * TetrisGameAI - StatsBox
 * ICS3U1 - Mr.Radulovic
 * December 28, 2017
 */

package frontend.base;

import backend.GameBrain;
import backend.Updatable;
import frontend.common.StatsBar;
import javafx.scene.layout.VBox;

public abstract class StatsBox extends VBox implements Updatable {

    private static final int ELEMENT_SPACING = 10;
    protected GameBrain mGameBrain;

    private StatsBar mModeBar;
    private StatsBar mScoreBar;
    private StatsBar mLinesBar;
    private StatsBar mLevelBar;

    public StatsBox(GameBrain gameBrain) {

        mGameBrain = gameBrain;

        setSpacing(ELEMENT_SPACING);

        mModeBar = new StatsBar("Game Mode", "Loading...");
        getChildren().add(mModeBar);

        mScoreBar = new StatsBar("Score", "0");
        getChildren().add(mScoreBar);

        mLinesBar = new StatsBar("Lines Cleared", "0");
        getChildren().add(mLinesBar);

        mLevelBar = new StatsBar("Level", "0");
        getChildren().add(mLevelBar);
    }

    private String zeroPad(int num, int numPlaces) {
        String strNum = Long.toString(num);
        while (strNum.length() < numPlaces) {
            strNum = "0".concat(strNum);
        }
        return strNum;
    }

    protected String getTimeInString(int time) {
        int hours = time / (60 * 60);
        time %= (60 * 60);
        int mins = time / (60);
        time %= (60);
        int secs = time;
        return (hours + ":" + zeroPad(mins, 2) + ":" + zeroPad(secs, 2));
    }


    @Override
    public void update(double deltaTime) {
        mModeBar.setValue(mGameBrain.getmGameMode().message);
        mModeBar.setColour(mGameBrain.getmGameMode().colour);
        mScoreBar.setValue(Integer.toString(mGameBrain.getmScore()));
        mLinesBar.setValue(Integer.toString(mGameBrain.getmNumLinesCleared()));
        mLevelBar.setValue(Integer.toString(mGameBrain.getmLevel()));
    }
}
