/*
 * Michael Pu
 * TetrisGameAI - DoubleStatsBox
 * ICS3U1 - Mr. Radulovic
 * December 29, 2017
 */

package frontend.common;

import backend.GameBrain;
import backend.GameMode;
import frontend.base.StatsBox;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class DoubleStatsBox extends StatsBox {

    private GameAreaStatsBox mLeftGameAreaStatsBox;
    private GameAreaStatsBox mRightGameAreaStatsBox;
    private StatsBar mTimeBar;
    private GameBrain mLeftGameBrain;
    private GameBrain mRightGameBrain;
    private GameArea mLeftGameArea;
    private GameArea mRightGameArea;

    public DoubleStatsBox(GameMode gameMode, GameArea leftGameArea, GameArea rightGameArea) {
        super(gameMode);

        mLeftGameArea = leftGameArea;
        mRightGameArea = rightGameArea;

        mLeftGameBrain = mLeftGameArea.getmGameBrain();
        mRightGameBrain = mRightGameArea.getmGameBrain();

        mTimeBar = new StatsBar("Time", "0:0:00");
        getChildren().add(mTimeBar);

        VBox leftStats = new VBox(5);
        leftStats.setPadding(new Insets(5));
        leftStats.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        getChildren().add(leftStats);

        Text leftLabel = new Text("Left Stats");
        leftStats.getChildren().add(leftLabel);

        mLeftGameAreaStatsBox = new GameAreaStatsBox(mLeftGameBrain, leftStats);

        VBox rightStats = new VBox(5);
        rightStats.setPadding(new Insets(5));
        rightStats.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        getChildren().add(rightStats);

        Text rightLabel = new Text("Right Stats");
        rightStats.getChildren().add(rightLabel);

        mRightGameAreaStatsBox = new GameAreaStatsBox(mRightGameBrain, rightStats);
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        mLeftGameAreaStatsBox.update(deltaTime);
        mRightGameAreaStatsBox.update(deltaTime);
        mTimeBar.setValue(getTimeInString((int) mLeftGameArea.getmElapsedTime()));
    }
}
