/*
 * Michael Pu
 * TetrisGameAI - StatsBox
 * ICS3U1 - Mr. Radulovic
 * December 29, 2017
 */

package frontend.base;

import backend.GameMode;
import backend.Updatable;
import frontend.common.StatsBar;
import javafx.scene.layout.VBox;

public abstract class StatsBox extends VBox implements Updatable {

    private static final int ELEMENT_SPACING = 10;
    protected GameMode mGameMode;

    private StatsBar mModeBar;

    public StatsBox(GameMode gameMode) {

        mGameMode = gameMode;

        setSpacing(ELEMENT_SPACING);

        mModeBar = new StatsBar("Game Mode", "Loading...");
        getChildren().add(mModeBar);
    }

    protected String zeroPad(int num, int numPlaces) {
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
        mModeBar.setValue(mGameMode.message);
        mModeBar.setColour(mGameMode.colour);
    }
}
