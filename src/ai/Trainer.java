/*
 * Michael Pu
 * TetrisGameAI - Trainer
 * ICS3U1 - Mr. Radulovic
 * December 25, 2017
 */

package ai;

import backend.ControllerKeys;
import backend.GameBrain;
import frontend.common.*;

public class Trainer extends GUIBrain {

    private Population mPopulation;
    private int mCurOrganismIndex;

    public Trainer(GameBrain gameBrain, GameController gameController, Population population) {
        super(gameBrain, gameController);
        mPopulation = population;
        goToFirstOrganism();
    }

    public void update(double deltaTime) {
        super.update(deltaTime);
        switch (mGameBrain.getmGameState()) {
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
    }

    private void goToFirstOrganism() {
        mCurOrganismIndex = -1;
        prepareNextOrganism();
    }

    private void prepareNextOrganism() {
        if (mCurOrganismIndex >= 0) {
            System.out.println(mCurOrganism.printFitness());
        }
        mCurOrganismIndex++;
        mCurOrganism = mPopulation.getOrganism(mCurOrganismIndex);
        System.out.println(mCurOrganism.printGenes());
    }

    public Population getmPopulation() {
        return mPopulation;
    }

    public int getmCurOrganismIndex() {
        return mCurOrganismIndex;
    }
}
