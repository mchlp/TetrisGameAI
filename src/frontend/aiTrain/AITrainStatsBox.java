/*
 * Michael Pu
 * TetrisGameAI - AITrainStatsBox
 * ICS3U1 - Mr. Radulovic
 * December 25, 2017
 */

package frontend.aiTrain;

import ai.Trainer;
import frontend.base.StatsBox;
import frontend.common.GameArea;
import frontend.common.StatsBar;

public class AITrainStatsBox extends StatsBox {

    private StatsBar mOrganismBar;
    private Trainer mTrainer;

    public AITrainStatsBox(GameArea gameArea, Trainer trainer) {
        super(gameArea);
        mTrainer = trainer;

        mOrganismBar = new StatsBar("Organism", "Loading...");
        mOrganismBar.setSmallerFont();
        getChildren().add(mOrganismBar);
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        mOrganismBar.setValue(mTrainer.getmCurTrainOrganism().getmId().toString());
    }
}
