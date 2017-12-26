/*
 * Michael Pu
 * TetrisGameAI - AIFastTrainStatsBox
 * ICS3U1 - Mr. Radulovic
 * December 25, 2017
 */

package frontend.aiFastTrain;

import ai.FastTrainer;
import ai.Trainer;
import frontend.base.StatsBox;
import frontend.common.GameArea;
import frontend.common.StatsBar;

public class AIFastTrainStatsBox extends StatsBox {

    private StatsBar mOrganismBar;
    private StatsBar mGenerationBar;
    private StatsBar mOrganismNumBar;
    private StatsBar mTrainTimeBar;
    private FastTrainer mFastTrainer;

    public AIFastTrainStatsBox(GameArea gameArea, FastTrainer fastTrainer) {
        super(gameArea);
        mFastTrainer = mFastTrainer;

        mOrganismBar = new StatsBar("Organism ID", "Loading...");
        mOrganismBar.setSmallerFont();
        getChildren().add(mOrganismBar);

        mOrganismNumBar = new StatsBar("Organism Number", "Loading...");
        getChildren().add(mOrganismNumBar);

        mGenerationBar = new StatsBar("Generation", "Loading...");
        getChildren().add(mGenerationBar);

        mTrainTimeBar = new StatsBar("Training Time: ", "0:00");
        getChildren().add(mTrainTimeBar);
    }

    @Override
    public void update(double deltaTime) {
        /*
        super.update(deltaTime);
        mOrganismBar.setValue(fas.getmCurOrganism().getmId().toString());
        mOrganismNumBar.setValue(mTrainer.getmCurOrganismIndex()+1 + "/" + mTrainer.getmPopulation().getNumOrganisms());
        mGenerationBar.setValue(Integer.toString(mTrainer.getmPopulation().getmGeneration()));
        mTrainTimeBar.setValue(getTimeInString((int) (mTrainer.getmPopulation().getmTrainTime())));
        */
    }
}
