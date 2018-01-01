/*
 * Michael Pu
 * TetrisGameAI - Trainer
 * ICS3U1 - Mr. Radulovic
 * December 30, 2017
 */

package ai;

import backend.ControllerKeys;
import backend.GameProcessor;
import frontend.common.GameController;

public class Trainer extends GUIBrain {

    private Population mPopulation;
    private int mCurOrganismIndex;

    public Trainer(GameProcessor gameProcessor, GameController gameController, Population population) {
        super(gameProcessor, gameController, true);
        mPopulation = population;
        goToFirstOrganism();
    }

    public void update(double deltaTime) {
        super.update(deltaTime);
        switch (mGameProcessor.getmGameState()) {
            case PLAYING:
                mPopulation.addmTrainTime(deltaTime);
                break;
            case OVER:
                if (mGameProcessor.getmScore() > mCurOrganism.getmMaxScore()) {
                    mCurOrganism.setmMaxScore(mGameProcessor.getmScore());
                }
                if (mGameProcessor.getmLevel() > mCurOrganism.getmMaxLevel()) {
                    mCurOrganism.setmMaxLevel(mGameProcessor.getmLevel());
                }
                if (mGameProcessor.getmNumLinesCleared() > mCurOrganism.getmMaxLinesCleared()) {
                    mCurOrganism.setmMaxLinesCleared(mGameProcessor.getmNumLinesCleared());
                }
                mCurOrganism.addScore(mGameProcessor.getmScore());
                mCurOrganism.setmGeneration(mPopulation.getmGeneration());

                if (mCurOrganismIndex == mPopulation.getNumOrganisms() - 1) {
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
        mCurOrganismIndex++;
        mCurOrganism = mPopulation.getOrganism(mCurOrganismIndex);
        System.out.println(mCurOrganism.getStatus());
    }

    public Population getmPopulation() {
        return mPopulation;
    }

    public int getmCurOrganismIndex() {
        return mCurOrganismIndex;
    }
}
