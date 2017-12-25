/*
 * Michael Pu
 * TetrisGameAI - Trainer
 * ICS3U1 - Mr. Radulovic
 * December 25, 2017
 */

package ai;

import backend.ControllerKeys;
import backend.Updatable;
import frontend.common.GameArea;
import frontend.common.GameController;
import frontend.common.GameState;

public class Trainer implements Updatable {

    private GameArea mGameArea;
    private GameController mGameController;
    private Population mPopulation;
    private int mCurTrainOrganismIndex;
    private Organism mCurTrainOrganism;

    public Trainer(GameArea gameArea, GameController gameController, Population population) {
        mGameArea = gameArea;
        mGameController = gameController;
        mPopulation = population;
        mCurTrainOrganismIndex = 0;
        mCurTrainOrganism = population.getOrganism(0);
    }

    @Override
    public void update(double deltaTime) {
        switch (mGameArea.getmGameState()) {
            case PLAYING:
                mGameController.keyPressed(ControllerKeys.DROP);
                break;
            case OVER:
                mGameController.keyPressed(ControllerKeys.RESTART);
                break;
        }
    }

    public Organism getmCurTrainOrganism() {
        return mCurTrainOrganism;
    }
}
