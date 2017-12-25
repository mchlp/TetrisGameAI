/*
 * Michael Pu
 * TetrisGameAI - StatsBox
 * ICS3U1 - Mr. Radulovic
 * December 24, 2017
 */

package frontend;

import backend.Updatable;
import javafx.scene.layout.VBox;

import java.time.Duration;
import java.util.Calendar;
import java.util.Date;

public class StatsBox extends VBox implements Updatable {

    private GameArea mGameArea;

    private StatsBar mStateBar;
    private StatsBar mScoreBar;
    private StatsBar mLinesBar;
    private StatsBar mLevelBar;
    private StatsBar mTimeBar;

    private static final int ELEMENT_SPACING = 10;

    public StatsBox(GameArea gameArea) {

        mGameArea = gameArea;

        setSpacing(ELEMENT_SPACING);

        mStateBar = new StatsBar("Game State", "Loading...");
        getChildren().add(mStateBar);

        mScoreBar = new StatsBar("Score", "0");
        getChildren().add(mScoreBar);

        mLinesBar = new StatsBar("Lines", "0");
        getChildren().add(mLinesBar);

        mLevelBar = new StatsBar("Level", "0");
        getChildren().add(mLevelBar);

        mTimeBar = new StatsBar("Time", "0:0:00");
        getChildren().add(mTimeBar);

    }

    private String zeroPad(int num, int numPlaces) {
        String strNum = Long.toString(num);
        while (strNum.length() < numPlaces) {
            strNum = "0".concat(strNum);
        }
        return strNum;
    }

    private String getTimeInString(int time) {
        int hours = time/(60*60);
        time %= (60*60);
        int mins = time/(60);
        time %= (60);
        int secs = time;
        return (hours + ":" + zeroPad(mins, 2) + ":" + zeroPad(secs, 2));
    }


    @Override
    public void update(double deltaTime) {
        mStateBar.setValue(mGameArea.getmGameState().message);
        mStateBar.setColour(mGameArea.getmGameState().colour);
        mScoreBar.setValue(Integer.toString(mGameArea.getmScore()));
        mLinesBar.setValue(Integer.toString(mGameArea.getmNumLinesCleared()));
        mLevelBar.setValue(Integer.toString(mGameArea.getmLevel()));
        mTimeBar.setValue(getTimeInString((int) mGameArea.getmElapsedTime()));

    }
}
