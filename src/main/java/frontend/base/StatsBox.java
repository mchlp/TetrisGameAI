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

/**
 * Displays the current stats of the game. Each stat is displayed in the form of a {@link StatsBar}.
 */
public abstract class StatsBox extends VBox implements Updatable {

    /**
     * Spacing between elements.
     */
    private static final int ELEMENT_SPACING = 10;

    /**
     * The current game mode.
     */
    protected GameMode mGameMode;

    /**
     * The {@link StatsBar} that displays the mode the game is currently in.
     */
    private StatsBar mModeBar;

    public StatsBox(GameMode gameMode) {

        // set up display properties
        setSpacing(ELEMENT_SPACING);

        // initialize member variables
        mGameMode = gameMode;
        mModeBar = new StatsBar("Game Mode", "Loading...");
        getChildren().add(mModeBar);
    }

    /**
     * Takes a integer and adds zeros to the front of the integer until it has reached the desired number of places. If
     * the integer already exceeds the number of places, the number is not changed.
     *
     * @param num       The integer to zero-pad.
     * @param numPlaces The number of places that the final number should have.
     * @return A string representing the zero-padded number.
     */
    protected String zeroPad(int num, int numPlaces) {
        String strNum = Integer.toString(num);
        while (strNum.length() < numPlaces) {
            strNum = "0".concat(strNum);
        }
        return strNum;
    }

    /**
     * Takes an integer number of seconds and formats it (H:MM:SS).
     *
     * @param time The number of seconds.
     * @return A string representing the formatted time.
     */
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
        // sets the value and the background colour of the mode bar
        mModeBar.setValue(mGameMode.message);
        mModeBar.setColour(mGameMode.colour);
    }
}
