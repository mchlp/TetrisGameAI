/*
 * Michael Pu
 * TetrisGameAI - AIFastTrainStatsBox
 * ICS3U1 - Mr. Radulovic
 * December 25, 2017
 */

package frontend.aiFastTrain;

import ai.FastTrainer;
import backend.GameBrain;
import frontend.base.StatsBox;
import frontend.common.GameArea;
import frontend.common.StatsBar;

public class AIFastTrainStatsBox extends StatsBox {

    private StatsBar mOrganismBar;
    private StatsBar mGenerationBar;
    private StatsBar mOrganismNumBar;
    private StatsBar mTrainTimeBar;
    private StatsBar mTopScoreBar;
    private FastTrainer mFastTrainer;

    public AIFastTrainStatsBox(GameBrain gameBrain, FastTrainer fastTrainer) {
        super(gameBrain);
        mFastTrainer = fastTrainer;

        mOrganismBar = new StatsBar("Organism ID", "Loading...");
        mOrganismBar.setSmallerFont();
        getChildren().add(mOrganismBar);

        mOrganismNumBar = new StatsBar("Organism Number", "Loading...");
        getChildren().add(mOrganismNumBar);

        mGenerationBar = new StatsBar("Generation", "Loading...");
        getChildren().add(mGenerationBar);

        mTrainTimeBar = new StatsBar("Training Time: ", "0:00");
        getChildren().add(mTrainTimeBar);

        mTopScoreBar = new StatsBar("Top Score: ", "0");
        getChildren().add(mTopScoreBar);
    }

    @Override
    public void update(double deltaTime) {

        super.update(deltaTime);
        mOrganismBar.setValue(mFastTrainer.getmCurOrganism().getmId().toString());
        mOrganismNumBar.setValue(mFastTrainer.getmCurOrganismIndex()+1 + "/" + mFastTrainer.getmPopulation().getNumOrganisms());
        mGenerationBar.setValue(Integer.toString(mFastTrainer.getmPopulation().getmGeneration()));
        mTrainTimeBar.setValue(getTimeInString((int) (mFastTrainer.getmPopulation().getmTrainTime())));
        mTopScoreBar.setValue(Integer.toString(mFastTrainer.getmTopScore()));
    }
}