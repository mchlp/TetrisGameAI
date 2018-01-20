/*
 * Michael Pu
 * TetrisGameAI - AITrainStatsBox
 * ICS3U1 - Mr.Radulovic
 * January 13, 2018
 */

package frontend.aitrain;

import ai.GUITrainer;
import backend.GameProcessor;
import frontend.common.BasicStatsBox;
import frontend.common.GameArea;
import frontend.common.StatsBar;

public class AITrainStatsBox extends frontend.base.StatsBox {

    private StatsBar mOrganismBar;
    private StatsBar mGenerationBar;
    private StatsBar mOrganismNumBar;
    private StatsBar mTrainTimeBar;
    private StatsBar mTimeBar;
    private GameArea mGameArea;
    private GUITrainer mGUITrainer;
    private GameProcessor mGameProcessor;
    private BasicStatsBox mBasicStatsBox;


    public AITrainStatsBox(GameArea gameArea, GUITrainer GUITrainer) {
        super(gameArea.getmGameProcessor().getmGameMode());
        mGUITrainer = GUITrainer;
        mGameArea = gameArea;
        mGameProcessor = mGameArea.getmGameProcessor();

        mBasicStatsBox = new BasicStatsBox(mGameProcessor, this, true);

        mOrganismBar = new StatsBar("Organism Name", "Loading...");
        getChildren().add(mOrganismBar);

        mOrganismNumBar = new StatsBar("Organism Number", "Loading...");
        getChildren().add(mOrganismNumBar);

        mGenerationBar = new StatsBar("Generation", "Loading...");
        getChildren().add(mGenerationBar);

        mTrainTimeBar = new StatsBar("Training Time: ", "0:00");
        getChildren().add(mTrainTimeBar);

        mTimeBar = new StatsBar("Time", "0:0:00");
        getChildren().add(mTimeBar);
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        mBasicStatsBox.update(deltaTime);
        mOrganismBar.setValue(mGUITrainer.getmCurOrganism().getmName());
        mOrganismNumBar.setValue(mGUITrainer.getmCurOrganismIndex() + 1 + "/" + mGUITrainer.getmPopulation().getNumOrganisms());
        mGenerationBar.setValue(Integer.toString(mGUITrainer.getmPopulation().getmGeneration()));
        mTrainTimeBar.setValue(getTimeInString((int) (mGUITrainer.getmPopulation().getmTrainTime())));
        mTimeBar.setValue(getTimeInString((int) mGameArea.getmElapsedTime()));
    }
}
