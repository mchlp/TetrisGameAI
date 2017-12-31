/*
 * Michael Pu
 * TetrisGameAI - AITrainStatsBox
 * ICS3U1 - Mr.Radulovic
 * December 28, 2017
 */

package frontend.aitrain;

import ai.Trainer;
import backend.GameProcessor;
import frontend.base.StatsBox;
import frontend.common.GameArea;
import frontend.common.GameAreaStatsBox;
import frontend.common.StatsBar;

public class AITrainStatsBox extends StatsBox {

    private StatsBar mOrganismBar;
    private StatsBar mGenerationBar;
    private StatsBar mOrganismNumBar;
    private StatsBar mTrainTimeBar;
    private StatsBar mTimeBar;
    private GameArea mGameArea;
    private Trainer mTrainer;
    private GameProcessor mGameProcessor;
    private GameAreaStatsBox mGameAreaStatsBox;


    public AITrainStatsBox(GameArea gameArea, Trainer trainer) {
        super(gameArea.getmGameProcessor().getmGameMode());
        mTrainer = trainer;
        mGameArea = gameArea;
        mGameProcessor = mGameArea.getmGameProcessor();

        mGameAreaStatsBox = new GameAreaStatsBox(mGameProcessor, this, true);

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
        mGameAreaStatsBox.update(deltaTime);
        mOrganismBar.setValue(mTrainer.getmCurOrganism().getmName());
        mOrganismNumBar.setValue(mTrainer.getmCurOrganismIndex() + 1 + "/" + mTrainer.getmPopulation().getNumOrganisms());
        mGenerationBar.setValue(Integer.toString(mTrainer.getmPopulation().getmGeneration()));
        mTrainTimeBar.setValue(getTimeInString((int) (mTrainer.getmPopulation().getmTrainTime())));
        mTimeBar.setValue(getTimeInString((int) mGameArea.getmElapsedTime()));
    }
}
