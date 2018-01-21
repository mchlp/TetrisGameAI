/*
 * Michael Pu
 * TetrisGameAI - DoubleStatsBox
 * ICS3U1 - Mr.Radulovic
 * January 20, 2018
 */

package frontend.common;

import backend.GameProcessor;
import backend.GameMode;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * A box that displays that stats of two games at are both playing at once.
 */
public class DoubleStatsBox extends frontend.base.StatsBox {

    /**
     * The {@link BasicStatsBox} for the game on the left.
     */
    private BasicStatsBox mLeftBasicStatsBox;

    /**
     * The {@link BasicStatsBox} for the game on the right.
     */
    private BasicStatsBox mRightBasicStatsBox;

    /**
     * StatsBar for displaying the time elapsed.
     */
    private StatsBar mTimeBar;

    /**
     * The {@link GameProcessor} for the game on the left.
     */
    private GameProcessor mLeftGameProcessor;

    /**
     * The {@link GameProcessor} for the game on the right.
     */
    private GameProcessor mRightGameProcessor;

    /**
     * The {@link GameArea} for the game on the left.
     */
    private GameArea mLeftGameArea;

    /**
     * The {@link GameArea} for the game on the right.
     */
    private GameArea mRightGameArea;

    /**
     * Creates a box that displays the stats for two games that are both playing at once.
     *
     * @param gameMode The current game mode.
     * @param leftGameArea The {@link GameArea} of the game on the left.
     * @param rightGameArea The {@link GameArea} of the right game on the right.
     * @param leftLabelText The text to label the game on the left.
     * @param rightLabelText The text to label the game on the right.
     */
    public DoubleStatsBox(GameMode gameMode, GameArea leftGameArea, GameArea rightGameArea, String leftLabelText, String rightLabelText) {
        super(gameMode);

        // initialize member variables
        mLeftGameArea = leftGameArea;
        mRightGameArea = rightGameArea;

        mLeftGameProcessor = mLeftGameArea.getmGameProcessor();
        mRightGameProcessor = mRightGameArea.getmGameProcessor();

        // set up the StatsBar to keep track of the amount of time that has passed
        mTimeBar = new StatsBar("Time", "0:0:00");
        getChildren().add(mTimeBar);

        // set up VBox to hold the stats of the game on the left
        VBox leftStats = new VBox(5);
        leftStats.setPadding(new Insets(5));
        leftStats.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        getChildren().add(leftStats);

        // label for the game on the left
        Text leftLabel = new Text(leftLabelText);
        leftStats.getChildren().add(leftLabel);

        // set up BasicStatsBox for game on the left
        mLeftBasicStatsBox = new BasicStatsBox(mLeftGameProcessor, leftStats, true);

        // set up VBox to hold the stats of the game on the right
        VBox rightStats = new VBox(5);
        rightStats.setPadding(new Insets(5));
        rightStats.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        getChildren().add(rightStats);

        // label for game on the right
        Text rightLabel = new Text(rightLabelText);
        rightStats.getChildren().add(rightLabel);

        // set up the BasicStatsBox for the game on the right
        mRightBasicStatsBox = new BasicStatsBox(mRightGameProcessor, rightStats, true);
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        // update the stats boxes
        mLeftBasicStatsBox.update(deltaTime);
        mRightBasicStatsBox.update(deltaTime);
        // update the time StatsBar
        mTimeBar.setValue(getTimeInString((int) mLeftGameArea.getmElapsedTime()));
    }
}
