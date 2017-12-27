/*
 * Michael Pu
 * TetrisGameAI - AITrainStatsBox
 * ICS3U1 - Mr. Radulovic
 * December 26, 2017
 */

package frontend.aiTrain;

import ai.Trainer;
import backend.GameBrain;
import frontend.base.StatsBox;
import frontend.common.GameArea;
import frontend.common.StatsBar;

public class AITrainStatsBox extends StatsBox {

    private StatsBar mOrganismBar;
    private StatsBar mGenerationBar;
    private StatsBar mOrganismNumBar;
    private StatsBar mTrainTimeBar;
    private StatsBar mTimeBar;
    private StatsBar mStateBar;
    private GameArea mGameArea;
    private Trainer mTrainer;


    public AITrainStatsBox(GameArea gameArea, Trainer trainer) {
        super(gameArea.getmGameBrain());
        mTrainer = trainer;
        mGameArea = gameArea;

        mStateBar = new StatsBar("Game State", "Loading...");
        getChildren().add(1, mStateBar);

        mOrganismBar = new StatsBar("Organism ID", "Loading...");
        mOrganismBar.setSmallerFont();
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
        mStateBar.setValue(mGameBrain.getmGameState().message);
        mStateBar.setColour(mGameBrain.getmGameState().colour);
        mOrganismBar.setValue(mTrainer.getmCurOrganism().getmId().toString());
        mOrganismNumBar.setValue(mTrainer.getmCurOrganismIndex()+1 + "/" + mTrainer.getmPopulation().getNumOrganisms());
        mGenerationBar.setValue(Integer.toString(mTrainer.getmPopulation().getmGeneration()));
        mTrainTimeBar.setValue(getTimeInString((int) (mTrainer.getmPopulation().getmTrainTime())));
        mTimeBar.setValue(getTimeInString((int) mGameArea.getmElapsedTime()));
    }
}
