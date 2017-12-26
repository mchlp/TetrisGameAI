/*
 * Michael Pu
 * TetrisGameAI - Trainer
 * ICS3U1 - Mr. Radulovic
 * December 25, 2017
 */

package ai;

import backend.ControllerKeys;
import backend.Updatable;
import frontend.common.*;

public class Trainer extends Brain implements Updatable {

    private Population mPopulation;
    private int mCurOrganismIndex;


    public Trainer(GameArea gameArea, GameController gameController, Population population) {
        super(gameArea, gameController);
        mPopulation = population;
        goToFirstOrganism();
    }

    public void update(double deltaTime) {
        switch (mGameArea.getmGameState()) {
            case PLAYING:
                mPopulation.addmTrainTime(deltaTime);
                break;
            case OVER:
                if (mCurOrganismIndex == mPopulation.getNumOrganisms()-1) {
                    mPopulation.evolve();
                    goToFirstOrganism();
                } else {
                    prepareNextOrganism();
                }
                mGameController.keyPressed(ControllerKeys.RESTART);
                break;
        }
        super.update(deltaTime);

    }

    private void goToFirstOrganism() {
        mCurOrganismIndex = -1;
        prepareNextOrganism();
    }

    private void prepareNextOrganism() {
        if (mCurOrganismIndex >= 0) {
            mCurOrganism.printFitness();
        }
        mCurOrganismIndex++;
        mCurOrganism = mPopulation.getOrganism(mCurOrganismIndex);
        mCurOrganism.printGenes();
    }

    public Population getmPopulation() {
        return mPopulation;
    }

    public int getmCurOrganismIndex() {
        return mCurOrganismIndex;
    }
}
