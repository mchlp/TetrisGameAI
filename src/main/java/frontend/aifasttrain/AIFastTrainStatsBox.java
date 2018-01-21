/*
 * Michael Pu
 * TetrisGameAI - AIFastTrainStatsBox
 * ICS3U1 - Mr.Radulovic
 * January 20, 2018
 */

package frontend.aifasttrain;

import ai.FastTrainer;
import backend.GameProcessor;
import frontend.base.StatsBox;
import frontend.common.BasicStatsBox;
import frontend.common.StatsBar;

/**
 * StatsBox for AI fast train mode.
 */
public class AIFastTrainStatsBox extends StatsBox {

    private StatsBar mOrganismBar;
    private StatsBar mGenerationBar;
    private StatsBar mOrganismNumBar;
    private StatsBar mTrainTimeBar;
    private StatsBar mTopScoreBar;
    private FastTrainer mFastTrainer;
    private BasicStatsBox mBasicStatsBox;

    public AIFastTrainStatsBox(GameProcessor gameProcessor, FastTrainer fastTrainer) {
        super(gameProcessor.getmGameMode());
        mFastTrainer = fastTrainer;

        // set up basic statsBox
        mBasicStatsBox = new BasicStatsBox(gameProcessor, this, false);

        // add additional stats bars for this mode
        mOrganismBar = new StatsBar("Organism Name", "Loading...");
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
        mBasicStatsBox.update(deltaTime);
        mOrganismBar.setValue(mFastTrainer.getmCurOrganism().getmName());
        mOrganismNumBar.setValue(mFastTrainer.getmCurOrganismIndex() + 1 + "/" + mFastTrainer.getmPopulation().getNumOrganisms());
        mGenerationBar.setValue(Integer.toString(mFastTrainer.getmPopulation().getmGeneration()));
        mTrainTimeBar.setValue(getTimeInString((int) (mFastTrainer.getmPopulation().getmTrainTime())));
        mTopScoreBar.setValue(Integer.toString(mFastTrainer.getmTopScore()));
    }
}
